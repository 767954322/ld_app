package com.autodesk.shejijia.consumer;

import android.os.Handler;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.consumer.base.activity.MPSplashActivity;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.shared.components.common.network.NetRequestManager;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.tools.wheel.CityDataHelper;
import com.autodesk.shejijia.shared.components.common.utility.LogUtils;
import com.autodesk.shejijia.shared.components.common.utility.MPNetworkUtils;
import com.autodesk.shejijia.shared.components.common.utility.SharedPreferencesUtils;
import com.autodesk.shejijia.shared.components.im.IWorkflowDelegate;
import com.autodesk.shejijia.shared.framework.AdskApplication;
import com.qy.appframe.common.AppFrame;
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
        AppFrame.initDebug(true);
        //初始化网络请求队列
        NetRequestManager.getInstance().init(this);

    }

    private void reqisterWXAppId() {

        api = WXAPIFactory.createWXAPI(this, APP_ID, true);
        api.registerApp(APP_ID);

    }

    @Override
    public void initData() {
        super.initData();
        dataHelper = CityDataHelper.getInstance(this);
        InputStream in = this.getResources().openRawResource(com.autodesk.shejijia.shared.R.raw.province);
        dataHelper.copyFile(in, CityDataHelper.DATABASE_NAME, CityDataHelper.DATABASES_DIR);
    }

    /// MainThread Handler .
    public static Handler getMainThreadHandler() {
        return mMainThreadHandler;
    }


    /// MainThread Id .
    public static int getMainThreadId() {
        return mMainThreadId;
    }

    @Override
    public IWorkflowDelegate getIMWorkflowDelegate() {
        return mDesignPlatformDelegate;
    }

    @Override
    public Class<?> getSplashActivityClass() {

        return MPSplashActivity.class;
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
    public static String thread_id = "";
    public static int high_level_audit = 0;
    public static int is_loho = 0;

}
