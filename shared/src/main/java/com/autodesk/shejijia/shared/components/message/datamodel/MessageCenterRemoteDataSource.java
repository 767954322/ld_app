package com.autodesk.shejijia.shared.components.message.datamodel;
import android.os.Bundle;
import android.support.annotation.NonNull;
import com.android.volley.VolleyError;
import com.autodesk.shejijia.shared.components.common.entity.ResponseError;
import com.autodesk.shejijia.shared.components.common.listener.ResponseCallback;
import com.autodesk.shejijia.shared.components.common.network.OkJsonArrayRequest;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.utility.ResponseErrorUtil;
import com.autodesk.shejijia.shared.components.message.network.MessageCenterHttpManagerImpl;
import org.json.JSONArray;
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
    public void getUnreadCount(String projectIds, String requestTag,@NonNull final ResponseCallback<JSONArray, ResponseError> callback) {
        MessageCenterHttpManagerImpl.getInstance().getUnreadCount(projectIds,requestTag,new OkJsonArrayRequest.OKResponseCallback(){
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                ResponseError responseError =  ResponseErrorUtil.checkVolleyError(volleyError);
                callback.onError(responseError);
            }

            @Override
            public void onResponse(JSONArray jsonArray) {
                callback.onSuccess(jsonArray);
            }
        });
    }
    @Override
    public void getMessageCenterInfo(Bundle requestParams, String requestTag, @NonNull final ResponseCallback<JSONObject, ResponseError> callback) {
        MessageCenterHttpManagerImpl.getInstance().getMessageCenterInfo(requestParams,requestTag,new OkJsonRequest.OKResponseCallback(){
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
    public void changeUnreadState(String requestTag, String memberId, String threadId, @NonNull final ResponseCallback<JSONObject, ResponseError> callback) {
        MessageCenterHttpManagerImpl.getInstance().changeUnreadState(requestTag,memberId,threadId, new OkJsonRequest.OKResponseCallback(){
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                ResponseError responseError =  ResponseErrorUtil.checkVolleyError(volleyError);
                callback.onError(responseError);
            }

            @Override
            public void onResponse(JSONObject jsonObject) {

            }
        });
    }
}
