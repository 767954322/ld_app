package com.autodesk.shejijia.enterprise.nodeprocess.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.autodesk.shejijia.enterprise.common.utils.Constants;
import com.autodesk.shejijia.enterprise.common.utils.ToastUtils;
import com.autodesk.shejijia.enterprise.nodeprocess.contract.ProjectListContract;
import com.autodesk.shejijia.enterprise.nodeprocess.data.NodeProcessRepository;
import com.autodesk.shejijia.enterprise.nodeprocess.data.entity.TaskListBean;
import com.autodesk.shejijia.enterprise.nodeprocess.data.source.NodeProcessDataSource;
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
    private NodeProcessRepository mNodeProcessRepository;

    public ProjectListPresenter(Context context, ProjectListContract.View projectListsView){
        this.mContext = context;
        this.mProjectListView = projectListsView;
        mNodeProcessRepository = NodeProcessRepository.getInstance();
    }

    @Override
    public void loadProjectListData(String requestUrl,final String eventTag,String requestTag, boolean isSwipeRefresh) {

        mNodeProcessRepository.getProjectList(requestUrl, eventTag, requestTag, new NodeProcessDataSource.LoadProjectListCallback() {
            @Override
            public void onProjectListLoadSuccess(TaskListBean taskList) {
                mProjectListView.hideLoading();
                if (eventTag.equalsIgnoreCase(Constants.REFRESH_EVENT)){
                    mProjectListView.refreshProjectListData(taskList);
                }else if (eventTag.equalsIgnoreCase(Constants.LOAD_MORE_EVENT)){
                    mProjectListView.addMoreProjectListData(taskList);
                }
            }

            @Override
            public void onProjectListLoadFailed(String errorMsg) {
                mProjectListView.hideLoading();
                mProjectListView.showNetError(errorMsg);
            }
        });
    }

    @Override
    public void onProjectClickListener(List<TaskListBean.TaskList> projectList, int position) {
        long projectId = projectList.get(position).getProject_id();
        Intent intent = new Intent(mContext, ProjectDetailsActivity.class);
        intent.putExtra("projectId",projectId);
        mContext.startActivity(intent);
    }

    @Override
    public void onTaskClickListener(List<TaskListBean.TaskList.Plan.Task> taskIdLists, int position) {
        ToastUtils.showShort((Activity)mContext,"node-details22"+position);
        Intent intent = new Intent(mContext, NodeDetailsActivity.class);
        intent.putExtra("taskId",taskIdLists.get(position).getTask_id());
        mContext.startActivity(intent);
    }
}
