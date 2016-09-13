package com.autodesk.shejijia.consumer.personalcenter.consumer.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.consumer.manager.constants.JsonConstants;
import com.autodesk.shejijia.consumer.personalcenter.consumer.entity.AmendDemandBean;
import com.autodesk.shejijia.consumer.personalcenter.consumer.entity.DemandDetailBean;
import com.autodesk.shejijia.consumer.personalcenter.resdecoration.entity.DecorationDetailBean;
import com.autodesk.shejijia.consumer.utils.ApiStatusUtil;
import com.autodesk.shejijia.consumer.utils.AppJsonFileReader;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.uielements.AddressDialog;
import com.autodesk.shejijia.shared.components.common.uielements.CustomProgress;
import com.autodesk.shejijia.shared.components.common.uielements.TextViewContent;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.AlertView;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.OnItemClickListener;
import com.autodesk.shejijia.shared.components.common.uielements.reusewheel.utils.OptionsPickerView;
import com.autodesk.shejijia.shared.components.common.utility.ConvertUtils;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.LogUtils;
import com.autodesk.shejijia.shared.components.common.utility.MPNetworkUtils;
import com.autodesk.shejijia.shared.components.common.utility.RegexUtil;
import com.autodesk.shejijia.shared.components.common.utility.StreamUtils;
import com.autodesk.shejijia.shared.components.common.utility.StringUtils;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.framework.activity.NavigationBarActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

import de.greenrobot.event.EventBus;

/**
 * @author Malidong .
 * @version 1.0 .
 * @date 16-6-7 上午9:15
 * @file AmendDemandActivity.java  .
 * @brief 修改需求.
 */
public class AmendDemandActivity extends NavigationBarActivity implements View.OnClickListener, OnItemClickListener {

