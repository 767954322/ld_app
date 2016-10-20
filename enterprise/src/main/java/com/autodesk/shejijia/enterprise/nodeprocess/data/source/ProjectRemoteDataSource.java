package com.autodesk.shejijia.enterprise.nodeprocess.data.source;

import android.support.annotation.NonNull;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.enterprise.common.entity.ProjectListBean;
import com.autodesk.shejijia.enterprise.common.network.EnterpriseServerHttpManager;
import com.autodesk.shejijia.enterprise.common.entity.ProjectBean;
import com.autodesk.shejijia.enterprise.common.listener.LoadDataCallback;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.LogUtils;

import org.json.JSONObject;

/**
 * Created by t_xuz on 10/17/16.
 *
 */
public final class ProjectRemoteDataSource implements ProjectDataSource {

    private ProjectRemoteDataSource(){
    }

    private static class ProjectRemoteDataSourceHolder{
        private static final ProjectRemoteDataSource INSTANCE = new ProjectRemoteDataSource();
    }

    public static ProjectRemoteDataSource getInstance(){
        return ProjectRemoteDataSourceHolder.INSTANCE;
    }

    @Override
    public void getProjectList(String requestUrl, final String eventTag, String requestTag, @NonNull final LoadDataCallback<ProjectListBean> callback) {

        EnterpriseServerHttpManager.getInstance().getUserProjectLists(requestUrl, requestTag, new OkJsonRequest.OKResponseCallback() {

            @Override
            public void onResponse(JSONObject jsonObject) {
                LogUtils.d(jsonObject + "");
                String result = jsonObject.toString();
                ProjectListBean projectListBean = GsonUtil.jsonToBean(result, ProjectListBean.class);
                callback.onLoadSuccess(projectListBean);
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                callback.onLoadFailed(volleyError.getMessage());
            }
        });
    }

    @Override
    public void getProjectDetails(@NonNull LoadDataCallback<ProjectBean> callback) {

    }
}
