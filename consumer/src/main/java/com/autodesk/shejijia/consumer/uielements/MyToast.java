package com.autodesk.shejijia.consumer.uielements;

import android.app.Activity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.utility.Assert;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;

/**
 * @author he.liu .
 * @version v1.0 .
 * @date 2016-6-12 .
 * @file MyToast.java .
 * @brief 自定义吐司 .
 */
public class MyToast {

    private static Toast mToast;

    private MyToast() {
    }

    /**
     * show a toast
     *
     * @param activity
     * @param text
     */
    public static void show(Activity activity, String text) {
        Assert.notNull(activity, "Context cant be NULL!");
        if (mToast == null) {
            mToast = Toast.makeText(activity, text, Toast.LENGTH_SHORT);
        }
        mToast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0, UIUtils.dip2px(activity, 100));
        mToast.setDuration(Toast.LENGTH_SHORT);
        mToast.setView(getToastStyle(activity, text));
        mToast.show();
    }

    /**
     * show a long toast
     *
     * @param activity
     * @param text
     */
    public static void showLong(Activity activity, String text) {
        Assert.notNull(activity, "Context cant be NULL!");
        if (mToast == null) {
            mToast = Toast.makeText(activity, text, Toast.LENGTH_LONG);
        }
        mToast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0, UIUtils.dip2px(activity, 100));
        mToast.setDuration(Toast.LENGTH_LONG);
        mToast.setView(getToastStyle(activity, text));
        mToast.show();
    }

    public static void show(Activity activity, int resId) {
        show(activity, activity.getString(resId));
    }

    public static void showLong(Activity activity, int resId) {
        showLong(activity, activity.getString(resId));
    }

    /**
     * 设置吐司的样式
     *
     * @param activity 上下文对象
     * @param text     吐司的内容
     * @return 吐司的控件
     */
    private static View getToastStyle(Activity activity, String text) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View layout = inflater.inflate(R.layout.view_custom_toast, null);
        TextView tv = (TextView) layout.findViewById(R.id.tv_toast);
        tv.setText(text);
        return layout;
    }
}
