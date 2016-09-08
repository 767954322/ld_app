package com.autodesk.shejijia.consumer.home.decorationlibrarys.activity;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.shared.framework.activity.NavigationBarActivity;

public class CaseLibraryRoamingWebView extends NavigationBarActivity {
    private WebView wvRoaming;

    private String roaming;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_case_library_roaming_web_view;
    }

    @Override
    protected void initView() {
        super.initView();
        wvRoaming = (WebView) findViewById(R.id.wv_roaming);
    }

    @Override
    protected void initExtraBundle() {
        super.initExtraBundle();
        roaming = getIntent().getStringExtra("roaming");

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        wvRoaming.getSettings().setJavaScriptEnabled(true);
        wvRoaming.getSettings().setSupportZoom(true);
        wvRoaming.getSettings().setBuiltInZoomControls(true);
        wvRoaming.loadUrl(roaming);
        //  wvRoaming.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        // wvRoaming.getSettings().setDefaultFontSize(18);
        //  wvRoaming.getSettings().setLoadsImagesAutomatically(true);
        //wvRoaming.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        wvRoaming.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });


//        wvRoaming.loadUrl("http://www.baidu.com/");
    }



}