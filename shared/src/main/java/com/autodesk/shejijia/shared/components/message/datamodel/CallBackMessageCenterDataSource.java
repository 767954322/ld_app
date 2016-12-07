package com.autodesk.shejijia.shared.components.message.datamodel;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.autodesk.shejijia.shared.components.common.entity.ProjectList;
import com.autodesk.shejijia.shared.components.common.entity.ResponseError;
import com.autodesk.shejijia.shared.components.common.listener.ResponseCallback;

/**
 * Created by luchongbin on 2016/12/6.
 */

public interface CallBackMessageCenterDataSource {
    public void listMessageCenterInfo(Bundle requestParams, String requestTag, @NonNull ResponseCallback<ProjectList, ResponseError> callback);
}
