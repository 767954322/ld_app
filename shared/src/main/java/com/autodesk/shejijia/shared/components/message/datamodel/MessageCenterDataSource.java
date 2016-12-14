package com.autodesk.shejijia.shared.components.message.datamodel;

import android.os.Bundle;
import android.support.annotation.NonNull;
import com.autodesk.shejijia.shared.components.common.entity.ResponseError;
import com.autodesk.shejijia.shared.components.common.listener.ResponseCallback;
import com.autodesk.shejijia.shared.components.message.entity.MessageInfo;

import org.json.JSONObject;

/**
 * Created by luchongbin on 2016/12/6.
 */
public interface MessageCenterDataSource {
     void getMessageCenterInfo(Bundle requestParams, String requestTag,@NonNull ResponseCallback<MessageInfo, ResponseError> callback);
     void changeUnreadMsgState(String requestTag,String memberId,String threadId,@NonNull ResponseCallback<JSONObject, ResponseError> callback);
}
