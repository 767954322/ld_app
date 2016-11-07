package com.autodesk.shejijia.shared.components.nodeprocess.data;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.autodesk.shejijia.shared.components.common.entity.Project;
import com.autodesk.shejijia.shared.components.common.entity.ProjectInfo;
import com.autodesk.shejijia.shared.components.common.entity.ProjectList;

import com.autodesk.shejijia.shared.components.common.entity.microbean.Plan;
import com.autodesk.shejijia.shared.components.common.entity.microbean.PlanInfo;
import com.autodesk.shejijia.shared.components.common.listener.LoadDataCallback;
import com.autodesk.shejijia.shared.components.common.datamodel.ProjectDataSource;
import com.autodesk.shejijia.shared.components.common.datamodel.ProjectRemoteDataSource;
import com.autodesk.shejijia.shared.components.common.utility.LogUtils;

import com.autodesk.shejijia.shared.components.common.entity.microbean.Like;
import com.autodesk.shejijia.shared.components.common.listener.ResponseCallback;
import com.autodesk.shejijia.shared.components.common.datamodel.ProjectDataSource;
import com.autodesk.shejijia.shared.components.common.datamodel.ProjectRemoteDataSource;
import com.autodesk.shejijia.shared.components.common.listener.ResponseCallback;

import org.json.JSONObject;


/**
 * Created by t_xuz on 10/17/16.
 */
public final class ProjectRepository implements ProjectDataSource {

    private ProjectList mProjectList;

    private ProjectRepository() {
    }

    private static final class NodeProcessRepositoryHolder {
        private static final ProjectRepository INSTANCE = new ProjectRepository();
    }

    public static ProjectRepository getInstance() {
        return NodeProcessRepositoryHolder.INSTANCE;
    }

    @Override
    public void getProjectList(Bundle requestParams, String requestTag, @NonNull final ResponseCallback<ProjectList> callback) {

        if (mProjectList != null) {
            callback.onSuccess(mProjectList);
        } else {

            ProjectRemoteDataSource.getInstance().getProjectList(requestParams, requestTag, new ResponseCallback<ProjectList>() {
                @Override
                public void onSuccess(ProjectList data) {
                    mProjectList = data;
                    callback.onSuccess(data);
                }

                @Override
                public void onError(String errorMsg) {
                    callback.onError(errorMsg);
                }
            });
        }
    }


    @Override
    public void getProjectTaskData(Bundle requestParams, String requestTag, @NonNull ResponseCallback<ProjectInfo> callback) {
        ProjectRemoteDataSource.getInstance().getProjectTaskData(requestParams, requestTag, callback);
    }

    @Override
    public void getProjectTaskId(Bundle requestParams, String requestTag, @NonNull ResponseCallback<Project> callback) {
        ProjectRemoteDataSource.getInstance().getProjectTaskId(requestParams, requestTag, callback);
    }

    @Override
    public void getPlanByProjectId(String pid, String requestTag, @NonNull LoadDataCallback<PlanInfo> callback) {
        ProjectRemoteDataSource.getInstance().getPlanByProjectId(pid, requestTag, callback);
	｝

    public void onStarProject(Bundle requestParams, String requestTag, JSONObject jsonRequest, @NonNull ResponseCallback<Like> callback) {
        ProjectRemoteDataSource.getInstance().onStarProject(requestParams, requestTag, jsonRequest, callback);
    }
}
