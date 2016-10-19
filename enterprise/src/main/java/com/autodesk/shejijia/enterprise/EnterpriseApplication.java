package com.autodesk.shejijia.enterprise;

import com.autodesk.shejijia.shared.components.common.network.NetRequestManager;
import com.autodesk.shejijia.shared.framework.AdskApplication;
import com.pgyersdk.crash.PgyCrashManager;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by t_xuz on 8/15/16.
 * 服务端主线application对象,不要放太多耗时操作在里面,否则启动app时间会很慢
 */
public class EnterpriseApplication extends AdskApplication {


    public static EnterpriseApplication application;

    @Override
    public void onCreate() {
        super.onCreate();

        application = this;

        //初始化网络请求队列
        NetRequestManager.getInstance().init(this);

        //内存检测工具初始化
        LeakCanary.install(this);

        //注册蒲公英
        PgyCrashManager.register(this);
    }

    @Override
    public boolean isDebug() {
        return BuildConfig.DEBUG;
    }

    public static synchronized EnterpriseApplication getInstance() {
        return application;
    }

}
