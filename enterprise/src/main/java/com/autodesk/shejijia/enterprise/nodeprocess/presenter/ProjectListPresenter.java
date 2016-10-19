package com.autodesk.shejijia.enterprise.nodeprocess.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.autodesk.shejijia.enterprise.common.entity.ProjectBean;
import com.autodesk.shejijia.enterprise.common.entity.ProjectListBean;
import com.autodesk.shejijia.enterprise.common.entity.microbean.Task;
import com.autodesk.shejijia.enterprise.common.utils.Constants;
import com.autodesk.shejijia.enterprise.common.utils.ToastUtils;
import com.autodesk.shejijia.enterprise.nodeprocess.contract.ProjectListContract;
import com.autodesk.shejijia.enterprise.nodeprocess.data.LoadDataCallback;
import com.autodesk.shejijia.enterprise.nodeprocess.data.ProjectRepository;
import com.autodesk.shejijia.enterprise.nodeprocess.data.source.ProjectDataSource;
import com.autodesk.shejijia.enterprise.nodeprocess.ui.activity.NodeDetailsActivity;
import com.autodesk.shejijia.enterprise.nodeprocess.ui.activity.ProjectDetailsActivity;

import java.util.List;

/**
 * Created by t_xuz on 10/11/16.
 * 主页 项目(任务)列表页对应的presenter的实现类-->对应 TaskListFragment
 */
public class ProjectListPresenter implements ProjectListContract.Presenter{

    private Context mContext;
    private ProjectListContract.View mProjectListView;
    private ProjectRepository mNodeProcessRepository;

    public ProjectListPresenter(Context context, ProjectListContract.View projectListsView){
        this.mContext = context;
        this.mProjectListView = projectListsView;
        mNodeProcessRepository = ProjectRepository.getInstance();
    }

    @Override
    public void loadProjectListData(String requestUrl,final String eventTag,String requestTag, boolean isSwipeRefresh) {

        mNodeProcessRepository.getProjectList(requestUrl, eventTag, requestTag, new LoadDataCallback<ProjectListBean>() {
            @Override
            public void onLoadSuccess(ProjectListBean taskList) {
                mProjectListView.hideLoading();
                if (eventTag.equalsIgnoreCase(Constants.REFRESH_EVENT)){
                    mProjectListView.refreshProjectListData(taskList.getData());
                }else if (eventTag.equalsIgnoreCase(Constants.LOAD_MORE_EVENT)){
                    mProjectListView.addMoreProjectListData(taskList.getData());
                }
            }

            @Override
            public void onLoadFailed(String errorMsg) {
                mProjectListView.hideLoading();
                mProjectListView.showNetError(errorMsg);
            }
        });
    }

    @Override
    public void navigateToProjectDetail(List<ProjectBean> projectList, int position) {
        long projectId = projectList.get(position).getProjectId();
        Intent intent = new Intent(mContext, ProjectDetailsActivity.class);
        intent.putExtra("projectId",projectId);
        mContext.startActivity(intent);
    }

    @Override
    public void navigateToTaskDetail(List<Task> taskIdLists, int position) {
        ToastUtils.showShort((Activity)mContext,"node-details22"+position);
        Intent intent = new Intent(mContext, NodeDetailsActivity.class);
        intent.putExtra("taskId",taskIdLists.get(position).getTaskId());
        mContext.startActivity(intent);
    }
}
