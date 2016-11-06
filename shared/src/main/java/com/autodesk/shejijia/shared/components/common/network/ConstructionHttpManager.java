package com.autodesk.shejijia.shared.components.common.network;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.appglobal.ConstructionConstants;
import com.autodesk.shejijia.shared.components.common.entity.Project;
import com.autodesk.shejijia.shared.components.common.listener.LoadDataCallback;
import com.autodesk.shejijia.shared.components.common.utility.UrlUtils;
import com.autodesk.shejijia.shared.components.common.utility.UserInfoUtils;
import com.autodesk.shejijia.shared.framework.AdskApplication;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by t_xuz on 8/16/16.
 * 与 服务端主线相关的 服务端api接口的封装类
 */
public class ConstructionHttpManager {

    private ConstructionHttpManager() {
    }

    private static class ServerHttpManagerHolder {
        private static final ConstructionHttpManager INSTANCE = new ConstructionHttpManager();
    }

    public static ConstructionHttpManager getInstance() {
        return ServerHttpManagerHolder.INSTANCE;
    }

    /*
    * 获取项目列表api接口
    * */
    public void getProjectLists(final int offset,
                                final int limit,
                                final String token,
                                OkJsonRequest.OKResponseCallback callback) {

        String url = ConstructionConstants.BASE_URL + "/projects?"
                + "like=0"
                + "&limit=" + limit
                + "&offset=" + offset;
        OkJsonRequest okRequest = new OkJsonRequest(OkJsonRequest.Method.GET, url, null, callback) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put(Constant.NetBundleKey.CONTENT_TYPE, Constant.NetBundleKey.APPLICATON_JSON);
                header.put("X-Token", token);
                return header;
            }
        };
        NetRequestManager.getInstance().addRequest(okRequest);
    }

    /*
    * 获取task列表中每个任务详情的api接口
    * pid 项目id
     *tid 任务id
    * */
    public void getTaskDetails(final long pid,
                               final String tid,
                               final String token,
                               OkJsonRequest.OKResponseCallback callback) {

        String url = ConstructionConstants.BASE_URL + "/projects"
                + "/" + pid
                + "/tasks/" + tid;
        OkJsonRequest okRequest = new OkJsonRequest(OkJsonRequest.Method.GET, url, null, callback) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put(Constant.NetBundleKey.CONTENT_TYPE, Constant.NetBundleKey.APPLICATON_JSON);
                header.put("X-Token", token);
                return header;
            }
        };
        NetRequestManager.getInstance().addRequest("task_details", okRequest);
    }


    /*
    * 获取项目列表的接口
    * requestUrl 查询的请求url
    * token 当前登陆用户的access token
    * callback 请求回调接口
    * */
    public void getUserProjectLists(@NonNull Bundle requestParams, @Nullable String requestTag,
                                    OkJsonRequest.OKResponseCallback callback) {
        String requestUrl = UrlUtils.buildUrl(ConstructionConstants.BASE_URL + "/users/projects?", requestParams);
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


    /*
    * 获取 plan 详情的接口,里面包括项目列表里的任务列表
    * requestUrl 查询的请求url
    * token 当前登陆用户的access token
    * callback 请求回调接口
    * */
    public void getPlanDetails(final long pid,
                               final String token,
                               OkJsonRequest.OKResponseCallback callback) {
        String url = ConstructionConstants.BASE_URL + "/projects"
                + "/" + pid
                + "/plan";
        OkJsonRequest okRequest = new OkJsonRequest(OkJsonRequest.Method.GET, url, null, callback) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put(Constant.NetBundleKey.CONTENT_TYPE, Constant.NetBundleKey.APPLICATON_JSON);
                header.put("X-Token", token);
                return header;
            }
        };
        NetRequestManager.getInstance().addRequest(okRequest);
    }

    /*
    * 获取项目详情的接口
    * requestUrl 查询的请求url
    * token 当前登陆用户的access token
    * callback 请求回调接口
    * */
    public void getProjectDetails(@NonNull Bundle requestParams, @Nullable String requestTag,
                                  OkJsonRequest.OKResponseCallback callback) {
        long pid = requestParams.getLong("pid");
        boolean task_data = requestParams.getBoolean("task_data", false);
        String requestUrl = ConstructionConstants.BASE_URL + "/projects/" + pid + "?task_data=" + task_data;
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

    public void getPlanByProjectId(@NonNull String pid, String requestTag, @NonNull OkJsonRequest.OKResponseCallback callback) {
        String requestUrl = ConstructionConstants.BASE_URL + "/projects/" + pid + "/plan";
        getData(requestTag, requestUrl, callback);
    }

    private void getData(String requestTag, String requestUrl, @NonNull OkJsonRequest.OKResponseCallback callback) {
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
}
