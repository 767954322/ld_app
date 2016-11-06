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
import com.autodesk.shejijia.shared.components.common.utility.DateUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wenhulin on 10/20/16.
 */

public class MileStoneNodeDecorator implements DayViewDecorator {
    private final Drawable drawable;
    private List<Task> tasks;

    public MileStoneNodeDecorator(Activity context) {
        //noinspection deprecation
        drawable = context.getResources().getDrawable(R.drawable.calander_milestone_selector);
        tasks = new ArrayList<>();
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        for(Task task : tasks) {
            Date date = DateUtil.isoStringToDate(task.getPlanningTime().getStart());
            if (DateUtil.isSameDay(date, day.getDate())) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new ForegroundColorSpan(Color.BLACK));
        view.setSelectionDrawable(drawable);
    }

    public void setData(List<Task> tasks) {
        this.tasks = tasks;
    }
}
