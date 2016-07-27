package com.autodesk.shejijia.consumer.personalcenter.workflow.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.consumer.personalcenter.designer.entity.DesignerInfoDetails;
import com.autodesk.shejijia.consumer.personalcenter.workflow.entity.MPContractDataBean;
import com.autodesk.shejijia.consumer.personalcenter.workflow.entity.MPContractNoBean;
import com.autodesk.shejijia.consumer.personalcenter.workflow.entity.MPDesignContractBean;
import com.autodesk.shejijia.consumer.utils.MPStatusMachine;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.uielements.AddressDialog;
import com.autodesk.shejijia.shared.components.common.uielements.CustomProgress;
import com.autodesk.shejijia.shared.components.common.uielements.MyToast;
import com.autodesk.shejijia.shared.components.common.uielements.TextViewContent;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.AlertView;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.OnDismissListener;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.OnItemClickListener;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.MPNetworkUtils;
import com.autodesk.shejijia.shared.components.common.utility.RegexUtil;
import com.autodesk.shejijia.shared.components.common.utility.StringUtils;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.google.gson.Gson;
import com.socks.library.KLog;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * @author Malidong .
 * @version 1.0 .
 * @date 16-6-7
 * @file FlowEstablishContractActivity.java  .
 * @brief 全流程设计合同.
 */
