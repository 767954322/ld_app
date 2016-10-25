package com.autodesk.shejijia.enterprise.nodeprocess.data.source;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.autodesk.shejijia.shared.components.common.entity.ProjectInfo;
import com.autodesk.shejijia.shared.components.common.entity.ProjectList;
import com.autodesk.shejijia.shared.components.common.listener.LoadDataCallback;

/**
 * Created by t_xuz on 10/17/16.
 * 主线所有与网络请求接口相关的
 */
public interface ProjectDataSource {

    /*
    ＊获取项目列表
    * */
    void getProjectList(Bundle requestParams, String requestTag, @NonNull LoadDataCallback<ProjectList> callback);

    /*
    * 获取项目详情
    * */
    void getProjectDetails(@NonNull LoadDataCallback<ProjectInfo> callback);

}
