package com.autodesk.shejijia.consumer.home.widget.widget;

import android.support.v4.view.PagerAdapter;

/**
 * @ClassName: AutoInterface.java
 * @author: 蜡笔小新
 * @date: 2016-05-26 11:53
 */
public interface AutoInterface {
    void updateIndicatorView(int size);

    void setAdapter(PagerAdapter adapter);

    void startScorll();

    void endScorll();
}
