package com.autodesk.shejijia.shared.components.common.uielements;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.autodesk.shejijia.shared.R;

/**
 * Created by allengu on 16-7-25.
 */
public class WXSharedPopWin extends PopupWindow {


    private final TextView tv_wx_shared_tofriends;
    private final TextView tv_wx_shared_tocircleof_friends;
    private Context mContext;

    private View view;


    public WXSharedPopWin(Context mContext, View.OnClickListener itemsOnClick) {

        this.view = LayoutInflater.from(mContext).inflate(R.layout.layout_pop_wx_shared, null);

        //找对象
        tv_wx_shared_tofriends = (TextView) this.view.findViewById(R.id.tv_wx_shared_tofriends);
        tv_wx_shared_tocircleof_friends = (TextView) this.view.findViewById(R.id.tv_wx_shared_tocircleof_friends);

        // 设置按钮监听
        tv_wx_shared_tofriends.setOnClickListener(itemsOnClick);
        tv_wx_shared_tocircleof_friends.setOnClickListener(itemsOnClick);


        // 设置外部可点击
        this.setOutsideTouchable(true);
        // mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        this.view.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = view.findViewById(R.id.pop_layout).getTop();

                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });


    /* 设置弹出窗口特征 */
        // 设置视图
        this.setContentView(this.view);
        // 设置弹出窗体的宽和高
        this.setHeight(RelativeLayout.LayoutParams.MATCH_PARENT);
        this.setWidth(RelativeLayout.LayoutParams.MATCH_PARENT);

        // 设置弹出窗体可点击
        this.setFocusable(true);

        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        // 设置弹出窗体的背景
        this.setBackgroundDrawable(dw);

        // 设置弹出窗体显示时的动画，从底部向上弹出
        this.setAnimationStyle(R.style.take_photo_anim);

    }

}
