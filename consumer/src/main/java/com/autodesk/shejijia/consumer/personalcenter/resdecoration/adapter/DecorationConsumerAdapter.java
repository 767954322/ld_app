package com.autodesk.shejijia.consumer.personalcenter.resdecoration.adapter;

import android.app.Activity;

import com.autodesk.shejijia.consumer.personalcenter.consumer.entity.DecorationNeedsListBean;
import com.autodesk.shejijia.consumer.personalcenter.resdecoration.listviewdelegate.MultiItemTypeAdapter;

import java.util.List;

/**
 * @author he.liu .
 * @version v1.0 .
 * @date 2016-8-15 .
 * @file DecorationConsumerAdapter.java .
 * @brief 消费者家装订单适配器 .
 */
public class DecorationConsumerAdapter extends MultiItemTypeAdapter<DecorationNeedsListBean> {

    public DecorationConsumerAdapter(Activity activity, List<DecorationNeedsListBean> datas) {
        super(activity, datas);

        /**
         * 套餐订单item
         */
        addItemViewDelegate(new DecorationBeiShuDelegate(activity));

        /**
         * 竞优订单item
         */
        addItemViewDelegate(new DecorationOrdinaryDelegate(activity));
    }
}
