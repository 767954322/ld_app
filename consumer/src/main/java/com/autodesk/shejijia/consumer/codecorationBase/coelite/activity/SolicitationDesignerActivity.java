package com.autodesk.shejijia.consumer.codecorationBase.coelite.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.base.utils.ConvertUtils;
import com.autodesk.shejijia.consumer.codecorationBase.coelite.entity.SelectionBean;
import com.autodesk.shejijia.consumer.codecorationBase.coelite.entity.SolicitationSelection;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.consumer.manager.constants.JsonConstants;
import com.autodesk.shejijia.consumer.personalcenter.consumer.entity.DecorationNeedsListBean;
import com.autodesk.shejijia.consumer.personalcenter.workflow.activity.FlowMeasureCostActivity;
import com.autodesk.shejijia.consumer.uielements.TextViewContent;
import com.autodesk.shejijia.consumer.utils.AppJsonFileReader;
import com.autodesk.shejijia.consumer.utils.MPStatusMachine;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.uielements.AddressDialog;
import com.autodesk.shejijia.shared.components.common.uielements.CustomProgress;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.AlertView;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.OnItemClickListener;
import com.autodesk.shejijia.shared.components.common.uielements.reusewheel.utils.TimePickerView;
import com.autodesk.shejijia.shared.components.common.utility.DateUtil;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.framework.activity.NavigationBarActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * @author .
 * @version 1.0 .
 * @date 16-8-16
 * @file SolicitationDesignerActivity.java  .
 * @brief 精选量房邀约 .
 */

