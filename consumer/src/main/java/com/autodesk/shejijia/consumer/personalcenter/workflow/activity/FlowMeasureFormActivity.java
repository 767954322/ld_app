package com.autodesk.shejijia.consumer.personalcenter.workflow.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.consumer.manager.constants.JsonConstants;
import com.autodesk.shejijia.consumer.personalcenter.workflow.entity.MPMeasureFormBean;
import com.autodesk.shejijia.consumer.utils.AppJsonFileReader;
import com.autodesk.shejijia.consumer.utils.SplitStringUtils;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.uielements.CustomProgress;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.AlertView;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.OnItemClickListener;
import com.autodesk.shejijia.shared.components.common.uielements.reusewheel.utils.TimePickerView;
import com.autodesk.shejijia.shared.components.common.utility.DateUtil;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.LogUtils;
import com.autodesk.shejijia.shared.components.common.utility.MPNetworkUtils;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author he.liu .
 * @version 1.0 .
 * @date 16-6-7
 * @file FlowMeasureFormActivity.java  .
 * @brief 全流程量房表单类 .
 */
public class FlowMeasureFormActivity extends BaseWorkFlowActivity implements OnItemClickListener, View.OnClickListener, TextWatcher {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_measure_empty_form;
    }

    @Override
    protected void initView() {
        super.initView();
        /* To prevent the pop-up keyboard */
        tvName = (TextView) findViewById(R.id.tvc_measure_form_name);
        tvPhone = (TextView) findViewById(R.id.tvc_measure_form_phone);

        rl_house_charge_show = (RelativeLayout) findViewById(R.id.rl_house_charge_show);
        consumer_house_charge_show = (LinearLayout) findViewById(R.id.consumer_house_charge_show);
        designer_house_charge_show = (LinearLayout) findViewById(R.id.designer_house_charge_show);
        tv_measure_form_designer_liangfangfeit = (EditText) findViewById(R.id.tv_measure_form_designer_liangfangfeit);
        consumer_rmb_show = (TextView) findViewById(R.id.consumer_rmb_show);

        tvc_measure_form_type = (TextView) findViewById(R.id.tvc_measure_form_house_type);
        tvc_measure_form_style = (TextView) findViewById(R.id.tvc_measure_form_house_style);
        tvProjectBudget = (TextView) findViewById(R.id.tvc_measure_form_project_budget);
        tvc_measure_fitment_budget = (TextView) findViewById(R.id.tvc_measure_fitment_budget);
        tvc_measure_form_area = (TextView) findViewById(R.id.tvc_measure_form_area);
        tvc_measure_form_house_type_model = (TextView) findViewById(R.id.tvc_measure_form_house_type_model);
        tvc_measure_form_time = (TextView) findViewById(R.id.tvc_measure_form_time);
        tvc_measure_form_address = (TextView) findViewById(R.id.tvc_measure_form_address);
        tvc_measure_form_estate = (TextView) findViewById(R.id.tvc_measure_form_estate);
        tvc_measure_form_fee = (TextView) findViewById(R.id.tv_measure_form_liangfangfei);
        ll_consumer_send = (LinearLayout) findViewById(R.id.ll_measure_form_consumer_send);
        ll_designer_send = (LinearLayout) findViewById(R.id.ll_measure_form_designer_send);
        ll_measure_form_style = (LinearLayout) findViewById(R.id.ll_measure_form_style);
        btn_measure_form_accept = (Button) findViewById(R.id.btn_measure_form_accept);
        btn_measure_form_send = (Button) findViewById(R.id.btn_measure_form_send);
        btn_measure_form_refuse = (Button) findViewById(R.id.btn_measure_form_refuse);
        tvWarmTips = (TextView) findViewById(R.id.tvWarmTips);
        tvWarmTipsContent = (TextView) findViewById(R.id.tvWarmTipsContent);
        rlMeasureWarmTips = (RelativeLayout) findViewById(R.id.rlMeasureWarmTips);
        rlWarmTips = (RelativeLayout) findViewById(R.id.rlWarmTips);
        tvMeasureWarmTips = (TextView) findViewById(R.id.tvMeasureWarmTips);
        tvMeasureWarmTipsContent = (TextView) findViewById(R.id.tvMeasureWarmTipsContent);
        tvIllustrate = (TextView) findViewById(R.id.tvIllustrate);
        ll_measure_form_style.setVisibility(View.GONE);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

        CustomProgress.show(this, "", false, null);
        super.initData(savedInstanceState);

        memType = memberEntity.getMember_type();
        user_id = memberEntity.getAcs_member_id();
        initAlertView();
    }

    @Override
    protected void onWorkFlowData() {
        super.onWorkFlowData();
        CustomProgress.cancelDialog();
//        if (!isElite(wk_cur_template_id)) {
//            setTitleForNavbar(getResources().getString(R.string.is_average_measure_house_form)); /// 竞优 .
//        } else {
//            setTitleForNavbar(getResources().getString(R.string.is_elite_measure_house_form)); ///  精选.
//        }
        //fix bug DP-6395
        setTitleForNavbar(getResources().getString(R.string.measure_house_form));
        updateDisplayData();
        updateRoomData();
        updateCityData();
        /// time .
        setMeasureTime();
    }


    @Override
    public void onItemClick(Object object, int position) {
        if (object == mRefuseMeasureHouseAlertView && position == 1) {
            CustomProgress.show(FlowMeasureFormActivity.this, null, false, null);
            refusedHouse(needs_id);
        }
        if (object == mAgreeResponseBidSuccessAlertView && position == 0) {
            Intent intent = new Intent();
            intent.putExtra(Constant.BundleKey.BUNDLE_SUB_NODE_ID, wk_cur_sub_node_id);
            intent.putExtra(Constant.BundleKey.BUNDLE_DESIGNER_ID, designer_id);
            setResult(RESULT_OK, intent);
            finish();
        }
        if (object == mAgreeMeasureHouseSuccessAlertView ||
                object == mAgreeMeasureHouseFailAlertView ||
                object == mAgreeResponseBidFailAlertView ||
                object == mRefuseMeasureHouseSuccessAlertView ||
                object == mRefuseMeasureHouseFailAlertView && position == 0) {
            finish();
        }
    }

    @Override
    protected void initListener() {
        super.initListener();
        btn_measure_form_accept.setOnClickListener(this);
        btn_measure_form_send.setOnClickListener(this);
        btn_measure_form_refuse.setOnClickListener(this);
        tvc_measure_form_time.setOnClickListener(this);
        tvIllustrate.setOnClickListener(this);

        tv_measure_form_designer_liangfangfeit.addTextChangedListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_measure_form_send: /// 发送量房表单 .
                JSONObject jsonObject = new JSONObject();
                try {
                    /**
                     * 获取系统当前时间
                     */
                    SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    String date = sDateFormat.format(new Date());

                    if (TextUtils.isEmpty(currentTime)) {
                        new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.amount_of_time_is_empty), null, null, new String[]{UIUtils.getString(R.string.sure)}, FlowMeasureFormActivity.this,
                                AlertView.Style.Alert, null).show();
                    } else {
                        jsonObject.put(JsonConstants.JSON_FLOW_MEASURE_FORM_SERVICE_DATE, currentTime);
                        jsonObject.put(JsonConstants.JSON_FLOW_MEASURE_FORM_USER_ID, user_id);
                        jsonObject.put(JsonConstants.JSON_FLOW_MEASURE_FORM_DESIGNER_ID, designer_id);
                        jsonObject.put(JsonConstants.JSON_FLOW_MEASURE_FORM_NEEDS_ID, needs_id);
                        jsonObject.put(JsonConstants.JSON_FLOW_MEASURE_FORM_USER_NAME, user_name);
                        jsonObject.put(JsonConstants.JSON_FLOW_MEASURE_FORM_MOBILE_NUMBER, mobile_number);
                        jsonObject.put(JsonConstants.JSON_FLOW_MEASURE_FORM_ORDER_TYPE, 0);

                        if (TextUtils.isEmpty(mThreead_id)) {
                            jsonObject.put(JsonConstants.JSON_MEASURE_FORM_THREAD_ID, ""); /// 聊天室ID，目前还没有做，先填写的是null
                        } else {
                            jsonObject.put(JsonConstants.JSON_MEASURE_FORM_THREAD_ID, mThreead_id);
                        }

                        if (TextUtils.isEmpty(amount) || "null".equals(amount)) {
                            jsonObject.put(JsonConstants.JSON_FLOW_MEASURE_FORM_AMOUNT, 0.00);
                        } else {
                            jsonObject.put(JsonConstants.JSON_FLOW_MEASURE_FORM_AMOUNT, amount);
                        }
                        jsonObject.put(JsonConstants.JSON_FLOW_MEASURE_FORM_ADJUSTMENT, 600);
                        jsonObject.put(JsonConstants.JSON_FLOW_MEASURE_FORM_CHANNEL_TYPE, "Android");

                        agreeResponseBid(jsonObject);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                break;
            case R.id.tvIllustrate: /// 量房费说明 .
                new AlertView(UIUtils.getString(R.string.illustrate), UIUtils.getString(R.string.warm_tips_content), null, null, new String[]{UIUtils.getString(R.string.finish_cur_pager)}, FlowMeasureFormActivity.this,
                        AlertView.Style.Alert, null).show();

                break;

            case R.id.btn_measure_form_accept: /// 同意量房 .
                agreeMeasureHouse(needs_id);
                break;

            case R.id.btn_measure_form_refuse: /// 拒绝量房 .
                mRefuseMeasureHouseAlertView.show();
                break;

            case R.id.tvc_measure_form_time: /// 设置量房时间 .
                if (pvTime != null) {
                    pvTime.show();
                }
                break;
        }
    }

    /// 更新显示状态钮得消失显示 .
    private void updateDisplayData() {

        if (TextUtils.isEmpty(wk_cur_sub_node_id)) {
            return;
        }
        int wk_cur_sub_node_id_i = Integer.valueOf(wk_cur_sub_node_id);


        if (memType.equals(Constant.UerInfoKey.CONSUMER_TYPE)) { // 消费者
            designer_house_charge_show.setVisibility(View.GONE);
            rlWarmTips.setVisibility(View.GONE);
            if (wk_cur_sub_node_id_i >= 11) {
                consumer_house_charge_show.setVisibility(View.VISIBLE);
                ll_consumer_send.setVisibility(View.GONE);
                tvIllustrate.setVisibility(View.GONE);
                tvc_measure_form_time.setClickable(false);
                String timerStr = mBidders.get(0).getMeasure_time();

                if (TextUtils.isEmpty(timerStr) || timerStr.equals("null")) {
                    tvc_measure_form_time.setText("");
                } else {
                    tvc_measure_form_time.setText(DateUtil.dateFormat(timerStr, "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm")); /// 量房时间 .
                }
            } else {
                ll_consumer_send.setVisibility(View.VISIBLE);
                tvIllustrate.setVisibility(View.VISIBLE);
                rl_house_charge_show.setVisibility(View.VISIBLE);
                tvWarmTips.setText(R.string.warmTips);
                tvWarmTipsContent.setText(R.string.warm_tips_content);
                tvc_measure_form_time.setText("");
                consumer_house_charge_show.setVisibility(View.GONE);
            }
        } else if (memType.equals(Constant.UerInfoKey.DESIGNER_TYPE)) { // 设计师
            tvc_measure_form_time.setClickable(false);
            tvc_measure_form_time.setText(mBidders.get(0).getMeasure_time());
            //  consumer_house_charge_show.setVisibility(View.GONE);

            String timerStr = mBidders.get(0).getMeasure_time();
//            tvc_measure_form_time.setText(DateUtil.dateFormat(timerStr, "yyyy-MM-dd HH:mm:ss", "yyyy年MM月dd日 HH点"));
            if (TextUtils.isEmpty(timerStr) || timerStr.equals("null")) {
                tvc_measure_form_time.setText("");
            } else {
                tvc_measure_form_time.setText(DateUtil.dateFormat(timerStr, "yyyy-MM-dd HH:mm:ss", "yyyy年MM月dd日 HH点")); /// 量房时间 .
            }

            boolean elite = isElite(wk_cur_template_id);

            designer_house_charge_show.setVisibility(View.VISIBLE);
            switch (wk_cur_sub_node_id_i) {
                case 11:
                    if (!elite) {
                        setViewAnimation(rlMeasureWarmTips);
                        if (state == Constant.WorkFlowStateKey.STEP_MATERIAL) {
                            ll_designer_send.setVisibility(View.GONE);
                            designer_house_charge_show.setVisibility(View.VISIBLE);
                            setViewAnimation(rlMeasureWarmTips);
                            // rlMeasureWarmTips.setVisibility(View.GONE);
                        } else {
                            // rlMeasureWarmTips.setVisibility(View.VISIBLE);
                            ll_designer_send.setVisibility(View.VISIBLE);
                            tvWarmTips.setText(R.string.Mrasuretips);
                            tvWarmTipsContent.setText(R.string.update_measure_house_cost);
                            tvMeasureWarmTips.setText(R.string.warmTips);
                            tvMeasureWarmTipsContent.setText(R.string.no_pay_rent);

                        }
                    }
                    break;

                default:
                    ll_designer_send.setVisibility(View.GONE);
                    rlMeasureWarmTips.setVisibility(View.GONE);
                    designer_house_charge_show.setVisibility(View.GONE);
                    // consumer_designer_house_charge_show.setVisibility(View.VISIBLE);
                    break;
            }
        }
    }

    //动画效果,增加温馨提示，出现与消失的动画效果
    private void setViewAnimation(final ViewGroup viewAnimation) {

        final AnimationSet animationSetShow = new AnimationSet(true);
        final AnimationSet animationSetHide = new AnimationSet(true);
        AlphaAnimation alphaAnimationShow = new AlphaAnimation(0, 1);
        AlphaAnimation alphaAnimationHide = new AlphaAnimation(1, 0);
        alphaAnimationShow.setDuration(3000);
        alphaAnimationHide.setDuration(3000);
        animationSetShow.addAnimation(alphaAnimationShow);
        animationSetHide.addAnimation(alphaAnimationHide);

        viewAnimation.setAnimation(animationSetShow);
        //动画显现监听
        alphaAnimationShow.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                rlMeasureWarmTips.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                viewAnimation.setAnimation(animationSetHide);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        //动画消失监听
        alphaAnimationHide.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                rlMeasureWarmTips.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


    }

    /// 将省市区Code改成汉字 .

    private void updateCityData() {
        String province_name = requirement.getProvince_name();
        String city_name = requirement.getCity_name();
        String district_name = requirement.getDistrict_name();
        user_name = requirement.getContacts_name();
        mobile_number = requirement.getContacts_mobile();
        amount = mBidders.get(0).getMeasurement_fee();
        tvName.setText(user_name);
        tvPhone.setText(mobile_number);
        tvProjectBudget.setText(TextUtils.isEmpty(requirement.getDesign_budget()) ? UIUtils.getString(R.string.no_select) : requirement.getDesign_budget() + "");


        tvc_measure_fitment_budget.setText(TextUtils.isEmpty(requirement.getDecoration_budget()) ?
                UIUtils.getString(R.string.no_select) : requirement.getDecoration_budget());

        tvc_measure_form_area.setText(requirement.getHouse_area() /*+ "m²"*/);

        district_name = UIUtils.getNoStringIfEmpty(district_name);

        tvc_measure_form_address.setText(province_name + " " + city_name + " " + district_name);
        tvc_measure_form_estate.setText(requirement.getCommunity_name());
        if (TextUtils.isEmpty(amount)
                || "null".equals(amount)
                || "0.0".equals(amount)) {                       /// 如果量房费空就默认为0.00 .
            amount = "0.00";
            tvc_measure_form_fee.setText(amount);
            tv_measure_form_designer_liangfangfeit.setText(amount);
            consumer_rmb_show.setText(amount + "元");
        } else {
            String measurement_fee = mBidders.get(0).getMeasurement_fee();
            tvc_measure_form_fee.setText(SplitStringUtils.splitStringDot(measurement_fee));
            tv_measure_form_designer_liangfangfeit.setText(SplitStringUtils.splitStringDot(measurement_fee));
            consumer_rmb_show.setText(SplitStringUtils.splitStringDot(measurement_fee) + "元");
        }
    }


    private void updateRoomData() {                                                                 /// 将获取到的数据变成可读的室卫厅 .
        roomJson = AppJsonFileReader.getRoomHall(this);
        livingRoomJson = AppJsonFileReader.getLivingRoom(this);
        toiletJson = AppJsonFileReader.getToilet(this);
        styleJson = AppJsonFileReader.getStyle(FlowMeasureFormActivity.this);
        spaceJson = AppJsonFileReader.getSpace(FlowMeasureFormActivity.this);

        room = requirement.getRoom();
        living_room = requirement.getLiving_room();

        toilet = requirement.getToilet();
        if (livingRoomJson.containsKey(living_room)) {
            living_room_convert = livingRoomJson.get(living_room);
        } else {
            living_room_convert = living_room;
        }
        if (roomJson.containsKey(room)) {
            room_convert = roomJson.get(room);
        } else {
            room_convert = room;
        }

        if (toiletJson.containsKey(toilet)) {
            toilet_convert = toiletJson.get(toilet);
        } else {
            toilet_convert = toilet;
        }
        //将风格转换成汉字
        if (styleJson.containsKey(requirement.getDecoration_style())) {

            house_style = styleJson.get(requirement.getDecoration_style());

        } else {

            house_style = requirement.getDecoration_style();
        }
        //将房屋类型转换成汉字
        if (spaceJson.containsKey(requirement.getHouse_type())) {

            house_type_content = spaceJson.get(requirement.getHouse_type());
        } else {

            house_type_content = requirement.getHouse_type();
        }
        String rlt = room_convert + " " + living_room_convert + " " + toilet_convert;
        rlt = (rlt.equals("null" + " " + " " + " " + " ")) ? UIUtils.getString(R.string.str_others) : rlt;
        tvc_measure_form_house_type_model.setText(rlt);

        tvc_measure_form_type.setText(house_type_content != null ? house_type_content : UIUtils.getString(R.string.no_select));

        tvc_measure_form_style.setText(TextUtils.isEmpty(house_style) ? UIUtils.getString(R.string.no_select) : house_style);
    }


    /**
     * @param needs_id
     * @brief 设计师同意量房 .
     */
    public void agreeMeasureHouse(String needs_id) {
        CustomProgress.show(FlowMeasureFormActivity.this, null, false, null);
        JSONObject jsonObject = new JSONObject();
        String fee = tv_measure_form_designer_liangfangfeit.getText().toString().trim();
        try {
            jsonObject.put("measurement_fee", fee);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        OkJsonRequest.OKResponseCallback okResponseCallback = new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                CustomProgress.cancelDialog();
                mAgreeMeasureHouseSuccessAlertView.show();
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                MPNetworkUtils.logError(TAG, volleyError);
                CustomProgress.cancelDialog();
                mAgreeMeasureHouseFailAlertView.show();
            }
        };
        MPServerHttpManager.getInstance().agreeMeasureHouse(needs_id, designer_id, jsonObject, okResponseCallback);
    }

    /**
     * @param needs_id
     * @brief 设计师拒绝量房 .
     */
    public void refusedHouse(String needs_id) {
        OkJsonRequest.OKResponseCallback okResponseCallback = new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                CustomProgress.cancelDialog();
                mRefuseMeasureHouseSuccessAlertView.show();
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                MPNetworkUtils.logError(TAG, volleyError);
                CustomProgress.cancelDialog();
                mRefuseMeasureHouseFailAlertView.show();
            }
        };
        MPServerHttpManager.getInstance().agreeRefusedHouse(needs_id, okResponseCallback);
    }

    /**
     * 消费者同意应标 .
     */
    public void agreeResponseBid(JSONObject jsonObject) {
        CustomProgress.show(FlowMeasureFormActivity.this, null, false, null);
        OkJsonRequest.OKResponseCallback okResponseCallback = new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                CustomProgress.cancelDialog();
                String userInfo = GsonUtil.jsonToString(jsonObject);
                MPMeasureFormBean mMPMeasureFormBean = GsonUtil.jsonToBean(userInfo, MPMeasureFormBean.class);
                updateViewFromData(mMPMeasureFormBean);
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                MPNetworkUtils.logError(TAG, volleyError);
                CustomProgress.cancelDialog();
                mAgreeResponseBidFailAlertView.show();
            }
        };
        MPServerHttpManager.getInstance().agreeResponseBid(jsonObject, okResponseCallback);
    }

    private void updateViewFromData(MPMeasureFormBean mMPMeasureFormBean) {
        List<MPMeasureFormBean.BiddersBean> bidders = mMPMeasureFormBean.getBidders();
        if (bidders != null && bidders.size() > 0) {
            wk_cur_sub_node_id = bidders.get(0).getWk_cur_sub_node_id();
            LogUtils.i("FlowMeasureFormActivity", wk_cur_sub_node_id + "");
        }
        mAgreeResponseBidSuccessAlertView.show();
    }

    /**
     * @brief 设置量房时间 .
     */
    private void setMeasureTime() {
        /// The time selector  .
        pvTime = new TimePickerView(this, TimePickerView.Type.ALL);
        /// Control the time range .
        pvTime.setRange(2016, 2018);
        pvTime.setTitle("量房时间");
        pvTime.setTime(new Date());
        pvTime.setCyclic(false);
        pvTime.setCancelable(true);
        /// The callback after the time to choose  .
        pvTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {

            @Override
            public void onTimeSelect(Date date) {
                currentTime = getTime(date);
                tvc_measure_form_time.setText(currentTime);
            }
        });
    }

    public static String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

    /**
     * 初始化AlertView
     */
    private void initAlertView() {
        mAgreeMeasureHouseSuccessAlertView = showAlertView(commonTip, UIUtils.getString(R.string.confirm_room_success));
        mAgreeMeasureHouseFailAlertView = showAlertView(commonTip, UIUtils.getString(R.string.confirm_room_fail));
        mRefuseMeasureHouseFailAlertView = showAlertView(commonTip, UIUtils.getString(R.string.refused_room_fail));
        mRefuseMeasureHouseSuccessAlertView = showAlertView(commonTip, UIUtils.getString(R.string.refused_room_success));
        mAgreeResponseBidSuccessAlertView = showAlertView(commonTip, UIUtils.getString(R.string.choose_ta_room_success));
        mAgreeResponseBidFailAlertView = showAlertView(commonTip, UIUtils.getString(R.string.choose_ta_room_fail));
        mRefuseMeasureHouseAlertView = new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.refuse_measure_house_alertview), null, new String[]{UIUtils.getString(R.string.sure_deny)}, new String[]{UIUtils.getString(R.string.sure_shi)}, this, AlertView.Style.Alert, this).setCancelable(true);
    }

    /**
     * @param tip
     * @param content
     * @return
     */
    private AlertView showAlertView(String tip, String content) {
        mCommonAlertView = new AlertView(tip, content, null, null, sureString, FlowMeasureFormActivity.this, AlertView.Style.Alert, FlowMeasureFormActivity.this);
        return mCommonAlertView;
    }

    private TextView tvName;
    private TextView tvPhone;
    private TextView tvProjectBudget;/// design budget .
    private TextView tvc_measure_fitment_budget;
    private TextView tvc_measure_form_area;
    private TextView tvc_measure_form_house_type_model;
    private TextView tvc_measure_form_time;
    private TextView tvc_measure_form_address;
    private TextView tvc_measure_form_estate;
    private TextView tvc_measure_form_fee;
    private TextView tvc_measure_form_type;
    private TextView tvc_measure_form_style;
    private TextView tvWarmTips;
    private TextView tvIllustrate;
    private TextView tvWarmTipsContent;

    private RelativeLayout rlMeasureWarmTips;
    private RelativeLayout rl_house_charge_show;
    private RelativeLayout rlWarmTips;
    private LinearLayout consumer_house_charge_show;
    private LinearLayout designer_house_charge_show;
    private EditText tv_measure_form_designer_liangfangfeit;
    private TextView tvMeasureWarmTips;
    private TextView tvMeasureWarmTipsContent;
    private TextView consumer_rmb_show;


    private Button btn_measure_form_accept;
    private Button btn_measure_form_send;
    private Button btn_measure_form_refuse;
    private LinearLayout ll_consumer_send;
    private LinearLayout ll_designer_send;
    private LinearLayout ll_measure_form_style;

    private TimePickerView pvTime;
    private AlertView mCommonAlertView;
    private AlertView mAgreeMeasureHouseSuccessAlertView;
    private AlertView mAgreeMeasureHouseFailAlertView;
    private AlertView mRefuseMeasureHouseFailAlertView;
    private AlertView mRefuseMeasureHouseSuccessAlertView;
    private AlertView mAgreeResponseBidSuccessAlertView;
    private AlertView mAgreeResponseBidFailAlertView;
    private AlertView mRefuseMeasureHouseAlertView;

    private Map<String, String> livingRoomJson;
    private Map<String, String> roomJson;
    private Map<String, String> toiletJson;
    private Map<String, String> styleJson, spaceJson;

    private String memType;
    private String house_type_content;
    private String house_style;
    private String living_room_convert;
    private String room_convert;
    private String toilet_convert;
    private String living_room;
    private String room;
    private String toilet;
    private String mobile_number;
    private String currentTime;
    private String amount;
    private String user_id;
    private String user_name;
    private String commonTip = UIUtils.getString(R.string.tip);
    private String[] sureString = new String[]{UIUtils.getString(R.string.sure)};

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {


        if (s.toString().contains(".")) {
            if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                s = s.toString().subSequence(0, s.toString().indexOf(".") + 3);
                tv_measure_form_designer_liangfangfeit.setText(s);
                tv_measure_form_designer_liangfangfeit.setSelection(s.length());
            }
        }

        if (s.toString().trim().substring(0).equals(".")) {
            s = "0" + s;
            tv_measure_form_designer_liangfangfeit.setText(s);
            tv_measure_form_designer_liangfangfeit.setSelection(2);
        }

        if (s.toString().startsWith("0") && s.toString().trim().length() > 1) {
            if (!s.toString().substring(1, 2).equals(".")) {
                tv_measure_form_designer_liangfangfeit.setText(s.subSequence(0, 1));
                tv_measure_form_designer_liangfangfeit.setSelection(1);
                return;
            }

        }


    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}