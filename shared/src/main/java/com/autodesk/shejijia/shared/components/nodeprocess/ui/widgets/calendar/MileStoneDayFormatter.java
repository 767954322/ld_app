package com.autodesk.shejijia.shared.components.nodeprocess.ui.widgets.calendar;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.autodesk.shejijia.shared.components.common.entity.microbean.Task;
import com.autodesk.shejijia.shared.components.common.uielements.calanderview.CalendarDay;
import com.autodesk.shejijia.shared.components.common.uielements.calanderview.format.DayFormatter;
import com.autodesk.shejijia.shared.components.common.utility.DateUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by wenhulin on 11/4/16.
 */
public class MileStoneDayFormatter implements DayFormatter {
    private HashMap<String, Task> dateTaskMap = new HashMap<>();

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
            String taskName = task.getName();
            if (!TextUtils.isEmpty(taskName) && taskName.length() > 3) {
                int middleIndex =  Math.round(taskName.length() / 2f);
                return taskName.substring(0, middleIndex) + "\n" + taskName.substring(middleIndex);
            } else {
                return taskName;
            }
        }
    }

    public void setData(List<Task> tasks) {
        this.dateTaskMap.clear();
        for (Task task : tasks) {
            // TODO Optimize date convert
            Date date = DateUtil.iso8601ToDate(task.getPlanningTime().getStart());
            if (date != null) {
                String dateString = DateUtil.getStringDateByFormat(date, "yyyy-MM-dd");
                this.dateTaskMap.put(dateString, task);
            }
        }
    }

    public void updateTask(Task task, Date oldDate, Date newDate) {
        String key = DateUtil.getStringDateByFormat(oldDate, "yyyy-MM-dd");
        this.dateTaskMap.remove(key);
        String dateString = DateUtil.getStringDateByFormat(newDate, "yyyy-MM-dd");
        this.dateTaskMap.put(dateString, task);
    }
}
