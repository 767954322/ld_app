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
 * @file BidSuccessFragment.java  .
 * @brief 我的应标:应标成功 .
 */
public class BidSuccessFragment extends BidBaseFragment {

    private static final int IS_BEI_SHU = 1;

    @Override
    protected int getEmptyDataMessage() {
        return R.string.bid_success_no_data_massage;
    }

    @Override
    protected String getCurrentBidStatus() {
        return BID_STATUS_SUCCESS;
    }

    @Override
    protected void initData() {
        super.initData();
    }

    protected CommonAdapter getCommonAdapter() {
        return new CommonAdapter<MyBidBean.BiddingNeedsListEntity>(UIUtils.getContext(), mBiddingNeedsListEntities, R.layout.item_mybid_suscuss) {
            @Override
            public void convert(CommonViewHolder holder, final MyBidBean.BiddingNeedsListEntity biddingNeedsListEntity) {
                setupBidItemView(holder, biddingNeedsListEntity);
                holder.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //查看详情， 不传1 就行 by zjl
                        showDetail(biddingNeedsListEntity.getNeeds_id(),"2");
                    }
                });
            }
        };
    }
}
