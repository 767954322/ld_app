package com.autodesk.shejijia.consumer.codecorationBase.packages.activity;

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
import com.autodesk.shejijia.consumer.codecorationBase.packages.view.ImageUrlUtils;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.consumer.manager.constants.JsonConstants;
import com.autodesk.shejijia.consumer.personalcenter.consumer.entity.ConsumerEssentialInfoEntity;
import com.autodesk.shejijia.consumer.utils.ApiStatusUtil;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.uielements.AddressDialog;
import com.autodesk.shejijia.shared.components.common.uielements.CustomProgress;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.AlertView;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.OnItemClickListener;
import com.autodesk.shejijia.shared.components.common.uielements.reusewheel.utils.OptionsPickerView;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.MPNetworkUtils;
import com.autodesk.shejijia.shared.components.common.utility.RegexUtil;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.framework.AdskApplication;
import com.autodesk.shejijia.shared.framework.activity.NavigationBarActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by allengu on 16-8-23.
 */
public class ReservationFormActivity extends NavigationBarActivity implements View.OnClickListener, OnItemClickListener {


    @Override
    protected int getLayoutResId() {

        return R.layout.activity_reservation_form;
    }

    @Override
    protected void initView() {
        super.initView();

        et_issue_demand_name = (TextView) findViewById(R.id.et_issue_demand_name);
        et_issue_demand_mobile = (EditText) findViewById(R.id.et_issue_demand_mobile);
        et_issue_demand_area = (EditText) findViewById(R.id.et_issue_demand_area);
        btn_send_demand = (Button) findViewById(R.id.btn_send_demand);
        tv_issue_demand_budget = (TextView) findViewById(R.id.tv_issue_demand_budget);
        tv_issue_address = (TextView) findViewById(R.id.tv_issue_address);
        tv_issue_demand_detail_address = (EditText) findViewById(R.id.tv_issue_demand_detail_address);

    }

    @Override
    protected void initExtraBundle() {
        super.initExtraBundle();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        setTitleForNavbar("预约表单");
        Intent intent = getIntent();
        item_num = intent.getIntExtra("item_num", -1);
        if (item_num == 0) {
            item_name = "";
        } else {
            item_name = ImageUrlUtils.getPackagesListNames()[item_num - 1];
        }

        acs_member_id = AdskApplication.getInstance().getMemberEntity().getAcs_member_id();
        getConsumerInfoData();
        setDecorationBudget();
        initAlertView();
    }

