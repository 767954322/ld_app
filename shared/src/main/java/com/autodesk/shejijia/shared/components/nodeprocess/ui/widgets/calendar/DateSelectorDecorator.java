package com.autodesk.shejijia.shared.components.nodeprocess.ui.widgets.calendar;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.uielements.calanderview.CalendarDay;
import com.autodesk.shejijia.shared.components.common.uielements.calanderview.DayViewDecorator;
import com.autodesk.shejijia.shared.components.common.uielements.calanderview.DayViewFacade;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wenhulin on 10/20/16.
 */

public class DateSelectorDecorator implements DayViewDecorator {
    private final Drawable drawable;
    private List<CalendarDay> dates = new ArrayList<>();

    @SuppressWarnings("deprecation")
    public DateSelectorDecorator(Activity context, boolean isMileStone) {
        if (isMileStone) {
            drawable = context.getResources().getDrawable(R.drawable.calander_milestone_selector);
        } else  {
            drawable = context.getResources().getDrawable(R.drawable.calander_default_selector);
        }

    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        for(CalendarDay date: dates) {
            if (day.equals(date)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.setSelectionDrawable(drawable);
    }

    public void setExcludeDates(List<CalendarDay> dates) {
        this.dates.clear();
        for(CalendarDay date: dates) {
            this.dates.add(date);
        }
    }

    public void addDate(@NonNull Date date) {
        this.dates.add(CalendarDay.from(date));
    }

    public void clearDates() {
        this.dates.clear();
    }
}