public class SolicitationDesignerActivity extends NavigationBarActivity implements View.OnClickListener {
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_measure_form2;
    }

    @Override
    protected void initView() {
        super.initView();

        textNotuse = (TextView) findViewById(R.id.text_notuse);
        textNotuse.requestFocus();

        tvcName = (TextView) findViewById(R.id.tvc_measure_form_name);
        tvcPhone = (TextViewContent) findViewById(R.id.tvc_measure_form_phone);
        tvcProjectBudget = (TextView) findViewById(R.id.tvc_measure_form_project_budget);
        tvcFitmentBudget = (TextView) findViewById(R.id.tvc_measure_fitment_budget);
        tvcArea = (TextViewContent) findViewById(R.id.tvc_measure_form_area);
        tvcHouseType = (TextView) findViewById(R.id.tvc_measure_form_house_type);
        tvcTime = (TextView) findViewById(R.id.tvc_measure_form_time);
        tvcAddress = (TextView) findViewById(R.id.tvc_measure_form_address);
        tvcEstate = (TextViewContent) findViewById(R.id.tvc_measure_form_estate);
        tvMeasureFee = (TextView) findViewById(R.id.tv_measure_form_liangfangfei);
        btnSendForm = (Button) findViewById(R.id.btn_send_measure_house_form);
        tvcMeasureFormType = (TextView) findViewById(R.id.tvc_measure_form_type);
        tvcMeasureFormStyle = (TextView) findViewById(R.id.tvc_measure_form_style);
        tvIllustrate = (TextView) findViewById(R.id.tvIllustrate);

        tvcName.setEnabled(false);
        tvcPhone.setEnabled(false);
        tvcArea.setEnabled(false);

        tvcName.setTextColor(getResources().getColor(R.color.bg_66));
        tvcPhone.setTextColor(getResources().getColor(R.color.bg_66));
        tvcArea.setTextColor(getResources().getColor(R.color.bg_66));
    }

    @Override
    protected void initExtraBundle() {
        super.initExtraBundle();
        getExtraData();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        setTitleForNavbar(UIUtils.getString(R.string.demand_elite_measure_house_form));
        tvcTime.setFocusable(false);
        tvcEstate.setEnabled(false);
//        List<DecorationBiddersBean> list = decorationNeedsListBean.getBidders();

        String province_name = decorationNeedsListBean.getProvince_name();
        String city_name = decorationNeedsListBean.getCity_name();
        String district_name = decorationNeedsListBean.getDistrict_name();

        String house_type = decorationNeedsListBean.getHouse_type();

        Map<String, String> spaceMap = AppJsonFileReader.getSpace(this);
        Map<String, String> styleMap = AppJsonFileReader.getStyle(this);

        Map<String, String> roomHallMap = AppJsonFileReader.getLivingRoom(this);
        Map<String, String> roomMap = AppJsonFileReader.getRoomHall(this);
        Map<String, String> toiletMap = AppJsonFileReader.getToilet(this);

        tvcArea.setText(decorationNeedsListBean.getHouse_area());
        tvcEstate.setText(decorationNeedsListBean.getCommunity_name());
        tvcFitmentBudget.setText(UIUtils.getNoSelectIfEmpty(decorationNeedsListBean.getDecoration_budget()));

        tvcProjectBudget.setText(UIUtils.getNoSelectIfEmpty(decorationNeedsListBean.getDesign_budget()));
        tvcName.setText(decorationNeedsListBean.getContacts_name());
        tvcPhone.setText(decorationNeedsListBean.getContacts_mobile());
        tvcMeasureFormType.setText(UIUtils.getNoSelectIfEmpty(spaceMap.get(house_type)));

        String room = ConvertUtils.getConvert2CN(roomMap, decorationNeedsListBean.getRoom());
        String livingRoom = ConvertUtils.getConvert2CN(roomHallMap, decorationNeedsListBean.getLiving_room());
        String toilet = ConvertUtils.getConvert2CN(toiletMap, decorationNeedsListBean.getToilet());

        String tvHouseType = TextUtils.isEmpty(room) ? UIUtils.getString(R.string.no_select) : room + livingRoom + toilet;

        tvcHouseType.setText(tvHouseType);//设置室 厅 卫
        String style = styleMap.get(decorationNeedsListBean.getDecoration_style());
        tvcMeasureFormStyle.setText(UIUtils.getNoSelectIfEmpty(style));//风格
        chageButtonValue();
        setMeasureTime();

        if (district_name.equals("none")) {
            tvcAddress.setText(province_name + city_name);
            return;
        }
        tvcAddress.setText(province_name + city_name + district_name);

    }

    @Override
    protected void initListener() {
        super.initListener();
        btnSendForm.setOnClickListener(this);
        tvIllustrate.setOnClickListener(this);
        tvcTime.setOnClickListener(this);
        tvcAddress.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvIllustrate:
                showAlertView(R.string.illustrate, UIUtils.getString(R.string.warm_tips_content), R.string.finish_cur_pager);
                break;
            case R.id.btn_send_measure_house_form:
                getJSONObject();
                break;
            case R.id.tvc_measure_form_time:
                pvTime.show();
                break;
            default:
                break;
        }

    }

    /**
     * 更改按钮的文字（是发送还是支付）
     */
    private void chageButtonValue() {
        SelectionBean selectionBean = decorationNeedsListBean.getElite();
        if (selectionBean == null) {
            return;
        }
        if (selectionBean.getMeasurement() == null) {
            return;
        }
        measureFee = selectionBean.getMeasurement().getFee();
        orderLineId = selectionBean.getMeasurement().getOrder_line_id();
        orderId = selectionBean.getMeasurement().getOrder_id();
        tvMeasureFee.setText(measureFee == null ? "" : measureFee);
        String payment_status = selectionBean.getMeasurement().getPayment_status();
        if (payment_status.equals("0")) {//0：未支付量房费 1：已支付量房费
            btnSendForm.setText(UIUtils.getString(R.string.pay_p));
            isPay = true;
        }

    }

    /**
     * 获取要提交的json字符串
     */

    private void getJSONObject() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(JsonConstants.JSON_MEASURE_FORM_AMOUNT, measureFee);
            jsonObject.put(JsonConstants.JSON_MEASURE_FORM_DESIGNER_ID, designerId);
            jsonObject.put(JsonConstants.JSON_FLOW_MEASURE_FORM_NEEDS_ID, decorationNeedsListBean.getNeeds_id());
            jsonObject.put(JsonConstants.JSON_MEASURE_FORM_SERVICE_DATE, currentData);
            jsonObject.put(JsonConstants.JSON_MEASURE_FORM_ORDER_TYPE, "");

            if (currentData == null) {
                showAlertView(R.string.tip, UIUtils.getString(R.string.please_select_quantity_room_time),
                        R.string.chatroom_audio_recording_erroralert_ok);
                return;
            }

            isSendMeasureForm(jsonObject);


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    /**
     * 验证是否发送过量房邀约
     *
     * @param jsonObject
     */

    private void isSendMeasureForm(JSONObject jsonObject) {
        if (isPay) {
            if (orderLineId != null && orderId != null && !orderLineId.equals("0") && !orderId.equals("0")) {
                pay(orderLineId, orderId);
                return;
            }
            postSendMeasureForm(jsonObject);
            return;
        }
        postSendMeasureForm(jsonObject);
    }

    /**
     * 跳转支付界面
     *
     * @param order_line_id
     * @param order_id
     */

    private void pay(String order_line_id, String order_id) {
        Intent intent = new Intent(this, FlowMeasureCostActivity.class);
        intent.putExtra(Constant.SeekDesignerDetailKey.DESIGNER_ID, designerId);
        intent.putExtra(Constant.SeekDesignerDetailKey.NEEDS_ID, decorationNeedsListBean.getNeeds_id());
        intent.putExtra(Constant.SeekDesignerDetailKey.ORDER_LINE_ID, order_line_id);
        intent.putExtra(Constant.SeekDesignerDetailKey.ORDER_ID, order_id);
        intent.putExtra(JsonConstants.JSON_MEASURE_FORM_AMOUNT, measureFee);
        intent.putExtra(Constant.BundleKey.TEMPDATE_ID, MPStatusMachine.NODE__MEANSURE_PAY);
        this.startActivityForResult(intent, 105);
    }

    /**
     * 发送量房邀约资料
     *
     * @param jsonObject
     */

    private void postSendMeasureForm(final JSONObject jsonObject) {

        CustomProgress.showDefaultProgress(this);
        OkJsonRequest.OKResponseCallback okResponseCallback = new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onResponse(JSONObject jsonObject) {

                String jsonString = GsonUtil.jsonToString(jsonObject);
                SolicitationSelection solicitationSelection = GsonUtil.jsonToBean(jsonString, SolicitationSelection.class);
                CustomProgress.cancelDialog();
                if (isPay) {
                    pay(solicitationSelection.getElite().getMeasurement().getOrder_line_id(),
                            solicitationSelection.getElite().getMeasurement().getOrder_id());
                } else {
                    new AlertView(UIUtils.getString(R.string.tip), "选TA量房成功", null, null, new String[]{UIUtils.getString(R.string.chatroom_audio_recording_erroralert_ok)}, SolicitationDesignerActivity.this,
                            AlertView.Style.Alert, new OnItemClickListener() {
                        @Override
                        public void onItemClick(Object object, int position) {
                            setResult(10058, new Intent());
                            finish();
                        }
                    }).show();
                }
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                CustomProgress.dialog.cancel();
            }
        };
        MPServerHttpManager.getInstance().SendMeasureForm(decorationNeedsListBean.getNeeds_id(), designerId, jsonObject, okResponseCallback);
    }

    /**
     * 打开提示信息框
     */

    private void showAlertView(int tip, String content, int isOK) {
        new AlertView(UIUtils.getString(tip), content, null, null, new String[]{UIUtils.getString(isOK)}, this,
                AlertView.Style.Alert, null).show();
    }

    //获取精选装修模式传递过来的数据
    private void getExtraData() {
        Intent intent = getIntent();
        decorationNeedsListBean = (DecorationNeedsListBean) intent.getSerializableExtra(Constant.ConsumerDecorationFragment.DECORATIONbIDDERBEAN);
        designerId = intent.getStringExtra(Constant.SeekDesignerDetailKey.DESIGNER_ID);
        falg = intent.getBooleanExtra(Constant.SeekDesignerDetailKey.ORDERS, false);
    }

    //设置量房时间
    private void setMeasureTime() {
        pvTime = new TimePickerView(this, TimePickerView.Type.ALL);
        pvTime.setRange(2016, 2018);
        pvTime.setTime(new Date());
        pvTime.setCyclic(false);
        pvTime.setCancelable(false);
        pvTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {

            @Override
            public void onTimeSelect(Date date) {
                currentData = getTime(date);
                tvcTime.setText(DateUtil.dateFormat(currentData, "yyyy-MM-dd HH:mm:ss", "yyyy年MM月dd日 HH点"));
            }
        });
    }

    /**
     * @brief 获取yyyy-MM-dd HH:mm:ss 格式的时间
     */
    public static String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == FlowMeasureCostActivity.RESULT_CODE) {

            setResult(10058, new Intent());
            finish();
        }

    }

    ///控件.
    private TextView textNotuse;
    private TextView tvcName;
    private TextViewContent tvcPhone;
    private TextView tvcProjectBudget;
    private TextView tvcFitmentBudget;
    private TextViewContent tvcArea;
    private TextView tvcHouseType;
    private TextView tvcTime;
    private TextView tvcAddress;
    private TextViewContent tvcEstate;
    private TextView tvMeasureFee;
    private TextView tvcMeasureFormType;
    private TextView tvcMeasureFormStyle;
    private Button btnSendForm;
    private TextView tvIllustrate;
    private TimePickerView pvTime;
    private AddressDialog mChangeAddressDialog;

    /// 变量.
    private String designerId;
    private String measureFee;
    private String currentData;
    private String orderLineId;
    private String orderId;


    private String mCurrentProvince, mCurrentCity, mCurrentDistrict;
    private String mCurrentProvinceCode, mCurrentCityCode, mCurrentDistrictCode;
    private boolean isPay = false;
    private boolean falg;

    ///　集合，类.

    private DecorationNeedsListBean decorationNeedsListBean;


}
