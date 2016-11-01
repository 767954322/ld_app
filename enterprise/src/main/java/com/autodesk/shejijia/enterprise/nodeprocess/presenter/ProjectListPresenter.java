package com.autodesk.shejijia.enterprise.nodeprocess.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.autodesk.shejijia.enterprise.common.utils.ToastUtils;
import com.autodesk.shejijia.enterprise.nodeprocess.contract.ProjectListContract;
import com.autodesk.shejijia.enterprise.nodeprocess.data.ProjectRepository;
import com.autodesk.shejijia.enterprise.nodeprocess.ui.activity.ProjectDetailsActivity;
import com.autodesk.shejijia.enterprise.nodeprocess.ui.activity.TaskDetailsActivity;
import com.autodesk.shejijia.shared.components.common.entity.ProjectInfo;
import com.autodesk.shejijia.shared.components.common.entity.ProjectList;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Task;
import com.autodesk.shejijia.shared.components.common.listener.LoadDataCallback;
import com.autodesk.shejijia.shared.components.common.appglobal.ConstructionConstants;

import java.util.List;

/**
 * Created by t_xuz on 10/11/16.
 * 主页 项目(任务)列表页对应的presenter的实现类-->对应 TaskListFragment
 */
public class ProjectListPresenter implements ProjectListContract.Presenter {

    private static final int PAGE_LIMIT = 30;
    private Context mContext;
    private ProjectListContract.View mProjectListView;
    private ProjectRepository mProjectRepository;
    private int mOffset = 0;
    private String mSelectedDate;
    private String mFilterLike; //null or true or false
    private String mFilterStatus;
    private List<ProjectInfo> mProjectList;

    public ProjectListPresenter(Context context, ProjectListContract.View projectListsView) {
        this.mContext = context;
        this.mProjectListView = projectListsView;
        mProjectRepository = ProjectRepository.getInstance();
    }

    @Override
    public void initRequestParams(@Nullable String date, @Nullable String filterLike, @Nullable String filterStatus) {
        this.mSelectedDate = date;
        this.mFilterLike = filterLike;
        this.mFilterStatus = filterStatus;
    }

    @Override
    public void onFilterDateChange(String newDate) {
        this.mSelectedDate = newDate;
        refreshProjectList();
    }

    @Override
    public void onFilterStatusChange(String newStatus) {
        this.mFilterStatus = newStatus;
        refreshProjectList();
    }

    @Override
    public void onFilterLikeChange(String newLike) {
        this.mFilterLike = newLike;
        refreshProjectList();
    }

    @Override
    public void refreshProjectList() {
        mProjectListView.showLoading();
        mOffset = 0;
        loadProjectList(mOffset);
    }

    @Override
    public void loadMoreProjectList() {
        mOffset++;
        loadProjectList(mOffset);
    }

    private void loadProjectList(int offset) {
        Bundle requestParamsBundle = new Bundle();
        if (mSelectedDate != null) {
            requestParamsBundle.putString("findDate", mSelectedDate);
        }
        if (mFilterStatus != null) {
            requestParamsBundle.putString("status", mFilterStatus);
        }
        if (mFilterLike != null) {
            requestParamsBundle.putString("like", mFilterLike);
        }
        requestParamsBundle.putInt("limit", PAGE_LIMIT);
        requestParamsBundle.putInt("offset", offset);
        loadProjectListData(requestParamsBundle);
    }

    private void loadProjectListData(Bundle requestParams) {

        mProjectRepository.getProjectList(requestParams, ConstructionConstants.REQUEST_TAG_LOAD_PROJECTS, new LoadDataCallback<ProjectList>() {
            @Override
            public void onLoadSuccess(ProjectList taskList) {
                mProjectListView.hideLoading();
                if (taskList.getOffset() == 0) {
                    mProjectList = taskList.getData();
                    mProjectListView.refreshProjectListView(taskList.getData());
                } else {
                    mOffset = taskList.getOffset();
                    mProjectList.addAll(taskList.getData());
                    mProjectListView.addMoreProjectListView(mProjectList);
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
    public void navigateToProjectDetail(List<ProjectInfo> projectList, int position) {
        long projectId = projectList.get(position).getProjectId();
        String projectName = projectList.get(position).getName();
        Intent intent = new Intent(mContext, ProjectDetailsActivity.class);
        intent.putExtra("projectId", projectId);
        intent.putExtra("projectName", projectName);
        mContext.startActivity(intent);
    }

    @Override
    public void navigateToTaskDetail(List<Task> taskIdLists, int position) {
        ToastUtils.showShort((Activity) mContext, "node-details22" + position);
        Intent intent = new Intent(mContext, TaskDetailsActivity.class);
        intent.putExtra("taskId", taskIdLists.get(position).getTaskId());
        mContext.startActivity(intent);
    }
}
