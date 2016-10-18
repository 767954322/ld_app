package com.autodesk.shejijia.enterprise.nodeprocess.data.source;

import android.support.annotation.NonNull;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.enterprise.common.network.EnterpriseServerHttpManager;
import com.autodesk.shejijia.enterprise.nodeprocess.data.entity.TaskListBean;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.LogUtils;

import org.json.JSONObject;

/**
 * Created by t_xuz on 10/17/16.
 *
 */
public final class NodeProcessRemoteDataSource implements NodeProcessDataSource {

    private NodeProcessRemoteDataSource(){
    }

    private static class NodeProcessRemoteDataSourceHolder{
        private static final NodeProcessRemoteDataSource INSTANCE = new NodeProcessRemoteDataSource();
    }

    public static NodeProcessRemoteDataSource getInstance(){
        return NodeProcessRemoteDataSourceHolder.INSTANCE;
    }

    @Override
    public void getProjectList(String requestUrl, final String eventTag, String requestTag, @NonNull final LoadProjectListCallback callback) {

        EnterpriseServerHttpManager.getInstance().getUserProjectLists(requestUrl, requestTag, new OkJsonRequest.OKResponseCallback() {

            @Override
            public void onResponse(JSONObject jsonObject) {
                LogUtils.d(jsonObject + "");
                String result = jsonObject.toString();
                TaskListBean taskListBean = GsonUtil.jsonToBean(result, TaskListBean.class);
                callback.onProjectListLoadSuccess(taskListBean);
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                callback.onProjectListLoadFailed(volleyError.getMessage());
            }
        });
    }

    @Override
    public void getProjectDetails(@NonNull GetProjectDetailsCallback callback) {

    }
}
