package com.autodesk.shejijia.consumer.codecorationBase.coelite.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.codecorationBase.coelite.entity.SelectionBean;
import com.autodesk.shejijia.consumer.codecorationBase.coelite.entity.SolicitationSelection;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.consumer.manager.constants.JsonConstants;
import com.autodesk.shejijia.consumer.personalcenter.consumer.entity.DecorationNeedsListBean;
import com.autodesk.shejijia.consumer.personalcenter.resdecoration.entity.DecorationBiddersBean;
import com.autodesk.shejijia.consumer.personalcenter.workflow.activity.FlowMeasureCostActivity;
import com.autodesk.shejijia.consumer.utils.AppJsonFileReader;
import com.autodesk.shejijia.consumer.utils.MPStatusMachine;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.uielements.AddressDialog;
import com.autodesk.shejijia.shared.components.common.uielements.CustomProgress;
import com.autodesk.shejijia.shared.components.common.uielements.TextViewContent;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.AlertView;
import com.autodesk.shejijia.shared.components.common.uielements.reusewheel.utils.TimePickerView;
import com.autodesk.shejijia.shared.components.common.utility.DateUtil;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.framework.activity.NavigationBarActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 量房邀约
 * Created by luchongbin on 16-8-19.
 */
public class SolicitationDesignerActivity extends NavigationBarActivity implements View.OnClickListener{
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_measure_form;
    }
    @Override
    protected void initView() {
        super.initView();

        /* To prevent the pop-up keyboard */
        text_notuse = (TextView) findViewById(R.id.text_notuse);
        text_notuse.requestFocus();

        tvc_name = (TextView) findViewById(R.id.tvc_measure_form_name);
        tvc_phone = (TextViewContent) findViewById(R.id.tvc_measure_form_phone);
        tvc_project_budget = (TextView) findViewById(R.id.tvc_measure_form_project_budget);
        tvc_fitment_budget = (TextView) findViewById(R.id.tvc_measure_fitment_budget);
        tvc_area = (TextViewContent) findViewById(R.id.tvc_measure_form_area);
        tvc_house_type = (TextView) findViewById(R.id.tvc_measure_form_house_type);
        tvc_time = (TextViewContent) findViewById(R.id.tvc_measure_form_time);
        tvc_address = (TextView) findViewById(R.id.tvc_measure_form_address);
        tvc_estate = (TextViewContent) findViewById(R.id.tvc_measure_form_estate);
        tv_measure_fee = (TextView) findViewById(R.id.tv_measure_form_liangfangfei);
        btn_send_form = (Button) findViewById(R.id.btn_send_measure_house_form);
        tvc_measure_form_type = (TextView) findViewById(R.id.tvc_measure_form_type);
        tvc_measure_form_style = (TextView) findViewById(R.id.tvc_measure_form_style);
        tvIllustrate = (TextView) findViewById(R.id.tvIllustrate);
    }
    @Override
    protected void initExtraBundle() {
        super.initExtraBundle();
        getExtraData();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        setTitleForNavbar(UIUtils.getString(R.string.demand_measure_house_form));
        tvc_time.setFocusable(false);
        tvc_estate.setEnabled(false);
        List<DecorationBiddersBean> list = decorationNeedsListBean.getBidders();

        String province_name = decorationNeedsListBean.getProvince_name();
        String city_name = decorationNeedsListBean.getCity_name();
        String district_name = decorationNeedsListBean.getDistrict_name();

        String house_type = decorationNeedsListBean.getHouse_type();

        Map<String, String> spaceMap= AppJsonFileReader.getSpace(this);
        Map<String, String> styleMap= AppJsonFileReader.getStyle(this);

        Map<String, String> roomHallMap= AppJsonFileReader.getRoomHall(this);
        Map<String, String> roomMap =AppJsonFileReader.getLivingRoom(this);
        Map<String, String> toiletMap = AppJsonFileReader.getToilet(this);

        tvc_area.setText(decorationNeedsListBean.getHouse_area());
        tvc_estate.setText(decorationNeedsListBean.getCommunity_name());
        tvc_fitment_budget.setText(decorationNeedsListBean.getDecoration_budget() != null?
                                   decorationNeedsListBean.getDecoration_budget():
                                   UIUtils.getString(R.string.no_select));

        tvc_project_budget.setText( decorationNeedsListBean.getDesign_budget() != null?decorationNeedsListBean.getDesign_budget():UIUtils.getString(R.string.no_select));
        tvc_name.setText(decorationNeedsListBean.getContacts_name());
        tvc_phone.setText(decorationNeedsListBean.getContacts_mobile());
        tvc_measure_form_type.setText(spaceMap.get(house_type) != null?spaceMap.get(house_type):UIUtils.getString(R.string.no_select));

        String tvHouseType = roomHallMap.get(decorationNeedsListBean.getLiving_room())
                +roomMap.get(decorationNeedsListBean.getRoom())
                +toiletMap.get(decorationNeedsListBean.getToilet());
        tvHouseType = tvHouseType.equals("nullnullnull")?UIUtils.getString(R.string.no_select):tvHouseType;


        tvc_house_type.setText(tvHouseType);//设置室 厅 卫
        tvc_measure_form_style.setText(styleMap.get(decorationNeedsListBean.getDecoration_style()));//风格

        tvc_address.setText(province_name + city_name + district_name);
        SelectionBean selectionBean = decorationNeedsListBean.getElite();

        if(selectionBean != null){
            if(selectionBean.getMeasurement()!= null){
                measureFee = selectionBean.getMeasurement().getFee();
                order_line_id = selectionBean.getMeasurement().getOrder_line_id();
                order_id= selectionBean.getMeasurement().getOrder_id();
                tv_measure_fee.setText(measureFee==null?"":measureFee);

                String payment_status =selectionBean.getMeasurement().getPayment_status();
                if(payment_status.equals("0")){//0：未支付量房费 1：已支付量房费
                    btn_send_form.setText(UIUtils.getString(R.string.pay_p));
                    isPay = true;
                }
            }
        }
        for(DecorationBiddersBean decorationBiddersBean:list){
            if(decorationBiddersBean.getDesigner_id().equals(designer_id)){
                tvc_name.setText(decorationBiddersBean.getUser_name());

               orders =decorationBiddersBean.getOrders();
                hs_uid = decorationBiddersBean.getUid();
            }
        }

        setMeasureTime();

    }
    @Override
    protected void initListener() {
        super.initListener();
        btn_send_form.setOnClickListener(this);
        tvIllustrate.setOnClickListener(this);
        tvc_time.setOnClickListener(this);
        tvc_address.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tvIllustrate:
                showAlertView();
                break;
            case R.id.btn_send_measure_house_form:
                btn_send_form.setEnabled(false);
                    getJSONObject();
                break;
            case R.id.tvc_measure_form_time:
                pvTime.show();
                break;
            default:
                break;
         }

    }
    private void getJSONObject(){
        name = tvc_name.getText().toString().trim();
        mobileNumber = tvc_phone.getText().toString().trim();
        communityName = tvc_estate.getText().toString().trim();
        houseArea = tvc_area.getText().toString().trim();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(JsonConstants.JSON_MEASURE_FORM_AMOUNT, measureFee);
            jsonObject.put(JsonConstants.JSON_MEASURE_FORM_DESIGNER_ID, designer_id);
            jsonObject.put(JsonConstants.JSON_FLOW_MEASURE_FORM_NEEDS_ID, decorationNeedsListBean.getNeeds_id());
            jsonObject.put(JsonConstants.JSON_MEASURE_FORM_SERVICE_DATE, currentData);
            jsonObject.put(JsonConstants.JSON_MEASURE_FORM_ORDER_TYPE, "");

            if (currentData == null) {
                getErrorHintAlertView(UIUtils.getString(R.string.please_select_quantity_room_time));
                return;
            }

            isSendMeasureForm(jsonObject);


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    private void getErrorHintAlertView(String content) {
        if (error_AlertView != null) {
            error_AlertView = null;
        }
        error_AlertView = new AlertView(UIUtils.getString(R.string.tip), content, null, null, new String[]{UIUtils.getString(R.string.chatroom_audio_recording_erroralert_ok)},this,
                AlertView.Style.Alert, null);
        error_AlertView.show();

    }
    private void isSendMeasureForm(JSONObject jsonObject){
        if(isPay){
            if(order_line_id != null && order_id != null&& !order_line_id.equals("0") && !order_id.equals("0")){
                pay(order_line_id,order_id);
                return;
            }
            postSendMeasureForm(jsonObject);
            return;
        }
        postSendMeasureForm(jsonObject);
    }

    private void pay(String order_line_id,String order_id){

        Intent intent = new Intent(this,FlowMeasureCostActivity.class);
        intent.putExtra(Constant.SeekDesignerDetailKey.DESIGNER_ID,designer_id);
        intent.putExtra(Constant.SeekDesignerDetailKey.NEEDS_ID,decorationNeedsListBean.getNeeds_id());

        intent.putExtra("order_line_id",order_line_id);
        intent.putExtra("order_id",order_id);

        intent.putExtra(JsonConstants.JSON_MEASURE_FORM_AMOUNT, measureFee);
        intent.putExtra(Constant.BundleKey.TEMPDATE_ID, MPStatusMachine.NODE__MEANSURE_PAY);

        this.startActivityForResult(intent,105);
    }

    private void postSendMeasureForm(final JSONObject jsonObject){

        OkJsonRequest.OKResponseCallback okResponseCallback = new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onResponse(JSONObject jsonObject) {

                String jsonString = GsonUtil.jsonToString(jsonObject);
                SolicitationSelection solicitationSelection = GsonUtil.jsonToBean(jsonString, SolicitationSelection.class);

                CustomProgress.cancelDialog();
                if(isPay){
                    pay(solicitationSelection.getElite().getMeasurement().getOrder_line_id(),
                            solicitationSelection.getElite().getMeasurement().getOrder_id());
                }

            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                CustomProgress.dialog.cancel();
            }
        };
        MPServerHttpManager.getInstance().SendMeasureForm(decorationNeedsListBean.getNeeds_id(),designer_id,jsonObject,okResponseCallback);
    }
    private void showAlertView(){
        new AlertView(UIUtils.getString(R.string.illustrate), UIUtils.getString(R.string.warm_tips_content), null,  null,new String[]{UIUtils.getString(R.string.finish_cur_pager)}, this,
                AlertView.Style.Alert, null).show();
    }
    //获取精选装修模式传递过来的数据
    private void getExtraData(){
        Intent intent = getIntent();
        decorationNeedsListBean = (DecorationNeedsListBean)intent.getSerializableExtra(Constant.ConsumerDecorationFragment.DECORATIONbIDDERBEAN);
        designer_id = intent.getStringExtra(Constant.SeekDesignerDetailKey.DESIGNER_ID);

    }
    //设置量房时间
    private void setMeasureTime() {
        ///  The time selector  .
        pvTime = new TimePickerView(this, TimePickerView.Type.ALL);
        pvTime.setRange(2016, 2018);
        ///Control the time range .
        pvTime.setTime(new Date());
        pvTime.setCyclic(false);
        pvTime.setCancelable(false);
        ///  The callback after the time to choose  .
        pvTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {

            @Override
            public void onTimeSelect(Date date) {
                currentData = getTime(date);
                currentData = DateUtil.dateFormat(currentData,"yyyy-MM-dd HH:mm:ss","yyyy年MM月dd日 HH点");
                tvc_time.setText(currentData);
            }
        });
    }
    /**
     * 获取省市区地址
     */
    private void getPCDAddress() {
        mChangeAddressDialog = new AddressDialog();
        mChangeAddressDialog.show(getFragmentManager(), "mChangeAddressDialog");
        mChangeAddressDialog
                .setAddressListener(new AddressDialog.OnAddressCListener() {
                    @Override
                    public void onClick(String province, String proviceCode, String city, String cityCode, String district, String areaCode) {
                        mCurrentProvince = province;
                        mCurrentProvinceCode = proviceCode;
                        mCurrentCity = city;
                        mCurrentCityCode = cityCode;
                        mCurrentDistrict = district;
                        mCurrentDistrictCode = TextUtils.isEmpty(mCurrentDistrict) ? "" : areaCode;
                        if ("null".equals(district) || "none".equals(district) || TextUtils.isEmpty(district)) {
                            district = "";
                        }
                        tvc_address.setText(province + city + district);
                        mChangeAddressDialog.dismiss();
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
        if(resultCode == FlowMeasureCostActivity.RESULT_CODE){

            setResult(10058,new Intent());
            finish();
//            if(data == null){
//                return;
//            }
//            if(data.getStringExtra(Constant.SixProductsFragmentKey.SELECTION).equals(Constant.SixProductsFragmentKey.ISELITE)){
//                CustomProgress.cancelDialog();
//            }
        }

    }

    private AlertView error_AlertView;
    ///控件.
    private TextView text_notuse;
    private TextView tvc_name;
    private TextViewContent tvc_phone;
    private TextView tvc_project_budget;
    private TextView tvc_fitment_budget;
    private TextViewContent tvc_area;
    private TextView tvc_house_type;
    private TextViewContent tvc_time;
    private TextView tvc_address;
    private TextViewContent tvc_estate;
    private TextView tv_measure_fee;
    private TextView tvc_measure_form_type;
    private TextView tvc_measure_form_style;
    private Button btn_send_form;
    private TextView tvIllustrate;
    private TimePickerView pvTime;
    private AddressDialog mChangeAddressDialog;

    /// 变量.
    private String designer_id;
    private String measureFee;
    private String hs_uid;
    private String name;
    private String mobileNumber;
    private String houseArea;

    private String communityName;
    private String currentData;
    private String order_line_id;
    private String order_id;


    private String mCurrentProvince, mCurrentCity, mCurrentDistrict;
    private String mCurrentProvinceCode, mCurrentCityCode, mCurrentDistrictCode;
    private boolean isPay = false;

    ///　集合，类.

    private List<DecorationBiddersBean.OrdersBean> orders;
    private DecorationNeedsListBean decorationNeedsListBean;


}
