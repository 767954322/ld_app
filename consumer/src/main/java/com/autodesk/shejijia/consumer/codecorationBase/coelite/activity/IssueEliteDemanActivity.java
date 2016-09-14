package com.autodesk.shejijia.consumer.codecorationBase.coelite.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.consumer.manager.constants.JsonConstants;
import com.autodesk.shejijia.consumer.personalcenter.consumer.entity.IssueDemandBean;
import com.autodesk.shejijia.consumer.utils.AppJsonFileReader;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.uielements.AddressDialog;
import com.autodesk.shejijia.shared.components.common.uielements.CustomProgress;
import com.autodesk.shejijia.shared.components.common.uielements.HomeTypeDialog;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.AlertView;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.OnItemClickListener;
import com.autodesk.shejijia.shared.components.common.uielements.reusewheel.utils.OptionsPickerView;
import com.autodesk.shejijia.shared.components.common.utility.ConvertUtils;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.LogUtils;
import com.autodesk.shejijia.shared.components.common.utility.MPNetworkUtils;
import com.autodesk.shejijia.shared.components.common.utility.RegexUtil;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.framework.activity.NavigationBarActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/**
 * @author  .
 * @version 1.0 .
 * @date 16-8-16
 * @file IssueEliteDemanActivity.java  .
 * @brief 精选发布需求 .
 */

