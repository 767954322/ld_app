package com.autodesk.shejijia.consumer;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.utility.MPNetworkUtils;
import com.autodesk.shejijia.shared.components.common.utility.SharedPreferencesUtils;
import com.autodesk.shejijia.shared.components.im.IWorkflowDelegate;
import com.autodesk.shejijia.shared.framework.AdskApplication;
import com.socks.library.KLog;

import org.json.JSONObject;

public class ConsumerApplication extends AdskApplication {
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void onLoginSuccess(MemberEntity entity) {

        super.onLoginSuccess(entity);

        //  getLoginThreadId(getMemberEntity().getAcs_member_id());
    }

    @Override
    public IWorkflowDelegate getIMWorkflowDelegate() {
        return mDesignPlatformDelegate;
    }

    private void getLoginThreadId(String designer_id) {
        OkJsonRequest.OKResponseCallback okResponseCallback = new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    KLog.json("ConsumerApplication", jsonObject.toString());
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


    private DesignPlatformDelegate mDesignPlatformDelegate = new DesignPlatformDelegate();

}
