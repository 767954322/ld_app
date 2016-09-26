package com.autodesk.shejijia.consumer.personalcenter.workflow.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.utils.MPStatusMachine;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.uielements.CustomProgress;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.AlertView;
import com.autodesk.shejijia.shared.framework.activity.NavigationBarActivity;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Malidong .
 * @version 1.0 .
 * @date 16-6-7
 * @file FlowWebContractDetailActivity.java  .
 * @brief 设计合同Web展示 .
 */
public class FlowWebContractDetailActivity extends NavigationBarActivity {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_flow_design_contract;
    }

    private AlertView UIAlert;

    protected void leftNavButtonClicked(View view) {

        SetReturnSesult(ContractDetail);
        super.leftNavButtonClicked(view);
    }

    @Override
    protected void initExtraBundle() {
        super.initExtraBundle();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        needs_id = bundle.getString(Constant.SeekDesignerDetailKey.NEEDS_ID);
        contract_number = intent.getStringExtra(Constant.SeekDesignerDetailKey.CONTRACT_NO);
        designer_id = bundle.getString(Constant.SeekDesignerDetailKey.DESIGNER_ID);
        bconsumerActionShow = bundle.getBoolean("CONSUMER_ACTION_SHOW");
        isAgree = bundle.getBoolean("CONSUMER_ACTION_AGREE");
    }

    @Override
    protected void initView() {
        initViewVariables();

        ll_designer_action_layout.setVisibility(View.GONE);
        ll_contract_input_form_layout.setVisibility(View.GONE);

        ll_consumer_action_layout.setVisibility(View.GONE);
        ll_contract_webview_layout.setVisibility(View.VISIBLE);
        ll_consumer_agree_content_layout.setVisibility(View.VISIBLE);

        img_agree_establish_contract = (ImageView) findViewById(R.id.img_agree_establish_contract);
    }

    protected void initViewVariables() {

        ll_designer_action_layout = (LinearLayout) findViewById(R.id.ll_flow_designer_send_contract); //設計師發送合同的layout
        ll_contract_input_form_layout = (LinearLayout) findViewById(R.id.design_contract_content_designer);//合同表單Layout
        ll_contract_webview_layout = (LinearLayout) findViewById(R.id.design_contract_content_consumer);//web view 合同表單

        /* init content for consumer form */
        twvc_contract_content_webview = (WebView) findViewById(R.id.contract_sub_content_webview);//webview 物件
        ll_consumer_action_layout = (LinearLayout) findViewById(R.id.ll_send_establish_contract);//消費者支付動作Layout
        ll_consumer_agree_content_layout = (LinearLayout) findViewById(R.id.ll_agree_establish_contract);//消費者己閱讀的提示Layout
        btn_consumer_submit_button = (Button) findViewById(R.id.btn_send_establish_contract);

         /* init content for designer form */
        llbtn_see_contract_detail = (LinearLayout) findViewById(R.id.ll_flow_examine_contract); //設計師合同詳情點擊條
    }

    private void UpdateUIActionLayout() {

        ll_consumer_action_layout.setVisibility(View.VISIBLE);
        ll_consumer_agree_content_layout.setVisibility(View.VISIBLE);

        btn_consumer_submit_button.setText(R.string.confirmation_and_payment);
        btn_consumer_submit_button.setEnabled(false);
        btn_consumer_submit_button.setBackgroundResource(R.drawable.bg_common_btn_pressed);

        UpdateUIConsumerAgreeCheckBox();
        btn_consumer_submit_button.setOnClickListener(new View.OnClickListener() { // 跳转到支付首款页面                              @Override
            public void onClick(View v) {
                Intent intent = new Intent(FlowWebContractDetailActivity.this, FlowFirstDesignActivity.class);
                intent.putExtra(Constant.SeekDesignerDetailKey.DESIGNER_ID, designer_id);
                intent.putExtra(Constant.SeekDesignerDetailKey.NEEDS_ID, needs_id);
                intent.putExtra(Constant.SeekDesignerDetailKey.CONTRACT_NO, contract_number);
                intent.putExtra(Constant.BundleKey.TEMPDATE_ID, MPStatusMachine.NODE__DESIGN_FIRST_PAY);
//                                    intent.putExtra(Constant.WorkFlowStateKey.JUMP_FROM_STATE, Constant.WorkFlowStateKey.STEP_FLOW);
                startActivityForResult(intent, ContractForFirst);
            }
        });

        img_agree_establish_contract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isAgree = !isAgree;
                UpdateUIConsumerAgreeCheckBox();
            }
        });
    }

    private void UpdateUIConsumerAgreeCheckBox() {

        if (!isAgree) { // 判断是否我已阅读（我已阅读）
            img_agree_establish_contract.setBackgroundResource(android.R.color.white);
            btn_consumer_submit_button.setEnabled(false);
            btn_consumer_submit_button.setBackgroundResource(R.drawable.bg_common_btn_pressed);
        } else { // 判断是否我已阅读（我未阅读）
            img_agree_establish_contract.setBackgroundResource(R.drawable.icon_selected_checked);
            btn_consumer_submit_button.setEnabled(true);
            btn_consumer_submit_button.setBackgroundResource(R.drawable.bg_common_btn_blue);
        }
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        CustomProgress.show(this, "", false, null);

        WebSettings webSettings = twvc_contract_content_webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowContentAccess(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setDefaultTextEncodingName(Constant.NetBundleKey.UTF_8);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);

        if (bconsumerActionShow)
            UpdateUIActionLayout();

        setContentView();
        CustomProgress.cancelDialog();

        //设置Web视图
        twvc_contract_content_webview.setWebViewClient(new webViewClient());
    }

    private void setContentView() {
        try {
            //Return an AssetManager instance for your application's package
            InputStream is = getAssets().open("Contract.txt");
            int size = is.available();

            // Read the entire asset into a local byte buffer.
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            String text = new String(buffer, Constant.NetBundleKey.UTF_8);

            twvc_contract_content_webview.loadDataWithBaseURL(null, text, Constant.NetBundleKey.MIME_TYPE_TEXT_HTML, Constant.NetBundleKey.UTF_8, "");

        } catch (IOException e) {
            // Should never happen!
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ContractForFirst) {
            SetReturnSesult(ContractDetail);
            finish();
        }

        if (resultCode == FirstForContract) {
            SetReturnSesult(FirstForContract);
            finish();
        }
    }

    private void SetReturnSesult(int code) {
        Intent reData = new Intent();
        reData.putExtra("CONSUMER_ACTION_AGREE", isAgree);
        setResult(code, reData);
    }

    //Web视图
    private class webViewClient extends WebViewClient {
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    private WebView twvc_contract_content_webview;

    private LinearLayout llbtn_see_contract_detail;
    private LinearLayout ll_consumer_action_layout;
    private LinearLayout ll_designer_action_layout;
    private LinearLayout ll_consumer_agree_content_layout;
    private LinearLayout ll_contract_input_form_layout;
    private LinearLayout ll_contract_webview_layout;
    private int ContractForFirst = 0; //　从合同跳转到设计首款
    private int FirstForContract = 1; // 首款调到设计合同
    private int ContractDetail = 2;

    private ImageView img_agree_establish_contract;
    private Button btn_consumer_submit_button;
    private boolean isAgree = false;
    private boolean bconsumerActionShow = false;

    protected String designer_id;
    protected String needs_id;
    protected String contract_number;

}