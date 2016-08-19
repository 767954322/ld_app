package com.autodesk.shejijia.consumer.personalcenter.resdecoration.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.consumer.personalcenter.consumer.activity.AmendDemandActivity;
import com.autodesk.shejijia.consumer.personalcenter.resdecoration.entity.DecorationBiddersBean;
import com.autodesk.shejijia.consumer.personalcenter.resdecoration.entity.DecorationDetailBean;
import com.autodesk.shejijia.consumer.utils.AppJsonFileReader;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.uielements.CustomProgress;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.AlertView;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.OnItemClickListener;
import com.autodesk.shejijia.shared.components.common.utility.ConvertUtils;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.MPNetworkUtils;
import com.autodesk.shejijia.shared.components.common.utility.StringUtils;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.framework.activity.NavigationBarActivity;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * @author he.liu .
 * @version v1.0 .
 * @date 2016-8-18 .
 * @file DecorationDetailActivity.java .
 * @brief 需求详情页面 .
 */
public class DecorationDetailActivity extends NavigationBarActivity implements View.OnClickListener, OnItemClickListener {

    /// 节点11,邀请量房状态 .
    private static final int IS_BIDING = 11;

    private TextView mTvNeedsId;
    private TextView mTvDecorationName;
    private TextView mTvAmendName;
    private TextView mTvDesignBudget;
    private TextView mTvAmendBudget;
    private TextView mTvAmendHouseType;
    private TextView mTvAmendMobile;
    private TextView mTvAmendArea;
    private TextView mTvAmendRoomType;
    private TextView mTvAmendStyle;
    private TextView mTvCommunityName;
    private TextView mTvAddress;
    private TextView mTvPublicTime;
    private Button mBtnAmendDemand;
    private Button mBtnStopDemand;
    private LinearLayout mLlDemandModify;
    private AlertView mStopDemandAlertView;
    private AlertView mStopDemandSuccessAlertView;

