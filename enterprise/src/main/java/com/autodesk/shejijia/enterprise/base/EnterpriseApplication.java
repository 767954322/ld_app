package com.autodesk.shejijia.enterprise.base;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.autodesk.shejijia.shared.BuildConfig;
import com.autodesk.shejijia.shared.components.common.utility.ImageUtils;
import com.autodesk.shejijia.shared.framework.AdskApplication;
import com.pgyersdk.crash.PgyCrashManager;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by t_xuz on 8/15/16.
 * 服务端主线application对象,不要放太多耗时操作在里面,否则启动app时间会很慢
 */
public class EnterpriseApplication extends AdskApplication{

    public RequestQueue queue;
    public static EnterpriseApplication application ;

    @Override
    public void onCreate() {
        super.onCreate();

        application = this;

        //volley请求队列
        queue = Volley.newRequestQueue(this);

        //初始化imageLoader
        ImageUtils.initImageLoader(this);

        //内存检测工具初始化
        LeakCanary.install(this);

        //注册蒲公英
        PgyCrashManager.register(this);
    }

    @Override
    public boolean isDebug() {
        return BuildConfig.DEBUG;
    }

    public static synchronized EnterpriseApplication getInstance(){
        return application;
    }

}
