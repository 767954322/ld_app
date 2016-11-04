package com.autodesk.shejijia.shared.components.common.listener;

/**
 * Created by t_xuz on 11/4/16.
 * 更新服务器接口回调接口－－对应api的put请求类型
 */

public interface DeleteDataCallback<T> {

    void onDeleteSuccess(T data);

    void onDeleteFailed(String errorMsg);

}
