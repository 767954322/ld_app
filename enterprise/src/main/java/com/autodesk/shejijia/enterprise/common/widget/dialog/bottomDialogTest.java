package com.autodesk.shejijia.enterprise.common.widget.dialog;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.autodesk.shejijia.enterprise.R;
import com.autodesk.shejijia.enterprise.common.utils.ToastUtils;

/**
 * Created by t_xuz on 8/22/16.
 */
public class bottomDialogTest {
    private Context mContext;
    private PopupWindow bottomPopUp;


    private void initBottomPopup(){
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.popup_save_update_dialog,null);
        TextView textView = (TextView)contentView.findViewById(R.id.save_update);
        TextView textView2 = (TextView)contentView.findViewById(R.id.delete_update);
        TextView textView3 = (TextView)contentView.findViewById(R.id.cancel_update);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.showShort((Activity) mContext,"预约时间已录入!");
            }
        });
       /* textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        textView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });*/

        contentView.setFocusable(true);
        contentView.setFocusableInTouchMode(true);
        bottomPopUp = new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        bottomPopUp.setFocusable(true);
        bottomPopUp.setOutsideTouchable(false);
        contentView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if (keyCode == KeyEvent.KEYCODE_BACK){

                    bottomPopUp.dismiss();
                    return true;
                }
                return false;
            }
        });
        bottomPopUp.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackGround(1.0f);
            }
        });
        View view = (((Activity)mContext).findViewById(android.R.id.content)).getRootView();
        bottomPopUp.setAnimationStyle(R.style.pop_bottom_animation);
        setBackGround(0.7f);
        bottomPopUp.showAtLocation(view, Gravity.BOTTOM,0,0);
    }

    private void setBackGround(float alpha){
        WindowManager.LayoutParams layoutParams = ((Activity)mContext).getWindow().getAttributes();
        layoutParams.alpha = alpha;
        ((Activity)mContext).getWindow().setAttributes(layoutParams);
    }
}
