package com.autodesk.shejijia.shared.components.form.data.source;

import android.support.annotation.NonNull;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.shared.components.common.network.OkJsonArrayRequest;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.form.common.network.FormServerHttpManager;
import com.autodesk.shejijia.shared.components.form.common.utils.FormJsonFileUtil;
import com.autodesk.shejijia.shared.components.form.entity.ContainedForm;
import com.autodesk.shejijia.shared.components.form.listener.LoadDataCallBack;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by t_panya on 16/10/21.
 */

public class FormRemoteDataSource implements FormDataSource {
    @Override
    public void getRemoteFormItemDetails(@NonNull final LoadDataCallBack<List> callBack, String[] fid) {
        FormServerHttpManager.getInstance().getFormItemDetails(fid, new OkJsonArrayRequest.OKResponseCallback() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                callBack.onLoadFailed(volleyError.getMessage());
            }
            @Override
            public void onResponse(JSONArray jsonArray) {
                List modelList = FormJsonFileUtil.jsonArray2ModelList(jsonArray, ContainedForm.class);
                callBack.onLoadSuccess(modelList);
            }
        });
    }

    @Override
    public void updateRemoteFormItems(@NonNull LoadDataCallBack callBack, String projectId, String taskId, List<ContainedForm> forms) {

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
