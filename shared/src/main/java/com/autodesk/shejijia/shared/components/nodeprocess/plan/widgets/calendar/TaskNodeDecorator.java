package com.autodesk.shejijia.shared.components.nodeprocess.plan.widgets.calendar;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.text.style.ForegroundColorSpan;

import com.autodesk.shejijia.shared.components.common.uielements.calanderview.CalendarDay;
import com.autodesk.shejijia.shared.components.common.uielements.calanderview.DayViewDecorator;
import com.autodesk.shejijia.shared.components.common.uielements.calanderview.DayViewFacade;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wenhulin on 10/20/16.
 */

public class TaskNodeDecorator implements DayViewDecorator {
    private List<CalendarDay> dates;
    private Context mContext;

    public TaskNodeDecorator(Context context) {
        dates = new ArrayList<>();
        mContext = context;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        for(CalendarDay date: dates) {
            if (day.equals(date)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new ForegroundColorSpan(Color.WHITE));
        //noinspection deprecation
        view.setBackgroundDrawable(new ColorDrawable(mContext.getResources().getColor(android.R.color.holo_blue_light)));
    }

    public void setDates(List<CalendarDay> dates) {
        this.dates.clear();
        for(CalendarDay date: dates) {
            this.dates.add(date);
        }
    }

    public void addDate(@NonNull Date date) {
        this.dates.add(CalendarDay.from(date));
    }

    public void addDate(@NonNull CalendarDay date) {
        this.dates.add(date);
    }

    public void clearDates() {
        this.dates.clear();
    }
}
