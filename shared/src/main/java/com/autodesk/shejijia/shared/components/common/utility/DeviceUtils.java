package com.autodesk.shejijia.shared.components.common.utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import com.autodesk.shejijia.shared.framework.AdskApplication;

/**
 * @author: yangxuewu   .
 * created at: 2015/12/3 0003 9:14 .
 * Describe :Cell phone information  .
 */
public class DeviceUtils {

    private static String mDeviceId = null;

    /**
     * For information on the screen  .
     *
     * @param context .
     * @return.
     * @author user  .
     * @since 1.0  .
     */
    public static DisplayMetrics getDisplayMetrics(Context context) {
        DisplayMetrics dm = context.getApplicationContext().getResources().getDisplayMetrics();
        return dm;
    }

    public static String getVersionName() {
        try {
            PackageInfo pi = AdskApplication.getInstance().getPackageManager()
                    .getPackageInfo(AdskApplication.getInstance().getPackageName(), 0);
            return pi.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "2.1";
    }

    /**
     * To obtain the version number  .
     *
     * @return
     */
    public static int getVersionCode() {
        try {
            PackageInfo pi = AdskApplication.getInstance().getPackageManager()
                    .getPackageInfo(AdskApplication.getInstance().getPackageName(), 0);
            return pi.versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static String getDeviceID(Context context) {
        if (mDeviceId == null) {
            SharedPreferences sharedpreferences = context.getSharedPreferences("bids", 0);
            String s = sharedpreferences.getString("i", null);

            if (s == null) {
                s = getIMEI(context);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("i", s);
                editor.commit();
            }
            String s1 = sharedpreferences.getString("a", null);
            if (s1 == null) {
                s1 = getAndroidId(context);
                SharedPreferences.Editor editor1 = sharedpreferences.edit();
                editor1.putString("a", s1);
                editor1.commit();
            }

            mDeviceId = Md5Tools.toMd5((new StringBuilder()).append("com.okay").append(s).append(s1).toString().getBytes(), true);
        }

        return mDeviceId;
    }

    private static String getIMEI(Context context) {
        String s = "";
        TelephonyManager telephonymanager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (telephonymanager != null) {
            s = telephonymanager.getDeviceId();
            if (TextUtils.isEmpty(s))
                s = "";
        }
        return s;
    }

    private static String getAndroidId(Context context) {
        String s = "";
        s = android.provider.Settings.Secure.getString(context.getContentResolver(), "android_id");
        if (TextUtils.isEmpty(s))
            s = "";
        return s;
    }


}