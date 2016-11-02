package com.autodesk.shejijia.shared.components.nodeprocess.contract;

import com.autodesk.shejijia.shared.framework.BasePresenter;
import com.autodesk.shejijia.shared.framework.BaseView;

/**
 * Created by t_xuz on 11/1/16.
 * 施工主线mvp中的vp对应的接口--项目详情页面
 */

public interface ProjectDetailsContract {

    interface View extends BaseView {

    }

    interface Presenter extends BasePresenter {

        void initRequestParams(long projectId, boolean isHasTaskData);

        void getProjectDetails();

    }
}
