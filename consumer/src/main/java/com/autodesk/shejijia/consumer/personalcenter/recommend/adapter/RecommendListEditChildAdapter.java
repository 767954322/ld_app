package com.autodesk.shejijia.consumer.personalcenter.recommend.adapter;

import android.app.Activity;

import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.RecommendBrandsBean;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.RecommendSCFDBean;
import com.autodesk.shejijia.consumer.personalcenter.resdecoration.listviewdelegate.MultiItemTypeAdapter;

import java.util.List;

/**
 * 编辑清单页面第二级,3d和普通订单分离器
 *
 * @author liuhea
 *         created at 16-10-25
 *         extends MultiItemTypeAdapter<DecorationNeedsListBean>
 */
public class RecommendListEditChildAdapter extends MultiItemTypeAdapter<RecommendBrandsBean> {

    public RecommendListEditChildAdapter(Activity activity, List<RecommendBrandsBean> datas, RecommendSCFDBean recommendSCFDBean) {
        super(activity, datas);
        /**
         * 基于BOM单的推荐清单详情
         */
        addItemViewDelegate(new RecommendListEdit3DDelegate(activity,recommendSCFDBean));

        /**
         * 新建推荐清单详情
         */
        addItemViewDelegate(new RecommendListEditNewDelegate(activity,recommendSCFDBean));
    }
}
