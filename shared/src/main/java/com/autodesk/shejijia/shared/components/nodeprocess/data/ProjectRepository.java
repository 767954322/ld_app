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
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;

import org.json.JSONObject;


/**
 * Created by t_xuz on 10/17/16.
 */
public final class ProjectRepository implements ProjectDataSource {

    private ProjectList mProjectList;
    private ProjectInfo mProjectInfo;
    private PlanInfo mActivePlan;
    private boolean mActivePlanEditing;

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


    @Override
    public void getProjectInfo(Bundle requestParams, String requestTag, @NonNull final ResponseCallback<ProjectInfo> callback) {

        ProjectRemoteDataSource.getInstance().getProjectInfo(requestParams, requestTag, new ResponseCallback<ProjectInfo>() {
            @Override
            public void onSuccess(ProjectInfo data) {
                mProjectInfo = data;
                mActivePlan = null;
                callback.onSuccess(data);
            }

            @Override
            public void onError(String errorMsg) {
                callback.onError(errorMsg);
            }
        });
    }

    @Override
    public void getProject(Bundle requestParams, String requestTag, @NonNull ResponseCallback<Project> callback) {
        ProjectRemoteDataSource.getInstance().getProject(requestParams, requestTag, callback);
    }

    @Override
    public void getPlanByProjectId(String pid, String requestTag, final @NonNull ResponseCallback<ProjectInfo> callback) {
        ProjectRemoteDataSource.getInstance().getPlanByProjectId(pid, requestTag, callback);
    }

    @Override
    public void updatePlan(String pid, Bundle requestParams, String requestTag, final @NonNull ResponseCallback<Project> callback) {
        ProjectRemoteDataSource.getInstance().updatePlan(pid, requestParams, requestTag, new ResponseCallback<Project>() {
            @Override
            public void onSuccess(Project data) {
                mActivePlan = null;
                callback.onSuccess(data);
            }

            @Override
            public void onError(String errorMsg) {
                callback.onError(errorMsg);
            }
        });
    }

    @Override
    public void updateProjectLikes(Bundle requestParams, String requestTag, JSONObject jsonRequest, @NonNull ResponseCallback<Like> callback) {
        ProjectRemoteDataSource.getInstance().updateProjectLikes(requestParams, requestTag, jsonRequest, callback);
    }

    public ProjectInfo getActiveProject() {
        return mProjectInfo;
    }

    public void getActivePlan(@NonNull String pid, String requestTag, final @NonNull ResponseCallback<PlanInfo> callback) {
        if (mActivePlan == null || !mActivePlan.getProjectId().equalsIgnoreCase(pid)) {
            getPlanByProjectId(pid, requestTag, new ResponseCallback<ProjectInfo>() {
                @Override
                public void onSuccess(ProjectInfo data) {
                    mActivePlanEditing = false;
                    mActivePlan = data.getPlan();
                    mActivePlan.setProjectId(String.valueOf(data.getProjectId()));
                    callback.onSuccess(mActivePlan);
                }

                @Override
                public void onError(String errorMsg) {
                    callback.onError(errorMsg);
                }
            });
        } else {
            callback.onSuccess(mActivePlan);
        }
    }

    public boolean isActivePlanEditing() {
        return mActivePlan != null && mActivePlanEditing;
    }

    public void setActivePlanEditing(boolean activePlanEditing) {
        this.mActivePlanEditing = activePlanEditing;
    }

    private PlanInfo copyPlan(PlanInfo planInfo) {
        String jsonString = GsonUtil.beanToString(planInfo);
        return GsonUtil.jsonToBean(jsonString, PlanInfo.class);
    }
}
