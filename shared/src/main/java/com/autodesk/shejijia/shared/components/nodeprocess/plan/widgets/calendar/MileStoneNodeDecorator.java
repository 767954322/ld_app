package com.autodesk.shejijia.shared.components.nodeprocess.plan.widgets.calendar;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.text.style.ForegroundColorSpan;

import com.autodesk.shejijia.enterprise.R;
import com.autodesk.shejijia.shared.components.common.uielements.calanderview.CalendarDay;
import com.autodesk.shejijia.shared.components.common.uielements.calanderview.DayViewDecorator;
import com.autodesk.shejijia.shared.components.common.uielements.calanderview.DayViewFacade;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wenhulin on 10/20/16.
 */

public class MileStoneNodeDecorator implements DayViewDecorator {
    private final Drawable drawable;
    private List<CalendarDay> dates;

    public MileStoneNodeDecorator(Activity context) {
        //noinspection deprecation
        drawable = context.getResources().getDrawable(R.drawable.demo_milestone_selector);
        dates = new ArrayList<>();
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
        view.addSpan(new ForegroundColorSpan(Color.BLACK));
        view.setSelectionDrawable(drawable);
    }

    public void setDates(List<Date> dates) {
        this.dates.clear();
        for(Date date: dates) {
            this.dates.add(CalendarDay.from(date));
        }
    }

    public void addDate(@NonNull Date date) {
        this.dates.add(CalendarDay.from(date));
    }

    public void clearDates() {
        this.dates.clear();
    }
}
