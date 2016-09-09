package com.autodesk.shejijia.consumer.bidhall.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.bidhall.entity.BidHallDetailEntity;
import com.autodesk.shejijia.consumer.bidhall.entity.RealNameBean;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.consumer.manager.constants.JsonConstants;
import com.autodesk.shejijia.consumer.utils.ApiStatusUtil;
import com.autodesk.shejijia.consumer.utils.AppJsonFileReader;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.uielements.CustomProgress;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.AlertView;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.OnItemClickListener;
import com.autodesk.shejijia.shared.components.common.utility.ConvertUtils;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.MPNetworkUtils;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.framework.AdskApplication;
import com.autodesk.shejijia.shared.framework.activity.NavigationBarActivity;
import com.socks.library.KLog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * @author DongXueQiu .
 * @version 1.0 .
 * @date 16-6-8
 * @file BiddingHallDetailActivity.java  .
 * @brief 应标大厅详情.
 */
public class BiddingHallDetailActivity extends NavigationBarActivity implements View.OnClickListener {

    private static final int BIDDER_MAX = 3; /// 最大应标人数 .

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_bid_hall_detail;
    }

    @Override
    protected void initView() {
        super.initView();
        mTvProjectNeedsId = (TextView) findViewById(R.id.tv_bid_prj_number);
        mTvHouseArea = (TextView) findViewById(R.id.tv_bid_hall_detail_area);
        mTvName = (TextView) findViewById(R.id.tv_bid_hall_detail_name);
        mTvPhone = (TextView) findViewById(R.id.tv_bid_hall_detail_phone);
        mTvHouseStyle = (TextView) findViewById(R.id.tv_bid_hall_detail_style);
        mTvHouseModel = (TextView) findViewById(R.id.tv_bid_hall_detail_house_type);
        mTvCommunityName = (TextView) findViewById(R.id.tv_bid_hall_detail_village_name);
        mTvPublishTime = (TextView) findViewById(R.id.tv_bid_hall_detail_release_time);
        mTvHouseType = (TextView) findViewById(R.id.tv_bid_hall_detail_property_type);
        mTvDesignBudget = (TextView) findViewById(R.id.tv_bid_hall_detail_project_budget);
        mTvDecorationBudget = (TextView) findViewById(R.id.tv_bid_hall_detail_decorate_budget);
        mTvProjectAddress = (TextView) findViewById(R.id.tv_bid_hall_detail_project_address);
        mBtnSendBid = (Button) findViewById(R.id.btn_bid_hall_detail_bid);
    }

    @Override
    protected void initExtraBundle() {
        super.initExtraBundle();
        Bundle bundle = getIntent().getExtras();
        needs_id = bundle.getString(Constant.DemandDetailBundleKey.DEMAND_NEEDS_ID);
        demand_type = bundle.getString(Constant.DemandDetailBundleKey.DEMAND_TYPE);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        showBidState();
        showLoadingIndicator(this, UIUtils.getString(R.string.getting_data));
        getBidHallDetailData(needs_id);
    }

    @Override
    protected void initListener() {
        super.initListener();
        mBtnSendBid.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_bid_hall_detail_bid:
                if (Constant.DemandDetailBundleKey.TYPE_BEING_FRAGMENT.equals(demand_type)) {
                    ///　终止应标 .
                } else {
                    MemberEntity memberEntity = AdskApplication.getInstance().getMemberEntity();
                    hsUid = memberEntity.getHs_uid();
                    designer_id = memberEntity.getMember_id();
                    CustomProgress.show(BiddingHallDetailActivity.this, UIUtils.getString(R.string.is_biding), false, null);
                    getRealName(hsUid, designer_id);
                }
                break;
            default:
                break;
        }
    }

    /**
     * 应标大厅详情数据
     *
     * @param needs_id 项目编号
     */
    public void getBidHallDetailData(String needs_id) {
        OkJsonRequest.OKResponseCallback okResponseCallback = new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                CustomProgress.cancelDialog();
                String str = GsonUtil.jsonToString(jsonObject);
                mBidHallEntity = GsonUtil.jsonToBean(str, BidHallDetailEntity.class);
                bid_status = mBidHallEntity.getBidding_status();
                updateViewFromBidHallDetailData();
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                MPNetworkUtils.logError(TAG, volleyError);
                CustomProgress.cancelDialog();
                ApiStatusUtil.getInstance().apiStatuError(volleyError, BiddingHallDetailActivity.this);
            }
        };
        MPServerHttpManager.getInstance().getBidHallDetailData(needs_id, okResponseCallback);
    }

    /**
     * 我要应标
     *
     * @param declaration
     * @param user_name
     * @param needs_id
     * @param designer_id
     */
    public void sendBidDemand(String declaration, String user_name, String needs_id, String designer_id) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(JsonConstants.JSON_BID_DETAIL_DECLARATION, declaration);
            jsonObject.put(JsonConstants.JSON_BID_DETAIL_USER_NAME, user_name);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        OkJsonRequest.OKResponseCallback okResponseCallback = new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                CustomProgress.cancelDialog();
                String info = GsonUtil.jsonToString(jsonObject);
                KLog.json(info);
                getAlertView(UIUtils.getString(R.string.designer_bid_detail_success), null, false).show();
                mBtnSendBid.setEnabled(false);
                mBtnSendBid.setBackgroundResource(R.drawable.bg_common_btn_pressed);
                mBtnSendBid.setTextColor(UIUtils.getColor(R.color.white));
                mBtnSendBid.setText(UIUtils.getString(R.string.bided));
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                MPNetworkUtils.logError(TAG, volleyError);
                CustomProgress.dialog.cancel();
                ApiStatusUtil.getInstance().apiStatuError(volleyError, BiddingHallDetailActivity.this);
            }
        };
        MPServerHttpManager.getInstance().sendBidDemand(jsonObject, needs_id, designer_id, okResponseCallback);
    }

    /**
     * 经过实名认证验证
     *
     * @param hsUid
     * @param designer_id
     */
    private void getRealName(final String hsUid, String designer_id) {
        final OkJsonRequest.OKResponseCallback okResponseCallback = new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                String info = GsonUtil.jsonToString(jsonObject);
                mRealNameBean = GsonUtil.jsonToBean(info, RealNameBean.class);
                KLog.json(info);
                updateViewFromRealNameData();
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                MPNetworkUtils.logError(TAG, volleyError);
                CustomProgress.cancelDialog();
                getAlertView("", null, false);
            }
        };
        MPServerHttpManager.getInstance().getRealName(hsUid, designer_id, okResponseCallback);
    }

    /**
     * 获取姓名信息，并更新
     */
    private void updateViewFromRealNameData() {
        if (mRealNameBean.getDesigner().getIs_real_name() == 2) {
            String measurement_price = mRealNameBean.getDesigner().getMeasurement_price();

            if (TextUtils.isEmpty(measurement_price)) {
                noSetMeasureFee();
                return;
            }
            if (bidder_count >= BIDDER_MAX) {
                bidCountFullDialog();
            } else {
                MemberEntity memberEntity = AdskApplication.getInstance().getMemberEntity();
                String designer_id = memberEntity.getAcs_member_id();
                String user_name = mRealNameBean.getUser_name();
                sendBidDemand("", user_name, needs_id, designer_id);
            }
        } else {
            new AlertView(UIUtils.getString(R.string.tip), "您还未通过实名认证\n请到网页端完成认证", null, null, new String[]{UIUtils.getString(R.string.sure)}, BiddingHallDetailActivity.this,
                    AlertView.Style.Alert, new OnItemClickListener() {
                @Override
                public void onItemClick(Object o, int position) {
                    if (position != AlertView.CANCELPOSITION) {
                        finish();
                    }
                }
            }).setCancelable(true).show();
        }

    }

    private void noSetMeasureFee() {
        getAlertView(UIUtils.getString(R.string.no_set_measure_fee), null, false).show();
    }

    /**
     * 获得数据更新界面
     */
    private void updateViewFromBidHallDetailData() {
        bidder_count = mBidHallEntity.getBidder_count();
        String house_type = mBidHallEntity.getHouse_type();
        String phone = mBidHallEntity.getContacts_mobile();
        String decoration_style = mBidHallEntity.getDecoration_style();

        String needs_id = mBidHallEntity.getNeeds_id();

        String living_room = mBidHallEntity.getLiving_room();
        String room = mBidHallEntity.getRoom();
        String toilet = mBidHallEntity.getToilet();
        String community_name = mBidHallEntity.getCommunity_name();
        String decoration_budget = mBidHallEntity.getDecoration_budget();
        String district_name = mBidHallEntity.getDistrict_name();

        Map<String, String> style = AppJsonFileReader.getStyle(BiddingHallDetailActivity.this);
        Map<String, String> roomJson = AppJsonFileReader.getRoomHall(BiddingHallDetailActivity.this);
        Map<String, String> toiletJson = AppJsonFileReader.getToilet(BiddingHallDetailActivity.this);
        Map<String, String> houseJson = AppJsonFileReader.getSpace(BiddingHallDetailActivity.this);
        Map<String, String> livingRoomJson = AppJsonFileReader.getLivingRoom(BiddingHallDetailActivity.this);

        String decoration_style_convert = ConvertUtils.getConvert2CN(style, decoration_style);
        String living_room_cn = ConvertUtils.getConvert2CN(livingRoomJson, living_room);
        String room_cn = ConvertUtils.getConvert2CN(roomJson, room);
        String toilet_cn = ConvertUtils.getConvert2CN(toiletJson, toilet);
        String house_type_convert = ConvertUtils.getConvert2CN(houseJson, house_type);

//        String livingRoom_room_toilet = UIUtils.getNoDataIfEmpty(room_cn) + UIUtils.getNoDataIfEmpty(living_room_cn) + UIUtils.getNoDataIfEmpty(toilet_cn);

        district_name = UIUtils.getNoStringIfEmpty(district_name);
        String projectAddress = UIUtils.getNoDataIfEmpty(mBidHallEntity.getProvince_name()) + " " + UIUtils.getNoDataIfEmpty(mBidHallEntity.getCity_name()) + " " + district_name;

        setTitleForNavbar(community_name);
        mTvProjectNeedsId.setText(needs_id);
        mTvHouseType.setText(UIUtils.getNoSelectIfEmpty(house_type_convert));
        mTvHouseStyle.setText(UIUtils.getNoSelectIfEmpty(decoration_style_convert));
        mTvHouseModel.setText(UIUtils.getNoSelectIfEmpty(living_room_cn)+UIUtils.getNoSelectIfEmpty(room_cn)+UIUtils.getNoSelectIfEmpty(toilet_cn));
        mTvName.setText(mBidHallEntity.getContacts_name());
        mTvDecorationBudget.setText(UIUtils.getNoSelectIfEmpty(decoration_budget));
        String design_budget = mBidHallEntity.getDesign_budget();
        mTvDesignBudget.setText(UIUtils.getNoSelectIfEmpty(design_budget));
        mTvHouseArea.setText(mBidHallEntity.getHouse_area() + "m²");
        mTvProjectAddress.setText(projectAddress);
        mTvPublishTime.setText(mBidHallEntity.getPublish_time());
        mTvCommunityName.setText(community_name);

        showOrHidePhone(phone);
    }

    /**
     * 是否已经应标
     */
    private void showBidState() {
        if (Constant.DemandDetailBundleKey.TYPE_DESIGNERORDER_ACTIVITY.equals(demand_type)) {
            mBtnSendBid.setVisibility(View.GONE);
        }
        if (Constant.DemandDetailBundleKey.TYPE_BEING_FRAGMENT.equals(demand_type)) {
            mBtnSendBid.setVisibility(View.GONE);
        }

        MemberEntity memberEntity = AdskApplication.getInstance().getMemberEntity();
        //判断是消费者，还是设计师，，从而区分消费者和设计师
        if (memberEntity != null && Constant.UerInfoKey.CONSUMER_TYPE.equals(memberEntity.getMember_type())) {

            mBtnSendBid.setVisibility(View.GONE);
        }
    }

    /**
     * 应标状态
     *
     * @param phone 手机号码
     */
    private void showOrHidePhone(String phone) {
        if (Constant.DemandDetailBundleKey.TYPE_DESIGNERORDER_ACTIVITY.equals(demand_type)) {
            mTvPhone.setText(phone);
        } else if (Constant.DemandDetailBundleKey.TYPE_BEING_FRAGMENT.equals(demand_type)) {
            mBtnSendBid.setVisibility(View.GONE);
            mTvPhone.setText(UIUtils.getString(R.string.shall_visible_bid_after));
        }

        if (bid_status) {
            mBtnSendBid.setEnabled(false);
            mBtnSendBid.setBackgroundResource(R.drawable.bg_common_btn_pressed);
            mBtnSendBid.setTextColor(UIUtils.getColor(R.color.white));
            mBtnSendBid.setText(UIUtils.getString(R.string.bided));
            mTvPhone.setText(phone);
        } else {
            mTvPhone.setText(UIUtils.getString(R.string.shall_visible_bid_after));
        }
    }

    /**
     * 获取AlertView 弹窗
     *
     * @param content 提示内容
     * @return AlertView
     */
    private AlertView getAlertView(String content, OnItemClickListener onItemClickListener, boolean isCancelable) {
        return new AlertView(UIUtils.getString(R.string.tip), content, null, null, new String[]{UIUtils.getString(R.string.sure)}, BiddingHallDetailActivity.this,
                AlertView.Style.Alert, onItemClickListener).setCancelable(isCancelable);
    }

    /**
     * 显示等待框
     */
    private void showLoadingIndicator(Activity activity, String content) {
        if (null != CustomProgress.dialog && !CustomProgress.dialog.isShowing()) {
            CustomProgress.show(activity, content, false, null);
        }
    }

    /**
     * 应标成功提示
     */
    public void bidCountFullDialog() {
        getAlertView(UIUtils.getString(R.string.should_number_full), null, false).show();
    }

    private TextView mTvName;
    private TextView mTvPhone;
    private TextView mTvDecorationBudget;
    private TextView mTvDesignBudget;
    private TextView mTvHouseType;
    private TextView mTvHouseArea;
    private TextView mTvHouseModel;
    private TextView mTvHouseStyle;
    private TextView mTvProjectAddress;
    private TextView mTvCommunityName;
    private TextView mTvPublishTime;
    private TextView mTvProjectNeedsId;
    private Button mBtnSendBid;

    private String needs_id;
    private String hsUid;
    private String designer_id;
    private String demand_type;
    private int bidder_count;
    private boolean bid_status;

    private RealNameBean mRealNameBean;
    private BidHallDetailEntity mBidHallEntity;

}
