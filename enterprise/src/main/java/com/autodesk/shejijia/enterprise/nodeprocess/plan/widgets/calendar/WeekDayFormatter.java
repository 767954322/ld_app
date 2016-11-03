package com.autodesk.shejijia.enterprise.nodeprocess.plan.widgets.calendar;

import android.content.Context;

import com.autodesk.shejijia.enterprise.R;

import java.util.Calendar;

/**
 * Created by wenhulin on 11/3/16.
 */

public class WeekDayFormatter implements com.autodesk.shejijia.shared.components.common.uielements.calanderview.format.WeekDayFormatter {
    private Context mContext;

    public WeekDayFormatter(Context context) {
        mContext = context;
    }

    @Override
    public CharSequence format(int dayOfWeek) {
        CharSequence weekString;
        switch (dayOfWeek) {
            case Calendar.SUNDAY:
                weekString = mContext.getString(R.string.calendar_sunday);
                break;
            case Calendar.MONDAY:
                weekString = mContext.getString(R.string.calendar_monday);
                break;
            case Calendar.TUESDAY:
                weekString = mContext.getString(R.string.calendar_tuesday);
                break;
            case Calendar.WEDNESDAY:
                weekString = mContext.getString(R.string.calendar_wednesday);
                break;
            case Calendar.THURSDAY:
                weekString = mContext.getString(R.string.calendar_thursday);
                break;
            case Calendar.FRIDAY:
                weekString = mContext.getString(R.string.calendar_friday);
                break;
            case Calendar.SATURDAY:
                weekString = mContext.getString(R.string.calendar_saturday);
                break;
            default:
                weekString = "";
                break;
        }

        return weekString;
    }
}
