package com.autodesk.shejijia.shared.components.common.listener;

/**
 * Created by t_xuz on 11/4/16.
 * 更新服务器接口回调接口－－对应api的post请求类型
 */

public interface AddDataCallback<T> {

    void onAddSuccess(T data);

    void onAddFailed(String errorMsg);

}
