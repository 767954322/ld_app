package com.autodesk.shejijia.consumer.uielements.pulltorefresh;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.autodesk.shejijia.shared.R;

/**
 * Created by wenhulin on 9/7/16.
 */
public class PullableLayout extends LinearLayout implements Pullable{
    private PullListView mPullableListView;

    public PullableLayout(Context context) {
        super(context);
    }

    public PullableLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PullableLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mPullableListView = (PullListView) findViewById(R.id.pullable_listview);
    }

    @Override
    public boolean canPullUp() {
        return mPullableListView.canPullUp();
    }

    @Override
    public boolean canPullDown() {
        return mPullableListView.canPullDown();
    }
}
