package com.autodesk.shejijia.consumer.personalcenter.designer.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.bidhall.activity.BiddingHallDetailActivity;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.consumer.personalcenter.designer.activity.MyBidActivity;
import com.autodesk.shejijia.consumer.personalcenter.designer.entity.MyBidBean;
import com.autodesk.shejijia.consumer.utils.ApiStatusUtil;
import com.autodesk.shejijia.consumer.utils.AppJsonFileReader;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.uielements.CustomProgress;
import com.autodesk.shejijia.shared.components.common.uielements.pulltorefresh.PullListView;
import com.autodesk.shejijia.shared.components.common.uielements.pulltorefresh.PullToRefreshLayout;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.MPNetworkUtils;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.framework.AdskApplication;
import com.autodesk.shejijia.shared.framework.adapter.CommonAdapter;
import com.autodesk.shejijia.shared.framework.adapter.CommonViewHolder;
import com.autodesk.shejijia.shared.framework.fragment.BaseFragment;
import com.socks.library.KLog;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author DongXueQiu .
 * @version 1.0 .
 * @date 16-6-7
 * @file BidBidingFragment.java  .
 * @brief 我的应标:应标中 .
 */
public class BidBidingFragment extends BidBaseFragment {

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_my_bid_be_being;
    }

    @Override
    protected void initView() {
        mListView = (PullListView) rootView.findViewById(R.id.lv_my_bid);
        mPullToRefreshLayout = ((PullToRefreshLayout) rootView.findViewById(R.id.refresh_my_bid_view));
        mFooterView = View.inflate(getActivity(), R.layout.view_empty_layout, null);
        rl_empty = (RelativeLayout) mFooterView.findViewById(R.id.rl_empty);
        tv_empty_message = (TextView) mFooterView.findViewById(R.id.tv_empty_message);
        CustomProgress.show(getActivity(), "", false, null);
    }

    @Override
    protected void initData() {
        super.initData();
        setListener();
        mList = new ArrayList<>();
        beBeingList = new ArrayList<>();
        commonAdapter = getCommonAdapter();
        mListView.setAdapter(commonAdapter);
//        addFooterViewForMListView();

        onWindowFocusChanged();
    }

    private void onWindowFocusChanged() {
        // 第一次进入自动刷新
        if (isFirstIn) {
            onRefresh(mPullToRefreshLayout);
            isFirstIn = false;
        }
    }

//    private void addFooterViewForMListView() {
//        rl_empty.setVisibility(View.GONE);
//        mListView.addFooterView(mFooterView);
//        WindowManager wm = (WindowManager) getActivity().getSystemService(getActivity().WINDOW_SERVICE);
//        int height = wm.getDefaultDisplay().getHeight();
//        android.view.ViewGroup.LayoutParams pp = rl_empty.getLayoutParams();
//        rl_empty.getLayoutParams();
//        pp.height = height - height / 5;
//        rl_empty.setLayoutParams(pp);
//        tv_empty_message.setText(UIUtils.getString(R.string.bidbiding_no_data_massage));
//    }

    public void onFragmentShown(List<MyBidBean.BiddingNeedsListEntity> biddingNeedsListEntitys) {
        beBeingList.clear();
        for (MyBidBean.BiddingNeedsListEntity biddingNeedsListEntity : biddingNeedsListEntitys) {
            MyBidBean.BiddingNeedsListEntity.BidderEntity bidderEntity = biddingNeedsListEntity.getBidder();
            if (bidderEntity != null) {
                status = bidderEntity.getStatus();
                if (BE_BEING.equals(status)) {
                    beBeingList.add(biddingNeedsListEntity);
                }
            }
        }
        mList.clear();
        mList.addAll(getData(0));
        commonAdapter.notifyDataSetChanged();
        isHideMFooterView(mList.size());
    }

    private void isHideMFooterView(int size) {
        if (size <= 0) {
            rl_empty.setVisibility(View.VISIBLE);
        } else {
            rl_empty.setVisibility(View.GONE);
        }
    }

    private void setListener() {
        mPullToRefreshLayout.setOnRefreshListener(this);
    }

    private CommonAdapter getCommonAdapter() {

        return new CommonAdapter<MyBidBean.BiddingNeedsListEntity>(UIUtils.getContext(), mList, R.layout.item_mybid_bidding) {
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

    private ArrayList<MyBidBean.BiddingNeedsListEntity> getData(int index) {
        int length = index + 10;
        ArrayList<MyBidBean.BiddingNeedsListEntity> list = new ArrayList<MyBidBean.BiddingNeedsListEntity>();
        for (int i = index; i < length && i < beBeingList.size(); i++) {
            MyBidBean.BiddingNeedsListEntity biddingNeedsListEntity = beBeingList.get(i);
            list.add(biddingNeedsListEntity);
        }
        return list;
    }

    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {

        MemberEntity memberEntity = AdskApplication.getInstance().getMemberEntity();
        if (memberEntity == null) {
            return;
        }
        String memType = memberEntity.getMember_type();
        String acsToken = memberEntity.getAcs_token();
        String designer_id = memberEntity.getAcs_member_id();
        getMyBidData(memType, acsToken, designer_id, 0, 100);

    }

    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {

        mList.addAll(getData(mList.size()));
        isHideMFooterView(mList.size());
        commonAdapter.notifyDataSetChanged();
        mPullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
    }

    //获取数据后，更新
    public void updateViewFromData(MyBidBean myBidBean) {
        mPullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
        if (fragmentCallBack != null) {
            fragmentCallBack.getMyBidBean(myBidBean);
        }
    }

    /**
     * @param memType
     * @param acsToken
     * @param designer_id
     * @param offset
     * @param limit
     * @brief 获取我的应标中所有得数据 .
     */
    public void getMyBidData(final String memType, final String acsToken, String designer_id, int offset, int limit) {
        OkJsonRequest.OKResponseCallback callback = new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                CustomProgress.cancelDialog();
                String str = GsonUtil.jsonToString(jsonObject);
                MyBidBean myBidBean = GsonUtil.jsonToBean(str, MyBidBean.class);
                onFragmentShown(myBidBean.getBidding_needs_list());
                updateViewFromData(myBidBean);
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                MPNetworkUtils.logError(TAG, volleyError);
                CustomProgress.cancelDialog();
                mPullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.FAIL);
//                new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.network_error), null, new String[]{UIUtils.getString(R.string.sure)}, null, getActivity(),
//                        AlertView.Style.Alert, null).show();
                ApiStatusUtil.getInstance().apiStatuError(volleyError,getActivity());
            }
        };
        MPServerHttpManager.getInstance().getMyBidData(memType, acsToken, offset, limit, designer_id, callback);
    }

    public interface FragmentCallBack {
        void getMyBidBean(MyBidBean myBidBean);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof FragmentCallBack) {
            fragmentCallBack = (FragmentCallBack)activity;
        }
    }


    /// 控件.
    private RelativeLayout rl_empty;
    private PullListView mListView;
    private PullToRefreshLayout mPullToRefreshLayout;
    private TextView tv_empty_message;
    private View mFooterView;

    /// 变量.
    private static final String BE_BEING = "0";
    private String status;
    private boolean isFirstIn = true;

    ///　集合，类.
    private ArrayList<MyBidBean.BiddingNeedsListEntity> mList;
    private List<MyBidBean.BiddingNeedsListEntity> beBeingList;
    private FragmentCallBack fragmentCallBack;
    private CommonAdapter commonAdapter;
}
