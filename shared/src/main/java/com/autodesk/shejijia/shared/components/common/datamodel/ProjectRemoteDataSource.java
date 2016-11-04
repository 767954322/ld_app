package com.autodesk.shejijia.shared.components.common.datamodel;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.shared.components.common.entity.Project;

import com.autodesk.shejijia.shared.components.common.entity.ProjectList;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Like;
import com.autodesk.shejijia.shared.components.common.listener.UpdateDataCallback;
import com.autodesk.shejijia.shared.components.common.network.ConstructionHttpManager;

import com.autodesk.shejijia.shared.components.common.entity.ProjectInfo;
import com.autodesk.shejijia.shared.components.common.entity.ProjectList;
import com.autodesk.shejijia.shared.components.common.entity.microbean.PlanInfo;
import com.autodesk.shejijia.shared.components.common.listener.LoadDataCallback;
import com.autodesk.shejijia.shared.components.common.network.ConstructionHttpManager;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.LogUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by t_xuz on 10/17/16.
 * 与 project 相关的api对应的dataModel
 */
public final class ProjectRemoteDataSource implements ProjectDataSource {
    private final static String LOG_TAG = "ProjectRemoteDataSource";

    private ProjectRemoteDataSource() {
    }

    private static class ProjectRemoteDataSourceHolder {
        private static final ProjectRemoteDataSource INSTANCE = new ProjectRemoteDataSource();
    }

    public static ProjectRemoteDataSource getInstance() {
        return ProjectRemoteDataSourceHolder.INSTANCE;
    }

    @Override
    public void getProjectList(Bundle requestParams, String requestTag, @NonNull final LoadDataCallback<ProjectList> callback) {
        ConstructionHttpManager.getInstance().getUserProjectLists(requestParams, requestTag, new OkJsonRequest.OKResponseCallback() {

            @Override
            public void onResponse(JSONObject jsonObject) {
                LogUtils.d("ProjectList--", jsonObject + "");
                String result = jsonObject.toString();
                ProjectList projectList = GsonUtil.jsonToBean(result, ProjectList.class);
                callback.onLoadSuccess(projectList);
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                callback.onLoadFailed(volleyError.getMessage());
            }
        });
    }

    @Override
    public void getProjectTaskData(final Bundle requestParams, final String requestTag, @NonNull final LoadDataCallback<ProjectInfo> callback) {
        ConstructionHttpManager.getInstance().getProjectDetails(requestParams, requestTag, new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                LogUtils.d("ProjectDetails--taskDetailsList", jsonObject + "");
                String result = jsonObject.toString();
                ProjectInfo projectInfo = GsonUtil.jsonToBean(result, ProjectInfo.class);
                callback.onLoadSuccess(projectInfo);
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                callback.onLoadFailed(volleyError.getMessage());
            }
        });
    }

    @Override
    public void getProjectTaskId(Bundle requestParams, String requestTag, @NonNull final LoadDataCallback<Project> callback) {
        ConstructionHttpManager.getInstance().getProjectDetails(requestParams, requestTag, new OkJsonRequest.OKResponseCallback() {

            @Override
            public void onResponse(JSONObject jsonObject) {
                LogUtils.d("ProjectDetails--taskIdList", jsonObject + "");
                String result = jsonObject.toString();
                Project project = GsonUtil.jsonToBean(result, Project.class);
                callback.onLoadSuccess(project);
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                callback.onLoadFailed(volleyError.getMessage());
            }
        });
    }

    @Override
    public void getPlanByProjectId(String pid, String requestTag, @NonNull final LoadDataCallback<PlanInfo> callback) {
        ConstructionHttpManager.getInstance().getPlanByProjectId(pid, requestTag, new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                LogUtils.d(LOG_TAG, jsonObject.toString());
                try {
                    JSONObject planJsonObject = jsonObject.getJSONObject("plan");
                    String result = planJsonObject.toString();
                    PlanInfo plan = GsonUtil.jsonToBean(result, PlanInfo.class);
                    callback.onLoadSuccess(plan);
                } catch (JSONException e) {
                    e.printStackTrace();
                    callback.onLoadFailed("Data format error");
                }
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                callback.onLoadFailed(volleyError.getMessage());
            }
        });
    }
    
    public void onStarProject(Bundle requestParams, String requestTag, JSONObject jsonRequest, @NonNull final UpdateDataCallback<Like> callback) {
        ConstructionHttpManager.getInstance().putStarProject(requestParams, requestTag, jsonRequest, new OkJsonRequest.OKResponseCallback() {

            @Override
            public void onResponse(JSONObject jsonObject) {
                LogUtils.d("Project--star", jsonObject + "");
                String result = jsonObject.toString();
                Like like = GsonUtil.jsonToBean(result, Like.class);
                callback.onUpdateSuccess(like);

            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {

                callback.onUpdateFailed(volleyError.getMessage());
            }

        });
    }
}
