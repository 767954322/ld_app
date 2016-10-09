
package com.autodesk.shejijia.consumer.uielements;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * @author   Malidong .
 * @version  v1.0 .
 * @date       2016-6-13 .
 * @file          NoScrollGridView.java .
 * @brief       Rewrite GridView  .
 */
public class NoScrollGridView extends GridView {
    public NoScrollGridView(Context context) {
        super(context);
    }

    public NoScrollGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
} 
