package com.autodesk.shejijia.consumer.utils;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.autodesk.shejijia.consumer.R;

/**
 * Created by：yangxuewu on 16/7/22 10:16
 */
public class ToastUtil {

    public static void showCustomToast(Activity context, String text) {
        LayoutInflater inflater = context.getLayoutInflater();
        View layout = inflater.inflate(R.layout.custome_toast_layout, null);
        TextView textView = (TextView) layout.findViewById(R.id.tvForToast);
        //设置TextView的text内容
        textView.setText(text);
        Toast toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setView(layout);
        toast.show();
    }
}
