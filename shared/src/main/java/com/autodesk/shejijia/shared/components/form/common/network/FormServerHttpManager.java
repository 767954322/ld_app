package com.autodesk.shejijia.shared.components.form.common.network;

import android.os.Bundle;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.autodesk.shejijia.shared.Config;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.network.NetRequestManager;
import com.autodesk.shejijia.shared.components.common.network.OkJsonArrayRequest;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.utility.UrlUtils;
import com.autodesk.shejijia.shared.components.common.utility.UserInfoUtils;
import com.autodesk.shejijia.shared.framework.AdskApplication;

import org.json.JSONObject;

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

    /**
     * 获取表单 组成Jsononarray形式的返回值
     * @param fid
     * @param callback
     */
    public void getFormItemDetails(final String[] fid, final OkJsonArrayRequest.OKResponseCallback callback){
        String url = UrlUtils.bindFormGetUrl(fid);
        OkJsonArrayRequest okRequest = new OkJsonArrayRequest(url,callback){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put("Accept", "application/json");
                header.put(Constant.NetBundleKey.CONTENT_TYPE, Constant.NetBundleKey.APPLICATON_JSON);
//                header.put("X-Token", UserInfoUtils.getToken(AdskApplication.getInstance()));
                header.put("X-Token","587e1e6bd9c26875535868dec8e3045c");
                return header;
            }
        };
        NetRequestManager.getInstance().addRequest(okRequest);
    }

    public void getFormWithIds(final String[] fids, final OkJsonRequest.OKResponseCallback callback){
        String url = UrlUtils.bindFormGetUrl(fids);
        OkJsonRequest okRequest = new OkJsonRequest(OkJsonRequest.Method.GET,url,null,callback){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put("Accept", "application/json");
                header.put("Content-type", "application/json;charset=UTF-8");
                header.put("X-Token", UserInfoUtils.getToken(AdskApplication.getInstance()));
                return header;
            }
        };
        NetRequestManager.getInstance().addRequest(okRequest);
    }

    /**
     * 上传mini表单
     * @param formList
     * @param bundle
     * @param callback
     */
    public void updateForms(List<Map> formList, Bundle bundle, final OkJsonRequest.OKResponseCallback callback){
        String url = Config.CONSTRUCTION_ISSUE_URL + "/forms";
        JSONObject object = combineJson(formList,bundle);
        OkJsonRequest okJsonRequest = new OkJsonRequest(Request.Method.PUT,url,object,callback){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put(Constant.NetBundleKey.CONTENT_TYPE, Constant.NetBundleKey.APPLICATON_JSON);
//                header.put("X-Token", UserInfoUtils.getToken(AdskApplication.getInstance()));
                header.put("X-Token","587e1e6bd9c26875535868dec8e3045c");

                return header;
            }
        };
        NetRequestManager.getInstance().addRequest(okJsonRequest);
    }

    private JSONObject combineJson(List<Map> formList, Bundle bundle) {
        Map<String,Object> putFormMap = new HashMap<>();
        putFormMap.put("project_id",bundle.get("project_id"));
        putFormMap.put("task_id",bundle.get("task_id"));
        putFormMap.put("user_id",bundle.get("user_id"));
        putFormMap.put("form_put_value",formList);
        JSONObject combineJson = new JSONObject(putFormMap);
        return combineJson;
    }

}
