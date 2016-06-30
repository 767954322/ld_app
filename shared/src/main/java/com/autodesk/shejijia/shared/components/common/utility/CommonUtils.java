package com.autodesk.shejijia.shared.components.common.utility;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;


/**
 * @author Malidong .
 * @version v1.0 .
 * @date 2016-6-13 .
 * @file CommonUtils.java .
 * @brief .
 */
public class CommonUtils {

    private CommonUtils() {
    }

    /**
     * @brief open activity .
     */
    public static void launchActivity(Context context, Class<?> activity) {
        Intent intent = new Intent(context, activity);
        context.startActivity(intent);
    }

    /**
     * 清空WebView缓存
     *
     * @param context
     */
    public static void clearCookie(Context context) {
        CookieSyncManager.createInstance(context);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();
        CookieSyncManager.getInstance().sync();
    }

    /**
     * 获取当前app的版本号
     *
     * @param context
     * @return 版本号
     */
    public static String getAppVersionName(Context context) {
        PackageInfo info = null;
        try {
            info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return info.versionName;
    }

}
