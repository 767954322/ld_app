package com.autodesk.shejijia.shared.components.common.datamodel;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.shared.components.common.appglobal.ConstructionConstants;
import com.autodesk.shejijia.shared.components.common.entity.Project;
import com.autodesk.shejijia.shared.components.common.entity.ProjectInfo;
import com.autodesk.shejijia.shared.components.common.entity.ProjectList;
import com.autodesk.shejijia.shared.components.common.entity.ResponseError;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Like;
import com.autodesk.shejijia.shared.components.common.entity.microbean.MileStone;
import com.autodesk.shejijia.shared.components.common.entity.microbean.PlanInfo;
import com.autodesk.shejijia.shared.components.common.listener.IConstructionApi;
import com.autodesk.shejijia.shared.components.common.listener.ResponseCallback;
import com.autodesk.shejijia.shared.components.common.network.ConstructionHttpManager;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.LogUtils;
import com.autodesk.shejijia.shared.components.common.utility.MPNetworkUtils;
import com.autodesk.shejijia.shared.components.common.utility.ResponseErrorUtil;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by t_xuz on 10/17/16.
 * 与 project 相关的api对应的dataModel
 */
public final class ProjectRemoteDataSource implements ProjectDataSource {

    private IConstructionApi<OkJsonRequest.OKResponseCallback> mConstructionHttpManager;

    private ProjectRemoteDataSource() {
        mConstructionHttpManager = ConstructionHttpManager.getInstance();
    }

    private static class ProjectRemoteDataSourceHolder {
        private static final ProjectRemoteDataSource INSTANCE = new ProjectRemoteDataSource();
    }

    public static ProjectRemoteDataSource getInstance() {
        return ProjectRemoteDataSourceHolder.INSTANCE;
    }

    @Override
    public void getProjectList(Bundle requestParams, String requestTag, @NonNull final ResponseCallback<ProjectList, ResponseError> callback) {

        mConstructionHttpManager.getUserProjectLists(requestParams, requestTag, new OkJsonRequest.OKResponseCallback() {

            @Override
            public void onResponse(JSONObject jsonObject) {
                LogUtils.d("ProjectList--", jsonObject + "");
                String result = jsonObject.toString();
                ProjectList projectList = GsonUtil.jsonToBean(result, ProjectList.class);
                callback.onSuccess(projectList);
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                callback.onError(ResponseErrorUtil.checkVolleyError(volleyError));
            }
        });
    }

    @Override
    public void getProjectInfo(final Bundle requestParams, final String requestTag, @NonNull final ResponseCallback<ProjectInfo, ResponseError> callback) {
        mConstructionHttpManager.getProjectDetails(requestParams, requestTag, new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                LogUtils.d("ProjectDetails--taskDetailsList", jsonObject + "");
                String result = jsonObject.toString();
                ProjectInfo projectInfo = GsonUtil.jsonToBean(result, ProjectInfo.class);
                callback.onSuccess(projectInfo);
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                callback.onError(ResponseErrorUtil.checkVolleyError(volleyError));
            }
        });
    }

    @Override
    public void getProject(Bundle requestParams, String requestTag, @NonNull final ResponseCallback<Project, ResponseError> callback) {
        mConstructionHttpManager.getProjectDetails(requestParams, requestTag, new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                LogUtils.d("ProjectDetails--taskIdList", jsonObject + "");
                String result = jsonObject.toString();
                Project project = GsonUtil.jsonToBean(result, Project.class);
                callback.onSuccess(project);
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                callback.onError(ResponseErrorUtil.checkVolleyError(volleyError));
            }
        });
    }

    @Override
    public void getPlanByProjectId(String pid, String requestTag, @NonNull final ResponseCallback<ProjectInfo, ResponseError> callback) {
        mConstructionHttpManager.getPlanByProjectId(pid, requestTag,
                getDefaultCallback(callback, ProjectInfo.class, MileStone.class, new MileStone.MileStoneTypeAdapter()));
    }

    @Override
    public void updatePlan(String pid, Bundle requestParams, String requestTag, @NonNull final ResponseCallback<Project, ResponseError> callback) {
        PlanInfo plan = (PlanInfo) requestParams.getSerializable("body");
        requestParams.remove("body");
        JSONObject jsonRequest = new JSONObject();
        try {
            JSONObject planJsonObject = GsonUtil.beanToJSONObject(plan);
            jsonRequest.put("project_id", pid);
            jsonRequest.put("plan_id", planJsonObject.get("plan_id"));
            jsonRequest.put("start", planJsonObject.get("start"));
            jsonRequest.put("completion", planJsonObject.get("completion"));
            jsonRequest.put("tasks", planJsonObject.get("tasks"));

            mConstructionHttpManager.updatePlan(pid, jsonRequest, requestParams, requestTag,
                    getDefaultCallback(callback, null));
        } catch (JSONException e) {
            e.printStackTrace();
            // TODO Optimize error hint
            ResponseError error = new ResponseError();
            error.setMessage("Date format error");
            callback.onError(error);
        }
    }

    @Override
    public void updateProjectLikes(Bundle requestParams, String requestTag, JSONObject jsonRequest, @NonNull final ResponseCallback<Like, ResponseError> callback) {
        mConstructionHttpManager.putProjectLikes(requestParams, requestTag, jsonRequest, new OkJsonRequest.OKResponseCallback() {

            @Override
            public void onResponse(JSONObject jsonObject) {
                LogUtils.d("Project--star", jsonObject + "");
                String result = jsonObject.toString();
                Like like = GsonUtil.jsonToBean(result, Like.class);
                callback.onSuccess(like);
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                callback.onError(ResponseErrorUtil.checkVolleyError(volleyError));
            }

        });
    }

    private <T, V> OkJsonRequest.OKResponseCallback getDefaultCallback(final ResponseCallback<T, ResponseError> callback, final Class<T> clazz,
                                                                       final Class<V> multiTypeClazz, final TypeAdapter<V> typeAdapter) {
        return new OkJsonRequest.OKResponseCallback() {

            @Override
            public void onResponse(JSONObject jsonObject) {
                String result = jsonObject.toString();
                LogUtils.d(ConstructionConstants.LOG_TAG_REQUEST, result);
                if (clazz == null) {
                    callback.onSuccess(null);
                } else {
                    try {
                        T bean;
                        if (multiTypeClazz != null && typeAdapter != null) {
                            bean = GsonUtil.jsonToBean(result, clazz, multiTypeClazz, typeAdapter);
                        } else {
                            bean = GsonUtil.jsonToBean(result, clazz);
                        }
                        callback.onSuccess(bean);
                    } catch (JsonSyntaxException exception) {
                        exception.printStackTrace();
                        LogUtils.e(ConstructionConstants.LOG_TAG_REQUEST, "e=" + exception);
                        ResponseError error = new ResponseError();
                        error.setMessage("JsonSyntaxException");
                        callback.onError(error);
                    }
                }
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                // TODO log error detail
                LogUtils.e(ConstructionConstants.LOG_TAG_REQUEST, volleyError.toString());
                MPNetworkUtils.logError(ConstructionConstants.LOG_TAG_REQUEST, volleyError, true);
                callback.onError(ResponseErrorUtil.checkVolleyError(volleyError));
            }

        };
    }

    private <T> OkJsonRequest.OKResponseCallback getDefaultCallback(final ResponseCallback<T, ResponseError> callback, final Class<T> clazz) {
        return getDefaultCallback(callback, clazz, null, null);
    }
}
