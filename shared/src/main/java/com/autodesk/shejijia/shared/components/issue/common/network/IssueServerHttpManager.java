package com.autodesk.shejijia.shared.components.issue.common.network;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.Volley;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.appglobal.ConstructionConstants;
import com.autodesk.shejijia.shared.components.common.appglobal.UrlConstants;
import com.autodesk.shejijia.shared.components.common.network.NetRequestManager;
import com.autodesk.shejijia.shared.components.common.network.OkJsonArrayRequest;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.utility.LogUtils;
import com.autodesk.shejijia.shared.components.common.utility.UserInfoUtils;
import com.autodesk.shejijia.shared.framework.AdskApplication;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.RequestBody;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by Menghao.Gu on 2016/12/5.
 */

public class IssueServerHttpManager {

    public IssueServerHttpManager() {
    }

    private static class ServerHttpManagerHolder {
        private static final IssueServerHttpManager INSTANCE = new IssueServerHttpManager();
    }

    public static IssueServerHttpManager getInstance() {
        return IssueServerHttpManager.ServerHttpManagerHolder.INSTANCE;
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

    private void post(String requestTag, String requestUrl, JSONObject jsonObject, @NonNull OkJsonRequest.OKResponseCallback callback) {
        OkJsonRequest okRequest = new OkJsonRequest(OkJsonRequest.Method.POST, requestUrl, jsonObject, callback) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put("Accept", Constant.NetBundleKey.APPLICATON_JSON);
                header.put("X-Token", UserInfoUtils.getToken(AdskApplication.getInstance()));
                return header;
            }
        };
        NetRequestManager.getInstance().addRequest(requestTag, okRequest);
    }

    /**
     * 获取列表数
     *
     * @param callback
     */
    public void getIssueNum(@NonNull OkJsonRequest.OKResponseCallback callback) {
        // TODO  目前先打通流程
        callback.onResponse(null);
        get("tag", "url", callback);
    }

    /**
     * 获取列表数
     *
     * @param callback
     */
    public void putIssueTracking(JSONObject jsonObject, @NonNull OkJsonRequest.OKResponseCallback callback) {

        String postUrl = "http://cp-uat-issue.homestyler.com/api/v1/issues";

        post(null, postUrl, jsonObject, callback);
    }

}
