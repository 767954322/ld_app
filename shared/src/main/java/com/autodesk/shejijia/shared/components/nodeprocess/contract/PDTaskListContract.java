package com.autodesk.shejijia.shared.components.nodeprocess.contract;

import android.support.v4.app.FragmentManager;

import com.autodesk.shejijia.shared.components.common.entity.microbean.Task;
import com.autodesk.shejijia.shared.framework.BasePresenter;
import com.autodesk.shejijia.shared.framework.BaseView;

import java.util.List;

/**
 * Created by t_xuz on 11/14/16.
 * 项目详情下的任务列表对应的vp接口
 */

public interface PDTaskListContract {

    interface View extends BaseView {

    }

    interface Presenter extends BasePresenter {

        void navigateToTaskDetail(FragmentManager fragmentManager, List<Task> taskList, int position); //跳转节点详情对话框

    }
}