public class FlowEstablishContractActivity extends BaseWorkFlowActivity implements View.OnClickListener, OnItemClickListener, OnDismissListener {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_flow_establish_contract;
    }

    @Override
    protected void initView() {
        super.initView();
        /* To prevent the pop-up keyboard */
        TextView textView = (TextView) findViewById(R.id.tv_prevent_edit_text);
        textView.requestFocus();
        ll_contract = (LinearLayout) findViewById(R.id.ll_flow_examine_contract);
        btn_send = (Button) findViewById(R.id.btn_send_establish_contract);
        ll_send = (LinearLayout) findViewById(R.id.ll_send_establish_contract);
        tv_contract_number = (TextView) findViewById(R.id.flow_establish_contract_contract_number);
        tvc_consumer_name = (TextViewContent) findViewById(R.id.flow_establish_contract_consumer_name);
        tvc_consumer_phone = (TextViewContent) findViewById(R.id.flow_establish_contract_consumer_phone);
        tvc_consumer_postcode = (TextViewContent) findViewById(R.id.flow_establish_contract_consumer_postcode);
        tvc_consumer_email = (TextViewContent) findViewById(R.id.flow_establish_contract_consumer_email);
        tvc_consumer_decorate_address = (TextView) findViewById(R.id.flow_establish_contract_consumer_decorate_address);
        tvc_consumer_detail_address = (TextViewContent) findViewById(R.id.flow_establish_contract_consumer_detail_address);
        tvc_designer_name = (TextViewContent) findViewById(R.id.flow_establish_contract_designer_name);
        tvc_designer_phone = (TextViewContent) findViewById(R.id.flow_establish_contract_designer_phone);
        tvc_designer_postcode = (TextViewContent) findViewById(R.id.flow_establish_contract_designer_postcode);
        tvc_designer_email = (TextViewContent) findViewById(R.id.flow_establish_contract_designer_email);
        tvc_designer_decorate_address = (TextViewContent) findViewById(R.id.flow_establish_contract_designer_decorate_address);
        tvc_designer_detail_address = (TextViewContent) findViewById(R.id.flow_establish_contract_designer_detail_address);
        tvc_total_cost = (TextViewContent) findViewById(R.id.flow_establish_contract_consumer_total_cost);
        tvc_first_cost = (TextViewContent) findViewById(R.id.flow_establish_contract_consumer_first_cost);
        tvc_last_cost = (TextViewContent) findViewById(R.id.tvc_flow_establish_contract_consumer_last_cost);
        tvc_design_sketch = (TextViewContent) findViewById(R.id.tvc_flow_establish_contract_designer_design_sketch);
        tvc_render_map = (TextViewContent) findViewById(R.id.tvc_flow_establish_contract_designer_render_map);
        tvc_sketch_plus = (TextViewContent) findViewById(R.id.tvc_flow_establish_contract_designer_design_sketch_plus);
        ll_agree_establish_contract = (LinearLayout) findViewById(R.id.ll_agree_establish_contract);
        img_agree_establish_contract = (ImageView) findViewById(R.id.img_agree_establish_contract);

        tvc_designer_name.setEnabled(false);
        tvc_designer_phone.setEnabled(false);
        tvc_designer_email.setEnabled(false);
        tvc_designer_decorate_address.setEnabled(false);
        tvc_designer_detail_address.setEnabled(false);
    }

    /**
     * @param savedInstanceState
     * @brief 初始化信息 .
     */
    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        setTitleForNavbar(getResources().getString(R.string.design_contract)); /// 设置标题 .
    }

    /**
     * @brief 监听方法 .
     */
    @Override
    protected void initListener() {
        super.initListener();
        ll_contract.setOnClickListener(this);
        tvc_consumer_decorate_address.addTextChangedListener(new EditTextWatcher(tvc_consumer_decorate_address));
        tvc_consumer_detail_address.addTextChangedListener(new EditTextWatcher(tvc_consumer_detail_address));
        tvc_sketch_plus.addTextChangedListener(new EditTextWatcher(tvc_sketch_plus));
        tvc_first_cost.addTextChangedListener(new EditTextWatcher(tvc_first_cost));
        tvc_total_cost.addTextChangedListener(new EditTextWatcher(tvc_total_cost));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_flow_examine_contract:
                Intent intent = new Intent(FlowEstablishContractActivity.this, FlowWebContractDetailActivity.class);

                if (tvc_design_sketch.getText().toString() != null) {
                    intent.putExtra(Constant.DesignerFlowEstablishContract.DESIGNSKETCH, tvc_design_sketch.getText().toString());
                }
                if (tvc_render_map.getText().toString() != null) {
                    intent.putExtra(Constant.DesignerFlowEstablishContract.EFFECTIVEPICTURE, tvc_render_map.getText().toString());
                }
                if (tvc_sketch_plus.getText().toString() != null) {
                    intent.putExtra(Constant.DesignerFlowEstablishContract.ADDCURRENCY, tvc_sketch_plus.getText().toString());
                }
                if (tvc_total_cost.getText().toString() != null) {
                    intent.putExtra(Constant.DesignerFlowEstablishContract.DESIGNPAY, tvc_total_cost.getText().toString());
                }
                if (tvc_first_cost.getText().toString() != null) {
                    intent.putExtra(Constant.DesignerFlowEstablishContract.DESIGNFIRSTFEE, tvc_first_cost.getText().toString());
                }
                if (tvc_last_cost.getText().toString() != null) {
                    intent.putExtra(Constant.DesignerFlowEstablishContract.DESIGNBALANCEFEE, tvc_last_cost.getText().toString());
                }
                startActivity(intent);
                break;
            case R.id.flow_establish_contract_consumer_decorate_address:
                getPCDAddress();
                break;
        }
    }

    @Override
    public void onItemClick(Object o, int position) {
        CustomProgress.cancelDialog();
    }

    @Override
    protected void onWorkFlowData() {
        super.onWorkFlowData();
        /**
         * 如果超过了33节点，就隐藏上传量房按钮
         */
        if (StringUtils.isNumeric(wk_cur_sub_node_id) && Integer.valueOf(wk_cur_sub_node_id) >= 33) {
            btn_send.setVisibility(View.GONE);
            ll_agree_establish_contract.setVisibility(View.GONE);
        }

        getDesignerInfoData(designer_id, hs_uid);
        int wk_cur_sub_node_idi = Integer.valueOf(wk_cur_sub_node_id);
        if (memberEntity != null) {
            memberType = memberEntity.getMember_type();
        }

        if (Constant.UerInfoKey.DESIGNER_TYPE.equals(memberType)) { /// 设计师.
            ll_agree_establish_contract.setVisibility(View.GONE);
            tvc_last_cost.setEnabled(false);
            tvc_designer_postcode.setEnabled(false);
            if (mBidders.get(0).getDesign_contract() == null) {                         /// 如果是新的合同.
                getContractNumber();                                                                   /// 获取合同编号.
                tvc_consumer_name.setText(requirement.getContacts_name());
                tvc_consumer_phone.setText(requirement.getContacts_mobile());

                String province_name = requirement.getProvince_name();
                String city_name = requirement.getCity_name();
                String district_name = requirement.getDistrict_name();
                if ("none".equals(requirement.getDistrict())
                        || "null".equals(district_name)
                        || "none".equals(district_name)
                        || TextUtils.isEmpty(district_name)) {
                    district_name = "";
                }
                tvc_consumer_decorate_address.setText(province_name + " " + city_name + " " + district_name);
                tvc_consumer_detail_address.setText(requirement.getCommunity_name());
                if (wk_cur_sub_node_idi == 31) {                                                            /// 设计师发完合同后可以继续发送按钮显示.
                    ll_send.setVisibility(View.VISIBLE);
                    btn_send.setText(R.string.send_design_contract);
                } else if (wk_cur_sub_node_idi > 31 && wk_cur_sub_node_idi != 33) { /// 当消费者发完设计首款按钮隐藏.
                    ll_send.setVisibility(View.GONE);
                } else if (wk_cur_sub_node_idi == 33) {
                    ll_send.setVisibility(View.VISIBLE);
                    btn_send.setText(R.string.uploaded_deliverable);
                } else {
                    ll_send.setVisibility(View.VISIBLE);
                    btn_send.setText(R.string.send_design_contract);
                }
            } else {                                                                                                             /// 已有合同
                if (state == Constant.WorkFlowStateKey.STEP_MATERIAL) {                  /// 从项目资料跳转.
                    setTvcCannotClickable();                                                                       /// 如果是已有的合同设置所有得按键都不可点击 .
                }
                if (wk_cur_sub_node_idi == 31) {                                                              /// 设计师发完合同后可以继续发送按钮显示 .
                    ll_send.setVisibility(View.VISIBLE);
                    btn_send.setText(R.string.send_design_contract);
                } else if (wk_cur_sub_node_idi > 31 && wk_cur_sub_node_idi != 33) {   /// 当消费者发完设计首款按钮隐藏 .
                    ll_send.setVisibility(View.GONE);
                } else if (wk_cur_sub_node_idi == 33) {
                    ll_send.setVisibility(View.VISIBLE);
                    btn_send.setText(R.string.uploaded_deliverable);
                }
                MPDesignContractBean designContractEntity = mBidders.get(0).getDesign_contract();
                if (null == designContractEntity) {
                    return;
                }
                String contractData = designContractEntity.getContract_data();
                String str = contractData.toString().replace("@jr@", "\"");                  /// 由于合同内容中不能含有特殊字符，所以把“'”用@jr@代替 .
                MPContractDataBean designContractBean = GsonUtil.jsonToBean(str, MPContractDataBean.class);
                String zip = designContractBean.getZip();
                String email = designContractBean.getEmail();
                zip = (TextUtils.isEmpty(zip) || "null".equals(zip)) ? "" : zip;
                email = (TextUtils.isEmpty(email) || "null".equals(email)) ? "" : email;

                tv_contract_number.setText(designContractEntity.getContract_no());
                tvc_consumer_name.setText(designContractBean.getName());
                tvc_consumer_phone.setText(designContractBean.getMobile());
                if (!"null".equals(zip)) {
                    tvc_consumer_postcode.setText(zip);
                } else {
                    tvc_consumer_postcode.setText("");
                }
                if (!"null".equals(email)) {
                    tvc_consumer_email.setText(email);
                } else {
                    tvc_consumer_email.setText("");
                }
                tvc_consumer_decorate_address.setText(designContractBean.getAddr());
                tvc_consumer_detail_address.setText(designContractBean.getAddrDe());
                tvc_design_sketch.setText(designContractBean.getDesign_sketch());
                tvc_render_map.setText(designContractBean.getRender_map());
                tvc_sketch_plus.setText(designContractBean.getDesign_sketch_plus());

                tvc_total_cost.setText(designContractEntity.getContract_charge());
                tvc_first_cost.setText(designContractEntity.getContract_first_charge());
                Double totalCost = Double.parseDouble(designContractEntity.getContract_charge());
                Double firstCost = Double.parseDouble(designContractEntity.getContract_first_charge());
                DecimalFormat df = new DecimalFormat("#.##"); /// 保留小数点后两位 .
                tvc_last_cost.setText(df.format(totalCost - firstCost));
                /**
                 * 如果过了上传量房交付物的节点，就不可能改合同
                 */
                if (wk_cur_sub_node_idi >= 33) {
                    setTvcCannotClickable();
                }
            }

            /**
             *  发送合同 .
             */
            btn_send.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    sendEstablishContractCon();
                }
            });

        } else if (Constant.UerInfoKey.CONSUMER_TYPE.equals(memberType)) { /// 消费者 .
            setTvcCannotClickable();                                                                  /// 如果是消费者得话也不需要手动填写，都是带进去得数据，所以设计按键不可点击 .
            MPDesignContractBean designContractEntity = mBidders.get(0).getDesign_contract();
            if (null == designContractEntity) { // 如果设计师没有发送设计合同 .
                mDesignContract = new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.please_wait_designer_send_contract), null, null, new String[]{UIUtils.getString(R.string.sure)}, FlowEstablishContractActivity.this, AlertView.Style.Alert, FlowEstablishContractActivity.this).setOnDismissListener(new OnDismissListener() {
                    @Override
                    public void onDismiss(Object o) {
                        finish();
                    }
                });
                mDesignContract.show();
                return;
            }
            String contractData = designContractEntity.getContract_data();
            final String str = contractData.toString().replace("@jr@", "\""); /// 将@jr@转换成引号格式，以便读取 .
            MPContractDataBean designContractBean = GsonUtil.jsonToBean(str, MPContractDataBean.class);
            String zip = designContractBean.getZip();
            String email = designContractBean.getEmail();
            zip = TextUtils.isEmpty(zip) ? "" : zip;
            email = TextUtils.isEmpty(email) ? "" : email;

            tv_contract_number.setText(designContractEntity.getContract_no());
            tvc_consumer_name.setText(designContractBean.getName());
            tvc_consumer_phone.setText(designContractBean.getMobile());
            if (!"null".equals(zip)) {
                tvc_consumer_postcode.setText(zip);
            } else {
                tvc_consumer_postcode.setText("");
            }
            if (!"null".equals(email)) {
                tvc_consumer_email.setText(email);
            } else {
                tvc_consumer_email.setText("");
            }

            tvc_consumer_decorate_address.setText(designContractBean.getAddr());
            tvc_consumer_detail_address.setText(designContractBean.getAddrDe());
            tvc_design_sketch.setText(designContractBean.getDesign_sketch());
            tvc_render_map.setText(designContractBean.getRender_map());
            tvc_sketch_plus.setText(designContractBean.getDesign_sketch_plus());
            tvc_total_cost.setText(designContractEntity.getContract_charge());
            tvc_first_cost.setText(designContractEntity.getContract_first_charge());
            Double totalCost = Double.parseDouble(designContractEntity.getContract_charge());
            Double firstCost = Double.parseDouble(designContractEntity.getContract_first_charge());
            DecimalFormat df = new DecimalFormat("#.##"); // 保留小数点后两位
            tvc_last_cost.setText(df.format(totalCost - firstCost));

            if (wk_cur_sub_node_idi == 31) {
                ll_send.setVisibility(View.VISIBLE);
                ll_agree_establish_contract.setVisibility(View.VISIBLE);

                btn_send.setEnabled(false);
                btn_send.setBackgroundResource(R.drawable.bg_common_btn_pressed);

                img_agree_establish_contract.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (isAgree) { // 判断是否我已阅读（我已阅读）
                            img_agree_establish_contract.setBackgroundResource(R.drawable.icon_selected_unchecked);
                            btn_send.setEnabled(false);
                            btn_send.setBackgroundResource(R.drawable.bg_common_btn_pressed);
                        } else { // 判断是否我已阅读（我未阅读）
                            img_agree_establish_contract.setBackgroundResource(R.drawable.icon_selected_checked);
                            btn_send.setEnabled(true);
                            btn_send.setBackgroundResource(R.drawable.bg_common_btn_blue);
                            btn_send.setOnClickListener(new View.OnClickListener() { // 跳转到支付首款页面                              @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(FlowEstablishContractActivity.this, FlowFirstDesignActivity.class);
                                    intent.putExtra(Constant.BundleKey.BUNDLE_DESIGNER_ID, designer_id);
                                    intent.putExtra(Constant.BundleKey.BUNDLE_ASSET_NEED_ID, needs_id);
                                    intent.putExtra(Constant.BundleKey.BUNDLE_ACTION_NODE_ID, MPStatusMachine.NODE__DESIGN_FIRST_PAY);
                                    intent.putExtra(Constant.WorkFlowStateKey.JUMP_FROM_STATE, Constant.WorkFlowStateKey.STEP_FLOW);
                                    startActivityForResult(intent, ContractForFirst);
                                }
                            });
                        }
                        isAgree = !isAgree;
                    }
                });
                btn_send.setText(R.string.agree_and_send_design_first_new);
            } else if (wk_cur_sub_node_idi > 31 && wk_cur_sub_node_idi != 33) {
                ll_send.setVisibility(View.GONE);
                ll_agree_establish_contract.setVisibility(View.GONE);
            } else if (wk_cur_sub_node_idi == 33) {
                ll_send.setVisibility(View.VISIBLE);
                ll_agree_establish_contract.setVisibility(View.GONE);
                btn_send.setText(R.string.receiving_room_deliverable);
            }
        }
    }

    @Override
    public void onActivityReenter(int resultCode, Intent data) { // 判断IM跳转到合同，合同跳转支付首款后进行得操作
        super.onActivityReenter(resultCode, data);
        if (resultCode == FirstForContract) {
            finish();
        }
    }

    /**
     * 设置输入框不可点击输入
     */
    private void setTvcCannotClickable() {
        tvc_consumer_name.setEnabled(false);
        tvc_consumer_phone.setEnabled(false);
        tvc_consumer_postcode.setEnabled(false);
        tvc_consumer_email.setEnabled(false);
        tvc_consumer_decorate_address.setEnabled(false);
        tvc_consumer_detail_address.setEnabled(false);
        tvc_designer_postcode.setEnabled(false);
        tvc_design_sketch.setEnabled(false);
        tvc_render_map.setEnabled(false);
        tvc_sketch_plus.setEnabled(false);
        tvc_first_cost.setEnabled(false);
        tvc_total_cost.setEnabled(false);
        tvc_last_cost.setEnabled(false);
    }

    /**
     * 获取设计师个人信息 .
     */
    public void getDesignerInfoData(String designer_id, String hs_uid) {
        MPServerHttpManager.getInstance().getDesignerInfoData(designer_id, hs_uid, new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                String jsonString = GsonUtil.jsonToString(jsonObject);
                list = GsonUtil.jsonToBean(jsonString, DesignerInfoDetails.class);
                tvc_designer_name.setText(list.getReal_name().getReal_name());
                tvc_designer_phone.setText(list.getReal_name().getMobile_number().toString());
                tvc_designer_email.setText(list.getEmail());
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                MPNetworkUtils.logError(TAG, volleyError, true);
            }
        });
    }

    /**
     * 获取合同编号 .
     */
    public void getContractNumber() {
        OkJsonRequest.OKResponseCallback callback = new OkJsonRequest.OKResponseCallback() {

            @Override
            public void onResponse(JSONObject jsonObject) {
                contractNo = new Gson().fromJson(jsonObject.toString(), MPContractNoBean.class);
                contract_no = contractNo.getContractNO(); /// 获取合同编号 .
                tv_contract_number.setText(contract_no); /// 设置合同编号 .
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                MPNetworkUtils.logError(TAG, volleyError);
                MyToast.show(FlowEstablishContractActivity.this, R.string.failure);
            }
        };
        MPServerHttpManager.getInstance().getContractNumber(callback);
    }

    /**
     * 发送设计合同
     */
    public void sendEstablishContract(String needs_id, String Member_Type, String ACS_Token, JSONObject jsonObject) {
        OkJsonRequest.OKResponseCallback callback = new OkJsonRequest.OKResponseCallback() {

            @Override
            public void onResponse(JSONObject jsonObject) {
                KLog.d(TAG, jsonObject.toString());
                ContractState = 0;
                CustomProgress.cancelDialog();
                mDesignContract = new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.the_contract_sent_successfully), null, null, new String[]{UIUtils.getString(R.string.sure)}, FlowEstablishContractActivity.this, AlertView.Style.Alert, FlowEstablishContractActivity.this).setOnDismissListener(FlowEstablishContractActivity.this);
                mDesignContract.show();
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                MPNetworkUtils.logError(TAG, volleyError);
                CustomProgress.cancelDialog();
                mDesignContract = new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.the_contract_sent_failure), null, null, new String[]{UIUtils.getString(R.string.sure)}, FlowEstablishContractActivity.this, AlertView.Style.Alert, FlowEstablishContractActivity.this).setOnDismissListener(FlowEstablishContractActivity.this);
                mDesignContract.show();
            }
        };
        MPServerHttpManager.getInstance().sendEstablishContract(needs_id, Member_Type, ACS_Token, jsonObject, callback);
    }

    /**
     * 修改:         consumer/src/main/res/layout/activity_flow_establish_contract.xml
     *
     * @brief 发送设计合同内容 .
     */
    private void sendEstablishContractCon() {
        JSONObject jsonO = new JSONObject();
        total_cost = tvc_total_cost.getText().toString();
        first_cost = tvc_first_cost.getText().toString();

        String consumerName = tvc_consumer_name.getText().toString();
        String consumerPhone = tvc_consumer_phone.getText().toString();
        String consumerPostcode = tvc_consumer_postcode.getText().toString();
        String consumerEmail = tvc_consumer_email.getText().toString();
        String decorateAddress = tvc_consumer_decorate_address.getText().toString();
        String detailAddress = tvc_consumer_detail_address.getText().toString();
        String designSketch = tvc_design_sketch.getText().toString();
        String renderMap = tvc_render_map.getText().toString();
        String sketchPlus = tvc_sketch_plus.getText().toString();

        consumerPostcode = TextUtils.isEmpty(consumerPostcode) ? "" : consumerPostcode;
        consumerEmail = TextUtils.isEmpty(consumerEmail) ? "" : consumerEmail;
        try {
            JSONObject jsonObj = new JSONObject();
            jsonObj.put(Constant.EstablishContractKey.NAME, consumerName);
            jsonObj.put(Constant.EstablishContractKey.MOBILE, consumerPhone);
            jsonObj.put(Constant.EstablishContractKey.ZIP, consumerPostcode);
            jsonObj.put(Constant.EstablishContractKey.EMAIL, consumerEmail);
            jsonObj.put(Constant.EstablishContractKey.ADDR, decorateAddress);
            jsonObj.put(Constant.EstablishContractKey.ADDRDE, detailAddress);
            jsonObj.put(Constant.EstablishContractKey.DESIGN_SKETCH, designSketch);
            jsonObj.put(Constant.EstablishContractKey.RENDER_MAP, renderMap);
            jsonObj.put(Constant.EstablishContractKey.DESIGN_SKETCH_PLUS, sketchPlus);

            contract_no = tv_contract_number.getText().toString().trim();
            jsonO.put(Constant.EstablishContractKey.CONTRACT_NO, contract_no); // 合同编号
            jsonO.put(Constant.EstablishContractKey.CONTRACT_CHARGE, total_cost); // 设计总额
            jsonO.put(Constant.EstablishContractKey.CONTRACT_FIRST_CHARGE, first_cost); // 设计首款
            jsonO.put(Constant.EstablishContractKey.DESIGNER_ID, designer_id);
            jsonO.put(Constant.EstablishContractKey.CONTRACT_TYPE, 0);
            jsonO.put(Constant.EstablishContractKey.CONTRACT_TEMPLATE_URL, "www.baidu.com");
            jsonO.put(Constant.EstablishContractKey.CONTRACT_DATA, jsonObj.toString().replace("\"", "@jr@"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        boolean isMobile = consumerPhone.matches(RegexUtil.PHONE_REGEX);
        boolean isEmail = consumerEmail.matches(RegexUtil.EMAIL_REGEX);
        boolean isSketch = designSketch.matches(RegexUtil.POSITIVE_INTEGER_REGEX); // 验证效果图张数
        boolean isMap = renderMap.matches(RegexUtil.POSITIVE_INTEGER_REGEX); //　验证渲染图张数
        boolean isPostNum = consumerPostcode.matches(RegexUtil.POST_NUMBER_REGEX);

        if (!consumerName.isEmpty()) {

            if (isMobile) {
                if (TextUtils.isEmpty(consumerPostcode) || isPostNum) {

                    if (isEmail || consumerEmail.isEmpty()) {

                        if (!detailAddress.isEmpty() && detailAddress.length() > 2 && decorateAddress.length() < 32) {

                            if (isSketch) {

                                if (isMap) {

                                    if (!sketchPlus.isEmpty() && Double.parseDouble(sketchPlus) >= 0) {

                                        if (!total_cost.isEmpty() && Double.parseDouble(total_cost) >= 0) {

                                            sendEstablishConContent(jsonO);

                                        } else if (total_cost.isEmpty()) {
                                            showAlertView(R.string.please_fill_in_the_total_project_design);
                                        } else if (Double.parseDouble(total_cost) < 0) {
                                            showAlertView(R.string.costs_cannot_be_negative);
                                        }
                                        return;
                                    } else if (sketchPlus.isEmpty()) {
                                        showAlertView(R.string.please_fill_out_each_increases_the_cost_of_a);
                                    } else if (Double.parseDouble(sketchPlus) < 0) {
                                        showAlertView(R.string.costs_cannot_be_negative);
                                    }
                                    return;
                                }
                                showAlertView(R.string.please_fill_in_the_number_of_rendering);
                                return;
                            }
                            showAlertView(R.string.please_fill_in_the_number_of_drawing);
                            return;
                        }
                        showAlertView(R.string.detailed_address_cannot_be_empty);
                        return;
                    }
                    showAlertView(R.string.please_fill_in_the_right_phone_email);
                    return;
                }
                showAlertView(R.string.please_fill_in_the_right_post_number);
                return;
            }
            showAlertView(R.string.please_fill_in_the_right_phone_number);
            return;
        }
        showAlertView(R.string.the_name_cannot_be_empty);

    }

    /// 发送设计合同方法 .
    private void sendEstablishConContent(JSONObject jsonO) {
        /// java中计算金额 .
        BigDecimal loanAmount = new BigDecimal(total_cost); // 贷款金额
        BigDecimal interestRate = new BigDecimal("0.8"); // 利率
        BigDecimal interest = loanAmount.multiply(interestRate); // 相乘

        BigDecimal decimal = new BigDecimal(interest.toString());
        decimal = decimal.setScale(3, RoundingMode.HALF_UP); // 保留小数点后三位

        double totalCost = Double.parseDouble(total_cost);
        double discountCost = Double.parseDouble(decimal.toString());
        if (!first_cost.isEmpty() && Double.parseDouble(first_cost) >= 0) {
            double firstCost = Double.parseDouble(first_cost);

            String acsToken = null;
            if (memberEntity != null) {
                acsToken = memberEntity.getAcs_token();
            }

            if (firstCost >= discountCost && firstCost <= totalCost) {
                String measurement_fee = mBiddersEntity.getMeasurement_fee();
                measurement_fee = TextUtils.isEmpty(measurement_fee) ? "0" : measurement_fee;
                if (firstCost > Double.valueOf(measurement_fee)) {
                    CustomProgress.show(FlowEstablishContractActivity.this, UIUtils.getString(R.string.in_contract_sent), false, null);
                    sendEstablishContract(needs_id, ((memberEntity != null) ? memberEntity.getMember_type() : null), acsToken, jsonO);
                } else {
                    new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.room_first_less_than_eighty_percent_of_the_total_amount_measure_fee), null, new String[]{UIUtils.getString(R.string.sure)}, null, FlowEstablishContractActivity.this,
                            AlertView.Style.Alert, null).show();
                }
            } else if (firstCost < discountCost) {
                new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.room_first_less_than_eighty_percent_of_the_total_amount_of_design), null, new String[]{UIUtils.getString(R.string.sure)}, null, FlowEstablishContractActivity.this,
                        AlertView.Style.Alert, null).show();
            } else {
                new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.total_quantity_room_first_is_not_greater_than_design), null, new String[]{UIUtils.getString(R.string.sure)}, null, FlowEstablishContractActivity.this,
                        AlertView.Style.Alert, null).show();
            }
        } else if (first_cost.isEmpty()) {
            new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.please_fill_out_the_design_first), null, new String[]{UIUtils.getString(R.string.sure)}, null, FlowEstablishContractActivity.this, AlertView.Style.Alert, null).show();
        } else if (Double.parseDouble(first_cost) < 0) {
            new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.costs_cannot_be_negative), null, new String[]{UIUtils.getString(R.string.sure)}, null, FlowEstablishContractActivity.this, AlertView.Style.Alert, null).show();
        }
    }

    /**
     * @brief 获取省市区地址 .
     */
    private void getPCDAddress() {
        mChangeAddressDialog = new AddressDialog();
        mChangeAddressDialog.show(getFragmentManager(), "mChangeAddressDialog");
        mChangeAddressDialog
                .setAddressListener(new AddressDialog.OnAddressCListener() {
                    @Override
                    public void onClick(String province, String provinceCode, String city, String cityCode, String area, String areaCode) {
                        if ("null".equals(area)
                                || "none".equals(area)
                                || TextUtils.isEmpty(area)) {
                            area = "";
                        }
                        tvc_consumer_decorate_address.setText(province + city + area);
                        mChangeAddressDialog.dismiss();
                    }

                });
    }

    /**
     * 监听EditText内容变化的内部类 .
     */
    public class EditTextWatcher implements TextWatcher {
        private View view;

        public EditTextWatcher(View view) {
            this.view = view;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (view == tvc_consumer_decorate_address) { /// 监听装修地址 .
                tvc_designer_decorate_address.setText(s);
            }
            if (view == tvc_consumer_detail_address) {
                tvc_designer_detail_address.setText(s);
            }
            if (view == tvc_total_cost) { /// 监听总款 .
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0, s.toString().indexOf(".") + 3);
                        tvc_total_cost.setText(s);
                        tvc_total_cost.setSelection(s.length());
                    }
                }

                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    tvc_total_cost.setText(s);
                    tvc_total_cost.setSelection(2);
                }

                if (s.toString().startsWith("0") && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        tvc_total_cost.setText(s.subSequence(0, 1));
                        tvc_total_cost.setSelection(1);
                        return;
                    }
                }
            }
            if (view == tvc_first_cost) { /// 监听首款 .
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + 3);
                        tvc_first_cost.setText(s);
                        tvc_first_cost.setSelection(s.length());
                    }
                }

                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    tvc_first_cost.setText(s);
                    tvc_first_cost.setSelection(2);
                }

                if (s.toString().startsWith("0") && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        tvc_first_cost.setText(s.subSequence(0, 1));
                        tvc_first_cost.setSelection(1);
                        return;
                    }
                }

                String firstCost;
                String totalCost;
                if (tvc_total_cost.getText().toString().isEmpty()) {
                    totalCost = "0";
                } else {
                    totalCost = tvc_total_cost.getText().toString();
                }
                if (tvc_first_cost.getText().toString().isEmpty()) {
                    firstCost = "0";
                } else {
                    firstCost = s.toString();
                }
                Double dTotalCost = Double.parseDouble(totalCost);
                DecimalFormat df = new DecimalFormat("#.##"); // 保留小数点后两位
                tvc_last_cost.setText(df.format((dTotalCost - Double.parseDouble(firstCost))));
            }
            if (view == tvc_sketch_plus) {
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + 3);
                        tvc_sketch_plus.setText(s);
                        tvc_sketch_plus.setSelection(s.length());
                    }
                }

                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    tvc_sketch_plus.setText(s);
                    tvc_sketch_plus.setSelection(2);
                }

                if (s.toString().startsWith("0") && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        tvc_sketch_plus.setText(s.subSequence(0, 1));
                        tvc_sketch_plus.setSelection(1);
                        return;
                    }
                }
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    }

    @Override
    public void onDismiss(Object o) {
        if (ContractState == 0) {
            finish();
        }
    }

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

    /**
     * 隐藏软键盘 .
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

    private void showAlertView(int content) {
        new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(content), null, new String[]{UIUtils.getString(R.string.sure)}, null, FlowEstablishContractActivity.this,
                AlertView.Style.Alert, null).show();
    }

    private TextViewContent tvc_consumer_name;
    private TextViewContent tvc_consumer_phone;
    private TextViewContent tvc_consumer_postcode;
    private TextViewContent tvc_consumer_email;
    private TextViewContent tvc_consumer_detail_address;
    private TextViewContent tvc_design_sketch;
    private TextViewContent tvc_render_map;
    private TextViewContent tvc_sketch_plus;
    private TextViewContent tvc_designer_name;
    private TextViewContent tvc_designer_phone;
    private TextViewContent tvc_designer_postcode;
    private TextViewContent tvc_designer_email;
    private TextViewContent tvc_designer_decorate_address;
    private TextViewContent tvc_designer_detail_address;
    private TextViewContent tvc_total_cost;
    private TextViewContent tvc_first_cost;
    private TextViewContent tvc_last_cost;
    private TextView tv_contract_number;
    private TextView tvc_consumer_decorate_address;
    private ImageView img_agree_establish_contract;
    private Button btn_send;
    private LinearLayout ll_contract;
    private LinearLayout ll_send;
    private LinearLayout ll_agree_establish_contract;

    private DesignerInfoDetails list;
    private MPContractNoBean contractNo; // 设计合同编号对象
    private AddressDialog mChangeAddressDialog;
    private AlertView mDesignContract;

    private String contract_no; // 设计合同编号
    private String total_cost;
    private String first_cost;
    private String memberType = null;
    private int ContractState = -1; // 判断合同是否发送成功弹出框的点击事件
    private int ContractForFirst = 0; //　从合同跳转到设计首款
    private int FirstForContract = 1; // 首款调到设计合同
    private boolean isAgree = false;
}
