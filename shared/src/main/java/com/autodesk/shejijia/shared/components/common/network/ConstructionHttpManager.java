package com.autodesk.shejijia.shared.components.common.network;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.appglobal.ConstructionConstants;
import com.autodesk.shejijia.shared.components.common.listener.IConstructionApi;
import com.autodesk.shejijia.shared.components.common.utility.LogUtils;
import com.autodesk.shejijia.shared.components.common.utility.UrlUtils;
import com.autodesk.shejijia.shared.components.common.utility.UserInfoUtils;
import com.autodesk.shejijia.shared.framework.AdskApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by t_xuz on 8/16/16.
 * 施工平台 api 封装类
 */
public class ConstructionHttpManager implements IConstructionApi<OkJsonRequest.OKResponseCallback> {
    private final static String LOG_TAG = "ConstructionHttpManager";

    private ConstructionHttpManager() {
    }

    private static class ServerHttpManagerHolder {
        private static final ConstructionHttpManager INSTANCE = new ConstructionHttpManager();
    }

    public static ConstructionHttpManager getInstance() {
        return ServerHttpManagerHolder.INSTANCE;
    }

    @Override
    public void getUserProjectLists(@NonNull Bundle requestParams, @Nullable String requestTag, @NonNull OkJsonRequest.OKResponseCallback callback) {
        String requestUrl = UrlUtils.buildUrl(ConstructionConstants.BASE_URL + "/users/projects?", requestParams);
        get(requestTag, requestUrl, callback);
    }

    @Override
    public void getProjectDetails(@NonNull Bundle requestParams, @Nullable String requestTag, @NonNull OkJsonRequest.OKResponseCallback callback) {
        String requestUrl = ConstructionConstants.BASE_URL + "/projects/" + requestParams.getLong("pid")
                + "?task_data=" + requestParams.getBoolean("task_data", false);
        get(requestTag, requestUrl, callback);
    }

    @Override
    public void putProjectLikes(@NonNull Bundle requestParams, @Nullable String requestTag, @NonNull JSONObject jsonRequest, @NonNull OkJsonRequest.OKResponseCallback callback) {
        String requestUrl = ConstructionConstants.BASE_URL + "/projects/" + requestParams.getLong("pid") + "/likes";
        put(requestTag, requestUrl, jsonRequest, callback);
    }

    @Override
    public void getPlanByProjectId(@NonNull String pid, @Nullable String requestTag, @NonNull OkJsonRequest.OKResponseCallback callback) {
        String requestUrl = ConstructionConstants.BASE_URL + "/projects/" + pid + "/plan";
        get(requestTag, requestUrl, callback);
    }

    @Override
    public void updatePlan(@NonNull String pid, @NonNull JSONObject jsonRequest, @NonNull Bundle requestParams, @Nullable String requestTag, @NonNull OkJsonRequest.OKResponseCallback callback) {
        String requestUrl = ConstructionConstants.BASE_URL + "/projects/" + pid + "/plan?";
        requestUrl = UrlUtils.buildUrl(requestUrl, requestParams);
        put(requestTag, requestUrl, jsonRequest, callback);
    }

    @Override
    public void getUnreadMessageAndIssue(@NonNull String projectIds, @Nullable String requestTag, @NonNull OkJsonRequest.OKResponseCallback callback) {
        String requestUrl = ConstructionConstants.BASE_URL + "projects/unread_message_and_unresolved_issue?project_ids=" + projectIds;
        get(requestTag, requestUrl, callback);
    }

    public void getTask(@NonNull Bundle requestParams, @NonNull String requestTag, @NonNull OkJsonRequest.OKResponseCallback callback) {
        String pid = requestParams.getString(ConstructionConstants.BUNDLE_KEY_PROJECT_ID);
        String tid = requestParams.getString(ConstructionConstants.BUNDLE_KEY_TASK_ID);
        String requestUrl = ConstructionConstants.BASE_URL + "/projects/" + pid + "/tasks/" + tid;
        get(requestTag, requestUrl, callback);
    }

