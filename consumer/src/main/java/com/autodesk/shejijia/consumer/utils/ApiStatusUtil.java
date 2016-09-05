package com.autodesk.shejijia.consumer.utils;

import android.app.Activity;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.VolleyError;
import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.AlertView;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.OnItemClickListener;
import com.autodesk.shejijia.shared.components.common.utility.MPNetworkUtils;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.framework.AdskApplication;

import java.util.Map;

/**
 * 网络状态判断类
 * Created by：yangxuewu on 16/8/20 15:28
 */
public class ApiStatusUtil {

    private Activity mContext;
    private static ApiStatusUtil apiStatusUtil = new ApiStatusUtil();

    public static ApiStatusUtil getInstance() {
        if (apiStatusUtil == null) {
            apiStatusUtil = new ApiStatusUtil();
        }
        return apiStatusUtil;
    }

    /**
     * 状态码：
     * 400  参数错误
     * 401  登陆过期
     * 403  当前操作不合法（无权限再次操作）
     * 500  API内部异常（服务器异常）
     *
     * @param error
     */
    public void apiStatuError(VolleyError error, Activity context) {
        this.mContext = context;
        Log.i("ApiStatusUtil", "--------------------------------------错误日志-------------------------------------");
        Log.e("Response Error", error.getMessage(), error);
        MPNetworkUtils.logError("Response url", error);
        NetworkResponse response = error.networkResponse;
        if (response == null) {
            showAlertView("请检查网络连接状态", 1);
            return;
        }
        Map<String, String> headers = response.headers;
        for (String key : headers.keySet()) {
            Log.e("RESPONSE HEADERS", key + "=" + headers.get(key));
        }
        Log.e("RESPONSE CODE", "ERROR CODE = " + response.statusCode);
        byte[] htmlBodyBytes = response.data;  //回应的报文的包体内容
        Log.e("Response body--->", new String(htmlBodyBytes));
        Log.i("ApiStatusUtil", "--------------------------------------错误日志-------------------------------------");


        if (response != null) {
            int statusCode = response.statusCode;
//            String data= String.valueOf(networkResponse.data);

            switch (statusCode) {
                case 401:// 登陆过期
                    showAlertView(context.getString(R.string.loginguoqi), 0);
                    break;
                case 400:// 参数错误
//                    showAlertView(context.getString(R.string.canshuyichang), 1);
                    break;
                case 402://该状态码是为了将来可能的需求而预留的
//                    showAlertView(context.getString(R.string.http402), 1);
                    break;
                case 403: // 当前操作不合法（无权限再次操作）
                   // showAlertView(context.getString(R.string.caozuoerror), 1);
                    break;
                case 404://请求失败，请求所希望得到的资源未被在服务器上发现。没有信息能够告诉用户这个状况到底是暂时的还是永久的
//                    showAlertView(context.getString(R.string.http404), 1);
                    break;
                case 405://
                case 406://
                case 407://
                case 408://
                case 409://
                case 410://
                case 411://
                case 412://
                case 413://
                case 414://
//                    showAlertView(context.getString(R.string.http402), 1);
                    break;
                case 500: // API内部异常（服务器异常）
                    showAlertView(context.getString(R.string.network_error), 1);
                    break;
                default:  //链接网络失败
                    showAlertView(UIUtils.getString(R.string.network_error), 1);
                    break;

            }

        } else {//网络链接失败
            showAlertView(UIUtils.getString(R.string.network_error), 1);
        }


    }

    private void showAlertView(String content, final int tag) {
        new AlertView(UIUtils.getString(R.string.tip), content, null, new String[]{UIUtils.getString(R.string.sure)}, null, mContext,
                AlertView.Style.Alert, new OnItemClickListener() {
            @Override
            public void onItemClick(Object object, int position) {
                if (tag == 0) {
                    AdskApplication.getInstance().doLogout(mContext);
                    AdskApplication.getInstance().doLogin(mContext);

                } else {

                }


            }
        }).show();
    }
}
