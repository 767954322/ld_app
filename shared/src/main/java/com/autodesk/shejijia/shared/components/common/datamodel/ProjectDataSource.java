package com.autodesk.shejijia.shared.components.common.datamodel;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.autodesk.shejijia.shared.components.common.entity.Project;
import com.autodesk.shejijia.shared.components.common.entity.ProjectInfo;
import com.autodesk.shejijia.shared.components.common.entity.ProjectList;
import com.autodesk.shejijia.shared.components.common.entity.ResponseError;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Like;
import com.autodesk.shejijia.shared.components.common.entity.microbean.PlanInfo;
import com.autodesk.shejijia.shared.components.common.entity.microbean.UnreadMessageIssue;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Task;
import com.autodesk.shejijia.shared.components.common.listener.ResponseCallback;

import org.json.JSONObject;

import java.util.Map;

/**
 * Created by t_xuz on 10/17/16.
 * 主线所有与网络请求接口相关的
 */
public interface ProjectDataSource {

    /*
    ＊获取项目列表
    * */
    void getProjectList(Bundle requestParams, String requestTag, @NonNull ResponseCallback<ProjectList, ResponseError> callback);

    /*
    * 获取项目详情－－含任务详情列表
    * */
    void getProjectInfo(Bundle requestParams, String requestTag, @NonNull ResponseCallback<ProjectInfo, ResponseError> callback);

    /*
    * 获取项目详情－－含任务id列表
    * */
    void getProject(Bundle requestParams, String requestTag, @NonNull ResponseCallback<Project, ResponseError> callback);

    /*
    * 获取plan
    * */
    void getPlanByProjectId(String pid, String requestTag, @NonNull ResponseCallback<ProjectInfo, ResponseError> callback);

    /*
    * 更新plan
    * */
    void updatePlan(String pid, Bundle requestParams, String requestTag, @NonNull ResponseCallback<Project, ResponseError> callback);

    /*
    * 星标项目
    * */
    void updateProjectLikes(Bundle requestParams, String requestTag, JSONObject jsonRequest, @NonNull ResponseCallback<Like, ResponseError> callback);


    /*
    * 获取有问题有更新
    * */
    void getUnReadMessageAndIssue(String projectIds, String requestTag, @NonNull ResponseCallback<UnreadMessageIssue, ResponseError> callback);

    void getTask(Bundle requestParams, String requestTag, ResponseCallback<Task, ResponseError> callback);

    void reserveTask(Bundle requestParams, String requestTag, ResponseCallback<Void, ResponseError> callback);

    void submitTaskComment(Bundle requestParams, String requestTag, ResponseCallback<Void, ResponseError> callback);

    void confirmTask(Bundle requestParams, String requestTag, ResponseCallback<Void, ResponseError> callback);

    void uploadTaskFiles(Bundle requestParams, String requestTag, ResponseCallback<Void, ResponseError> callback);

	/**
     * 跟新Task的Status
     */
    void updateTaskStatus(Bundle requestParams, String requestTag, JSONObject jsonRequest, @NonNull ResponseCallback<Map<String,String>, ResponseError> callback);
}
