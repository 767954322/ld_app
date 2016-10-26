package com.autodesk.shejijia.consumer.personalcenter.recommend.view;

import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.RecommendEntity;

/**
 * @User: 蜡笔小新
 * @date: 16-10-26
 * @GitHub: https://github.com/meikoz
 */

public interface RecommendView {
    void onLoadDataSuccess(int offset, RecommendEntity entity);

    void onLoadFailer();
}
