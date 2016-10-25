package com.autodesk.shejijia.shared.components.common.network;

import android.app.Application;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.utility.Constants;
import com.autodesk.shejijia.shared.components.common.utility.UserInfoUtils;
import com.autodesk.shejijia.shared.framework.AdskApplication;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by t_xuz on 8/16/16.
 * 与 服务端主线相关的 服务端api接口的封装类
 */
public class EnterpriseServerHttpManager {

    private EnterpriseServerHttpManager() {
    }

    private static class ServerHttpManagerHolder {
        private static final EnterpriseServerHttpManager INSTANCE = new EnterpriseServerHttpManager();
    }

    public static EnterpriseServerHttpManager getInstance() {
        return ServerHttpManagerHolder.INSTANCE;
    }

    /*
    * 获取项目列表api接口
    * */
    public void getProjectLists(final int offset,
                                final int limit,
                                final String token,
                                OkJsonRequest.OKResponseCallback callback) {

        String url = Constants.BASE_URL + "/projects?"
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

        String url = Constants.BASE_URL + "/projects"
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
    * 按日期查询任务列表
    * requestUrl 查询的请求url
    * token 当前登陆用户的access token
    * callback 请求回调接口
    * */
    public void getUserProjectLists(@NonNull String requestUrl, @Nullable String requestTag,
                                    OkJsonRequest.OKResponseCallback callback) {
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
    * */
    public void getPlanDetails(final long pid,
                               final String token,
                               OkJsonRequest.OKResponseCallback callback) {
        String url = Constants.BASE_URL + "/projects"
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
    * 获取 project 详情的接口,含 task 详情的
    * */
    public void getProjectDetails(final long pid,
                                  final String token,
                                  final boolean isTaskDetail,
                                  OkJsonRequest.OKResponseCallback callback) {
        String url = Constants.BASE_URL + "/projects"
                + "/" + pid
                + "?task_data=" + isTaskDetail;
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
}
