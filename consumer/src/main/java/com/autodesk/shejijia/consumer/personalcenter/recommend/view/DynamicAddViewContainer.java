package com.autodesk.shejijia.consumer.personalcenter.recommend.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.BtnStatusBean;
import com.autodesk.shejijia.consumer.utils.ToastUtil;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;

/**
 * Created by yaoxuehua on 16-10-25.
 */

public class DynamicAddViewContainer extends LinearLayout {

    private Activity context;
    private OnButtonClickedListener onButtonClickedListener;
    private int singleClickOrDoubleBtnCount = 1;//选btn状态
    private TextView[] textViews;
    private String[] arr;
    private int height;//该控件宽度
    private int width;//该控件高度
    private double widthDouble = 0.00d;
    private static double NO_CHANGE_HEIGHT = 1184.00d;
    private static double ADAPTER_COUNT = 1.00d;
    private int heightParent = 0;//父控件高度
    private int widthLine ;// 画线宽度
    private int heightLine;//线高度
    private int startXLine;//画线起始横坐标
    private int StartYLine;//画线起始纵坐标
    private int endXLine;//画线结束横坐标
    private int endYLine;//画线结束纵坐标
    private int countNumber = 2;//判断是点击的哪一个item
    private int widthOne = 0;//子类宽度

    public DynamicAddViewContainer(Activity context, int heightParent) {
        super(context);
        this.context = context;
        this.heightParent = heightParent;
        setOrientation(HORIZONTAL);
        setBackgroundResource(R.drawable.material_one_category_bg);
        height = context.getWindowManager().getDefaultDisplay().getHeight();
        width = context.getWindowManager().getDefaultDisplay().getWidth();
        widthDouble = width / 3;
        ADAPTER_COUNT = height / NO_CHANGE_HEIGHT;
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        setLayoutParams(layoutParams);

    }

    /**
     * 动态添加数据
     */
    public void dynamicAddData(String[] arr) {

        this.arr = arr;
        dynamicCreateStoreLine(arr.length);
    }

    /**
     * 动态创建店铺选项行数
     */
    public void dynamicCreateStoreLine(int totalLine) {

        textViews = new TextView[totalLine];
        for (int i = 0; i < totalLine; i++) {
            LinearLayout linearLayout = new LinearLayout(context);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams((int) widthDouble, (int) heightParent * 105 / 120);
            linearLayout.setGravity(Gravity.CENTER);
            layoutParams.weight = 1;
            linearLayout.setLayoutParams(layoutParams);
            dynamicAddView(linearLayout, 1, i);
            addView(linearLayout);

        }
        invalidate();
        getWidthAndLocation(0);
    }


    /**
     * 动态添加控件（BUTTON / TEXTVIEW）
     */
    public void dynamicAddView(LinearLayout linearLayout, int count, int numberCount) {

        TextView textView;
        BtnStatusBean btnStatusBean;
        LinearLayout.LayoutParams layoutParamsButton = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);

        textView = new TextView(context);
        btnStatusBean = new BtnStatusBean();
        textViews[numberCount] = textView;
        btnStatusBean.setCountOffset(numberCount);
        btnStatusBean.setSingleClickOrDoubleBtnCount(1);
        textView.setTag(btnStatusBean);
        textView.setText(arr[numberCount]);
        textView.setTextSize(15);
        textView.setTextColor(UIUtils.getColor(R.color.text_item_name));
        textView.setGravity(Gravity.CENTER);
//        textView.setBackgroundResource(R.drawable.material_add_bg);
        textView.setLayoutParams(layoutParamsButton);
        textViews[numberCount].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BtnStatusBean btnStatusBean = (BtnStatusBean) v.getTag();
                    onButtonClickedListener.onButtonClicked(btnStatusBean);
                changeTextViewBackgroudAndText(btnStatusBean, textViews[btnStatusBean.getCountOffset()]);
                for (int i = 0; i < textViews.length; i++) {

                    if (i != btnStatusBean.getCountOffset()) {

                        changeTextViewBackgroudAndTextUnChecked(btnStatusBean, textViews[i]);
                    }

                }
            }
        });
        int spec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        textView.measure(spec, spec);
        linearLayout.addView(textView);


    }

    /**
     * 改变按钮背景，字体颜色
     */
    public void changeTextViewBackgroudAndText(BtnStatusBean btnStatusBean, TextView textView) {


        textView.setTextColor(UIUtils.getColor(R.color.bg_0084ff));
        getWidthAndLocation(btnStatusBean.getCountOffset());

        invalidate();
//        textView.setBackgroundResource(R.drawable.store_bg_btn_checked);


    }

    public void changeTextViewBackgroudAndTextUnChecked(BtnStatusBean btnStatusBean, TextView textView) {

        textView.setTextColor(UIUtils.getColor(R.color.text_item_name));
//        textView.setBackgroundResource(R.drawable.material_add_bg);
        invalidate();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    /**
     * 获取每个item宽度，方便画线
     * */
    public void getWidthAndLocation(int count){

        this.countNumber = count;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        widthOne = this.getChildAt(0).getWidth();
        startXLine = textViews[countNumber].getLeft() + widthOne * countNumber;
        widthLine = textViews[countNumber].getWidth();
        Paint paint = new Paint();
        paint.setStrokeWidth((int) heightParent * 5 / 120);
        paint.setColor(getResources().getColor(com.autodesk.shejijia.shared.R.color.my_project_title_pointer_color));
        canvas.drawLine(startXLine,(int) heightParent * 115 / 120 + 1,widthLine + startXLine,(int) heightParent * 115 / 120 + 1,paint);
        invalidate();
    }

    public interface OnButtonClickedListener {
        /**
         * 接口回调，方便调用数据；
         * Callback method to be invoked when current item clicked
         *
         *  BtnStatusBean the index of clicked button tag
         */
        void onButtonClicked(BtnStatusBean btnStatusBean);
    }

    public void setListener(OnButtonClickedListener onButtonClickedListener) {

        this.onButtonClickedListener = onButtonClickedListener;
    }
}
