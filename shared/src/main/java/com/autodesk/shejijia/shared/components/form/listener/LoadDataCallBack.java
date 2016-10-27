package com.autodesk.shejijia.shared.components.form.listener;

import org.json.JSONException;

/**
 * Created by t_panya on 16/10/20.
 */

public interface LoadDataCallBack<T> {

    void onLoadSuccess(T data);

    void onLoadFailed(String errorMsg);
}
