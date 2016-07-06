package com.autodesk.shejijia.shared.components.common.tools.login;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.framework.activity.BaseActivity;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.appglobal.UrlConstants;
import com.autodesk.shejijia.shared.components.im.constants.BroadCastInfo;
import com.autodesk.shejijia.shared.components.common.utility.CommonUtils;
import com.socks.library.KLog;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * @author he.liu .
 * @version v1.0 .
 * @date 2016-6-7 .
 * @file RegisterOrLoginActivity.java .
 * @brief 跳转到登录页面后执行的操作 .
 */
public class RegisterOrLoginActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_webview_login;
    }

    @Override
    protected void initView() {
        mWebView = (WebView) findViewById(R.id.webview_login);
        mTvFinishWebView = (TextView) findViewById(R.id.tv_finish_webview);
        mLlWebViewBackup = (LinearLayout) findViewById(R.id.ll_webview_backup);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        /// 加载登录页面 .
        mWebView.loadUrl(UrlConstants.LOGIN_PATH);
        initSetting();
        initWebViewClient();
    }

    @Override
    protected void initListener() {
        mTvFinishWebView.setVisibility(View.GONE);
        mLlWebViewBackup.setOnClickListener(this);
        mTvFinishWebView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.tv_finish_webview)
        {
            CommonUtils.clearCookie(this);
            finish();
        }
        else if (i == R.id.ll_webview_backup)
        {
            if (mWebView != null && mWebView.canGoBack())
            {
                if (!TextUtils.isEmpty(pagerFinishedUrl) && pagerFinishedUrl.contains(PAGER_FINISHED_TAG))
                {
                    CommonUtils.clearCookie(this);
                    finish();
                }
                else
                {
                    mWebView.goBack();// 返回前一个页面
                    mTvFinishWebView.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    /**
     * 设置加载WebView过程中执行的操作
     */
    private void initSetting() {
        mWebSettings = mWebView.getSettings();
        /// 设置支持Javascript .
        mWebSettings.setJavaScriptEnabled(true);

        mWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        mWebSettings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
        mWebSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        mWebSettings.setLoadWithOverviewMode(true);
        /// WebView打开加速 .
        mWebSettings.setBlockNetworkImage(true);
        mWebSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        if (Build.VERSION.SDK_INT >= 21) {
            mWebSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        /// 解决某些Android5.0系统无法代码WebView情况 .
        if (Build.VERSION.SDK_INT >= 19) {
            mWebView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else {
            mWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        /// 调用JavaScript设置的方法 .
        mWebView.addJavascriptInterface(new InJavaScriptLocalObj(), ANDROID);
    }

    /**
     * 操作WebView的基本操作
     */
    private void initWebViewClient() {
        mWebViewClient = new MyWebViewClient();
        mWebView.setWebViewClient(mWebViewClient);
    }

    /**
     * WebView调用JavaScript代码进行的回调
     */
    private final class InJavaScriptLocalObj {

        @JavascriptInterface
        public void getToken(String token) {
            try {
                String strToken = URLDecoder.decode(token, Constant.NetBundleKey.UTF_8).substring(6);
                /// 登录成功后,发送广播 .
                Intent intent = new Intent(BroadCastInfo.LOGIN_ACTIVITY_FINISHED);
                intent.putExtra(BroadCastInfo.LOGIN_TOKEN, strToken);
                sendBroadcast(intent);

                finish();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 加载网页过程,用于处理加具体的网页跳转
     */
    private class MyWebViewClient extends WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
//            KLog.d(TAG, url);
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//            KLog.d(TAG, url);
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            mWebSettings.setBlockNetworkImage(false);
            pagerFinishedUrl = url;
            KLog.d(TAG, url);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String
                failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            KLog.e(TAG, errorCode + ":" + description);
        }
    }

    private static final String PAGER_FINISHED_TAG = "access_type=design&reutnr_url=";
    private static final String ANDROID = "android";

    private LinearLayout mLlWebViewBackup;
    private TextView mTvFinishWebView;
    private WebView mWebView;

    private String pagerFinishedUrl;
    private MyWebViewClient mWebViewClient;
    private WebSettings mWebSettings;
}
