package com.autodesk.shejijia.shared.components.nodeprocess.data;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.autodesk.shejijia.shared.components.common.datamodel.ProjectDataSource;
import com.autodesk.shejijia.shared.components.common.datamodel.ProjectRemoteDataSource;
import com.autodesk.shejijia.shared.components.common.entity.Project;
import com.autodesk.shejijia.shared.components.common.entity.ProjectInfo;
import com.autodesk.shejijia.shared.components.common.entity.ProjectList;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Like;
import com.autodesk.shejijia.shared.components.common.entity.microbean.PlanInfo;
import com.autodesk.shejijia.shared.components.common.listener.ResponseCallback;

import org.json.JSONObject;


/**
 * Created by t_xuz on 10/17/16.
 */
public final class ProjectRepository implements ProjectDataSource {

    private ProjectList mProjectList;
    private ProjectInfo mProjectInfo;

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
    public void getProjectTaskData(Bundle requestParams, String requestTag, @NonNull final ResponseCallback<ProjectInfo> callback) {

        ProjectRemoteDataSource.getInstance().getProjectTaskData(requestParams, requestTag, new ResponseCallback<ProjectInfo>() {
            @Override
            public void onSuccess(ProjectInfo data) {
                mProjectInfo = data;
                callback.onSuccess(data);
            }

            @Override
            public void onError(String errorMsg) {
                callback.onError(errorMsg);
            }
        });
    }

    @Override
    public void getProjectTaskId(Bundle requestParams, String requestTag, @NonNull ResponseCallback<Project> callback) {
        ProjectRemoteDataSource.getInstance().getProjectTaskId(requestParams, requestTag, callback);
    }

    @Override
    public void getPlanByProjectId(String pid, String requestTag, @NonNull ResponseCallback<PlanInfo> callback) {
        ProjectRemoteDataSource.getInstance().getPlanByProjectId(pid, requestTag, callback);
    }

    @Override
    public void updatePlan(String pid, Bundle requestParams, String requestTag, @NonNull ResponseCallback<Project> callback) {
        ProjectRemoteDataSource.getInstance().updatePlan(pid, requestParams, requestTag, callback);
        //TODO notify active project is dirty
    }

    @Override
    public void updateProjectLikes(Bundle requestParams, String requestTag, JSONObject jsonRequest, @NonNull ResponseCallback<Like> callback) {
        ProjectRemoteDataSource.getInstance().updateProjectLikes(requestParams, requestTag, jsonRequest, callback);
    }

    public ProjectInfo getProjectInfoByCache() {
        return mProjectInfo;
    }
}
