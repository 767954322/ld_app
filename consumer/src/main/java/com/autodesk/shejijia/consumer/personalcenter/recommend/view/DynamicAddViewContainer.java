package com.autodesk.shejijia.consumer.personalcenter.recommend.view;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.BtnStatusBean;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;

/**
 * Created by yaoxuehua on 16-10-25.
 */

public class DynamicAddViewContainer extends LinearLayout {

    private Context context;
    private DynamicAddView.OnButtonClickedListener onButtonClickedListener;
    private int singleClickOrDoubleBtnCount = 1;//选btn状态
    private TextView[] textViews;

    public DynamicAddViewContainer(Context context) {
        super(context);
        this.context = context;
        setOrientation(HORIZONTAL);
        setWeightSum(2);
        setBackgroundResource(R.drawable.store_bg_btn_checked);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);
        setLayoutParams(layoutParams);

    }

    /**
     * 动态添加数据
     */
    public void dynamicAddData(String[] arr) {


    }

    /**
     * 动态创建店铺选项行数
     */
    public void dynamicCreateStoreLine(int totalLine) {


        for (int i = 0; i < totalLine; i++) {

            LinearLayout linearLayout = new LinearLayout(context);
//            linearLayout.setBackgroundResource(R.drawable.store_bg_btn_checked);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.weight = 1;
            linearLayout.setOrientation(VERTICAL);
            linearLayout.setGravity(Gravity.CENTER);
            linearLayout.setLayoutParams(layoutParams);
            dynamicAddView(linearLayout);
            addView(linearLayout);
            invalidate();
        }
    }

    /**
     * 动态添加控件
     */
    public void dynamicAddView(LinearLayout linearLayout) {

        LinearLayout.LayoutParams layoutParamsButton = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParamsButton.weight= 1;
        LinearLayout.LayoutParams layoutParamsContainer = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 110);

        TextView textView = new TextView(context);
        textView.setText("主材");
        textView.setGravity(Gravity.CENTER);
        textView.setLayoutParams(layoutParamsButton);
        textView.setTextColor(UIUtils.getColor(R.color.bg_0084ff));
//        textView.setBackgroundResource(R.drawable.store_bg_btn_checked);
        linearLayout.addView(textView);

        TextView textView1 = new TextView(context);
        textView1.setLayoutParams(layoutParamsContainer);
        textView1.setText("222");
        linearLayout.addView(textView1);

    }


    /**
     * 动态添加控件（BUTTON / TEXTVIEW）
     */
    public void dynamicAddView(int count) {

        TextView textView;
        BtnStatusBean btnStatusBean;
        LinearLayout.LayoutParams layoutParamsButton = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParamsButton.leftMargin = 26;
        layoutParamsButton.rightMargin = 26;
        layoutParamsButton.topMargin = 22;
        layoutParamsButton.bottomMargin = 22;

        textViews = new TextView[5];
        for (int i = 0; i < 2; i++) {
            textView = new TextView(context);
            btnStatusBean = new BtnStatusBean();
            textViews[i] = textView;
            btnStatusBean.setCountOffset(i);
            btnStatusBean.setSingleClickOrDoubleBtnCount(1);
            textView.setTag(btnStatusBean);
            textView.setText("主材");
            textView.setTextColor(UIUtils.getColor(R.color.text_item_name));
            textView.setGravity(Gravity.CENTER);
            textView.setMinWidth(200);
            textView.setPadding(40, 0, 40, 0);
            textView.setBackgroundResource(R.drawable.material_add_bg);
            textView.setLayoutParams(layoutParamsButton);
            textViews[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BtnStatusBean btnStatusBean = (BtnStatusBean) v.getTag();
//                    onButtonClickedListener.onButtonClicked(btnStatusBean.getCountOffset());
                    changeTextViewBackgroudAndText(btnStatusBean, textViews[btnStatusBean.getCountOffset()]);
//                    textViews[btnStatusBean.getCountOffset()].setTextColor(UIUtils.getColor(R.color.bg_0084ff));
//                    textViews[btnStatusBean.getCountOffset()].setBackgroundResource(R.drawable.store_bg_btn_checked);
                    for (int i = 0; i < textViews.length; i++) {

                        if (i != btnStatusBean.getCountOffset()) {

//                            textViews[i].setTextColor(UIUtils.getColor(R.color.text_item_name));
//                            textViews[i].setBackgroundResource(R.drawable.material_add_bg);
                            changeTextViewBackgroudAndTextUnChecked(btnStatusBean, textViews[i]);
                        }

                    }
                }
            });
            addView(textView);
        }

        textViews[3].setText("fdfdf");

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
}
