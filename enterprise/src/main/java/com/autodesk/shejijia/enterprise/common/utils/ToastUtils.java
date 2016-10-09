package com.autodesk.shejijia.enterprise.common.utils;

import android.app.Activity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.autodesk.shejijia.enterprise.R;

/**
 * Created by t_xuz on 8/17/16.
 *  自定义样式的toast提示框
 */
public class ToastUtils {

    private ToastUtils(){
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static Boolean isShow = true;


    public static void showShort(Activity mContext,String tip){
        if (!isShow) return;
        if (TextUtils.isEmpty(tip)){
            return;
        }
        View toastLayout = mContext.getLayoutInflater().inflate(R.layout.toast_tip_layout,(ViewGroup)mContext.findViewById(R.id.my_toast));
        TextView tipContent = (TextView)toastLayout.findViewById(R.id.tv_toast);
        Toast toast = new Toast(mContext);
        tipContent.setText(tip);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.setView(toastLayout);
        toast.show();
    }

    public static void showLong(Activity mContext,String tip){
        if (!isShow) return;
        if (TextUtils.isEmpty(tip)){
            return;
        }
        View toastLayout = mContext.getLayoutInflater().inflate(R.layout.toast_tip_layout,(ViewGroup)mContext.findViewById(R.id.my_toast));
        TextView tipContent = (TextView)toastLayout.findViewById(R.id.tv_toast);
        Toast toast = new Toast(mContext);
        tipContent.setText(tip);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.setView(toastLayout);
        toast.show();
    }
}
