package com.autodesk.shejijia.enterprise.nodeprocess.plan.widgets.calendar;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;

import com.autodesk.shejijia.shared.components.common.uielements.calanderview.CalendarDay;
import com.autodesk.shejijia.shared.components.common.uielements.calanderview.DayViewDecorator;
import com.autodesk.shejijia.shared.components.common.uielements.calanderview.DayViewFacade;
import com.autodesk.shejijia.shared.components.common.uielements.calanderview.MaterialCalendarView;

import java.util.Date;

/**
 * Created by wenhulin on 10/21/16.
 */

public class MileStoneActiveDecorator implements DayViewDecorator {
    private CalendarDay date;
    private Context mContext;


    public MileStoneActiveDecorator(Activity context) {
        mContext = context;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return date != null && day.equals(date);
    }

    @Override
    public void decorate(DayViewFacade view) {
        //noinspection deprecation
        view.setBackgroundDrawable(new ColorDrawable(mContext.getResources().getColor(android.R.color.holo_blue_light)));
    }

    /**
     * We're changing the internals, so make sure to call {@linkplain MaterialCalendarView#invalidateDecorators()}
     */
    public void setDate(Date date) {
        this.date = CalendarDay.from(date);
    }
}
