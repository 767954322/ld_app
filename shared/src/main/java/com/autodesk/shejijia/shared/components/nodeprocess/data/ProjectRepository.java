package com.autodesk.shejijia.shared.components.nodeprocess.data;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.shared.components.common.datamodel.ProjectDataSource;
import com.autodesk.shejijia.shared.components.common.datamodel.ProjectRemoteDataSource;
import com.autodesk.shejijia.shared.components.common.entity.Project;
import com.autodesk.shejijia.shared.components.common.entity.ProjectInfo;
import com.autodesk.shejijia.shared.components.common.entity.ProjectList;
import com.autodesk.shejijia.shared.components.common.entity.ResponseError;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Like;
import com.autodesk.shejijia.shared.components.common.entity.microbean.PlanInfo;
import com.autodesk.shejijia.shared.components.common.entity.microbean.UnreadMessageIssue;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Task;
import com.autodesk.shejijia.shared.components.common.listener.ResponseCallback;
import com.autodesk.shejijia.shared.components.common.network.OkJsonArrayRequest;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.JsonFileUtil;
import com.autodesk.shejijia.shared.components.common.utility.ResponseErrorUtil;
import com.autodesk.shejijia.shared.components.message.network.MessageCenterHttpManagerImpl;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.Map;
import java.util.List;


/**
 * Created by t_xuz on 10/17/16.
 */
public final class ProjectRepository implements ProjectDataSource {

    private ProjectInfo mProjectInfo;
    private PlanInfo mActivePlan;
    private boolean mActivePlanEditing;

    private ProjectRepository() {
    }

    private static final class ProjectRepositoryHolder {
        private static final ProjectRepository INSTANCE = new ProjectRepository();
    }

    public static ProjectRepository getInstance() {
        return ProjectRepositoryHolder.INSTANCE;
    }

    @Override
    public void getProjectList(Bundle requestParams, String requestTag, @NonNull final ResponseCallback<ProjectList, ResponseError> callback) {
        ProjectRemoteDataSource.getInstance().getProjectList(requestParams, requestTag, callback);
    }

    @Override
    public void getProjectInfo(Bundle requestParams, String requestTag, @NonNull final ResponseCallback<ProjectInfo, ResponseError> callback) {

        ProjectRemoteDataSource.getInstance().getProjectInfo(requestParams, requestTag, new ResponseCallback<ProjectInfo, ResponseError>() {
            @Override
            public void onSuccess(ProjectInfo data) {
                mProjectInfo = data;
                mActivePlan = null;
                callback.onSuccess(data);
            }

            @Override
            public void onError(ResponseError error) {
                callback.onError(error);
            }
        });
    }

    @Override
    public void getProject(Bundle requestParams, String requestTag, @NonNull ResponseCallback<Project, ResponseError> callback) {
        ProjectRemoteDataSource.getInstance().getProject(requestParams, requestTag, callback);
    }

    @Override
    public void getPlanByProjectId(String pid, String requestTag, final @NonNull ResponseCallback<ProjectInfo, ResponseError> callback) {
        ProjectRemoteDataSource.getInstance().getPlanByProjectId(pid, requestTag, callback);
    }

    @Override
    public void updatePlan(String pid, Bundle requestParams, String requestTag, final @NonNull ResponseCallback<Project, ResponseError> callback) {
        ProjectRemoteDataSource.getInstance().updatePlan(pid, requestParams, requestTag, new ResponseCallback<Project, ResponseError>() {
            @Override
            public void onSuccess(Project data) {
                mActivePlan = null;
                callback.onSuccess(data);
            }

            @Override
            public void onError(ResponseError error) {
                callback.onError(error);
            }
        });
    }

    @Override
    public void updateProjectLikes(Bundle requestParams, String requestTag, JSONObject jsonRequest, @NonNull ResponseCallback<Like, ResponseError> callback) {
        ProjectRemoteDataSource.getInstance().updateProjectLikes(requestParams, requestTag, jsonRequest, callback);
    }

    @Override
    public void getTask(Bundle requestParams, String requestTag, ResponseCallback<Task, ResponseError> callback) {
        ProjectRemoteDataSource.getInstance().getTask(requestParams, requestTag, callback);
    }

    @Override
    public void reserveTask(Bundle requestParams, String requestTag, ResponseCallback<Void, ResponseError> callback) {
        ProjectRemoteDataSource.getInstance().reserveTask(requestParams, requestTag, callback);
    }

    @Override
    public void submitTaskComment(Bundle requestParams, String requestTag, ResponseCallback<Void, ResponseError> callback) {
        ProjectRemoteDataSource.getInstance().submitTaskComment(requestParams, requestTag, callback);
    }

    @Override
    public void confirmTask(Bundle requestParams, String requestTag, ResponseCallback<Void, ResponseError> callback) {
        ProjectRemoteDataSource.getInstance().confirmTask(requestParams, requestTag, callback);
    }

    @Override
    public void uploadTaskFiles(Bundle requestParams, String requestTag, ResponseCallback<Void, ResponseError> callback) {
        ProjectRemoteDataSource.getInstance().uploadTaskFiles(requestParams, requestTag, callback);
    }

    @Override
    public void updateTaskStatus(Bundle requestParams, String requestTag, JSONObject jsonRequest, @NonNull ResponseCallback<Map<String, String>, ResponseError> callback) {
        ProjectRemoteDataSource.getInstance().updateTaskStatus(requestParams,requestTag,jsonRequest,callback);
    }

    public ProjectInfo getActiveProject() {
        return mProjectInfo;
    }


    public void getActivePlan(@NonNull String pid, String requestTag, final @NonNull ResponseCallback<PlanInfo, ResponseError> callback) {
        if (mActivePlan == null || !mActivePlan.getProjectId().equalsIgnoreCase(pid)) {
            getPlanByProjectId(pid, requestTag, new ResponseCallback<ProjectInfo, ResponseError>() {
                @Override
                public void onSuccess(ProjectInfo data) {
                    mActivePlanEditing = false;
                    mActivePlan = data.getPlan();
                    mActivePlan.setProjectId(String.valueOf(data.getProjectId()));
                    callback.onSuccess(mActivePlan);
                }

                @Override
                public void onError(ResponseError error) {
                    callback.onError(error);
                }
            });
        } else {
            callback.onSuccess(mActivePlan);
        }
    }

    @Override
    public void getUnreadMsgCount(String projectIds, String requestTag,@NonNull final ResponseCallback<List, ResponseError> callback) {
        MessageCenterHttpManagerImpl.getInstance().getUnreadMsgCount(projectIds,requestTag,new OkJsonArrayRequest.OKResponseCallback(){
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                ResponseError responseError =  ResponseErrorUtil.checkVolleyError(volleyError);
                callback.onError(responseError);
            }

            @Override
            public void onResponse(JSONArray jsonArray) {
                List list = JsonFileUtil.jsonArray2List(jsonArray);
                callback.onSuccess(list);
            }
        });
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

    @Override
    public void getUnReadMessageAndIssue(String projectIds, String requestTag, @NonNull ResponseCallback<UnreadMessageIssue, ResponseError> callback) {
        ProjectRemoteDataSource.getInstance().getUnReadMessageAndIssue(projectIds, requestTag, callback);
    }
}
