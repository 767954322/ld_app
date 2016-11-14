package com.autodesk.shejijia.shared.components.nodeprocess.ui.widgets.calendar;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.style.ForegroundColorSpan;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Task;
import com.autodesk.shejijia.shared.components.common.uielements.calanderview.CalendarDay;
import com.autodesk.shejijia.shared.components.common.uielements.calanderview.DayViewDecorator;
import com.autodesk.shejijia.shared.components.common.uielements.calanderview.DayViewFacade;
import com.autodesk.shejijia.shared.components.common.uielements.calanderview.format.DayFormatter;
import com.autodesk.shejijia.shared.components.common.utility.DateUtil;
import com.autodesk.shejijia.shared.components.common.utility.LogUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wenhulin on 10/20/16.
 */

public class MileStoneNodeDecorator implements DayViewDecorator {
    private final Drawable drawable;
    private Map<String, Task> dateTaskMap = new HashMap<>();

    public MileStoneNodeDecorator(Activity context) {
        //noinspection deprecation
        drawable = context.getResources().getDrawable(R.drawable.calander_milestone_selector);
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        String dateString = DateUtil.getStringDateByFormat(day.getDate(), "yyyy-MM-dd");
        return dateTaskMap.containsKey(dateString);
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new ForegroundColorSpan(Color.BLACK));
//        view.setSelectionDrawable(drawable);
        view.setDaysActivated(true);
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
