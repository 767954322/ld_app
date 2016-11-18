package com.autodesk.shejijia.shared.components.nodeprocess.contract;

import com.autodesk.shejijia.shared.components.common.entity.microbean.PlanInfo;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Task;
import com.autodesk.shejijia.shared.framework.BasePresenter;
import com.autodesk.shejijia.shared.framework.BaseView;

import java.util.ArrayList;
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

        void updateFilterIcon(int icon);

        void showAddIcon(boolean show);

        void showAddTaskDialog(ArrayList<Task> deletedTasks);

        void scrollToTask(Task task);
    }

    interface TaskNodePresenter extends BasePresenter {

        void bindView(TaskNodeView view);

        void fetchPlan();

        void editTaskNode(Task task);

        void onFilterTypeChange(TaskFilterType filterType);

        void updateTask(List<Date> selectedDates);

        void deleteTasks(List<Task> tasks);

        void addTask(Task task);

        void startAddTask();

        void commitPlan();

        boolean shouldShowAddIcon();

        enum TaskFilterType {
            ALL_TASKS,
            CONSTRUCTION_TASKS,
            MATERIAL_TASKS
        }
    }
}