    private static final String IS_PUBLIC = "is_public";

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_amend_demand;
    }

    @Override
    protected void initView() {
        super.initView();
        etAmendAmendName = (TextView) findViewById(R.id.et_amend_amend_name);
        etIssueAmendMobile = (EditText) findViewById(R.id.et_issue_amend_mobile);
        tvAmendDesignBudget = (TextView) findViewById(R.id.tv_amend_design_budget);
        tvAmendBudget = (TextView) findViewById(R.id.tv_aemand_budget);
        tvAmendHouseType = (TextView) findViewById(R.id.tv_amend_house_type);
        etAmendArea = (EditText) findViewById(R.id.et_ademand_area);
        tvAmendRoomType = (TextView) findViewById(R.id.tv_amend_room_type);
        tvAmendStyle = (TextView) findViewById(R.id.tv_amend_style);
        tvIssueAddress = (TextView) findViewById(R.id.tv_issue_address);
        etIssueDemandDetailAddress = (TextViewContent) findViewById(R.id.et_issue_demand_detail_address);
        tvPublicTime = (TextView) findViewById(R.id.tv_public_time);
        btnFitmentAmendDemand = (Button) findViewById(R.id.btn_fitment_amend_demand);
    }

    @Override
    protected void initExtraBundle() {
        super.initExtraBundle();
        Bundle extras = getIntent().getExtras();
        needs_id = (String) extras.get(Constant.ConsumerDecorationFragment.NEED_ID);
        wk_template_id = (String) extras.get(Constant.ConsumerDecorationFragment.WK_TEMPLATE_ID);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        setTitleForNavbar(UIUtils.getString(R.string.amend_demand));
        getAmendDemand(needs_id);

        getJsonFileReader();
        setFormViewData();

        /// 提示框.
        initAlertView();
    }

    @Override
    protected void initListener() {
        super.initListener();
        btnFitmentAmendDemand.setOnClickListener(this);
        tvAmendDesignBudget.setOnClickListener(this);
        tvAmendBudget.setOnClickListener(this);
        tvAmendHouseType.setOnClickListener(this);
        tvAmendRoomType.setOnClickListener(this);
        tvAmendStyle.setOnClickListener(this);
        tvIssueAddress.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_fitment_amend_demand: /// 修改重新提交修改的需求 .
                area = etAmendArea.getText().toString();
                mobile = etIssueAmendMobile.getText().toString();
                detail_address = etIssueDemandDetailAddress.getText().toString();
                style = tvAmendStyle.getText().toString();
                boolean regex_area_right = area.matches(RegexUtil.AREA_REGEX);
                boolean regex_address = String.valueOf(detail_address).matches(RegexUtil.ADDRESS_REGEX);
                boolean regex_phoneRight = mobile.matches(RegexUtil.PHONE_REGEX);

                if (TextUtils.isEmpty(mobile) || !regex_phoneRight) {
                    showAlertView(UIUtils.getString(R.string.please_enter_correct_phone_number));
                    return;
                }
                if (TextUtils.isEmpty(area) || !regex_area_right) {
                    showAlertView(UIUtils.getString(R.string.please_input_correct_area));
                    return;
                }
                if (TextUtils.isEmpty(detail_address) || !regex_address) {
                    showAlertView(UIUtils.getString(R.string.please_enter_correct_address));
                    return;
                }

                JSONObject amendJson = new JSONObject();
                try {
                    amendJson.put(JsonConstants.JSON_FLOW_MEASURE_FORM_NEEDS_ID, needs_id);
                    amendJson.put(JsonConstants.JSON_SEND_DESIGN_REQUIREMENTS_CITY, city);
                    amendJson.put(JsonConstants.JSON_SEND_DESIGN_REQUIREMENTS_CITY_NAME, city_name);
                    amendJson.put(JsonConstants.JSON_SEND_DESIGN_REQUIREMENTS_CLICK_NUMBER, click_number);
                    amendJson.put(JsonConstants.JSON_SEND_DESIGN_REQUIREMENTS_COMMUNITY_NAME, detail_address);
                    amendJson.put(JsonConstants.JSON_SEND_DESIGN_REQUIREMENTS_CONSUMER_MOBILE, consumer_mobile);
                    amendJson.put(JsonConstants.JSON_SEND_DESIGN_REQUIREMENTS_CONSUMER_NAME, consumer_name);
                    amendJson.put(JsonConstants.JSON_SEND_DESIGN_REQUIREMENTS_CONTACTS_MOBILE, mobile);
                    amendJson.put(JsonConstants.JSON_SEND_DESIGN_REQUIREMENTS_CONTACTS_NAME, contacts_name);

                    amendJson.put(JsonConstants.JSON_SEND_DESIGN_REQUIREMENTS_DECORATION_STYLE, style);
                    amendJson.put(JsonConstants.JSON_SEND_DESIGN_REQUIREMENTS_DECORATION_BUDGET, decoration_budget);
                    amendJson.put(JsonConstants.JSON_MEASURE_FORM_DESIGN_BUDGET, design_budget);
                    amendJson.put(JsonConstants.JSON_SEND_DESIGN_REQUIREMENTS_DETAIL_DESC, detail_desc);
                    amendJson.put(JsonConstants.JSON_SEND_DESIGN_REQUIREMENTS_DISTRICT, district);
                    amendJson.put(JsonConstants.JSON_MEASURE_FORM_DISTRICT_NAME, district_name);
                    amendJson.put(JsonConstants.JSON_SEND_DESIGN_REQUIREMENTS_HOUSE_AREA, area);
                    amendJson.put(JsonConstants.JSON_SEND_DESIGN_REQUIREMENTS_HOUSE_TYPE, house_type);
                    amendJson.put(JsonConstants.JSON_MODIFY_DESIGNER_REQUIREMENT_LIVING_ROOM, living_room);
                    amendJson.put(JsonConstants.JSON_SEND_DESIGN_REQUIREMENTS_PROVINCE, province);
                    amendJson.put(JsonConstants.JSON_SEND_DESIGN_REQUIREMENTS_PROVINCE_NAME, province_name);
                    amendJson.put(JsonConstants.JSON_MODIFY_DESIGNER_REQUIREMENT_ROOM, room);
                    amendJson.put(JsonConstants.JSON_MODIFY_DESIGNER_REQUIREMENT_TOILET, toilet);


                    /// TODO 九月份迭代 .
                    mDecorationDetailBean = GsonUtil.jsonToBean(amendJson.toString(), DecorationDetailBean.class);

                    amendDemandBean = GsonUtil.jsonToBean(amendJson.toString(), AmendDemandBean.class);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                LogUtils.e("AmendDemandActivity", "amendJson:" + amendJson);
                sendAmendDemand(needs_id, amendJson);
                break;


            case R.id.tv_amend_house_type: /// 修改房屋类型 .
                pvHouseTypeOptions.show();
                break;

            case R.id.tv_amend_room_type: /// 修改户型.
                pvRoomTypeOptions.show();
                break;

            case R.id.tv_amend_style: /// 修改风格 .
                pvStyleOptions.show();
                break;

            case R.id.tv_aemand_budget: /// 修改装修费 .
                pvDecorationBudgetOptions.show();
                break;

            case R.id.tv_amend_design_budget: /// 设置设计费 .
                pvDesignBudgetOptions.show();
                break;

            case R.id.tv_issue_address: /// 设置地址 .
                getPCDAddress();
                break;
        }
    }

    /**
     * 提示框按钮点击事件
     */
    @Override
    public void onItemClick(Object obj, int position) {

        if (obj == mStopDemandAlertView && position != AlertView.CANCELPOSITION) {
            CustomProgress.show(this, "", false, null);
            sendStopDemand(needs_id, 1);
            return;
        }

        if (obj == mStopDemandSuccessAlertView && position != AlertView.CANCELPOSITION) {
            if (!TextUtils.isEmpty(is_public_amend)) {

                Intent intent = new Intent();
                intent.putExtra("is_public_amend", is_public_amend);
                intent.putExtra("custom_string_status", custom_string_status_amend);
                intent.putExtra("wk_template_id", wk_template_id_amend);
                intent.putExtra(JsonConstants.JSON_FLOW_MEASURE_FORM_NEEDS_ID, needs_id);
                setResult(AmendDemandActivity.REFUSEResultCode, intent);

            }
            finish();
            return;
        }
        if (obj == mAmendDemandSuccessAlertView && position != AlertView.CANCELPOSITION) {
            if (amendDemandBean != null) {
                if (Constant.NumKey.CERTIFIED_FAILED.equals(custom_string_status)
                        || Constant.NumKey.CERTIFIED_FAILED_1.equals(custom_string_status)) {
                    /**
                     * 审核未通过,修改需求表单后重新审核.
                     */
                    amendDemandBean.setCustom_string_status(Constant.NumKey.CERTIFIED_CHECKING);
                }

                Intent intent = new Intent();
                Bundle bundle = new Bundle();

                bundle.putSerializable(JsonConstants.AMENDEMANDBEAN, amendDemandBean);

                intent.putExtras(bundle);
                setResult(ResultCode, intent);
            }

            if (null != mDecorationDetailBean) {
                EventBus.getDefault().postSticky(mDecorationDetailBean);
            }
            finish();
            return;
        }
        if (obj == mAmendDemandSuccessAlertView) {
            finish();
            return;

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
                demandDetailBean = GsonUtil.jsonToBean(info, DemandDetailBean.class);
                updateViewFromData(demandDetailBean);
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                MPNetworkUtils.logError(TAG, volleyError);
                CustomProgress.cancelDialog();
                ApiStatusUtil.getInstance().apiStatuError(volleyError, AmendDemandActivity.this);
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
                    String s = GsonUtil.jsonToString(jsonObject);
                    is_public_amend = jsonObject.getString(IS_PUBLIC);
                    wk_template_id_amend = jsonObject.getString("wk_template_id");
                    custom_string_status_amend = jsonObject.getString("custom_string_status");
                    mStopDemandSuccessAlertView.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                MPNetworkUtils.logError(TAG, volleyError);
                CustomProgress.dialog.cancel();
                ApiStatusUtil.getInstance().apiStatuError(volleyError, AmendDemandActivity.this);
            }
        };
        MPServerHttpManager.getInstance().getStopDesignerRequirement(needs_id,
                is_deleted, okResponseCallback);
    }

    /**
     * 修改需求
     */
    private void sendAmendDemand(String needs_id, JSONObject amendJson) {
        CustomProgress.show(this, "", false, null);

        OkJsonRequest.OKResponseCallback okResponseCallback = new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                CustomProgress.cancelDialog();
                if (!CustomProgress.dialog.isShowing()) {
                    mAmendDemandSuccessAlertView.show();
                }
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                MPNetworkUtils.logError(TAG, volleyError);
                CustomProgress.cancelDialog();
                if (!CustomProgress.dialog.isShowing()) {
//                    new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.network_error), null, new String[]{"确定"}, null, AmendDemandActivity.this,
//                            AlertView.Style.Alert, null).show();
                    ApiStatusUtil.getInstance().apiStatuError(volleyError, AmendDemandActivity.this);
                }
            }
        };
        MPServerHttpManager.getInstance().getModifyDesignerRequirement(needs_id, wk_template_id, amendJson, okResponseCallback);
    }

    /**
     * 　网络获取数据，更新页面
     */
    private void updateViewFromData(DemandDetailBean demandDetailBean) {
        house_type = demandDetailBean.getHouse_type();
        room = demandDetailBean.getRoom();
        toilet = demandDetailBean.getToilet();
        living_room = demandDetailBean.getLiving_room();
        house_area = demandDetailBean.getHouse_area();
        click_number = demandDetailBean.getClick_number();
        consumer_mobile = demandDetailBean.getConsumer_mobile();
        consumer_name = demandDetailBean.getConsumer_name();
        contacts_mobile = demandDetailBean.getContacts_mobile();
        contacts_name = demandDetailBean.getContacts_name();
        this.custom_string_status = demandDetailBean.getCustom_string_status();
        decoration_budget = demandDetailBean.getDecoration_budget();
        detail_desc = demandDetailBean.getDetail_desc();
        province_name = demandDetailBean.getProvince_name();
        city_name = demandDetailBean.getCity_name();
        String district_name = demandDetailBean.getDistrict_name();
        province = demandDetailBean.getProvince();
        city = demandDetailBean.getCity() + "";
        district = demandDetailBean.getDistrict();
        design_budget = demandDetailBean.getDesign_budget();
        String publish_time = demandDetailBean.getPublish_time();
        String community_name = demandDetailBean.getCommunity_name();

        if (this.custom_string_status.equals(Constant.NumKey.CERTIFIED_PASS) || this.custom_string_status.equals(Constant.NumKey.CERTIFIED_PASS_1)) {
            btnFitmentAmendDemand.setClickable(false);
            btnFitmentAmendDemand.setPressed(false);
            btnFitmentAmendDemand.setBackgroundColor(UIUtils.getColor(R.color.font_gray));
        }

        district_name = UIUtils.getNoStringIfEmpty(district_name);

        String address = province_name + city_name + district_name;

        convertEn2Cn();

        String livingRoom_room_toilet = room_convert + living_room_convert + toilet_convert;

        tvAmendRoomType.setText(TextUtils.isEmpty(livingRoom_room_toilet) ? UIUtils.getString(R.string.no_data) : livingRoom_room_toilet);
        tvAmendStyle.setText(TextUtils.isEmpty(decoration_style_convert) ? UIUtils.getString(R.string.no_data) : decoration_style_convert);
        tvAmendHouseType.setText(TextUtils.isEmpty(house_type_convert) ? UIUtils.getString(R.string.no_data) : house_type_convert);
        etAmendAmendName.setText(TextUtils.isEmpty(contacts_name) ? UIUtils.getString(R.string.no_data) : contacts_name);
        etIssueAmendMobile.setText(TextUtils.isEmpty(contacts_mobile) ? UIUtils.getString(R.string.no_data) : contacts_mobile);
        tvAmendDesignBudget.setText(TextUtils.isEmpty(design_budget) ? UIUtils.getString(R.string.no_data) : design_budget);
        tvAmendBudget.setText(TextUtils.isEmpty(decoration_budget) ? UIUtils.getString(R.string.no_data) : decoration_budget);
        etAmendArea.setText(TextUtils.isEmpty(house_area) ? UIUtils.getString(R.string.no_data) : house_area);
        tvIssueAddress.setText(TextUtils.isEmpty(address) ? UIUtils.getString(R.string.no_data) : address);
        etIssueDemandDetailAddress.setText(TextUtils.isEmpty(community_name) ? UIUtils.getString(R.string.no_data) : community_name);
        tvPublicTime.setText(TextUtils.isEmpty(publish_time) ? UIUtils.getString(R.string.no_data) : publish_time);
    }

    /**
     * 设置表单的写入逻辑
     */
    private void setFormViewData() {
        /// 房屋类型.
        setHouseType();
        /// 风格.
        setStyleType();
        /// 室、厅、卫.
        setRoomType();
        /// 设计预算 .
        setDesignBudget();
        /// 装修预算 .
        setDecorationBudget();
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
                                        public void onClick(String province_name_1, String province_1, String city_name_1, String city_1, String district_name_1, String district_1) {

                                            province_name = province_name_1;
                                            city_name = city_name_1;
                                            district_name = district_name_1;

                                            province = province_1;
                                            city = city_1;
                                            district = district_1;

                                            district_name_1 = UIUtils.getNoStringIfEmpty(district_name);

                                            String address = province_name + city_name + district_name_1;
                                            tvIssueAddress.setText(address);
                                            mChangeAddressDialog.dismiss();
                                        }
                                    }
                );
    }

    /**
     * 选择 :室 厅 卫
     */
    private void setRoomType() {
        final ArrayList<ArrayList<ArrayList<String>>> toiletsList = new ArrayList<>();
        final ArrayList<ArrayList<String>> hallsList = new ArrayList<>();
        final ArrayList<String> roomsList = new ArrayList<>();
        String[] rooms = UIUtils.getResources().getStringArray(R.array.mlivingroom);
        String[] halls = UIUtils.getResources().getStringArray(R.array.hall);
        String[] toilets = UIUtils.getResources().getStringArray(R.array.toilet);
        pvRoomTypeOptions = new OptionsPickerView(this);
        //room
        for (String op : rooms) {
            roomsList.add(op);
        }
        //hall
        ArrayList<String> options2Items_01 = new ArrayList<>();
        for (String op2 : halls) {
            options2Items_01.add(op2);
        }
        int length = rooms.length;
        for (int i = 0; i < length; i++) {
            hallsList.add(options2Items_01);
        }
        //toilet
        ArrayList<ArrayList<String>> options3Items_01 = new ArrayList<>();
        ArrayList<String> options3Items_01_01 = new ArrayList<>();
        for (String op3 : toilets) {
            options3Items_01_01.add(op3);
        }
        int length1 = halls.length;
        for (int i = 0; i < length1; i++) {
            options3Items_01.add(options3Items_01_01);
        }
        int length2 = rooms.length;
        for (int i = 0; i < length2; i++) {
            toiletsList.add(options3Items_01);
        }
        pvRoomTypeOptions.setPicker(roomsList, hallsList, toiletsList, true);
        pvRoomTypeOptions.setCyclic(false, false, false);
        pvRoomTypeOptions.setSelectOptions(0, 0, 0);
        pvRoomTypeOptions.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int op_room, int op_living_room, int op_toilet) {

                room_convert = roomsList.get(op_room);
                living_room_convert = hallsList.get(op_room).get(op_living_room);
                toilet_convert = toiletsList.get(op_room).get(op_living_room).get(op_toilet);
                String roomType = room_convert + living_room_convert + toilet_convert;

                tvAmendRoomType.setText(roomType);
                Map<String, String> roomHall = AppJsonFileReader.getRoomHall(AmendDemandActivity.this);
                Map<String, String> livingRoom = AppJsonFileReader.getLivingRoom(AmendDemandActivity.this);
                Map<String, String> toiletMap = AppJsonFileReader.getToilet(AmendDemandActivity.this);

                /// convet .
                living_room = ConvertUtils.getKeyByValue(livingRoom, living_room_convert);
                room = ConvertUtils.getKeyByValue(roomHall, room_convert);
                toilet = ConvertUtils.getKeyByValue(toiletMap, toilet_convert);
            }
        });
    }

    /**
     * 选择风格
     */
    private void setStyleType() {
        final ArrayList<String> styleItems;
        styleItems = new ArrayList<>();
        String[] styles = UIUtils.getStringArray(R.array.style);
        pvStyleOptions = new OptionsPickerView(this);
        for (String item : styles) {
            styleItems.add(item);
        }
        pvStyleOptions.setPicker(styleItems);
        pvStyleOptions.setSelectOptions(0);
        pvStyleOptions.setCyclic(false);
        pvStyleOptions.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                style = styleItems.get(options1);
                tvAmendStyle.setText(style);
                Map<String, String> space = AppJsonFileReader.getStyle(AmendDemandActivity.this);
                style = ConvertUtils.getKeyByValue(space, style);
            }
        });
    }

    /**
     * 选择房屋类型
     */
    private void setHouseType() {
        final ArrayList<String> houseTypeItems = new ArrayList<>();
        String[] houseType = UIUtils.getResources().getStringArray(R.array.hType);
        pvHouseTypeOptions = new OptionsPickerView(this);
        for (String item : houseType) {
            houseTypeItems.add(item);
        }
        pvHouseTypeOptions.setPicker(houseTypeItems);
        pvHouseTypeOptions.setSelectOptions(0);
        pvHouseTypeOptions.setCyclic(false);
        pvHouseTypeOptions.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                house_type = houseTypeItems.get(options1);
                tvAmendHouseType.setText(house_type);
                Map<String, String> space = AppJsonFileReader.getSpace(AmendDemandActivity.this);
                house_type = ConvertUtils.getKeyByValue(space, house_type);
            }
        });
    }

    /**
     * 设置设计费
     */
    private void setDesignBudget() {
        final ArrayList<String> designBudgetItems = new ArrayList<>();
        String[] design_budgets = UIUtils.getStringArray(R.array.design_budget);
        pvDesignBudgetOptions = new OptionsPickerView(this);
        for (String item : design_budgets) {
            designBudgetItems.add(item);
        }
        pvDesignBudgetOptions.setPicker(designBudgetItems);
        pvDesignBudgetOptions.setSelectOptions(2);
        pvDesignBudgetOptions.setCyclic(false);
        pvDesignBudgetOptions.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                design_budget = designBudgetItems.get(options1);
                tvAmendDesignBudget.setText(design_budget);
            }
        });
    }

    /**
     * 设置装修预算 .
     */
    private void setDecorationBudget() {
        final ArrayList<String> decorationBudgetItems = new ArrayList<>();
        String[] decoration_budgets = UIUtils.getStringArray(R.array.decoration_budget);
        pvDecorationBudgetOptions = new OptionsPickerView(this);
        for (String item : decoration_budgets) {
            decorationBudgetItems.add(item);
        }
        pvDecorationBudgetOptions.setPicker(decorationBudgetItems);
        pvDecorationBudgetOptions.setSelectOptions(2);
        pvDecorationBudgetOptions.setCyclic(false);
        pvDecorationBudgetOptions.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                decoration_budget = decorationBudgetItems.get(options1);
                tvAmendBudget.setText(decoration_budget);
            }
        });
    }

    /**
     * 读取室卫厅转化json对象
     */
    private void getJsonFileReader() {
        styleJson = AppJsonFileReader.getStyle(AmendDemandActivity.this);
        spaceJson = AppJsonFileReader.getSpace(AmendDemandActivity.this);
        livingRoomJson = AppJsonFileReader.getLivingRoom(AmendDemandActivity.this);
        roomJson = AppJsonFileReader.getRoomHall(AmendDemandActivity.this);
        toiletJson = AppJsonFileReader.getToilet(AmendDemandActivity.this);
    }

    /**
     * 将英文转换为汉字
     */
    private void convertEn2Cn() {
        decoration_style_convert = ConvertUtils.getConvert2CN(styleJson, demandDetailBean.getDecoration_style());
        house_type_convert = ConvertUtils.getConvert2CN(spaceJson, house_type);
        living_room_convert = ConvertUtils.getConvert2CN(livingRoomJson, living_room);
        room_convert = ConvertUtils.getConvert2CN(roomJson, room);
        toilet_convert = ConvertUtils.getConvert2CN(toiletJson, toilet);
    }

    /**
     * 输入非法条件时的弹出框
     */
    private void showAlertView(String content) {
        new AlertView(UIUtils.getString(R.string.tip), content, null, null, new String[]{UIUtils.getString(R.string.sure)}, AmendDemandActivity.this, AlertView.Style.Alert, null).show();
    }

    /**
     * 提示框.
     */
    private void initAlertView() {
        mStopDemandAlertView = new AlertView(UIUtils.getString(R.string.termination_demand), UIUtils.getString(R.string.termination_demand_true), UIUtils.getString(R.string.cancel), null, new String[]{UIUtils.getString(R.string.sure)}, this, AlertView.Style.Alert, this).setCancelable(true);
        mStopDemandSuccessAlertView = new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.termination_demand_success), null, null, new String[]{UIUtils.getString(R.string.sure)}, AmendDemandActivity.this, AlertView.Style.Alert, AmendDemandActivity.this);
        mAmendDemandSuccessAlertView = new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.modify_success), null, null, new String[]{UIUtils.getString(R.string.sure)}, AmendDemandActivity.this, AlertView.Style.Alert, AmendDemandActivity.this);
    }

    /**
     * 滑动当前页面执行的操作
     *
     * @param ev 触摸事件
     * @return 是否处理触摸事件
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    /**
     * 滑动当前页面是隐藏软键盘
     */
    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            return !(event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom);
        }
        return false;
    }

    /// 控件　.
    private TextView etAmendAmendName, tvAmendDesignBudget, tvAmendBudget, tvAmendHouseType;
    private EditText etIssueAmendMobile, etAmendArea;
    private TextView tvAmendRoomType, tvAmendStyle, tvIssueAddress, tvPublicTime;
    private Button btnFitmentAmendDemand;
    private AddressDialog mChangeAddressDialog;
    private TextViewContent etIssueDemandDetailAddress;
    private OptionsPickerView pvDesignBudgetOptions;
    private OptionsPickerView pvDecorationBudgetOptions;
    private OptionsPickerView pvStyleOptions;
    private OptionsPickerView pvRoomTypeOptions; /// 选择器 .
    private OptionsPickerView pvHouseTypeOptions;
    private AlertView mStopDemandAlertView;
    private AlertView mStopDemandSuccessAlertView;
    private AlertView mAmendDemandSuccessAlertView;

    ///　变量.
    private String area;
    private String mobile;
    private String detail_address;
    private String province, city, district;
    private String province_name, city_name, district_name;
    private String living_room, room, toilet;
    private String house_type, house_area, needs_id, wk_template_id;
    private int click_number;
    private String consumer_mobile, consumer_name, contacts_mobile, contacts_name;
    private String detail_desc, decoration_budget, design_budget, custom_string_status;
    private DemandDetailBean demandDetailBean;
    private AmendDemandBean amendDemandBean;
    private DecorationDetailBean mDecorationDetailBean;
    private Map<String, String> styleJson, spaceJson, livingRoomJson, roomJson, toiletJson;
    private String decoration_style_convert, house_type_convert, living_room_convert;
    private String room_convert, toilet_convert;
    private String style;
    private String is_public_amend = "0";
    private String wk_template_id_amend = "0";
    private String custom_string_status_amend = "0";
    public static final int ResultCode = 100101;
    public static final int REFUSEResultCode = 1009;

}