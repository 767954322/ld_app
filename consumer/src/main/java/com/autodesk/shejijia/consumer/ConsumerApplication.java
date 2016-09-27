package com.autodesk.shejijia.consumer;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.consumer.base.activity.MPSplashActivity;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.network.OkStringRequest;
import com.autodesk.shejijia.shared.components.common.network.PushNotificationHttpManager;
import com.autodesk.shejijia.shared.components.common.tools.login.RegisterOrLoginActivity;
import com.autodesk.shejijia.shared.components.common.tools.wheel.CityDataHelper;
import com.autodesk.shejijia.shared.components.common.utility.CommonUtils;
import com.autodesk.shejijia.shared.components.common.utility.LogUtils;
import com.autodesk.shejijia.shared.components.common.utility.MPNetworkUtils;
import com.autodesk.shejijia.shared.components.common.utility.SharedPreferencesUtils;
import com.autodesk.shejijia.shared.components.im.IWorkflowDelegate;
import com.autodesk.shejijia.shared.components.im.constants.BroadCastInfo;
import com.autodesk.shejijia.shared.framework.AdskApplication;
import com.autodesk.shejijia.shared.framework.receiver.JPushMessageReceiver;
import com.autodesk.shejijia.shared.framework.receiver.SignInNotificationReceiver;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.json.JSONObject;

import java.io.InputStream;

public class ConsumerApplication extends AdskApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        mMainThreadHandler = new Handler();
        mMainThreadId = android.os.Process.myTid();

        reqisterWXAppId();

    }

    private void reqisterWXAppId() {

        api = WXAPIFactory.createWXAPI(this, APP_ID, true);
        api.registerApp(APP_ID);

    }

    @Override
    public void initData(){
        super.initData();
        dataHelper = CityDataHelper.getInstance(this);
        InputStream in = this.getResources().openRawResource(com.autodesk.shejijia.shared.R.raw.province);
        dataHelper.copyFile(in, CityDataHelper.DATABASE_NAME, CityDataHelper.DATABASES_DIR);
    }

    @Override
    public void initListener() {
        super.initListener();
        mSignInNotificationReceiver = new SignInNotificationReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(BroadCastInfo.USER_DID_LOGOUT);
        filter.addAction(BroadCastInfo.LOGIN_ACTIVITY_FINISHED);
        registerReceiver(mSignInNotificationReceiver, filter);
    }

    @Override
    public void onLoginSuccess(MemberEntity entity) {
        super.onLoginSuccess(entity);
        //注册推送回调
        registerForPushNotification();
        /// 将获取到底数据设置为全局可以访问.
        setMemberEntity(entity);
        /// 保存获取到的数据 .
        SharedPreferencesUtils.saveObject(getApplicationContext(), Constant.UerInfoKey.USER_INFO, entity);
        //发送登录成功的广播
        Intent loginIntent = new Intent(BroadCastInfo.USER_DID_LOGIN);
        sendBroadcast(loginIntent);
    }

    @Override
    public void onLogout(){
        super.onLogout();
        unRegisterForPushNotification();
        setMemberEntity(null);
        CommonUtils.clearCookie(this);
        CommonUtils.clearAppCache(this);
        SharedPreferencesUtils.clear(AdskApplication.getInstance(), SharedPreferencesUtils.CONFIG);
    }

    private void registerForPushNotification() {
        String regId = SharedPreferencesUtils.readString(JPushMessageReceiver.REGID);
        if (regId != null)
            PushNotificationHttpManager.registerDeviceWithMarketplace(regId, new OkStringRequest.OKResponseCallback() {
                @Override
                public void onResponse(String s) {
                }

                @Override
                public void onErrorResponse(VolleyError volleyError) {
                }
            });
    }


    private void unRegisterForPushNotification() {
        String regId = SharedPreferencesUtils.readString(JPushMessageReceiver.REGID);
        SharedPreferencesUtils.delete(getApplicationContext(), JPushMessageReceiver.REGID);

        if (regId != null)
            PushNotificationHttpManager.unRegisterDeviceWithMarketplace(regId, new OkStringRequest.OKResponseCallback() {
                @Override
                public void onResponse(String s) {
                }

                @Override
                public void onErrorResponse(VolleyError volleyError) {
                }
            });
    }



    /// MainThread Handler .
    public static Handler getMainThreadHandler() {
        return mMainThreadHandler;
    }


    /// MainThread Id .
    public static int getMainThreadId() {
        return mMainThreadId;
    }

    public static void setMemberEntity(MemberEntity memberEntity) {
        ConsumerApplication.memberEntity = memberEntity;
    }

    @Override
    public MemberEntity getMemberEntity() {
        return memberEntity;
    }

    @Override
    public IWorkflowDelegate getIMWorkflowDelegate() {
        return mDesignPlatformDelegate;
    }

    @Override
    public Class<?> getSplashActivityClass() {

        return MPSplashActivity.class;
    }

    @Override
    public boolean isDebug() {
        return BuildConfig.DEBUG;
    }

    private void getLoginThreadId(String designer_id) {
        OkJsonRequest.OKResponseCallback okResponseCallback = new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    LogUtils.i("ConsumerApplication", jsonObject.toString());
                    String im_msg_thread_id = jsonObject.getString(IM_MSG_THREAD_ID);
                    String inner_sit_msg_thread_id = jsonObject.getString(INNER_SIT_MSG_THREAD_ID);

                    SharedPreferencesUtils.writeString(IM_MSG_THREAD_ID, im_msg_thread_id);
                    SharedPreferencesUtils.writeString(INNER_SIT_MSG_THREAD_ID, inner_sit_msg_thread_id);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                MPNetworkUtils.logError("ConsumerApplication", volleyError);
            }

            private static final String IM_MSG_THREAD_ID = "im_msg_thread_id";
            private static final String INNER_SIT_MSG_THREAD_ID = "inner_sit_msg_thread_id";
        };
        MPServerHttpManager.getInstance().getLoginThreadId(designer_id, okResponseCallback);
    }

    private static final String APP_ID = "wx4128c321fa069fa8";
    public static IWXAPI api;
    private DesignPlatformDelegate mDesignPlatformDelegate = new DesignPlatformDelegate();
    /// MainThread Hanlder .
    private static Handler mMainThreadHandler = null;
    /// MainThread Id .
    private static int mMainThreadId;
    private CityDataHelper dataHelper;
    private SignInNotificationReceiver mSignInNotificationReceiver;

}
