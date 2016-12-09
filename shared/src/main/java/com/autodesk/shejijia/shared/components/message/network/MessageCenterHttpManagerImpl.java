package com.autodesk.shejijia.shared.components.message.network;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.android.volley.AuthFailureError;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.appglobal.ConstructionConstants;
import com.autodesk.shejijia.shared.components.common.network.NetRequestManager;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.utility.UrlUtils;
import com.autodesk.shejijia.shared.components.common.utility.UserInfoUtils;
import com.autodesk.shejijia.shared.framework.AdskApplication;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by luchongbin on 2016/12/6.
 */

public class MessageCenterHttpManagerImpl implements MessageCenterHttpManager {

    private static class ServerHttpManagerHolder {
        private static final MessageCenterHttpManagerImpl INSTANCE = new MessageCenterHttpManagerImpl();
    }

    public static MessageCenterHttpManagerImpl getInstance() {
        return MessageCenterHttpManagerImpl.ServerHttpManagerHolder.INSTANCE;
    }
    @Override
    public void getUnreadCount(String projectIds, String requestTag, @NonNull OkJsonRequest.OKResponseCallback callback) {
        String requestUrl = ConstructionConstants.BASE_URL + "/notifications/unread_count?project_ids=" + projectIds;
        get(requestTag, requestUrl, callback);
    }
    @Override
    public void getMessageCenterInfo(Bundle requestParams, String requestTag, @NonNull OkJsonRequest.OKResponseCallback callback) {
        String requestUrl = UrlUtils.buildUrl(ConstructionConstants.BASE_URL + "/notifications/messages?", requestParams);
        get(requestTag, requestUrl,callback);
    }
    private void get(String requestTag, String requestUrl, @NonNull OkJsonRequest.OKResponseCallback callback) {
        OkJsonRequest okRequest = new OkJsonRequest(OkJsonRequest.Method.GET, requestUrl, null, callback) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put(Constant.NetBundleKey.ACCPET, Constant.NetBundleKey.APPLICATON_JSON);
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
