package com.autodesk.shejijia.shared.components.common.uielements;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

/**
 * Created by t_xuz on 12/10/16.
 * 该swipeRefreshLayout是为了解决嵌套子view时出现的子view的水平方向与其竖直方向出现的滑动冲突问题
 */

public class SwipeRefreshLayout extends android.support.v4.widget.SwipeRefreshLayout {

    private int mLastX;
    private int mLastY;
    private int mTouchSlop;
    private boolean mIsChildViewDrag;// 记录子控件是否拖拽的标记

    public SwipeRefreshLayout(Context context) {
        this(context, null);
    }

    public SwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mIsChildViewDrag = false;
                break;
            case MotionEvent.ACTION_MOVE:
                if (mIsChildViewDrag) {
                    return false;
                }
                int distanceX = Math.abs(x - mLastX);
                int distanceY = Math.abs(y - mLastY);
                if (distanceX > mTouchSlop && distanceX > distanceY) {//水平方向滑动距离大于竖直方向的话，就将事件交给子 view处理
                    mIsChildViewDrag = true;
                    return false;
                }
                break;
            case MotionEvent.ACTION_UP:
                mIsChildViewDrag = false;
                break;
        }
        mLastX = x;
        mLastY = y;

        return super.onInterceptTouchEvent(event);
    }
}
