package com.autodesk.shejijia.consumer.personalcenter.recommend.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.framework.activity.NavigationBarActivity;

public class NewInventoryActivity extends NavigationBarActivity {

    private TextView mTvProjectName;
    private EditText mEtMemberAccount;
    private EditText mEtCustomerName;
    private EditText mEtPhoneNumber;
    private EditText mEtProjectAddress;
    private EditText mEtCommunityName;
    private EditText mEtDetailAddress;
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_new_inventory;
    }

    @Override
    protected void initView() {
        super.initView();
        setTitleForNavbar(UIUtils.getString(R.string.personal_new_inventory));
        mTvProjectName = (TextView) findViewById(R.id.tv_new_inventory_project_name);
        mEtMemberAccount = (EditText) findViewById(R.id.et_new_inventory_member_account);
        mEtCustomerName = (EditText) findViewById(R.id.et_new_inventory_community_name);
        mEtPhoneNumber = (EditText) findViewById(R.id.et_new_inventory_phone_number);
        mEtProjectAddress = (EditText) findViewById(R.id.et_new_inventory_project_address);
        mEtCommunityName = (EditText) findViewById(R.id.et_new_inventory_community_name);
        mEtDetailAddress = (EditText) findViewById(R.id.et_new_inventory_detailed_address);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
    }

    @Override
    protected void initListener() {
        super.initListener();
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
