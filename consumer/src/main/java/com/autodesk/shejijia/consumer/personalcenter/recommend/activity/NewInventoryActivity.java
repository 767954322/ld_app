package com.autodesk.shejijia.consumer.personalcenter.recommend.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.MemberAccountEntity;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.NewInventoryEntity;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.SelectProjectEntity;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.uielements.AddressDialog;
import com.autodesk.shejijia.shared.components.common.uielements.CustomProgress;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.AlertView;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.OnItemClickListener;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.RegexUtil;
import com.autodesk.shejijia.shared.components.common.utility.StringUtils;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.framework.AdskApplication;
import com.autodesk.shejijia.shared.framework.activity.NavigationBarActivity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author DongXueQiu .
 * @version 1.0 .
 * @date 16-10-23
 * @file NewInventoryActivity.java  .
 * @brief 新建清单.
 */

public class NewInventoryActivity extends NavigationBarActivity implements View.OnClickListener, TextWatcher, OnItemClickListener {

    private TextView mTvProjectName;
    private EditText mEtMemberAccount;
    private EditText mEtCustomerName;
    private EditText mEtPhoneNumber;
    private EditText mEtProjectAddress;
    private EditText mEtCommunityName;
    private EditText mEtDetailAddress;
    private Button mBtnNextPager;

