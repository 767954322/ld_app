package com.autodesk.shejijia.enterprise;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.autodesk.shejijia.enterprise.common.utils.Constants;
import com.autodesk.shejijia.enterprise.nodeprocess.ui.activity.BaseEnterpriseHomeActivity;
import com.autodesk.shejijia.enterprise.personalcenter.activity.PersonalCenterActivity;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.common.tools.login.RegisterOrLoginActivity;
import com.autodesk.shejijia.shared.components.common.utility.SharedPreferencesUtils;
import com.pgyersdk.update.PgyUpdateManager;

public class EnterpriseHomeActivity extends BaseEnterpriseHomeActivity implements OnCheckedChangeListener, View.OnClickListener {

    //RadioButton
    private RadioButton mTaskBtn;
    private RadioButton mIssueBtn;
    private RadioButton mSessionBtn;
    //RadioGroup
    private RadioGroup mProjectGroup;
    //topBar
    private TextView mProjectDate;
    private ImageButton mPersonalCenter;
    private ImageButton mSearchBtn; //搜索
    private ImageButton mScreenBtn; //筛选
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
        mProjectGroup = (RadioGroup) this.findViewById(R.id.rdoGrp_project_list);
        //topBar
        mProjectDate = (TextView) this.findViewById(R.id.tv_project_date);
        mScreenBtn = (ImageButton) this.findViewById(R.id.imgBtn_screen);
        mSearchBtn = (ImageButton) this.findViewById(R.id.imgBtn_search);
        mPersonalCenter = (ImageButton) this.findViewById(R.id.imgBtn_personal_center);
    }

    @Override
    protected void initListener() {
        //init RadioBtn Event
        mProjectGroup.setOnCheckedChangeListener(this);
        mTaskBtn.setChecked(true);
        //init TopBar Event
        mPersonalCenter.setOnClickListener(this);
        mScreenBtn.setOnClickListener(this);
        mSearchBtn.setOnClickListener(this);
        mProjectDate.setOnClickListener(this);

        //version update
        PgyUpdateManager.register(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkId) {

        switch (checkId) {
            case R.id.rdoBtn_project_task:
                changeFragment(Constants.TASK_LIST_FRAGMENT, 0);
                //init topBar status
                initViewsData();
                break;
            case R.id.rdoBtn_project_issue:
                changeFragment(Constants.ISSUE_LIST_FRAGMENT, 1);
                break;
            case R.id.rdoBtn_project_session:
                changeFragment(Constants.GROUP_CHAT_FRAGMENT, 2);
                break;
        }
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()){
            case R.id.imgBtn_personal_center:
                if (mMemberEntity != null) {
                    intent = new Intent(this, PersonalCenterActivity.class);
                    startActivity(intent);
                }else {
                    intent = new Intent(this, RegisterOrLoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.imgBtn_screen:

                break;
            case R.id.imgBtn_search:

                break;
            case R.id.tv_project_date:

                break;
        }
    }

    private void initViewsData(){
        mProjectDate.setText("8月8日");
    }


}

