package com.autodesk.shejijia.consumer.personalcenter.recommend.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.base.adapter.CommonAdapter;
import com.autodesk.shejijia.consumer.base.adapter.CommonViewHolder;
import com.autodesk.shejijia.consumer.personalcenter.recommend.activity.RecommendListDetailActivity;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.RecommendDetailsBean;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.AlertView;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.OnItemClickListener;
import com.autodesk.shejijia.shared.components.common.utility.DateUtil;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;

import java.util.Date;
import java.util.List;

/**
 * @User: 蜡笔小新
 * @date: 16-10-21
 * @GitHub: https://github.com/meikoz
 */

public class RecommendAdapter extends CommonAdapter<RecommendDetailsBean> {
    private boolean isDesiner = false;

    public RecommendAdapter(Context context, List<RecommendDetailsBean> datas, int layoutId, boolean isDesiner) {
        super(context, datas, layoutId);
        this.isDesiner = isDesiner;
    }

    @Override
    public void convert(CommonViewHolder holder, final RecommendDetailsBean item) {
        holder.setVisible(R.id.tv_cancel_btn, isDesiner);
        String status = item.getSent_status();
        if (!TextUtils.isEmpty(status)) {
            boolean unsent = status.equals("unsent");
            holder.setVisible(R.id.iv_reco_wfsico, unsent);
        }
        holder.setText(R.id.tv_edit_btn, (isDesiner ? "编辑" : "删除"));
        holder.setText(R.id.tv_recommend_name, item.getCommunity_name());
        holder.setText(R.id.tv_asset_id, "清单编号：" + item.getProject_code() + "");
        holder.setText(R.id.tv_reco_consumer_name, item.getConsumer_name());
        holder.setText(R.id.tv_reco_consumer_mobile, item.getConsumer_mobile());
        holder.setText(R.id.tv_reco_item_address, item.getProvince_name() + item.getCity_name() + item.getDistrict_name());
        holder.setText(R.id.tv_reco_item_details_address, item.getCommunity_address());
        holder.setText(R.id.tv_create_date, DateUtil.getStringDateByFormat(new Date(item.getDate_submitted()), "yyyy-MM-dd HH:mm"));
        holder.setOnClickListener(R.id.tv_edit_btn, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isDesiner) {
                    RecommendListDetailActivity.actionStartActivity(mContext, item.getDesign_project_id() + "");
                } else {
                    onItemDeteleClick(item);
                }
            }
        });
    }

    private void onItemDeteleClick(RecommendDetailsBean item) {
        new AlertView(null, "您确定要删除吗?", "取消", null, new String[]{UIUtils.getString(R.string.sure)}, mContext, AlertView.Style.Alert, new OnItemClickListener() {
            @Override
            public void onItemClick(Object object, int position) {
                Log.d("recommend", "确定");
            }
        }).show();
    }
}
