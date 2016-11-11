package com.autodesk.shejijia.consumer.personalcenter.recommend.view;

import android.app.Activity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.BtnStatusBean;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;


/**
 * *@author yaoxuehua .
 *
 * @version v1.0 .
 * @date 16-10-21 .
 * @file StoreActivity.java .
 * @brief 动态添加view, 封装后可多页面复用  店铺 .
 */

public class DynamicAddViewControls extends LinearLayout {

    private Activity context;
    private int countOffset = 0;
    private String[] arr;
    private OnButtonClickedListener onButtonClickedListener;
    private int singleClickOrDoubleBtnCount = 1;
    private TextView[] textViews;
    private boolean justAll = false;
    private int height;//该控件宽度
    private int width;//该控件高度
    private static double NO_CHANGE_HEIGHT = 1187.00d;
    private static double ADAPTER_COUNT = 1.00d;


    public DynamicAddViewControls(Activity context) {
        super(context);
        this.context = context;
        setOrientation(VERTICAL);
        height = context.getWindowManager().getDefaultDisplay().getHeight();
        width = context.getWindowManager().getDefaultDisplay().getWidth();
        ADAPTER_COUNT = (double) height / NO_CHANGE_HEIGHT;
    }


    public void dynamicData(String[] arr) {

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

        textViews = new TextView[arr.length];

        for (int i = 0; i < totalLine; i++) {

            LinearLayout linearLayout = new LinearLayout(context);
            LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (70 * ADAPTER_COUNT));
            layoutParams.topMargin = (int) (38 * ADAPTER_COUNT);
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
        BtnStatusBean btnStatusBean;
        LayoutParams layoutParamsButton = new LayoutParams((int) (186 * ADAPTER_COUNT), (int) (68 * ADAPTER_COUNT));
        layoutParamsButton.weight = 1;
        layoutParamsButton.leftMargin = (int) (32 * ADAPTER_COUNT);
        layoutParamsButton.rightMargin = (int) (32 * ADAPTER_COUNT);

        for (int i = 0; i < 3; i++) {

            textView = new TextView(context);
            btnStatusBean = new BtnStatusBean();
            textView.setGravity(Gravity.CENTER);
            textView.setMaxEms(6);
            textView.setTextColor(UIUtils.getColor(R.color.bg_33));
            textView.setEllipsize(TextUtils.TruncateAt.END);
            textView.setSingleLine(true);
            textView.setLayoutParams(layoutParamsButton);

            if (countSize + countOffset <= arr.length) {

                textView.setText(arr[countOffset]);
                textViews[countOffset] = textView;
                btnStatusBean.setCountOffset(countOffset);
                btnStatusBean.setSingleClickOrDoubleBtnCount(1);
                textView.setTag(btnStatusBean);
                textView.setBackgroundResource(R.drawable.store_bg_btn);
                if (countOffset < arr.length) {

                    countOffset++;
                }
                textView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        BtnStatusBean btnStatusBean = (BtnStatusBean) v.getTag();
                        onButtonClickedListener.onButtonClicked(btnStatusBean);

                        if (btnStatusBean.getCountOffset() == 0) {

                            changeBtnStatus(btnStatusBean);
                            justAll = true;
                            for (int i = 1; i < textViews.length; i++) {

                                BtnStatusBean btnStatusBeenOne = (BtnStatusBean) textViews[i].getTag();
                                btnStatusBeenOne.setSingleClickOrDoubleBtnCount(2);
                                changeBtnStatus(btnStatusBeenOne);//将‘全部’之外的按钮选中取消
                            }
                        } else {

                            changeBtnStatus(btnStatusBean);
                            if (justAll) {

                                BtnStatusBean btnStatusBeenOne = (BtnStatusBean) textViews[0].getTag();
                                btnStatusBeenOne.setSingleClickOrDoubleBtnCount(2);
                                changeBtnStatus(btnStatusBeenOne);//将‘全部’按钮取消
                                justAll = false;
                            }
                        }
                    }
                });
            } else {

                textView.setAlpha(0);
            }

            linearLayout.addView(textView);
        }
    }

    /**
     * 更改按钮状态
     */
    public void changeBtnStatus(BtnStatusBean btnStatusBean) {

        if (btnStatusBean.getSingleClickOrDoubleBtnCount() == 1) {

            changeTextViewBackgroudAndText(btnStatusBean, textViews[btnStatusBean.getCountOffset()]);
            btnStatusBean.setSingleClickOrDoubleBtnCount(2);
        } else if (btnStatusBean.getSingleClickOrDoubleBtnCount() == 2) {

            changeTextViewBackgroudAndTextUnChecked(btnStatusBean, textViews[btnStatusBean.getCountOffset()]);
            btnStatusBean.setSingleClickOrDoubleBtnCount(1);
        }
        textViews[btnStatusBean.getCountOffset()].setTag(btnStatusBean);
    }


    /**
     * 改变按钮背景，字体颜色
     */
    public void changeTextViewBackgroudAndText(BtnStatusBean btnStatusBean, TextView textView) {


        textView.setTextColor(UIUtils.getColor(R.color.bg_0084ff));
        textView.setBackgroundResource(R.drawable.store_bg_btn_checked);


    }

    public void changeTextViewBackgroudAndTextUnChecked(BtnStatusBean btnStatusBean, TextView textView) {

        textView.setTextColor(UIUtils.getColor(R.color.bg_33));
        textView.setBackgroundResource(R.drawable.store_bg_btn);
        invalidate();
    }

    public interface OnButtonClickedListener {
        /**
         * 接口回调，方便调用数据；
         * Callback method to be invoked when current item clicked
         * <p>
         * the index of clicked button tag
         */
        void onButtonClicked(BtnStatusBean btnStatusBean);
    }

    public void setListener(OnButtonClickedListener onButtonClickedListener) {

        this.onButtonClickedListener = onButtonClickedListener;
    }
}


