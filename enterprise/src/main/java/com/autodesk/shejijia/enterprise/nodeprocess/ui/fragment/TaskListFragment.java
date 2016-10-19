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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by t_xuz on 8/25/16.
 * 首页-项目列表
 */
public class TaskListFragment extends BaseFragment implements ProjectListContract.View, ProjectListAdapter.ProjectListItemListener {

    private RecyclerView mProjectListView;
    private ProjectListAdapter mProjectListAdapter;
    private ProjectListContract.Presenter mProjectListPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_task_list_view;
    }

    @Override
    protected void initData() {

        mProjectListPresenter = new ProjectListPresenter(getActivity(), this);
        //get ProjectLists
        String requestUrl = UrlHelper.getInstance().getUserProjectListUrl(Constants.PROJECT_LIST_BY_DATE, "2016-08-08", null, false, 0);
        mProjectListPresenter.loadProjectListData(requestUrl, Constants.REFRESH_EVENT, "project_list", false);

    }

    @Override
    protected void initViews(View view, Bundle savedInstanceState) {
        mProjectListView = (RecyclerView) mContext.findViewById(R.id.rcy_task_list);
        //init recyclerView
        mProjectListView.setLayoutManager(new LinearLayoutManager(mContext));
        mProjectListView.setHasFixedSize(true);
        mProjectListView.setItemAnimator(new DefaultItemAnimator());
        //init recyclerView adapter
        mProjectListAdapter = new ProjectListAdapter(new ArrayList<ProjectBean>(0), R.layout.listitem_task_list_view, mContext, this);
        mProjectListView.setAdapter(mProjectListAdapter);
    }

    @Override
    protected void initEvents() {
    }

    /*
    * 当网络请求返回结果成功,presenter回掉view层的该方法,进行结果集的传递
    * */
    @Override
    public void refreshProjectListData(List<ProjectBean> projectList) {
        if (projectList != null && projectList.size() > 0) {
            mProjectListAdapter.setProjectLists(projectList);
        }
    }

    @Override
    public void addMoreProjectListData(List<ProjectBean> projectList) {

    }

    @Override
    public void onProjectClick(List<ProjectBean> projectList, int position) {
        mProjectListPresenter.navigateToProjectDetail(projectList, position);
    }

    @Override
    public void onTaskClick(List<Task> taskLists, int position) {
        mProjectListPresenter.navigateToTaskDetail(taskLists, position);
    }
}
