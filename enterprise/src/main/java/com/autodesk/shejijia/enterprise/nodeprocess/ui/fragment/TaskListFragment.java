package com.autodesk.shejijia.enterprise.nodeprocess.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.autodesk.shejijia.enterprise.R;
import com.autodesk.shejijia.enterprise.base.fragments.BaseFragment;
import com.autodesk.shejijia.enterprise.common.entity.ProjectBean;
import com.autodesk.shejijia.enterprise.common.entity.ProjectListBean;
import com.autodesk.shejijia.enterprise.common.entity.microbean.Task;
import com.autodesk.shejijia.enterprise.common.utils.Constants;
import com.autodesk.shejijia.enterprise.common.utils.UrlHelper;
import com.autodesk.shejijia.enterprise.nodeprocess.contract.ProjectListContract;
import com.autodesk.shejijia.enterprise.nodeprocess.presenter.ProjectListPresenter;
import com.autodesk.shejijia.enterprise.nodeprocess.ui.adapter.ProjectListAdapter;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.common.utility.LogUtils;
import com.autodesk.shejijia.shared.components.common.utility.SharedPreferencesUtils;

import java.util.List;

/**
 * Created by t_xuz on 8/25/16.
 * 首页-项目列表
 */
public class TaskListFragment extends BaseFragment implements ProjectListContract.View ,ProjectListAdapter.ProjectListItemListener{

    private RecyclerView mTaskListView;
    private List<ProjectBean> projectLists;
    private ProjectListAdapter mProjectListAdapter;
    private MemberEntity entity;
    private ProjectListContract.Presenter mProjectListPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_task_list_view;
    }

    @Override
    protected void initData() {
        entity = (MemberEntity) SharedPreferencesUtils.getObject(mContext, Constants.USER_INFO);
        mProjectListPresenter = new ProjectListPresenter(getActivity(), this);
        LogUtils.e("project--entity", entity + "");
        if (entity != null && !TextUtils.isEmpty(entity.getHs_accesstoken())) {
            LogUtils.e("acs_token", entity.getToken());
            //get ProjectLists
            String requestUrl = UrlHelper.getInstance().getUserProjectListUrl(Constants.PROJECT_LIST_BY_DATE, "2016-08-08", null, false, 0);
            mProjectListPresenter.loadProjectListData(requestUrl, Constants.REFRESH_EVENT, "project_list", false);
        }
    }

    @Override
    protected void initViews(View view, Bundle savedInstanceState) {
        mTaskListView = (RecyclerView) mContext.findViewById(R.id.rcy_task_list);
        //init recyclerView
        mTaskListView.setLayoutManager(new LinearLayoutManager(mContext));
        mTaskListView.setHasFixedSize(true);
        mTaskListView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    protected void initEvents() {
    }

    /*
    * 当网络请求返回结果成功,presenter回掉view层的该方法,进行结果集的传递
    * */
    @Override
    public void refreshProjectListData(ProjectListBean projectListBean) {

        if (projectListBean != null) {
            //获取当前日期(默认就是当前日期)的任务列表
            projectLists = projectListBean.getData();
            if (projectLists != null && projectLists.size() > 0) {
                //显示任务列表到页面上
                mProjectListAdapter = new ProjectListAdapter(projectLists, R.layout.listitem_task_list_view, mContext,this);
                mTaskListView.setAdapter(mProjectListAdapter);
            }
        }
    }

    @Override
    public void addMoreProjectListData(ProjectListBean projectListBean) {

    }

    @Override
    public void onProjectClick(List<ProjectBean> projectList, int position) {
        mProjectListPresenter.navigateToProjectDetail(projectList,position);
    }

    @Override
    public void onTaskClick(List<Task> taskLists, int position) {
        mProjectListPresenter.navigateToTaskDetail(taskLists,position);
    }
}
