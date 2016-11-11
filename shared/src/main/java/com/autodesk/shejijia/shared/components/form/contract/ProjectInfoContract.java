package com.autodesk.shejijia.shared.components.form.contract;


import com.autodesk.shejijia.shared.components.common.entity.microbean.Task;
import com.autodesk.shejijia.shared.framework.BasePresenter;
import com.autodesk.shejijia.shared.framework.BaseView;

/**
 * Created by t_aij on 16/10/21.
 */

public interface ProjectInfoContract {

    interface View extends BaseView {
        void setToolbar();   //设置bar的显示

        void selectCancel();    //取消进入表格

        String getStatus();   //获取到task的状态
        Task getTask();       //获取到task

        void enterPrecheck(Task task);  //进入条件检查界面

        void dismiss();   //自身消失

    }

    interface Presenter extends BasePresenter {

        void confirm();   //确定进入
        void cancel();    //取消退出
    }

}
