package com.autodesk.shejijia.enterprise.base;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.autodesk.shejijia.shared.BuildConfig;
import com.autodesk.shejijia.shared.components.common.appglobal.ApiManager;
import com.autodesk.shejijia.shared.components.common.appglobal.UrlMessagesContants;
import com.autodesk.shejijia.shared.components.common.utility.ImageUtils;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.pgyersdk.crash.PgyCrashManager;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by t_xuz on 8/15/16.
 * 服务端主线application对象,不要放太多耗时操作在里面,否则启动app时间会很慢
 */
public class EnterpriseApplication extends Application{ //TODO why not extends shared application

    public RequestQueue queue;
    public static EnterpriseApplication application ;

    @Override
    public void onCreate() {
        super.onCreate();

        application = this;

        //volley请求队列
        queue = Volley.newRequestQueue(this);

        //初始化 IM 相关
        boolean imServer = ApiManager.isRunningDevelopment(ApiManager.RUNNING_DEVELOPMENT);
        UrlMessagesContants.init(imServer);

        //初始化log打印工具
//        KLog.init(BuildConfig.LOG_DEBUG);
        initLog();

        //初始化imageLoader
        ImageUtils.initImageLoader(this);

        //内存检测工具初始化
        LeakCanary.install(this);

        //注册蒲公英
        PgyCrashManager.register(this);
    }

    public static synchronized EnterpriseApplication getInstance(){
        return application;
    }

    private void initLog(){
        Logger
                .init("enterprise")                 // default PRETTYLOGGER or use just init()
                .methodCount(3)                 // default 2
                .hideThreadInfo()               // default shown
                .logLevel(LogLevel.FULL)        // default LogLevel.FULL(LogLevel.NONE for release)
                .methodOffset(2)                // default 0
                ; //default AndroidLogAdapter
    }
}
