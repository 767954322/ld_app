package com.autodesk.shejijia.shared.components.form.data.source;

import android.support.annotation.NonNull;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.shared.components.common.network.OkJsonArrayRequest;
import com.autodesk.shejijia.shared.components.form.common.network.FormServerHttpManager;
import com.autodesk.shejijia.shared.components.common.utility.FormJsonFileUtil;
import com.autodesk.shejijia.shared.components.form.common.entity.ContainedForm;
import com.autodesk.shejijia.shared.components.common.listener.LoadDataCallback;

import org.json.JSONArray;

import java.util.List;

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
    public void getRemoteFormItemDetails(@NonNull final LoadDataCallback<List> callBack, String[] fid) {
        FormServerHttpManager.getInstance().getFormItemDetails(fid, new OkJsonArrayRequest.OKResponseCallback() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                callBack.onLoadFailed(volleyError.getMessage());
            }
            @Override
            public void onResponse(JSONArray jsonArray) {
                List jsonMapList = FormJsonFileUtil.jsonArray2List(jsonArray);
                callBack.onLoadSuccess(jsonMapList);
            }
        });
    }

    @Override
    public void updateRemoteFormItems(@NonNull LoadDataCallback callBack, String projectId, String taskId, List<ContainedForm> forms) {

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
