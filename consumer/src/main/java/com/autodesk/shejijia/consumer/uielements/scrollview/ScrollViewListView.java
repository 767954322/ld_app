package com.autodesk.shejijia.consumer.uielements.scrollview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by yaoxuehua on 16-9-3.
 */
public class ScrollViewListView extends ListView {

    private Context context;
    public ScrollViewListView(Context context) {
        super(context);
        this.context =context;
    }

    public ScrollViewListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context =context;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //重新设置高度
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

}
