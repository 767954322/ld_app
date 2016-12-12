package com.autodesk.shejijia.shared.components.message.datamodel;

import android.os.Bundle;
import android.support.annotation.NonNull;
import com.autodesk.shejijia.shared.components.common.entity.ResponseError;
import com.autodesk.shejijia.shared.components.common.listener.ResponseCallback;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by luchongbin on 2016/12/6.
 */
public interface MessageCenterDataSource {
     void getUnreadCount(String projectIds, String requestTag,@NonNull ResponseCallback<JSONArray, ResponseError> callback);
     void getMessageCenterInfo(Bundle requestParams, String requestTag,@NonNull ResponseCallback<JSONObject, ResponseError> callback);
     void changeUnreadState(String requestTag,String memberId,String threadId,@NonNull ResponseCallback<JSONObject, ResponseError> callback);
}
