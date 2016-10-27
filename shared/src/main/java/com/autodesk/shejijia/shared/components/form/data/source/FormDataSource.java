package com.autodesk.shejijia.shared.components.form.data.source;

import android.support.annotation.NonNull;

import com.autodesk.shejijia.shared.components.form.entity.ContainedForm;
import com.autodesk.shejijia.shared.components.form.listener.LoadDataCallBack;

import java.util.List;

/**
 * Created by t_panya on 16/10/20.
 * 表单获取data source,包括本地和网络
 */

public interface FormDataSource {

    void getRemoteFormItemDetails(@NonNull LoadDataCallBack<List> callBack, String[] fIds);

    void updateRemoteFormItems(@NonNull LoadDataCallBack callBack, String projectId, String taskId, List<ContainedForm> forms);

}
