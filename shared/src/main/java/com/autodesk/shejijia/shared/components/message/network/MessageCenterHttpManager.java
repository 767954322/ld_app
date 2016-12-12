package com.autodesk.shejijia.shared.components.message.network;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.autodesk.shejijia.shared.components.common.network.OkJsonArrayRequest;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;

/**
 * Created by luchongbin on 2016/12/6.
 */

public interface MessageCenterHttpManager {
    void getUnreadCount(String projectIds, String requestTag, @NonNull OkJsonArrayRequest.OKResponseCallback callback);
    void getMessageCenterInfo(Bundle requestParams, String requestTag, @NonNull OkJsonRequest.OKResponseCallback callback);
}
