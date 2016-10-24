package com.autodesk.shejijia.consumer.personalcenter.recommend.view;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.BtnStatusBean;
import com.autodesk.shejijia.shared.components.common.tools.wheel.WheelView;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;


/**
 * *@author yaoxuehua .
 * @version v1.0 .
 * @date 16-10-21 .
 * @file StoreActivity.java .
 * @brief 动态添加view,封装后可多页面复用 .
 */

public class DynamicAddViewControls extends LinearLayout{

    private Context context;
    private int countOffset = 0;
    private String[] arr;
    private OnButtonClickedListener onButtonClickedListener;
    private int singleClickOrDoubleBtnCount = 1;


    public DynamicAddViewControls(Context context) {
        super(context);
        this.context = context;
        setOrientation(VERTICAL);
    }


    public void dynamicData(String[] arr){

        this.arr = arr;
        dynamicCreateStoreLine();
        invalidate();
    }

    /**
     * 动态创建店铺选项行数
     */
    public void dynamicCreateStoreLine() {

        int countSize = arr.length % 3;
        int totalLine = 0;
        if (countSize == 0) {
            totalLine = arr.length / 3;
        } else {
            totalLine = arr.length / 3 + 1;
        }

        for (int i = 0; i < totalLine; i++) {

            LinearLayout linearLayout = new LinearLayout(context);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200);
            linearLayout.setLayoutParams(layoutParams);
            dynamicAddStoreButton(linearLayout, countSize, arr);
            addView(linearLayout);
            invalidate();
        }
    }

    /**
     * 动态添加店铺按钮
     */
    public void dynamicAddStoreButton(LinearLayout linearLayout, final int countSize, final String[] arr) {

        TextView textView;
        BtnStatusBean  btnStatusBean;
        LinearLayout.LayoutParams layoutParamsButton = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParamsButton.weight = 1;
        layoutParamsButton.leftMargin = 25;
        layoutParamsButton.rightMargin = 25;
        layoutParamsButton.topMargin = 20;
        layoutParamsButton.bottomMargin = 20;

        for (int i = 0; i < 3; i++) {

            textView = new TextView(context);
            btnStatusBean = new BtnStatusBean();
            textView.setGravity(Gravity.CENTER);
            textView.setLayoutParams(layoutParamsButton);

            if (countSize + countOffset <= arr.length) {

                textView.setText(arr[countOffset]);

                btnStatusBean.setCountOffset(countOffset);
                btnStatusBean.setSingleClickOrDoubleBtnCount(1);
                textView.setTag(btnStatusBean);
                textView.setBackgroundResource(R.drawable.store_bg_btn);
                if (countOffset < arr.length) {

                    countOffset++;
                }
                final TextView finaltextView = textView;
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        BtnStatusBean btnStatusBean = (BtnStatusBean) v.getTag();
                        onButtonClickedListener.onButtonClicked(btnStatusBean.getCountOffset());

                        if (btnStatusBean.getSingleClickOrDoubleBtnCount() == 1){

                            finaltextView.setTextColor(UIUtils.getColor(R.color.bg_0084ff));
                            finaltextView.setBackgroundResource(R.drawable.store_bg_btn_checked);
                            btnStatusBean.setSingleClickOrDoubleBtnCount(2);
                        }else if (btnStatusBean.getSingleClickOrDoubleBtnCount() == 2){

                            finaltextView.setTextColor(Color.BLACK);
                            finaltextView.setBackgroundResource(R.drawable.store_bg_btn);
                            btnStatusBean.setSingleClickOrDoubleBtnCount(1);
                        }
                        finaltextView.setTag(btnStatusBean);
                    }
                });
            }else {

                textView.setAlpha(0);
            }

            linearLayout.addView(textView);
        }
    }

    public interface OnButtonClickedListener {
        /**
         * 接口回调，方便调用数据；
         * Callback method to be invoked when current item clicked
         * @param itemIndex the index of clicked button tag
         */
        void onButtonClicked(int itemIndex);
    }

    public void setListener(OnButtonClickedListener onButtonClickedListener){

        this.onButtonClickedListener = onButtonClickedListener;
    }
}


