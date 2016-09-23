package com.autodesk.shejijia.consumer.home.decorationlibrarys.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

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
        wvRoaming.getSettings().setDomStorageEnabled(true);
        wvRoaming.loadUrl(roaming);


        setImageForNavButton(ButtonType.RIGHT,R.drawable.ic_menu_share);
        setVisibilityForNavButton(ButtonType.RIGHT,true);

        //  wvRoaming.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        // wvRoaming.getSettings().setDefaultFontSize(18);
        //  wvRoaming.getSettings().setLoadsImagesAutomatically(true);
        //wvRoaming.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        if (Build.VERSION.SDK_INT >= 21) {
            wvRoaming.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        /// 解决某些Android5.0系统无法代码WebView情况 .
        if (Build.VERSION.SDK_INT >= 19) {
            wvRoaming.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else {
            wvRoaming.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        wvRoaming.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });


//        wvRoaming.loadUrl("http://www.baidu.com/");
    }


    @Override
    protected void rightNavButtonClicked(View view) {
        super.rightNavButtonClicked(view);
      //  Toast.makeText(this,"2",Toast.LENGTH_LONG).show();
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addCategory("android.intent.category.BROWSABLE");
        intent.setData(Uri.parse(roaming));
        startActivity(intent);

    }

    @Override
    protected void onPause(){
        super.onPause();

        wvRoaming.pauseTimers();
//        if(isFinishing()){
////            wvRoaming.loadUrl("about:blank");
//            setContentView(new FrameLayout(this));
//        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        wvRoaming.resumeTimers();
    }

}