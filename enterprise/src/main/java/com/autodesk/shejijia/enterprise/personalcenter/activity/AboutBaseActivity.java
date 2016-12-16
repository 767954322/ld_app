package com.autodesk.shejijia.enterprise.personalcenter.activity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.framework.activity.BaseActivity;

/**
 * Created by luchongbin on 16-12-16.
 */

public class AboutBaseActivity extends BaseActivity {
    @Override
    protected int getLayoutResId() {
        return 0;
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }
    protected void initWebView(WebView webView,String url) {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowContentAccess(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setDefaultTextEncodingName(Constant.NetBundleKey.UTF_8);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
        webView.loadUrl(url);
        webView.setWebViewClient(new webViewClient());

    }
    private class webViewClient extends WebViewClient {
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
