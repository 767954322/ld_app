package com.autodesk.shejijia.consumer.utils;

import android.app.Activity;

import com.android.volley.NetworkResponse;
import com.android.volley.VolleyError;
import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.AlertView;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.OnItemClickListener;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.framework.AdskApplication;

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
        NetworkResponse networkResponse = error.networkResponse;

        if (networkResponse != null) {
            int statusCode = networkResponse.statusCode;
//            String data= String.valueOf(networkResponse.data);

            switch (statusCode) {
                case 401:// 登陆过期
                    showAlertView(context.getString(R.string.loginguoqi), 0);
                    break;
                case 400:// 参数错误
                    showAlertView(context.getString(R.string.canshuyichang), 1);
                    break;
                case 403: // 当前操作不合法（无权限再次操作）
                    showAlertView(context.getString(R.string.caozuoerror), 1);
                    break;
                case 500: // API内部异常（服务器异常）
                    showAlertView(context.getString(R.string.apierrror), 1);
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

                }else {

                }


            }
        }).show();
    }
}
