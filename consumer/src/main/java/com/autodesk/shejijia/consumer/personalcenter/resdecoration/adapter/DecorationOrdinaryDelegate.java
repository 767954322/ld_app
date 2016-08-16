package com.autodesk.shejijia.consumer.personalcenter.resdecoration.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.personalcenter.consumer.entity.DecorationNeedsListBean;
import com.autodesk.shejijia.consumer.personalcenter.resdecoration.activity.DecorationBidderActivity;
import com.autodesk.shejijia.consumer.personalcenter.resdecoration.activity.DecorationDetailActivity;
import com.autodesk.shejijia.consumer.personalcenter.resdecoration.entity.DecorationBiddersBean;
import com.autodesk.shejijia.consumer.personalcenter.resdecoration.listviewdelegate.ItemViewDelegate;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.framework.adapter.CommonViewHolder;

import java.util.ArrayList;

/**
 * <p>Description:家装订单:普通家装订单 </p>
 *
 * @author liuhea
 * @date 16/8/16
 *  
 */
public class DecorationOrdinaryDelegate implements ItemViewDelegate<DecorationNeedsListBean> {

    ///is_beishu:0 北舒套餐 1 非北舒.
    private static final String IS_NOT_BEI_SHU = "1";
    private DecorationDesignerListAdapter mDecorationDesignerListAdapter;
    private Activity mActivity;
    private Intent mIntent;

    public DecorationOrdinaryDelegate(Activity activity) {
        mActivity = activity;

    }

    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_decoration_list;
    }

    @Override
    public boolean isForViewType(DecorationNeedsListBean needsListBean, int position) {
        return IS_NOT_BEI_SHU.equals(needsListBean.getIs_beishu());
    }

    @Override
    public void convert(CommonViewHolder holder, DecorationNeedsListBean decorationNeedsListBean, int position) {

        final ArrayList<DecorationBiddersBean> mBidders = (ArrayList<DecorationBiddersBean>) decorationNeedsListBean.getBidders();

        final String mNeeds_id = decorationNeedsListBean.getNeeds_id();

        String contacts_name = decorationNeedsListBean.getContacts_name();
        String community_name = decorationNeedsListBean.getCommunity_name();

        String province_name = decorationNeedsListBean.getProvince_name();
        String city_name = decorationNeedsListBean.getCity_name();
        String district_name = decorationNeedsListBean.getDistrict_name();

        holder.setText(R.id.tv_decoration_name, contacts_name + "/" + community_name);
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
        ListView mDesignerListView = holder.getView(R.id.lv_decoration_bid);
        if (null == mDecorationDesignerListAdapter) {
            mDecorationDesignerListAdapter = new DecorationDesignerListAdapter(mActivity, mBidders, mNeeds_id);
        }
        mDesignerListView.setAdapter(mDecorationDesignerListAdapter);

        holder.setOnClickListener(R.id.rl_bidder_count, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIntent = new Intent(mActivity, DecorationBidderActivity.class);
                mIntent.putExtra(DecorationBidderActivity.BIDDER_KEY, mBidders);
                mIntent.putExtra(Constant.ConsumerDecorationFragment.NEED_ID, mNeeds_id);
                mActivity.startActivity(mIntent);
            }
        });
        holder.setOnClickListener(R.id.tv_decoration_detail, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIntent = new Intent(mActivity, DecorationDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(Constant.ConsumerDecorationFragment.NEED_ID, mNeeds_id);
                mIntent.putExtras(bundle);
                mActivity.startActivityForResult(mIntent, 0);
            }
        });
    }

}
