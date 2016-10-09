package com.autodesk.shejijia.shared.components.common.network;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by t_xuz on 10/9/16.
 * manager volley queue
 */
public class NetRequestManager {

    public static final String DEFAULT_REQUEST_TAG = "volley_request";

    private RequestQueue requestQueue = null;

    private NetRequestManager() {
    }

    private static class NetRequestManagerHolder {
        private static final NetRequestManager INSTANCE = new NetRequestManager();
    }

    public static NetRequestManager getInstance() {
        return NetRequestManagerHolder.INSTANCE;
    }

    public void init(Context context) {
        requestQueue = Volley.newRequestQueue(context);
    }

    /*
    * get requestQueue
    * */
    public RequestQueue getRequestQueue() {
        if (requestQueue != null) {
            return requestQueue;
        } else {
            throw new IllegalArgumentException("RequestQueue is not initialized.");
        }
    }

    public void addRequest(@NonNull Request request) {
        addRequest(null, request);
    }

    /*
    * add request to requestQueue
    * */
    public <T> void addRequest(@Nullable T tag, @NonNull Request request) {
        if (requestQueue != null) {
            if (tag == null) {
                request.setTag(DEFAULT_REQUEST_TAG);
            } else {
                request.setTag(tag);
            }
            requestQueue.add(request);
        } else {
            throw new IllegalArgumentException("RequestQueue is not initialized.");
        }
    }

    /*
    * cancel request by tag
    * */
    public <T> void cancelRequest(@NonNull T tag) {
        if (requestQueue != null) {
            requestQueue.cancelAll(tag);
        } else {
            throw new IllegalArgumentException("RequestQueue is not initialized.");
        }
    }

    /*
    * cancel request by tag if request confirm canceled
    * */
    public <T> void cancelRequest(@NonNull T tag, @NonNull Request request) {
        if (requestQueue != null) {
            if (!request.isCanceled()) {
                requestQueue.cancelAll(tag);
            }
        } else {
            throw new IllegalArgumentException("RequestQueue is not initialized.");
        }
    }
}
