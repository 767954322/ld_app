package com.autodesk.shejijia.shared.components.nodeprocess.ui.widgets.calendar;

import android.support.annotation.NonNull;

import com.autodesk.shejijia.shared.components.common.entity.microbean.Task;
import com.autodesk.shejijia.shared.components.common.uielements.calanderview.CalendarDay;
import com.autodesk.shejijia.shared.components.common.uielements.calanderview.format.DayFormatter;
import com.autodesk.shejijia.shared.components.common.utility.DateUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wenhulin on 11/4/16.
 */
public class MileStoneDayFormatter implements DayFormatter {
    private Map<String, Task> dateTaskMap = new HashMap<>();

    public MileStoneDayFormatter() {
    }

    @NonNull
    @Override
    public String format(@NonNull CalendarDay day) {
        String dateString = DateUtil.getStringDateByFormat(day.getDate(), "yyyy-MM-dd");
        Task task = dateTaskMap.get(dateString);
        if (task == null) {
            return DayFormatter.DEFAULT.format(day);
        } else {
            return task.getName();
        }
    }

    public void setData(List<Task> tasks) {
        this.dateTaskMap.clear();
        for (Task task : tasks) {
            String dateString = DateUtil.dateFormat(task.getPlanningTime().getStart(), "yyyy-MM-dd'T'HH:mm:ss'Z'", "yyyy-MM-dd");
            this.dateTaskMap.put(dateString, task);
        }
    }
}
