package com.autodesk.shejijia.shared.components.form.contract;

import com.autodesk.shejijia.shared.framework.BasePresenter;
import com.autodesk.shejijia.shared.framework.BaseView;

/**
 * Created by t_aij on 16/10/21.
 */
public interface ProjectIdCodeContract {

    interface View extends BaseView {
        void setToolbar();  //设置navigationBar

        String getProjectId();   //获取到projectid

        void dismiss();   //界面消失


    }


    interface Presenter extends BasePresenter {
        void confirmProject();  //确定登入

        void enterCode();   //进入扫码
    }

}
