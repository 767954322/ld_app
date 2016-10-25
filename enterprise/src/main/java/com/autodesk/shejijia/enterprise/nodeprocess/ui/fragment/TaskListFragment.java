package com.autodesk.shejijia.enterprise.nodeprocess.ui.fragment;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.autodesk.shejijia.enterprise.R;
import com.autodesk.shejijia.enterprise.base.fragments.BaseEnterpriseFragment;
import com.autodesk.shejijia.enterprise.common.utils.ToastUtils;
import com.autodesk.shejijia.enterprise.nodeprocess.contract.ProjectListContract;
import com.autodesk.shejijia.enterprise.nodeprocess.presenter.ProjectListPresenter;
import com.autodesk.shejijia.enterprise.nodeprocess.ui.adapter.ProjectListAdapter;
import com.autodesk.shejijia.shared.components.common.entity.ProjectInfo;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by t_xuz on 8/25/16.
 * 首页-项目列表
 */
public class TaskListFragment extends BaseEnterpriseFragment implements ProjectListContract.View, ProjectListAdapter.ProjectListItemListener {

    private RecyclerView mProjectListView;
    private ProjectListAdapter mProjectListAdapter;
    private ProjectListContract.Presenter mProjectListPresenter;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_task_list_view;
    }


    @Override
    protected void onFirstUserVisible() {

    }

    @Override
    protected void initData() {
        mProjectListPresenter = new ProjectListPresenter(getActivity(), this);
        //refresh ProjectLists
        mProjectListPresenter.initRequestOptions("2016-10-08", null, null);
        mProjectListPresenter.refreshProjectList();
    }

    @Override
    protected void initView() {
        mProjectListView = (RecyclerView) rootView.findViewById(R.id.rcy_task_list);
        //init recyclerView
        mProjectListView.setLayoutManager(new LinearLayoutManager(mContext));
        mProjectListView.setHasFixedSize(true);
        mProjectListView.setItemAnimator(new DefaultItemAnimator());
        //init recyclerView adapter
        mProjectListAdapter = new ProjectListAdapter(new ArrayList<ProjectInfo>(0), R.layout.listitem_task_list_view, activity, this);
        mProjectListView.setAdapter(mProjectListAdapter);
        //set this fragment to hold optionsMenu
        setHasOptionsMenu(true);
    }

    // TODO: 10/25/16 模拟上拉刷新监听
    private void onRefresh() {
        mProjectListPresenter.refreshProjectList();
    }

    // TODO: 10/25/16 模拟下拉加载更多监听
    private void onLoadMore() {
        mProjectListPresenter.loadMoreProjectList();
    }

    /*
    * toolbar 日期变更点击回调方法（EnterpriseHomeActivity调用）
    * */
    public void refreshProjectListByDate(String date) {
        mProjectListPresenter.onFilterDateChange(date);
        mProjectListPresenter.refreshProjectList();
    }

    @Override
    public void refreshProjectListView(List<ProjectInfo> projectList) {
        if (projectList != null && projectList.size() > 0) {
            mProjectListAdapter.setProjectLists(projectList);
        }
    }

    @Override
    public void addMoreProjectListView(List<ProjectInfo> projectList) {
        if (projectList != null && projectList.size() > 0) {
            mProjectListAdapter.appendProjectLists(projectList);
        }
    }

    @Override
    public void onProjectClick(List<ProjectInfo> projectList, int position) {
        mProjectListPresenter.navigateToProjectDetail(projectList, position);
    }

    @Override
    public void onTaskClick(List<Task> taskLists, int position) {
        mProjectListPresenter.navigateToTaskDetail(taskLists, position);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.home_task_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home_toolbar_search:
                ToastUtils.showShort(mContext, "search");
                // TODO: 10/25/16 跳转到搜索页面
                break;
            case R.id.home_toolbar_screen:
                ToastUtils.showShort(mContext, "screen");
                // TODO: 10/25/16 筛选－－ 条件修改
                mProjectListPresenter.onFilterLikeChange(null);
                mProjectListPresenter.refreshProjectList();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
