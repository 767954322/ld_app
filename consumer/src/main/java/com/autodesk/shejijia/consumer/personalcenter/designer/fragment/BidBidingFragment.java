package com.autodesk.shejijia.consumer.personalcenter.designer.fragment;

import android.view.View;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.personalcenter.designer.entity.MyBidBean;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.consumer.base.adapter.CommonAdapter;
import com.autodesk.shejijia.consumer.base.adapter.CommonViewHolder;

/**
 * @author DongXueQiu .
 * @version 1.0 .
 * @date 16-6-7
 * @file BidBidingFragment.java  .
 * @brief 我的应标:应标中 .
 */
public class BidBidingFragment extends BidBaseFragment {

    @Override
    protected int getEmptyDataMessage() {
        return R.string.bidbiding_no_data_massage;
    }

    @Override
    protected String getCurrentBidStatus() {
        return BID_STATUS_BEING;
    }

    @Override
    protected void initView() {
        super.initView();
    }

    protected CommonAdapter getCommonAdapter() {

        return new CommonAdapter<MyBidBean.BiddingNeedsListEntity>(UIUtils.getContext(), mBiddingNeedsListEntities, R.layout.item_mybid_bidding) {
            @Override
            public void convert(CommonViewHolder holder, final MyBidBean.BiddingNeedsListEntity biddingNeedsListEntity) {
                setupBidItemView(holder, biddingNeedsListEntity);
                holder.setText(R.id.tv_bidder_count, String.format(getString(R.string.bid_designer_num),
                        biddingNeedsListEntity.getBidder_count()));
                holder.setText(R.id.tv_decoration_end_day, biddingNeedsListEntity.getEnd_day());
                holder.setVisible(R.id.iv_icon_time, true);
                holder.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showDetail(biddingNeedsListEntity.getNeeds_id(), "2");
                    }
                });
            }
        };
    }
}
