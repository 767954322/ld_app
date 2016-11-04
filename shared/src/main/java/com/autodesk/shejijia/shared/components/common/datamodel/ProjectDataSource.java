package com.autodesk.shejijia.shared.components.common.datamodel;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.autodesk.shejijia.shared.components.common.entity.Project;
import com.autodesk.shejijia.shared.components.common.entity.ProjectInfo;
import com.autodesk.shejijia.shared.components.common.entity.ProjectList;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Plan;
import com.autodesk.shejijia.shared.components.common.entity.microbean.PlanInfo;

import com.autodesk.shejijia.shared.components.common.entity.microbean.Like;
import com.autodesk.shejijia.shared.components.common.listener.LoadDataCallback;
import com.autodesk.shejijia.shared.components.common.listener.UpdateDataCallback;

import org.json.JSONObject;

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
    * 获取项目详情－－含任务详情列表
    * */
    void getProjectTaskData(Bundle requestParams, String requestTag, @NonNull LoadDataCallback<ProjectInfo> callback);

    /*
    * 获取项目详情－－含任务id列表
    * */
    void getProjectTaskId(Bundle requestParams, String requestTag, @NonNull LoadDataCallback<Project> callback);


    void getPlanByProjectId(String pid, String requestTag, @NonNull LoadDataCallback<PlanInfo> callback);

    /*
    * 星标项目
    * */
    void onStarProject(Bundle requestParams, String requestTag, JSONObject jsonRequest, @NonNull UpdateDataCallback<Like> callback);


}
