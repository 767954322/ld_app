package com.autodesk.shejijia.shared.components.common.utility;

import com.android.volley.NetworkResponse;
import com.android.volley.VolleyError;

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
    public static String checkVolleyError(VolleyError volleyError) {
        NetworkResponse networkResponse = volleyError.networkResponse;
        if (null == networkResponse) {

            return "网络连接失败,请查看网络连接是否正常";
        } else {
            // TODO: 16/11/9 后期在不断修改,后端还没完全做好
            String errorLog;
            try {
                errorLog = "响应错误状态码:" + networkResponse.statusCode +
                        " 和错误信息:" + new String(networkResponse.data);
            } catch (Exception e) {
                errorLog = "响应错误状态码:" + networkResponse.statusCode;
            }

            return errorLog;
        }
    }
}