    @Override
    protected void initListener() {
        super.initListener();

        btn_send_demand.setOnClickListener(this);
        tv_issue_demand_budget.setOnClickListener(this);
        tv_issue_address.setOnClickListener(this);
        et_issue_demand_area.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String area = et_issue_demand_area.getText().toString().trim();
                    if (TextUtils.isEmpty(area)) {
                        area = "";
                    } else {
                        area = String.format("%.2f", Double.valueOf(area));
                    }
                    et_issue_demand_area.setText(area);
                }
            }
        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_issue_demand_budget: ///装修预算

                pvDecorationBudgetOptions.show();
                et_issue_demand_area.clearFocus();

                break;
            case R.id.tv_issue_address: ///地址：省 市 区 .

                getPCDAddress();
                et_issue_demand_area.clearFocus();

                break;

            case R.id.btn_send_demand: /// 提交 .

                sendPackageFormClick();

                break;


        }
    }


    //监听（提交套餐预约）
    private void sendPackageFormClick() {

        //姓名未作校验
        String demand_name = et_issue_demand_name.getText().toString().trim();

        // ----------------以下code by zjl------------------
        // 增加姓名校验
        boolean matches = demand_name.matches(RegexUtil.NICK_NAME_REGEX);
        if (!matches || TextUtils.isEmpty(demand_name)) {
            new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.check_name_tip),
                    null, null, new String[]{UIUtils.getString(R.string.sure)}, ReservationFormActivity.this, AlertView.Style.Alert, null).show();
            return;
        }
        // ----------------以上code by zjl------------------


        //手机号码校验
        String phone_num = et_issue_demand_mobile.getText().toString();
        Boolean ifOKPhoneNum = phone_num.matches(RegexUtil.PHONE_REGEX);
        if (TextUtils.isEmpty(phone_num) || !ifOKPhoneNum) {
            showAlertView(R.string.please_enter_correct_phone_number);
            return;
        }
        //项目面积校验
        String area_project = et_issue_demand_area.getText().toString();
        if (!TextUtils.isEmpty(area_project.trim())) {
            boolean ifOKArea = area_project.matches(RegexUtil.AREA_REGEX);
            String subNum = "0";
            if (area_project.contains(".")) {
                subNum = area_project.substring(0, area_project.indexOf("."));
            }
            if (TextUtils.isEmpty(area_project) || Float.valueOf(area_project) == 0) {
                showAlertView(R.string.please_input_correct_area);
                return;
            } else {
                if ((subNum.length() > 1 && subNum.startsWith("0")) || subNum.length() > 4) {
                    showAlertView(R.string.please_input_correct_area);
                    return;
                } else {
                    if (!area_project.matches("^[0-9]{1,4}+(.[0-9]{1,2})?$") || subNum.length() > 4) {
                        showAlertView(R.string.please_input_correct_area);
                        return;
                    }
                }
            }

        } else {
            area_project = "";
        }
        //装修预算校验
        if (TextUtils.isEmpty(mDecorationBudget)) {
            showAlertView(R.string.please_select_decorate_budget);
            return;
        }
        //项目地址校验
        if (TextUtils.isEmpty(mCurrentDistrictCode)) {
            showAlertView(R.string.please_choose_addresses);
            return;
        }
        //小区名称校验
        String detail_address = tv_issue_demand_detail_address.getText().toString().trim();
        if (!TextUtils.isEmpty(detail_address.trim())) {
            boolean regex_address_right = detail_address.matches(RegexUtil.ADDRESS_REGEX);
            if (TextUtils.isEmpty(detail_address) || !regex_address_right) {
                showAlertView(R.string.please_enter_correct_address);
                return;
            }
        } else {
            detail_address = "";
        }


        //提交的JSONObject
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(JsonConstants.JSON_PACKAGES_NAME, demand_name);///demand_name
            jsonObject.put(JsonConstants.JSON_PACKAGES_PHONE_NUM, phone_num);///phone_num
            jsonObject.put(JsonConstants.JSON_PACKAGES_PROVINCE, mCurrentProvinceCode);///mCurrentProvince
            jsonObject.put(JsonConstants.JSON_PACKAGES_PROVINCE_NAME, mCurrentProvince);///mCurrentProvince
            jsonObject.put(JsonConstants.JSON_PACKAGES_CITY, mCurrentCityCode);///mCurrentProvince
            jsonObject.put(JsonConstants.JSON_PACKAGES_CITY_NAME, mCurrentCity);///mCurrentProvince
            jsonObject.put(JsonConstants.JSON_PACKAGES_DISTRICT, mCurrentDistrictCode);///mCurrentProvince
            jsonObject.put(JsonConstants.JSON_PACKAGES_DISTRICT_NAME, mCurrentDistrict);///mCurrentProvince
            jsonObject.put(JsonConstants.JSON_PACKAGES_ADDRESS, detail_address);///mCurrentProvince
            jsonObject.put(JsonConstants.JSON_PACKAGES_PROJECT_AREA, area_project);///mCurrentProvince
            jsonObject.put(JsonConstants.JSON_PACKAGES_EXPENSE_BUDGET, mDecorationBudget);///mCurrentProvince
            jsonObject.put(JsonConstants.JSON_PACKAGES_PKG, item_num);///mCurrentProvince
            jsonObject.put(JsonConstants.JSON_PACKAGES_PKG_NAME, item_name);///mCurrentProvince
        } catch (JSONException e) {
            e.printStackTrace();
        }

        CustomProgress.show(ReservationFormActivity.this, "提交中...", false, null);
        sendPackageForm(jsonObject, acs_member_id);

    }

    //提交套餐预约
    private void sendPackageForm(JSONObject jsonObject, String customer_id) {

        MPServerHttpManager.getInstance().sendPackagesForm(jsonObject, customer_id, new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                ApiStatusUtil.getInstance().apiStatuError(volleyError, ReservationFormActivity.this);
                CustomProgress.cancelDialog();
            }

            @Override
            public void onResponse(JSONObject jsonObject) {
                success_ALert = new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.succes_package), null, null, new String[]{UIUtils.getString(R.string.sure)}, ReservationFormActivity.this, AlertView.Style.Alert, new OnItemClickListener() {
                    @Override
                    public void onItemClick(Object object, int position) {
                        ReservationFormActivity.this.finish();
                    }
                });

                success_ALert.show();
                CustomProgress.cancelDialog();
            }
        });
    }

    //把String数组转成List集合
    private List<String> filledData(String[] date) {
        List<String> mSortList = new ArrayList<String>();
        for (String str : date) {
            mSortList.add(str);
        }
        return mSortList;
    }

    //获取省市区地址
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

                        tv_issue_address.setText(province + " " + city + " " + area);
                        mChangeAddressDialog.dismiss();
                    }

                });
    }

    //设置装修预算
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
        pvDecorationBudgetOptions.setTitle(UIUtils.getString(R.string.demand_project_budget_title));
        pvDecorationBudgetOptions.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                mDecorationBudget = decorationBudgetItems.get(options1);
                tv_issue_demand_budget.setText(mDecorationBudget);
            }
        });
    }

    //提示框
    private void initAlertView() {
        mSendDesignRequirementSuccessAlertView = new AlertView(UIUtils.getString(R.string.send_design_requirement_save_success_alert_view), UIUtils.getString(R.string.send_design_requirement_success_alert_view_1), null, null, new String[]{UIUtils.getString(R.string.sure)}, this, AlertView.Style.Alert, this).setCancelable(false);
    }

    //打开AlertView对话框
    private void showAlertView(int content) {
        new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(content), null, null, new String[]{UIUtils.getString(R.string.sure)}, ReservationFormActivity.this, AlertView.Style.Alert, null).show();
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

    //是否隐藏View
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


    //获取个人基本信息
    public void getConsumerInfoData() {

        MemberEntity mMemberEntity = AdskApplication.getInstance().getMemberEntity();
        if (null == mMemberEntity) {
            AdskApplication.getInstance().doLogin(this);
            return;
        }

        String mNick_name = mMemberEntity.getNick_name();
        String mobile_number = mMemberEntity.getMobile_number();
        if (!TextUtils.isEmpty(mNick_name)) {
            et_issue_demand_name.setText(mNick_name);
        }
        if (!TextUtils.isEmpty(mobile_number)) {
            et_issue_demand_mobile.setText(mobile_number);
        }
    }

    @Override
    public void onItemClick(Object obj, int position) {
        if (obj == mSendDesignRequirementSuccessAlertView && position != AlertView.CANCELPOSITION) {
            finish();
        }
    }

    /// 控件.
    private TextView et_issue_demand_name;
    private TextView tv_issue_demand_budget;
    private TextView tv_issue_address;
    private EditText tv_issue_demand_detail_address;
    private EditText et_issue_demand_mobile;
    private EditText et_issue_demand_area;
    private Button btn_send_demand;
    private AlertView success_ALert;
    private AlertView mSendDesignRequirementSuccessAlertView;
    private AddressDialog mChangeAddressDialog;
    private OptionsPickerView pvDecorationBudgetOptions;

    /// 变量.
    private int item_num;
    private String acs_member_id;
    private String item_name;
    private String mCurrentProvince, mCurrentCity, mCurrentDistrict;
    private String mCurrentProvinceCode, mCurrentCityCode, mCurrentDistrictCode;
    private String mDecorationBudget;

}
