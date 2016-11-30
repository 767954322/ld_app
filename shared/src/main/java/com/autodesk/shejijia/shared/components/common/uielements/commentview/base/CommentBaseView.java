package com.autodesk.shejijia.shared.components.common.uielements.commentview.base;

/**
 * Created by t_panya on 16/12/5.
 */

public interface CommentBaseView<T> {
    /**
     *
     * @param presenter
     */
    void setPresenter(T presenter);

    /**
     *
     * @param message
     */
    void showToast(String message);
}
