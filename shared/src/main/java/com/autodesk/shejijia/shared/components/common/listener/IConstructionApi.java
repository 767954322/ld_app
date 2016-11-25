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

    void getUserProjectLists(@NonNull Bundle requestParams, @Nullable String requestTag, @NonNull T callback);

    void getProjectDetails(@NonNull Bundle requestParams, @Nullable String requestTag, @NonNull T callback);

    void putProjectLikes(@NonNull Bundle requestParams, @Nullable String requestTag, @NonNull JSONObject jsonRequest, @NonNull T callback);

    void getPlanByProjectId(@NonNull String pid, @Nullable String requestTag, @NonNull T callback);

    void updatePlan(@NonNull String pid, @NonNull JSONObject jsonRequest, @NonNull Bundle requestParams, @Nullable String requestTag, @NonNull T callback);

}
