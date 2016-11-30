package com.autodesk.shejijia.shared.components.form.contract;

import com.autodesk.shejijia.shared.components.common.entity.microbean.Task;
import com.autodesk.shejijia.shared.framework.BasePresenter;
import com.autodesk.shejijia.shared.framework.BaseView;

/**
 * Created by t_aij on 16/11/28.
 */

public interface FormContract {

    interface View extends BaseView {

        void initFormList(String title);

    }

    interface Presenter extends BasePresenter {

        void show(Task task);   //通过具体的任务,展现后以后的数据
    }


}
