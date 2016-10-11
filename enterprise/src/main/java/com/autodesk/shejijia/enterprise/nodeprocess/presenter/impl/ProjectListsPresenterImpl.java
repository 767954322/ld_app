package com.autodesk.shejijia.enterprise.nodeprocess.presenter.impl;

import android.content.Context;
import android.view.View;

import com.autodesk.shejijia.enterprise.common.Interface.BaseLoadedListener;
import com.autodesk.shejijia.enterprise.common.utils.Constants;
import com.autodesk.shejijia.enterprise.nodeprocess.entity.TaskListBean;
import com.autodesk.shejijia.enterprise.nodeprocess.interactor.ProjectListInteractor;
import com.autodesk.shejijia.enterprise.nodeprocess.interactor.impl.ProjectListInteratorImpl;
import com.autodesk.shejijia.enterprise.nodeprocess.presenter.ProjectListsPresenter;
import com.autodesk.shejijia.enterprise.nodeprocess.view.ProjectListsView;

/**
 * Created by t_xuz on 10/11/16.
 * 主页 项目(任务)列表页对应的presenter的实现类-->对应 TaskListFragment
 */
public class ProjectListsPresenterImpl implements ProjectListsPresenter,BaseLoadedListener<TaskListBean>{

    private Context mContext;
    private ProjectListsView mProjectListsView;
    private ProjectListInteractor mProjectListInteractor;

    public ProjectListsPresenterImpl(Context context,ProjectListsView projectListsView){
        this.mContext = context;
        this.mProjectListsView = projectListsView;
        mProjectListInteractor = new ProjectListInteratorImpl(this);
    }

    @Override
    public void onSuccess(String eventTag,TaskListBean data) {
        mProjectListsView.hideLoading();
        if (eventTag.equalsIgnoreCase(Constants.REFRESH_EVENT)){
            mProjectListsView.refreshProjectListData(data);
        }else if (eventTag.equalsIgnoreCase(Constants.LOAD_MORE_EVENT)){
            mProjectListsView.addMoreProjectListData(data);
        }
    }

    @Override
    public void onError(String msg) {
        mProjectListsView.hideLoading();
        mProjectListsView.showNetError(msg);
    }

    @Override
    public void loadTaskListData(String findDate,String requestTag, int pageSize, boolean isSwipeRefresh) {
        mProjectListsView.hideLoading();
        mProjectListInteractor.getProjectListData(findDate,requestTag,pageSize);
    }

    @Override
    public void onItemTopClickListener(View view, int position, TaskListBean entity) {
        mProjectListsView.navigateProjectDetails(view,position,entity);
    }

    @Override
    public void onItemChildItemClickListener(View view, int position, TaskListBean entity) {
        mProjectListsView.navigateTaskDetails(view,position,entity);
    }
}
