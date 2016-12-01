package com.autodesk.shejijia.shared.components.common.uielements.commoncomment.contract;

import com.autodesk.shejijia.shared.components.common.uielements.photoselect.base.BasePresenter;
import com.autodesk.shejijia.shared.components.common.uielements.photoselect.base.BaseView;

/**
 * Created by t_panya on 16/11/29.
 */

public interface CommentContract {
    interface View {
        void setLayoutType();
        /**
         * 设置presenter
         * @param presenter
         */
        void setPresenter(Presenter presenter);

        /**
         * 用来显示提示信息，用Toast实现
         * @param message 消息体
         */
        void showToast(CharSequence message);
    }

    interface Presenter{
        /**
         * 一般在onResume（）方法中调用，执行一些数据初始化工作
         */
        void start();
    }
}
