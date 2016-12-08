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
    private MessageCenterHttpManager mMessageCenterHttpManager;
    public MessageCenterRemoteDataSource() {
        mMessageCenterHttpManager = MessageCenterHttpManagerImpl.getInstance();
    }
    @Override
    public void getUnreadCount(String project_ids, String requestTag,@NonNull final ResponseCallback<JSONObject, ResponseError> callback) {
        mMessageCenterHttpManager.getUnreadCount(project_ids,requestTag,new OkJsonRequest.OKResponseCallback(){
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
        mMessageCenterHttpManager.listMessageCenterInfo(requestParams,requestTag,new OkJsonRequest.OKResponseCallback(){
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
