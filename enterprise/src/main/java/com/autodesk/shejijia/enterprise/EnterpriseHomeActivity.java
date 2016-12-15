package com.autodesk.shejijia.enterprise;


import android.content.Intent;
import android.os.Bundle;
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
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.autodesk.shejijia.enterprise.personalcenter.activity.MoreActivity;
import com.autodesk.shejijia.enterprise.personalcenter.activity.ProjectListActivity;
import com.autodesk.shejijia.shared.components.common.appglobal.ChatConstants;
import com.autodesk.shejijia.shared.components.common.utility.ToastUtils;
import com.autodesk.shejijia.shared.components.common.utility.UserInfoUtils;
import com.autodesk.shejijia.shared.components.im.fragment.MPThreadListFragment;
import com.autodesk.shejijia.shared.components.issue.ui.fragment.IssueListFragment;
import com.autodesk.shejijia.shared.components.nodeprocess.ui.fragment.ProjectListFragment;
import com.autodesk.shejijia.shared.framework.activity.BaseActivity;
import com.pgyersdk.update.PgyUpdateManager;

public class EnterpriseHomeActivity extends BaseActivity implements View.OnClickListener, OnCheckedChangeListener,
        NavigationView.OnNavigationItemSelectedListener {

    private static final String FRAGMENT_TAG_TASK = "taskList";
    private static final String FRAGMENT_TAG_ISSUE = "issueList";
    private static final String FRAGMENT_TAG_GROUP_CHAT = "groupChatList";

    private RadioButton mTaskBtn;
    private RadioButton mIssueBtn;
    private RadioButton mSessionBtn;
    private RadioGroup mBottomGroup;
    private ImageButton mHeadPicBtn;
    private Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private TextView toolbarTitle;//self define
    private TextView mUserNameView;
    private TextView mUserRoleView;
    private String currentFragmentTag;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_enterprise_main;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
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
        mHeadPicBtn = (ImageButton) headerView.findViewById(R.id.imgBtn_personal_headPic);
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
                // TODO: 11/9/16  into userInfo activity
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
                bundle.putString(MPThreadListFragment.LIST_TYPE, ChatConstants.BUNDLE_VALUE_GROUP_CHAT_LIST);
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

}

