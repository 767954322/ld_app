package com.autodesk.shejijia.shared.components.form.data.source;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.autodesk.shejijia.shared.components.common.entity.ResponseError;
import com.autodesk.shejijia.shared.components.common.listener.ResponseCallback;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * Created by t_panya on 16/10/20.
 * 表单获取data source,包括本地和网络
 */

public interface FormDataSource {

    void getRemoteFormItemDetails(@NonNull ResponseCallback<List, ResponseError> callBack, List<String> formIds);

    void verifyInspector(@NonNull Long pid,@NonNull ResponseCallback<Map, ResponseError> callBack);

    void inspectTask(@NonNull Bundle bundle, JSONObject jsonRequest,@NonNull ResponseCallback<Map, ResponseError> callback);
}
