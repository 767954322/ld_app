package com.autodesk.shejijia.shared.components.nodeprocess.contract;

import com.autodesk.shejijia.shared.components.common.entity.microbean.PlanInfo;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Task;
import com.autodesk.shejijia.shared.components.nodeprocess.presenter.EditTaskNodePresenter;
import com.autodesk.shejijia.shared.framework.BasePresenter;
import com.autodesk.shejijia.shared.framework.BaseView;

import java.util.Date;
import java.util.List;

/**
 * Created by wenhulin on 11/2/16.
 */

public interface EditPlanContract {

    interface MileStoneView extends BaseView {

        void showTasks(List<Task> tasks);

        void showActiveTask(PlanInfo planInfo, Task task);

        void onTaskDateChange(Task task, Date oldDate, Date newDate);
    }

    interface MileStonePresenter extends BasePresenter {

        void bindView(MileStoneView view);

        void fetchPlan();

        void updateTask(Date selectedDate);
    }

    interface TaskNodeView extends BaseView {

        void showTasks(List<Task> tasks);

        void showBottomSheet(List<Task> milestones, Task task);

        void showUpLoading();

        void hideUpLoading();

        void onCommitSuccess();

        void onCommitError(String error);
    }

    interface TaskNodePresenter extends BasePresenter {

        void fetchPlan();

        void bindView(TaskNodeView view);

        void editTaskNode(Task task);

        void updateTask(List<Date> selectedDates);

        void commitPlan();
    }
}
