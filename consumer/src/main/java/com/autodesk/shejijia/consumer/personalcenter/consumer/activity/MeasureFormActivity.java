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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.consumer.manager.constants.JsonConstants;
import com.autodesk.shejijia.consumer.personalcenter.consumer.entity.ConsumerEssentialInfoEntity;
import com.autodesk.shejijia.consumer.personalcenter.consumer.entity.DecorationNeedsListBean;
import com.autodesk.shejijia.consumer.personalcenter.workflow.entity.WkFlowDetailsBean;
import com.autodesk.shejijia.consumer.utils.AppJsonFileReader;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.uielements.AddressDialog;
import com.autodesk.shejijia.shared.components.common.uielements.CustomProgress;
import com.autodesk.shejijia.shared.components.common.uielements.TextViewContent;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.AlertView;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.OnItemClickListener;
import com.autodesk.shejijia.shared.components.common.uielements.reusewheel.listener.OnDismissListener;
import com.autodesk.shejijia.shared.components.common.uielements.reusewheel.utils.OptionsPickerView;
import com.autodesk.shejijia.shared.components.common.uielements.reusewheel.utils.TimePickerView;
import com.autodesk.shejijia.shared.components.common.utility.ConvertUtils;
import com.autodesk.shejijia.shared.components.common.utility.DateUtil;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.MPNetworkUtils;
import com.autodesk.shejijia.shared.components.common.utility.RegexUtil;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.framework.AdskApplication;
import com.autodesk.shejijia.shared.framework.activity.NavigationBarActivity;
import com.socks.library.KLog;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author DongXueQiu .
 * @version 1.0 .
 * @date 16-6-7 上午10:42
 * @file MeasureFormActivity.java  .
 * @brief 量房表单页面 .
 */

public class MeasureFormActivity extends NavigationBarActivity implements View.OnClickListener, OnDismissListener, OnItemClickListener {

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
        ll_style = (LinearLayout) findViewById(R.id.ll_measure_form_style);
        ll_type = (LinearLayout) findViewById(R.id.ll_measure_form_type);
        btn_send_form = (Button) findViewById(R.id.btn_send_measure_house_form);
        tvc_measure_form_type = (TextView) findViewById(R.id.tvc_measure_form_type);
        tvc_measure_form_style = (TextView) findViewById(R.id.tvc_measure_form_style);
        tvIllustrate = (TextView) findViewById(R.id.tvIllustrate);
        ll_time_restrict = (LinearLayout) findViewById(R.id.ll_time_restrict);


        tvc_area.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String area = tvc_area.getText().toString().trim();
                    if (TextUtils.isEmpty(area)) {
                        area = "0";
                    }
                    area = String.format("%.2f", Double.valueOf(area));
                    houseArea = area;
                    tvc_area.setText(area);
                }
            }
        });
    }

    @Override
    protected void initExtraBundle() {
        super.initExtraBundle();
        if (iselite) {
            getExtraData();
            return;
        }
        if (first) {
            first = false;
            Bundle extras = getIntent().getExtras();
            flow_state = (int) extras.get(Constant.SeekDesignerDetailKey.FLOW_STATE);
            needs_id = (String) extras.get(Constant.SeekDesignerDetailKey.NEEDS_ID);
            designer_id = (String) extras.get(Constant.SeekDesignerDetailKey.DESIGNER_ID);
            hs_uid = (String) extras.get(Constant.SeekDesignerDetailKey.HS_UID);
            mFree = (String) extras.get(Constant.SeekDesignerDetailKey.MEASURE_FREE);
            String styleAll = (String) extras.get(Constant.SeekDesignerDetailKey.DESIGNER_STYLE_ALL);
            mThread_id = (String) extras.get(Constant.ProjectMaterialKey.IM_TO_FLOW_THREAD_ID);
            // String styleAll = getIntent().getStringExtra(Constant.SeekDesignerDetailKey.DESIGNER_STYLE_ALL);
            styles = new ArrayList<String>();
            if (styleAll != null && styleAll.length() > 0) {
                String Style[] = styleAll.split("，");
                for (int i = 0; i < Style.length; i++) {
                    String styleItem = Style[i];
                    styles.add(styleItem);
                }
            }

        }
    }

    //获取精选装修模式传递过来的数据
    private void getExtraData() {
        Intent intent = getIntent();
        decorationNeedsListBean = (DecorationNeedsListBean) intent.getSerializableExtra(Constant.ConsumerDecorationFragment.DECORATIONbIDDERBEAN);
        designer_id = intent.getStringExtra(Constant.SeekDesignerDetailKey.DESIGNER_ID);
        iselite = intent.getBooleanExtra(Constant.SixProductsFragmentKey.ISELITE, false);

    }


    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        setTitleForNavbar(UIUtils.getString(R.string.demand_measure_house_form));
        if (iselite) {
            return;
        }
        if (flow_state == 1) {
            getRealNameAuditStatus(designer_id, hs_uid);
        }

        tvc_time.setFocusable(false);