    private String needs_id;
    private DecorationDetailBean mDecorationDetailBean;
    private Map<String, String> mStyleJson, mSpaceJson, mLivingRoomJson, mRoomJson, mToiletJson;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_decoration_detail;
    }

    @Override
    protected void initView() {
        super.initView();
        mTvAmendName = (TextView) findViewById(R.id.et_amend_amend_name);
        mTvAmendMobile = (TextView) findViewById(R.id.et_issue_amend_mobile);
        mTvDesignBudget = (TextView) findViewById(R.id.tv_amend_design_budget);
        mTvAmendBudget = (TextView) findViewById(R.id.tv_aemand_budget);
        mTvAmendHouseType = (TextView) findViewById(R.id.tv_amend_house_type);
        mTvAmendArea = (TextView) findViewById(R.id.et_ademand_area);
        mTvAmendRoomType = (TextView) findViewById(R.id.tv_amend_room_type);
        mTvAmendStyle = (TextView) findViewById(R.id.tv_amend_style);
        mTvAddress = (TextView) findViewById(R.id.tv_issue_address);
        mTvCommunityName = (TextView) findViewById(R.id.et_issue_demand_detail_address);
        mTvPublicTime = (TextView) findViewById(R.id.tv_public_time);
        mBtnAmendDemand = (Button) findViewById(R.id.btn_fitment_amend_demand);
        mBtnStopDemand = (Button) findViewById(R.id.btn_fitment_stop_demand);
        mTvNeedsId = (TextView) findViewById(R.id.tv_decoration_needs_id);
        mTvDecorationName = (TextView) findViewById(R.id.tv_decoration_name);

        mLlDemandModify = (LinearLayout) findViewById(R.id.ll_demand_modify);
    }

    @Override
    protected void initExtraBundle() {
        super.initExtraBundle();
        Bundle extras = getIntent().getExtras();
        needs_id = (String) extras.get(Constant.ConsumerDecorationFragment.NEED_ID);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);

        setTitleForNavbar(UIUtils.getString(R.string.title_decoration_detail));
        mTvNeedsId.setText(needs_id);

        getJsonFileReader();

        initAlertView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAmendDemand(needs_id);
    }

    @Override
    protected void initListener() {
        super.initListener();
        mBtnAmendDemand.setOnClickListener(this);
        mBtnStopDemand.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_fitment_amend_demand: ///修改需求 .
                Intent intent = new Intent(this, AmendDemandActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(Constant.ConsumerDecorationFragment.NEED_ID, needs_id);
                intent.putExtras(bundle);
                startActivityForResult(intent, 0);
                break;

            case R.id.btn_fitment_stop_demand: /// 终止应标 .
                mStopDemandAlertView.show();
                break;
        }
    }

    /**
     * 获取当前需求
     */
    public void getAmendDemand(String need_id) {
        CustomProgress.show(this, "", false, null);
        OkJsonRequest.OKResponseCallback okResponseCallback = new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                CustomProgress.cancelDialog();
                String info = GsonUtil.jsonToString(jsonObject);
                mDecorationDetailBean = GsonUtil.jsonToBean(info, DecorationDetailBean.class);
                updateViewFromData(mDecorationDetailBean);
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                MPNetworkUtils.logError(TAG, volleyError);
                CustomProgress.cancelDialog();
                if (!CustomProgress.dialog.isShowing()){
                    new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.network_error), null, new String[]{"确定"}, null, DecorationDetailActivity.this,
                            AlertView.Style.Alert, null).show();
                }
            }
        };
        MPServerHttpManager.getInstance().getAmendDemand(need_id, okResponseCallback);
    }

    /**
     * 终止需求
     */
    private void sendStopDemand(String needs_id, int is_deleted) {
        OkJsonRequest.OKResponseCallback okResponseCallback = new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    CustomProgress.cancelDialog();
                    mStopDemandSuccessAlertView.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                MPNetworkUtils.logError(TAG, volleyError);
                CustomProgress.dialog.cancel();
                new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.network_error), null, new String[]{"确定"}, null, DecorationDetailActivity.this,
                        AlertView.Style.Alert, null).show();
            }
        };
        MPServerHttpManager.getInstance().getStopDesignerRequirement(needs_id,
                is_deleted, okResponseCallback);
    }


    /**
     * 　获取数据，更新页面
     */
    private void updateViewFromData(DecorationDetailBean demandDetailBean) {
        String contacts_name = demandDetailBean.getContacts_name();
        String district_name = demandDetailBean.getDistrict_name();
        String publish_time = demandDetailBean.getPublish_time();
        String community_name = demandDetailBean.getCommunity_name();
        String house_area = demandDetailBean.getHouse_area();
        String contacts_mobile = demandDetailBean.getContacts_mobile();
        String custom_string_status = demandDetailBean.getCustom_string_status();
        String decoration_budget = demandDetailBean.getDecoration_budget();
        String province_name = demandDetailBean.getProvince_name();
        String city_name = demandDetailBean.getCity_name();
        String district = demandDetailBean.getDistrict();
        String design_budget = demandDetailBean.getDesign_budget();
        String decoration_style = demandDetailBean.getDecoration_style();

        String house_type = demandDetailBean.getHouse_type();
        String room = demandDetailBean.getRoom();
        String toilet = demandDetailBean.getToilet();
        String living_room = demandDetailBean.getLiving_room();

        /**
         * 当is_public为1,表示当前需求已经终止，隐藏修改终止需求按钮
         */
        String is_public = demandDetailBean.getIs_public();
        if (Constant.NumKey.ONE.equals(is_public)) {
            mLlDemandModify.setVisibility(View.GONE);
        } else {
            mLlDemandModify.setVisibility(View.VISIBLE);
        }

        district_name = TextUtils.isEmpty(district)
                || "none".equals(district) ||
                TextUtils.isEmpty(district_name)
                || district_name.equals("none") ? "" : district_name;

        String address = province_name + city_name + district_name;

        decoration_style = convertEn2Cn(mStyleJson, decoration_style);
        house_type = convertEn2Cn(mSpaceJson, house_type);
        room = convertEn2Cn(mRoomJson, room);
        living_room = convertEn2Cn(mLivingRoomJson, living_room);
        toilet = convertEn2Cn(mToiletJson, toilet);

        String livingRoom_room_toilet = room + living_room + toilet;

        mTvAmendRoomType.setText(TextUtils.isEmpty(livingRoom_room_toilet) ? UIUtils.getString(R.string.no_data) : livingRoom_room_toilet);
        mTvAmendStyle.setText(TextUtils.isEmpty(decoration_style) ? UIUtils.getString(R.string.content_others) : decoration_style);
        mTvAmendHouseType.setText(TextUtils.isEmpty(house_type) ? UIUtils.getString(R.string.content_others) : house_type);
        mTvAmendName.setText(TextUtils.isEmpty(contacts_name) ? UIUtils.getString(R.string.no_data) : contacts_name);
        mTvAmendMobile.setText(TextUtils.isEmpty(contacts_mobile) ? UIUtils.getString(R.string.no_data) : contacts_mobile);
        mTvDesignBudget.setText(TextUtils.isEmpty(design_budget) ? UIUtils.getString(R.string.no_data) : design_budget);
        mTvAmendBudget.setText(TextUtils.isEmpty(decoration_budget) ? UIUtils.getString(R.string.no_data) : decoration_budget);
        mTvAmendArea.setText(TextUtils.isEmpty(house_area) ? UIUtils.getString(R.string.no_data) : house_area);
        mTvAddress.setText(TextUtils.isEmpty(address) ? UIUtils.getString(R.string.no_data) : address);
        mTvCommunityName.setText(TextUtils.isEmpty(community_name) ? UIUtils.getString(R.string.no_data) : community_name);
        mTvPublicTime.setText(TextUtils.isEmpty(publish_time) ? UIUtils.getString(R.string.no_data) : publish_time);

        mTvDecorationName.setText(contacts_name + "/" + community_name);

        /**
         * 控制修改按钮置灰
         * custom_string_status 审核状态
         */
        if (Constant.NumKey.THREE.equals(custom_string_status)
                || Constant.NumKey.ZERO_THREE.equals(custom_string_status)) {
            setButtonGray(mBtnAmendDemand);
        }

        /**
         * 控制修改和终止按钮是否可以点击
         */
        List<DecorationBiddersBean> bidders = demandDetailBean.getBidders();
        DecorationBiddersBean biddersBean = null;
        if (null != bidders) {
            if (bidders.size() == 1) {
                biddersBean = bidders.get(0);
            } else if (bidders.size() > 1) {
                biddersBean = bidders.get(bidders.size() - 1);
            } else if (bidders.size() < 1) {
                return;
            }
            String wk_cur_sub_node_id = biddersBean.getWk_cur_sub_node_id();
            if (StringUtils.isNumeric(wk_cur_sub_node_id) && Integer.valueOf(wk_cur_sub_node_id) >= IS_BIDING) {
                setButtonGray(mBtnStopDemand);
                setButtonGray(mBtnAmendDemand);
            }
        }

        if (null != bidders && bidders.size() > 0) {
            setButtonGray(mBtnStopDemand);
            setButtonGray(mBtnAmendDemand);
        }
    }

    /**
     * 按钮置灰，不可点击
     */
    private void setButtonGray(Button btn) {
        btn.setClickable(false);
        btn.setPressed(false);
        btn.setTextColor(UIUtils.getColor(R.color.white));
        btn.setBackgroundColor(UIUtils.getColor(R.color.font_gray));
    }


    /**
     * 终止需求提示
     */
    @Override
    public void onItemClick(Object obj, int position) {

        if (obj == mStopDemandAlertView && position != AlertView.CANCELPOSITION) {
            CustomProgress.show(this, "", false, null);
            sendStopDemand(needs_id, 1);
        }

        if (obj == mStopDemandSuccessAlertView && position != AlertView.CANCELPOSITION) {
            finish();
        }
    }

    /**
     * 读取室卫厅转化json对象
     */
    private void getJsonFileReader() {
        mStyleJson = AppJsonFileReader.getStyle(DecorationDetailActivity.this);
        mSpaceJson = AppJsonFileReader.getSpace(DecorationDetailActivity.this);
        mLivingRoomJson = AppJsonFileReader.getLivingRoom(DecorationDetailActivity.this);
        mRoomJson = AppJsonFileReader.getRoomHall(DecorationDetailActivity.this);
        mToiletJson = AppJsonFileReader.getToilet(DecorationDetailActivity.this);
    }


    /**
     * 将英文转换为汉字
     */
    private String convertEn2Cn(Map<String, String> map, String key) {
        return ConvertUtils.getConvert2CN(map, key);
    }

    /**
     * 提示框.
     */
    private void initAlertView() {
        mStopDemandAlertView = new AlertView(
                UIUtils.getString(R.string.alert_decoration_title),
                UIUtils.getString(R.string.alert_decoration_message),
                UIUtils.getString(R.string.cancel), null,
                new String[]{UIUtils.getString(R.string.sure)}, this,
                AlertView.Style.Alert, this).setCancelable(true);

        mStopDemandSuccessAlertView = new AlertView(
                UIUtils.getString(R.string.tip),
                UIUtils.getString(R.string.termination_demand_success), null, null,
                new String[]{UIUtils.getString(R.string.sure)}, DecorationDetailActivity.this,
                AlertView.Style.Alert, this);
    }

}