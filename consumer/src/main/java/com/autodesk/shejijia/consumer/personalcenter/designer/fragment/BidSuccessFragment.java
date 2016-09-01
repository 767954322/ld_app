package com.autodesk.shejijia.consumer.personalcenter.designer.fragment;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.personalcenter.designer.activity.DesignerOrderActivity;
import com.autodesk.shejijia.consumer.personalcenter.designer.activity.DesignerOrderBeiShuActivity;
import com.autodesk.shejijia.consumer.personalcenter.designer.activity.MyBidActivity;
import com.autodesk.shejijia.consumer.personalcenter.designer.entity.MyBidBean;
import com.autodesk.shejijia.consumer.utils.AppJsonFileReader;
import com.autodesk.shejijia.shared.components.common.uielements.pulltorefresh.PullListView;
import com.autodesk.shejijia.shared.components.common.uielements.pulltorefresh.PullToRefreshLayout;
import com.autodesk.shejijia.shared.components.common.utility.CommonUtils;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.framework.adapter.CommonAdapter;
import com.autodesk.shejijia.shared.framework.adapter.CommonViewHolder;
import com.autodesk.shejijia.shared.framework.fragment.BaseFragment;

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

    private static final String IS_BEI_SHU = "1";

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
        return new CommonAdapter<MyBidBean.BiddingNeedsListEntity>(UIUtils.getContext(), mBiddingNeedsListEntities, R.layout.item_lv_bingo_fragment) {
            @Override
            public void convert(CommonViewHolder holder, MyBidBean.BiddingNeedsListEntity biddingNeedsListEntity) {

                holder.setText(R.id.tv_bin_go_address, biddingNeedsListEntity.getNeeds_name());
                holder.setText(R.id.tv_bin_go_time, biddingNeedsListEntity.getEnd_day());
                String lRoom = biddingNeedsListEntity.getRoom();
                String livingRoom = biddingNeedsListEntity.getLiving_room();
                String mToilet = biddingNeedsListEntity.getToilet();
                String house_area = biddingNeedsListEntity.getHouse_area();

                if (living_room.containsKey(livingRoom)) {
                    holder.setText(R.id.tv_bin_go_living_room, living_room.get(livingRoom));
                } else {
                    holder.setText(R.id.tv_bin_go_living_room, biddingNeedsListEntity.getLiving_room());
                }
                if (room.containsKey(lRoom)) {
                    holder.setText(R.id.tv_bin_go_room, room.get(lRoom));
                } else {
                    holder.setText(R.id.tv_bin_go_room, biddingNeedsListEntity.getRoom());
                }
                if (toilet.containsKey(mToilet)) {
                    holder.setText(R.id.tv_bin_go_toilet, toilet.get(mToilet));
                } else {
                    holder.setText(R.id.tv_bin_go_toilet, biddingNeedsListEntity.getToilet());
                }
                if (area.containsKey(house_area)) {
                    holder.setText(R.id.tv_bin_go_area, area.get(house_area) + "m²");
                } else {
                    holder.setText(R.id.tv_bin_go_area, biddingNeedsListEntity.getHouse_area() + "m²");
                }
                String house_type = biddingNeedsListEntity.getHouse_type();
                if (space.containsKey(house_type)) {
                    holder.setText(R.id.tv_bin_go_type, space.get(house_type));
                } else {
                    holder.setText(R.id.tv_bin_go_type, biddingNeedsListEntity.getHouse_type());
                }
                String renovation_style = biddingNeedsListEntity.getRenovation_style();
                if (style.containsKey(renovation_style)) {
                    holder.setText(R.id.tv_bin_go_style, style.get(renovation_style));
                } else {
                    holder.setText(R.id.tv_bin_go_style, biddingNeedsListEntity.getRenovation_style());
                }
                holder.setText(R.id.tv_bin_go_budget, UIUtils.getString(R.string.my_bid_black) + biddingNeedsListEntity.getRenovation_budget());

//                final String is_beishu = biddingNeedsListEntity.getIs_beishu();

                final int is_loho = ((MyBidActivity) getActivity()).is_loho;
                holder.getView(R.id.item_lv_bingo_fragment_order).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (IS_BEI_SHU.equals(is_loho)) {
                            /// 北舒 .
                            CommonUtils.launchActivity(getActivity(), DesignerOrderBeiShuActivity.class);
                        } else {
                            CommonUtils.launchActivity(getActivity(), DesignerOrderActivity.class);
                        }
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