//      showState(needs_id, designer_id);
        getUserId();
        getConsumerInfoData(memberId);
        /// designBudget .
        setDesignBudget();
        /// decoration .
        setDecorationBudget();
        ///  house type .
        setHouseType();
        /// roomItems.
        setRoomType();
        /// time .
        setMeasureTime();
        /// styleItems.
        setStyleType();
    }


    /**
     * 是否进行了实名认证
     *
     * @param designer_id
     * @param hs_uid
     */
    public void getRealNameAuditStatus(String designer_id, String hs_uid) {
        MPServerHttpManager.getInstance().getSeekDesignerDetailHomeData(designer_id, hs_uid, new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onResponse(JSONObject jsonObject) {

                try {
                    JSONObject jsonObject1 = jsonObject.getJSONObject("designer");
                    int is_real_name = jsonObject1.getInt("is_real_name");
                    if (2 != is_real_name) {
                        if (MeasureFormActivity.this != null) {
                            showAlertView(UIUtils.getString(R.string.desiner_not_real_name_authentication));
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                MPNetworkUtils.logError(TAG, volleyError);
                if (MeasureFormActivity.this != null) {
                    showAlertView(UIUtils.getString(R.string.desiner_not_real_name_authentication));
                }
            }
        });
    }

    private void getUserId() {
        MemberEntity memberEntity = AdskApplication.getInstance().getMemberEntity();
        if (memberEntity != null) {
            user_id = memberEntity.getAcs_member_id();
            memberId = memberEntity.getAcs_member_id();
        }

        String has_yet_to_fill_out = UIUtils.getString(R.string.has_yet_to_fill_out);
        if (has_yet_to_fill_out.equals(mFree)) {
            tv_measure_fee.setText("0.00");
            mFree = "0";
        } else {
            tv_measure_fee.setText(mFree);
        }
    }


    @Override
    protected void initListener() {
        super.initListener();
        tvc_project_budget.setOnClickListener(this);
        tvc_fitment_budget.setOnClickListener(this);
        tvc_measure_form_type.setOnClickListener(this);
        tvc_house_type.setOnClickListener(this);
        tvc_measure_form_style.setOnClickListener(this);
        tvc_address.setOnClickListener(this);
        tvc_time.setOnClickListener(this);
        btn_send_form.setOnClickListener(this);
        tvIllustrate.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_send_measure_house_form:
                name = tvc_name.getText().toString().trim();
                mobileNumber = tvc_phone.getText().toString().trim();
                communityName = tvc_estate.getText().toString().trim();

                boolean bName = name.matches("^[^ ]+[\\s\\S]*[^ ]+$"); /// 中间可以有空格 .
                boolean bMobile = mobileNumber.matches(RegexUtil.PHONE_REGEX);
                checkMeasureArea(houseArea);

                boolean bAddress = communityName.matches(RegexUtil.ADDRESS_REGEX);

                if (name.length() < 2 || name.length() > 20 || !bName) {

                    getErrorHintAlertView(UIUtils.getString(R.string.please_fill_your_name));

                    return;
                }

                if (!bMobile || mobileNumber.isEmpty()) {

                    getErrorHintAlertView(UIUtils.getString(R.string.please_fill_phone_number));
                    return;
                }

                if (housingType == null) {
                    getErrorHintAlertView(UIUtils.getString(R.string.demand_please_project_types));
                    return;
                }

                if (TextUtils.isEmpty(houseArea)) {
                    houseArea = "0";
                }
                houseArea = String.format("%.2f", Double.valueOf(houseArea));
                if (Double.valueOf(houseArea) < 1 || Double.valueOf(houseArea) > 9999) {
                    getErrorHintAlertView(UIUtils.getString(R.string.alert_msg_area));
                    return;
                }

                tvc_area.setText(houseArea);
                String subNum = "0";
                if (houseArea.contains(".")) {
                    subNum = houseArea.substring(0, houseArea.indexOf("."));
                }
                if (TextUtils.isEmpty(houseArea) || Float.valueOf(houseArea) == 0) {
                    getErrorHintAlertView(UIUtils.getString(R.string.please_input_correct_area));
                    return;
                } else {
                    if ((subNum.length() > 1 && subNum.startsWith("0")) || subNum.length() > 4) {
                        getErrorHintAlertView(UIUtils.getString(R.string.please_input_correct_area));
                        return;
                    } else {
                        if (!houseArea.matches("^[0-9]{1,4}+(.[0-9]{1,2})?$") || subNum.length() > 4) {
                            getErrorHintAlertView(UIUtils.getString(R.string.please_input_correct_area));
                            return;
                        }
                    }
                }


                if (designBudget == null) {
                    getErrorHintAlertView(UIUtils.getString(R.string.please_select_design_budget));
                    return;
                }

                if (decorateBudget == null) {
                    getErrorHintAlertView(UIUtils.getString(R.string.please_select_decorate_budget));
                    return;
                }

                if (mRoom == null || mHall == null || mToilet == null) {
                    getErrorHintAlertView(UIUtils.getString(R.string.please_select_form));
                    return;
                }

                if (style == null) {
                    getErrorHintAlertView(UIUtils.getString(R.string.please_select_style));
                    return;
                }

                if (mCurrentProvince == null || mCurrentCity == null || mCurrentDistrict == null) {
                    getErrorHintAlertView(UIUtils.getString(R.string.please_select_addresses));
                    return;
                }

                if (!bAddress || communityName.isEmpty()) {
                    getErrorHintAlertView(UIUtils.getString(R.string.please_fill_detailed_address));
                    return;
                }

                if (currentData == null) {

                    getErrorHintAlertView(UIUtils.getString(R.string.please_select_quantity_room_time));
                    return;
                }

                if (TextUtils.isEmpty(mFree)) {

                    getErrorHintAlertView(UIUtils.getString(R.string.volume_rate_cannot_empty));
                    return;
                }

                /**
                 * 获取系统当前时间,通过SimpleDateFormat获取24小时制时间
                 *
                 */
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy年 MM月 dd日 HH点", Locale.getDefault());
                String date = /*currentTime += */sdf.format(new Date());

//                try {
//                    if (formatDate(date, currentData)) {
                CustomProgress.show(MeasureFormActivity.this, UIUtils.getString(R.string.data_send), false, null);

//                houseArea = tvc_area.getText().toString().trim();
                JSONObject jsonObject = new JSONObject();
                try {

                    jsonObject.put(JsonConstants.JSON_MEASURE_FORM_AMOUNT, mFree);
                    jsonObject.put(JsonConstants.JSON_MEASURE_FORM_CHANNEL_TYPE, "IOS");
                    jsonObject.put(JsonConstants.JSON_MEASURE_FORM_CITY, mCurrentCityCode);
                    jsonObject.put(JsonConstants.JSON_MEASURE_FORM_CITY__NAME, mCurrentCity);
                    jsonObject.put(JsonConstants.JSON_MEASURE_FORM_COMMUNITY_NAME, communityName);
                    jsonObject.put(JsonConstants.JSON_MEASURE_FORM_CONTACTS_MOBILE, mobileNumber);
                    jsonObject.put(JsonConstants.JSON_MEASURE_FORM_CONTACTS_NAME, name);
                    jsonObject.put(JsonConstants.JSON_MEASURE_FORM_DECORATION_BUDGET, decorateBudget);
                    jsonObject.put(JsonConstants.JSON_MEASURE_FORM_DECORATION_STYLE, style);
                    jsonObject.put(JsonConstants.JSON_MEASURE_FORM_DESIGN_BUDGET, designBudget);
                    jsonObject.put(JsonConstants.JSON_MEASURE_FORM_DESIGNER_ID, designer_id);
                    jsonObject.put(JsonConstants.JSON_MEASURE_FORM_DISTRICT, mCurrentDistrictCode);
                    jsonObject.put(JsonConstants.JSON_MEASURE_FORM_DISTRICT_NAME, mCurrentDistrict);
                    jsonObject.put(JsonConstants.JSON_MEASURE_FORM_HOUSE_AREA, houseArea);
                    jsonObject.put(JsonConstants.JSON_MEASURE_FORM_HOUSE_TYPE, housType);
                    jsonObject.put(JsonConstants.JSON_MEASURE_FORM_LIVING_ROOM, hall);
                    jsonObject.put(JsonConstants.JSON_MEASURE_FORM_HS_UID, hs_uid);
                    jsonObject.put(JsonConstants.JSON_MEASURE_FORM_ORDER_TYPE, 0);
                    jsonObject.put(JsonConstants.JSON_MEASURE_FORM_PROVINCE, mCurrentProvinceCode);
                    jsonObject.put(JsonConstants.JSON_MEASURE_FORM_PROVINCE_NAME, mCurrentProvince);
                    jsonObject.put(JsonConstants.JSON_MEASURE_FORM_ROOM, room);
                    jsonObject.put(JsonConstants.JSON_MEASURE_FORM_SERVICE_DATE, currentData);
                    jsonObject.put(JsonConstants.JSON_MEASURE_FORM_TOILET, toilet);
                    jsonObject.put(JsonConstants.JSON_MEASURE_FORM_USER_ID, user_id);
                    if (null == mThread_id || "".equals(mThread_id)) {
                        jsonObject.put(JsonConstants.JSON_MEASURE_FORM_THREAD_ID, ""); /// 聊天室ID，目前还没有做，先填写的是null
                    } else {
                        jsonObject.put(JsonConstants.JSON_MEASURE_FORM_THREAD_ID, mThread_id);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                postSendMeasureForm(jsonObject);
//                    } else {
//                        new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.amount_of_time_than_current_time_one_hour), null, null, new String[]{UIUtils.getString(R.string.sure)}, MeasureFormActivity.this, AlertView.Style.Alert, null).show();
//                    }
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
                break;
            case R.id.tvc_measure_form_project_budget:
                pvDesignBudgetOptions.show();
                break;
            case R.id.tvc_measure_fitment_budget:
                pvDecorationBudgetOptions.show();
                break;
            case R.id.tvc_measure_form_type:
                pvHouseTypeOptions.show();
                break;
            case R.id.tvc_measure_form_house_type:
                pvRoomTypeOptions.show();
                break;
            case R.id.tvc_measure_form_style:
                pvStyleOptions.show();
                break;
            case R.id.tvc_measure_form_time:
                pvTime.show();
                break;
            case R.id.tvc_measure_form_address:
                getPCDAddress();

                break;
            case R.id.tvIllustrate:
                new AlertView(UIUtils.getString(R.string.illustrate), UIUtils.getString(R.string.warm_tips_content), null, null, new String[]{UIUtils.getString(R.string.finish_cur_pager)}, MeasureFormActivity.this,
                        AlertView.Style.Alert, null).show();
                break;
            default:
                break;
        }
    }

    private boolean checkMeasureArea(String area) {
        if(TextUtils.isEmpty(area)){
            return false;
        }else {
            String[] split = area.split("\\.");
            if (null != split) {

                if (split.length == 1) {
                    if (split[0].length() <= 4) {
                        if (area.matches(RegexUtil.AREA_REGEX_ZERO)) {
                            return true;
                        }
                    }
                }
                if (split.length == 2) {
                    if (split[0].length() <= 4 && split[1].length() <= 2) {

                        if (area.matches(RegexUtil.AREA_REGEX_ZERO)) {

                            return true;
                        }
                    }
                }
            }
            return false;
        }
    }


    @Override
    protected int getRightButtonImageResourceId() {
        //default set in XML
        return R.drawable.icon_time_normal;
    }

    @Override
    protected void rightNavButtonClicked(View view) {
        Intent intent = new Intent(MeasureFormActivity.this, ExistMeasureOrderActivity.class);
        intent.putExtra(Constant.ConsumerMeasureFormKey.DESIGNER_ID, designer_id);
        intent.putExtra(Constant.ConsumerMeasureFormKey.HS_UID, hs_uid);
        intent.putExtra(Constant.ConsumerMeasureFormKey.MEASURE, mFree);
        startActivity(intent);
        finish();
    }

    /**
     * 把String数组转成List集合
     */
    private List<String> filledData(String[] date) {
        List<String> mSortList = new ArrayList<>();
        for (String str : date) {
            mSortList.add(str);
        }
        return mSortList;
    }

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
                currentData = DateUtil.dateFormat(currentData, "yyyy-MM-dd HH:mm:ss", "yyyy年MM月dd日 HH点");
                tvc_time.setText(currentData);
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


    /**
     * @brief 设置设计费
     */
    private void setDesignBudget() {
        List<String> design_budgets = filledData(getResources().getStringArray(R.array.design_budget));
        final ArrayList<String> designBudgetItems = new ArrayList<>();

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
                designBudget = designBudgetItems.get(options1);
                tvc_project_budget.setText(designBudget);
            }
        });
    }

    /**
     * @brief 设置量房费
     */
    private void setDecorationBudget() {
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
                decorateBudget = decorationBudgetItems.get(options1);
                tvc_fitment_budget.setText(decorateBudget);
            }
        });
    }

    /**
     * @brief 设置房屋类型
     */
    private void setHouseType() {
        List<String> houseType = filledData(getResources().getStringArray(R.array.hType));
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
                housingType = houseTypeItems.get(options1);
                tvc_measure_form_type.setText(housingType);

                Map<String, String> space = AppJsonFileReader.getSpace(MeasureFormActivity.this); // 转换成英文
                housType = ConvertUtils.getKeyByValue(space, housingType);
            }
        });
    }

    /**
     * @brief 设置室 厅 卫
     */
    private void setRoomType() {
        List<String> rooms = filledData(getResources().getStringArray(R.array.mlivingroom));
        final List<String> halls = filledData(getResources().getStringArray(R.array.hall));
        List<String> toilets = filledData(getResources().getStringArray(R.array.toilet));
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
        for (int i = 0; i < rooms.size(); i++) {
            hallsList.add(options2Items_01);
        }
        //toilet
        ArrayList<ArrayList<String>> options3Items_01 = new ArrayList<>();
        ArrayList<String> options3Items_01_01 = new ArrayList<>();
        for (String op3 : toilets) {
            options3Items_01_01.add(op3);
        }
        for (int i = 0; i < halls.size(); i++) {
            options3Items_01.add(options3Items_01_01);
        }
        for (int i = 0; i < rooms.size(); i++) {
            toiletsList.add(options3Items_01);
        }

        pvRoomTypeOptions.setPicker(roomsList, hallsList, toiletsList, true);
        pvRoomTypeOptions.setCyclic(false, false, false);
        pvRoomTypeOptions.setSelectOptions(0, 0, 0);
        pvRoomTypeOptions.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                livingType = roomsList.get(options1)
                        + hallsList.get(options1).get(option2)
                        + toiletsList.get(options1).get(option2).get(options3);
                mRoom = roomsList.get(options1);
                mHall = hallsList.get(options1).get(option2);
                mToilet = toiletsList.get(options1).get(option2).get(options3);

                Map<String, String> roomHall = AppJsonFileReader.getRoomHall(MeasureFormActivity.this); // 转换成英文
                Map<String, String> livingRoom = AppJsonFileReader.getLivingRoom(MeasureFormActivity.this); // 转换成英文
                Map<String, String> toiletMap = AppJsonFileReader.getToilet(MeasureFormActivity.this); // 转换成英文
                room = ConvertUtils.getKeyByValue(roomHall, mRoom);
                hall = ConvertUtils.getKeyByValue(livingRoom, mHall);
                toilet = ConvertUtils.getKeyByValue(toiletMap, mToilet);

                tvc_house_type.setText(livingType);
            }
        });
    }

    /**
     * @brief 风格
     */
    private void setStyleType() {
        final ArrayList<String> styleItems = new ArrayList<>();
//
//        if (styles != null) {
//
//
//        } else {

        styles = filledData(getResources().getStringArray(R.array.style));

//        }
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
                tvc_measure_form_style.setText(style);
                Map<String, String> space = AppJsonFileReader.getStyle(MeasureFormActivity.this);
                style = ConvertUtils.getKeyByValue(space, style);

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
                        // 由于有些地区没有区这个字段，将含有区域得字段name改为none，code改为0

                        mCurrentDistrict = district;
                        mCurrentDistrictCode = areaCode;

                        district = UIUtils.getNoStringIfEmpty(mCurrentDistrict);

                        address = province + " " + city + " " + district;
                        tvc_address.setText(address);
                        mChangeAddressDialog.dismiss();
                    }

                });
    }

    /**
     * @param beforeDate
     * @param afterDate
     * @brief yyyy-MM-dd HH:mm:ss格式转化成毫秒数(long)进行判断 .
     */
    public static boolean formatDate(String beforeDate, String afterDate) throws ParseException {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy年 MM月 dd日 HH点");
        Date d1 = sf.parse(beforeDate);
        Date d2 = sf.parse(afterDate);
        long stamp = d2.getTime() - d1.getTime();
        return stamp >= (3600 * 1000);
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

    @Override
    public void onDismiss(Object o) {
        finish();
    }


    /**
     * 获取个人基本信息
     *
     * @param member_id
     */
    public void getConsumerInfoData(String member_id) {
        MPServerHttpManager.getInstance().getConsumerInfoData(member_id, new OkJsonRequest.OKResponseCallback() {

            @Override
            public void onResponse(JSONObject jsonObject) {
                String jsonString = GsonUtil.jsonToString(jsonObject);
                mConsumerEssentialInfoEntity = GsonUtil.jsonToBean(jsonString, ConsumerEssentialInfoEntity.class);
                nick_name = mConsumerEssentialInfoEntity.getNick_name();
                tvc_name.setText(nick_name);//设置消费者姓名
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
            }
        });
    }

    /**
     * 提交量房数据
     *
     * @param jsonObject
     */
    public void postSendMeasureForm(JSONObject jsonObject) {
        OkJsonRequest.OKResponseCallback okResponseCallback = new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                KLog.d(TAG, jsonObject);
                new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString((R.string.consume_send_success)), null, null, new String[]{UIUtils.getString(R.string.sure)}, MeasureFormActivity.this,
                        AlertView.Style.Alert, MeasureFormActivity.this).show();
                CustomProgress.cancelDialog();
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                MPNetworkUtils.logError(TAG, volleyError);
                showAlertView(UIUtils.getString(R.string.choose_ta_room_fail));

                CustomProgress.dialog.cancel();
            }
        };
        MPServerHttpManager.getInstance().agreeOneselfResponseBid(jsonObject, okResponseCallback);
    }

    private void getErrorHintAlertView(String content) {
        if (error_AlertView != null) {
            error_AlertView = null;
        }
        error_AlertView = new AlertView(UIUtils.getString(R.string.tip), content, null, null, new String[]{UIUtils.getString(R.string.chatroom_audio_recording_erroralert_ok)}, MeasureFormActivity.this,
                AlertView.Style.Alert, null);
        error_AlertView.show();

    }

    private void showAlertView(String content) {
        new AlertView(UIUtils.getString(R.string.tip), content, null, new String[]{UIUtils.getString(R.string.sure)}, null, MeasureFormActivity.this,
                AlertView.Style.Alert, MeasureFormActivity.this).show();
    }

    @Override
    public void onItemClick(Object object, int position) {
//        if (object == mStopDemandAlertView && position != AlertView.CANCELPOSITION) {
//            CustomProgress.show(this, "", false, null);
//            sendStopDemand(needs_id, 1);
//        }
        if (object != error_AlertView) {
            MeasureFormActivity.this.finish();
        }

    }

    private AlertView error_AlertView;
    ///控件.
    private LinearLayout ll_time_restrict;
    private LinearLayout ll_type;
    private LinearLayout ll_style;
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
    private OptionsPickerView pvDesignBudgetOptions;
    private OptionsPickerView pvDecorationBudgetOptions;
    private OptionsPickerView pvStyleOptions;
    private OptionsPickerView pvHouseTypeOptions;
    private OptionsPickerView pvRoomTypeOptions;
    private TimePickerView pvTime;
    private AddressDialog mChangeAddressDialog;

    /// 变量.
    private int flow_state;
    private boolean first = true;
    private String designer_id;
    private String mThread_id;
    private String hs_uid;
    private String mFree;
    private String needs_id;
    private String name;
    private String mobileNumber;
    private String designBudget;
    private String decorateBudget;
    private String housingType;
    private String houseArea;
    private String livingType;
    private String style;
    private String communityName;
    private String nick_name;
    private String user_id;
    private String currentData;
    private String housType;
    private String mRoom, mHall, mToilet;
    private String room, hall, toilet;
    private String mCurrentProvince, mCurrentCity, mCurrentDistrict;
    private String mCurrentProvinceCode, mCurrentCityCode, mCurrentDistrictCode;
    private String memberId;
    private boolean iselite;
    private String address;

    ///　集合，类.
    private ArrayList<String> decorationBudgetItems = new ArrayList<>();
    private ArrayList<String> houseTypeItems = new ArrayList<>();
    private ArrayList<String> roomsList = new ArrayList<>();
    private ArrayList<ArrayList<String>> hallsList = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> toiletsList = new ArrayList<>();
    private List<String> styles;
    private WkFlowDetailsBean wkFlowDetailsBean;
    private ConsumerEssentialInfoEntity mConsumerEssentialInfoEntity;
    private DecorationNeedsListBean decorationNeedsListBean;

}