public class IssueEliteDemanActivity extends NavigationBarActivity implements View.OnClickListener, OnItemClickListener,View.OnFocusChangeListener {
    private HomeTypeDialog homeTypeDialog;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_issue_elite_deman;
    }

    @Override
    protected void initView() {
        super.initView();
        llIssueHouseType = (LinearLayout) findViewById(R.id.ll_issue_house_type);
        llIssueStyle = (LinearLayout) findViewById(R.id.ll_issue_style);
        etIssueDemandName = (TextView) findViewById(R.id.et_issue_demand_name);
        etIssueDemandMobile = (EditText) findViewById(R.id.et_issue_demand_mobile);
        etIssueDemandArea = (EditText) findViewById(R.id.et_issue_demand_area);
        btnSendDemand = (Button) findViewById(R.id.btn_send_demand);
        tvIssueHouseType = (TextView) findViewById(R.id.tv_issue_house_type);
        tvIssueDemandDesignBudget = (TextView) findViewById(R.id.tv_issue_demand_design_budget);
        tvIssueDemandBudget = (TextView) findViewById(R.id.tv_issue_demand_budget);
        tvIssueRoom = (TextView) findViewById(R.id.tv_issue_room);
        tvIssueStyle = (TextView) findViewById(R.id.tv_issue_style);
        tvIssueAddress = (TextView) findViewById(R.id.tv_issue_address);
        tvIssueDemandDetailAddress = (EditText) findViewById(R.id.tv_issue_demand_detail_address);

    }


    @Override
    protected void initExtraBundle() {
        super.initExtraBundle();
        nick_name = getIntent().getStringExtra(Constant.ConsumerPersonCenterFragmentKey.NICK_NAME);
        etIssueDemandName.setText(nick_name);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        setTitleForNavbar(UIUtils.getString(R.string.reservation));
        setHouseType();
        setStyleType();
        setRoomType();
        setDesignBudget();
        setDecorationBudget();
        initAlertView();
    }

    @Override
    protected void initListener() {
        super.initListener();
        btnSendDemand.setOnClickListener(this);
        llIssueHouseType.setOnClickListener(this);
        tvIssueRoom.setOnClickListener(this);
        llIssueStyle.setOnClickListener(this);
        tvIssueDemandBudget.setOnClickListener(this);
        tvIssueDemandDesignBudget.setOnClickListener(this);
        tvIssueAddress.setOnClickListener(this);
        etIssueDemandArea.setOnFocusChangeListener(this);
    }
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()){
            case R.id.et_issue_demand_area:
                onFocusChangeForArea(hasFocus);
                break;
            default:
                break;
        }

    }
    /**
     *设置房屋面积
     */
    private void onFocusChangeForArea(boolean hasFocus){
        if (!hasFocus) {
            String area = etIssueDemandArea.getText().toString().trim();
            area = String.format("%.2f", Double.valueOf(area));
            etIssueDemandArea.setText(area);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_issue_house_type: /// 房屋类型 .
                pvHouseTypeOptions.show();
                etIssueDemandArea.clearFocus();
                break;

            case R.id.tv_issue_room: /// 请选择户型：室 厅 卫 .
                homeTypeDialog.show(getFragmentManager(), null);
                etIssueDemandArea.clearFocus();
                break;

            case R.id.ll_issue_style: /// 风格 .
                pvStyleOptions.show();
                etIssueDemandArea.clearFocus();
                break;

            case R.id.tv_issue_demand_budget: /// 请选择装修预算 .
                pvDecorationBudgetOptions.show();
                etIssueDemandArea.clearFocus();
                break;

            case R.id.tv_issue_demand_design_budget: /// 请选择设计预算 .
                pvDesignBudgetOptions.show();
                etIssueDemandArea.clearFocus();
                break;

            case R.id.tv_issue_address: /// 请选择地址：省 市 区 .
                getPCDAddress();
                etIssueDemandArea.clearFocus();
                break;

            case R.id.btn_send_demand: /// 提交 .
                commit();
                break;
        }
    }

    /**
     * 点击提交按钮 发布精选需求
     */
    private  void commit(){
        if (!isSendState) {
            return;
        }
        etIssueDemandArea.clearFocus();
        String area = etIssueDemandArea.getText().toString();

        String mobile = etIssueDemandMobile.getText().toString();
        String detailAddress = tvIssueDemandDetailAddress.getText().toString();
        if(!VerificationRequired(area,mobile,detailAddress)){
            return;
        }
        JSONObject jsonObject = getJSONObject(area,mobile,detailAddress);
        isSendState = false;
        CustomProgress.show(this, UIUtils.getString(R.string.data_submission), false, null);
        sendDesignRequirements(jsonObject);

    }
    private boolean VerificationRequired(String area,String mobile,String detail_address){
        boolean phoneRight = mobile.matches(RegexUtil.PHONE_REGEX);
        boolean regex_address_right = detail_address.matches(RegexUtil.ADDRESS_REGEX);
        if (TextUtils.isEmpty(mobile) || !phoneRight) {
            showAlertView(R.string.please_enter_correct_phone_number);
            return false;
        }

        area = (area != null && area.length() > 0) ? String.format("%.2f", Double.valueOf(area)) : "";
        etIssueDemandArea.setText(area);
        String subNum = "0";
        if (area.contains(".")) {
            subNum = area.substring(0, area.indexOf("."));
        }
        if (TextUtils.isEmpty(area) || Float.valueOf(area) == 0) {
            showAlertView(R.string.please_input_correct_area);
            return false;
        }

        if ((subNum.length() > 1 && subNum.startsWith("0")) || subNum.length() > 4) {
            showAlertView(R.string.please_input_correct_area);
            return false;
        }
        if (!area.matches("^[0-9]{1,4}+(.[0-9]{1,2})?$") || subNum.length() > 4) {
            showAlertView(R.string.please_input_correct_area);
            return false;
        }

        if (TextUtils.isEmpty(mCurrentDistrictCode)) {
            showAlertView(R.string.please_select_addresses);
            return false;
        }
        if (TextUtils.isEmpty(detail_address) || !regex_address_right) {
            showAlertView(R.string.please_enter_correct_address);
            return false;
        }
        return true;

    }
    private JSONObject getJSONObject(String area,String mobile,String detail_address){
        JSONObject jsonObject = new JSONObject();
        try {
            String click_number = "0";
            jsonObject.put(JsonConstants.JSON_SEND_DESIGN_REQUIREMENTS_CITY, mCurrentCityCode);
            jsonObject.put(JsonConstants.JSON_SEND_DESIGN_REQUIREMENTS_CITY_NAME, mCurrentCity);
            jsonObject.put(JsonConstants.JSON_SEND_DESIGN_REQUIREMENTS_CLICK_NUMBER, click_number);
            jsonObject.put(JsonConstants.JSON_SEND_DESIGN_REQUIREMENTS_COMMUNITY_NAME, detail_address);
            jsonObject.put(JsonConstants.JSON_SEND_DESIGN_REQUIREMENTS_CONSUMER_MOBILE, mobile);
            jsonObject.put(JsonConstants.JSON_SEND_DESIGN_REQUIREMENTS_CONSUMER_NAME, nick_name);
            jsonObject.put(JsonConstants.JSON_MODIFY_DESIGNER_REQUIREMENT_CONTACTS_MOBILE, mobile);
            jsonObject.put(JsonConstants.JSON_MODIFY_DESIGNER_REQUIREMENT_CONTACTS_NAME, nick_name);
            jsonObject.put(JsonConstants.JSON_SEND_DESIGN_REQUIREMENTS_DECORATION_BUDGET, mDecorationBudget);
            jsonObject.put(JsonConstants.JSON_SEND_DESIGN_REQUIREMENTS_DECORATION_STYLE, style);
            jsonObject.put(JsonConstants.JSON_MEASURE_FORM_DESIGN_BUDGET, mDesignBudget);
            jsonObject.put(JsonConstants.JSON_SEND_DESIGN_REQUIREMENTS_DETAIL_DESC, "desc");
            jsonObject.put(JsonConstants.JSON_SEND_DESIGN_REQUIREMENTS_DISTRICT, mCurrentDistrictCode);
            jsonObject.put(JsonConstants.JSON_SEND_DESIGN_REQUIREMENTS_DISTRICT_NAME, mCurrentDistrict);
            jsonObject.put(JsonConstants.JSON_MODIFY_DESIGNER_REQUIREMENT_HOUSE_AREA, area);
            jsonObject.put(JsonConstants.JSON_MODIFY_DESIGNER_REQUIREMENT_HOUSE_TYPE, house_type);
            jsonObject.put(JsonConstants.JSON_MODIFY_DESIGNER_REQUIREMENT_LIVING_ROOM, livingRoom);
            jsonObject.put(JsonConstants.JSON_SEND_DESIGN_REQUIREMENTS_PROVINCE, mCurrentProvinceCode);
            jsonObject.put(JsonConstants.JSON_SEND_DESIGN_REQUIREMENTS_PROVINCE_NAME, mCurrentProvince);
            jsonObject.put(JsonConstants.JSON_MODIFY_DESIGNER_REQUIREMENT_ROOM, room);
            jsonObject.put(JsonConstants.JSON_SEND_DESIGN_REQUIREMENTS_TOILET, mToilet);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    /**
     * @brief 打开AlertView对话框
     */
    private void showAlertView(int content) {
        new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(content), null, null, new String[]{UIUtils.getString(R.string.sure)}, IssueEliteDemanActivity.this, AlertView.Style.Alert, null).show();
    }

    /**
     * @brief 把String数组转成List集合
     */
    private List<String> filledData(String[] date) {
        List<String> mSortList = new ArrayList<String>();
        for (String str : date) {
            mSortList.add(str);
        }
        return mSortList;
    }

    /**
     * @brief 获取省市区地址
     */
    private void getPCDAddress() {
        mChangeAddressDialog = new AddressDialog();
        mChangeAddressDialog.show(getFragmentManager(), "mChangeAddressDialog");
        mChangeAddressDialog
                .setAddressListener(new AddressDialog.OnAddressCListener() {
                    @Override
                    public void onClick(String province, String provinceCode, String city, String cityCode, String area, String areaCode) {
                        mCurrentProvince = province;
                        mCurrentProvinceCode = provinceCode;
                        mCurrentCity = city;
                        mCurrentCityCode = cityCode;
                        mCurrentDistrict = area;
                        mCurrentDistrictCode = areaCode;

                        area = UIUtils.getNoStringIfEmpty(area);

                        tvIssueAddress.setText(province + " " + city + " " + area);
                        mChangeAddressDialog.dismiss();
                    }

                });
    }


    /**
     * 发布需求
     *
     * @param jsonObject
     */
    private void sendDesignRequirements(JSONObject jsonObject) {
        MPServerHttpManager.getInstance().sendDesignRequirements(jsonObject, true, new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                CustomProgress.cancelDialog();
                String str = GsonUtil.jsonToString(jsonObject);
                LogUtils.i(TAG, str);
                IssueDemandBean issueDemandBean = GsonUtil.jsonToBean(str, IssueDemandBean.class);
                if (issueDemandBean != null && issueDemandBean.getNeeds_id() != null && issueDemandBean.getNeeds_id().length() > 0) {
                    mSendDesignRequirementSuccessAlertView.show();
                }
                isSendState = true;
//                Intent intent = new Intent();
//                intent.putExtra("SUCCESS", success);
//                setResult(RESULT_CODE, intent);
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                MPNetworkUtils.logError(TAG, volleyError);
                isSendState = true;
                CustomProgress.cancelDialog();
                showAlertView(R.string.network_error);
            }
        });
    }

    /**
     * @brief 设置室 厅 卫
     */
    private void setRoomType() {
        homeTypeDialog = HomeTypeDialog.getInstance(this);
        homeTypeDialog.setOnAddressCListener(new HomeTypeDialog.OnAddressCListener() {
            @Override
            public void onClick(String roomName, String livingRoom, String toilet) {
                String roomType = roomName + livingRoom + toilet;
                tvIssueRoom.setText(roomType);

                /// convet .
                Map<String, String> livingRoomMap = AppJsonFileReader.getLivingRoom(IssueEliteDemanActivity.this);
                Map<String, String> roomHallMap = AppJsonFileReader.getRoomHall(IssueEliteDemanActivity.this);
                Map<String, String> toiletMap = AppJsonFileReader.getToilet(IssueEliteDemanActivity.this);

                livingRoom = ConvertUtils.getKeyByValue(livingRoomMap, livingRoom);
                room = ConvertUtils.getKeyByValue(roomHallMap, roomName);
                mToilet = ConvertUtils.getKeyByValue(toiletMap, toilet);

                homeTypeDialog.dismiss();
            }
        });
    }

    /**
     * @brief 风格
     */
    private void setStyleType() {
        final ArrayList<String> styleItems = new ArrayList<>();
        List<String> styles = filledData(UIUtils.getStringArray(R.array.style));

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
                tvIssueStyle.setText(style);
                Map<String, String> space = AppJsonFileReader.getStyle(IssueEliteDemanActivity.this);
                style = ConvertUtils.getKeyByValue(space, style);
            }
        });
    }

    /**
     * 设置房屋类型
     */
    private void setHouseType() {
        final ArrayList<String> houseTypeItems = new ArrayList<>();
        List<String> houseType = filledData(UIUtils.getStringArray(R.array.hType));

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
                tvIssueHouseType.setText(house_type);
                Map<String, String> space = AppJsonFileReader.getSpace(IssueEliteDemanActivity.this);
                house_type = ConvertUtils.getKeyByValue(space, house_type);
            }
        });
    }

    /**
     * 设置设计费
     */
    private void setDesignBudget() {
        final ArrayList<String> designBudgetItems = new ArrayList<>();
        List<String> design_budgets = filledData(getResources().getStringArray(R.array.design_budget));
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
                mDesignBudget = designBudgetItems.get(options1);
                tvIssueDemandDesignBudget.setText(mDesignBudget);
            }
        });
    }

    /**
     * 设置量房费
     */
    private void setDecorationBudget() {
        final ArrayList<String> decorationBudgetItems = new ArrayList<>();
        List<String> decoration_budgets = filledData(getResources().getStringArray(R.array.decoration_budget));
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
                mDecorationBudget = decorationBudgetItems.get(options1);
                tvIssueDemandBudget.setText(mDecorationBudget);
            }
        });
    }

    /**
     * 提示框
     */
    private void initAlertView() {
        mSendDesignRequirementSuccessAlertView = new AlertView(UIUtils.getString(R.string.send_design_requirement_save_success_alert_view), UIUtils.getString(R.string.send_design_requirement_success_alert_view_1), null, null, new String[]{UIUtils.getString(R.string.sure)}, this, AlertView.Style.Alert, this).setCancelable(false);
    }

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
     * @brief 是否隐藏View
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

    @Override
    public void onItemClick(Object obj, int position) {
        if (obj == mSendDesignRequirementSuccessAlertView && position != AlertView.CANCELPOSITION) {
            finish();
        }
    }

    /// 控件.
    private LinearLayout llIssueHouseType;
    private LinearLayout llIssueStyle;
    private TextView etIssueDemandName;
    private TextView tvIssueDemandBudget;
    private TextView tvIssueDemandDesignBudget;
    private TextView tvIssueHouseType;
    private TextView tvIssueRoom;
    private TextView tvIssueStyle;
    private TextView tvIssueAddress;
    private EditText tvIssueDemandDetailAddress;
    private EditText etIssueDemandMobile;
    private EditText etIssueDemandArea;
    private Button btnSendDemand;
    private AlertView mSendDesignRequirementSuccessAlertView;
    private AddressDialog mChangeAddressDialog;
    private OptionsPickerView pvDesignBudgetOptions;
    private OptionsPickerView pvDecorationBudgetOptions, pvStyleOptions, pvHouseTypeOptions;

    /// 变量.
    private String mCurrentProvince, mCurrentCity, mCurrentDistrict;
    private String mCurrentProvinceCode, mCurrentCityCode, mCurrentDistrictCode;
    private String house_type; /// 房屋类型 .
    private String style;   /// 风格 .
    private String mDesignBudget;
    private String mDecorationBudget;
    private String nick_name;
    private String room, livingRoom, mToilet;
    private boolean isSendState = true;
    public static final int RESULT_CODE = 101;



}
