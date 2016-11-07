package com.autodesk.shejijia.shared.components.common.listener;

/**
 * Created by t_xuz on 10/19/16.
 *
 * 获取网络数据接口回调 －－ 对应get请求类型
 */
public interface ResponseCallback<T> {

    void onSuccess(T data);

    void onError(String errorMsg);

}
