package com.autodesk.shejijia.consumer.uielements.scrollview;

/**
 * @author yaoxuehua .
 * @version 1.0 .
 * @date 16-8-8
 * @file MyScrollViewListener. interface .
 * @brief 自定义ScrollView的MyScrollViewListener,方便监听ScrollView的滑动改变
 */
public interface MyScrollViewListener {

    void onScrollChange(MyScrollView scrollView, int x, int y, int oldx, int oldy);
}
