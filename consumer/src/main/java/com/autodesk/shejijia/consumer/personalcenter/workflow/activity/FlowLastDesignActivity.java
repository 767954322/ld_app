package com.autodesk.shejijia.consumer.personalcenter.workflow.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.consumer.personalcenter.designer.entity.DesignerInfoDetails;
import com.autodesk.shejijia.consumer.personalcenter.workflow.entity.AliPayBean;
import com.autodesk.shejijia.consumer.personalcenter.workflow.entity.WkFlowDetailsBean;
import com.autodesk.shejijia.consumer.utils.AliPayService;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.MPNetworkUtils;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.components.common.uielements.MyToast;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.AlertView;
import com.google.gson.Gson;
import com.socks.library.KLog;

import org.json.JSONObject;

import java.text.DecimalFormat;

/**
 * @author Malidong .
 * @version 1.0 .
 * @date 16-6-7
 * @file FlowLastDesignActivity.java  .
 * @brief 全流程设计尾款 .
 */
public class FlowLastDesignActivity extends BaseWorkFlowActivity {


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_flow_last_design;
    }

    @Override
    protected void initView() {
        super.initView();

        tv_flow_last_design_contract_no = (TextView) findViewById(R.id.tv_flow_last_design_contract_no);
        tv_flow_last_design_name = (TextView) findViewById(R.id.tv_flow_last_design_name);
        tv_flow_last_design_phone = (TextView) findViewById(R.id.tv_flow_last_design_phone);
        tv_flow_last_design_aggregate_amount = (TextView) findViewById(R.id.tv_flow_last_design_aggregate_amount);
        tv_flow_last_design_first = (TextView) findViewById(R.id.tv_flow_last_design_first);
        tv_flow_last_design_last = (TextView) findViewById(R.id.tv_flow_last_design_last);
        tv_flow_last_design_should_first = (TextView) findViewById(R.id.tv_flow_last_design_should_first);
        ll_flow_last_design_send = (LinearLayout) findViewById(R.id.ll_flow_last_design_send);
        btn_flow_last_design_send = (Button) findViewById(R.id.btn_flow_last_design_send);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);

        setTitleForNavbar(getResources().getString(R.string.flow_cost_detail)); /// 设置标题 .
    }

    @Override
    protected void onWorkFlowData() {
        super.onWorkFlowData();

        getDesignerInfoData(designer_id, hs_uid);

        upDataViewFromData();
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

                upDataViewFromInfoData();
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                MPNetworkUtils.logError(TAG, volleyError);
                new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.network_error), null, new String[]{UIUtils.getString(R.string.sure)}, null, FlowLastDesignActivity.this,
                        AlertView.Style.Alert, null).show();
            }
        });
    }


    private void upDataViewFromData() {
        tv_flow_last_design_contract_no.setText(requirement.getBidders().get(0).getDesign_contract().getContract_no());
        tv_flow_last_design_aggregate_amount.setText(requirement.getBidders().get(0).getDesign_contract().getContract_charge());
        tv_flow_last_design_first.setText(requirement.getBidders().get(0).getDesign_contract().getContract_first_charge());

        Double totalCost = Double.parseDouble(requirement.getBidders().get(0).getDesign_contract().getContract_charge());
        Double firstCost = Double.parseDouble(requirement.getBidders().get(0).getDesign_contract().getContract_first_charge());
        DecimalFormat df = new DecimalFormat("#.##"); // 保留小数点后两位
        tv_flow_last_design_last.setText(df.format(totalCost - firstCost)); // 设计尾款

        if (Integer.valueOf(wk_cur_sub_node_id) == 41) {
            ll_flow_last_design_send.setVisibility(View.VISIBLE);
            btn_flow_last_design_send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    WkFlowDetailsBean.RequirementEntity.BiddersEntity.OrdersEntity order = null;

                    order = getOrderEntityByStep(FlowLastDesignActivity.this.wk_cur_ActionNode_id);
                    if (order == null)
                        return;

                    String order_line_no = order.getOrder_line_no();
                    String order_no = order.getOrder_no();
                    if (isLock) {
                        getAlipayDetailsInfo(order_no, order_line_no);
                        isLock = false;
                    }
                }
            });
        }
    }

    private void upDataViewFromInfoData() {
        if (!designerInfoList.equals(null)) {
            tv_flow_last_design_name.setText(designerInfoList.getReal_name().getReal_name());
            tv_flow_last_design_phone.setText(designerInfoList.getReal_name().getMobile_number().toString());
            Double totalCost = Double.parseDouble(requirement.getBidders().get(0).getDesign_contract().getContract_charge());
            Double firstCost = Double.parseDouble(requirement.getBidders().get(0).getDesign_contract().getContract_first_charge());
            DecimalFormat df = new DecimalFormat("#.##"); // 保留小数点后两位
            tv_flow_last_design_should_first.setText(df.format(totalCost - firstCost)); // 本次应付设计尾款
        }
    }

    /**
     * @param order_no
     * @param order_line_no
     * @brief 获取支付宝详细信息 .
     */
    private void getAlipayDetailsInfo(String order_no, String order_line_no) {
        MPServerHttpManager.getInstance().getAlipayDetailInfo(order_no, order_line_no, new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                String userInfo = GsonUtil.jsonToString(jsonObject);
                KLog.json(TAG, userInfo);

                AliPayBean aliPayBean = GsonUtil.jsonToBean(userInfo, AliPayBean.class);

                String amount;
                String notifyURL;
                String tradeNO;
                String productName;
                String Seller;
                String Partner;

                Seller = aliPayBean.getSeller();
                Partner = aliPayBean.getPartner();
                productName = aliPayBean.getProductName();
                amount = aliPayBean.getAmount();
                notifyURL = aliPayBean.getNotifyURL();
                tradeNO = aliPayBean.getTradeNO();

                AliPayService PayService = new AliPayService(Seller, Partner, productName, amount, notifyURL, productName, tradeNO);
                PayService.SetCallBack(AliCallBack);
                PayService.DoPayment(FlowLastDesignActivity.this);
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                MPNetworkUtils.logError(TAG, volleyError);
            }
        });
    }

    AliPayService.AliPayActionStatus AliCallBack = new AliPayService.AliPayActionStatus() {
        public void onOK() {
            MyToast.show(FlowLastDesignActivity.this, UIUtils.getString(R.string.pay_success));
            finish();
        }

        public void onFail() {
            MyToast.show(FlowLastDesignActivity.this, UIUtils.getString(R.string.pay_failed));
            isLock = true;
        }
    };

    private LinearLayout ll_flow_last_design_send;
    private TextView tv_flow_last_design_contract_no;
    private TextView tv_flow_last_design_name;
    private TextView tv_flow_last_design_phone;
    private TextView tv_flow_last_design_aggregate_amount;
    private TextView tv_flow_last_design_first;
    private TextView tv_flow_last_design_last;
    private TextView tv_flow_last_design_should_first;
    private Button btn_flow_last_design_send;

    private boolean isLock = true; // 是否锁定按键

}