package com.autodesk.shejijia.shared.components.message.datamodel;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;

/**
 * Created by luchongbin on 2016/12/6.
 */
public interface MessageCenterDataSource {
    public void getUnreadCount(String project_ids, String requestTag);
    public void listMessageCenterInfo(Bundle requestParams, String requestTag);
}
