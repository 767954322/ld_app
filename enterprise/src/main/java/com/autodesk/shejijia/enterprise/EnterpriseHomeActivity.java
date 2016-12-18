package com.autodesk.shejijia.enterprise;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.autodesk.shejijia.enterprise.personalcenter.PersonalCenterContract;
import com.autodesk.shejijia.enterprise.personalcenter.PersonalCenterPresenter;
import com.autodesk.shejijia.enterprise.personalcenter.activity.MoreActivity;
import com.autodesk.shejijia.enterprise.personalcenter.activity.ProjectListActivity;
import com.autodesk.shejijia.shared.components.common.entity.ResponseError;
import com.autodesk.shejijia.shared.components.common.utility.BackGroundUtils;
import com.autodesk.shejijia.shared.components.common.utility.ImageUtils;
import com.autodesk.shejijia.shared.components.common.utility.ToastUtils;
import com.autodesk.shejijia.shared.components.common.utility.UserInfoUtils;
import com.autodesk.shejijia.shared.components.im.constants.MPChatConstants;
import com.autodesk.shejijia.shared.components.im.fragment.MPThreadListFragment;
import com.autodesk.shejijia.shared.components.message.ProjectMessageCenterContract;
import com.autodesk.shejijia.shared.components.nodeprocess.ui.fragment.GroupChatFragment;
import com.autodesk.shejijia.shared.components.issue.ui.fragment.IssueListFragment;
import com.autodesk.shejijia.shared.components.nodeprocess.ui.fragment.ProjectListFragment;
import com.autodesk.shejijia.shared.framework.activity.BaseActivity;
import com.pgyersdk.update.PgyUpdateManager;

import java.util.Random;

