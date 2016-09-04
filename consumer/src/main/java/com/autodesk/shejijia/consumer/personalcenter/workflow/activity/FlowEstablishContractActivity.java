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
import android.webkit.JavascriptInterface;
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
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
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
import com.autodesk.shejijia.consumer.utils.AppDataFormatValidator.MPDesignFormatValidator;
import com.autodesk.shejijia.shared.components.im.constants.BroadCastInfo;
import com.autodesk.shejijia.shared.framework.AdskApplication;
import com.google.gson.Gson;
import com.socks.library.KLog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLDecoder;
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
        return R.layout.activity_flow_design_contract;
    }

    /**
     * @param savedInstanceState
     * @brief 初始化信息 .
     */
    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);


    }

    @Override
    protected void initView() {
        super.initView();

        setTitleForNavbar(getResources().getString(R.string.design_contract)); /// 设置标题 .

        initViewVariables();
        initWebViewConfig();


        tvc_designer_name.setEnabled(false);
        tvc_designer_phone.setEnabled(false);
        tvc_designer_email.setEnabled(false);
        tvc_designer_decorate_address.setEnabled(false);



        /* To prevent the pop-up keyboard */
        TextView textView = (TextView) findViewById(R.id.tv_prevent_edit_text);
        textView.requestFocus();
    }

    protected void initViewVariables() {

         /* init content for designer form */
        // Layout
        ll_contract = (LinearLayout) findViewById(R.id.ll_flow_examine_contract);
        ll_send = (LinearLayout) findViewById(R.id.ll_send_establish_contract);
        ll_agree_establish_contract = (LinearLayout) findViewById(R.id.ll_agree_establish_contract);
        ll_contract_content_designer = (LinearLayout) findViewById(R.id.design_contract_content_designer);
        ll_contract_content_consumer = (LinearLayout) findViewById(R.id.design_contract_content_consumer);
        /////

        //Variables
        btn_send = (Button) findViewById(R.id.btn_send_establish_contract);
        tv_contract_number = (TextView) findViewById(R.id.flow_establish_contract_contract_number);
        tvc_consumer_name = (TextViewContent) findViewById(R.id.flow_establish_contract_consumer_name);
        tvc_consumer_phone = (TextViewContent) findViewById(R.id.flow_establish_contract_consumer_phone);
        tvc_consumer_email = (TextViewContent) findViewById(R.id.flow_establish_contract_consumer_email);
        tvc_consumer_detail_address = (TextViewContent) findViewById(R.id.flow_establish_contract_consumer_detail_address);
        tvc_designer_name = (TextViewContent) findViewById(R.id.flow_establish_contract_designer_name);
        tvc_designer_phone = (TextViewContent) findViewById(R.id.flow_establish_contract_designer_phone);
        tvc_designer_email = (TextViewContent) findViewById(R.id.flow_establish_contract_designer_email);
        tvc_designer_decorate_address = (TextViewContent) findViewById(R.id.flow_establish_contract_designer_decorate_address);
        tvc_total_cost = (TextViewContent) findViewById(R.id.flow_establish_contract_consumer_total_cost);
        tvc_first_cost = (TextViewContent) findViewById(R.id.flow_establish_contract_consumer_first_cost);
        tvc_last_cost = (TextViewContent) findViewById(R.id.tvc_flow_establish_contract_consumer_last_cost);
        img_agree_establish_contract = (ImageView) findViewById(R.id.img_agree_establish_contract);
        tvc_treeD_render_count = (TextViewContent) findViewById(R.id.tvc_flow_establish_contract_designer_render_map);
        tvc_consumer_community_name= (TextViewContent) findViewById(R.id.flow_contract_community_name);
        tvc_consumer_local_area= (TextView) findViewById(R.id.flow_establish_contract_consumer_decorate_address);

         /* init content for consumer form */
        twvc_consumerContent = (WebView) findViewById(R.id.contract_sub_content_webview);


    }

    private void initWebViewConfig(){

        WebSettings webSettings = twvc_consumerContent.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowContentAccess(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setDefaultTextEncodingName(Constant.NetBundleKey.UTF_8);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDefaultTextEncodingName("utf-8");

        twvc_consumerContent.setWebViewClient(new webViewClient());
        twvc_consumerContent.addJavascriptInterface(new WebViewJavaScriptCallback(), ANDROID);
    }

    private void UpdateUIlayoutContract()
    {
        UpdateUIlayoutContractContent();

        if (isRoleDesigner()) { /// 设计师　.
            UpdateUIcontractContentDesigner();
        } else if (isRoleCustomer()) { /// 消费者 .
            UpdateUIcontractContentConsumer();
        }

    }

    private void UpdateUIlayoutContractContent() {
        if ( isRoleDesigner()){
            ll_contract_content_consumer.setVisibility(View.GONE);
            ll_contract_content_designer.setVisibility(View.VISIBLE);
        }
        if ( isRoleCustomer()){
            ll_contract_content_designer.setVisibility(View.GONE);
            ll_contract_content_consumer.setVisibility(View.VISIBLE);
        }

    }

    private void UpdateUIcontractContentDesigner() {
        ll_agree_establish_contract.setVisibility(View.GONE);
        tvc_last_cost.setEnabled(false);


        if (getContractDataEntityFromFirstBidder() == null) {                         /// 如果是新的合同.
            AsyncGetContractNumber(new commonJsonResponseCallback(){
                @Override
                public void onJsonResponse(String jsonResponse) {
                    contractNo = new Gson().fromJson(jsonResponse.toString(), MPContractNoBean.class);
                    contract_no = contractNo.getContractNO(); /// 获取合同编号 .
                    tv_contract_number.setText(contract_no); /// 设置合同编号 .
                }

                @Override
                public void onError(VolleyError volleyError) {
                    MPNetworkUtils.logError(TAG, volleyError);
                    MyToast.show(FlowEstablishContractActivity.this, R.string.failure);
                }
            });                                                                   /// 获取合同编号.

            tvc_designer_name.setText(designer_name);
            tvc_designer_phone.setText(designer_mobile);
            tvc_designer_email.setText(designer_mail);

            tvc_consumer_name.setText(requirement.getContacts_name());
            tvc_consumer_phone.setText(requirement.getContacts_mobile());

            String province_name = requirement.getProvince_name();
            String city_name = requirement.getCity_name();
            String district_name = requirement.getDistrict_name();



            String locationArea=Validator.getStrProvinceCityDistrict(province_name,city_name,district_name);
            //  tvc_consumer_decorate_address.setText(province_name + " " + city_name + " " + district_name);

            String Community_name=Validator.getStringWithNullDefaultString(requirement.getCommunity_name(), UIUtils.getString(R.string.data_null));
            tvc_consumer_community_name.setText(Community_name);


            //tvc_consumer_detail_address.setText(requirement.);
            //tvc_designer_decorate_address.setText(Community_name);

            if (WorkFlowSubNodeStep() == 31) {                                                            /// 设计师发完合同后可以继续发送按钮显示.
                ll_send.setVisibility(View.VISIBLE);
                btn_send.setText(R.string.send_design_contract);
            } else if (WorkFlowSubNodeStep() > 31 && WorkFlowSubNodeStep() != 33) { /// 当消费者发完设计首款按钮隐藏.
                ll_send.setVisibility(View.GONE);
            } else if (WorkFlowSubNodeStep() == 33) {
                ll_send.setVisibility(View.VISIBLE);
                btn_send.setText(R.string.uploaded_deliverable);
            } else {
                ll_send.setVisibility(View.VISIBLE);
                btn_send.setText(R.string.send_design_contract);
            }
        } else {  /// 已有合同
            if (state == Constant.WorkFlowStateKey.STEP_MATERIAL) {// 从项目资料跳转.
                UpdateUIdisableFieldsInput();  /// 如果是已有的合同设置所有得按键都不可点击 .
            }
            if (WorkFlowSubNodeStep() == 31) { /// 设计师发完合同后可以继续发送按钮显示 .
                ll_send.setVisibility(View.VISIBLE);
                btn_send.setText(R.string.send_design_contract);
            } else if (WorkFlowSubNodeStep() > 31 && WorkFlowSubNodeStep() != 33) {   /// 当消费者发完设计首款按钮隐藏 .
                ll_send.setVisibility(View.GONE);
            } else if (WorkFlowSubNodeStep() == 33) {
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
            String email = Validator.getStringWithNullDefaultEmpty(designContractBean.getEmail());
            zip = (TextUtils.isEmpty(zip) || "null".equals(zip)) ? "" : zip;


            tv_contract_number.setText(designContractEntity.getContract_no());
            tvc_consumer_name.setText(designContractBean.getName());
            tvc_consumer_phone.setText(designContractBean.getMobile());


            //tvc_consumer_decorate_address.setText(designContractBean.getAddr());
            tvc_consumer_detail_address.setText(designContractBean.getAddrDe());
            //tvc_design_sketch.setText(designContractBean.getDesign_sketch());

            tvc_total_cost.setText(designContractEntity.getContract_charge());
            tvc_first_cost.setText(designContractEntity.getContract_first_charge());
            Double totalCost = Double.parseDouble(designContractEntity.getContract_charge());
            Double firstCost = Double.parseDouble(designContractEntity.getContract_first_charge());
            DecimalFormat df = new DecimalFormat("#.##"); /// 保留小数点后两位 .
            tvc_last_cost.setText(df.format(totalCost - firstCost));
            /**
             * 如果过了上传量房交付物的节点，就不可能改合同
             */
            if (WorkFlowSubNodeStep() >= 33) {
                UpdateUIdisableFieldsInput();
            }
        }



    }

    private void UpdateUIcontractContentConsumer() {
        UpdateUIsetContentViewConsumer();

        if (WorkFlowSubNodeStep() == 31) {
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
                                intent.putExtra(Constant.SeekDesignerDetailKey.DESIGNER_ID, designer_id);
                                intent.putExtra(Constant.SeekDesignerDetailKey.NEEDS_ID, needs_id);
                                intent.putExtra(Constant.BundleKey.TEMPDATE_ID, MPStatusMachine.NODE__DESIGN_FIRST_PAY);
//                                    intent.putExtra(Constant.WorkFlowStateKey.JUMP_FROM_STATE, Constant.WorkFlowStateKey.STEP_FLOW);
                                startActivityForResult(intent, ContractForFirst);
                            }
                        });
                    }

                    isAgree = !isAgree;
                }
            });
            btn_send.setText(R.string.agree_and_send_design_first_new);
        } else if (WorkFlowSubNodeStep() > 31 && WorkFlowSubNodeStep() != 33) {
            ll_send.setVisibility(View.GONE);
            ll_agree_establish_contract.setVisibility(View.GONE);
        } else if (WorkFlowSubNodeStep() == 33) {
            ll_send.setVisibility(View.VISIBLE);
            ll_agree_establish_contract.setVisibility(View.GONE);
            btn_send.setText(R.string.receiving_room_deliverable);
        }
    }

    private void UpdateUIsetContentViewConsumer() {

        try {
            //Return an AssetManager instance for your application's package
            InputStream is = getAssets().open("contract_content_consumer.txt");
            int size = is.available();

            // Read the entire asset into a local byte buffer.
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            String text = new String(buffer, Constant.NetBundleKey.UTF_8);



            while (true)
            {
                text = text.replace("#val(designer_name)", designer_name);
                text = text.replace("#val(designer_mobile)", designer_mobile);
                text = text.replace("#val(designer_mail)", designer_mail);

                if  (contract_data_entity==null)
                    break;

                MPContractDataBean contract_detail=getContractDetailData(contract_data_entity);
                if  (contract_detail==null)
                    break;

                String contract_number=contract_data_entity.getContract_no();
                String contract_date=contract_data_entity.getContract_create_date();
                String design_amount=contract_data_entity.getContract_charge();
                String design_amount_first=contract_data_entity.getContract_first_charge();


                Double totalCost = Double.parseDouble(design_amount);
                Double firstCost = Double.parseDouble(design_amount_first);
                DecimalFormat df = new DecimalFormat("#.##"); /// 保留小数点后两位 .
                String design_amount_balance= df.format(totalCost - firstCost);


                String consumer_name=contract_detail.getName();
                String consumer_mobile=contract_detail.getMobile();
                String consumer_addr=contract_detail.getAddrDe();
                String consumer_mail=contract_detail.getEmail();
                String tree_d_renderimage=contract_detail.getRender_map();
                String designer_addr=contract_detail.getAddrDe();

                text = text.replace("#val(contract_number)", Validator.getStringWithNullDefaultString(contract_number,UIUtils.getString(R.string.data_null)));
                text = text.replace("#val(contract_date)", Validator.getStringWithNullDefaultString(contract_date,UIUtils.getString(R.string.data_null)));
                text = text.replace("#val(design_amount)", Validator.getStringWithNullDefaultString(design_amount,UIUtils.getString(R.string.data_null)));
                text = text.replace("#val(design_amount_first)", Validator.getStringWithNullDefaultString(design_amount_first,UIUtils.getString(R.string.data_null)));
                text = text.replace("#val(design_amount_balance)", Validator.getStringWithNullDefaultString(design_amount_balance,UIUtils.getString(R.string.data_null)));
                text = text.replace("#val(consumer_name)", Validator.getStringWithNullDefaultString(consumer_name,UIUtils.getString(R.string.data_null)));
                text = text.replace("#val(consumer_mobile)", Validator.getStringWithNullDefaultString(consumer_mobile,UIUtils.getString(R.string.data_null)));
                text = text.replace("#val(consumer_addr)", Validator.getStringWithNullDefaultString(consumer_addr,UIUtils.getString(R.string.data_null)));
                text = text.replace("#val(consumer_mail)", Validator.getStringWithNullDefaultString(consumer_mail,UIUtils.getString(R.string.data_null)));
                text = text.replace("#val(tree_d_renderimage)", Validator.getStringWithNullDefaultString(tree_d_renderimage,UIUtils.getString(R.string.data_null)));
                text = text.replace("#val(designer_addr)", Validator.getStringWithNullDefaultString(designer_addr,UIUtils.getString(R.string.data_null)));
                break;
            };
            twvc_consumerContent.loadDataWithBaseURL(null, text, Constant.NetBundleKey.MIME_TYPE_TEXT_HTML, Constant.NetBundleKey.UTF_8, "");

        } catch (IOException e) {
            // Should never happen!
            throw new RuntimeException(e);
        }
    }

    /**
     * 设置输入框不可点击输入且无边框
     */

    private void UpdateUIdisableFieldsInput() {
        tvc_consumer_name.setEnabled(false);
        tvc_consumer_phone.setEnabled(false);
        //tvc_consumer_postcode.setEnabled(false);
        tvc_consumer_email.setEnabled(false);
        //tvc_consumer_decorate_address.setEnabled(false);
        tvc_consumer_detail_address.setEnabled(false);
        //tvc_designer_postcode.setEnabled(false);
        //tvc_design_sketch.setEnabled(false);
        //tvc_render_map.setEnabled(false);
        //tvc_sketch_plus.setEnabled(false);
        tvc_first_cost.setEnabled(false);
        tvc_total_cost.setEnabled(false);
        tvc_last_cost.setEnabled(false);
        tvc_consumer_name.setBackgroundResource(0);
        tvc_consumer_phone.setBackgroundResource(0);
        //tvc_consumer_postcode.setBackgroundResource(0);
        tvc_consumer_email.setBackgroundResource(0);
        //tvc_consumer_decorate_address.setBackgroundResource(0);
        tvc_consumer_detail_address.setBackgroundResource(0);
        //tvc_designer_postcode.setBackgroundResource(0);
        //tvc_design_sketch.setBackgroundResource(0);
        //tvc_render_map.setBackgroundResource(0);
        //tvc_sketch_plus.setBackgroundResource(0);
        tvc_first_cost.setBackgroundResource(0);
        tvc_total_cost.setBackgroundResource(0);
        tvc_last_cost.setBackgroundResource(0);
        tvc_designer_name.setBackgroundResource(0);
        tvc_designer_decorate_address.setBackgroundResource(0);
        //tvc_designer_detail_address.setBackgroundResource(0);
        tvc_designer_email.setBackgroundResource(0);
        tvc_designer_phone.setBackgroundResource(0);
    }

    /**
     * @brief 监听方法 .
     */
    @Override
    protected void initListener() {
        super.initListener();
        ll_contract.setOnClickListener(this);
        tvc_consumer_local_area.setOnClickListener(this);
        btn_send.setOnClickListener(this);
        tvc_consumer_detail_address.addTextChangedListener(new EditTextWatcher(tvc_consumer_detail_address));
        tvc_first_cost.addTextChangedListener(new EditTextWatcher(tvc_first_cost));
        tvc_total_cost.addTextChangedListener(new EditTextWatcher(tvc_total_cost));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_flow_examine_contract:
                Intent intent = new Intent(FlowEstablishContractActivity.this, FlowWebContractDetailActivity.class);
                startActivity(intent);
                break;
            case R.id.flow_establish_contract_consumer_decorate_address:
                getPCDAddress();
                break;
            case R.id.btn_send_establish_contract:
                if (checkValidFormInputWithErrorAlertView())
                    submitDesignContract();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == FirstForContract) {
            finish();
        }
    }

    @Override
    public void onItemClick(Object o, int position) {
        CustomProgress.cancelDialog();
    }

    private void doClickOpenContractDetail()
    {
        Intent intent = new Intent(FlowEstablishContractActivity.this, FlowWebContractDetailActivity.class);
        startActivity(intent);

    }

    // work flow related
    @Override
    protected void onWorkFlowData() {
        super.onWorkFlowData();
        /**
         * 如果超过了33节点，就隐藏上传合同按钮
         */

        //Here we can determine if we already have a contract
        contract_data_entity=getContractDataEntityFromFirstBidder();
        if  (contract_data_entity==null)
        {
            if(isRoleCustomer())
            {

            }
        }

        if (StringUtils.isNumeric(wk_cur_sub_node_id) && Integer.valueOf(wk_cur_sub_node_id) >= 33) {
            btn_send.setVisibility(View.GONE);
            ll_agree_establish_contract.setVisibility(View.GONE);
        }

        //get designer infomation
        restgetDesignerInfoData(designer_id, hs_uid,new commonJsonResponseCallback(){
            @Override
            public void onJsonResponse(String jsonResponse) {
                list = GsonUtil.jsonToBean(jsonResponse, DesignerInfoDetails.class);
                designer_name=list.getReal_name().getReal_name();
                designer_mobile=list.getReal_name().getMobile_number().toString();
                designer_mail=list.getEmail();
                UpdateUIlayoutContract();

            }

            @Override
            public void onError(VolleyError volleyError) {

            }
        });



        UpdateUIlayoutContract();
    }




    //contract data method
    private MPDesignContractBean getContractDataEntityFromFirstBidder() {
        MPDesignContractBean designContractEntity = mBidders.get(0).getDesign_contract();
        if (null == designContractEntity) // 如果设计师没有发送设计合同 .
            return null;

        return designContractEntity;
    }

    private MPContractDataBean getContractDetailData(MPDesignContractBean contractEntity) {
        String contractData = contractEntity.getContract_data();
        final String str = contractData.toString().replace("@jr@", "\""); /// 将@jr@转换成引号格式，以便读取 .
        MPContractDataBean designContractDetail = GsonUtil.jsonToBean(str, MPContractDataBean.class);

        return designContractDetail;
    }



    //Async method
    public void AsyncGetContractNumber(final commonJsonResponseCallback callBack) {
        MPServerHttpManager.getInstance().getContractNumber(new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                String jsonString = GsonUtil.jsonToString(jsonObject);
                callBack.onJsonResponse(jsonString);

            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                MPNetworkUtils.logError(TAG, volleyError, true);
                callBack.onError(volleyError);
            }
        });
    }

    /**
     * 发送设计合同
     */
    //JSONObject jsonO = new JSONObject();
    public void sendEstablishContract(String needs_id, String Member_Type , JSONObject jsonObject) {
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
        MPServerHttpManager.getInstance().sendEstablishContract(needs_id, Member_Type, jsonObject, callback);
    }

    /**
     * 修改:         consumer/src/main/res/layout/activity_flow_establish_contract.xml
     *
     * @brief 发送设计合同内容 .
     */
    private boolean checkValidFormInputWithErrorAlertView() {

        String total_cost = tvc_total_cost.getText().toString();
        String first_cost = tvc_first_cost.getText().toString();

        String consumerName = tvc_consumer_name.getText().toString();
        String consumerPhone = tvc_consumer_phone.getText().toString();
        String consumerEmail = tvc_consumer_email.getText().toString();
        String detailAddress = tvc_consumer_detail_address.getText().toString();
        String renderCount = tvc_treeD_render_count.getText().toString();
        String location_area = tvc_consumer_local_area.getText().toString();
        String communityName = tvc_consumer_community_name.getText().toString();
        boolean bValid=true;

        while(true)
        {
            if (!Validator.isNameValid(consumerName))
            {showAlertView(R.string.no_input_name);  bValid =false; break;}

            if (!Validator.isMobileValid(consumerPhone))
            {showAlertView(R.string.please_fill_in_the_right_phone_number);  bValid =false; break;}

            if (!Validator.isEmailValid(consumerEmail))
            {showAlertView(R.string.please_fill_in_the_right_phone_email);  bValid =false; break;}

            if (!Validator.isAddressValid(location_area))
            {showAlertView(R.string.demand_please_address);  bValid =false; break;}


            if (!Validator.isAddressValid(communityName))
            {showAlertView(R.string.please_fill_detailed_address);  bValid =false; break;}

            if (!Validator.isAddressValid(detailAddress))
            {showAlertView(R.string.please_enter_correct_address);  bValid =false; break;}

            if (!Validator.isStringPositiveNumberValid(renderCount))
            {showAlertView(R.string.please_fill_in_the_number_of_rendering);  bValid =false; break;}

            if (!Validator.isStringValid(total_cost)){
                new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.please_fill_in_the_total_project_design), null, new String[]{UIUtils.getString(R.string.sure)}, null, FlowEstablishContractActivity.this, AlertView.Style.Alert, null).show();
                bValid = false; break;
            }

            if (!Validator.isStringValid(first_cost)){
                new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.please_fill_out_the_design_first), null, new String[]{UIUtils.getString(R.string.sure)}, null, FlowEstablishContractActivity.this, AlertView.Style.Alert, null).show();
                bValid = false; break;
            }

            if (!checkAmontContractValidwithResult(mBiddersEntity.getMeasurement_fee(),total_cost,first_cost)){
                bValid =false; break;
            }

            break;
        }

        return bValid;
    }

    public boolean  checkAmontContractValidwithResult(String meansurePrice,String total,String downpay)
    {
        String result;
        String msg;
        String balanceStrVal;


        BigDecimal loanAmount = new BigDecimal(total); //
        BigDecimal interestRate = new BigDecimal("0.8"); //
        BigDecimal interest = loanAmount.multiply(interestRate); //

        BigDecimal decimal = new BigDecimal(interest.toString());
        decimal = decimal.setScale(3, RoundingMode.HALF_UP); // 保留小数点后三位

        double totalCost = Double.parseDouble(total);
        double discountCost = Double.parseDouble(decimal.toString());
        double firstCost = Double.parseDouble(downpay);

        boolean bValid=true;

        while(true)
        {
            if (!Validator.isStringValid(downpay)){
                new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.please_fill_out_the_design_first), null, new String[]{UIUtils.getString(R.string.sure)}, null, FlowEstablishContractActivity.this, AlertView.Style.Alert, null).show();
                bValid = false; break;
            }


            if (!Validator.isStringValid(downpay)){
                new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.costs_cannot_be_negative), null, new String[]{UIUtils.getString(R.string.sure)}, null, FlowEstablishContractActivity.this, AlertView.Style.Alert, null).show();
                bValid = false; break;
            }

            if (firstCost < discountCost){
                new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.room_first_less_than_eighty_percent_of_the_total_amount_of_design), null, new String[]{UIUtils.getString(R.string.sure)}, null, FlowEstablishContractActivity.this,
                        AlertView.Style.Alert, null).show();
                bValid = false; break;
            }

            if (firstCost > totalCost){
                new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.total_quantity_room_first_is_not_greater_than_design), null, new String[]{UIUtils.getString(R.string.sure)}, null, FlowEstablishContractActivity.this,
                        AlertView.Style.Alert, null).show();
                bValid = false; break;
            }

            if (firstCost < Double.valueOf(meansurePrice)){
                new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.room_first_less_than_eighty_percent_of_the_total_amount_measure_fee), null, new String[]{UIUtils.getString(R.string.sure)}, null, FlowEstablishContractActivity.this,
                        AlertView.Style.Alert, null).show();
                bValid = false; break;
            }
            break;
        }



        return bValid;
    }
    /// 发送设计合同方法 .
    private void submitDesignContract() {

        JSONObject jsonO = new JSONObject();
        String total_cost = tvc_total_cost.getText().toString();
        String first_cost = tvc_first_cost.getText().toString();

        String consumerName = tvc_consumer_name.getText().toString();
        String consumerPhone = tvc_consumer_phone.getText().toString();
        String consumerEmail = tvc_consumer_email.getText().toString();
        String detailAddress = tvc_consumer_detail_address.getText().toString();
        String renderCount = tvc_treeD_render_count.getText().toString();
        String location_area = tvc_consumer_local_area.getText().toString();
        String communityName = tvc_consumer_community_name.getText().toString();

        try {
            JSONObject jsonObj = new JSONObject();
            jsonObj.put(Constant.EstablishContractKey.NAME, consumerName);
            jsonObj.put(Constant.EstablishContractKey.MOBILE, consumerPhone);
            jsonObj.put(Constant.EstablishContractKey.EMAIL, consumerEmail);
            jsonObj.put(Constant.EstablishContractKey.ADDRDE, detailAddress);
            jsonObj.put(Constant.EstablishContractKey.RENDER_MAP, renderCount);


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

        CustomProgress.show(FlowEstablishContractActivity.this, UIUtils.getString(R.string.in_contract_sent), false, null);
        sendEstablishContract(needs_id, ((memberEntity != null) ? memberEntity.getMember_type() : null), jsonO);

    }

    //Web view client
    private class webViewClient extends WebViewClient {
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    private final class WebViewJavaScriptCallback {

        @JavascriptInterface
        public void onClickContractDetail() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    doClickOpenContractDetail();
                    //mWebView.loadUrl("javascript: showFromHtml2('IT-homer blog')");
                    //Toast.makeText(JSAndroidActivity.this, "clickBtn2", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }



    ///others
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

                        area=Validator.getStrProvinceCityDistrict(province,city,area);
                        tvc_consumer_local_area.setText(area);
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



            if (view == tvc_consumer_detail_address){

                tvc_designer_decorate_address.setText(tvc_consumer_detail_address.getText());


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




    MPDesignFormatValidator Validator=new MPDesignFormatValidator();

    private static final String ANDROID = "android";
    private TextViewContent tvc_consumer_name;
    private TextViewContent tvc_consumer_phone;
    private TextViewContent tvc_consumer_email;
    private TextViewContent tvc_consumer_detail_address;
    private TextViewContent tvc_treeD_render_count;
    private TextViewContent tvc_designer_name;
    private TextViewContent tvc_designer_phone;
    private TextViewContent tvc_designer_email;
    private TextViewContent tvc_designer_decorate_address;
    private TextView tvc_consumer_local_area;
    private TextViewContent tvc_consumer_community_name;

    private TextViewContent tvc_total_cost;
    private TextViewContent tvc_first_cost;
    private TextViewContent tvc_last_cost;
    private TextView tv_contract_number;
    private ImageView img_agree_establish_contract;
    private Button btn_send;
    private LinearLayout ll_contract;
    private LinearLayout ll_send;
    private LinearLayout ll_agree_establish_contract;
    private LinearLayout ll_contract_content_designer;
    private LinearLayout ll_contract_content_consumer;

    private DesignerInfoDetails list;
    private MPContractNoBean contractNo; // 设计合同编号对象
    private AddressDialog mChangeAddressDialog;
    private AlertView mDesignContract;
    private WebView twvc_consumerContent;

    private String contract_no; // 设计合同编号
    //private String total_cost;
    //private String first_cost;
    private String memberType = null;
    private int ContractState = -1; // 判断合同是否发送成功弹出框的点击事件
    private int ContractForFirst = 0; //　从合同跳转到设计首款
    private int FirstForContract = 1; // 首款调到设计合同
    private boolean isAgree = false;

    private MPDesignContractBean contract_data_entity;

    private String designer_name="";
    private String designer_mobile="";
    private String designer_mail="";

}
