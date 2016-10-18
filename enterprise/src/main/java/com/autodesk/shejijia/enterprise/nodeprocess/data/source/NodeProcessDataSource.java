package com.autodesk.shejijia.enterprise.nodeprocess.data.source;

import android.support.annotation.NonNull;

import com.autodesk.shejijia.enterprise.nodeprocess.data.entity.TaskListBean;

/**
 * Created by t_xuz on 10/17/16.
 * 主线所有与网络请求接口相关的
 */
public interface NodeProcessDataSource {

    /*
    * 获取项目列表接口回调
    * */
    interface LoadProjectListCallback {

        void onProjectListLoadSuccess(TaskListBean taskList);

        void onProjectListLoadFailed(String errorMsg);
    }

    /*
    * 获取项目详情接口回调
    * */
    interface GetProjectDetailsCallback {

        void onProjectDetailsLoadSuccess();

        void onProjectDetailsLoadFailed();
    }


    void getProjectList(String requestUrl, final String eventTag, String requestTag,@NonNull LoadProjectListCallback callback);

    void getProjectDetails(@NonNull GetProjectDetailsCallback callback);

}