public class EnterpriseHomeActivity extends BaseActivity implements View.OnClickListener, OnCheckedChangeListener,
        NavigationView.OnNavigationItemSelectedListener,PersonalCenterContract.View {
    private static final String FRAGMENT_TAG_TASK = "taskList";
    private static final String FRAGMENT_TAG_ISSUE = "issueList";
    private static final String FRAGMENT_TAG_GROUP_CHAT = "groupChatList";
    private static final int SYS_INTENT_REQUEST = 0XFF01;
    private static final int CAMERA_INTENT_REQUEST = 0XFF02;
    private static final int CROP_SMALL_PICTURE = 5;

    private RadioButton mTaskBtn;
    private RadioButton mIssueBtn;
    private RadioButton mSessionBtn;
    private RadioGroup mBottomGroup;
    private ImageView mHeadPicBtn;
    private Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private TextView toolbarTitle;//self define
    private TextView mUserNameView;
    private TextView mUserRoleView;
    private PopupWindow mBottomPopUp;
    private String currentFragmentTag;
    private Uri mUritempFile;

    private PersonalCenterContract.Presenter mPersonalCenterContract;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_enterprise_main;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mPersonalCenterContract = new PersonalCenterPresenter(this);
        mPersonalCenterContract.getPersonalHeadPicture(getClass().getSimpleName());
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void initView() {
        mTaskBtn = (RadioButton) this.findViewById(R.id.rdoBtn_project_task);
        mIssueBtn = (RadioButton) this.findViewById(R.id.rdoBtn_project_issue);
        mSessionBtn = (RadioButton) this.findViewById(R.id.rdoBtn_project_session);
        mBottomGroup = (RadioGroup) this.findViewById(R.id.rdoGrp_project_list);
        mDrawerLayout = (DrawerLayout) this.findViewById(R.id.home_drawer_layout);
        mNavigationView = (NavigationView) this.findViewById(R.id.home_navigation_view);
        toolbar = (Toolbar) this.findViewById(R.id.toolbar_topBar);
        toolbarTitle = (TextView) toolbar.findViewById(R.id.tv_toolbar_title);
        View headerView = mNavigationView.getHeaderView(0);
        mUserNameView = (TextView) headerView.findViewById(R.id.tv_user_name);
        mUserRoleView = (TextView) headerView.findViewById(R.id.tv_user_role);
        mHeadPicBtn = (ImageView) headerView.findViewById(R.id.imgBtn_personal_headPic);
    }

    @Override
    protected void initListener() {
        //init RadioBtn Event
        mBottomGroup.setOnCheckedChangeListener(this);
        mTaskBtn.setChecked(true);
        //init NavigationView Event
        mNavigationView.setNavigationItemSelectedListener(this);
        mHeadPicBtn.setOnClickListener(this);
        toolbarTitle.setOnClickListener(this);
        //pgy update register
        if (BuildConfig.DEBUG) {
            PgyUpdateManager.register(this);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgBtn_personal_headPic:
                ShowBottomPopup();
                break;
            case R.id.tv_toolbar_title:
                // TODO: 10/25/16  get date from calendar and set data to taskListFragment
                ToastUtils.showShort(EnterpriseHomeActivity.this, "title");
                ProjectListFragment projectListFragment = (ProjectListFragment) getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG_TASK);
                if (projectListFragment != null) {
                    projectListFragment.refreshProjectListByDate("2016-10-25");
                }
                break;
            default:
                break;
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkId) {

        switch (checkId) {
            case R.id.rdoBtn_project_task:
                controlShowFragment(FRAGMENT_TAG_TASK);
                initToolbar(toolbar, toolbarTitle, true, true, getString(R.string.toolbar_task_title));
                break;
            case R.id.rdoBtn_project_issue:
                controlShowFragment(FRAGMENT_TAG_ISSUE);
                initToolbar(toolbar, toolbarTitle, true, false, getString(R.string.toolbar_question_title));
                break;
            case R.id.rdoBtn_project_session:
                controlShowFragment(FRAGMENT_TAG_GROUP_CHAT);
                initToolbar(toolbar, toolbarTitle, true, false, getString(R.string.toolbar_groupChat_title));
                break;
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.personal_all_project:
                startActivity(new Intent(this, ProjectListActivity.class));
                break;
            case R.id.personal_more:
                startActivity(new Intent(this, MoreActivity.class));
                break;
            default:
                break;
        }
        menuItem.setChecked(true);
        mDrawerLayout.closeDrawers();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                initNavigationHeadState();
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawers();
            return;
        }
        super.onBackPressed();
    }

    private void controlShowFragment(String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        Fragment oldFragment = fragmentManager.findFragmentByTag(currentFragmentTag);
        if (oldFragment != null) {
            transaction.hide(oldFragment);
        }
        currentFragmentTag = tag;

        Fragment currentFragment = fragmentManager.findFragmentByTag(tag);
        if (currentFragment != null) {
            transaction.show(currentFragment);
        } else {
            transaction.add(R.id.main_content, getFragment(tag), tag);
        }
        transaction.commitAllowingStateLoss();

        if (mDrawerLayout.isShown()) {
            mDrawerLayout.closeDrawers();
        }
    }

    private Fragment getFragment(String tag) {
        Fragment fragment = null;
        switch (tag) {
            case FRAGMENT_TAG_TASK:
                fragment = ProjectListFragment.newInstance();
                break;
            case FRAGMENT_TAG_ISSUE:
                fragment = IssueListFragment.newInstance();
                break;
            case FRAGMENT_TAG_GROUP_CHAT:
                fragment = new MPThreadListFragment();
                Bundle bundle = new Bundle();
                bundle.putBoolean(MPThreadListFragment.ISFILEBASE, false);
                bundle.putString(MPThreadListFragment.MEMBERID, UserInfoUtils.getAcsMemberId(this));
                bundle.putString(MPThreadListFragment.MEMBERTYPE, UserInfoUtils.getMemberType(this));
                bundle.putString(MPThreadListFragment.LIST_TYPE, MPChatConstants.BUNDLE_VALUE_GROUP_CHAT_LIST);
                fragment.setArguments(bundle);
                break;
            default:
                break;
        }
        return fragment;
    }

    private void initNavigationHeadState() {
        if (!TextUtils.isEmpty(UserInfoUtils.getNikeName(this))) {
            mUserNameView.setText(UserInfoUtils.getNikeName(this));
        }

        String memberType = UserInfoUtils.getMemberType(this);
        if (!TextUtils.isEmpty(memberType)) {
            switch (memberType) {
                case "designer":
                    mUserRoleView.setText(getString(R.string.designer));
                    break;
                case "member":
                    mUserRoleView.setText(getString(R.string.member));
                    break;
                case "clientmanager":
                    mUserRoleView.setText(getString(R.string.clientmanager));
                    break;
                case "materialstaff":
                    mUserRoleView.setText(getString(R.string.materialstaff));
                    break;
                case "foreman":
                    mUserRoleView.setText(getString(R.string.foreman));
                    break;
                case "inspector":
                    mUserRoleView.setText(getString(R.string.inspector));
                    break;
                default:
                    break;
            }
        }
    }

    private void initToolbar(Toolbar toolbar, @Nullable TextView toolbarTitle, boolean homeAsUpEnabled, boolean isSelfDefineTile, String title) {
        if (isSelfDefineTile) {
            toolbar.setTitle("");
            if (toolbarTitle != null) {
                toolbarTitle.setVisibility(View.VISIBLE);
                toolbarTitle.setText(title);
            }
        } else {
            toolbar.setTitle(title);
            toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white));
            if (toolbarTitle != null) {
                toolbarTitle.setVisibility(View.GONE);
            }
        }
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(homeAsUpEnabled);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case SYS_INTENT_REQUEST:
                if (data != null) {
                    Uri originalUri = data.getData();
                    cropImageUri(originalUri, 300, 300, CROP_SMALL_PICTURE);
                }
                break;
            case CROP_SMALL_PICTURE:
                if (mUritempFile != null) {
                    String path = mUritempFile.getPath();
                    ImageUtils.loadImageRound(mHeadPicBtn,"file:"+ path);
                }

                break;
            case CAMERA_INTENT_REQUEST://相机
                cropImageUri(mUritempFile, 300, 300, CROP_SMALL_PICTURE);
                break;
            default:
                break;
        }
    }
    @Override
    public void updatePersonalHeadPictureView(String avatar) {
        ImageUtils.loadImageRound(mHeadPicBtn,avatar);
    }

    @Override
    public void showNetError(ResponseError error) {}

    @Override
    public void showError(String errorMsg) {}

    @Override
    public void showLoading() {}

    @Override
    public void hideLoading() {}

    private void ShowBottomPopup() {
        View contentView = LayoutInflater.from(this).inflate(R.layout.popup_personal_headpic_dialog, null);
        TextView tvSletectAlbum = (TextView) contentView.findViewById(R.id.tv_sletect_album);
        TextView tvPhotograph = (TextView) contentView.findViewById(R.id.tv_photograph);
        TextView tvCancel = (TextView) contentView.findViewById(R.id.tv_cancel);
        tvSletectAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                systemPhoto();

            }
        });
        tvPhotograph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cameraPhoto();
            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBottomPopUp.dismiss();
            }
        });

        contentView.setFocusable(true);
        contentView.setFocusableInTouchMode(true);
        mBottomPopUp = new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mBottomPopUp.setFocusable(true);
        mBottomPopUp.setOutsideTouchable(false);
        contentView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    mBottomPopUp.dismiss();
                    return true;
                }
                return false;
            }
        });
        mBottomPopUp.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                BackGroundUtils.dimWindowBackground(EnterpriseHomeActivity.this, 0.7f, 1.0f);
            }
        });
        View view = (this.findViewById(android.R.id.content).getRootView());
        mBottomPopUp.setAnimationStyle(R.style.pop_bottom_animation);
        BackGroundUtils.dimWindowBackground(this, 1.0f, 0.7f);
        mBottomPopUp.showAtLocation(view, Gravity.BOTTOM, 0, 0);
    }

    private void systemPhoto() {
        Random random = new Random();
        int ss = Math.abs(random.nextInt(100));
        mUritempFile = Uri.parse("file://" + "/" + Environment.getExternalStorageDirectory().getPath() + "/" +ss+ "small.png");
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");
        intent.putExtra("return-data", true);
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, SYS_INTENT_REQUEST);
    }
    private void cameraPhoto() {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) {
            return;
        }
        mUritempFile = Uri.parse("file://" + "/" + Environment.getExternalStorageDirectory().getPath() + "/" + "small.jpg");
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mUritempFile);
        startActivityForResult(intent, CAMERA_INTENT_REQUEST);
    }

    /**
     * 图片截取
     *
     * @param uri
     * @param outputX
     * @param outputY
     * @param requestCode
     */
    private void cropImageUri(Uri uri, int outputX, int outputY, int requestCode) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mUritempFile);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection
        startActivityForResult(intent, requestCode);
    }
}

