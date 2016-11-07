package com.autodesk.shejijia.shared.components.nodeprocess.presenter;

import android.content.Context;
import android.os.Bundle;

import com.autodesk.shejijia.shared.components.common.appglobal.ConstructionConstants;
import com.autodesk.shejijia.shared.components.common.entity.ProjectInfo;
import com.autodesk.shejijia.shared.components.common.listener.ResponseCallback;
import com.autodesk.shejijia.shared.components.common.utility.LogUtils;
import com.autodesk.shejijia.shared.components.nodeprocess.contract.ProjectDetailsContract;
import com.autodesk.shejijia.shared.components.nodeprocess.data.ProjectRepository;

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
        mProjectRepository.getProjectTaskData(requestParams, ConstructionConstants.REQUEST_TAG_GET_PROJECT_DETAILS, new ResponseCallback<ProjectInfo>() {
            @Override
            public void onSuccess(ProjectInfo data) {
                mProjectDetailsView.hideLoading();
                LogUtils.d("project_details", data.toString());
                // TODO: 11/1/16 调用view层方法，传递项目详情信息
            }

            @Override
            public void onError(String errorMsg) {
                mProjectDetailsView.hideLoading();
                mProjectDetailsView.showNetError(errorMsg);
            }
        });
    }
}
