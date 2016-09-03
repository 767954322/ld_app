package com.autodesk.shejijia.consumer.personalcenter.designer.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.consumer.home.homepage.activity.MPConsumerHomeActivity;
import com.autodesk.shejijia.shared.components.common.appglobal.UrlMessagesContants;
import com.autodesk.shejijia.shared.components.im.activity.BaseChatRoomActivity;
import com.autodesk.shejijia.shared.components.im.activity.MPFileHotspotActivity;
import com.autodesk.shejijia.shared.framework.AdskApplication;
import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.consumer.manager.constants.JsonConstants;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.consumer.personalcenter.consumer.entity.ConsumerQrEntity;
import com.autodesk.shejijia.consumer.personalcenter.designer.entity.BeiShuMealEntity;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.im.activity.ChatRoomActivity;
import com.autodesk.shejijia.shared.framework.activity.NavigationBarActivity;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.MPNetworkUtils;
import com.autodesk.shejijia.shared.components.common.utility.RegexUtil;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.components.common.uielements.AddressDialog;
import com.autodesk.shejijia.shared.components.common.uielements.CustomProgress;
import com.autodesk.shejijia.shared.components.common.uielements.MyToast;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.AlertView;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.OnItemClickListener;
import com.socks.library.KLog;

import org.json.JSONException;
import org.json.JSONObject;

import de.greenrobot.event.EventBus;

/**
 * @author he.liu .
 * @version v1.0 .
 * @date 2016-6-8 .
 * @file BeiShuMealActivity.java .
 * @brief 扫码生成的北舒套餐表单 .
 */
public class BeiShuMealActivity extends NavigationBarActivity implements View.OnClickListener, OnItemClickListener {

