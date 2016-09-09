package com.autodesk.shejijia.consumer.personalcenter.designer.fragment;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.shared.framework.adapter.CommonAdapter;
import com.autodesk.shejijia.shared.framework.adapter.CommonViewHolder;
import com.autodesk.shejijia.shared.components.common.uielements.pulltorefresh.PullToRefreshLayout;
import com.autodesk.shejijia.consumer.personalcenter.designer.entity.MyBidBean;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;

import java.util.ArrayList;

/**
 * @author DongXueQiu .
 * @version 1.0 .
 * @date 16-6-7
 * @file BidFailureFragment.java  .
 * @brief 我的应标, 应标失败  .
 */
public class BidFailureFragment extends BidBaseFragment {

    @Override
    protected int getEmptyDataMessage() {
        return R.string.bid_failure_no_data_massage;
    }

    @Override
    protected String getCurrentBidStatus() {
        return BID_STATUS_FAIL;
    }

    @Override
    protected void initData() {
        super.initData();
    }

    ///适配器.
    protected CommonAdapter getCommonAdapter() {
        return new CommonAdapter<MyBidBean.BiddingNeedsListEntity>(UIUtils.getContext(), mBiddingNeedsListEntities, R.layout.item_mybid_fail) {
            @Override
            public void convert(CommonViewHolder holder, MyBidBean.BiddingNeedsListEntity biddingNeedsListEntity) {
                setupBidItemView(holder, biddingNeedsListEntity);

                ViewGroup itemHeader = holder.getView(R.id.mybid_item_header);
                ViewGroup itemInfo = holder.getView(R.id.bid_item_info);
                setTextColor(itemHeader, getResources().getColor(R.color.mybid_text_color_light));
                setTextColor(itemInfo, getResources().getColor(R.color.mybid_text_color_light));
                holder.setVisible(R.id.tv_decoration_detail, false);
           }
        };
    }

    private void setTextColor(View view, int color) {
        if(view instanceof ViewGroup) {
            ViewGroup vg = (ViewGroup) view;
            int count = vg.getChildCount();
            for(int i=0; i<count; i++) {
                setTextColor(vg.getChildAt(i), color);
            }
        } else if(view instanceof TextView) {
            ((TextView) view).setTextColor(color);
        }
    }
}
