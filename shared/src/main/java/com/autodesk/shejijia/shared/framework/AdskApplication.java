package com.autodesk.shejijia.shared.framework;

import android.app.Activity;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.autodesk.shejijia.shared.BuildConfig;
import com.autodesk.shejijia.shared.components.common.appglobal.ApiManager;
import com.autodesk.shejijia.shared.Config;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.common.appglobal.UrlMessagesContants;
import com.autodesk.shejijia.shared.components.common.network.OkStringRequest;
import com.autodesk.shejijia.shared.components.common.network.PushNotificationHttpManager;
import com.autodesk.shejijia.shared.components.common.utility.CommonUtils;
import com.autodesk.shejijia.shared.components.common.utility.ConfigProperties;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.ImageUtils;
import com.autodesk.shejijia.shared.components.common.utility.LogUtils;
import com.autodesk.shejijia.shared.components.common.utility.SharedPreferencesUtils;
import com.autodesk.shejijia.shared.components.im.IWorkflowDelegate;
import com.autodesk.shejijia.shared.components.im.constants.BroadCastInfo;
import com.autodesk.shejijia.shared.components.im.service.webSocketService;
import com.autodesk.shejijia.shared.framework.receiver.JPushMessageReceiver;
import com.pgyersdk.crash.PgyCrashManager;

import cn.jpush.android.api.JPushInterface;


/**
 * @author yangxuewu.
 * @version v1.0 .
 * @date 2016-6-7 .
 * @file AdskApplication.java .
 * @brief 设置全局数据 .
 */
