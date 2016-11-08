package com.autodesk.shejijia.shared.components.nodeprocess.ui.widgets.calendar;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;

import com.autodesk.shejijia.shared.components.common.entity.microbean.Task;
import com.autodesk.shejijia.shared.components.common.uielements.calanderview.CalendarDay;
import com.autodesk.shejijia.shared.components.common.uielements.calanderview.DayViewDecorator;
import com.autodesk.shejijia.shared.components.common.uielements.calanderview.DayViewFacade;
import com.autodesk.shejijia.shared.components.common.utility.DateUtil;

import java.util.Date;

/**
 * Created by wenhulin on 10/21/16.
 */

public class ActiveMileStoneDecorator implements DayViewDecorator {
    private Task mTask;
    private Context mContext;


    public ActiveMileStoneDecorator(Activity context) {
        mContext = context;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        if (mTask == null) {
            return false;
        }

        Date date = DateUtil.iso8601ToDate(mTask.getPlanningTime().getStart());
        return DateUtil.isSameDay(date, day.getDate());
    }

    @Override
    public void decorate(DayViewFacade view) {
        //noinspection deprecation
        view.setBackgroundDrawable(new ColorDrawable(mContext.getResources().getColor(android.R.color.holo_blue_light)));
    }

    public void setActiveTask(Task task) {
        this.mTask = task;
    }
}
