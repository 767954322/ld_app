package com.autodesk.shejijia.shared.components.message.datamodel;

import android.os.Bundle;
import android.support.annotation.NonNull;
import com.autodesk.shejijia.shared.components.common.entity.ResponseError;
import com.autodesk.shejijia.shared.components.common.listener.ResponseCallback;

import org.json.JSONObject;

/**
 * Created by luchongbin on 2016/12/6.
 */
public interface MessageCenterDataSource {
    public void getUnreadCount(String project_ids, String requestTag,@NonNull ResponseCallback<JSONObject, ResponseError> callback);
    public void listMessageCenterInfo(Bundle requestParams, String requestTag,@NonNull ResponseCallback<JSONObject, ResponseError> callback);
}
