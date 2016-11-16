package com.autodesk.shejijia.shared.components.nodeprocess.ui.widgets.calendar;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.autodesk.shejijia.shared.components.common.appglobal.TaskEnum;
import com.autodesk.shejijia.shared.components.common.entity.microbean.PlanInfo;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Task;
import com.autodesk.shejijia.shared.components.common.uielements.calanderview.CalendarDay;
import com.autodesk.shejijia.shared.components.common.uielements.calanderview.DayViewDecorator;
import com.autodesk.shejijia.shared.components.common.uielements.calanderview.DayViewFacade;
import com.autodesk.shejijia.shared.components.common.utility.DateUtil;
import com.autodesk.shejijia.shared.components.common.utility.LogUtils;

import java.util.Date;

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
        if (task == null || plan == null) {
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
                mEnableDaysStart = getStartDate(plan);
                mEnableDaysEnd = getEndDate(plan);
                break;
        }
    }

    private Date getStartDate(PlanInfo plan) {
        Task task = getTaskByTemplateId(plan, TaskEnum.TemplateId.KAI_GONG_JIAO_DI.getValue());
        return task == null ? null : DateUtil.iso8601ToDate(task.getPlanningTime().getStart());
    }

    private Date getEndDate(PlanInfo plan) {
        Task task = getTaskByTemplateId(plan, TaskEnum.TemplateId.JUNGONG_YANSHOU.getValue());
        return task == null ? null : DateUtil.iso8601ToDate(task.getPlanningTime().getStart());
    }

    private Task getTaskByTemplateId(@NonNull PlanInfo planInfo, @NonNull String templateId) {
        for (Task task: planInfo.getTasks()) {
            if (templateId.equals(task.getTaskTemplateId())) {
                return  task;
            }
        }

        return null;
    }
}
