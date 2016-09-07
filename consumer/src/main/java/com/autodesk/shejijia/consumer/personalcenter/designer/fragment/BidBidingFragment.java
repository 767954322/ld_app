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
import com.autodesk.shejijia.shared.components.common.uielements.CustomProgress;
import com.autodesk.shejijia.shared.components.common.uielements.pulltorefresh.PullToRefreshLayout;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.MPNetworkUtils;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.framework.AdskApplication;
import com.autodesk.shejijia.shared.framework.adapter.CommonAdapter;
import com.autodesk.shejijia.shared.framework.adapter.CommonViewHolder;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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
    protected boolean validateData(String status) {
        return BE_BEING.equals(status);
    }

    @Override
    protected void initView() {
        super.initView();
        CustomProgress.show(getActivity(), "", false, null);
    }

    @Override
    protected void initData() {
        super.initData();
        onWindowFocusChanged();
    }

    private void onWindowFocusChanged() {
        // 第一次进入自动刷新
        if (isFirstIn) {
            onRefresh(mPullToRefreshLayout);
            isFirstIn = false;
        }
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
        mBiddingNeedsListEntities.addAll(getData(mBiddingNeedsListEntities.size()));
        mCommonAdapter.notifyDataSetChanged();
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

    /// 变量.
    private boolean isFirstIn = true;

    private FragmentCallBack fragmentCallBack;
}
