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
import com.autodesk.shejijia.consumer.utils.ApiStatusUtil;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.AlertView;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.MPNetworkUtils;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.framework.AdskApplication;
import com.autodesk.shejijia.shared.framework.activity.NavigationBarActivity;
import com.socks.library.KLog;

import org.json.JSONException;
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
        rlTiXian = (RelativeLayout) findViewById(R.id.rl_tixian);

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
            String hs_uid = memberEntity.getHs_uid();
            String acs_Member_Id = memberEntity.getMember_id();
            ifIsLohoDesiner(acs_Member_Id, hs_uid);
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
//                bundle.putString("real_name", real_name);
//                bundle.putString("designer_id", designer_id);
//                bundle.putSerializable(Constant.DesignerMyPropertyKey.MY_PROPERTY_BEAN, myPropertyBean);
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
                rlTiXian.setVisibility(View.VISIBLE);
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                setBtnUnpress();
                tv_my_property_account_balance.setText("¥ " + "0.00");
                ApiStatusUtil.getInstance().apiStatuError(volleyError, MyPropertyActivity.this);
                MPNetworkUtils.logError(TAG, volleyError);
            }
        });
    }


    /**
     * 判断设计师类型
     *
     * @param desiner_id
     * @param hs_uid
     */
    private void ifIsLohoDesiner(String desiner_id, String hs_uid) {

        MPServerHttpManager.getInstance().ifIsLohoDesiner(desiner_id, hs_uid, new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
            }

            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    JSONObject jsonObject1 = jsonObject.getJSONObject("designer");
                    int is_loho = jsonObject1.getInt("is_loho");
                    String is_real_name = jsonObject1.getString("is_real_name");
                    if (is_real_name.equals("null")) {
                        showAlertView("您还未通过实名认证,请到网页端完成认证");
//
                    } else {

                    }
                    // change button show logic
                    btn_my_property_withdrawal.setVisibility(is_loho != 0 ? View.GONE : View.VISIBLE);
//                    if (is_loho != 0) {
//                        rlTiXian.setVisibility(View.GONE);
//                    } else {
//                        rlTiXian.setVisibility(View.VISIBLE);
//                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    //打开AlertView对话框
    private void showAlertView(String content) {
        new AlertView(UIUtils.getString(R.string.tip), content, null, null, new String[]{UIUtils.getString(R.string.sure)}, MyPropertyActivity.this, AlertView.Style.Alert, null).show();
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
        btn_my_property_withdrawal.setBackgroundResource(R.drawable.bg_common_btn_gap_blue);
        btn_my_property_withdrawal.setTextColor(UIUtils.getColor(R.color.bg_0084ff));
    }


    private TextView tv_my_property_account_balance;
    private Button btn_my_property_withdrawal;
    private RelativeLayout rl_my_property_transaction_record;
    private RelativeLayout rl_my_property_withdrawal_record;
    private RelativeLayout rlTiXian;

    private MyPropertyBean myPropertyBean;
    private String real_name;

    private String designer_id;
    private String amount;
}
