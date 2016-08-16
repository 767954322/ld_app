package com.autodesk.shejijia.consumer.personalcenter.consumer.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.personalcenter.consumer.activity.DecorationBidderActivity;
import com.autodesk.shejijia.consumer.personalcenter.consumer.activity.DecorationDetailActivity;
import com.autodesk.shejijia.consumer.personalcenter.consumer.entity.DecorationBiddersBean;
import com.autodesk.shejijia.consumer.personalcenter.consumer.entity.DecorationNeedsListBean;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.framework.adapter.CommonAdapter;
import com.autodesk.shejijia.shared.framework.adapter.CommonViewHolder;

import java.util.List;

/**
 * @author he.liu .
 * @version v1.0 .
 * @date 2016-8-15 .
 * @file ConsumerDecorationAdapter.java .
 * @brief 消费者家装订单适配器 .
 */
public class ConsumerDecorationAdapter extends CommonAdapter<DecorationNeedsListBean> implements
        View.OnClickListener {

    private Activity mActivity;
    private List<DecorationNeedsListBean> mDecorationNeedsList;
    private String mNeeds_id;
    private ListView mDesignerListView;
    private DecorationDesignerListAdapter mDecorationDesignerListAdapter;
    private Intent mIntent;

    public ConsumerDecorationAdapter(Activity activity, List<DecorationNeedsListBean> datas) {
        super(activity, datas, R.layout.fragment_consumer_decoration);
        mActivity = activity;
        mDecorationNeedsList = datas;
    }

    @Override
    public void convert(CommonViewHolder holder, DecorationNeedsListBean decorationNeedsListBean) {
        String province_name = decorationNeedsListBean.getProvince_name();

        String city_name = decorationNeedsListBean.getCity_name();
        String district_name = decorationNeedsListBean.getDistrict_name();
        mNeeds_id = decorationNeedsListBean.getNeeds_id();

        List<DecorationBiddersBean> bidders = decorationNeedsListBean.getBidders();
        holder.setText(R.id.tv_decoration_name, decorationNeedsListBean.getCommunity_name());
        holder.setText(R.id.tv_decoration_needs_id, decorationNeedsListBean.getNeeds_id());
        holder.setText(R.id.tv_decoration_house_type, decorationNeedsListBean.getHouse_type());
        holder.setText(R.id.tv_decoration_address, province_name + city_name + district_name);
        holder.setText(R.id.tv_decoration_phone, decorationNeedsListBean.getContacts_mobile());
        holder.setText(R.id.tv_decoration_style, decorationNeedsListBean.getDecoration_style());
        holder.setText(R.id.tv_bidder_count, decorationNeedsListBean.getBidder_count() + "人");
        holder.setText(R.id.tv_decoration_end_day, " " + decorationNeedsListBean.getEnd_day() + " 天");

        /**
         * 订单状态
         */
        holder.setText(R.id.tv_decoration_state, decorationNeedsListBean.getIs_public());

        /**
         * 应标设计师列表
         */
        mDesignerListView = holder.getView(R.id.lv_decoration_bid);
        if (null == mDecorationDesignerListAdapter) {
            mDecorationDesignerListAdapter = new DecorationDesignerListAdapter(mActivity, bidders);
        }
        mDesignerListView.setAdapter(mDecorationDesignerListAdapter);

        holder.setOnClickListener(R.id.rl_bidder_count, this);
        holder.setOnClickListener(R.id.tv_decoration_detail, this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_bidder_count:
                mIntent = new Intent(mContext, DecorationBidderActivity.class);
                mActivity.startActivity(mIntent);
                break;

            case R.id.tv_decoration_detail:
                mIntent = new Intent(mActivity, DecorationDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(Constant.ConsumerDecorationFragment.NEED_ID, mNeeds_id);
                mIntent.putExtras(bundle);
                mActivity.startActivityForResult(mIntent, 0);
                break;
        }
    }

}
