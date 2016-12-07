package com.autodesk.shejijia.shared.components.message.datamodel;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.shared.components.common.entity.ProjectList;
import com.autodesk.shejijia.shared.components.common.entity.ResponseError;
import com.autodesk.shejijia.shared.components.common.listener.ResponseCallback;

import org.json.JSONObject;

/**
 * Created by luchongbin on 2016/12/6.
 */

public interface CallBackMessageCenterDataSource {

    public void onErrorResponse(ResponseError responseError);
    public void onResponse(JSONObject jsonObject);
}
