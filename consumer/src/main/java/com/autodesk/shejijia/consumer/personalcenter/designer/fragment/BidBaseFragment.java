package com.autodesk.shejijia.consumer.personalcenter.designer.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.consumer.personalcenter.designer.entity.MyBidBean;
import com.autodesk.shejijia.consumer.personalcenter.resdecoration.activity.DecorationDetailActivity;
import com.autodesk.shejijia.consumer.utils.ApiStatusUtil;
import com.autodesk.shejijia.consumer.utils.AppJsonFileReader;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.consumer.uielements.pulltorefresh.PullListView;
import com.autodesk.shejijia.consumer.uielements.pulltorefresh.PullToRefreshLayout;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.MPNetworkUtils;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.framework.AdskApplication;
import com.autodesk.shejijia.consumer.base.adapter.CommonAdapter;
import com.autodesk.shejijia.consumer.base.adapter.CommonViewHolder;
import com.autodesk.shejijia.shared.framework.fragment.BaseFragment;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by wenhulin on 9/7/16.
 */
public abstract class BidBaseFragment extends BaseFragment implements PullToRefreshLayout.OnRefreshListener {

    protected abstract int getEmptyDataMessage();

    protected abstract CommonAdapter getCommonAdapter();

    protected abstract String getCurrentBidStatus();

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_my_bid;
    }

    @Override
    protected void initData() {
        style = AppJsonFileReader.getStyle(getActivity());
        area = AppJsonFileReader.getArea(getActivity());
        space = AppJsonFileReader.getSpace(getActivity());
        living_room = AppJsonFileReader.getLivingRoom(getActivity());
        room = AppJsonFileReader.getRoomHall(getActivity());
        toilet = AppJsonFileReader.getToilet(getActivity());

        mPullToRefreshLayout.setOnRefreshListener(this);
        mBiddingNeedsListEntities = new ArrayList<>();
        mCommonAdapter = getCommonAdapter();
        mPullListView.setAdapter(mCommonAdapter);
        if (mIsFirstIn || isVisible()) {
            mPullToRefreshLayout.autoRefresh();
        }
        mIsFirstIn = false;
    }


    @Override
    protected void initView() {
        mPullListView = (PullListView) rootView.findViewById(R.id.pullable_listview);
        mPullToRefreshLayout = ((PullToRefreshLayout) rootView.findViewById(R.id.pull_to_refresh_layout));
        mEmptyView = (RelativeLayout) rootView.findViewById(R.id.rl_empty);
        TextView tvEmptyMessage = (TextView) mEmptyView.findViewById(R.id.tv_empty_message);
        tvEmptyMessage.setText(getEmptyDataMessage());

    }

    @Override
    public void onResume() {
        super.onResume();
      // FIXME: 16-9-21 remove into refresh by zjl 9-12 email
//        if (mIsFirstIn || isVisible()) {
//            mPullToRefreshLayout.autoRefresh();
//        }
//        mIsFirstIn = false;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
        fetchMyBidData(0, FETCH_MYBID_DATA_LIMIT, true);
    }

    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
        fetchMyBidData(mBiddingNeedsListEntities.size(), FETCH_MYBID_DATA_LIMIT, false);
    }

    private boolean validateData(String status) {
        return getCurrentBidStatus().equals(status);
    }

    public void updateView(List<MyBidBean.BiddingNeedsListEntity> biddingNeedsListEntitys, boolean isRefresh) {
        if (mPullListView.getEmptyView() == null) {
            mPullListView.setEmptyView(mEmptyView);
        }

        if (isRefresh) {
            mBiddingNeedsListEntities.clear();
        }

        if (biddingNeedsListEntitys != null && biddingNeedsListEntitys.size() > 0) {
            mBiddingNeedsListEntities.addAll(biddingNeedsListEntitys);
        }

        mCommonAdapter.notifyDataSetChanged();
    }


    /**
     * @param memType
     * @param acsToken
     * @param designer_id
     * @param offset
     * @param limit
     * @brief Get mybid data by bid status
     */
    public void fetchMyBidData(int offset, int limit, final boolean isRefresh) {
        MemberEntity memberEntity = AdskApplication.getInstance().getMemberEntity();
        if (memberEntity == null) {
            return;
        }

        String memType = memberEntity.getMember_type();
        String acsToken = memberEntity.getAcs_token();
        int designerId = Integer.parseInt(memberEntity.getAcs_member_id());
        OkJsonRequest.OKResponseCallback callback = new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                mPullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                String str = GsonUtil.jsonToString(jsonObject);
                MyBidBean myBidBean = GsonUtil.jsonToBean(str, MyBidBean.class);
                updateView(myBidBean.getBidding_needs_list(), isRefresh);
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                MPNetworkUtils.logError(TAG, volleyError);
                mPullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.FAIL);
                ApiStatusUtil.getInstance().apiStatuError(volleyError, getActivity());
            }
        };
        MPServerHttpManager.getInstance().getMyBidData(memType, acsToken, offset, limit, designerId, getCurrentBidStatus(),
                callback);
    }

    protected void setupBidItemView(CommonViewHolder holder, MyBidBean.BiddingNeedsListEntity biddingNeedsListEntity) {
        // 若小区名称超过四个字后用...省略  by dongxueqiu
        String need_name = UIUtils.substring(biddingNeedsListEntity.getNeeds_name(), 4);
        holder.setText(R.id.tv_decoration_name, need_name);

//        holder.setText(R.id.tv_decoration_name, biddingNeedsListEntity.getNeeds_name());
        holder.setText(R.id.tv_decoration_buget, biddingNeedsListEntity.getRenovation_budget());
        holder.setText(R.id.tv_decoration_needs_id, biddingNeedsListEntity.getNeeds_id());
        holder.setText(R.id.tv_decoration_address, getProjectAddress(biddingNeedsListEntity));
        holder.setVisible(R.id.decoration_phone_container, false);
        holder.setText(R.id.tv_decoration_house_type, getProjectHourseType(biddingNeedsListEntity));
        holder.setText(R.id.tv_decoration_style, getProjectDecorationStyle(biddingNeedsListEntity));
    }

    protected void showDetail(String needsId,String wk_template_id) {
//        Intent intent = new Intent(getActivity(), BiddingHallDetailActivity.class);
//        Bundle bundle = new Bundle();
//        LogUtils.i(TAG, needsId);
//        intent.putExtra(Constant.DemandDetailBundleKey.DEMAND_NEEDS_ID, needsId);
//        intent.putExtra(Constant.DemandDetailBundleKey.DEMAND_TYPE, Constant.DemandDetailBundleKey.TYPE_BEING_FRAGMENT);
//        intent.putExtra(Constant.DemandDetailBundleKey.DEMAND_BID_STATUS, true);
//        intent.putExtras(bundle);
//        startActivity(intent);

        // fix ui不符合  by zhoujinlong

        Bundle bundle = new Bundle();
        bundle.putString(Constant.ConsumerDecorationFragment.WK_TEMPLATE_ID, wk_template_id);
        bundle.putString(Constant.ConsumerDecorationFragment.NEED_ID, needsId);
        DecorationDetailActivity.jumpTo(getActivity(), bundle);

    }

    protected String getProjectAddress(MyBidBean.BiddingNeedsListEntity biddingNeedsListEntity) {
        String provinceName = biddingNeedsListEntity.getProvinceName();
        String cityName = biddingNeedsListEntity.getCityName();
        String districtName = biddingNeedsListEntity.getDistrictName();
        districtName = TextUtils.isEmpty(districtName) || "none".equals(districtName) ? "" : districtName;
        provinceName = TextUtils.isEmpty(provinceName) ? "" : provinceName;
        cityName = TextUtils.isEmpty(cityName) ? "" : cityName;
        return provinceName.trim() + cityName.trim() + districtName.trim();
    }

    protected String getProjectHourseType(MyBidBean.BiddingNeedsListEntity biddingNeedsListEntity) {
        String houseType = biddingNeedsListEntity.getHouse_type();
        if (space.containsKey(houseType)) {
            return space.get(houseType);
        } else {
            return houseType;
        }
    }

    protected String getProjectDecorationStyle(MyBidBean.BiddingNeedsListEntity biddingNeedsListEntity) {
        String decorationStyle = biddingNeedsListEntity.getRenovation_style();
        if (style.containsKey(decorationStyle)) {
            return style.get(decorationStyle);
        } else {
            return decorationStyle;
        }
    }

    private Map<String, String> living_room;
    private Map<String, String> style;
    private Map<String, String> area;
    private Map<String, String> room;
    private Map<String, String> toilet;
    private Map<String, String> space;

    ///控件.
    protected PullToRefreshLayout mPullToRefreshLayout;
    protected PullListView mPullListView;
    protected RelativeLayout mEmptyView;

    protected CommonAdapter mCommonAdapter;
    protected ArrayList<MyBidBean.BiddingNeedsListEntity> mBiddingNeedsListEntities;

    protected static final String BID_STATUS_BEING = "0";
    protected static final String BID_STATUS_SUCCESS = "1";
    protected static final String BID_STATUS_FAIL = "2";

    private static final int FETCH_MYBID_DATA_LIMIT = 10;

    /// 变量.
    protected boolean mIsFirstIn = true;
}
