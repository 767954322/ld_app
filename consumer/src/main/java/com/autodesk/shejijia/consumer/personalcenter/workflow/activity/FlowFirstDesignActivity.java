package com.autodesk.shejijia.consumer.personalcenter.workflow.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.home.decorationdesigners.activity.DesignerFiltrateActivity;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.consumer.personalcenter.designer.entity.DesignerInfoDetails;
import com.autodesk.shejijia.consumer.personalcenter.workflow.entity.MPAliPayBean;
import com.autodesk.shejijia.consumer.personalcenter.workflow.entity.MPDesignContractBean;
import com.autodesk.shejijia.consumer.personalcenter.workflow.entity.MPOrderBean;
import com.autodesk.shejijia.consumer.utils.AliPayService;
import com.autodesk.shejijia.consumer.utils.ApiStatusUtil;
import com.autodesk.shejijia.consumer.utils.SplitStringUtils;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.uielements.MyToast;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.MPNetworkUtils;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.socks.library.KLog;

import org.json.JSONObject;

import java.text.DecimalFormat;

/**
 * @author Malidong .
 * @version 1.0 .
 * @date 16-6-7
 * @file FlowFirstDesignActivity.java  .
 * @brief 全流程设计首款 .
 */
public class FlowFirstDesignActivity extends BaseWorkFlowActivity {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_flow_first_design;
    }

    @Override
    protected void initView() {
        super.initView();

        tv_flow_first_design_contract_no = (TextView) findViewById(R.id.tv_flow_first_design_contract_no);
        tv_flow_first_design_name = (TextView) findViewById(R.id.tv_flow_first_design_name);
        tv_flow_first_design_phone = (TextView) findViewById(R.id.tv_flow_first_design_phone);
        tv_flow_first_design_aggregate_amount = (TextView) findViewById(R.id.tv_flow_first_design_aggregate_amount);
        tv_flow_first_design_first = (TextView) findViewById(R.id.tv_flow_first_design_first);
        tv_flow_first_design_last = (TextView) findViewById(R.id.tv_flow_first_design_last);
        tv_flow_first_design_should_first = (TextView) findViewById(R.id.tv_flow_first_design_should_first);
        ll_flow_first_design_send = (LinearLayout) findViewById(R.id.ll_flow_first_design_send);
        btn_flow_first_design_send = (Button) findViewById(R.id.btn_flow_first_design_send);
        tv_flow_first_design_deduct_measure_cost = (TextView) findViewById(R.id.tv_flow_first_design_deduct_measure_cost);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        setTitleForNavbar(getResources().getString(R.string.flow_cost_detail)); /// 设置标题 .
    }

    @Override
    protected void onWorkFlowData() {
        super.onWorkFlowData();
        updateViewFromData();
        getDesignerInfoData(designer_id, hs_uid);
    }

    /**
     * @param designer_id
     * @param hs_uid
     * @brief 获取设计师基础信息 .
     */
    public void getDesignerInfoData(String designer_id, String hs_uid) {
        MPServerHttpManager.getInstance().getDesignerInfoData(designer_id, hs_uid, new OkJsonRequest.OKResponseCallback() {
            String jsonString;

            @Override
            public void onResponse(JSONObject jsonObject) {
                jsonString = GsonUtil.jsonToString(jsonObject);
                designerInfoList = GsonUtil.jsonToBean(jsonString, DesignerInfoDetails.class);
                KLog.json("FlowFirstDesignActivity", jsonString);
                updateViewFromInfoData();
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                MPNetworkUtils.logError(TAG, volleyError);
//                new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.network_error), null, new String[]{UIUtils.getString(R.string.sure)}, null, FlowFirstDesignActivity.this,
//                        AlertView.Style.Alert, null).show();
                ApiStatusUtil.getInstance().apiStatuError(volleyError,FlowFirstDesignActivity.this);
            }
        });
    }

    private void updateViewFromData() {
        MPDesignContractBean designContractEntity = mBidders.get(0).getDesign_contract();
        if (null == designContractEntity) {
            return;
        }
        tv_flow_first_design_contract_no.setText(designContractEntity.getContract_no());
        tv_flow_first_design_aggregate_amount.setText(designContractEntity.getContract_charge());
        tv_flow_first_design_first.setText(designContractEntity.getContract_first_charge());

        Double totalCost = Double.parseDouble(designContractEntity.getContract_charge());
        Double firstCost = Double.parseDouble(designContractEntity.getContract_first_charge());
        DecimalFormat df = new DecimalFormat("#.##"); // 保留小数点后两位
        tv_flow_first_design_last.setText(df.format(totalCost - firstCost)); // 设计尾款

        if (Integer.valueOf(wk_cur_sub_node_id) == 31) {
            ll_flow_first_design_send.setVisibility(View.VISIBLE);
            btn_flow_first_design_send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MPOrderBean order = null;
                    order = getOrderEntityByStep(FlowFirstDesignActivity.this.nodeState);
                    if (order == null)
                        return;
                    String order_line_no = order.getOrder_line_no();
                    String order_no = order.getOrder_no();
                    if (isLock) {
                        getAliPayDetailInfo(order_no, order_line_no);
                        isLock = false;
                    }
                }
            });
        }
    }

    private void updateViewFromInfoData() {
        if (!designerInfoList.equals(null)) {
            tv_flow_first_design_name.setText(designerInfoList.getReal_name().getReal_name());
            tv_flow_first_design_phone.setText(designerInfoList.getReal_name().getMobile_number().toString());

            String measurement_price = mBidders.get(0).getMeasurement_fee();
            if (TextUtils.isEmpty(measurement_price) || "0".equals(measurement_price) || "0.0".equals(measurement_price)) {
                measurement_price = "0.00";
            }
            measurement_price = SplitStringUtils.splitStringDot(measurement_price);
            tv_flow_first_design_deduct_measure_cost.setText(measurement_price); // 已扣除量房费

            MPDesignContractBean designContractEntity = mBidders.get(0).getDesign_contract();
            if (null == designContractEntity) {
                return;
            }
            Double firstCost = Double.parseDouble(designContractEntity.getContract_first_charge());
            Double measureCost = Double.parseDouble(mBidders.get(0).getMeasurement_fee());
            DecimalFormat df = new DecimalFormat("#.##"); // 保留小数点后两位
            tv_flow_first_design_should_first.setText(df.format(firstCost - measureCost)); // 本次应付设计首款
        }
    }

    /**
     * @param order_no
     * @param order_line_no
     * @brief 获取支付宝详细信息 .
     */
    private void getAliPayDetailInfo(String order_no, String order_line_no) {

        MPServerHttpManager.getInstance().getAlipayDetailInfo(order_no, order_line_no, new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                String userInfo = GsonUtil.jsonToString(jsonObject);
                KLog.d("FlowFirstDesignActivity", userInfo);
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
                PayService.DoPayment(FlowFirstDesignActivity.this);
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                MPNetworkUtils.logError(TAG, volleyError);
            }
        });
    }

    AliPayService.AliPayActionStatus AliCallBack = new AliPayService.AliPayActionStatus() {

        public void onOK() {
            MyToast.show(FlowFirstDesignActivity.this, UIUtils.getString(R.string.pay_success));
            setResult(FirstForContract);
            finish();
        }

        public void onFail() {
            MyToast.show(FlowFirstDesignActivity.this, UIUtils.getString(R.string.pay_failed));
            isLock = true;
        }
    };

    private LinearLayout ll_flow_first_design_send;
    private TextView tv_flow_first_design_name;
    private TextView tv_flow_first_design_phone;
    private TextView tv_flow_first_design_first;
    private TextView tv_flow_first_design_last;
    private TextView tv_flow_first_design_contract_no;
    private TextView tv_flow_first_design_should_first;
    private TextView tv_flow_first_design_aggregate_amount;
    private TextView tv_flow_first_design_deduct_measure_cost;
    private Button btn_flow_first_design_send;
    private int FirstForContract = 1; // 首款调到设计合同

    protected DesignerInfoDetails designerInfoList;
    private boolean isLock = true; // 是否锁定按键
}
