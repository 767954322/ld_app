package com.autodesk.shejijia.enterprise;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.CardView;
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
import com.autodesk.shejijia.enterprise.personalcenter.fragment.MoreFragment;
import com.autodesk.shejijia.enterprise.personalcenter.fragment.ProjectListFragment;
import com.autodesk.shejijia.shared.components.common.utility.ImageUtils;
import com.autodesk.shejijia.shared.components.common.utility.ToastUtils;
import com.autodesk.shejijia.shared.components.common.utility.UserInfoUtils;
import com.autodesk.shejijia.shared.components.nodeprocess.ui.fragment.GroupChatFragment;
import com.autodesk.shejijia.shared.components.nodeprocess.ui.fragment.IssueListFragment;
import com.autodesk.shejijia.shared.components.nodeprocess.ui.fragment.TaskListFragment;
import com.autodesk.shejijia.shared.framework.activity.NavigationConstructionActivity;

public class EnterpriseHomeActivity extends NavigationConstructionActivity implements View.OnClickListener, OnCheckedChangeListener,
        NavigationView.OnNavigationItemSelectedListener {

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
    private int currentPosition;//左抽屉当前点击的位置

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

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, 0, 0);
        toggle.syncState();
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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgBtn_personal_headPic:

                break;
            case R.id.tv_toolbar_title:
                // TODO: 10/25/16  get date from calendar and set data to taskListFragment
                ToastUtils.showShort(EnterpriseHomeActivity.this, "title");
                TaskListFragment taskListFragment = (TaskListFragment) getSupportFragmentManager().findFragmentByTag(makeTag(2));
                if (taskListFragment != null) {
                    taskListFragment.refreshProjectListByDate("2016-10-25");
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
                controlShowFragment(0);
                initToolbar(toolbar, toolbarTitle, true, true, getString(R.string.toolbar_task_title));
                break;
            case R.id.rdoBtn_project_issue:
                controlShowFragment(1);
                initToolbar(toolbar, toolbarTitle, true, false, getString(R.string.toolbar_question_title));
                break;
            case R.id.rdoBtn_project_session:
                controlShowFragment(2);
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
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawers();
            return;
        }
        super.onBackPressed();
    }

    private void controlShowFragment(int position) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        Fragment oldFragment = fragmentManager.findFragmentByTag(makeTag(currentPosition));
        if (oldFragment != null) {
            transaction.hide(oldFragment);
        }
        currentPosition = position;

        Fragment currentFragment = fragmentManager.findFragmentByTag(makeTag(position));
        if (currentFragment != null) {
            transaction.show(currentFragment);
        } else {
            transaction.add(R.id.main_content, getFragment(position), makeTag(position));
        }
        transaction.commitAllowingStateLoss();

        if (mDrawerLayout.isShown()) {
            mDrawerLayout.closeDrawers();
        }
    }

    private String makeTag(int position) {
        return R.id.main_content + position + "";
    }

    private Fragment getFragment(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = TaskListFragment.newInstance();
                break;
            case 1:
                fragment = IssueListFragment.newInstance();
                break;
            case 2:
                fragment = GroupChatFragment.newInstance();
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
            if (memberType.equals("designer")) {
                mUserRoleView.setText(getString(R.string.designer));
            } else if (memberType.equals("member")) {
                mUserRoleView.setText(getString(R.string.member));
            } else if (memberType.equals("clientmanager")) {
                mUserRoleView.setText(getString(R.string.clientmanager));
            } else if (memberType.equals("materialstaff")) {
                mUserRoleView.setText(getString(R.string.materialstaff));
            } else if (memberType.equals("foreman")) {
                mUserRoleView.setText(getString(R.string.foreman));
            } else if (memberType.equals("inspector")) {
                mUserRoleView.setText(getString(R.string.inspector));
            }
        }
    }

}