    private AddressDialog mChangeAddressDialog;
    private String designer_id;
    private String designer_uid;
    private String mCurrentProvince, mCurrentCity, mCurrentDistrict;
    private String mCurrentProvinceCode, mCurrentCityCode, mCurrentDistrictCode;
    private AlertView mBackAlertView;
    private String acs_member_id;
    private boolean isEditable = false;


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_new_inventory;
    }

    @Override
    protected void initView() {
        super.initView();
        setTitleForNavbar(UIUtils.getString(R.string.personal_new_inventory));
        mTvProjectName = (TextView) findViewById(R.id.et_new_inventory_project_name);
        mEtMemberAccount = (EditText) findViewById(R.id.et_new_inventory_member_account);
        mEtCustomerName = (EditText) findViewById(R.id.et_new_inventory_name);
        mEtPhoneNumber = (EditText) findViewById(R.id.et_new_inventory_phone_number);
        mEtProjectAddress = (EditText) findViewById(R.id.et_new_inventory_project_address);
        mEtCommunityName = (EditText) findViewById(R.id.et_new_inventory_community_name);
        mEtDetailAddress = (EditText) findViewById(R.id.et_new_inventory_detailed_address);
        mBtnNextPager = (Button) findViewById(R.id.btn_new_inventory_next);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        MemberEntity memberEntity = AdskApplication.getInstance().getMemberEntity();
        designer_id = memberEntity.getAcs_member_id();
        designer_uid = memberEntity.getHs_uid();

    }

    /**
     * 重写返回键
     *
     * @param view
     */
    protected void leftNavButtonClicked(View view) {
        String mProjectName = mTvProjectName.getText().toString();
        String mMemberAccount = mEtMemberAccount.getText().toString();
        String mCustomerName = mEtCustomerName.getText().toString();
        String mPhoneNumber = mEtPhoneNumber.getText().toString();
        String mProjectAddress = mEtProjectAddress.getText().toString();
        String mCommunityName = mEtCommunityName.getText().toString();
        String mDetailAddress = mEtDetailAddress.getText().toString();
        if (!(StringUtils.isEmpty(mProjectName) && StringUtils.isEmpty(mMemberAccount) && StringUtils.isEmpty(mCustomerName) && StringUtils.isEmpty(mPhoneNumber) && StringUtils.isEmpty(mProjectAddress) && StringUtils.isEmpty(mCommunityName) && StringUtils.isEmpty(mDetailAddress))) {
            mBackAlertView = new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.new_inventory_if_cancel), UIUtils.getString(R.string.cancel), new String[]{UIUtils.getString(R.string.sure)}, null, this, null, this).setCancelable(false);
            mBackAlertView.show();
        } else {
            finish();
        }

    }

    @Override
    protected void initListener() {
        super.initListener();
        mTvProjectName.setOnClickListener(this);
        mTvProjectName.addTextChangedListener(this);
        mBtnNextPager.setOnClickListener(this);
        mEtProjectAddress.setOnClickListener(this);
        mEtProjectAddress.addTextChangedListener(this);
        mEtMemberAccount.addTextChangedListener(this);
        mEtCustomerName.addTextChangedListener(this);
        mEtPhoneNumber.addTextChangedListener(this);
        mEtCommunityName.addTextChangedListener(this);
        mEtDetailAddress.addTextChangedListener(this);
    }

    /**
     * EditText 变化监听
     *
     * @param charSequence
     * @param i
     * @param i1
     * @param i2
     */
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        if (inputInfo()) {
            mBtnNextPager.setEnabled(false);
            mBtnNextPager.setBackgroundResource(R.drawable.bg_common_btn_gray);

        } else {
            mBtnNextPager.setEnabled(true);
            mBtnNextPager.setBackgroundResource(R.drawable.bg_common_btn_blue);
            mBtnNextPager.setTextColor(UIUtils.getColor(R.color.bg_ff));
        }

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.et_new_inventory_project_name:
                Intent intent = new Intent(NewInventoryActivity.this, SelectProjectActivity.class);
                startActivityForResult(intent, 0);
                break;
            case R.id.et_new_inventory_project_address:
                getPCDAddress();

                break;
            case R.id.btn_new_inventory_next:
                String mProjectName = mTvProjectName.getText().toString();
                String mMemberAccount = mEtMemberAccount.getText().toString();
                String mCustomerName = mEtCustomerName.getText().toString();
                String mPhoneNumber = mEtPhoneNumber.getText().toString();
                String mProjectAddress = mEtProjectAddress.getText().toString();
                String mCommunityName = mEtCommunityName.getText().toString();
                String mDetailAddress = mEtDetailAddress.getText().toString();
                if (isEditable) {
                    /**
                     * 会员正则校验
                     */
                    boolean mMatchesPhone = mMemberAccount.matches(RegexUtil.PHONE_REGEX);
                    boolean mMatchesMail = mMemberAccount.matches(RegexUtil.EMAIL_REGEX);
                    boolean mMatchesAccountNumber = mMemberAccount.matches(RegexUtil.ACCOUNT_NUMBER_REGEX);

                    if (!(mMatchesPhone || mMatchesMail || mMatchesAccountNumber)) {
                        new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.new_inventory_input_right_account_number),
                                null, null, new String[]{UIUtils.getString(R.string.sure)}, NewInventoryActivity.this, AlertView.Style.Alert, null).show();

                        return;
                    }

                    /**
                     * 客户姓名正则校验
                     */
                    boolean mMatchesName = mCustomerName.matches(RegexUtil.NICK_NAME_REGEX);
                    if (!mMatchesName) {
                        new AlertView(UIUtils.getString(R.string.tip), "姓名应为2-20个中文、英文、数字字符",
                                null, null, new String[]{UIUtils.getString(R.string.sure)}, NewInventoryActivity.this, AlertView.Style.Alert, null).show();

                        return;
                    }

                    /**
                     *手机号码正则校验
                     */

                    boolean mMatchesPhoneNumber = mPhoneNumber.matches(RegexUtil.PHONE_REGEX);
                    if (!mMatchesPhoneNumber) {
                        new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.new_inventory_input_right_phone_number),
                                null, null, new String[]{UIUtils.getString(R.string.sure)}, NewInventoryActivity.this, AlertView.Style.Alert, null).show();

                        return;
                    }


                    /**
                     * 小区名称
                     */
                    boolean mMatchesCommunityName = mCommunityName.matches(RegexUtil.ADDRESS_REGEX);
                    if (!mMatchesCommunityName) {
                        new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.new_inventory_input_right_community_name),
                                null, null, new String[]{UIUtils.getString(R.string.sure)}, NewInventoryActivity.this, AlertView.Style.Alert, null).show();

                        return;
                    }

                    /**
                     * 详细地址
                     */
                    if (!StringUtils.isEmpty(mDetailAddress)) {
                        boolean mMatchesDetailAddress = mDetailAddress.matches(RegexUtil.ADDRESS_REGEX);
                        if (!mMatchesDetailAddress) {
                            new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.new_inventory_input_right_detail_address),
                                    null, null, new String[]{UIUtils.getString(R.string.sure)}, NewInventoryActivity.this, AlertView.Style.Alert, null).show();

                            return;
                        }
                    }
                }
                /**
                 * 判断是否为会员帐号
                 */
                getMemberAccountList(mMemberAccount);

                break;
        }
    }

    private void newInvertoryList(String acs_member_id) {

        String mDetailAddress = mEtDetailAddress.getText().toString();
        String mCustomerName = mEtCustomerName.getText().toString();
        String mCommunityName = mEtCommunityName.getText().toString();
        String mPhoneNumber = mEtPhoneNumber.getText().toString();
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put(JsonConstants.JSON_NEW_INVENTORY_CITY, mCurrentCityCode);
            jsonObject.put(JsonConstants.JSON_NEW_INVENTORY_CITY_NAME, mCurrentCity);
            jsonObject.put(JsonConstants.JSON_NEW_INVENTORY_COMMUNITY_ADDRESS, mDetailAddress);
            jsonObject.put(JsonConstants.JSON_NEW_INVENTORY_COMMUNITY_NAME, mCommunityName);
            jsonObject.put(JsonConstants.JSON_NEW_INVENTORY_CONSUMER_ID, acs_member_id);
            jsonObject.put(JsonConstants.JSON_NEW_INVENTORY_CONSUMER_MOBILE, mPhoneNumber);
            jsonObject.put(JsonConstants.JSON_NEW_INVENTORY_CUSTOMER_NAME, mCustomerName);

            jsonObject.put(JsonConstants.JSON_NEW_INVENTORY_CONSUMER_UID, "123456789");

            jsonObject.put(JsonConstants.JSON_NEW_INVENTORY_DESIGNER_UID, designer_uid);
            jsonObject.put(JsonConstants.JSON_NEW_INVENTORY_DISTRICT, mCurrentDistrictCode);
            jsonObject.put(JsonConstants.JSON_NEW_INVENTORY_DISTRICT_NAME, mCurrentDistrict);

            jsonObject.put(JsonConstants.JSON_NEW_INVENTORY_PROVINCE, mCurrentProvinceCode);
            jsonObject.put(JsonConstants.JSON_NEW_INVENTORY_PROVINCE_NAME, mCurrentProvince);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        CustomProgress.show(this, UIUtils.getString(R.string.data_submission), false, null);
        getNewInventoryList(jsonObject, designer_id);
    }

    /**
     * EditText判空
     *
     * @return
     */
    private Boolean inputInfo() {

        String mMemberAccount = mEtMemberAccount.getText().toString();
        String mCustomerName = mEtCustomerName.getText().toString();
        String mPhoneNumber = mEtPhoneNumber.getText().toString();
        String mCommunityName = mEtCommunityName.getText().toString();
        String mDetailAddress = mEtDetailAddress.getText().toString();
        String mProjectName = mTvProjectName.getText().toString();
        String mProjectAddress = mEtProjectAddress.getText().toString();

        if (StringUtils.isEmpty(mMemberAccount)) {
            return true;
        }
        if (StringUtils.isEmpty(mCustomerName)) {
            return true;
        }
        if (StringUtils.isEmpty(mPhoneNumber)) {
            return true;
        }
        if (StringUtils.isEmpty(mCommunityName)) {
            return true;
        }
       /* if (StringUtils.isEmpty(mDetailAddress)) {
            return true;
        }*/

        if (StringUtils.isEmpty(mProjectName)) {
            return true;
        }
        if (StringUtils.isEmpty(mProjectAddress)) {
            return true;
        }
        return false;
    }


    /**
     * 保存项目
     *
     * @param jsonObject
     */
    private void getNewInventoryList(JSONObject jsonObject, String designer_id) {

        MPServerHttpManager.getInstance().getNewInventoryList(jsonObject, designer_id, new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                CustomProgress.cancelDialog();
                Log.d("NewInventoryActivity", jsonObject.toString());
                NewInventoryEntity entity = GsonUtil.jsonToBean(jsonObject.toString(), NewInventoryEntity.class);
                String asset_id = entity.getAsset_id();
                RecommendListDetailActivity.actionStartActivity(NewInventoryActivity.this, asset_id);
                finish();

            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                CustomProgress.cancelDialog();
                Log.d("NewInventoryActivity", "失败啦");
                new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.new_inventory_save_project_fail),
                        null, null, new String[]{UIUtils.getString(R.string.sure)}, NewInventoryActivity.this, AlertView.Style.Alert, null).show();
            }
        });
    }

    private void getMemberAccountList(String member_account) {

        MPServerHttpManager.getInstance().getMemberAccountList(member_account, new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                CustomProgress.cancelDialog();
                Log.d("NewInventoryActivity", jsonObject.toString());
                MemberAccountEntity entity = GsonUtil.jsonToBean(jsonObject.toString(), MemberAccountEntity.class);
                Integer flag = entity.getCheck_flag();
                if (flag == 0) {
                    new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.new_inventory_member_account_not_exit),
                            null, null, new String[]{UIUtils.getString(R.string.sure)}, NewInventoryActivity.this, AlertView.Style.Alert, null).show();
                    return;
                }
                acs_member_id = entity.getAcs_member_id();
                newInvertoryList(acs_member_id);
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                CustomProgress.cancelDialog();
                new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.network_error),
                        null, null, new String[]{UIUtils.getString(R.string.sure)}, NewInventoryActivity.this, AlertView.Style.Alert, null).show();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == RESULT_OK && null != data) {
            SelectProjectEntity.DesignerProjectsBean designerProjectsBean = (SelectProjectEntity.DesignerProjectsBean) data.getSerializableExtra("mSelectList");
            updateNotify(designerProjectsBean);
        }
    }

    private void updateNotify(SelectProjectEntity.DesignerProjectsBean designerProjectsBean) {


        if (!StringUtils.isEmpty(designerProjectsBean)) {
            isEditable = false;
            setEditTextEnable(isEditable);

            mTvProjectName.setText(designerProjectsBean.getCommunity_name());
            mEtMemberAccount.setText(designerProjectsBean.getConsumer_zid());
            mEtCustomerName.setText(designerProjectsBean.getConsumer_name());
            mEtPhoneNumber.setText(designerProjectsBean.getConsumer_mobile());

            mCurrentProvinceCode = designerProjectsBean.getProvince();
            mCurrentCityCode = designerProjectsBean.getCity();
            mCurrentDistrictCode = designerProjectsBean.getDistrict();
            mCurrentProvince = designerProjectsBean.getProvince_name();
            mCurrentCity = designerProjectsBean.getCity_name();
            mCurrentDistrict = designerProjectsBean.getDistrict_name();

            mEtProjectAddress.setText(mCurrentProvince + mCurrentCity +
                    UIUtils.getNoStringIfEmpty(mCurrentDistrict));
            mEtCommunityName.setText(designerProjectsBean.getCommunity_name());
            mEtDetailAddress.setText(designerProjectsBean.getCommunity_address());
        } else {
            isEditable = true;
            setEditTextEnable(isEditable);
            mTvProjectName.setText("创建新的项目");
            mEtMemberAccount.setText("");
            mEtCustomerName.setText("");
            mEtPhoneNumber.setText("");
            mEtProjectAddress.setText("");
            mEtCommunityName.setText("");
            mEtDetailAddress.setText("");
        }
    }

    /**
     * 设置EditText是否可以编辑
     */
    private void setEditTextEnable(boolean isEditable) {
        mEtMemberAccount.setEnabled(isEditable);
        mEtCustomerName.setEnabled(isEditable);
        mEtPhoneNumber.setEnabled(isEditable);
        mEtProjectAddress.setEnabled(isEditable);
        mEtCommunityName.setEnabled(isEditable);
        mEtDetailAddress.setEnabled(isEditable);
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
                        // 由于有些地区没有区这个字段，将含有区域得字段name改为none，code改为0
                        mCurrentDistrict = area;
                        mCurrentDistrictCode = areaCode;

                        area = UIUtils.getNoStringIfEmpty(area);

                        mEtProjectAddress.setText(province + " " + city + " " + area);
                        mChangeAddressDialog.dismiss();
                    }

                });
    }


    @Override
    public void onItemClick(Object object, int position) {
        if (mBackAlertView == object && position != AlertView.CANCELPOSITION) {
            finish();
        }
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

}
