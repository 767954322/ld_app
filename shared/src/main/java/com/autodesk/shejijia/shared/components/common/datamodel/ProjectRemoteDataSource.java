package com.autodesk.shejijia.shared.components.common.datamodel;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.shared.components.common.appglobal.ConstructionConstants;
import com.autodesk.shejijia.shared.components.common.entity.Project;
import com.autodesk.shejijia.shared.components.common.entity.ProjectInfo;
import com.autodesk.shejijia.shared.components.common.entity.ProjectList;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Like;
import com.autodesk.shejijia.shared.components.common.entity.microbean.PlanInfo;
import com.autodesk.shejijia.shared.components.common.listener.ResponseCallback;
import com.autodesk.shejijia.shared.components.common.network.ConstructionHttpManager;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.LogUtils;
import com.autodesk.shejijia.shared.components.common.utility.ResponseErrorUtil;

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

    public void getProjectList(Bundle requestParams, String requestTag, @NonNull final ResponseCallback<ProjectList> callback) {

        ConstructionHttpManager.getInstance().getUserProjectLists(requestParams, requestTag, new OkJsonRequest.OKResponseCallback() {

            @Override
            public void onResponse(JSONObject jsonObject) {
                LogUtils.d("ProjectList--", jsonObject + "");
                String result = jsonObject.toString();
                ProjectList projectList = GsonUtil.jsonToBean(result, ProjectList.class);
                callback.onSuccess(projectList);
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                ResponseErrorUtil.checkVolleyError(volleyError,callback);
            }
        });
    }

    @Override
    public void getProjectTaskData(final Bundle requestParams, final String requestTag, @NonNull final ResponseCallback<ProjectInfo> callback) {
        ConstructionHttpManager.getInstance().getProjectDetails(requestParams, requestTag, new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                LogUtils.d("ProjectDetails--taskDetailsList", jsonObject + "");
                String result = jsonObject.toString();
                ProjectInfo projectInfo = GsonUtil.jsonToBean(result, ProjectInfo.class);
                callback.onSuccess(projectInfo);
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                ResponseErrorUtil.checkVolleyError(volleyError,callback);
            }
        });
    }

    @Override
    public void getProjectTaskId(Bundle requestParams, String requestTag, @NonNull final ResponseCallback<Project> callback) {
        ConstructionHttpManager.getInstance().getProjectDetails(requestParams, requestTag, new OkJsonRequest.OKResponseCallback() {

            @Override
            public void onResponse(JSONObject jsonObject) {
                LogUtils.d("ProjectDetails--taskIdList", jsonObject + "");
                String result = jsonObject.toString();
                Project project = GsonUtil.jsonToBean(result, Project.class);
                callback.onSuccess(project);
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                ResponseErrorUtil.checkVolleyError(volleyError,callback);
            }
        });
    }

    @Override
    public void getPlanByProjectId(String pid, String requestTag, @NonNull final ResponseCallback<PlanInfo> callback) {
        ConstructionHttpManager.getInstance().getPlanByProjectId(pid, requestTag, new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                LogUtils.d(ConstructionConstants.LOG_TAG_REQUEST, jsonObject.toString());
                try {
                    JSONObject planJsonObject = jsonObject.getJSONObject("plan");
                    String result = planJsonObject.toString();
                    PlanInfo plan = GsonUtil.jsonToBean(result, PlanInfo.class);
                    callback.onSuccess(plan);
                } catch (JSONException e) {
                    e.printStackTrace();
                    callback.onError("Data format error");
                }
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                ResponseErrorUtil.checkVolleyError(volleyError,callback);
            }
        });
    }

    @Override
    public void updatePlan(String pid, Bundle requestParams, String requestTag, @NonNull final ResponseCallback<Project> callback) {
        PlanInfo plan = (PlanInfo) requestParams.getSerializable("body");
        requestParams.remove("body");
        JSONObject jsonRequest = new JSONObject();
        try {
            JSONObject planJsonObject = GsonUtil.beanToJSONObject(plan);
            jsonRequest.put("plan_id", planJsonObject.get("plan_id"));
            jsonRequest.put("start", planJsonObject.get("start"));
            jsonRequest.put("completion", planJsonObject.get("completion"));
            jsonRequest.put("tasks", planJsonObject.get("tasks"));
            jsonRequest.put("project_id", pid);

            ConstructionHttpManager.getInstance().updatePlan(pid, jsonRequest, requestParams, requestTag,
                    getDefaultCallback(callback, Project.class));
        } catch (JSONException e) {
            e.printStackTrace();
            // TODO Optimize error hint
            callback.onError("Date format error");
        }
    }


    public void updateProjectLikes(Bundle requestParams, String requestTag, JSONObject jsonRequest, @NonNull final ResponseCallback<Like> callback) {
        ConstructionHttpManager.getInstance().putProjectLikes(requestParams, requestTag, jsonRequest, new OkJsonRequest.OKResponseCallback() {

            @Override
            public void onResponse(JSONObject jsonObject) {
                LogUtils.d("Project--star", jsonObject + "");
                String result = jsonObject.toString();
                Like like = GsonUtil.jsonToBean(result, Like.class);
                callback.onSuccess(like);
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                ResponseErrorUtil.checkVolleyError(volleyError,callback);
            }

        });
    }

    private <T> OkJsonRequest.OKResponseCallback getDefaultCallback(final ResponseCallback<T> callback, final Class<T> clazz) {
        return new OkJsonRequest.OKResponseCallback() {

            @Override
            public void onResponse(JSONObject jsonObject) {
                String result = jsonObject.toString();
                LogUtils.d(ConstructionConstants.LOG_TAG_REQUEST, result);
                T bean = GsonUtil.jsonToBean(result, clazz);
                callback.onSuccess(bean);
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                // TODO log error detail
                LogUtils.e(ConstructionConstants.LOG_TAG_REQUEST, volleyError.toString());
                callback.onError(volleyError.getMessage());
            }

        };
    }
}
