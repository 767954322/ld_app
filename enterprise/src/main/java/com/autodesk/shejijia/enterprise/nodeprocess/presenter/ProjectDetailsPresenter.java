package com.autodesk.shejijia.enterprise.nodeprocess.presenter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.autodesk.shejijia.enterprise.nodeprocess.contract.ProjectDetailsContract;
import com.autodesk.shejijia.enterprise.nodeprocess.data.ProjectRepository;
import com.autodesk.shejijia.shared.components.common.appglobal.ConstructionConstants;
import com.autodesk.shejijia.shared.components.common.entity.ProjectInfo;
import com.autodesk.shejijia.shared.components.common.listener.LoadDataCallback;
import com.autodesk.shejijia.shared.components.common.utility.LogUtils;

/**
 * Created by t_xuz on 10/31/16.
 * 项目详情presenter-->ProjectDetailsFragment
 */

public class ProjectDetailsPresenter implements ProjectDetailsContract.Presenter {

    private Context mContext;
    private ProjectDetailsContract.View mProjectDetailsView;
    private ProjectRepository mProjectRepository;
    private long mProjectId;
    private boolean isHasTaskData;

    public ProjectDetailsPresenter(Context context, ProjectDetailsContract.View projectDetailsView) {
        this.mContext = context;
        this.mProjectDetailsView = projectDetailsView;
        mProjectRepository = ProjectRepository.getInstance();
    }

    @Override
    public void initRequestParams(long projectId, boolean taskData) {
        this.mProjectId = projectId;
        this.isHasTaskData = taskData;
    }

    @Override
    public void getProjectDetails() {
        Bundle requestParamsBundle = new Bundle();
        requestParamsBundle.putLong("pid", mProjectId);
        requestParamsBundle.putBoolean("task_data", isHasTaskData);

        getProjectDetailsData(requestParamsBundle);
    }

    private void getProjectDetailsData(Bundle requestParams) {
        mProjectRepository.getProjectDetails(requestParams, ConstructionConstants.REQUEST_TAG_GET_PROJECT_DETAILS, new LoadDataCallback<ProjectInfo>() {
            @Override
            public void onLoadSuccess(ProjectInfo data) {
                mProjectDetailsView.hideLoading();
                LogUtils.d("project_details", data.toString());
                // TODO: 11/1/16 调用view层方法，传递项目详情信息
            }

            @Override
            public void onLoadFailed(String errorMsg) {
                mProjectDetailsView.hideLoading();
                mProjectDetailsView.showNetError(errorMsg);
            }
        });
    }
}
