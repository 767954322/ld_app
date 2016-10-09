package com.autodesk.shejijia.enterprise.nodeprocess.projectlists.activitys;


import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.enterprise.R;
import com.autodesk.shejijia.enterprise.common.utils.Constants;
import com.autodesk.shejijia.enterprise.base.network.EnterpriseServerHttpManager;
import com.autodesk.shejijia.enterprise.nodeprocess.nodedetails.entity.NodeBean;
import com.autodesk.shejijia.enterprise.personalcenter.activitys.PersonalCenterActivity;
import com.autodesk.shejijia.enterprise.nodeprocess.projectlists.entity.ProjectListBean;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.LogUtils;
import com.pgyersdk.update.PgyUpdateManager;

import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import org.json.JSONObject;

public class ProjectListsActivity extends BaseProjectListActivity implements OnCheckedChangeListener, View.OnClickListener {

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

    @Override
    protected int getContentViewId() {
        return R.layout.activity_enterprise_main;
    }

    @Override
    protected int getFragmentContentId() {
        return R.id.main_content;
    }

    @Override
    protected void initViews() {
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
    protected void initEvents() {
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
                intent = new Intent();
                intent.setClass(this, PersonalCenterActivity.class);
                startActivity(intent);
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

    private void getProjectLists(final long uid,
                                 final int project_status,
                                 final int limit,
                                 final int offset,
                                 final String token) {
        OkJsonRequest.OKResponseCallback callbackResult = new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }

            @Override
            public void onResponse(JSONObject jsonObject) {
                LogUtils.e("projects-->", jsonObject.toString());
                String result = GsonUtil.jsonToString(jsonObject);
                ProjectListBean projectListBean = GsonUtil.jsonToBean(result, ProjectListBean.class);
                //获取项目列表

            }
        };
        EnterpriseServerHttpManager.getInstance().getProjectLists(offset, limit, token, callbackResult);
    }

    private void getPlanDetails(final long pid,
                                final String token) {
        OkJsonRequest.OKResponseCallback callback = new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }

            @Override
            public void onResponse(JSONObject jsonObject) {

            }
        };

        EnterpriseServerHttpManager.getInstance().getPlanDetails(pid, token, callback);
    }


    private void getTaskDetails(final long pid,
                                final String tid,
                                final String token) {

        OkJsonRequest.OKResponseCallback callback = new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }

            @Override
            public void onResponse(JSONObject jsonObject) {

                String result = GsonUtil.jsonToString(jsonObject);
                NodeBean nodeBean = GsonUtil.jsonToBean(result, NodeBean.class);

                LogUtils.e("TaskDetails", nodeBean.toString());

            }
        };
        EnterpriseServerHttpManager.getInstance().getTaskDetails(pid, tid, token, callback);
    }

}

