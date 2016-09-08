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
 * Created by wenhulin on 9/7/16.
 */
public abstract class BidBaseFragment extends BaseFragment implements PullToRefreshLayout.OnRefreshListener {

    protected abstract int getEmptyDataMessage();
    protected abstract boolean validateData(String status);
    protected abstract CommonAdapter getCommonAdapter();

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
        mBiddingNeedsListEntityArrayList = new ArrayList<>();
        mCommonAdapter = getCommonAdapter();
        mPullListView.setAdapter(mCommonAdapter);
    }

    @Override
    protected void initView() {
        mPullListView = (PullListView) rootView.findViewById(R.id.pullable_listview);
        mPullToRefreshLayout = ((PullToRefreshLayout) rootView.findViewById(R.id.pull_to_refresh_layout));
        mEmptyView = (RelativeLayout) rootView.findViewById(R.id.rl_empty);
        TextView tvEmptyMessage = (TextView) mEmptyView.findViewById(R.id.tv_empty_message);
        tvEmptyMessage.setText(getEmptyDataMessage());
    }

    public void onFragmentShown(List<MyBidBean.BiddingNeedsListEntity> biddingNeedsListEntitys) {
        if (mPullListView.getEmptyView() == null) {
            mPullListView.setEmptyView(mEmptyView);
        }

        mBiddingNeedsListEntityArrayList.clear();
        for (MyBidBean.BiddingNeedsListEntity biddingNeedsListEntity : biddingNeedsListEntitys) {
            MyBidBean.BiddingNeedsListEntity.BidderEntity bidderEntity = biddingNeedsListEntity.getBidder();
            if (bidderEntity != null && validateData(bidderEntity.getStatus())) {
                mBiddingNeedsListEntityArrayList.add(biddingNeedsListEntity);
            }
        }
        mBiddingNeedsListEntities.clear();
        mBiddingNeedsListEntities.addAll(getData(0));
        mCommonAdapter.notifyDataSetChanged();
    }

    protected ArrayList<MyBidBean.BiddingNeedsListEntity> getData(int index) {
        int length = index + 10;
        ArrayList<MyBidBean.BiddingNeedsListEntity> list = new ArrayList<MyBidBean.BiddingNeedsListEntity>();
        for (int i = index; i < length && i < mBiddingNeedsListEntityArrayList.size(); i++) {
            MyBidBean.BiddingNeedsListEntity biddingNeedsListEntity = mBiddingNeedsListEntityArrayList.get(i);
            list.add(biddingNeedsListEntity);
        }
        return list;
    }

    protected void setupBidItemView(CommonViewHolder holder, MyBidBean.BiddingNeedsListEntity biddingNeedsListEntity) {
        holder.setText(R.id.tv_decoration_name, biddingNeedsListEntity.getNeeds_name());
        holder.setText(R.id.tv_decoration_buget, biddingNeedsListEntity.getRenovation_budget());

        holder.setText(R.id.tv_decoration_needs_id, biddingNeedsListEntity.getNeeds_id());
        holder.setText(R.id.tv_decoration_address, getProjectAddress(biddingNeedsListEntity));
        holder.setVisible(R.id.decoration_phone_container, false);
        holder.setText(R.id.tv_decoration_house_type, getProjectHourseType(biddingNeedsListEntity));
        holder.setText(R.id.tv_decoration_style, getProjectDecorationStyle(biddingNeedsListEntity));
    }

    protected void showDetail(String needsId) {
        Intent intent = new Intent(getActivity(), BiddingHallDetailActivity.class);
        Bundle bundle = new Bundle();
        KLog.d(TAG, needsId);
        intent.putExtra(Constant.DemandDetailBundleKey.DEMAND_NEEDS_ID, needsId);
        intent.putExtra(Constant.DemandDetailBundleKey.DEMAND_TYPE, Constant.DemandDetailBundleKey.TYPE_BEING_FRAGMENT);
        intent.putExtra(Constant.DemandDetailBundleKey.DEMAND_BID_STATUS, true);
        intent.putExtras(bundle);
        startActivity(intent);
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
    protected ArrayList<MyBidBean.BiddingNeedsListEntity> mBiddingNeedsListEntityArrayList;

    protected static final String BE_BEING = "0";
    protected static final String IS_SUCCESS = "1";
}
