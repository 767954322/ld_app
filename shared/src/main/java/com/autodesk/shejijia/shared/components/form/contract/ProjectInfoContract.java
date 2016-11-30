package com.autodesk.shejijia.shared.components.form.contract;


import com.autodesk.shejijia.shared.components.common.entity.microbean.Task;
import com.autodesk.shejijia.shared.framework.BasePresenter;
import com.autodesk.shejijia.shared.framework.BaseView;

/**
 * Created by t_aij on 16/10/21.
 */

public interface ProjectInfoContract {

    interface View extends BaseView {

        void enterPrecheck(Task task);
    }

    interface Presenter extends BasePresenter {

        void submit(Task task);

    }

}
