package com.autodesk.shejijia.enterprise.base.network;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * Created by t_xuz on 8/24/16.
 *
 */
public class MyOkJsonRequest extends OkJsonRequest{

    public MyOkJsonRequest(int method, String url, JSONObject jsonRequest, Response.Listener<JSONObject> successListener, Response.ErrorListener errorListener) {
        super(method, url, jsonRequest, successListener, errorListener);
    }

    public MyOkJsonRequest(int method, String url, JSONObject jsonRequest, OKResponseCallback callback) {
        super(method, url, jsonRequest, callback);
    }

    /*
    * 指定编码方式为utf-8,避免中文乱码
    * */
    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data, "UTF-8");
            return Response.success(new JSONObject(jsonString),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException je) {
            return Response.error(new ParseError(je));
        }

    }
}
