package com.autodesk.shejijia.shared.components.form.data.source;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.shared.components.common.entity.ResponseError;
import com.autodesk.shejijia.shared.components.common.listener.ResponseCallback;
import com.autodesk.shejijia.shared.components.common.network.OkJsonArrayRequest;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.utility.JsonFileUtil;
import com.autodesk.shejijia.shared.components.common.utility.LogUtils;
import com.autodesk.shejijia.shared.components.common.utility.ResponseErrorUtil;
import com.autodesk.shejijia.shared.components.form.common.network.FormServerHttpManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
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
    public void getRemoteFormItemDetails(@NonNull final ResponseCallback<List,ResponseError> callBack, List<String> formIds) {
        FormServerHttpManager.getInstance().getFormItemDetails(formIds, new OkJsonArrayRequest.OKResponseCallback() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                callBack.onError(ResponseErrorUtil.checkVolleyError(volleyError));
            }
            @Override
            public void onResponse(JSONArray jsonArray) {
                List jsonMapList = JsonFileUtil.jsonArray2List(jsonArray);
                callBack.onSuccess(jsonMapList);
            }
        });
    }

    @Override
    public void verifyInspector(@NonNull Long pid, @NonNull final ResponseCallback<Map, ResponseError> callBack) {
        FormServerHttpManager.getInstance().verifyInspector(pid,new OkJsonRequest.OKResponseCallback() {

            @Override
            public void onResponse(JSONObject jsonObject) {
                Map<String,Object> map = new HashMap<>();
                try {
                    map.put("project_id",jsonObject.get("project_id"));
                    map.put("allow_inspect",jsonObject.get("allow_inspect"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                callBack.onSuccess(map);
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                callBack.onError(ResponseErrorUtil.checkVolleyError(volleyError));
            }
        });
    }

    @Override
    public void inspectTask(@NonNull Bundle bundle, JSONObject jsonRequest, @NonNull final ResponseCallback callback) {
        FormServerHttpManager.getInstance().inspectTask(bundle,jsonRequest,new OkJsonRequest.OKResponseCallback() {

            @Override
            public void onResponse(JSONObject jsonObject) {
                LogUtils.d("inspectTask", jsonObject.toString());
                Map<String,Object> map = new HashMap<>();
                try {
                    map.put("project_id",jsonObject.get("project_id"));
                    map.put("task_id",jsonObject.get("task_id"));
                    map.put("subtask_id",jsonObject.get("subject_id"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                callback.onSuccess(map);
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                callback.onError(ResponseErrorUtil.checkVolleyError(volleyError));
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

}
