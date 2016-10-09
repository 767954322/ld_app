package com.autodesk.shejijia.enterprise.base.network;

import com.android.volley.AuthFailureError;
import com.autodesk.shejijia.enterprise.common.utils.Constants;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.network.NetRequestManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by t_xuz on 8/16/16.
 * 与 服务端主线相关的 服务端api接口的封装类
 */
public class EnterpriseServerHttpManager {

    private EnterpriseServerHttpManager() {
    }

    private static class ServerHttpManagerHolder {
        private static final EnterpriseServerHttpManager INSTANCE = new EnterpriseServerHttpManager();
    }

    public static EnterpriseServerHttpManager getInstance() {
        return ServerHttpManagerHolder.INSTANCE;
    }

    /*
    * 获取项目列表api接口
    * */
    public void getProjectLists(final int offset,
                                final int limit,
                                final String token,
                                OkJsonRequest.OKResponseCallback callback) {

        String url = Constants.BASE_URL + "/projects?"
                + "like=0"
                + "&limit=" + limit
                + "&offset=" + offset;
        OkJsonRequest okRequest = new OkJsonRequest(OkJsonRequest.Method.GET, url, null, callback) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put(Constant.NetBundleKey.CONTENT_TYPE, Constant.NetBundleKey.APPLICATON_JSON);
                header.put("X-Token", token);
                return header;
            }
        };
        NetRequestManager.getInstance().addRequest(okRequest);
    }

    /*
    * 获取task列表中每个任务详情的api接口
    * pid 项目id
     *tid 任务id
    * */
    public void getTaskDetails(final long pid,
                               final String tid,
                               final String token,
                               OkJsonRequest.OKResponseCallback callback) {

        String url = Constants.BASE_URL + "/projects"
                + "/" + pid
                + "/tasks/" + tid;
        OkJsonRequest okRequest = new OkJsonRequest(OkJsonRequest.Method.GET, url, null, callback) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put(Constant.NetBundleKey.CONTENT_TYPE, Constant.NetBundleKey.APPLICATON_JSON);
                header.put("X-Token", token);
                return header;
            }
        };
        NetRequestManager.getInstance().addRequest("task_details",okRequest);
    }


    /*
    * 按日期查询任务列表
    * findDate 查询日期 (例:2016-08-08)
    * like 星标项目:0 所有 ,1 星标
    * limit 每页条数:为0时不启用分页
    * offset 当前页:从0开始
    * token 当前登陆用户的access token
    * callback 请求回调接口
    * */
    public void getTaskLists(final String findDate,
                             final int like,
                             final int limit,
                             final int offset,
                             final String token,
                             OkJsonRequest.OKResponseCallback callback) {
        String url = Constants.BASE_URL + "/tasks?"
                + "findDate=" + findDate
                + "&like=" + like
                + "&limit=" + limit
                + "&offset=" + offset;
        OkJsonRequest okRequest = new OkJsonRequest(OkJsonRequest.Method.GET, url, null, callback) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put(Constant.NetBundleKey.CONTENT_TYPE, Constant.NetBundleKey.APPLICATON_JSON);
                header.put("X-Token", token);
                return header;
            }
        };
        NetRequestManager.getInstance().addRequest(okRequest);
    }


    /*
    * 获取 plan 详情的接口,里面包括项目列表里的任务列表
    * */
    public void getPlanDetails(final long pid,
                               final String token,
                               OkJsonRequest.OKResponseCallback callback) {
        String url = Constants.BASE_URL + "/projects"
                + "/" + pid
                + "/plan";
        OkJsonRequest okRequest = new OkJsonRequest(OkJsonRequest.Method.GET, url, null, callback) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put(Constant.NetBundleKey.CONTENT_TYPE, Constant.NetBundleKey.APPLICATON_JSON);
                header.put("X-Token", token);
                return header;
            }
        };
        NetRequestManager.getInstance().addRequest(okRequest);
    }

    /*
    * 获取 project 详情的接口,含 task 详情的
    * */
    public void getProjectDetails(final long pid,
                                  final String token,
                                  OkJsonRequest.OKResponseCallback callback){
        String url = Constants.BASE_URL + "/projects"
                + "/" + pid
                + "?task_data=true";
        OkJsonRequest okRequest = new OkJsonRequest(OkJsonRequest.Method.GET, url, null, callback) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put(Constant.NetBundleKey.CONTENT_TYPE, Constant.NetBundleKey.APPLICATON_JSON);
                header.put("X-Token", token);
                return header;
            }
        };
        NetRequestManager.getInstance().addRequest(okRequest);
    }
}
