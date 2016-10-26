package com.autodesk.shejijia.shared.components.form.data.source;

import android.support.annotation.NonNull;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.shared.components.common.network.OkJsonArrayRequest;
import com.autodesk.shejijia.shared.components.form.common.network.FormServerHttpManager;
import com.autodesk.shejijia.shared.components.form.listener.LoadDataCallBack;

import org.json.JSONArray;

/**
 * Created by t_panya on 16/10/21.
 */

public class FormRemoteDataSource implements FormDataSource {
    @Override
    public void getRemoteFormItemDetails(@NonNull final LoadDataCallBack<JSONArray> callBack, String[] fid) {
        FormServerHttpManager.getInstance().getFormItemDetails(fid, new OkJsonArrayRequest.OKResponseCallback() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                callBack.onLoadFailed(volleyError.getMessage());
            }

            @Override
            public void onResponse(JSONArray jsonArray) {
                callBack.onLoadSuccess(jsonArray);
            }
        });
    }

    @Override
    public void getLocalFormItemDetails(@NonNull LoadDataCallBack callBack, String[] fileName) {

    }

    @Override
    public void getLocalFormItemDetails(@NonNull LoadDataCallBack callBack, String fileName) {

    }

}
