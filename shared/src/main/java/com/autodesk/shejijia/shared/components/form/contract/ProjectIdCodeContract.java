package com.autodesk.shejijia.shared.components.form.contract;

import com.autodesk.shejijia.shared.components.common.entity.microbean.Building;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Member;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Task;
import com.autodesk.shejijia.shared.framework.BasePresenter;
import com.autodesk.shejijia.shared.framework.BaseView;

/**
 * Created by t_aij on 16/10/21.
 */
public interface ProjectIdCodeContract {

    interface View extends BaseView {

        String getProjectId();

        void enterProjectInfo(Task task, Building building, Member role);

    }


    interface Presenter extends BasePresenter {

        void enterProjectInfo();

    }

}
