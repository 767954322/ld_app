package com.autodesk.shejijia.consumer.personalcenter.recommend.widget;

import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.RecommendSCFDBean;

/**
 * Created by liuhe on 16-11-7.
 */

public interface BrandChangListener {
    /**
     * 品牌变更回调
     */
    void onBrandChangListener(RecommendSCFDBean recommendSCFDBean, String brandCode);

    /**
     * 品牌增加
     */
    void onBrandAddListener(RecommendSCFDBean recommendSCFDBean);

}
