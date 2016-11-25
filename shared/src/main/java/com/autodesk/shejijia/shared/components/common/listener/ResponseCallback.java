package com.autodesk.shejijia.shared.components.common.listener;

/**
 * Created by t_xuz on 10/19/16.
 * <p>
 * 获取网络数据接口回调
 */
public interface ResponseCallback<T, V> {

    void onSuccess(T data);

    void onError(V error);

}
