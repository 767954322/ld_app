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
    protected void initData() {
        super.initData();
        setListener();
        mBiddingNeedsListEntities = new ArrayList<>();
        mBiddingNeedsListEntityArrayList = new ArrayList<>();
        mCommonAdapter = getCommonAdapter();
        mPullListView.setAdapter(mCommonAdapter);

        Bundle data = getArguments();
        ArrayList<MyBidBean.BiddingNeedsListEntity> biddingNeedsListEntitys = (ArrayList<MyBidBean.BiddingNeedsListEntity>) data.getSerializable("FragmentData");
        onFragmentShown(biddingNeedsListEntitys);
    }

    public void onFragmentShown(ArrayList<MyBidBean.BiddingNeedsListEntity> biddingNeedsListEntitys) {
        mBiddingNeedsListEntityArrayList.clear();
        for (MyBidBean.BiddingNeedsListEntity biddingNeedsListEntity : biddingNeedsListEntitys) {
            MyBidBean.BiddingNeedsListEntity.BidderEntity bidderEntity = biddingNeedsListEntity.getBidder();
            if (bidderEntity != null) {
                status = bidderEntity.getStatus();
                if (status.equals(IS_SUCCESS)) {
                    mBiddingNeedsListEntityArrayList.add(biddingNeedsListEntity);
                }
            }
        }
        mBiddingNeedsListEntities.clear();
        mBiddingNeedsListEntities.addAll(getData(0));
        mCommonAdapter.notifyDataSetChanged();
    }

    private void setListener() {
        mPullToRefreshLayout.setOnRefreshListener(this);
    }

    private CommonAdapter getCommonAdapter() {
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

    private ArrayList<MyBidBean.BiddingNeedsListEntity> getData(int index) {
        int length = index + 10;
        ArrayList<MyBidBean.BiddingNeedsListEntity> list = new ArrayList<MyBidBean.BiddingNeedsListEntity>();
        for (int i = index; i < length && i < mBiddingNeedsListEntityArrayList.size(); i++) {
            MyBidBean.BiddingNeedsListEntity biddingNeedsListEntity = mBiddingNeedsListEntityArrayList.get(i);
            list.add(biddingNeedsListEntity);
        }
        return list;
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

    ///变量.
    private static final String IS_SUCCESS = "1";
    private String status;
    private CommonAdapter mCommonAdapter;
    private ArrayList<MyBidBean.BiddingNeedsListEntity> mBiddingNeedsListEntities;
    private ArrayList<MyBidBean.BiddingNeedsListEntity> mBiddingNeedsListEntityArrayList;
}
