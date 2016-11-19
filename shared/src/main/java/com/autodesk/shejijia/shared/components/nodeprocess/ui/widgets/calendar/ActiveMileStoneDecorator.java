package com.autodesk.shejijia.shared.components.nodeprocess.ui.widgets.calendar;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.autodesk.shejijia.shared.components.common.appglobal.ConstructionConstants;
import com.autodesk.shejijia.shared.components.common.entity.microbean.PlanInfo;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Task;
import com.autodesk.shejijia.shared.components.common.uielements.calanderview.CalendarDay;
import com.autodesk.shejijia.shared.components.common.uielements.calanderview.DayViewDecorator;
import com.autodesk.shejijia.shared.components.common.uielements.calanderview.DayViewFacade;
import com.autodesk.shejijia.shared.components.common.utility.CollectionUtils;
import com.autodesk.shejijia.shared.components.common.utility.DateUtil;

import java.util.Date;
import java.util.List;


/**
 * @author wenhulin
 * @since 11/3/16
 */

public class ActiveMileStoneDecorator implements DayViewDecorator {
    private Date mEnableDaysStart;
    private Date mEnableDaysEnd;

    public ActiveMileStoneDecorator() {
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        Date date = day.getDate();
        return mEnableDaysStart != null && date.before(mEnableDaysStart)
                || mEnableDaysEnd != null && date.after(mEnableDaysEnd);
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.setDaysDisabled(true);
    }

    public void setActiveTask(@NonNull PlanInfo plan, @Nullable Task task) {
        setActiveTask(plan.getTasks(), task);
    }

    public void setActiveTask(@NonNull List<Task> tasks, @Nullable Task task) {
        if (task == null || CollectionUtils.isEmpty(tasks)) {
            mEnableDaysStart = null;
            mEnableDaysEnd = null;
            return;
        }

        String taskTemplateId = task.getTaskTemplateId();
        if (ConstructionConstants.TaskTemplateId.KAI_GONG_JIAO_DI.equalsIgnoreCase(taskTemplateId)
                || ConstructionConstants.TaskTemplateId.JUNGONG_YANSHOU.equalsIgnoreCase(taskTemplateId)) {
            mEnableDaysStart = null;
            mEnableDaysEnd = null;
        } else {
            mEnableDaysStart = getStartDate(tasks);
            mEnableDaysEnd = getEndDate(tasks);
        }
    }

    private Date getStartDate(List<Task> tasks) {
        Task task = getTaskByTemplateId(tasks, ConstructionConstants.TaskTemplateId.KAI_GONG_JIAO_DI);
        return task == null ? null : DateUtil.iso8601ToDate(task.getPlanningTime().getStart());
    }

    private Date getEndDate(List<Task> tasks) {
        Task task = getTaskByTemplateId(tasks, ConstructionConstants.TaskTemplateId.JUNGONG_YANSHOU);
        return task == null ? null : DateUtil.iso8601ToDate(task.getPlanningTime().getStart());
    }

    private Task getTaskByTemplateId(@NonNull List<Task> tasks, @NonNull String templateId) {
        for (Task task: tasks) {
            if (templateId.equals(task.getTaskTemplateId())) {
                return  task;
            }
        }

        return null;
    }
}
