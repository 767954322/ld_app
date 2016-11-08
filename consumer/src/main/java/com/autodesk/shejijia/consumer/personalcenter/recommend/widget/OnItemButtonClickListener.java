package com.autodesk.shejijia.consumer.personalcenter.recommend.widget;

import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.RecommendDetailsBean;

/**
 * @User: 蜡笔小新
 * @date: 16-11-8
 * @GitHub: https://github.com/meikoz
 */

public interface OnItemButtonClickListener {
    void onItemDeteleOnClick(String text, RecommendDetailsBean item);

    void onItemReturnOnClick(String text, RecommendDetailsBean item);
}
