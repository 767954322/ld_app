package com.autodesk.shejijia.consumer.uielements.scrollview;

import android.app.Activity;
import android.content.Context;
import android.text.Layout;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by yaoxuehua on 16-9-3.
 *
 * 当scrolview嵌套listview时，如果item中有多个TextView，且TextView的字数较多，会出现，之计算一行的错误，
 * 所以要重写TextView的计算高度的方法
 */
public class ScrollViewTextView extends TextView {

    private Context context;

    public ScrollViewTextView(Context context) {
        super(context);
        this.context = context;
    }

    public ScrollViewTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);


        Layout layout = getLayout();
        if (layout != null) {
            int height = (int) (Math.ceil(getMaxLineHeight(this.getText().toString()))
                                + getCompoundPaddingTop() + getCompoundPaddingBottom());
            int width = getMeasuredWidth();
            setMeasuredDimension(width, height);
        }

    }


    private float getMaxLineHeight(String str) {
        float height = 0.0f;
        float screenW = ((Activity)context).getWindowManager().getDefaultDisplay().getWidth();
        float paddingLeft = ((LinearLayout)this.getParent()).getPaddingLeft();
        float paddingReft = ((LinearLayout)this.getParent()).getPaddingRight();
//这里具体this.getPaint()要注意使用，要看你的TextView在什么位置，这个是拿TextView父控件的Padding的，为了更准确的算出换行
        int line = (int) Math.ceil( (this.getPaint().measureText(str)/(screenW-paddingLeft-paddingReft))); height = (this.getPaint().getFontMetrics().descent-this.getPaint().getFontMetrics().ascent)*line; return height;}
}
