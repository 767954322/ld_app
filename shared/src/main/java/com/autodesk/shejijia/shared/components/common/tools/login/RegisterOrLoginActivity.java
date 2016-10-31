package com.autodesk.shejijia.shared.components.common.tools.login;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.common.appglobal.UrlConstants;
import com.autodesk.shejijia.shared.components.common.uielements.CustomProgress;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.AlertView;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.OnItemClickListener;
import com.autodesk.shejijia.shared.components.common.utility.CommonUtils;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.LogUtils;
import com.autodesk.shejijia.shared.components.common.utility.LoginUtils;
import com.autodesk.shejijia.shared.components.common.utility.SharedPreferencesUtils;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.components.im.constants.BroadCastInfo;
import com.autodesk.shejijia.shared.framework.AdskApplication;
import com.autodesk.shejijia.shared.framework.activity.BaseActivity;

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

    private boolean isFirst = true;

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
        if (i == R.id.tv_finish_webview) {
            CommonUtils.clearCookie(this);
            finish();
        } else if (i == R.id.ll_webview_backup) {
            if (mWebView != null && mWebView.canGoBack()) {
                if (!TextUtils.isEmpty(pagerFinishedUrl) && pagerFinishedUrl.contains(PAGER_FINISHED_TAG)) {
                    CommonUtils.clearCookie(this);
                    finish();
                } else {
                    mWebView.goBack();// 返回前一个页面
                    mTvFinishWebView.setVisibility(View.VISIBLE);
                }
            }else {
                finish();
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
                MemberEntity entity = GsonUtil.jsonToBean(strToken, MemberEntity.class);
                LogUtils.e("memberEntity22",entity+"");
                String member_type = entity.getMember_type();
                if (!TextUtils.isEmpty(member_type) && ( member_type.equals("designer") || member_type.equals("member")
                        || member_type.equals("clientmanager") || member_type.equals("materialstaff") || member_type.equals("foreman"))){//登陆账号为消费者或者设计师
                    AdskApplication.getInstance().saveSignInInfo(entity);
                    // 解决切换帐号的时候 我的项目Fragment 不刷新问题
                    SharedPreferencesUtils.writeBoolean("islogin", true);
//                /// 登录成功后,发送广播 .
                    Intent intent = new Intent(BroadCastInfo.LOGIN_ACTIVITY_FINISHED);
                    intent.putExtra(BroadCastInfo.LOGIN_TOKEN, strToken);
                    sendBroadcast(intent);
                    finish();
                }else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new AlertView(UIUtils.getString(R.string.tip), "您的账户无法在此平台登录", null, new String[]{UIUtils.getString(R.string.sure)}, null, RegisterOrLoginActivity.this,
                                    AlertView.Style.Alert, new OnItemClickListener() {
                                @Override
                                public void onItemClick(Object object, int position) {
                                    LoginUtils.doLogout(RegisterOrLoginActivity.this);
                                    finish();
                                }
                            }).show();
                        }
                    });

                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        if (mWebView != null) {
//            mWebView.onResume();
//            mWebView.resumeTimers();
//        }
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        if (mWebView != null) {
//            mWebView.onPause();
//            mWebView.pauseTimers();
//        }
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CustomProgress.cancelDialog();

//        if (mWebView != null) {
//            mWebView.destroy();
//        }
    }

    /**
     * 加载网页过程,用于处理加具体的网页跳转
     */
    private class MyWebViewClient extends WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
//            LogUtils.i(TAG, url);
            super.onPageStarted(view, url, favicon);
            if (isFirst) {
                isFirst = false;
                if (!isFinishing()){
                    CustomProgress.show(RegisterOrLoginActivity.this, "", false, null);
                }
            }
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//            LogUtils.i(TAG, url);
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            mWebSettings.setBlockNetworkImage(false);
            pagerFinishedUrl = url;
            LogUtils.i(TAG, url);
            CustomProgress.cancelDialog();
            isFirst = true;
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed();
            //     handler.cancel();
            //     handler.handleMessage(null);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String
                failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            LogUtils.e(TAG, errorCode + ":" + description);
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
