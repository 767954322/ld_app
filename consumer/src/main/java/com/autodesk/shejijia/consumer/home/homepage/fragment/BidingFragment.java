package com.autodesk.shejijia.consumer.home.homepage.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.bidhall.activity.BiddingHallDetailActivity;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.consumer.personalcenter.designer.entity.MyBidBean;
import com.autodesk.shejijia.consumer.utils.AppJsonFileReader;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.AlertView;
import com.autodesk.shejijia.shared.components.common.uielements.pulltorefresh.PullListView;
import com.autodesk.shejijia.shared.components.common.uielements.pulltorefresh.PullToRefreshLayout;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.MPNetworkUtils;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.components.im.activity.ChatRoomActivity;
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
 * @author yaoxuehua .
 * @version 1.0 .
 * @date 16-7-20
 * @file DesignerOrderBeiShuActivity.java  .
 * @brief 应标中fragment.
 */
public class BidingFragment extends BaseFragment implements PullToRefreshLayout.OnRefreshListener{
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

    }

    @Override
    protected void initData() {

        setListener();
        mList = new ArrayList<>();
        beBeingList = new ArrayList<>();

        style = AppJsonFileReader.getStyle(getActivity());
        area = AppJsonFileReader.getArea(getActivity());
        space = AppJsonFileReader.getSpace(getActivity());
        living_room = AppJsonFileReader.getLivingRoom(getActivity());
        room = AppJsonFileReader.getRoomHall(getActivity());
        toilet = AppJsonFileReader.getToilet(getActivity());

        commonAdapter = getCommonAdapter();
        mListView.setAdapter(commonAdapter);
        addFooterViewForMListView();

        onWindowFocusChanged();

    }

    @Override
    public void onStart() {
        super.onStart();


        if (isLoginAgain){

            onWindowFocusChanged();
            isLoginAgain = false;
        }

    }

    private void onWindowFocusChanged() {
        // 第一次进入自动刷新
        if (isFirstIn) {
            mPullToRefreshLayout.autoRefresh();
            isFirstIn = false;
        }
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
        tv_empty_message.setText(UIUtils.getString(R.string.bidbiding_no_data_massage));
    }

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

        return new CommonAdapter<MyBidBean.BiddingNeedsListEntity>(UIUtils.getContext(), mList, R.layout.item_lv_my_bid_be_being) {
            @Override
            public void convert(CommonViewHolder holder, final MyBidBean.BiddingNeedsListEntity biddingNeedsListEntity) {
                holder.setText(R.id.tv_be_being_address, biddingNeedsListEntity.getNeeds_name());
                holder.setText(R.id.tv_be_being_time, biddingNeedsListEntity.getEnd_day());
                String livingRoom = biddingNeedsListEntity.getLiving_room();
                String roomHall = biddingNeedsListEntity.getRoom();
                String mToilet = biddingNeedsListEntity.getToilet();
                String house_area = biddingNeedsListEntity.getHouse_area();
                String house_type = biddingNeedsListEntity.getHouse_type();
                String renovation_style = biddingNeedsListEntity.getRenovation_style();
                final String needs_id = biddingNeedsListEntity.getNeeds_id();

                if (living_room.containsKey(livingRoom)) {
                    holder.setText(R.id.tv_be_being_living_room, living_room.get(livingRoom));
                } else {
                    holder.setText(R.id.tv_be_being_living_room, biddingNeedsListEntity.getLiving_room());
                }
                if (room.containsKey(roomHall)) {
                    holder.setText(R.id.tv_be_being_room, room.get(roomHall));
                } else {
                    holder.setText(R.id.tv_be_being_room, biddingNeedsListEntity.getRoom());
                }
                if (toilet.containsKey(mToilet)) {
                    holder.setText(R.id.tv_be_being_toilet, toilet.get(mToilet));
                } else {
                    holder.setText(R.id.tv_be_being_toilet, biddingNeedsListEntity.getToilet());
                }
                if (area.containsKey(house_area)) {
                    holder.setText(R.id.tv_be_being_area, area.get(house_area) + "m²");
                } else {
                    holder.setText(R.id.tv_be_being_area, biddingNeedsListEntity.getHouse_area() + "m²");
                }
                if (space.containsKey(house_type)) {
                    holder.setText(R.id.tv_be_being_type, space.get(house_type));
                } else {
                    holder.setText(R.id.tv_be_being_type, biddingNeedsListEntity.getHouse_type());
                }
                if (style.containsKey(renovation_style)) {
                    holder.setText(R.id.tv_be_being_style, style.get(renovation_style));
                } else {
                    holder.setText(R.id.tv_be_being_style, biddingNeedsListEntity.getRenovation_style());
                }
                holder.setText(R.id.tv_be_being_budget, biddingNeedsListEntity.getRenovation_budget());

                holder.getView(R.id.btn_be_being_demand_details).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity(), BiddingHallDetailActivity.class);
                        Bundle bundle = new Bundle();
                        KLog.d(TAG, needs_id);
                        intent.putExtra(Constant.DemandDetailBundleKey.DEMAND_NEEDS_ID, needs_id);
                        intent.putExtra(Constant.DemandDetailBundleKey.DEMAND_TYPE, Constant.DemandDetailBundleKey.TYPE_BEING_FRAGMENT);
                        intent.putExtra(Constant.DemandDetailBundleKey.DEMAND_BID_STATUS, true);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });

                holder.getView(R.id.into_charRoom).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MemberEntity mMemberEntity = AdskApplication.getInstance().getMemberEntity();
                        String loggedInUserId = mMemberEntity.getAcs_member_id();
                        String member_type = mMemberEntity.getMember_type();
                        String designer_thread_id = biddingNeedsListEntity.getBidder().getDesign_thread_id();
                        String userName = biddingNeedsListEntity.getUser_name();
                        String acs_member_id = biddingNeedsListEntity.getAcs_member_id();

                        Intent intent = new Intent(getActivity(), ChatRoomActivity.class);
                        intent.putExtra(ChatRoomActivity.ACS_MEMBER_ID, loggedInUserId);
                        intent.putExtra(ChatRoomActivity.RECIEVER_USER_NAME, userName);
                        intent.putExtra(ChatRoomActivity.ASSET_ID, needs_id);
                        intent.putExtra(ChatRoomActivity.RECIEVER_USER_ID, acs_member_id);
                        intent.putExtra(ChatRoomActivity.THREAD_ID, designer_thread_id);
                        intent.putExtra(ChatRoomActivity.MEMBER_TYPE, member_type);
                        getActivity().startActivity(intent);
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
                String str = GsonUtil.jsonToString(jsonObject);
                MyBidBean myBidBean = GsonUtil.jsonToBean(str, MyBidBean.class);
                onFragmentShown(myBidBean.getBidding_needs_list());
                updateViewFromData(myBidBean);
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                MPNetworkUtils.logError(TAG, volleyError);
                mPullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.FAIL);
                new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.network_error), null, new String[]{UIUtils.getString(R.string.sure)}, null, getActivity(),
                        AlertView.Style.Alert, null).show();
            }
        };
        MPServerHttpManager.getInstance().getMyBidData(memType, acsToken, offset, limit, designer_id, callback);
    }

    //判断重新登陆时要及时刷新相关的fragment
    public void setRestartFragment(boolean isLoginAgainJust){

        this.isLoginAgain = isLoginAgainJust;

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
    private boolean isLoginAgain = false;

    ///　集合，类.
    private ArrayList<MyBidBean.BiddingNeedsListEntity> mList;
    private List<MyBidBean.BiddingNeedsListEntity> beBeingList;
    private Map<String, String> living_room;
    private Map<String, String> style;
    private Map<String, String> area;
    private Map<String, String> room;
    private Map<String, String> toilet;
    private Map<String, String> space;
    private CommonAdapter commonAdapter;
}
