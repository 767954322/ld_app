package com.autodesk.shejijia.enterprise;

import com.autodesk.shejijia.shared.components.common.network.NetRequestManager;
import com.autodesk.shejijia.shared.framework.AdskApplication;
import com.pgyersdk.crash.PgyCrashManager;

/**
 * Created by t_xuz on 8/15/16.
 * 服务端主线application对象,不要放太多耗时操作在里面,否则启动app时间会很慢
 */
public class EnterpriseApplication extends AdskApplication {


    public static EnterpriseApplication application;

    @Override
    public void onCreate() {
        super.onCreate();
        //内存检测工具初始化
        LibUtils.installLeakCanary(this);

        application = this;

        //初始化网络请求队列
        NetRequestManager.getInstance().init(this);

        //注册pgy
        if (BuildConfig.DEBUG) {
            PgyCrashManager.register(this);
        }
    }

    public static synchronized EnterpriseApplication getInstance() {
        return application;
    }

}
