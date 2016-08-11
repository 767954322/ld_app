package com.autodesk.shejijia.shared.components.common.network;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.autodesk.shejijia.shared.components.common.appglobal.UrlMessagesContants;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yangxuewu .
 * @version v1.0 .
 * @date 2016-6-7 .
 * @file OkStringRequest.java .
 * @brief String对象请求.
 */
public class OkStringRequest extends StringRequest {

    public String getAcs_x_session() {
        return Acs_x_session;
    }

    public void setAcs_x_session(String acs_x_session) {
        Acs_x_session = acs_x_session;
    }

    public OkStringRequest(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener, String Acs_x_session) {
        super(method, url, listener, errorListener);
        this.Acs_x_session = Acs_x_session;
        this.setRetryPolicy(new DefaultRetryPolicy(300 * 1000, 0, 1.0f));
    }

    public OkStringRequest(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
        this.setRetryPolicy(new DefaultRetryPolicy(300 * 1000, 0, 1.0f));
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> map = new HashMap<>();
        map.put("Content-Type", "application/x-www-form-urlencoded");
        map.put("X-AFC", UrlMessagesContants.initializeMarketplaceWithAFC);
        if (Acs_x_session != null) {
            map.put("X-Session", Acs_x_session);
        }
        return map;
    }

    /**
     * seting acs-seccion
     *
     * @param method
     * @param url
     * @param callback
     * @param Acs_x_session
     */
    public OkStringRequest(int method, String url, OKResponseCallback callback, String Acs_x_session) {
        this(method, url, callback, callback, Acs_x_session);
    }

    /**
     * @param method
     * @param url
     * @param callback
     */
    public OkStringRequest(int method, String url, OKResponseCallback callback) {
        this(method, url, callback, callback);
    }

    /**
     * In response to the callback  .
     */
    public interface OKResponseCallback extends Response.Listener<String>, Response.ErrorListener {
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        String parsed;
        try {
            parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            //Allow null
            if (parsed == null || parsed.length() == 0) {
                return Response.success(null, HttpHeaderParser.parseCacheHeaders(response));
            }
        } catch (UnsupportedEncodingException var4) {
            parsed = new String(response.data);
        }

        return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
    }

    private String Acs_x_session;
}
