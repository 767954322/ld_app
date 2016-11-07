package com.autodesk.shejijia.shared.components.common.utility;

import com.android.volley.NetworkResponse;
import com.android.volley.VolleyError;
import com.autodesk.shejijia.shared.components.common.entity.ResponseError;
import com.autodesk.shejijia.shared.components.common.listener.ResponseCallback;

/**
 * Created by t_aij on 16/11/1.
 * 网络获取数据不成功时的错误解析
 */

public class ResponseErrorUtil {
    /**
     * 网络获取数据不成功时的volleyError解析
     * @param volleyError
     * @param callback
     */
    public static void checkVolleyError(VolleyError volleyError, ResponseCallback<?> callback) {
        NetworkResponse networkResponse = volleyError.networkResponse;
        if(null == networkResponse) {
            callback.onError("网络连接失败,请查看网络连接是否正常");
        } else {
            String data = new String(networkResponse.data);
            ResponseError responseError = GsonUtil.jsonToBean(data, ResponseError.class);
            callback.onError(responseError.getMessage());
        }
    }
}
