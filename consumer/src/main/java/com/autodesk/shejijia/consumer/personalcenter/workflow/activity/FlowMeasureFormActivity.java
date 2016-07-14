package com.autodesk.shejijia.consumer.personalcenter.workflow.activity;

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
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.consumer.manager.constants.JsonConstants;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.consumer.personalcenter.workflow.entity.AgreeResponseBean;
import com.autodesk.shejijia.consumer.utils.AppJsonFileReader;
import com.autodesk.shejijia.shared.components.common.utility.DbUtils;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.MPNetworkUtils;
import com.autodesk.shejijia.shared.components.common.utility.StringUtils;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.components.common.uielements.CustomProgress;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.AlertView;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.OnItemClickListener;
import com.autodesk.shejijia.shared.components.common.uielements.reusewheel.utils.TimePickerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
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
public class FlowMeasureFormActivity extends BaseWorkFlowActivity implements OnItemClickListener, View.OnClickListener {

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
        tvProjectBudget = (TextView) findViewById(R.id.tvc_measure_form_project_budget);
        tvc_measure_fitment_budget = (TextView) findViewById(R.id.tvc_measure_fitment_budget);
        tvc_measure_form_area = (TextView) findViewById(R.id.tvc_measure_form_area);
        tvc_measure_form_house_type = (TextView) findViewById(R.id.tvc_measure_form_house_type);
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
        ll_measure_form_style.setVisibility(View.GONE);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        setTitleForNavbar(getResources().getString(R.string.demand_measure_house_form)); /// 设置标题 .

