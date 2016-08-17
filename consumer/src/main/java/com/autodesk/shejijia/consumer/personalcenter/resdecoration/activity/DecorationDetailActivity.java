package com.autodesk.shejijia.consumer.personalcenter.resdecoration.activity;

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
import com.autodesk.shejijia.consumer.personalcenter.consumer.activity.AmendDemandActivity;
import com.autodesk.shejijia.consumer.personalcenter.resdecoration.entity.DecorationBiddersBean;
import com.autodesk.shejijia.consumer.personalcenter.resdecoration.entity.DecorationDetailBean;
import com.autodesk.shejijia.consumer.utils.AppJsonFileReader;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.uielements.CustomProgress;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.AlertView;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.OnItemClickListener;
import com.autodesk.shejijia.shared.components.common.uielements.reusewheel.utils.OptionsPickerView;
import com.autodesk.shejijia.shared.components.common.utility.ConvertUtils;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.MPNetworkUtils;
import com.autodesk.shejijia.shared.components.common.utility.StringUtils;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.framework.activity.NavigationBarActivity;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;

/**
 * @author Malidong .
 * @version 1.0 .
 * @date 16-6-7 上午9:15
 * @file AmendDemandActivity.java  .
 * @brief 修改需求.
 */
public class DecorationDetailActivity extends NavigationBarActivity implements View.OnClickListener, OnItemClickListener {

