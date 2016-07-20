package com.autodesk.shejijia.consumer.personalcenter.workflow.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.consumer.personalcenter.designer.entity.DesignerInfoDetails;
import com.autodesk.shejijia.consumer.personalcenter.workflow.entity.MPAliPayBean;
import com.autodesk.shejijia.consumer.personalcenter.workflow.entity.MPOrderBean;
import com.autodesk.shejijia.consumer.utils.AliPayService;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.uielements.MyToast;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.AlertView;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.MPNetworkUtils;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.google.gson.Gson;
import com.socks.library.KLog;

import org.json.JSONObject;

/**
 * @author Malidong .
 * @version 1.0 .
 * @date 16-6-7
 * @file FlowMeasureCostActivity.java  .
 * @brief 支付量房费 .
 */
public class FlowMeasureCostActivity extends BaseWorkFlowActivity implements View.OnClickListener {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_flow_measure_cost;
    }

    @Override
    protected void initView() {
        super.initView();
        tv_name = (TextView) findViewById(R.id.tv_flow_measure_cost_name);
        tv_phone = (TextView) findViewById(R.id.tv_flow_measure_cost_phone);
        tv_house_cost = (TextView) findViewById(R.id.tv_flow_measure_house_cost);
        btn_pay_measure = (Button) findViewById(R.id.btn_pay_measure);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_pay_measure: /// 支付量房费 .
                MPOrderBean order = getOrderEntityByStep(this.wk_cur_ActionNode_id);
                if (order == null) {
                    return;
                }

                String order_line_no = order.getOrder_line_no();
                String order_no = order.getOrder_no();
                if (isLock) {
                    getAliPayDetailsInfo(order_no, order_line_no);
                    isLock = false;
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onWorkFlowData() {
        super.onWorkFlowData();
        getDesignerInfoData(designer_id, hs_uid);
        updateViewFromData();
    }

    /**
     * @param designer_id
     * @param hs_uid
     * @brief 获取设计师基础信息 .
     */
    public void getDesignerInfoData(String designer_id, String hs_uid) {
        MPServerHttpManager.getInstance().getDesignerInfoData(designer_id, hs_uid, new OkJsonRequest.OKResponseCallback() {

            @Override
            public void onResponse(JSONObject jsonObject) {
                String jsonString = GsonUtil.jsonToString(jsonObject);
                designerInfoList = new Gson().fromJson(jsonString, DesignerInfoDetails.class);
                if (null != designerInfoList) {
                    tv_name.setText(designerInfoList.getReal_name().getReal_name());
                    tv_phone.setText(designerInfoList.getReal_name().getMobile_number().toString());
                }
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                MPNetworkUtils.logError(TAG, volleyError);
                new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.network_error), null, new String[]{UIUtils.getString(R.string.sure)}, null, FlowMeasureCostActivity.this,
                        AlertView.Style.Alert, null).show();
            }
        });
    }

    private void updateViewFromData() {
        String measurement_fee = mBiddersEntity.getMeasurement_fee();
        if (TextUtils.isEmpty(measurement_fee)||"0.0".equals(measurement_fee)){
            measurement_fee = "0.00";
        }
        tv_house_cost.setText(measurement_fee);

        if (Constant.UerInfoKey.CONSUMER_TYPE.equals(memberEntity.getMember_type())) {
            setTitleForNavbar(getResources().getString(R.string.i_want_to_pay));
            if (Integer.valueOf(this.wk_cur_sub_node_id) >= 21) {                         /// 如果是支付量房费状态及后续状态，就隐藏支付按钮 .
                btn_pay_measure.setVisibility(View.GONE);
                btn_pay_measure.setOnClickListener(null);

            } else {
                if (payOk == true) {
                    btn_pay_measure.setVisibility(View.GONE);
                    btn_pay_measure.setOnClickListener(null);
                } else {
                    btn_pay_measure.setVisibility(View.VISIBLE);
                    btn_pay_measure.setOnClickListener(this);
                }
            }
        } else if (memberEntity.getMember_type().equals(Constant.UerInfoKey.DESIGNER_TYPE)) {
            setTitleForNavbar(getResources().getString(R.string.flow_cost_detail));
            btn_pay_measure.setVisibility(View.GONE);
            btn_pay_measure.setOnClickListener(null);
        }
    }

    /**
     * @param order_no
     * @param order_line_no
     * @biref 获取支付宝详细信息 .
     */
    private void getAliPayDetailsInfo(String order_no, String order_line_no) {
        MPServerHttpManager.getInstance().getAlipayDetailInfo(order_no, order_line_no, new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                String userInfo = GsonUtil.jsonToString(jsonObject);
                KLog.json(TAG, userInfo);

                MPAliPayBean MPAliPayBean = GsonUtil.jsonToBean(userInfo, MPAliPayBean.class);

                String amount;
                String notifyURL;
                String tradeNO;
                String productName;
                String Seller;
                String Partner;

                Seller = MPAliPayBean.getSeller();
                Partner = MPAliPayBean.getPartner();
                productName = MPAliPayBean.getProductName();
                amount = MPAliPayBean.getAmount();
                notifyURL = MPAliPayBean.getNotifyURL();
                tradeNO = MPAliPayBean.getTradeNO();

                AliPayService PayService = new AliPayService(Seller, Partner, productName, amount, notifyURL, productName, tradeNO);
                PayService.SetCallBack(AliCallBack);
                PayService.DoPayment(FlowMeasureCostActivity.this);
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                MPNetworkUtils.logError(TAG, volleyError);
            }
        });
    }

    AliPayService.AliPayActionStatus AliCallBack = new AliPayService.AliPayActionStatus() {
        public void onOK() {
            MyToast.show(FlowMeasureCostActivity.this, UIUtils.getString(R.string.pay_success));
            payOk = true;
            finish();
        }

        public void onFail() {
            MyToast.show(FlowMeasureCostActivity.this, UIUtils.getString(R.string.pay_failed));
            payOk = false;
            isLock = true;
        }
    };

    private TextView tv_name;
    private TextView tv_phone;
    private TextView tv_house_cost;
    private Button btn_pay_measure;

    protected DesignerInfoDetails designerInfoList;
    private boolean payOk = false;
    private boolean isLock = true; // 是否锁定按键
}
