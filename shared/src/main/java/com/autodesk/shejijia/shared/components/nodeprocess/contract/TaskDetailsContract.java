package com.autodesk.shejijia.shared.components.nodeprocess.contract;

import com.autodesk.shejijia.shared.framework.BasePresenter;
import com.autodesk.shejijia.shared.framework.BaseView;

/**
 * Created by t_xuz on 11/11/16.
 */

public interface TaskDetailsContract {

    interface View extends BaseView {

    }

    interface Presenter extends BasePresenter {

        void fetchTaskDetails(); //获取节点详情


    }
}
