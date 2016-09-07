package com.autodesk.shejijia.consumer.home.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ListView;

/**
 * Created by t_xuz on 9/7/16.
 * 拦截事件处理
 */
public class ScrollListView extends ListView {
    private int mLastMotionX;
    private int mLastMotionY;

    public ScrollListView(Context context) {
        super(context);
    }

    public ScrollListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
/*        int y = (int) ev.getY();
        int x = (int) ev.getX();
        boolean isIntercept = false;
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastMotionY = y;
                mLastMotionX = x;
                isIntercept = false;
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaY = y - mLastMotionY;
                int deltaX = x - mLastMotionX;
                if (Math.abs(deltaX) > Math.abs(deltaY)) {
                    isIntercept = false;
                } else {
                    isIntercept = true;
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }
        Log.e("isIntercept---",isIntercept+"");*/
        return true;
    }
}
