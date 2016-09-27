package com.autodesk.shejijia.shared.framework;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.multidex.MultiDex;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.autodesk.shejijia.shared.components.common.appglobal.ApiManager;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.common.appglobal.UrlMessagesContants;
import com.autodesk.shejijia.shared.components.common.utility.CommonUtils;
import com.autodesk.shejijia.shared.components.common.utility.ConfigProperties;
import com.autodesk.shejijia.shared.components.common.utility.ImageUtils;
import com.autodesk.shejijia.shared.components.common.utility.LogUtils;
import com.autodesk.shejijia.shared.components.common.utility.SharedPreferencesUtils;
import com.autodesk.shejijia.shared.components.im.IWorkflowDelegate;
import com.autodesk.shejijia.shared.components.im.service.webSocketService;

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

        MemberEntity entity = (MemberEntity) SharedPreferencesUtils.getObject(sAdskApplication, Constant.UerInfoKey.USER_INFO);
        if (entity != null) {
            onLoginSuccess(entity);
        }
        JPushInterface.setDebugMode(false);    // Enable logging settings, turn off logging when you publish
        JPushInterface.init(this);            // Init JPush
    }

    public void initListener() {

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

    public abstract boolean isDebug();

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
        return null;
    }

    public IWorkflowDelegate getIMWorkflowDelegate() {
        return null;
    }


    public Class<?> getSplashActivityClass()
    {
        return null;
    }


    /**
     * 登录成功后执行的操作
     *
     * @param entity 登录后的用户信息
     */
    public void onLoginSuccess(MemberEntity entity) {
        //登陆状态，开启推送
        JPushInterface.resumePush(AdskApplication.this);

        openChatConnection();
    }

    /**
     * 退出登录后执行的操作
     */
    public void onLogout() {

        //退出登陆状态，关闭推送
        JPushInterface.stopPush(AdskApplication.this);

        closeChatConnection();
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

    private void closeChatConnection() {
        Intent stopIntent = new Intent(this, webSocketService.class);
        stopService(stopIntent);
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

    private static AdskApplication sAdskApplication;

    private boolean IsWebSocketConnecting = false;
    private boolean mIsChatRoomActivityInForeground = false;
    protected static MemberEntity memberEntity;
    public RequestQueue queue;
}
