package com.autodesk.shejijia.shared.components.common.datamodel;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.shared.components.common.entity.Project;
import com.autodesk.shejijia.shared.components.common.entity.ProjectList;
import com.autodesk.shejijia.shared.components.common.network.ConstructionHttpManager;
import com.autodesk.shejijia.shared.components.common.entity.ProjectInfo;
import com.autodesk.shejijia.shared.components.common.listener.LoadDataCallback;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.LogUtils;

import org.json.JSONObject;

/**
 * Created by t_xuz on 10/17/16.
 * 与 project 相关的api对应的dataModel
 */
public final class ProjectRemoteDataSource implements ProjectDataSource {

    private ProjectRemoteDataSource() {
    }

    private static class ProjectRemoteDataSourceHolder {
        private static final ProjectRemoteDataSource INSTANCE = new ProjectRemoteDataSource();
    }

    public static ProjectRemoteDataSource getInstance() {
        return ProjectRemoteDataSourceHolder.INSTANCE;
    }

    @Override
    public void getProjectList(Bundle requestParams, String requestTag, @NonNull final LoadDataCallback<ProjectList> callback) {

        ConstructionHttpManager.getInstance().getUserProjectLists(requestParams, requestTag, new OkJsonRequest.OKResponseCallback() {

            @Override
            public void onResponse(JSONObject jsonObject) {
                LogUtils.d("ProjectList--", jsonObject + "");
                String result = jsonObject.toString();
                ProjectList projectList = GsonUtil.jsonToBean(result, ProjectList.class);
                callback.onLoadSuccess(projectList);
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                callback.onLoadFailed(volleyError.getMessage());
            }
        });
    }

    @Override
    public void getProjectDetails(final Bundle requestParams, final String requestTag, @NonNull final LoadDataCallback callback) {
        ConstructionHttpManager.getInstance().getProjectDetails(requestParams, requestTag, new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                LogUtils.d("ProjectDetails--", jsonObject + "");
                String result = jsonObject.toString();
                boolean isHasTaskData = requestParams.getBoolean("task_data");

                if (isHasTaskData) {//含有任务详情的
                    ProjectInfo projectInfo = GsonUtil.jsonToBean(result, ProjectInfo.class);
                    callback.onLoadSuccess(projectInfo);
                } else {//不含任务详情的
                    Project project = GsonUtil.jsonToBean(result, Project.class);
                    callback.onLoadSuccess(project);
                }
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                callback.onLoadFailed(volleyError.getMessage());
            }
        });
    }
}