    @Override
    public void reserveTask(@NonNull Bundle requestParams, @Nullable String requestTag, @NonNull OkJsonRequest.OKResponseCallback callback) {
        String pid = requestParams.getString(ConstructionConstants.BUNDLE_KEY_PROJECT_ID);
        String tid = requestParams.getString(ConstructionConstants.BUNDLE_KEY_TASK_ID);
        String start = requestParams.getString(ConstructionConstants.BUNDLE_KEY_TASK_START_DATE);
        String requestUrl = ConstructionConstants.BASE_URL + "/projects/" + pid + "/tasks/" + tid + "/reserve";

        JSONObject jsonRequest = new JSONObject();
        try {
            jsonRequest.put("start", start);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        put(requestTag, requestUrl, jsonRequest, callback);
    }

    @Override
    public void submitTaskComment(@NonNull Bundle requestParams, @Nullable String requestTag, @NonNull OkJsonRequest.OKResponseCallback callback) {
        String pid = requestParams.getString(ConstructionConstants.BUNDLE_KEY_PROJECT_ID);
        String tid = requestParams.getString(ConstructionConstants.BUNDLE_KEY_TASK_ID);
        String cid = requestParams.getString(ConstructionConstants.BUNDLE_KEY_TASK_COMMENT_ID);
        String requestUrl = ConstructionConstants.BASE_URL + "/projects/" + pid + "/tasks/" + tid + "/comments";

        String comment = requestParams.getString(ConstructionConstants.BUNDLE_KEY_TASK_COMMENT_CONTENT);
        JSONObject jsonRequest = new JSONObject();
        try {
            jsonRequest.put("content", comment);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (cid == null) {
            post(requestTag, requestUrl, jsonRequest, callback);
        } else {
            requestUrl += "/" + cid;
            put(requestTag, requestUrl, jsonRequest, callback);
        }
    }

    @Override
    public void confirmTask(@NonNull Bundle requestParams, @Nullable String requestTag, @NonNull OkJsonRequest.OKResponseCallback callback) {
        String pid = requestParams.getString(ConstructionConstants.BUNDLE_KEY_PROJECT_ID);
        String tid = requestParams.getString(ConstructionConstants.BUNDLE_KEY_TASK_ID);
        String requestUrl = ConstructionConstants.BASE_URL + "/projects/" + pid + "/tasks/" + tid + "/confirm";

        JSONObject jsonRequest = new JSONObject();
        put(requestTag, requestUrl, jsonRequest, callback);
    }

    @Override
    public void uploadTaskFiles(@NonNull Bundle requestParams, @Nullable String requestTag, @NonNull OkJsonRequest.OKResponseCallback callback) {
        String pid = requestParams.getString(ConstructionConstants.BUNDLE_KEY_PROJECT_ID);
        String tid = requestParams.getString(ConstructionConstants.BUNDLE_KEY_TASK_ID);
        String requestUrl = ConstructionConstants.BASE_URL + "/projects/" + pid + "/tasks/" + tid + "/files?operation=add";

        String filesJson = requestParams.getString(ConstructionConstants.BUNDLE_KEY_TASK_FILES);
        JSONObject jsonRequest = null;
        try {
            jsonRequest = new JSONObject(filesJson);
        } catch (JSONException e) {
            e.printStackTrace();
            throw new RuntimeException("Data format is incorrect, e=" + e);
        }

        put(requestTag, requestUrl, jsonRequest, callback);
    }

    @Override
    public void updateTaskStatus(@NonNull Bundle requestParams, @Nullable String requestTag, @NonNull JSONObject jsonRequest, @NonNull OkJsonRequest.OKResponseCallback callback) {
        String requestUrl = ConstructionConstants.BASE_URL + "/projects/" + requestParams.getLong("pid")
                + "/tasks/" + requestParams.getString("tid") + "/status";
        put(requestTag,requestUrl,jsonRequest,callback);
    }

    private void get(String requestTag, String requestUrl, @NonNull OkJsonRequest.OKResponseCallback callback) {
        LogUtils.i(ConstructionConstants.LOG_TAG_REQUEST, requestUrl);
        LogUtils.i(ConstructionConstants.LOG_TAG_REQUEST, "token=" + UserInfoUtils.getToken(AdskApplication.getInstance()));
        OkJsonRequest okRequest = new OkJsonRequest(OkJsonRequest.Method.GET, requestUrl, null, callback) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put(Constant.NetBundleKey.CONTENT_TYPE, Constant.NetBundleKey.APPLICATON_JSON);
                header.put("X-Token", UserInfoUtils.getToken(AdskApplication.getInstance()));
                return header;
            }
        };
        NetRequestManager.getInstance().addRequest(requestTag, okRequest);
    }

    private void put(String requestTag, String requestUrl, JSONObject jsonRequest, @NonNull OkJsonRequest.OKResponseCallback callback) {
        LogUtils.i(ConstructionConstants.LOG_TAG_REQUEST, requestUrl);
        LogUtils.i(ConstructionConstants.LOG_TAG_REQUEST, jsonRequest == null ? "null" : jsonRequest.toString());
        OkJsonRequest okRequest = new OkJsonRequest(Request.Method.PUT, requestUrl, jsonRequest, callback) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put("Accept", Constant.NetBundleKey.APPLICATON_JSON);
                header.put("X-Token", UserInfoUtils.getToken(AdskApplication.getInstance()));
                return header;
            }

            @Override
            public String getBodyContentType() {
                return Constant.NetBundleKey.APPLICATON_JSON;
            }
        };

        NetRequestManager.getInstance().addRequest(requestTag, okRequest);
    }

    private void post(String requestTag, String requestUrl, JSONObject jsonRequest, @NonNull OkJsonRequest.OKResponseCallback callback) {
        LogUtils.i(ConstructionConstants.LOG_TAG_REQUEST, requestUrl);
        LogUtils.i(ConstructionConstants.LOG_TAG_REQUEST, jsonRequest.toString());
        OkJsonRequest okRequest = new OkJsonRequest(Request.Method.POST, requestUrl, jsonRequest, callback) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put("Accept", Constant.NetBundleKey.APPLICATON_JSON);
                header.put("X-Token", UserInfoUtils.getToken(AdskApplication.getInstance()));
                return header;
            }

            @Override
            public String getBodyContentType() {
                return Constant.NetBundleKey.APPLICATON_JSON;
            }
        };

        NetRequestManager.getInstance().addRequest(requestTag, okRequest);
    }

}
