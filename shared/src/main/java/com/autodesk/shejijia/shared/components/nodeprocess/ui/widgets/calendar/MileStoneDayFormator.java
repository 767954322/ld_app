package com.autodesk.shejijia.shared.components.nodeprocess.ui.widgets.calendar;

import android.support.annotation.NonNull;

import com.autodesk.shejijia.shared.components.common.entity.microbean.Task;
import com.autodesk.shejijia.shared.components.common.uielements.calanderview.CalendarDay;
import com.autodesk.shejijia.shared.components.common.uielements.calanderview.format.DayFormatter;
import com.autodesk.shejijia.shared.components.common.utility.DateUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wenhulin on 11/4/16.
 */
public class MileStoneDayFormator implements DayFormatter {
    private List<Task> tasks;

    public MileStoneDayFormator() {
        tasks = new ArrayList<>();
    }

    @NonNull
    @Override
    public String format(@NonNull CalendarDay day) {
        for (Task task : tasks) {
            Date date = DateUtil.isoStringToDate(task.getPlanningTime().getStart());
            if (DateUtil.isSameDay(date, day.getDate())) {
                return task.getName();
            }
        }
        return DayFormatter.DEFAULT.format(day);
    }

    public void setData(List<Task> tasks) {
        this.tasks = tasks;
    }
}
