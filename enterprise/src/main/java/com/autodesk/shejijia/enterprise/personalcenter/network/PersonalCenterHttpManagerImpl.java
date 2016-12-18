package com.autodesk.shejijia.enterprise.personalcenter.network;

import android.support.annotation.NonNull;
import com.android.volley.AuthFailureError;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.appglobal.ConstructionConstants;
import com.autodesk.shejijia.shared.components.common.network.NetRequestManager;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.utility.UserInfoUtils;
import com.autodesk.shejijia.shared.framework.AdskApplication;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by luchongbin on 2016/12/18.
 */

public class PersonalCenterHttpManagerImpl implements PersonalCenterHttpManager {
    private static class ServerHttpManagerHolder {
        private static final PersonalCenterHttpManagerImpl INSTANCE = new PersonalCenterHttpManagerImpl();
    }

    public static PersonalCenterHttpManagerImpl getInstance() {
        return PersonalCenterHttpManagerImpl.ServerHttpManagerHolder.INSTANCE;
    }

    @Override
    public void getPersonalHeadPicPicture(String requestTag,String acsMemberId,@NonNull OkJsonRequest.OKResponseCallback callback) {
        String requestUrl = ConstructionConstants.MEMBER_PATH+"/members/"+acsMemberId;
        get(requestTag,requestUrl,callback);
    }
    private void get(String requestTag, String requestUrl, @NonNull OkJsonRequest.OKResponseCallback callback) {
        OkJsonRequest okRequest = new OkJsonRequest(OkJsonRequest.Method.GET, requestUrl, null, callback) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put(Constant.NetBundleKey.X_TOKEN, "Basic "+UserInfoUtils.getToken(AdskApplication.getInstance()));
                return header;
            }
            @Override
            public String getBodyContentType() {
                return Constant.NetBundleKey.APPLICATON_JSON;
            }
        };
        NetRequestManager.getInstance().addRequest(requestTag, okRequest);
    }
}
