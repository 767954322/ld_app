package com.autodesk.shejijia.shared.components.form.data.source;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.autodesk.shejijia.shared.components.common.entity.Project;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Task;
import com.autodesk.shejijia.shared.components.common.listener.LoadDataCallback;

/**
 * Created by t_aij on 16/11/1.
 * 除表格之外的接口调用方法
 */

public interface OtherDataSource {

    /**
     * 获取项目的详情(默认不包含具体的task详情)
     * @param callback
     */
    void getProjectDetail(@NonNull LoadDataCallback<Project> callback,Bundle requestParams);

    /**
     * 获取具体的task的详情
     * @param callback
     * @param requestParams
     */
    void getTaskDetail(@NonNull LoadDataCallback<Task> callback,Bundle requestParams);

}
