package com.autodesk.shejijia.enterprise.nodeprocess.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.enterprise.R;
import com.autodesk.shejijia.enterprise.base.activitys.BaseEnterpriseActivity;
import com.autodesk.shejijia.enterprise.common.network.EnterpriseServerHttpManager;
import com.autodesk.shejijia.enterprise.common.utils.Constants;
import com.autodesk.shejijia.enterprise.common.entity.ProjectBean;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.LogUtils;
import com.autodesk.shejijia.shared.components.common.utility.SharedPreferencesUtils;

import org.json.JSONObject;

/**
 * Created by t_xuz on 8/24/16.
 *
 */
public class ProjectDetailsActivity extends BaseEnterpriseActivity implements View.OnClickListener{

    //bottom bar
    private TextView mPatrolBtn;
    private TextView mIssueBtn;
    private TextView mSessionBtn;
    //top bar
    private ImageButton mBackBtn;
    private TextView mProjectName;
    //content
    private RecyclerView mProjectTaskListView;
    private MemberEntity entity;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_project_details_main;
    }

    @Override
    protected void initView() {
        mProjectTaskListView = (RecyclerView)findViewById(R.id.rcy_project_task);
        //init recyclerView
        mProjectTaskListView.setLayoutManager(new LinearLayoutManager(this));
        mProjectTaskListView.setHasFixedSize(true);
        mProjectTaskListView.setItemAnimator(new DefaultItemAnimator());
        //top bar
        mBackBtn = (ImageButton)this.findViewById(R.id.imgBtn_back);
        mProjectName = (TextView)this.findViewById(R.id.tv_project_name);
        //bottom bar
        mPatrolBtn = (TextView)this.findViewById(R.id.tv_project_patrol);
        mIssueBtn = (TextView)this.findViewById(R.id.tv_project_issue);
        mSessionBtn = (TextView)this.findViewById(R.id.tv_project_session);
    }

    @Override
    protected void initListener() {
        mBackBtn.setOnClickListener(this);
        mPatrolBtn.setOnClickListener(this);
        mIssueBtn.setOnClickListener(this);
        mSessionBtn.setOnClickListener(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        entity = (MemberEntity) SharedPreferencesUtils.getObject(this, Constants.USER_INFO);
        if (entity != null && !TextUtils.isEmpty(entity.getHs_accesstoken())) {
            //get ProjectTaskLists
            LogUtils.e("projectId",getIntent().getLongExtra("projectId",0)+"");
            if (getIntent().getLongExtra("projectId",0) != 0) {
                getProjectDetails(getIntent().getLongExtra("projectId",0), "587e1e6bd9c26875535868dec8e3045c");
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_project_patrol:

                break;
            case R.id.tv_project_issue:

                break;
            case R.id.tv_project_session:

                break;
            case R.id.imgBtn_back:
                finish();
                break;
        }
    }

    private void getProjectDetails(final long pid, final String token){

        OkJsonRequest.OKResponseCallback callback = new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }

            @Override
            public void onResponse(JSONObject jsonObject) {
                LogUtils.e(jsonObject.toString());

                String result = jsonObject.toString();
                ProjectBean projectBean = GsonUtil.jsonToBean(result, ProjectBean.class);

                //update ui view
                updateUI(projectBean);
            }
        };
        EnterpriseServerHttpManager.getInstance().getProjectDetails(pid,token,true,callback);
    }

    private void updateUI(ProjectBean projectBean){
        if (!TextUtils.isEmpty(projectBean.getName())) {
            mProjectName.setText(projectBean.getName());
        }
    }
}
