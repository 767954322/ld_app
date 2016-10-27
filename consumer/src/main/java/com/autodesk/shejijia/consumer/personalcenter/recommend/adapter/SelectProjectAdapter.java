package com.autodesk.shejijia.consumer.personalcenter.recommend.adapter;

import android.content.Context;

import com.autodesk.shejijia.consumer.base.adapter.CommonAdapter;
import com.autodesk.shejijia.consumer.base.adapter.CommonViewHolder;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.RecommendEntity;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.SelectProjectEntity;

import java.util.List;


/**
 * Created by xueqiudong on 16-10-25.
 */

public class SelectProjectAdapter extends CommonAdapter<RecommendEntity.ItemsBean> {


    public SelectProjectAdapter(Context context, List<RecommendEntity.ItemsBean> datas) {
        super(context, datas, android.R.layout.simple_list_item_single_choice);

    }

    @Override
    public void convert(final CommonViewHolder holder, RecommendEntity.ItemsBean itemsBean) {
        holder.setText(android.R.id.text1, itemsBean.getCommunity_name());

    }
}
