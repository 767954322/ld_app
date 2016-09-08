package com.autodesk.shejijia.shared.components.common.uielements.scrollview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.ScrollView;
import android.widget.TextView;

import com.autodesk.shejijia.shared.components.common.uielements.pulltorefresh.Pullable;

/**
 * @author yaoxuehua .
 * @version 1.0 .
 * @date 16-8-8
 * @file MyScrollView.java  .
 * @brief 自定义ScrollView,方便监听ScrollView的滑动改变
 */
public class MyScrollView extends ScrollView implements Pullable {

    private MyScrollViewListener myScrollViewListener;
    private boolean isRefresh = true;
    private boolean isLoad = false;

    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setMyScrollViewListener(MyScrollViewListener myScrollViewListener){

        this.myScrollViewListener = myScrollViewListener;

    }

    @Override
    protected boolean dispatchHoverEvent(MotionEvent event) {
        return super.dispatchHoverEvent(event);


    }

    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);

        if (myScrollViewListener != null){

            myScrollViewListener.onScrollChange(this,x,y,oldx,oldy);

        }

    }
    //设置是否能刷新
    public void setIsRefresh(boolean isRefresh){

        this.isRefresh = isRefresh;
    }

    public void setIsLoad(boolean isLoad){

        this.isLoad = isLoad;
    }

    @Override
    public boolean canPullDown() {
//        if (getCount() == 0)
//        {
//            // 没有item的时候也可以下拉刷新
//            return true;
//        } else // 滑到ListView的顶部了
//            return getFirstVisiblePosition() == 0
//                    && getChildAt(0).getTop() >= 0;


        return isRefresh;
    }

    @Override
    public boolean canPullUp() {
//        if (getCount() == 0)
//        {
//            // 没有item的时候也可以上拉加载
//            return true;
//        } else if (getLastVisiblePosition() == (getCount() - 1))
//        {
//            // 滑到底部了
//            if (getChildAt(getLastVisiblePosition() - getFirstVisiblePosition()) != null
//                    && getChildAt(
//                    getLastVisiblePosition()
//                            - getFirstVisiblePosition()).getBottom() <= getMeasuredHeight())
//                return true;
//        }
        return isLoad;
    }
}
