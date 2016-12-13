package com.autodesk.shejijia.shared.components.form.common.network;

import android.os.Bundle;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.autodesk.shejijia.shared.Config;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.appglobal.ConstructionConstants;
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

    private FormServerHttpManager() {

    }

    private static class ServerHttpManagerHolder {
        private static final FormServerHttpManager INSTANCE = new FormServerHttpManager();
    }

    public static FormServerHttpManager getInstance() {
        return ServerHttpManagerHolder.INSTANCE;
    }

    /**
     * 获取表单 组成Jsononarray形式的返回值
     *
     * @param formIds
     * @param callback
     */
    public void getFormItemDetails(final List<String> formIds, final OkJsonArrayRequest.OKResponseCallback callback) {
        String url = UrlUtils.bindFormGetUrl(formIds);
        OkJsonArrayRequest okRequest = new OkJsonArrayRequest(url, callback) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put("Accept", "application/json");
                header.put(Constant.NetBundleKey.CONTENT_TYPE, Constant.NetBundleKey.APPLICATON_JSON);
                header.put("X-Token", UserInfoUtils.getToken(AdskApplication.getInstance()));
//                header.put("X-Token","587e1e6bd9c26875535868dec8e3045c");
                return header;
            }
        };
        NetRequestManager.getInstance().addRequest(okRequest);
    }

    /**
     * 上传mini表单
     *
     * @param formList
     * @param bundle
     * @param callback
     */
    public void updateForms(List<Map> formList, Bundle bundle, final OkJsonRequest.OKResponseCallback callback) {
        String url = Config.CONSTRUCTION_MAIN_URL + "/forms";
        JSONObject object = combineJson(formList, bundle);
        OkJsonRequest okJsonRequest = new OkJsonRequest(Request.Method.PUT, url, object, callback) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put("X-Token", UserInfoUtils.getToken(AdskApplication.getInstance()));
//                header.put("X-Token","587e1e6bd9c26875535868dec8e3045c");

                return header;
            }
        };
        NetRequestManager.getInstance().addRequest(okJsonRequest);
    }

    /**
     * 监理是否有资格检验该项目
     */
    public void verifyInspector(Long pid, OkJsonRequest.OKResponseCallback callback) {
        String url = ConstructionConstants.BASE_URL + "/projects/" + pid + "/inspect_verify";
        OkJsonRequest okJsonRequest = new OkJsonRequest(Request.Method.GET, url, null, callback) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put("Accept", "application/json");
                header.put(Constant.NetBundleKey.CONTENT_TYPE, Constant.NetBundleKey.APPLICATON_JSON);
                header.put("X-Token", UserInfoUtils.getToken(AdskApplication.getInstance()));
                return header;
            }
        };

        NetRequestManager.getInstance().addRequest(okJsonRequest);

    }

    /**
     *检测表格
     */
    public void inspectTask(Bundle bundle, JSONObject jsonRequest, OkJsonRequest.OKResponseCallback callback) {
        String url = ConstructionConstants.BASE_URL + "/projects/" + bundle.getLong("pid")
                + "/tasks/" + bundle.getString("tid") + "/inspect";
        OkJsonRequest okJsonRequest = new OkJsonRequest(Request.Method.PUT, url, jsonRequest, callback) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put("Accept", "application/json");
                header.put("X-Token", UserInfoUtils.getToken(AdskApplication.getInstance()));
                return header;
            }
        };

        NetRequestManager.getInstance().addRequest(okJsonRequest);
    }


    private JSONObject combineJson(List<Map> formList, Bundle bundle) {
        Map<String, Object> putFormMap = new HashMap<>();
        putFormMap.put("user_id", bundle.get("user_id"));
        putFormMap.put("form_put_value", formList);
        JSONObject combineJson = new JSONObject(putFormMap);
        return combineJson;
    }

}
