package com.autodesk.shejijia.consumer.personalcenter.recommend.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

import com.autodesk.shejijia.consumer.uielements.scrollview.MyScrollView;
import com.autodesk.shejijia.consumer.uielements.scrollview.MyScrollViewListener;

/**
 * Created by yaoxuehua on 16-11-10.
 */

public class DynamicScrollView extends HorizontalScrollView {

    private MyScrollViewListener myScrollViewListener;
    private Context context;
    private int currentItemLocation = 0;
    private boolean isLastItem;
    private int backLocation = 0;//item从最后末端回来的适合位置
    private int onePx = 160;
    private int width;//该控件高度
    private int height;//该控件宽度
    private static double NO_CHANGE_HEIGHT = 1187.00d;
    private static double ADAPTER_COUNT = 1.00d;

    public DynamicScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public void setMyScrollViewListener(MyScrollViewListener myScrollViewListener) {
        this.myScrollViewListener = myScrollViewListener;
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);

        if (myScrollViewListener != null) {
            myScrollViewListener.onScrollChange(this, x, y, oldx, oldy);
        }
    }

    /**
     * 设置适配
     */
    public void setAdapterForItem(int height) {
        ADAPTER_COUNT = (double) height / NO_CHANGE_HEIGHT;
        onePx = (int) (onePx * ADAPTER_COUNT);
    }


    /**
     * 根据当前距离，进行滑动
     */
    public void useCurrentDistanceScroll(int x, int currentDistance, int scrollHeight, int scrollAtOneHeight, int totalCount, int currentClickItem) {

        if (currentDistance == 0) {
            //第一次进入滑动
            scrollBy(x - onePx, 0);
        } else {
            //从第二次开始滑动记录
            if (x > currentItemLocation) {
                //向右滑动
                scrollBy(x - currentDistance - onePx, 0);
            } else {
                //向做滑动
                scrollBy(x - currentItemLocation - onePx, 0);
                //判断是否到底
                if (scrollAtOneHeight - x < scrollHeight) {
                } else {
                    currentItemLocation = x - onePx;
                }
            }
        }
    }

    /**
     * @author yaoxuehua .
     * @version 1.0 .
     * @date 16-8-8
     * @file MyScrollViewListener. interface .
     * @brief 自定义ScrollView的MyScrollViewListener, 方便监听ScrollView的滑动改变
     */
    public interface MyScrollViewListener {
        void onScrollChange(DynamicScrollView scrollView, int x, int y, int oldx, int oldy);
    }
}
