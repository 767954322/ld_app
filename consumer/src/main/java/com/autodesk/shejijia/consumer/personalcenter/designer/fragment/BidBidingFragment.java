package com.autodesk.shejijia.consumer.personalcenter.designer.fragment;

import android.app.Activity;
import android.view.View;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.consumer.personalcenter.designer.entity.MyBidBean;
import com.autodesk.shejijia.consumer.utils.ApiStatusUtil;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.uielements.pulltorefresh.PullToRefreshLayout;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.MPNetworkUtils;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.framework.AdskApplication;
import com.autodesk.shejijia.shared.framework.adapter.CommonAdapter;
import com.autodesk.shejijia.shared.framework.adapter.CommonViewHolder;

import org.json.JSONObject;

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

                holder.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showDetail(biddingNeedsListEntity.getNeeds_id());
                    }
                });
            }
        };
    }
}
