package com.autodesk.shejijia.shared.components.common.uielements.swiperecyclerview.footerview;

/**
 * Created by t_xuz on 11/24/16.
 * swipeRefreshView footerView listener
 */

public interface FooterViewListener {

    void onNetChange(String message); //网络状态变化时

    void onLoadingMore(); //正在loading状态

    void onNoMore(String message);  //没有更多数据状态

    void onError(String message);   //加载失败状态
}
