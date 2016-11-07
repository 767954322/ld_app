package com.autodesk.shejijia.shared.components.nodeprocess.contract;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;

import com.autodesk.shejijia.shared.components.common.entity.microbean.Task;
import com.autodesk.shejijia.shared.components.nodeprocess.presenter.EditPlanPresenter;
import com.autodesk.shejijia.shared.framework.BasePresenter;
import com.autodesk.shejijia.shared.framework.BaseView;

import java.util.Date;
import java.util.List;

/**
 * Created by wenhulin on 11/2/16.
 */

public interface EditPlanContract {
    interface View extends BaseView {
        void showTasks(List<Task> tasks);
        void bindPresenter(Presenter presenter);
        void showActiveTask(Task task);
        void onTaskDateChange(Task task, Date oldDate, Date newDate);
    }

    interface Presenter extends BasePresenter {
        void bindView(View view);
        /**
         * fetch exist plan
         */
        void fetchPlan();

        /**
         * update task planning time, the data will save in memory or local
         */
        void updateTask(Task task, Date newDate);

        /**
         * commit edited plan
         */
        void commitPlan();

        /**
         * Update edit state
         * @param newState
         */
        void updateEditState(EditPlanPresenter.EditState newState);

        /**
         * Get current edit state
         * @return
         */
        EditPlanPresenter.EditState getEditState();

        void onDateSelected(Date date, boolean selected);
    }
}
