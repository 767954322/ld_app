package com.autodesk.shejijia.consumer.personalcenter.resdecoration.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.consumer.manager.WkTemplateConstants;
import com.autodesk.shejijia.consumer.personalcenter.consumer.activity.AmendDemandActivity;
import com.autodesk.shejijia.consumer.personalcenter.resdecoration.entity.DecorationBiddersBean;
import com.autodesk.shejijia.consumer.personalcenter.resdecoration.entity.DecorationDetailBean;
import com.autodesk.shejijia.consumer.utils.ApiStatusUtil;
import com.autodesk.shejijia.consumer.utils.AppJsonFileReader;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.uielements.CustomProgress;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.AlertView;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.OnItemClickListener;
import com.autodesk.shejijia.shared.components.common.utility.ConvertUtils;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.MPNetworkUtils;
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

    /**
     * is_public=1,表示终止了需求
     */
    private static final String IS_PUBLIC = "1";

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
    private String wk_template_id;
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
        wk_template_id = (String) extras.get(Constant.ConsumerDecorationFragment.WK_TEMPLATE_ID);

        /**
         * 控制修改和删除按钮显示、隐藏
         */
        if (!WkTemplateConstants.IS_AVERAGE.equals(wk_template_id)) {
            mLlDemandModify.setVisibility(View.GONE);
        }
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
                bundle.putString(Constant.ConsumerDecorationFragment.WK_TEMPLATE_ID, wk_template_id);
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
                if (!CustomProgress.dialog.isShowing()) {
                    ApiStatusUtil.getInstance().apiStatuError(volleyError, DecorationDetailActivity.this);
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
                CustomProgress.cancelDialog();
                ApiStatusUtil.getInstance().apiStatuError(volleyError, DecorationDetailActivity.this);
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
        String house_area = demandDetailBean.getHouse_area() + "㎡";
        String contacts_mobile = demandDetailBean.getContacts_mobile();
        String custom_string_status = demandDetailBean.getCustom_string_status();
        String decoration_budget = demandDetailBean.getDecoration_budget();
        String province_name = demandDetailBean.getProvince_name();
        String city_name = demandDetailBean.getCity_name();
        String design_budget = demandDetailBean.getDesign_budget();
        String decoration_style = demandDetailBean.getDecoration_style();

        String house_type = demandDetailBean.getHouse_type();
        String room = demandDetailBean.getRoom();
        String toilet = demandDetailBean.getToilet();
        String living_room = demandDetailBean.getLiving_room();

        district_name = UIUtils.getNoStringIfEmpty(district_name);
        String address = province_name + city_name + district_name;

        decoration_style = convertEn2Cn(mStyleJson, decoration_style);
        house_type = convertEn2Cn(mSpaceJson, house_type);
        room = convertEn2Cn(mRoomJson, room);
        living_room = convertEn2Cn(mLivingRoomJson, living_room);
        toilet = convertEn2Cn(mToiletJson, toilet);

        String livingRoom_room_toilet = room + living_room + toilet;

        mTvAmendRoomType.setText(UIUtils.getNoSelectIfEmpty(livingRoom_room_toilet));
        mTvAmendStyle.setText(UIUtils.getNoSelectIfEmpty(decoration_style));
        mTvAmendHouseType.setText(UIUtils.getNoSelectIfEmpty(house_type) );
        mTvAmendName.setText(UIUtils.getNoDataIfEmpty(contacts_name));
        mTvAmendMobile.setText(UIUtils.getNoDataIfEmpty(contacts_mobile));
        mTvDesignBudget.setText(UIUtils.getNoSelectIfEmpty(design_budget));
        mTvAmendBudget.setText(UIUtils.getNoSelectIfEmpty(decoration_budget) );
        mTvAmendArea.setText(UIUtils.getNoSelectIfEmpty(house_area) );
        mTvAddress.setText(UIUtils.getNoDataIfEmpty(address));
        mTvCommunityName.setText(UIUtils.getNoDataIfEmpty(community_name));
        mTvPublicTime.setText(UIUtils.getNoDataIfEmpty(publish_time));

        mTvDecorationName.setText(contacts_name + "/" + community_name);
        if (WkTemplateConstants.IS_AVERAGE.equals(wk_template_id)) {
            mLlDemandModify.setVisibility(View.VISIBLE);

            /**
             * 控制修改和终止按钮是否可以点击
             * 审核通过，考虑应标人数情况,大于0时候，隐藏修改终止按钮
             *
             * custom_string_status 审核状态
             */
            List<DecorationBiddersBean> bidders = demandDetailBean.getBidders();
            if (Constant.NumKey.CERTIFIED_PASS.equals(custom_string_status)
                    || Constant.NumKey.CERTIFIED_PASS_1.equals(custom_string_status)) {

                setButtonGray(mBtnAmendDemand);

                if (null != bidders) {
                    if (bidders.size() <= 0) {
                        mLlDemandModify.setVisibility(View.VISIBLE);
                        mBtnAmendDemand.setVisibility(View.VISIBLE);
                        mBtnStopDemand.setVisibility(View.VISIBLE);
                        setButtonGray(mBtnAmendDemand);
                    } else {
                        mLlDemandModify.setVisibility(View.GONE);
                    }
                }
            } else {
                mLlDemandModify.setVisibility(View.VISIBLE);
                mBtnAmendDemand.setVisibility(View.VISIBLE);
                mBtnStopDemand.setVisibility(View.VISIBLE);
            }

            /**
             * 当is_public为1,表示需求终止
             */
            String is_public = demandDetailBean.getIs_public();
            if (IS_PUBLIC.equals(is_public)) {
                mLlDemandModify.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 按钮置灰，不可点击
     */
    private void setButtonGray(Button btn) {
        btn.setClickable(false);
        btn.setPressed(false);
        btn.setTextColor(UIUtils.getColor(R.color.white));
        btn.setBackground(UIUtils.getDrawable(R.drawable.bg_common_btn_gray));
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