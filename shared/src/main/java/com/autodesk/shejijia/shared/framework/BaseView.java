package com.autodesk.shejijia.shared.framework;

import com.autodesk.shejijia.shared.components.common.entity.ResponseError;

/**
 * Created by t_xuz on 10/10/16.
 * 说明：如果是网络请求错误，使用showNetError，如果是其它错误，使用showError
 */
public interface BaseView {

    void showNetError(ResponseError error);

    void showError(String errorMsg);

    void showLoading();

    void hideLoading();

}