    private String mHsUid;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_designer_beishu_meal;
    }

    @Override
    protected void initView() {
        super.initView();
        tv_consumer_name = (TextView) findViewById(R.id.tv_consumer_name);
        et_consumer_phone = (EditText) findViewById(R.id.et_consumer_phone);
        tv_consumer_address = (TextView) findViewById(R.id.tv_consumer_address);    /// 房屋地址 .
        et_consumer_detail_address = (EditText) findViewById(R.id.et_consumer_detail_address);  ///小区名称 .
        btn_consumer_finish = (Button) findViewById(R.id.btn_consumer_finish);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        EventBus.getDefault().registerSticky(this);
        setTitleForNavbar(UIUtils.getString(R.string.designer_beishu_meal_form));

        initAlertView();
    }

    @Override
    protected void initListener() {
        super.initListener();
        btn_consumer_finish.setOnClickListener(this);
        tv_consumer_address.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_consumer_finish: ///提交表单按钮 .
                if (!isSendState) {
                    return;
                }
                /**
                 * 在我的北舒套餐中显示.提示保存成功，进入聊天页面.
                 */
                name = tv_consumer_name.getText().toString();
                String phone = et_consumer_phone.getText().toString();
                community_name = et_consumer_detail_address.getText().toString();
                boolean phoneRight = phone.matches(RegexUtil.PHONE_REGEX);
                boolean address_right = community_name.matches(RegexUtil.ADDRESS_REGEX);
                if (TextUtils.isEmpty(phone) || !phoneRight) {
                    showAlertView(UIUtils.getString(R.string.please_enter_correct_phone_number));
                    return;
                }
                if (TextUtils.isEmpty(mCurrentProvince)) {
                    showAlertView(UIUtils.getString(R.string.please_input_correct_address));
                    return;
                }
                if (TextUtils.isEmpty(community_name) || !address_right) {
                    showAlertView(UIUtils.getString(R.string.please_enter_correct_address));
                    return;
                }
                JSONObject beiShuMealJson = new JSONObject();
                try {
                    beiShuMealJson.put(JsonConstants.JSON_SEND_DESIGN_REQUIREMENTS_CITY, mCurrentCityCode);
                    beiShuMealJson.put(JsonConstants.JSON_SEND_DESIGN_REQUIREMENTS_CITY_NAME, mCurrentCity);
                    beiShuMealJson.put(JsonConstants.JSON_SEND_DESIGN_REQUIREMENTS_COMMUNITY_NAME, community_name);
                    beiShuMealJson.put(JsonConstants.JSON_BEI_SHU_MEAL_CONSUMER_UID, mHsUid);
                    beiShuMealJson.put(JsonConstants.JSON_MODIFY_DESIGNER_REQUIREMENT_CONTACTS_MOBILE, phone);
                    beiShuMealJson.put(JsonConstants.JSON_MODIFY_DESIGNER_REQUIREMENT_CONTACTS_NAME, name);
                    beiShuMealJson.put(JsonConstants.JSON_SEND_DESIGN_REQUIREMENTS_DISTRICT, mCurrentDistrictCode);
                    beiShuMealJson.put(JsonConstants.JSON_SEND_DESIGN_REQUIREMENTS_DISTRICT_NAME, mCurrentDistrict);
                    beiShuMealJson.put(JsonConstants.JSON_SEND_DESIGN_REQUIREMENTS_PROVINCE, mCurrentProvinceCode);
                    beiShuMealJson.put(JsonConstants.JSON_SEND_DESIGN_REQUIREMENTS_PROVINCE_NAME, mCurrentProvince);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                CustomProgress.show(BeiShuMealActivity.this, UIUtils.getString(R.string.data_save), false, null);
                isSendState = false;
                sendBeiShuMealInfoData(beiShuMealJson);
                break;

            case R.id.tv_consumer_address: /// 设置地址 .
                getPCDAddress();
                break;
        }
    }

    @Override
    public void onItemClick(Object obj, int position) {
        if (obj == mAlertViewExt && position != AlertView.CANCELPOSITION) {
            MemberEntity memberEntity = AdskApplication.getInstance().getMemberEntity();
            if (memberEntity != null) {

                mMemberType = memberEntity.getMember_type();
                String beishu_thread_id = mBeiShuMealEntity.getBeishu_thread_id();
                String needs_id_chat = mBeiShuMealEntity.getNeeds_id() + "";
                String acs_member_id = memberEntity.getAcs_member_id();

                Intent intent = new Intent(BeiShuMealActivity.this, ChatRoomActivity.class);
                intent.putExtra(ChatRoomActivity.THREAD_ID, beishu_thread_id);
                intent.putExtra(ChatRoomActivity.ASSET_ID, needs_id_chat);
                intent.putExtra(ChatRoomActivity.RECIEVER_USER_ID, consumer_member_id);
                intent.putExtra(ChatRoomActivity.ACS_MEMBER_ID, acs_member_id);
                intent.putExtra(ChatRoomActivity.MEMBER_TYPE, mMemberType);
                intent.putExtra(ChatRoomActivity.MEDIA_TYPE, UrlMessagesContants.mediaIdProject);
                intent.putExtra(BaseChatRoomActivity.RECIEVER_USER_NAME, name);
                intent.putExtra("project_title",community_name);
                startActivity(intent);
                finish();
            }
        }
    }

    /**
     * 发送北舒套餐表单到服务器
     *
     * @param jsonObject 北舒表单内容
     */
    public void sendBeiShuMealInfoData(JSONObject jsonObject) {
        MPServerHttpManager.getInstance().sendBeiShuMealInfoData(consumer_member_id, jsonObject, new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                CustomProgress.cancelDialog();
                mAlertViewExt.show();
                jsonString = GsonUtil.jsonToString(jsonObject);
                mBeiShuMealEntity = GsonUtil.jsonToBean(jsonString, BeiShuMealEntity.class);
                KLog.d(TAG, jsonString);
                isSendState = true;
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                MPNetworkUtils.logError(TAG, volleyError);
                isSendState = true;
                MyToast.show(BeiShuMealActivity.this, UIUtils.getString(R.string.char_msg__file));
            }
        });
    }

    /**
     * 获取扫码后得到的数据
     *
     * @param consumerQrEntity
     */
    public void onEventMainThread(ConsumerQrEntity consumerQrEntity) {
        if (consumerQrEntity != null) {
            /**
             * avatar : http://uat415img.gdfcx.net:8082/img/570c7064ed50cc1782eb26f5.img
             * hs_uid : 2a12c0b1-f5a0-49f1-a3ed-f6954e9641f1
             * member_id : 20730177
             * member_type : member
             * mobile_number : 13718601763
             * name : hsing.lin
             */
            contacts_name = consumerQrEntity.getName();
            consumer_mobile = consumerQrEntity.getMobile_number();
            mConsumerAvatar = consumerQrEntity.getAvatar();
            consumer_member_id = consumerQrEntity.getMember_id();
            mHsUid = consumerQrEntity.getHs_uid();

            tv_consumer_name.setText(contacts_name);
            et_consumer_phone.setText(consumer_mobile);
        }
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
                    public void onClick(String province, String proviceCode, String city, String cityCode, String area, String areaCode) {
                        mCurrentProvince = province;
                        mCurrentProvinceCode = proviceCode;
                        mCurrentCity = city;
                        mCurrentCityCode = cityCode;
                        mCurrentDistrict = area;
                        mCurrentDistrictCode = areaCode;
                        tv_consumer_address.setText(province + city + area);
                        mChangeAddressDialog.dismiss();
                    }

                });
    }

    /**
     * 处理按返回键的操作
     */
    private void handleBackEvent() {
        Intent intent = new Intent(BeiShuMealActivity.this, MPConsumerHomeActivity.class);
        intent.putExtra(Constant.DesignerBeiShuMeal.SKIP_DESIGNER_PERSONAL_CENTER, 1);
        startActivity(intent);
    }


    //弹窗提醒
    private void initAlertView() {
        mAlertViewExt = new AlertView(UIUtils.getString(R.string.tip_success), UIUtils.getString(R.string.save_decoration_success), null, null, new String[]{UIUtils.getString(R.string.sure)}, BeiShuMealActivity.this, AlertView.Style.Alert, BeiShuMealActivity.this).setCancelable(false);
    }

    /**
     * 弹窗提醒
     *
     * @param string 弹窗内容
     */
    private void showAlertView(String string) {
        new AlertView(UIUtils.getString(R.string.tip), string, null, null, new String[]{UIUtils.getString(R.string.sure)}, BeiShuMealActivity.this, AlertView.Style.Alert, this).show();
    }

    /**
     * 处理手指滑动隐藏键盘
     *
     * @param ev
     * @return
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
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    /**
     * 处理触摸手势,是否隐藏键盘
     *
     * @param v     控件
     * @param event 触摸事件
     * @return 隐藏键盘与否
     */
    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
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
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            //按下的如果是BACK，同时没有重复
            handleBackEvent();
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 消毁Activity,并取消注册EventBus
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private TextView tv_consumer_name;
    private String  community_name;
    private TextView tv_consumer_address;
    private EditText et_consumer_phone, et_consumer_detail_address;
    private Button btn_consumer_finish;
    private AlertView mAlertViewExt;//窗口拓展例子
    private AddressDialog mChangeAddressDialog;

    public String mConsumerAvatar;
    private String name;
    public String jsonString;
    private boolean isSendState = true;
    private String contacts_name = "";
    private String consumer_mobile;
    //    private String member_id;
    private String consumer_member_id;
    private String mMemberType;
    private String mCurrentProvince, mCurrentCity, mCurrentDistrict;
    private String mCurrentProvinceCode, mCurrentCityCode, mCurrentDistrictCode;
    public BeiShuMealEntity mBeiShuMealEntity;
}

