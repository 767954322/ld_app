package com.autodesk.shejijia.consumer.personalcenter.recommend.view;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.BtnStatusBean;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.MaterialCategoryBean;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;

import java.util.List;


/**
 * @author yaoxuehua .
 * @version v1.0 .
 * @date 16-10-25 .
 * @file DynamicAddView.java .
 * @brief 动态添加按钮.====二级品类展示
 */
public class DynamicAddView extends LinearLayout {
    private Activity context;
    private OnButtonClickedListener onButtonClickedListener;
    private int singleClickOrDoubleBtnCount = 1;//选btn状态
    private String[] arrStringTotal;
    private TextView[] textViews;
    private int height;//该控件宽度
    private int width;//该控件高度
    private int[] locationArr = new int[5];//item位置坐标，分别时左，上，右，下，宽度

    private class T {
    }

    public DynamicAddView(Activity context) {
        super(context);
        this.context = context;
        setOrientation(HORIZONTAL);
        height = context.getWindowManager().getDefaultDisplay().getHeight();
        width = context.getWindowManager().getDefaultDisplay().getWidth();
    }

    /**
     * 动态添加数据
     *
     * @param arr
     */
    public void dynamicAddData(List<MaterialCategoryBean.Categories3dBean.SubCategoryBean> arr) {

        MaterialCategoryBean.Categories3dBean.SubCategoryBean subCategoryBean;
        arrStringTotal = new String[arr.size()];
        for (int i = 0; i < arr.size(); i++) {

            arrStringTotal[i] = arr.get(i).getSub_category_3d_name();

        }
        dynamicAddView(arrStringTotal.length);
    }

    /**
     * 动态添加控件（BUTTON / TEXTVIEW）
     */
    public void dynamicAddView(int count) {

        TextView textView;
        BtnStatusBean btnStatusBean;
        LinearLayout.LayoutParams layoutParamsButton = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParamsButton.leftMargin = width / 50;
        layoutParamsButton.rightMargin = width / 50;
        layoutParamsButton.topMargin = width / 50;
        layoutParamsButton.bottomMargin = width / 50;

        textViews = new TextView[count];
        for (int i = 0; i < count; i++) {
            textView = new TextView(context);
            btnStatusBean = new BtnStatusBean();
            textViews[i] = textView;
            btnStatusBean.setCountOffset(i);
            btnStatusBean.setSingleClickOrDoubleBtnCount(1);
            textView.setTag(btnStatusBean);
            textView.setText(arrStringTotal[i]);
            textView.setTextColor(UIUtils.getColor(R.color.text_item_name));
            textView.setGravity(Gravity.CENTER);
            textView.setMinWidth(width / 6);
            textView.setPadding(width / 25, 0, width / 25, 0);
            textView.setBackgroundResource(R.drawable.material_add_bg);
            textView.setLayoutParams(layoutParamsButton);
            textViews[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BtnStatusBean btnStatusBean = (BtnStatusBean) v.getTag();
                    if (onButtonClickedListener != null) {

                        onButtonClickedListener.onButtonClicked(btnStatusBean);
                        int x = (int) v.getX();
                        int y = (int) v.getY();
                        int xForRight = v.getRight();
                        int width = v.getWidth();
                        onButtonClickedListener.onGetCurrentClickLocation(x, y, xForRight, width, btnStatusBean);
                    }
                    changeTextViewBackgroudAndText(btnStatusBean, textViews[btnStatusBean.getCountOffset()]);
                    for (int i = 0; i < textViews.length; i++) {

                        if (i != btnStatusBean.getCountOffset()) {
                            changeTextViewBackgroudAndTextUnChecked(btnStatusBean, textViews[i]);
                        }

                    }
                }
            });
            addView(textView);
        }


        invalidate();

    }

    /**
     * 外界获取该控件内部item的位置
     */
    public int[] getLocationNumber(int item) {

        View v = textViews[item];
        int x = (int) v.getX();
        int y = (int) v.getY();
        int xForRight = v.getRight();
        int width = v.getWidth();
        locationArr[0] = x;
        locationArr[2] = xForRight;
        locationArr[4] = width;

        return locationArr;
    }

    /**
     * 外界获取该控件的item数量
     */
    public int getItemCount() {

        return textViews.length;
    }


    /**
     * 外界动态改变按钮选中状态
     */
    public void setButtonCheckedStatus(BtnStatusBean btnStatusBean) {

        changeTextViewBackgroudAndText(btnStatusBean, textViews[btnStatusBean.getCountOffset()]);
        for (int i = 0; i < textViews.length; i++) {

            if (i != btnStatusBean.getCountOffset()) {
                changeTextViewBackgroudAndTextUnChecked(btnStatusBean, textViews[i]);
            }

        }
        invalidate();

    }

    /**
     * 改变按钮背景，字体颜色
     */
    public void changeTextViewBackgroudAndText(BtnStatusBean btnStatusBean, TextView textView) {


        textView.setTextColor(UIUtils.getColor(R.color.bg_0084ff));
        textView.setBackgroundResource(R.drawable.store_bg_btn_checked);


    }

    public void changeTextViewBackgroudAndTextUnChecked(BtnStatusBean btnStatusBean, TextView textView) {

        textView.setTextColor(UIUtils.getColor(R.color.text_item_name));
        textView.setBackgroundResource(R.drawable.material_add_bg);
        invalidate();
    }

    public interface OnButtonClickedListener {
        /**
         * 接口回调，方便调用数据；
         * Callback method to be invoked when current item clicked
         * <p>
         * BtnStatusBean the index of clicked button tag
         */
        void onButtonClicked(BtnStatusBean btnStatusBean);

        void onGetCurrentClickLocation(int x, int y, int xForRight, int width, BtnStatusBean btnStatusBean);
    }

    public void setListener(OnButtonClickedListener onButtonClickedListener) {

        this.onButtonClickedListener = onButtonClickedListener;
    }
}
