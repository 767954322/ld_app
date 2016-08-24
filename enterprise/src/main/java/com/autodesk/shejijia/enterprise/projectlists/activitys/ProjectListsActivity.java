package com.autodesk.shejijia.enterprise.projectlists.activitys;


import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.enterprise.R;
import com.autodesk.shejijia.enterprise.base.activitys.BaseActivity;
import com.autodesk.shejijia.enterprise.base.common.utils.Constants;
import com.autodesk.shejijia.enterprise.base.common.utils.LogUtils;
import com.autodesk.shejijia.enterprise.base.network.EnterpriseServerHttpManager;
import com.autodesk.shejijia.enterprise.base.network.MyOkJsonRequest;
import com.autodesk.shejijia.enterprise.nodedetails.entity.NodeBean;
import com.autodesk.shejijia.enterprise.projectlists.adapter.ProjectListAdapter;
import com.autodesk.shejijia.enterprise.projectlists.entity.ProjectListBean;
import com.autodesk.shejijia.enterprise.projectlists.entity.TaskListBean;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.SharedPreferencesUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ProjectListsActivity extends BaseActivity {

    private List<ProjectListBean.ProjectList> projectList;
    private List<TaskListBean.TaskList> taskLists;
    private MemberEntity entity;
    private RecyclerView mProjectListView;
    private ProjectListAdapter mProjectListAdapter;
    private int limit = 10;
    private int offset;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_enterprise_main;
    }

    @Override
    protected void initData() {

        taskLists = new ArrayList<>();
        entity = (MemberEntity) SharedPreferencesUtils.getObject(this, Constants.USER_INFO);
        LogUtils.e("project--entity", entity + "");
        if (entity != null && !TextUtils.isEmpty(entity.getHs_accesstoken())) {
            LogUtils.e("acs_token", entity.getToken());
            //get ProjectLists
//            getProjectLists(0, 0, 10, 0, "587e1e6bd9c26875535868dec8e3045c");

            //get TaskLists
            getTaskLists("2016-08-08",0,10,0,"587e1e6bd9c26875535868dec8e3045c");

        }

    }

    @Override
    protected void initViews() {

        mProjectListView = (RecyclerView) this.findViewById(R.id.project_ry);
    }

    @Override
    protected void initEvents() {
        //init recyclerView
        mProjectListView.setLayoutManager(new LinearLayoutManager(this));
        mProjectListView.setHasFixedSize(true);
        mProjectListView.setItemAnimator(new DefaultItemAnimator());

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
                projectList = projectListBean.getData();

            }
        };
        EnterpriseServerHttpManager.getInstance().getProjectLists(offset, limit, token, callbackResult);
    }


    private void getTaskLists(final String findDate,
                              final int like,
                              final int limit,
                              final int offset,
                              final String token) {
        MyOkJsonRequest.OKResponseCallback callback = new MyOkJsonRequest.OKResponseCallback() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }

            @Override
            public void onResponse(JSONObject jsonObject) {
                LogUtils.e("taskLists-->", jsonObject.toString());
                String result = jsonObject.toString();
                TaskListBean taskListBean = GsonUtil.jsonToBean(result, TaskListBean.class);
                LogUtils.e("taskListBean-->", taskListBean.toString());

                //获取当前日期(默认就是当前日期)的任务列表
                taskLists = taskListBean.getData();
                //显示任务列表到页面上
                mProjectListAdapter = new ProjectListAdapter(taskLists,R.layout.project_list_item_view,ProjectListsActivity.this);
                mProjectListView.setAdapter(mProjectListAdapter);

            }
        };
        EnterpriseServerHttpManager.getInstance().getTaskLists(findDate, like, limit, offset, token, callback);
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

