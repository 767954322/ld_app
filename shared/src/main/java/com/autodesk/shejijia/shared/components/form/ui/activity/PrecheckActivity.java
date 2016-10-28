package com.autodesk.shejijia.shared.components.form.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.form.contract.PrecheckContract;
import com.autodesk.shejijia.shared.framework.activity.BaseActivity;

/**
 * Created by t_aij on 16/10/25.
 */

public class PrecheckActivity extends BaseActivity implements View.OnClickListener, PrecheckContract.View {

    private ImageView mLeft;
    private TextView mCenter;
    private TextView mRight;
    private RadioButton mOk;
    private RadioButton mNo;
    private LinearLayout mIsNecessary;
    private LinearLayout mIsAdditional;
    private Button mSelect;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_precheck;
    }

    @Override
    protected void initView() {
        //navigationbar
        mLeft = (ImageView) findViewById(R.id.iv_left);
        mCenter = (TextView) findViewById(R.id.tv_center);
        mRight = (TextView) findViewById(R.id.tv_right);
        //验收条件的按钮
        mOk = (RadioButton) findViewById(R.id.btn_ok);
        mNo = (RadioButton) findViewById(R.id.btn_no);
        //必要条件
        mIsNecessary = (LinearLayout) findViewById(R.id.ll_is_necessary);
        //辅助条件
        mIsAdditional = (LinearLayout) findViewById(R.id.ll_is_additional);
        //确定按钮
        mSelect = (Button) findViewById(R.id.btn_select);
    }

    @Override
    protected void initExtraBundle() {
        // TODO: 16/10/27  获取到的task信息
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        // TODO: 16/10/27 意外终止时保存的数据
    }

    @Override
    protected void initListener() {
        // TODO: 16/10/27  设置监听事件
        mLeft.setOnClickListener(this);
        mOk.setOnClickListener(this);
        mNo.setOnClickListener(this);
        mSelect.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        //合格与不合格的标识,0为初始化,1为合格,2为不合格
        int flag = 0;
        int i = v.getId();
        if (i == R.id.iv_left) {
            finish();

        } else if (i == R.id.btn_ok) {// TODO: 16/10/27 点击合格的按钮
            if (flag != 1) {
                //方法写在presenter中
                flag = 1;
            }


        } else if (i == R.id.btn_no) {// TODO: 16/10/27 点击不合格的按钮
            if (flag != 2) {

                flag = 2;
            }

        } else if (i == R.id.btn_select) {// TODO: 16/10/27  点击验收的按钮


        } else {
        }
    }

    @Override
    public void setNavigationBar() {

    }

    @Override
    public void showNetError(String msg) {

    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}



