        memType = memberEntity.getMember_type();
        user_id = memberEntity.getAcs_member_id();
        initAlertView();
    }


    @Override
    public void onItemClick(Object object, int position) {
        if (object == mRefuseMeasureHouseAlertView && position == 1) {
            CustomProgress.show(FlowMeasureFormActivity.this, null, false, null);
            agreeRefusedHouse(needs_id);
        }
        if (object == mAgreeResponseBidSuccessAlertView && position == 0) {
            Intent intent = new Intent();
            intent.putExtra(Constant.BundleKey.BUNDLE_SUB_NODE_ID, wk_cur_sub_node_id);
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

                    if (currentTime == null) {
                        new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.amount_of_time_is_empty), null, new String[]{UIUtils.getString(R.string.sure)}, null, FlowMeasureFormActivity.this,
                                AlertView.Style.Alert, null).show();
                    } else {
                        if (formatDate(date, currentTime)) {

                            CustomProgress.show(FlowMeasureFormActivity.this, null, false, null);

                            jsonObject.put(JsonConstants.JSON_FLOW_MEASURE_FORM_SERVICE_DATE, currentTime);
                            jsonObject.put(JsonConstants.JSON_FLOW_MEASURE_FORM_USER_ID, user_id);
                            jsonObject.put(JsonConstants.JSON_FLOW_MEASURE_FORM_DESIGNER_ID, designer_id);
                            jsonObject.put(JsonConstants.JSON_FLOW_MEASURE_FORM_NEEDS_ID, needs_id);
                            jsonObject.put(JsonConstants.JSON_FLOW_MEASURE_FORM_USER_NAME, user_name);
                            jsonObject.put(JsonConstants.JSON_FLOW_MEASURE_FORM_MOBILE_NUMBER, mobile_number);
                            jsonObject.put(JsonConstants.JSON_FLOW_MEASURE_FORM_ORDER_TYPE, 0);
                            if (amount == null) {
                                jsonObject.put(JsonConstants.JSON_FLOW_MEASURE_FORM_AMOUNT, 0.01);
                            } else {
                                jsonObject.put(JsonConstants.JSON_FLOW_MEASURE_FORM_AMOUNT, amount);
                            }
                            jsonObject.put(JsonConstants.JSON_FLOW_MEASURE_FORM_ADJUSTMENT, 600);
                            jsonObject.put(JsonConstants.JSON_FLOW_MEASURE_FORM_CHANNEL_TYPE, "Android");

                            agreeResponseBid(jsonObject);
                        } else {
                            new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.amount_of_time_than_current_time_one_hour), null, new String[]{UIUtils.getString(R.string.sure)}, null, FlowMeasureFormActivity.this,
                                    AlertView.Style.Alert, null).show();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_measure_form_accept: /// 同意量房 .
                CustomProgress.show(FlowMeasureFormActivity.this, null, false, null);
                agreeMeasureHouse(needs_id);
                break;
            case R.id.btn_measure_form_refuse: /// 拒绝量房 .
                mRefuseMeasureHouseAlertView.show();
                break;
            case R.id.tvc_measure_form_time: /// 设置量房时间 .
                pvTime.show();
                break;
        }
    }

    @Override
    protected void onWorkFlowData() {
        super.onWorkFlowData();

        updateDisplayData();

        updateRoomData();

        updateCityData();

        /// time .
        setMeasureTime();
    }

    /// 更新显示状态钮得消失显示 .
    private void updateDisplayData() {

        if (TextUtils.isEmpty(wk_cur_sub_node_id)) {
            return;
        }
        int wk_cur_sub_node_id_i = Integer.valueOf(wk_cur_sub_node_id);
        if (memType.equals(Constant.UerInfoKey.CONSUMER_TYPE)) { // 消费者
            if (wk_cur_sub_node_id_i >= 11) {
                ll_consumer_send.setVisibility(View.GONE);
                tvc_measure_form_time.setClickable(false);
                tvc_measure_form_time.setText(mBidders.get(0).getMeasure_time()); /// 量房时间 .

            } else {
                ll_consumer_send.setVisibility(View.VISIBLE);
                tvc_measure_form_time.setText("");
            }
        } else if (memType.equals(Constant.UerInfoKey.DESIGNER_TYPE)) { // 设计师
            tvc_measure_form_time.setClickable(false);
            tvc_measure_form_time.setText(mBidders.get(0).getMeasure_time());
            switch (wk_cur_sub_node_id_i) {
                case 11:
                    if (state == Constant.WorkFlowStateKey.STEP_MATERIAL) {
                        ll_designer_send.setVisibility(View.GONE);
                    } else {
                        ll_designer_send.setVisibility(View.VISIBLE);
                    }
                    break;
                default:
                    ll_designer_send.setVisibility(View.GONE);
                    break;
            }
        }
    }

    /// 将省市区Code改成汉字 .
    private void updateCityData() {
        String province_name = requirement.getProvince();
        String city_name = requirement.getCity();
        String district_name = requirement.getDistrict();
        if (StringUtils.isNumeric(province_name)) {
            province_name = DbUtils.getCodeName(UIUtils.getContext(), Constant.DbTag.PROVINCE, province_name);
        }
        if (StringUtils.isNumeric(city_name)) {
            city_name = DbUtils.getCodeName(UIUtils.getContext(), Constant.DbTag.CITY, city_name);
        }
        if (StringUtils.isNumeric(district_name)) {
            district_name = DbUtils.getCodeName(UIUtils.getContext(), Constant.DbTag.DISTRICT, district_name);
        }
        user_name = requirement.getContacts_name();
        mobile_number = requirement.getContacts_mobile();
        amount = requirement.getBidders().get(0).getMeasurement_fee();

        tvName.setText(user_name);
        tvPhone.setText(mobile_number);
        tvProjectBudget.setText(requirement.getDesign_budget() + "");
        tvc_measure_fitment_budget.setText(requirement.getDecoration_budget());
        tvc_measure_form_area.setText(requirement.getHouse_area());

        tvc_measure_form_address.setText(province_name + " " + city_name + " " + district_name);
        tvc_measure_form_estate.setText(requirement.getCommunity_name());
        if (TextUtils.isEmpty(amount) || "null".equals(amount)) { /// 如果量房费空就默认为0.00 .
            amount = "0.00";
            tvc_measure_form_fee.setText(amount);
        } else {
            tvc_measure_form_fee.setText(requirement.getBidders().get(0).getMeasurement_fee());
        }
    }

    private void updateRoomData() { /// 将获取到的数据变成可读的室卫厅 .
        livingRoomJson = AppJsonFileReader.getLivingRoom(this);
        roomJson = AppJsonFileReader.getRoomHall(this);
        toiletJson = AppJsonFileReader.getToilet(this);
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
        tvc_measure_form_house_type.setText(room_convert + " " + living_room_convert + " " + toilet_convert);
    }

    /**
     * @param beforeDate
     * @param afterDate
     * @brief yyyy-MM-dd HH:mm:ss格式转化成毫秒数(long)进行判断 .
     */
    public static boolean formatDate(String beforeDate, String afterDate) throws ParseException {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d1 = sf.parse(beforeDate);
        Date d2 = sf.parse(afterDate);
        long stamp = d2.getTime() - d1.getTime();
        return stamp >= (3600 * 1000);
    }

    /**
     * @param needs_id
     * @brief 设计师同意量房 .
     */
    public void agreeMeasureHouse(String needs_id) {
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
        MPServerHttpManager.getInstance().agreeMeasureHouse(needs_id, okResponseCallback);
    }

    /**
     * @param needs_id
     * @brief 设计师拒绝量房 .
     */
    public void agreeRefusedHouse(String needs_id) {
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
     * @param jsonObject
     * @brief 消费者同意应标 .
     */
    public void agreeResponseBid(JSONObject jsonObject) {
        OkJsonRequest.OKResponseCallback okResponseCallback = new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                String userInfo = GsonUtil.jsonToString(jsonObject);
                AgreeResponseBean mAgreeResponseBean = GsonUtil.jsonToBean(userInfo, AgreeResponseBean.class);
                updateViewFromData(mAgreeResponseBean);
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

    private void updateViewFromData(AgreeResponseBean mAgreeResponseBean) {
        CustomProgress.cancelDialog();

        List<AgreeResponseBean.BiddersEntity> bidders = mAgreeResponseBean.getBidders();
        if (bidders != null && bidders.size() > 0) {
            wk_cur_sub_node_id = bidders.get(0).getWk_cur_sub_node_id();
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
    private TextView tvc_measure_form_house_type;
    private TextView tvc_measure_form_time;
    private TextView tvc_measure_form_address;
    private TextView tvc_measure_form_estate;
    private TextView tvc_measure_form_fee;
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

    private String memType;
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
}