package com.autodesk.shejijia.consumer.personalcenter.designer.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.consumer.base.adapter.BaseAdapter;
import com.autodesk.shejijia.consumer.personalcenter.designer.entity.BidHallEntity;

import java.util.List;

/**
 * @author DongXueQiu .
 * @version 1.0 .
 * @date 16-6-7
 * @file BidHallAdapter.java  .
 * @brief 应标大厅.
 */
public class BidHallAdapter extends BaseAdapter<BidHallEntity.NeedsListBean> {


    public BidHallAdapter(Context context, List<BidHallEntity.NeedsListBean> datas) {
        super(context, datas);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_lv_custom_bid;
    }

    @Override
    public Holder initHolder(View container) {
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.tvCustomBidHouseAddress = (TextView) container.findViewById(R.id.tv_bid_hall_address);
        viewHolder.tv_bid_hall_style = (TextView) container.findViewById(R.id.tv_bid_hall_style);
        viewHolder.tv_bid_livingroom_roomhall_t_toilet = (TextView) container.findViewById(R.id.tv_bid_livingroom_roomhall_t_toilet);
        viewHolder.tv_bid_hall_area = (TextView) container.findViewById(R.id.tv_bid_hall_area);
        viewHolder.tvCustomBidPerson = (TextView) container.findViewById(R.id.tv_bid_hall_person);
        viewHolder.tvCustomBidBidBudget = (TextView) container.findViewById(R.id.tv_bid_hall_budget);
        viewHolder.tv_bid_hall_per = (TextView) container.findViewById(R.id.tv_bid_hall_per);
        return viewHolder;
    }

    @Override
    public void initItem(View view, Holder holder, int position) {
        BidHallEntity.NeedsListBean customBid = mDatas.get(position);
        ((ViewHolder) holder).tvCustomBidHouseAddress.setText(customBid.getCommunity_name());

        ((ViewHolder) holder).tv_bid_hall_style.setText(customBid.getDecoration_style() + "/");
        ((ViewHolder) holder).tv_bid_livingroom_roomhall_t_toilet.setText(customBid.getRoom() + customBid.getLiving_room() + customBid.getToilet());
        ((ViewHolder) holder).tv_bid_hall_area.setText(customBid.getHouse_area() + UIUtils.getString(R.string.m2));

        ((ViewHolder) holder).tvCustomBidPerson.setText(customBid.getContacts_name());
        ((ViewHolder) holder).tvCustomBidBidBudget.setText(customBid.getDecoration_budget());
        ((ViewHolder) holder).tv_bid_hall_per.setText(customBid.getBidder_count()+mContext.getString(R.string.bind_per));
    }


    public class ViewHolder extends BaseAdapter.Holder {
        public TextView tvCustomBidHouseAddress;
        public TextView tvCustomBidPerson;
        public TextView tvCustomBidBidBudget;
        public TextView tv_bid_hall_per;

        public TextView tv_bid_hall_style;
        public TextView tv_bid_livingroom_roomhall_t_toilet;
        public TextView tv_bid_hall_area;
    }

    class MyOnClickListener implements View.OnClickListener {
        private int position;
        private ViewHolder holder;

        private MyOnClickListener(int position, ViewHolder holder) {
            this.position = position;
            this.holder = holder;
        }

        public void onClick(View v) {
            switch (v.getId()) {
//
//                case R.id.img_custom_bid_head:
//                    Intent intent = new Intent(mContext, DesignerHomeActivity.class);
//                    mContext.startActivity(intent);
//                    break;
            }
        }
    }
}

