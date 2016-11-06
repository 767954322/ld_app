package com.autodesk.shejijia.shared.components.common.datamodel;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.shared.components.common.entity.Project;
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
    public void getPlanByProjectId(String pid, String requestTag, @NonNull LoadDataCallback<PlanInfo> callback) {
        ConstructionHttpManager.getInstance().getPlanByProjectId(pid, requestTag,
                buildOkhttpCallback(callback, PlanInfo.class));
    }

    private <T> OkJsonRequest.OKResponseCallback buildOkhttpCallback(final LoadDataCallback<T> callback, final Class<T> clazz) {
        return new OkJsonRequest.OKResponseCallback() {

            @Override
            public void onResponse(JSONObject jsonObject) {
                LogUtils.d("getPlanByProjectId", jsonObject.toString());
                try {
                    JSONObject planJsonObject = jsonObject.getJSONObject("plan");
                    String result = planJsonObject.toString();
                    T project = GsonUtil.jsonToBean(result, clazz);
                    callback.onLoadSuccess(project);
                } catch (JSONException e) {
                    e.printStackTrace();
                    callback.onLoadFailed("Data format error");
                }
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                callback.onLoadFailed(volleyError.getMessage());
            }
        };
    }


}
