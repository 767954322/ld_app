package com.autodesk.shejijia.shared.components.form.common.network;

import com.android.volley.AuthFailureError;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.network.NetRequestManager;
import com.autodesk.shejijia.shared.components.common.network.OkJsonArrayRequest;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.form.common.utils.UrlHelper;
import com.autodesk.shejijia.shared.components.form.common.utils.UserInfoUtil;
import com.autodesk.shejijia.shared.components.form.entity.ContainedForm;
import com.autodesk.shejijia.shared.framework.AdskApplication;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by t_panya on 16/10/20.
 */

public class FormServerHttpManager {

    private FormServerHttpManager(){

    }

    private static class ServerHttpManagerHolder {
        private static final FormServerHttpManager INSTANCE = new FormServerHttpManager();
    }

    public static FormServerHttpManager getInstance(){
        return ServerHttpManagerHolder.INSTANCE;
    }

    public void getFormItemDetails(final String[] fid, final OkJsonArrayRequest.OKResponseCallback callback){
        String url = UrlHelper.bindFormGetUrl(fid);
        OkJsonArrayRequest okRequest = new OkJsonArrayRequest(url,callback){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put(Constant.NetBundleKey.CONTENT_TYPE, Constant.NetBundleKey.APPLICATON_JSON);
                header.put("X-Token", UserInfoUtil.getToken(AdskApplication.getInstance()));
                return header;
            }
        };
        NetRequestManager.getInstance().addRequest(okRequest);
    }

    public void getFormWithIds(final String[] fids, final OkJsonRequest.OKResponseCallback callback){
        String url = UrlHelper.bindFormGetUrl(fids);
        OkJsonRequest okRequest = new OkJsonRequest(OkJsonRequest.Method.GET,url,null,callback){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put(Constant.NetBundleKey.CONTENT_TYPE, Constant.NetBundleKey.APPLICATON_JSON);
                header.put("X-Token", UserInfoUtil.getToken(AdskApplication.getInstance()));
                return header;
            }
        };
        NetRequestManager.getInstance().addRequest(okRequest);
    }

    public void updateForms(String projectId, String taskId, String userId, List<ContainedForm> formList){
        String url = UrlHelper.bindFormPutUrl();
    }

}
