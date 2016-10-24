package com.autodesk.shejijia.consumer.personalcenter.recommend.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.base.adapter.BaseAdapter;
import com.autodesk.shejijia.consumer.base.adapter.CommonAdapter;
import com.autodesk.shejijia.consumer.base.adapter.CommonViewHolder;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.RecommendEntity;
import com.autodesk.shejijia.shared.components.common.utility.DateUtil;

import java.util.Date;
import java.util.List;

/**
 * @User: 蜡笔小新
 * @date: 16-10-21
 * @GitHub: https://github.com/meikoz
 */

public class RecommendAdapter extends CommonAdapter<RecommendEntity.ItemsBean> {
    private boolean isDesiner = false;

    public RecommendAdapter(Context context, List<RecommendEntity.ItemsBean> datas, int layoutId, boolean isDesiner) {
        super(context, datas, layoutId);
        this.isDesiner = isDesiner;
    }

    @Override
    public void convert(CommonViewHolder holder, RecommendEntity.ItemsBean item) {
        holder.setVisible(R.id.tv_cancel_btn, isDesiner);
        if (!TextUtils.isEmpty(item.getStatus())) {
            holder.setVisible(R.id.iv_reco_wfsico, item.getStatus().equals("unsent"));
        }
        holder.setText(R.id.tv_edit_btn, (isDesiner ? "编辑" : "删除"));
        holder.setText(R.id.tv_recommend_name, item.getCommunity_name());
        holder.setText(R.id.tv_asset_id, "清单编号：" + item.getAsset_id() + "");
        holder.setText(R.id.tv_reco_consumer_name, item.getConsumer_name());
        holder.setText(R.id.tv_reco_consumer_mobile, item.getConsumer_mobile());
        holder.setText(R.id.tv_reco_item_address, "北京市朝阳区");
        holder.setText(R.id.tv_reco_item_details_address, item.getCommunity_address());
        holder.setText(R.id.tv_create_date, DateUtil.getStringDateByFormat(new Date(item.getDate_submitted()), "yyyy-MM-dd HH:mm"));
    }
}
