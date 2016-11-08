package com.autodesk.shejijia.shared.components.nodeprocess.presenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.autodesk.shejijia.shared.components.common.appglobal.ConstructionConstants;
import com.autodesk.shejijia.shared.components.common.entity.ProjectInfo;
import com.autodesk.shejijia.shared.components.common.entity.ProjectList;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Like;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Task;
import com.autodesk.shejijia.shared.components.common.listener.ResponseCallback;
import com.autodesk.shejijia.shared.components.common.utility.LogUtils;
import com.autodesk.shejijia.shared.components.nodeprocess.contract.ProjectListContract;
import com.autodesk.shejijia.shared.components.nodeprocess.data.ProjectRepository;
import com.autodesk.shejijia.shared.components.nodeprocess.ui.activity.ProjectDetailsActivity;
import com.autodesk.shejijia.shared.components.nodeprocess.ui.activity.TaskDetailsActivity;

import org.json.JSONObject;

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
    public void initFilterRequestParams(@Nullable String date, @Nullable String filterLike, @Nullable String filterStatus) {
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
    public String getScreenPopupState() {
        return mFilterLike;
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

        mProjectRepository.getProjectList(requestParams, ConstructionConstants.REQUEST_TAG_LOAD_PROJECTS, new ResponseCallback<ProjectList>() {
            @Override
            public void onSuccess(ProjectList taskList) {
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
            public void onError(String errorMsg) {
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
        Intent intent = new Intent(mContext, TaskDetailsActivity.class);
        intent.putExtra("taskId", taskIdLists.get(position).getTaskId());
        mContext.startActivity(intent);
    }

    @Override
    public void updateProjectLikesState(List<ProjectInfo> projectList, final boolean like, int position) {
        //init requestParams
        Bundle requestParamsBundle = new Bundle();
        requestParamsBundle.putLong("pid", projectList.get(position).getProjectId());
        LogUtils.e("projectId", projectList.get(position).getProjectId() + "");
        JSONObject requestJson = new JSONObject();
        try {
            requestJson.put("like", like);
        } catch (Exception e) {
            e.printStackTrace();
        }

        updateProjectLikes(requestParamsBundle, requestJson);
    }

    private void updateProjectLikes(Bundle requestParamsBundle, JSONObject requestJson) {
        mProjectRepository.updateProjectLikes(requestParamsBundle, ConstructionConstants.REQUEST_TAG_STAR_PROJECTS, requestJson, new ResponseCallback<Like>() {
            @Override
            public void onSuccess(Like data) {
                mProjectListView.hideLoading();
                LogUtils.e("like", data.getLike() + "---" + data.getUid());
                // TODO: 11/4/16 更新ui

            }

            @Override
            public void onError(String errorMsg) {
                mProjectListView.hideLoading();
                LogUtils.e("like-error", errorMsg);
                // TODO: 11/4/16 用ui提示错误
            }
        });
    }

}