    private static final String IS_PUBLIC = "is_public";
    private TextView mTvNeedsId;
    private TextView mTvDecorationName;
    private LinearLayout mLlDemandModify;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_decoration_detail;
    }

    @Override
    protected void initView() {
        super.initView();
        etAmendAmendName = (TextView) findViewById(R.id.et_amend_amend_name);
        etIssueAmendMobile = (TextView) findViewById(R.id.et_issue_amend_mobile);
        tvAmendDesignBudget = (TextView) findViewById(R.id.tv_amend_design_budget);
        tvAmendBudget = (TextView) findViewById(R.id.tv_aemand_budget);
        tvAmendHouseType = (TextView) findViewById(R.id.tv_amend_house_type);
        etAmendArea = (TextView) findViewById(R.id.et_ademand_area);
        tvAmendRoomType = (TextView) findViewById(R.id.tv_amend_room_type);
        tvAmendStyle = (TextView) findViewById(R.id.tv_amend_style);
        tvIssueAddress = (TextView) findViewById(R.id.tv_issue_address);
        etIssueDemandDetailAddress = (TextView) findViewById(R.id.et_issue_demand_detail_address);
        tvPublicTime = (TextView) findViewById(R.id.tv_public_time);
        btnFitmentAmendDemand = (Button) findViewById(R.id.btn_fitment_amend_demand);
        btnFitmentStopDemand = (Button) findViewById(R.id.btn_fitment_stop_demand);
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
        btnFitmentStopDemand.setOnClickListener(this);
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
                CustomProgress.dialog.cancel();

                String info = GsonUtil.jsonToString(jsonObject);
                demandDetailBean = GsonUtil.jsonToBean(info, DecorationDetailBean.class);
                updateViewFromData(demandDetailBean);
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                MPNetworkUtils.logError(TAG, volleyError);
                CustomProgress.dialog.cancel();
                new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.network_error), null, new String[]{"确定"}, null, DecorationDetailActivity.this,
                        AlertView.Style.Alert, null).show();
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
                    is_public_amend = jsonObject.getString(IS_PUBLIC);
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
     * 　网络获取数据，更新页面
     */
    private void updateViewFromData(DecorationDetailBean demandDetailBean) {
        String contacts_name = demandDetailBean.getContacts_name();
        String district_name = demandDetailBean.getDistrict_name();
        String publish_time = demandDetailBean.getPublish_time();
        String community_name = demandDetailBean.getCommunity_name();
        house_type = demandDetailBean.getHouse_type();
        room = demandDetailBean.getRoom();
        toilet = demandDetailBean.getToilet();
        living_room = demandDetailBean.getLiving_room();
        house_area = demandDetailBean.getHouse_area();
        contacts_mobile = demandDetailBean.getContacts_mobile();
        custom_string_status = demandDetailBean.getCustom_string_status();
        decoration_budget = demandDetailBean.getDecoration_budget();
        province_name = demandDetailBean.getProvince_name();
        city_name = demandDetailBean.getCity_name();
        district = demandDetailBean.getDistrict();
        design_budget = demandDetailBean.getDesign_budget();

        /**
         * 当is_public为1,表示当前需求已经终止，隐藏修改终止需求按钮
         */
        String is_public = demandDetailBean.getIs_public();
        if (Constant.NumKey.ONE.equals(is_public)) {
            mLlDemandModify.setVisibility(View.GONE);
        } else {
            mLlDemandModify.setVisibility(View.VISIBLE);
        }

        if (custom_string_status.equals(Constant.NumKey.THREE)
                || custom_string_status.equals(Constant.NumKey.ZERO_THREE)) {
            btnFitmentAmendDemand.setClickable(false);
            btnFitmentAmendDemand.setPressed(false);
            btnFitmentAmendDemand.setBackgroundColor(UIUtils.getColor(R.color.font_gray));
        }


        district_name = TextUtils.isEmpty(district) || "none".equals(district) || TextUtils.isEmpty(district_name) || district_name.equals("none") ? "" : district_name;
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

        mTvDecorationName.setText(contacts_name + "/" + community_name);

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
            if (StringUtils.isNumeric(wk_cur_sub_node_id) && Integer.valueOf(wk_cur_sub_node_id) > 33) {
                btnFitmentStopDemand.setClickable(false);
                btnFitmentStopDemand.setPressed(false);
                btnFitmentStopDemand.setTextColor(UIUtils.getColor(R.color.white));
                btnFitmentStopDemand.setBackgroundColor(UIUtils.getColor(R.color.font_gray));
            }
        }
    }

    /**
     * 设置表单的写入逻辑
     */
    private void setFormViewData() {
        mTvNeedsId.setText(needs_id);

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
     * 终止需求提示
     */
    @Override
    public void onItemClick(Object obj, int position) {

        if (obj == mStopDemandAlertView && position != AlertView.CANCELPOSITION) {
            CustomProgress.show(this, "", false, null);
            sendStopDemand(needs_id, 1);
        }

        if (obj == mStopDemandSuccessAlertView && position != AlertView.CANCELPOSITION) {
            if (!TextUtils.isEmpty(is_public_amend)) {
                EventBus.getDefault().postSticky(is_public_amend);
            }
            finish();
        }
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
                Map<String, String> roomHall = AppJsonFileReader.getRoomHall(DecorationDetailActivity.this);
                Map<String, String> livingRoom = AppJsonFileReader.getLivingRoom(DecorationDetailActivity.this);
                Map<String, String> toiletMap = AppJsonFileReader.getToilet(DecorationDetailActivity.this);

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
                Map<String, String> space = AppJsonFileReader.getStyle(DecorationDetailActivity.this);
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
                Map<String, String> space = AppJsonFileReader.getSpace(DecorationDetailActivity.this);
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
        styleJson = AppJsonFileReader.getStyle(DecorationDetailActivity.this);
        spaceJson = AppJsonFileReader.getSpace(DecorationDetailActivity.this);
        livingRoomJson = AppJsonFileReader.getLivingRoom(DecorationDetailActivity.this);
        roomJson = AppJsonFileReader.getRoomHall(DecorationDetailActivity.this);
        toiletJson = AppJsonFileReader.getToilet(DecorationDetailActivity.this);
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
    private TextView etIssueAmendMobile, etAmendArea;
    private TextView tvAmendRoomType, tvAmendStyle, tvIssueAddress, tvPublicTime;
    private Button btnFitmentAmendDemand, btnFitmentStopDemand;
    private TextView etIssueDemandDetailAddress;
    private OptionsPickerView pvDesignBudgetOptions;
    private OptionsPickerView pvDecorationBudgetOptions;
    private OptionsPickerView pvStyleOptions;
    private OptionsPickerView pvRoomTypeOptions; /// 选择器 .
    private OptionsPickerView pvHouseTypeOptions;
    private AlertView mStopDemandAlertView;
    private AlertView mStopDemandSuccessAlertView;

    ///　变量.
    private String district;
    private String province_name, city_name;
    private String living_room, room, toilet;
    private String house_type, house_area, needs_id;
    private String contacts_mobile;
    private String decoration_budget, design_budget, custom_string_status;
    private DecorationDetailBean demandDetailBean;
    private Map<String, String> styleJson, spaceJson, livingRoomJson, roomJson, toiletJson;
    private String decoration_style_convert, house_type_convert, living_room_convert;
    private String room_convert, toilet_convert;
    private String style;
    private String is_public_amend = "0";

}