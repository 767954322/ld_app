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
        view.setSelectionDrawable(drawable);
    }

    public void setData(List<Task> tasks) {
        this.dateTaskMap.clear();
        for (Task task : tasks) {
            String dateString = DateUtil.dateFormat(task.getPlanningTime().getStart(), "yyyy-MM-dd'T'HH:mm:ss'Z'", "yyyy-MM-dd");
            this.dateTaskMap.put(dateString, task);
        }
    }
}
