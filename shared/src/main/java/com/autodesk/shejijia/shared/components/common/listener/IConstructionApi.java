package com.autodesk.shejijia.shared.components.common.listener;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.json.JSONObject;

/**
 * Created by t_xuz on 11/25/16.
 * 施工平台 －－ 网络请求接口
 */

public interface IConstructionApi<T> {

    /*
    * 获取项目列表接口
    * */
    void getUserProjectLists(@NonNull Bundle requestParams, @Nullable String requestTag, @NonNull T callback);

    /*
    * 获取项目详情接口
    * */
    void getProjectDetails(@NonNull Bundle requestParams, @Nullable String requestTag, @NonNull T callback);

    /*
    * 更新星标项目接口
    * */
    void putProjectLikes(@NonNull Bundle requestParams, @Nullable String requestTag, @NonNull JSONObject jsonRequest, @NonNull T callback);

    /*
    * 获取项目plan接口
    * */
    void getPlanByProjectId(@NonNull String pid, @Nullable String requestTag, @NonNull T callback);

    /*
    * 更新项目plan接口
    * */
    void updatePlan(@NonNull String pid, @NonNull JSONObject jsonRequest, @NonNull Bundle requestParams, @Nullable String requestTag, @NonNull T callback);

}
