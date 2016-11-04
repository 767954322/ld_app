package com.autodesk.shejijia.enterprise;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.autodesk.shejijia.enterprise.base.BaseEnterpriseHomeActivity;
import com.autodesk.shejijia.shared.components.common.utility.ToastUtils;
import com.autodesk.shejijia.shared.components.nodeprocess.ui.fragment.TaskListFragment;
import com.autodesk.shejijia.enterprise.personalcenter.activity.PersonalCenterActivity;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.common.tools.login.RegisterOrLoginActivity;
import com.autodesk.shejijia.shared.components.common.appglobal.ConstructionConstants;
import com.autodesk.shejijia.shared.components.common.utility.SharedPreferencesUtils;

public class EnterpriseHomeActivity extends BaseEnterpriseHomeActivity implements OnCheckedChangeListener {

    //RadioButton
    private RadioButton mTaskBtn;
    private RadioButton mIssueBtn;
    private RadioButton mSessionBtn;
    //RadioGroup
    private RadioGroup mBottomGroup;
    //topBar
    private Toolbar toolbar;
    private TextView toolbarTitle;//self define
    private MemberEntity mMemberEntity;//用户信息

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_enterprise_main;
    }

    @Override
    protected int getFragmentContentId() {
        return R.id.main_content;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        mMemberEntity = (MemberEntity) SharedPreferencesUtils.getObject(this, Constant.UerInfoKey.USER_INFO);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void initView() {
        //bottomBar
        mTaskBtn = (RadioButton) this.findViewById(R.id.rdoBtn_project_task);
        mIssueBtn = (RadioButton) this.findViewById(R.id.rdoBtn_project_issue);
        mSessionBtn = (RadioButton) this.findViewById(R.id.rdoBtn_project_session);
        mBottomGroup = (RadioGroup) this.findViewById(R.id.rdoGrp_project_list);
        //toolBar
        toolbar = (Toolbar) this.findViewById(R.id.toolbar_topBar);
        //self define toolbar title
        toolbarTitle = (TextView) toolbar.findViewById(R.id.tv_toolbar_title);
        setSupportActionBar(toolbar);
        // 显示导航按钮
        toolbar.setNavigationIcon(R.drawable.ic_navigation);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @Override
    protected void initListener() {
        //init RadioBtn Event
        mBottomGroup.setOnCheckedChangeListener(this);
        mTaskBtn.setChecked(true);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkId) {

        switch (checkId) {
            case R.id.rdoBtn_project_task:
                changeFragment(ConstructionConstants.TASK_LIST_FRAGMENT, 0);
                toolbarTitle.setText(R.string.toolbar_task_title);
                // toolbarTitle.setCompoundDrawables(null, null, ContextCompat.getDrawable(this, R.drawable.ic_pull_down), null);
                toolbarTitle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // TODO: 10/25/16  get date from calendar and set data to taskListFragment
                        ToastUtils.showShort(EnterpriseHomeActivity.this, "title");
                        TaskListFragment taskListFragment = (TaskListFragment) getSupportFragmentManager().findFragmentByTag(ConstructionConstants.TASK_LIST_FRAGMENT);
                        if (taskListFragment != null) {
                            taskListFragment.refreshProjectListByDate("2016-10-25");
                        }
                    }
                });
                break;
            case R.id.rdoBtn_project_issue:
                changeFragment(ConstructionConstants.ISSUE_LIST_FRAGMENT, 1);
                toolbarTitle.setText(R.string.toolbar_question_title);
                toolbarTitle.setCompoundDrawables(null, null, null, null);
                toolbarTitle.setOnClickListener(null);
                break;
            case R.id.rdoBtn_project_session:
                changeFragment(ConstructionConstants.GROUP_CHAT_FRAGMENT, 2);
                toolbarTitle.setCompoundDrawables(null, null, null, null);
                toolbarTitle.setText(R.string.toolbar_groupChat_title);
                toolbarTitle.setOnClickListener(null);
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = null;
                if (mMemberEntity != null) {
                    intent = new Intent(this, PersonalCenterActivity.class);
                    startActivity(intent);
                } else {
                    intent = new Intent(this, RegisterOrLoginActivity.class);
                    startActivity(intent);
                }
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}

