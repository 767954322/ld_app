package com.autodesk.shejijia.shared.components.message.datamodel;
import android.os.Bundle;
import android.support.annotation.NonNull;
import com.android.volley.VolleyError;
import com.autodesk.shejijia.shared.components.common.entity.ResponseError;
import com.autodesk.shejijia.shared.components.common.listener.ResponseCallback;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.utility.ResponseErrorUtil;
import com.autodesk.shejijia.shared.components.message.network.MessageCenterHttpManager;
import com.autodesk.shejijia.shared.components.message.network.MessageCenterHttpManagerImpl;

import org.json.JSONObject;

/**
 * Created by luchongbin on 2016/12/6.
 */

public class MessageCenterRemoteDataSource implements MessageCenterDataSource{
    public MessageCenterRemoteDataSource() {
    }
    private static class ServerHttpManagerHolder {
        private static final MessageCenterRemoteDataSource INSTANCE = new MessageCenterRemoteDataSource();
    }

    public static MessageCenterRemoteDataSource getInstance() {
        return MessageCenterRemoteDataSource.ServerHttpManagerHolder.INSTANCE;
    }

    @Override
    public void getUnreadCount(String projectIds, String requestTag,@NonNull final ResponseCallback<JSONObject, ResponseError> callback) {
        MessageCenterHttpManagerImpl.getInstance().getUnreadCount(projectIds,requestTag,new OkJsonRequest.OKResponseCallback(){
            @Override
            public void onResponse(JSONObject jsonObject) {
                callback.onSuccess(jsonObject);
            }
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                ResponseError responseError =  ResponseErrorUtil.checkVolleyError(volleyError);
                callback.onError(responseError);
            }
        });
    }
    @Override
    public void listMessageCenterInfo(Bundle requestParams, String requestTag, @NonNull final ResponseCallback<JSONObject, ResponseError> callback) {
        MessageCenterHttpManagerImpl.getInstance().listMessageCenterInfo(requestParams,requestTag,new OkJsonRequest.OKResponseCallback(){
            @Override
            public void onResponse(JSONObject jsonObject) {
                callback.onSuccess(jsonObject);
            }
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                ResponseError responseError =  ResponseErrorUtil.checkVolleyError(volleyError);
                callback.onError(responseError);
            }
        });
    }
}
