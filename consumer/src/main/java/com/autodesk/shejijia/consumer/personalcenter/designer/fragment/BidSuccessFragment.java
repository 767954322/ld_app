package com.autodesk.shejijia.consumer.personalcenter.designer.fragment;

import android.os.Bundle;
import android.view.View;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.personalcenter.designer.entity.MyBidBean;
import com.autodesk.shejijia.shared.components.common.uielements.pulltorefresh.PullToRefreshLayout;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.framework.adapter.CommonAdapter;
import com.autodesk.shejijia.shared.framework.adapter.CommonViewHolder;

import java.util.ArrayList;

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
    protected boolean validateData(String status) {
        return IS_SUCCESS.equals(status);
    }

    @Override
    protected void initData() {
        super.initData();

        Bundle data = getArguments();
        ArrayList<MyBidBean.BiddingNeedsListEntity> biddingNeedsListEntitys = (ArrayList<MyBidBean.BiddingNeedsListEntity>) data.getSerializable("FragmentData");
        onFragmentShown(biddingNeedsListEntitys);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!mIsFirstIn) {
            mPullToRefreshLayout.autoRefresh();
        }
        mIsFirstIn = false;
    }

    protected CommonAdapter getCommonAdapter() {
        return new CommonAdapter<MyBidBean.BiddingNeedsListEntity>(UIUtils.getContext(), mBiddingNeedsListEntities, R.layout.item_mybid_suscuss) {
            @Override
            public void convert(CommonViewHolder holder, final MyBidBean.BiddingNeedsListEntity biddingNeedsListEntity) {
                setupBidItemView(holder, biddingNeedsListEntity);
                holder.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showDetail(biddingNeedsListEntity.getNeeds_id());
                    }
                });
            }
        };
    }

    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
        mBiddingNeedsListEntities.clear();
        mBiddingNeedsListEntities.addAll(getData(0));
        mCommonAdapter.notifyDataSetChanged();
        mPullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
    }

    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
        mBiddingNeedsListEntities.addAll(getData(mBiddingNeedsListEntities.size()));
        mCommonAdapter.notifyDataSetChanged();
        mPullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
    }
}
