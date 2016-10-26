package com.autodesk.shejijia.shared.components.common.network;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.autodesk.shejijia.shared.framework.AdskApplication;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by t_panya on 16/10/25.
 */

public class OkJsonArrayRequest extends JsonArrayRequest {
    public OkJsonArrayRequest(String url, Response.Listener<JSONArray> listener, Response.ErrorListener errorListener) {
        super(url, listener, errorListener);
        this.setRetryPolicy(new DefaultRetryPolicy(30 * 1000, 1, 1.0f));
    }

    /**
     *
     * @param url
     * @param callback
     */
    public OkJsonArrayRequest(String url, OkJsonArrayRequest.OKResponseCallback callback) {
        this(url,callback, callback);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        HashMap<String, String> header = new HashMap<>();
        header.put("Accept", "application/json");
        header.put("Content-type", "application/json;charset=UTF-8");
        return header;
    }

    public interface  OKResponseCallback extends Response.Listener<JSONArray> , Response.ErrorListener{

    }

    @Override
    public Request<?> setCacheEntry(Cache.Entry entry) {
        return super.setCacheEntry(entry);
    }

    @Override
    protected Response<JSONArray> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers));
            //Allow null
            if (jsonString == null || jsonString.length() == 0) {
                return Response.success(null,
                        HttpHeaderParser.parseCacheHeaders(response));
            } else {
                return Response.success(new JSONArray(jsonString),
                        HttpHeaderParser.parseCacheHeaders(response));
            }
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException je) {
            return Response.error(new ParseError(je));
        }
    }

    /**
     * 取消请求
     *
     * @param tag 请求TAG
     */
    public static void cancelRequest(Object tag) {
        if (AdskApplication.getInstance().queue != null) {
            AdskApplication.getInstance().queue.cancelAll(tag);//从请求队列中取消对应的任务
        }
    }
}

