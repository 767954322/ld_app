package com.autodesk.shejijia.consumer.uielements;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * @author   he.liu .
 * @version  v1.0 .
 * @date       2016-6-12 .
 * @file          ListViewForScrollView.java .
 * @brief       ListView中嵌套ScrollView,导致滑动失效,自定义控件 .
 */
public class ListViewForScrollView extends ListView {

    public ListViewForScrollView(Context context) {
        super(context);
    }

    public ListViewForScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ListViewForScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    /**
     * 重写该方法，达到使ListView适应ScrollView的效果
     */
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
