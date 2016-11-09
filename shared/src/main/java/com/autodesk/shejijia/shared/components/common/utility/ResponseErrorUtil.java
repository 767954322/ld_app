package com.autodesk.shejijia.shared.components.common.utility;

import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.VolleyError;
import com.autodesk.shejijia.shared.components.common.listener.ResponseCallback;

import java.util.Map;

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
            // TODO: 16/11/9 后期在不断修改,后端还没完全做好
           callback.onError("服务端找不到资源,不存在,或者服务端在维修");
            Log.d("asdf","status:" + networkResponse.statusCode);
            Map<String, String> headers = networkResponse.headers;
            Log.d("asdf","headers:" + headers.toString());
            String data = new String(networkResponse.data);
            Log.d("asdf","datas:" + data);
//            ResponseError responseError = GsonUtil.jsonToBean(data, ResponseError.class);
//            callback.onError(responseError.getMessage());
        }
    }
}
