package com.autodesk.shejijia.shared.components.message.datamodel;
import com.autodesk.shejijia.shared.components.common.entity.ResponseError;
import org.json.JSONObject;

/**
 * Created by luchongbin on 2016/12/6.
 */

public interface CallBackMessageCenterDataSource {

    public void onErrorResponse(ResponseError responseError);
    public void onResponse(JSONObject jsonObject);
}
