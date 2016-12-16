package com.autodesk.shejijia.shared.components.nodeprocess.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Task;
import com.autodesk.shejijia.shared.components.common.utility.LogUtils;
import com.autodesk.shejijia.shared.components.nodeprocess.contract.ProjectDetailsTasksContract;
import com.autodesk.shejijia.shared.components.nodeprocess.presenter.ProjectDetailsTasksPresenter;
import com.autodesk.shejijia.shared.components.nodeprocess.ui.adapter.ProjectDetailsTasksAdapter;
import com.autodesk.shejijia.shared.framework.fragment.BaseConstructionFragment;

import java.util.List;

/**
 * Created by t_xuz on 11/14/16.
 * 项目详情页面下的task列表
 */

public class ProjectDetailTasksFragment extends BaseConstructionFragment implements ProjectDetailsTasksContract.View, ProjectDetailsTasksAdapter.TaskListItemClickListener {

    private RecyclerView mTaskListView;
    private ProjectDetailsTasksContract.Presenter mPDTaskListPresenter;
    private ProjectDetailsTasksAdapter mTaskListAdapter;

    public static ProjectDetailTasksFragment newInstance(Bundle taskBundle) {
        ProjectDetailTasksFragment pdTaskListFragment = new ProjectDetailTasksFragment();
        pdTaskListFragment.setArguments(taskBundle);
        return pdTaskListFragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_task_list_view;
    }

    @Override
    protected void initView() {
        mTaskListView = (RecyclerView) rootView.findViewById(R.id.rcy_task_list);
        //init recyclerView
        mTaskListView.setLayoutManager(new LinearLayoutManager(mContext));
        mTaskListView.setHasFixedSize(true);
        mTaskListView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    protected void initData() {
        mPDTaskListPresenter = new ProjectDetailsTasksPresenter(this);
        mPDTaskListPresenter.handleTaskList(getArguments());
    }

    @Override
    public void refreshTaskListView(List<Task> taskList, String avatarUrl) {
        mTaskListAdapter = new ProjectDetailsTasksAdapter(taskList, avatarUrl, R.layout.listitem_projectdetails_task_list_view, mContext, this);
        mTaskListView.setAdapter(mTaskListAdapter);
    }

    @Override
    public void onTaskClick(List<Task> taskList, int position) {
        mPDTaskListPresenter.navigateToTaskDetail(getChildFragmentManager(), taskList, position);
    }
}
