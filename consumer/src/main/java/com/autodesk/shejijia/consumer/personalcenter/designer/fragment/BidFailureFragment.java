package com.autodesk.shejijia.consumer.personalcenter.designer.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.bidhall.activity.BiddingHallDetailActivity;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.framework.fragment.BaseFragment;
import com.autodesk.shejijia.shared.framework.adapter.CommonAdapter;
import com.autodesk.shejijia.shared.framework.adapter.CommonViewHolder;
import com.autodesk.shejijia.shared.components.common.uielements.pulltorefresh.PullToRefreshLayout;
import com.autodesk.shejijia.shared.components.common.uielements.pulltorefresh.PullListView;
import com.autodesk.shejijia.consumer.personalcenter.designer.entity.MyBidBean;
import com.autodesk.shejijia.consumer.utils.AppJsonFileReader;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author DongXueQiu .
 * @version 1.0 .
 * @date 16-6-7
 * @file BidFailureFragment.java  .
 * @brief 我的应标, 应标失败  .
 */
public class BidFailureFragment extends BidBaseFragment {

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_my_bid_outflow;
    }

    @Override
    protected void initView() {
        mListView = (PullListView) rootView.findViewById(R.id.lv_out_bid);
        mPullToRefreshLayout = ((PullToRefreshLayout) rootView.findViewById(R.id.refresh_my_out_view));
        mFooterView = View.inflate(getActivity(), R.layout.view_empty_layout, null);
        rl_empty = (RelativeLayout) mFooterView.findViewById(R.id.rl_empty);
        tv_empty_message = (TextView) mFooterView.findViewById(R.id.tv_empty_message);
    }

    @Override
    protected void initData() {
        super.initData();
        setListener();
        mCommonAdapter = getCommonAdapter();
        mListView.setAdapter(mCommonAdapter);
        addFooterViewForMListView();
        Bundle data = getArguments();
        ArrayList<MyBidBean.BiddingNeedsListEntity> biddingNeedsListEntitys = (ArrayList<MyBidBean.BiddingNeedsListEntity>) data.getSerializable("FragmentData");
        onFragmentShown(biddingNeedsListEntitys);
    }

    private void addFooterViewForMListView() {
        rl_empty.setVisibility(View.GONE);
        mListView.addFooterView(mFooterView);
        WindowManager wm = (WindowManager) getActivity().getSystemService(getActivity().WINDOW_SERVICE);
        int height = wm.getDefaultDisplay().getHeight();
        android.view.ViewGroup.LayoutParams pp = rl_empty.getLayoutParams();
        rl_empty.getLayoutParams();
        pp.height = height - height / 5;
        rl_empty.setLayoutParams(pp);
        tv_empty_message.setText(UIUtils.getString(R.string.bid_failure_no_data_massage));
    }

    public void onFragmentShown(ArrayList<MyBidBean.BiddingNeedsListEntity> biddingNeedsListEntitys) {
        mBiddingNeedsListEntities.clear();
        for (MyBidBean.BiddingNeedsListEntity biddingNeedsListEntity : biddingNeedsListEntitys) {
            MyBidBean.BiddingNeedsListEntity.BidderEntity bidderEntity = biddingNeedsListEntity.getBidder();
            if (bidderEntity != null) {
                String status = bidderEntity.getStatus();
                if (!status.equals(IS_SUCCESS) && !status.equals(BE_BEING)) {
                    mBiddingNeedsListEntities.add(biddingNeedsListEntity);
                }
            }
        }
        mBiddingNeedsListEntities1.clear();
        mBiddingNeedsListEntities1.addAll(getData(0));
        isHideMFooterView(mBiddingNeedsListEntities1.size());
        mCommonAdapter.notifyDataSetChanged();
    }

    private void isHideMFooterView(int size) {
        if (size <= 0) {
            rl_empty.setVisibility(View.VISIBLE);
        } else {
            rl_empty.setVisibility(View.GONE);
        }
    }

    ///适配器.
    private CommonAdapter getCommonAdapter() {
        return new CommonAdapter<MyBidBean.BiddingNeedsListEntity>(UIUtils.getContext(), mBiddingNeedsListEntities1, R.layout.item_mybid_fail) {
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

    private void setListener() {
        mPullToRefreshLayout.setOnRefreshListener(this);
    }

    private ArrayList<MyBidBean.BiddingNeedsListEntity> getData(int index) {
        int length = index + 10;
        ArrayList<MyBidBean.BiddingNeedsListEntity> list = new ArrayList<MyBidBean.BiddingNeedsListEntity>();
        for (int i = index; i < length && i < mBiddingNeedsListEntities.size(); i++) {
            MyBidBean.BiddingNeedsListEntity biddingNeedsListEntity = mBiddingNeedsListEntities.get(i);
            list.add(biddingNeedsListEntity);
        }
        return list;
    }

    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
        mBiddingNeedsListEntities1.clear();
        mBiddingNeedsListEntities1.addAll(getData(0));
        isHideMFooterView(mBiddingNeedsListEntities1.size());
        mCommonAdapter.notifyDataSetChanged();
        mPullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);

    }

    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {

        mBiddingNeedsListEntities1.addAll(getData(mBiddingNeedsListEntities1.size()));
        isHideMFooterView(mBiddingNeedsListEntities1.size());
        mCommonAdapter.notifyDataSetChanged();
        mPullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
    }

    /// 控件.
    private PullToRefreshLayout mPullToRefreshLayout;
    private RelativeLayout rl_empty;
    private PullListView mListView;
    private TextView tv_empty_message;
    private View mFooterView;

    //变量
    private static final String BE_BEING = "0";
    private static final String IS_SUCCESS = "1";

    ///集合，类.
    private CommonAdapter mCommonAdapter;
    private ArrayList<MyBidBean.BiddingNeedsListEntity> mBiddingNeedsListEntities = new ArrayList<MyBidBean.BiddingNeedsListEntity>();
    private ArrayList<MyBidBean.BiddingNeedsListEntity> mBiddingNeedsListEntities1 = new ArrayList<>();
}
