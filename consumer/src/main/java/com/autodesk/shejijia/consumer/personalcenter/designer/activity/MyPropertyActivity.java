package com.autodesk.shejijia.consumer.personalcenter.designer.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.consumer.personalcenter.designer.entity.MyPropertyBean;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.MPNetworkUtils;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.framework.AdskApplication;
import com.autodesk.shejijia.shared.framework.activity.NavigationBarActivity;
import com.socks.library.KLog;

import org.json.JSONObject;

/**
 * @author Luchongbin .
 * @version 1.0 .
 * @date 16-6-7
 * @file MyPropertyActivity.java  .
 * @brief 我的资产页面  .
 */
public class MyPropertyActivity extends NavigationBarActivity implements View.OnClickListener {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_my_property;
    }

    @Override
    protected void initView() {
        super.initView();
        tv_my_property_account_balance = (TextView) findViewById(R.id.tv_my_property_account_balance);
        btn_my_property_withdrawal = (Button) findViewById(R.id.btn_my_property_withdrawal);
        rl_my_property_transaction_record = (RelativeLayout) findViewById(R.id.rl_my_property_transaction_record);
        rl_my_property_withdrawal_record = (RelativeLayout) findViewById(R.id.rl_my_property_withdrawal_record);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        setTitleForNavbar(UIUtils.getString(R.string.my_property));
        initMemberEntity();
    }

    private void initMemberEntity() {

        MemberEntity memberEntity = AdskApplication.getInstance().getMemberEntity();
        if (null != memberEntity) {
            designer_id = memberEntity.getAcs_member_id();
            getMyPropertyData(designer_id);
        }
    }

    @Override
    protected void initListener() {
        super.initListener();
        rl_my_property_transaction_record.setOnClickListener(this);
        rl_my_property_withdrawal_record.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_my_property_transaction_record:    /// 交易记录页面 .
                startActivity(new Intent(this, TransactionRecordActivity.class));
                break;
            case R.id.rl_my_property_withdrawal_record:  /// 提现记录页面 .
                startActivity(new Intent(this, WithdrawalRecordActivity.class));
                break;
            case R.id.btn_my_property_withdrawal:          /// 我的提现页面 .
                Intent intent = new Intent(this, WithdrawalActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constant.DesignerMyPropertyKey.MY_PROPERTY_BEAN, myPropertyBean);
                intent.putExtras(bundle);
                startActivityForResult(intent, 0);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == Activity.RESULT_OK) {
            amount = data.getStringExtra(Constant.DesignerWithDraw.AMOUNT);
            boolean flag = data.getBooleanExtra(Constant.DesignerWithDraw.IS_SUCCESS, false);
            if (!flag) {
                return;
            }
            setBtnUnpress();
            tv_my_property_account_balance.setText("¥ " + amount);
        }
    }

    /**
     * 获取我的资产信息
     *
     * @param designer_id
     */
    public void getMyPropertyData(String designer_id) {
        MPServerHttpManager.getInstance().getMyPropertyData(designer_id, new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                String jsonString = GsonUtil.jsonToString(jsonObject);
                myPropertyBean = GsonUtil.jsonToBean(jsonString, MyPropertyBean.class);
                KLog.json(TAG, jsonString);
                amount = myPropertyBean.getAmount();
                if (null == myPropertyBean || TextUtils.isEmpty(amount) || "0".equals(amount)) {
                    amount = "0.00";
                    setBtnUnpress();
                    tv_my_property_account_balance.setText("¥ " + amount);
                    return;
                }
                setBtnCanpress();
                tv_my_property_account_balance.setText("¥ " + amount);
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                setBtnUnpress();
                tv_my_property_account_balance.setText("¥ " + "0.00");
                MPNetworkUtils.logError(TAG, volleyError);
            }
        });
    }

    /**
     * 设置提现按钮不可点击
     */
    private void setBtnUnpress() {
        btn_my_property_withdrawal.setOnClickListener(null);
        btn_my_property_withdrawal.setEnabled(false);
        btn_my_property_withdrawal.setBackgroundResource(R.drawable.bg_common_btn_pressed);
        btn_my_property_withdrawal.setTextColor(UIUtils.getColor(R.color.white));
    }

    /**
     * 设置提现按钮可以点击
     */
    private void setBtnCanpress() {
        btn_my_property_withdrawal.setOnClickListener(this);
        btn_my_property_withdrawal.setEnabled(true);
        btn_my_property_withdrawal.setBackgroundResource(R.drawable.bg_common_btn_blue);
        btn_my_property_withdrawal.setTextColor(UIUtils.getColor(R.color.white));
    }


    private TextView tv_my_property_account_balance;
    private Button btn_my_property_withdrawal;
    private RelativeLayout rl_my_property_transaction_record;
    private RelativeLayout rl_my_property_withdrawal_record;

    private MyPropertyBean myPropertyBean;

    private String designer_id;
    private String amount;
}
