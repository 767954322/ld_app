package com.autodesk.shejijia.shared.components.form.data.source;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.shared.components.common.listener.ResponseCallback;
import com.autodesk.shejijia.shared.components.common.network.OkJsonArrayRequest;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.utility.ResponseErrorUtil;
import com.autodesk.shejijia.shared.components.form.common.network.FormServerHttpManager;
import com.autodesk.shejijia.shared.components.common.utility.FormJsonFileUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * Created by t_panya on 16/10/21.
 */

public class FormRemoteDataSource implements FormDataSource {

    private FormRemoteDataSource(){

    }

    private static class FormRemoteDataSourceHolder{
        private static final FormRemoteDataSource INSTANCE = new FormRemoteDataSource();
    }

    public static FormRemoteDataSource getInstance(){
        return FormRemoteDataSourceHolder.INSTANCE;
    }

    @Override
    public void getRemoteFormItemDetails(@NonNull final ResponseCallback<List> callBack, List<String> formIds) {
        FormServerHttpManager.getInstance().getFormItemDetails(formIds, new OkJsonArrayRequest.OKResponseCallback() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                callBack.onError(ResponseErrorUtil.checkVolleyError(volleyError));
            }
            @Override
            public void onResponse(JSONArray jsonArray) {
                List jsonMapList = FormJsonFileUtil.jsonArray2List(jsonArray);
                callBack.onSuccess(jsonMapList);
            }
        });
    }

    public void updateFormDataWithData(List<Map> forms, Bundle bundle, @NonNull final ResponseCallback callBack) {
        FormServerHttpManager.getInstance().updateForms(forms, bundle, new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                callBack.onError(ResponseErrorUtil.checkVolleyError(volleyError));
            }

            @Override
            public void onResponse(JSONObject jsonObject) {
                callBack.onSuccess(jsonObject);
            }
        });
    }

//
//    @Override
//    public void getRemoteFormItemDetails(@NonNull final LoadDataCallBack<Map> callBack, String[] fIds) {
//        FormServerHttpManager.getInstance().getFormWithIds(fIds, new OkJsonRequest.OKResponseCallback() {
//            @Override
//            public void onErrorResponse(VolleyError volleyError) {
//                callBack.onLoadFailed(volleyError.getMessage());
//            }
//
//            @Override
//            public void onResponse(JSONObject jsonObject) {
//                Map map = FormJsonFileUtil.jsonObj2Map(jsonObject);
//                callBack.onLoadSuccess(map);
//            }
//        });
//    }

}
