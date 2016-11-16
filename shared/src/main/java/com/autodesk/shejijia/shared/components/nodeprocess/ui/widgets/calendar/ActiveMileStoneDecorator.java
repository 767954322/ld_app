package com.autodesk.shejijia.shared.components.nodeprocess.ui.widgets.calendar;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.res.TypedArrayUtils;

import com.autodesk.shejijia.shared.components.common.appglobal.TaskEnum;
import com.autodesk.shejijia.shared.components.common.entity.microbean.PlanInfo;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Task;
import com.autodesk.shejijia.shared.components.common.uielements.calanderview.CalendarDay;
import com.autodesk.shejijia.shared.components.common.uielements.calanderview.DayViewDecorator;
import com.autodesk.shejijia.shared.components.common.uielements.calanderview.DayViewFacade;
import com.autodesk.shejijia.shared.components.common.utility.CollectionUtils;
import com.autodesk.shejijia.shared.components.common.utility.DateUtil;
import com.autodesk.shejijia.shared.components.common.utility.LogUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wenhulin on 10/21/16.
 */

public class ActiveMileStoneDecorator implements DayViewDecorator {
    private Date mEnableDaysStart;
    private Date mEnableDaysEnd;
    private Context mContext;


    public ActiveMileStoneDecorator(Activity context) {
        mContext = context;
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
        if (plan == null) {
            mEnableDaysStart = null;
            mEnableDaysEnd = null;
            return;
        }

        setActiveTask(plan.getTasks(), task);
    }

    public void setActiveTask(@NonNull List<Task> tasks, @Nullable Task task) {
        if (task == null || CollectionUtils.isEmpty(tasks)) {
            mEnableDaysStart = null;
            mEnableDaysEnd = null;
            return;
        }


        switch (TaskEnum.TemplateId.getEnum(task.getTaskTemplateId())) {
            case KAI_GONG_JIAO_DI:
                mEnableDaysStart = null;
                mEnableDaysEnd = null;
                break;
            case JUNGONG_YANSHOU:
                mEnableDaysStart = null;
                mEnableDaysEnd = null;
                break;
            default:
                mEnableDaysStart = getStartDate(tasks);
                mEnableDaysEnd = getEndDate(tasks);
                break;
        }
    }

    private Date getStartDate(List<Task> tasks) {
        Task task = getTaskByTemplateId(tasks, TaskEnum.TemplateId.KAI_GONG_JIAO_DI.getValue());
        return task == null ? null : DateUtil.iso8601ToDate(task.getPlanningTime().getStart());
    }

    private Date getEndDate(List<Task> tasks) {
        Task task = getTaskByTemplateId(tasks, TaskEnum.TemplateId.JUNGONG_YANSHOU.getValue());
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
