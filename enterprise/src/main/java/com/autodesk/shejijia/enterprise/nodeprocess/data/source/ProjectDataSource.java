package com.autodesk.shejijia.enterprise.nodeprocess.data.source;

import android.support.annotation.NonNull;

import com.autodesk.shejijia.enterprise.common.entity.ProjectBean;
import com.autodesk.shejijia.enterprise.common.entity.ProjectListBean;
import com.autodesk.shejijia.enterprise.nodeprocess.data.LoadDataCallback;

/**
 * Created by t_xuz on 10/17/16.
 * 主线所有与网络请求接口相关的
 */
public interface ProjectDataSource {

    /*
    ＊获取项目列表
    * */
    void getProjectList(String requestUrl, final String eventTag, String requestTag,@NonNull LoadDataCallback<ProjectListBean> callback);

    /*
    * 获取项目详情
    * */
    void getProjectDetails(@NonNull LoadDataCallback<ProjectBean> callback);

}
