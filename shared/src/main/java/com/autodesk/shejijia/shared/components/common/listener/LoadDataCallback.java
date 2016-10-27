package com.autodesk.shejijia.shared.components.common.listener;

/**
 * Created by t_xuz on 10/19/16.
 *
 * 获取网络数据接口回调
 */
public interface LoadDataCallback<T> {

    void onLoadSuccess(T data);

    void onLoadFailed(String errorMsg);

}
