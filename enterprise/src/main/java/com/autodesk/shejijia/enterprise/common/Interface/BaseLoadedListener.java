package com.autodesk.shejijia.enterprise.common.Interface;

/**
 * Created by t_xuz on 10/11/16.
 * 加载数据是否成功的回调接口
 */
public interface BaseLoadedListener<T> {

    /*
    * when data callback success
    * */
    void onSuccess(String eventTag,T data);

    /*
    * when data callback error
    * */
    void onError(String msg);
}
