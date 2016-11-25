package com.autodesk.shejijia.shared.components.common.utility;

import com.android.volley.NetworkResponse;
import com.android.volley.VolleyError;
import com.autodesk.shejijia.shared.components.common.entity.ResponseError;

/**
 * Created by t_aij on 16/11/1.
 * 网络获取数据不成功时的错误解析
 */

public class ResponseErrorUtil {
    /**
     * 网络获取数据不成功时的volleyError解析
     *
     * @param volleyError
     */
    public static ResponseError checkVolleyError(VolleyError volleyError) {
        NetworkResponse networkResponse = volleyError.networkResponse;
        ResponseError responseError;
        if (null == networkResponse) {
            responseError = new ResponseError();
            responseError.setMessage("网络连接错误");
            return responseError;
        } else {
            responseError = new ResponseError();
            responseError.setStatus(networkResponse.statusCode);
            responseError.setMessage("错误信息:" + new String(networkResponse.data));
            return responseError;
        }
    }
}