public abstract class AdskApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        sAdskApplication = this;

        ConfigProperties.initProperties(this);

        boolean imServer = ApiManager.isRunningDevelopment(ApiManager.RUNNING_DEVELOPMENT);
        UrlMessagesContants.init(imServer);

        initData();
        initListener();
        LogUtils.i("SourceSet=" + Config.API_VERSION_NAME + ", Debug=" + BuildConfig.DEBUG);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    /**
     * 初始化操作
     */
    public void initData() {
        queue = Volley.newRequestQueue(this);
        ImageUtils.initImageLoader(this);
        JPushInterface.setDebugMode(false);    // Enable logging settings, turn off logging when you publish
        JPushInterface.init(this);            // Init JPush

        MemberEntity entity = (MemberEntity) SharedPreferencesUtils.getObject(sAdskApplication, Constant.UerInfoKey.USER_INFO);
        if (entity != null) {
            onLoginSuccess(entity);
        }
        JPushInterface.setDebugMode(false);    // Enable logging settings, turn off logging when you publish
        JPushInterface.init(this);            // Init JPush
    }

    public void initListener() {
        //注册pgy
        if (BuildConfig.DEBUG) {
            PgyCrashManager.register(this);
        }
        mSignInNotificationReceiver = new SignInNotificationReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(BroadCastInfo.USER_DID_LOGOUT);
        filter.addAction(BroadCastInfo.LOGIN_ACTIVITY_FINISHED);
        registerReceiver(mSignInNotificationReceiver, filter);

        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            }

            @Override
            public void onActivityStarted(Activity activity) {
            }

            @Override
            public void onActivityResumed(Activity activity) {
                //if (activity instanceof BaseChatRoomActivity)
                mIsChatRoomActivityInForeground = true;
            }

            @Override
            public void onActivityPaused(Activity activity) {
                //if (activity instanceof BaseChatRoomActivity)
                mIsChatRoomActivityInForeground = false;
            }

            @Override
            public void onActivityStopped(Activity activity) {
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
            }
        });

    }

    public static synchronized AdskApplication getInstance() {
        return sAdskApplication;
    }

    public void setWebSocketStatus(boolean result) {
        IsWebSocketConnecting = result;
    }

    public boolean getWebSocketStatus() {
        return IsWebSocketConnecting;
    }

    public boolean isChatRoomActivityInForeground() {
        return mIsChatRoomActivityInForeground;
    }

    public MemberEntity getMemberEntity() {
        return memberEntity;
    }

    public IWorkflowDelegate getIMWorkflowDelegate() {
        return null;
    }


    public Class<?> getSplashActivityClass() {
        return null;
    }

    protected void onLoginSuccess(MemberEntity entity) {
        /// 将获取到底数据设置为全局可以访问.
        AdskApplication.setMemberEntity(entity);

        //登陆状态，开启推送
        JPushInterface.resumePush(AdskApplication.this);
        openChatConnection();

        registerForPushNotification();

        /// 保存获取到的数据 .
        SharedPreferencesUtils.saveObject(getApplicationContext(), Constant.UerInfoKey.USER_INFO, entity);

        Intent loginIntent = new Intent(BroadCastInfo.USER_DID_LOGIN);
        sendBroadcast(loginIntent);
    }

    /**
     * 退出登录后执行的操作
     */
    private void onLogout() {
        AdskApplication.setMemberEntity(null);

        //推出登陆状态，关闭推送
        JPushInterface.stopPush(AdskApplication.this);

        closeChatConnection();

        unRegisterForPushNotification();
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

    /**
     * 开启聊天室服务
     */
    public void openChatConnection() {
        if (CommonUtils.isChatServiceRunning(this,webSocketService.class))
            closeChatConnection();

        if (memberEntity != null) {
            Intent intent = new Intent(this, webSocketService.class);
            intent.putExtra("acs_member_id", memberEntity.getAcs_member_id());
            intent.putExtra("acs_x_session", memberEntity.getAcs_x_session());
            startService(intent);
        }
    }

    public void closeChatConnection() {
        Intent stopIntent = new Intent(this, webSocketService.class);
        stopService(stopIntent);
    }

    public static void setMemberEntity(MemberEntity memberEntity) {
        AdskApplication.memberEntity = memberEntity;
    }

    /**
     * 用于处理登录后数据的操作
     * @param entity
     */

    public  void saveSignInInfo(MemberEntity entity){
        String ZERO = "0";
        /// 为不符合规则的acs_member_id 补足位数 .
        String acs_member_id = entity.getAcs_member_id();
        if (acs_member_id.length() < 8) {
            acs_member_id += ZERO;
            entity.setAcs_member_id(acs_member_id);
        }
        LogUtils.i("APPLICATION", "memberEntity:" + entity);

        onLoginSuccess(entity);
    }

    /**
     * 全局的广播接收者,用于处理登录后数据的操作
     */
    private class SignInNotificationReceiver extends BroadcastReceiver {//此广播会有延时，在进入界面后会有获取不到登陆人信息的的情况
        @Override
        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction();
            if (action.equalsIgnoreCase(BroadCastInfo.USER_DID_LOGOUT)) {
                onLogout();
            } else if (action.equalsIgnoreCase(BroadCastInfo.LOGIN_ACTIVITY_FINISHED)) {
                String strToken = intent.getStringExtra(BroadCastInfo.LOGIN_TOKEN);

                MemberEntity entity = GsonUtil.jsonToBean(strToken, MemberEntity.class);

                String ZERO = "0";

                /// 为不符合规则的acs_member_id 补足位数 .
                String acs_member_id = entity.getAcs_member_id();
                if (acs_member_id.length() < 8) {
                    acs_member_id += ZERO;
                    entity.setAcs_member_id(acs_member_id);
                }
                LogUtils.i("APPLICATION", "memberEntity:" + entity);

                onLoginSuccess(entity);
            }
        }
    }

    private static AdskApplication sAdskApplication;
    private SignInNotificationReceiver mSignInNotificationReceiver;

    private boolean IsWebSocketConnecting = false;
    private boolean mIsChatRoomActivityInForeground = false;
    protected static MemberEntity memberEntity;
    public RequestQueue queue;

    public static final String JPUSH_STORE_KEY = "com.autodesk.easyhome.marketplace.JPUSHSTORE";
}
