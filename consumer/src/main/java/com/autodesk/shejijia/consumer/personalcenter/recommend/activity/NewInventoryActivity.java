package com.autodesk.shejijia.consumer.personalcenter.recommend.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.personalcenter.consumer.activity.IssueDemandActivity;
import com.autodesk.shejijia.consumer.personalcenter.workflow.activity.FlowEstablishContractActivity;
import com.autodesk.shejijia.consumer.utils.ToastUtil;
import com.autodesk.shejijia.shared.components.common.uielements.AddressDialog;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.AlertView;
import com.autodesk.shejijia.shared.components.common.utility.RegexUtil;
import com.autodesk.shejijia.shared.components.common.utility.StringUtils;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.framework.activity.NavigationBarActivity;
import com.squareup.okhttp.internal.Util;

import java.io.Serializable;

import static com.autodesk.shejijia.consumer.R.array.all;

public class NewInventoryActivity extends NavigationBarActivity implements View.OnClickListener, TextWatcher {

    private EditText mTvProjectName;
    private EditText mEtMemberAccount;
    private EditText mEtCustomerName;
    private EditText mEtPhoneNumber;
    private EditText mEtProjectAddress;
    private EditText mEtCommunityName;
    private EditText mEtDetailAddress;
    private Button mBtnNextPager;

    private AddressDialog mChangeAddressDialog;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_new_inventory;
    }

    @Override
    protected void initView() {
        super.initView();
        setTitleForNavbar(UIUtils.getString(R.string.personal_new_inventory));
        mTvProjectName = (EditText) findViewById(R.id.et_new_inventory_project_name);
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

    }

    @Override
    protected void initListener() {
        super.initListener();
        mTvProjectName.setOnClickListener(this);
//        mTvProjectName.addTextChangedListener(this);
        mBtnNextPager.setOnClickListener(this);
        mEtProjectAddress.setOnClickListener(this);
        mEtProjectAddress.addTextChangedListener(this);
        mEtMemberAccount.addTextChangedListener(this);
        mEtCustomerName.addTextChangedListener(this);
        mEtPhoneNumber.addTextChangedListener(this);
        mEtCommunityName.addTextChangedListener(this);
        mEtDetailAddress.addTextChangedListener(this);
    }

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
                startActivity(intent);
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
                boolean mMatchesName = mCustomerName.matches(RegexUtil.NAME_REGEX);
                if (!mMatchesName) {
                    new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.new_inventory_input_right_name),
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
                boolean mMatchesCommunityName = mCommunityName.matches(RegexUtil.COMMUNITY_NAME_REGEX);
                if (!mMatchesCommunityName) {
                    new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.new_inventory_input_right_community_name),
                            null, null, new String[]{UIUtils.getString(R.string.sure)}, NewInventoryActivity.this, AlertView.Style.Alert, null).show();
                    return;
                }

                /**
                 * 详细地址
                 */
                boolean mMatchesDetailAddress = mDetailAddress.matches(RegexUtil.COMMUNITY_NAME_REGEX);
                if (!mMatchesDetailAddress) {
                    new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.new_inventory_input_right_detail_address),
                            null, null, new String[]{UIUtils.getString(R.string.sure)}, NewInventoryActivity.this, AlertView.Style.Alert, null).show();
                    return;
                }

                break;
        }

    }

    private Boolean inputInfo() {

        String mMemberAccount = mEtMemberAccount.getText().toString();
        String mCustomerName = mEtCustomerName.getText().toString();
        String mPhoneNumber = mEtPhoneNumber.getText().toString();
        String mCommunityName = mEtCommunityName.getText().toString();
        String mDetailAddress = mEtDetailAddress.getText().toString();
//        String mProjectName = mTvProjectName.getText().toString();
        String mProjectAddress = mEtProjectAddress.getText().toString();
//
//        if (StringUtils.isEmpty(mMemberAccount)) {
//            return true;
//        }
        if (StringUtils.isEmpty(mProjectAddress)) {
            return true;
        }
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
        if (StringUtils.isEmpty(mDetailAddress)) {
            return true;
        }
        return false;
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
//                        mCurrentProvince = province;
//                        mCurrentProvinceCode = provinceCode;
//                        mCurrentCity = city;
//                        mCurrentCityCode = cityCode;
//                        // 由于有些地区没有区这个字段，将含有区域得字段name改为none，code改为0
//                        mCurrentDistrict = area;
//                        mCurrentDistrictCode = areaCode;
//
//                        area = UIUtils.getNoStringIfEmpty(area);

                        mEtProjectAddress.setText(province + " " + city + " " + area);
                        mChangeAddressDialog.dismiss();
                    }

                });
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
