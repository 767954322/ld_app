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

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by t_xuz on 8/16/16.
 * 与 服务端主线相关的 服务端api接口的封装类
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

    private void get(String requestTag, String requestUrl, @NonNull OkJsonRequest.OKResponseCallback callback) {
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
        LogUtils.v(ConstructionConstants.LOG_TAG_REQUEST, requestUrl);
        LogUtils.v(ConstructionConstants.LOG_TAG_REQUEST, jsonRequest.toString());
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


}
