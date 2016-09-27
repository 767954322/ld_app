package com.autodesk.shejijia.shared.components.common.uielements;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;

/**
 * Created by：yangxuewu on 16/6/20 12:11
 */
public class CustomDialog {

    interface OnDialogClickListenerCallBack {
        void onClickOkListener();

        void onClickCancelListener();
    }

    public CustomDialog(Context context, OnDialogClickListenerCallBack onDialogClickListenerCallBack) {
        this.context = context;
        this.onDialogClickListenerCallBack = onDialogClickListenerCallBack;
    }

    /**
     * 默认的Dialog
     *
     * @param title                    显示的标题
     * @param msg                      显示的信息
     * @param isSure                   是否显示ok按钮
     * @param isCancel                 是否显示cancel按钮
     * @param isCanceledOnTouchOutside 是否点击对话框之外取消
     */
    public void showCustomViewDialog(String title, String msg, boolean isShowTitle, boolean isSure, boolean isCancel, boolean isCanceledOnTouchOutside) {
        showCustomViewDialog(title, msg, UIUtils.getString(R.string.sure), UIUtils.getString(R.string.cancel), isShowTitle, isSure, isCancel, isCanceledOnTouchOutside);
    }

    /**
     * 自定义Dialog，确定和取消文字自定义
     *
     * @param title                  　显示的标题
     * @param msg                    　显示的信息
     * @param sureString             　显示ok按钮自定义
     * @param cancelString           　显示cancel按钮自定义
     * @param isShowTitle            　是否显示title按钮
     * @param isSure                 是否显示确定按钮
     * @param isCancel               　是否显示取消按钮
     * @param CanceledOnTouchOutside 　点击对话框之外是否取消
     */
    public void showCustomViewDialog(String title, String msg, String sureString, String cancelString, boolean isShowTitle, boolean isSure, boolean isCancel, boolean CanceledOnTouchOutside) {
        LayoutInflater inflater = LayoutInflater.from(context);
        RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.view_dialg_util, null);
        final Dialog dialog = new AlertDialog.Builder(context).create();
        dialog.show();
        dialog.setCanceledOnTouchOutside(CanceledOnTouchOutside);
        dialog.getWindow().setContentView(layout);
        TextView dialog_msg = (TextView) layout.findViewById(R.id.dialog_msg);
        TextView dialog_title = (TextView) layout.findViewById(R.id.dialog_title);
        if (isShowTitle) {
            dialog_title.setText(title);
        } else {
            dialog_title.setVisibility(View.GONE);
        }

        dialog_msg.setText(msg);

        Button btnOK = (Button) layout.findViewById(R.id.dialog_ok);
        btnOK.setText(sureString);
        btnOK.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onDialogClickListenerCallBack.onClickOkListener();
                dialog.dismiss();
            }
        });

        Button btnCancel = (Button) layout.findViewById(R.id.dialog_cancel);
        btnCancel.setText(cancelString);
        btnCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onDialogClickListenerCallBack.onClickCancelListener();
                dialog.dismiss();
            }
        });
        if (isSure) {
            btnOK.setVisibility(View.VISIBLE);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(UIUtils.dip2px(context, 120), UIUtils.dip2px(context, 48));
            btnOK.setLayoutParams(params);
        } else {
            btnOK.setVisibility(View.GONE);
        }

        if (isCancel) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(UIUtils.dip2px(context, 120), UIUtils.dip2px(context, 48));
            btnCancel.setLayoutParams(params);
            btnCancel.setVisibility(View.VISIBLE);
        } else {
            btnCancel.setVisibility(View.GONE);
        }
    }

    private Context context;

    private OnDialogClickListenerCallBack onDialogClickListenerCallBack;
}
