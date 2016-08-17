package com.autodesk.shejijia.consumer.personalcenter.workflow.activity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
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
        return R.layout.activity_flow_contract_detail;
    }

    @Override
    protected void initView() {
        webView = (WebView) findViewById(R.id.webview_flow_contract_detail);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowContentAccess(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setDefaultTextEncodingName(Constant.NetBundleKey.UTF_8);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);

        setContentView();

        //设置Web视图
        webView.setWebViewClient(new webViewClient());
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

            String designSketch = (String) getIntent().getExtras().get(Constant.DesignerFlowEstablishContract.DESIGNSKETCH);
            String effectivePicture = (String) getIntent().getExtras().get(Constant.DesignerFlowEstablishContract.EFFECTIVEPICTURE);
            String addCurrency = (String) getIntent().getExtras().get(Constant.DesignerFlowEstablishContract.ADDCURRENCY);
            String designPay = (String) getIntent().getExtras().get(Constant.DesignerFlowEstablishContract.DESIGNPAY);
            String designFirstFee = (String) getIntent().getExtras().get(Constant.DesignerFlowEstablishContract.DESIGNFIRSTFEE);
            String designBalanceFee = (String) getIntent().getExtras().get(Constant.DesignerFlowEstablishContract.DESIGNBALANCEFEE);

            text = text.replace("<wf-designSketch>", designSketch); // 效果图
            text = text.replace("<wf-effectivePicture>", effectivePicture); // 渲染图
            text = text.replace("<wf-addCurrency>", addCurrency); // 增加的钱数
            text = text.replace("<wf-designPay>", designPay); // 总额
            text = text.replace("<wf-designFirstFee>", designFirstFee); // 首款
            text = text.replace("<wf-designBalanceFee>", designBalanceFee); // 尾款

            webView.loadDataWithBaseURL(null, text, Constant.NetBundleKey.MIME_TYPE_TEXT_HTML, Constant.NetBundleKey.UTF_8, "");

        } catch (IOException e) {
            // Should never happen!
            throw new RuntimeException(e);
        }
    }


    //Web视图
    private class webViewClient extends WebViewClient {
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    private WebView webView;
}