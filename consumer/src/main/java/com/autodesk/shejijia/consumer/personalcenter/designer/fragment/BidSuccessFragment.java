package com.autodesk.shejijia.consumer.personalcenter.designer.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.bidhall.activity.BiddingHallDetailActivity;
import com.autodesk.shejijia.consumer.personalcenter.designer.entity.MyBidBean;
import com.autodesk.shejijia.consumer.utils.AppJsonFileReader;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.uielements.pulltorefresh.PullListView;
import com.autodesk.shejijia.shared.components.common.uielements.pulltorefresh.PullToRefreshLayout;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.framework.adapter.CommonAdapter;
import com.autodesk.shejijia.shared.framework.adapter.CommonViewHolder;
import com.autodesk.shejijia.shared.framework.fragment.BaseFragment;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author DongXueQiu .
 * @version 1.0 .
 * @date 16-6-7
 * @file BidSuccessFragment.java  .
 * @brief 我的应标:应标成功 .
 */
public class BidSuccessFragment extends BaseFragment implements PullToRefreshLayout.OnRefreshListener {

    private static final int IS_BEI_SHU = 1;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_my_bid_bingo;
    }

    @Override
    protected void initView() {
        mPullListView = (PullListView) rootView.findViewById(R.id.lv_bingo_fragment);
        mPullToRefreshLayout = ((PullToRefreshLayout) rootView.findViewById(R.id.refresh_my_bid_bingo_view));
        mFooterView = View.inflate(getActivity(), R.layout.view_empty_layout, null);
        rl_empty = (RelativeLayout) mFooterView.findViewById(R.id.rl_empty);
        tv_empty_message = (TextView) mFooterView.findViewById(R.id.tv_empty_message);
    }

    @Override
    protected void initData() {
        setListener();
        mBiddingNeedsListEntities = new ArrayList<>();
        mBiddingNeedsListEntityArrayList = new ArrayList<>();

        living_room = AppJsonFileReader.getLivingRoom(getActivity());
        style = AppJsonFileReader.getStyle(getActivity());
        area = AppJsonFileReader.getArea(getActivity());
        room = AppJsonFileReader.getRoomHall(getActivity());
        toilet = AppJsonFileReader.getToilet(getActivity());
        space = AppJsonFileReader.getSpace(getActivity());
        mCommonAdapter = getCommonAdapter();
        mPullListView.setAdapter(mCommonAdapter);

        addFooterViewForMListView();
        Bundle data = getArguments();
        ArrayList<MyBidBean.BiddingNeedsListEntity> biddingNeedsListEntitys = (ArrayList<MyBidBean.BiddingNeedsListEntity>) data.getSerializable("FragmentData");
        onFragmentShown(biddingNeedsListEntitys);
    }

    private void addFooterViewForMListView() {
        rl_empty.setVisibility(View.GONE);
        mPullListView.addFooterView(mFooterView);
        WindowManager wm = (WindowManager) getActivity().getSystemService(getActivity().WINDOW_SERVICE);
        int height = wm.getDefaultDisplay().getHeight();
        android.view.ViewGroup.LayoutParams pp = rl_empty.getLayoutParams();
        rl_empty.getLayoutParams();
        pp.height = height - height / 5;
        rl_empty.setLayoutParams(pp);
        tv_empty_message.setText(UIUtils.getString(R.string.bid_success_no_data_massage));
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
        isHideMFooterView(mBiddingNeedsListEntities.size());
        mCommonAdapter.notifyDataSetChanged();

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
        return new CommonAdapter<MyBidBean.BiddingNeedsListEntity>(UIUtils.getContext(), mBiddingNeedsListEntities, R.layout.item_mybid_suscuss) {
            @Override
            public void convert(CommonViewHolder holder, final MyBidBean.BiddingNeedsListEntity biddingNeedsListEntity) {
                holder.setText(R.id.tv_decoration_name, biddingNeedsListEntity.getNeeds_name());
                holder.setText(R.id.tv_decoration_buget, biddingNeedsListEntity.getRenovation_budget());

                holder.setText(R.id.tv_decoration_needs_id, biddingNeedsListEntity.getNeeds_id());
                holder.setText(R.id.tv_decoration_address, getProjectAddress(biddingNeedsListEntity));
                holder.setVisible(R.id.decoration_phone_container, false);
                holder.setText(R.id.tv_decoration_house_type, getProjectHourseType(biddingNeedsListEntity));
                holder.setText(R.id.tv_decoration_style, getProjectDecorationStyle(biddingNeedsListEntity));

                holder.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showDetail(biddingNeedsListEntity.getNeeds_id());
                    }
                });
            }
        };
    }

    private void showDetail(String needsId) {
        Intent intent = new Intent(getActivity(), BiddingHallDetailActivity.class);
        Bundle bundle = new Bundle();
        KLog.d(TAG, needsId);
        intent.putExtra(Constant.DemandDetailBundleKey.DEMAND_NEEDS_ID, needsId);
        intent.putExtra(Constant.DemandDetailBundleKey.DEMAND_TYPE, Constant.DemandDetailBundleKey.TYPE_BEING_FRAGMENT);
        intent.putExtra(Constant.DemandDetailBundleKey.DEMAND_BID_STATUS, true);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private String getProjectAddress(MyBidBean.BiddingNeedsListEntity biddingNeedsListEntity) {
        String provinceName = biddingNeedsListEntity.getProvinceName();
        String cityName = biddingNeedsListEntity.getCityName();
        String districtName = biddingNeedsListEntity.getDistrictName();
        districtName = TextUtils.isEmpty(districtName) || "none".equals(districtName) ? "" : districtName;
        provinceName = TextUtils.isEmpty(provinceName) ? "" : provinceName;
        cityName = TextUtils.isEmpty(cityName) ? "" : cityName;
        return provinceName.trim() + cityName.trim() + districtName.trim();
    }

    private String getProjectHourseType(MyBidBean.BiddingNeedsListEntity biddingNeedsListEntity) {
        String houseType = biddingNeedsListEntity.getHouse_type();
        if (space.containsKey(houseType)) {
            return space.get(houseType);
        } else {
            return houseType;
        }
    }

    private String getProjectDecorationStyle(MyBidBean.BiddingNeedsListEntity biddingNeedsListEntity) {
        String decorationStyle = biddingNeedsListEntity.getRenovation_style();
        if (style.containsKey(decorationStyle)) {
            return style.get(decorationStyle);
        } else {
            return decorationStyle;
        }
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
        isHideMFooterView(mBiddingNeedsListEntities.size());
        mCommonAdapter.notifyDataSetChanged();
        mPullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
    }

    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
        mBiddingNeedsListEntities.addAll(getData(mBiddingNeedsListEntities.size()));
        isHideMFooterView(mBiddingNeedsListEntities.size());
        mCommonAdapter.notifyDataSetChanged();
        mPullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
    }

    ///控件.
    private PullToRefreshLayout mPullToRefreshLayout;
    private View mFooterView;
    private RelativeLayout rl_empty;
    private PullListView mPullListView;
    private TextView tv_empty_message;

    ///变量.
    private static final String IS_SUCCESS = "1";
    private String status;
    private CommonAdapter mCommonAdapter;
    private ArrayList<MyBidBean.BiddingNeedsListEntity> mBiddingNeedsListEntities;
    private ArrayList<MyBidBean.BiddingNeedsListEntity> mBiddingNeedsListEntityArrayList;
    private Map<String, String> living_room;
    private Map<String, String> style;
    private Map<String, String> area;
    private Map<String, String> room;
    private Map<String, String> toilet;
    private Map<String, String> space;
}
