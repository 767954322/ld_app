package com.autodesk.shejijia.consumer.personalcenter.recommend.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Printer;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.BtnStatusBean;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.MaterialCategoryBean;
import com.autodesk.shejijia.consumer.utils.ToastUtil;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;

import java.util.List;

/**
 * Created by yaoxuehua on 16-10-26.
 */

public class DynamicPopWindow extends PopupWindow {

    private Activity context;
    private int countOffset = 0;
    private String[] arr;
    private onButtonPopWindowClickedListener onButtonClickedListener;
    private int singleClickOrDoubleBtnCount = 1;
    private LinearLayout containView;
    private String[] arrStringTotal;
    private TextView[] textViews;
    private int height;//该控件宽度
    private int width;//该控件高度
    private static double NO_CHANGE_HEIGHT = 1187.00d;
    private static double ADAPTER_COUNT = 1.00d;
    public boolean currentStatus = false;//当前开闭的状态

    public DynamicPopWindow(Activity context) {
        super(context);
        this.context = context;
        init();
    }

    public DynamicPopWindow(Activity context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    public void init() {

        View view = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.popwindow_layout, null);
        view.setBackground(null);
        containView = (LinearLayout) view.findViewById(R.id.containView);
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.gray_bg);
        linearLayout.getBackground().setAlpha(150);
        height = context.getWindowManager().getDefaultDisplay().getHeight();
        width = context.getWindowManager().getDefaultDisplay().getWidth();
        ADAPTER_COUNT = (double) height / NO_CHANGE_HEIGHT;
        this.setContentView(view);
        this.setWidth(width);
        this.setHeight(height);
//        this.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
    }

    /**
     * 添加数据
     */
    public void addDataForView(List<MaterialCategoryBean.Categories3dBean.SubCategoryBean> arr) {

        MaterialCategoryBean.Categories3dBean.SubCategoryBean subCategoryBean;
        arrStringTotal = new String[arr.size()];
        for (int i = 0; i < arr.size(); i++) {

            arrStringTotal[i] = arr.get(i).getSub_category_3d_name();

        }
        dynamicAddViewLinearLayout(arrStringTotal);
        containView.invalidate();
    }

    /**
     * 动态添加行数
     */
    public void dynamicAddViewLinearLayout(String[] arr) {

        textViews = new TextView[arr.length + 1];
        int countSize = arr.length % 2;
        int totalLine = 0;
        if (countSize == 0) {
            totalLine = arr.length / 2;
        } else {
            totalLine = arr.length / 2 + 1;
        }

        for (int i = 0; i < totalLine; i++) {

            LinearLayout linearLayout = new LinearLayout(context);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (80 * ADAPTER_COUNT));
            linearLayout.setLayoutParams(layoutParams);
            dynamicAddViewAndData(linearLayout, countSize, arr);
            containView.addView(linearLayout);
        }
    }


    /**
     * 动态加载数据，展示view
     */
    public void dynamicAddViewAndData(LinearLayout linearLayout, final int countSize, final String[] arr) {

        TextView textView;
        BtnStatusBean btnStatusBean;
        LinearLayout.LayoutParams layoutParamsButton = new LinearLayout.LayoutParams(width/2, (int) (80 * ADAPTER_COUNT));
        layoutParamsButton.weight = 1;
        layoutParamsButton.leftMargin = (int) (32 * ADAPTER_COUNT);
        layoutParamsButton.rightMargin = (int) (32 * ADAPTER_COUNT);

        for (int i = 0; i < 2; i++) {

            textView = new TextView(context);
            btnStatusBean = new BtnStatusBean();
            textView.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
            textView.setLayoutParams(layoutParamsButton);

            if (countSize + countOffset <= arr.length) {

                textView.setText(arr[countOffset]);

                btnStatusBean.setCountOffset(countOffset);
                btnStatusBean.setSingleClickOrDoubleBtnCount(1);
                textViews[countOffset] = textView;
                textView.setTag(btnStatusBean);
                if (countOffset < arr.length) {

                    countOffset++;
                }
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        BtnStatusBean btnStatusBean = (BtnStatusBean) v.getTag();
                        onButtonClickedListener.onButtonOnClick(btnStatusBean);
                        changeTextViewBackgroudAndText(btnStatusBean, textViews[btnStatusBean.getCountOffset()]);
                        for (int i = 0; i < textViews.length - 1; i++) {

                            if (i != btnStatusBean.getCountOffset()) {
                                changeTextViewBackgroudAndTextUnChecked(btnStatusBean, textViews[i]);
                            }

                        }

                        dismiss();
                    }
                });
            } else {

                textView.setAlpha(0);
            }

            linearLayout.addView(textView);
        }

    }

    /**
     * 改变按钮背景，字体颜色
     */
    public void changeTextViewBackgroudAndText(BtnStatusBean btnStatusBean, TextView textView) {


        textView.setTextColor(UIUtils.getColor(R.color.bg_0084ff));


    }

    public void changeTextViewBackgroudAndTextUnChecked(BtnStatusBean btnStatusBean, TextView textView) {

        textView.setTextColor(UIUtils.getColor(R.color.text_item_name));
    }

    /**
     * 外界动态改变选中按钮
     * */
    public void setButtonCheckedStatus(BtnStatusBean btnStatusBean){

        changeTextViewBackgroudAndText(btnStatusBean, textViews[btnStatusBean.getCountOffset()]);
        for (int i = 0; i < textViews.length - 1; i++) {

            if (i != btnStatusBean.getCountOffset()) {
                changeTextViewBackgroudAndTextUnChecked(btnStatusBean, textViews[i]);
            }

        }

    }

    /**
     * 定义接口回调
     */
    public interface onButtonPopWindowClickedListener {

        void onButtonOnClick(BtnStatusBean btnStatusBean);

    }

    /**
     * 接口实现方法
     */
    public void setListener(onButtonPopWindowClickedListener onButtonClickedListener) {

        this.onButtonClickedListener = onButtonClickedListener;
    }

    /**
     * 弹出
     */
    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
            currentStatus = true;
            this.showAsDropDown(parent, parent.getLayoutParams().width / 2, width /50);
//            backgroundAlpha(0.5F);
        } else {
            currentStatus = false;
            this.dismiss();
//            backgroundAlpha(1.0F);
        }
    }

    /**
     * 设置添加屏幕的背景透明度
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha)
    {
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        context.getWindow().setAttributes(lp);
    }
}
