package com.autodesk.shejijia.shared.components.form.data.source;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.shared.components.common.entity.Project;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Task;
import com.autodesk.shejijia.shared.components.common.listener.LoadDataCallback;
import com.autodesk.shejijia.shared.components.common.network.ConstructionHttpManager;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.ResponseErrorUtil;

import org.json.JSONObject;

/**
 * Created by t_aij on 16/11/1.
 */

public class OtherRemoteDataSource implements OtherDataSource {
    private OtherRemoteDataSource(){
    }
    private static class OtherRemoteDataSourceHolder{
        private final static OtherRemoteDataSource instance = new OtherRemoteDataSource();
    }
    public static OtherRemoteDataSource getInstance(){
        return OtherRemoteDataSourceHolder.instance;
    }


    @Override
    public void getProjectDetail(@NonNull final LoadDataCallback<Project> callback, Bundle requestParams) {
        Long pid = requestParams.getLong("pid");
        String token = requestParams.getString("token");
        boolean task_data = requestParams.getBoolean("task_data",false);
        if (TextUtils.isEmpty(String.valueOf(pid)) && TextUtils.isEmpty(token)) {
            callback.onLoadFailed("项目pid或者token为空");
            return;
        }

        ConstructionHttpManager.getInstance().getProjectDetails(pid, token, task_data, new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                ResponseErrorUtil.checkVolleyError(volleyError,callback);
            }

            @Override
            public void onResponse(JSONObject jsonObject) {
                Project project = GsonUtil.jsonToBean(jsonObject.toString(), Project.class);
                callback.onLoadSuccess(project);
            }
        });



    }



    @Override
    public void getTaskDetail(@NonNull final LoadDataCallback<Task> callback, Bundle requestParams) {
        long pid = requestParams.getLong("pid");
        String tid = requestParams.getString("tid");
        String token = requestParams.getString("token");
        if (TextUtils.isEmpty(String.valueOf(pid)) && TextUtils.isEmpty(tid) && TextUtils.isEmpty(token)) {
            callback.onLoadFailed("项目pid,任务tid或者token能为空");
            return;
        }

        ConstructionHttpManager.getInstance().getTaskDetails(pid, tid, token, new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                ResponseErrorUtil.checkVolleyError(volleyError,callback);
            }

            @Override
            public void onResponse(JSONObject jsonObject) {
                Task task = GsonUtil.jsonToBean(jsonObject.toString(),Task.class);
                callback.onLoadSuccess(task);
            }
        });
    }
}
