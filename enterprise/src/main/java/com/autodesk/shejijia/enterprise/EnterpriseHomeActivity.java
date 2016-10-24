package com.autodesk.shejijia.enterprise;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.autodesk.shejijia.enterprise.base.activitys.BaseEnterpriseHomeActivity;
import com.autodesk.shejijia.enterprise.common.utils.Constants;
import com.autodesk.shejijia.enterprise.personalcenter.activity.PersonalCenterActivity;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.common.tools.login.RegisterOrLoginActivity;
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
        mMemberEntity = (MemberEntity) SharedPreferencesUtils.getObject(this, Constants.USER_INFO);
    }

    @Override
    protected void initView() {
        //bottomBar
        mTaskBtn = (RadioButton) this.findViewById(R.id.rdoBtn_project_task);
        mIssueBtn = (RadioButton) this.findViewById(R.id.rdoBtn_project_issue);
        mSessionBtn = (RadioButton) this.findViewById(R.id.rdoBtn_project_session);
        mBottomGroup = (RadioGroup) this.findViewById(R.id.rdoGrp_project_list);
        //toolBar
        toolbar = (Toolbar)this.findViewById(R.id.toolbar_topBar);
        setSupportActionBar(toolbar);
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

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkId) {

        switch (checkId) {
            case R.id.rdoBtn_project_task:
                changeFragment(Constants.TASK_LIST_FRAGMENT, 0);
                //init toolbar
                // 显示导航按钮
                toolbar.setNavigationIcon(R.mipmap.default_head);
                // 显示标题
                toolbar.setTitle(R.string.toolbar_title);
                toolbar.setTitleTextColor(getResources().getColor(R.color.white));
                break;
            case R.id.rdoBtn_project_issue:
                changeFragment(Constants.ISSUE_LIST_FRAGMENT, 1);
                break;
            case R.id.rdoBtn_project_session:
                changeFragment(Constants.GROUP_CHAT_FRAGMENT, 2);
                break;
        }
        //会自动执行onPrepareOptionsMenu，用来管理不同fragment下的menu。
        invalidateOptionsMenu();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_activity_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = null;
        switch (item.getItemId()) {
            case android.R.id.home:
                if (mMemberEntity != null) {
                    intent = new Intent(this, PersonalCenterActivity.class);
                    startActivity(intent);
                }else {
                    intent = new Intent(this, RegisterOrLoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.home_toolbar_search:

                break;
            case R.id.home_toolbar_screen:

                break;
            default:
               break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }


}

