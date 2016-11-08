package com.autodesk.shejijia.shared.components.common.uielements.calanderview;

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.uielements.calanderview.format.DayFormatter;
import com.autodesk.shejijia.shared.components.common.uielements.calanderview.format.TitleFormatter;
import com.autodesk.shejijia.shared.components.common.uielements.calanderview.format.WeekDayFormatter;
import com.autodesk.shejijia.shared.components.common.utility.DateUtil;

import java.util.List;

/**
 * Created by wenhulin on 10/24/16.
 */

public class VerticalCalendarViewAdapter extends BaseAdapter implements IAdapter{
    private AdapterHelper mAdapterHelper;
    private MaterialCalendarView mcv;

    VerticalCalendarViewAdapter(MaterialCalendarView mcv) {
        this.mcv = mcv;
        mAdapterHelper = new AdapterHelper<MonthView>(mcv);
    }

    public void setRangeDates(CalendarDay min, CalendarDay max) {
        mAdapterHelper.setRangeDates(min, max);
        notifyDataSetChanged();
        mAdapterHelper.invalidateSelectedDates();
    }

    @Override
    public int getCount() {
        return mAdapterHelper.getCount();
    }

    @Override
    public CalendarDay getItem(int position) {
        return mAdapterHelper.getItem(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewGroup monthContainer;
        MonthView monthView;
        CalendarDay calendarDay = getItem(position);
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mcv.getContext());
            monthContainer = (ViewGroup) inflater.inflate(R.layout.item_month, null);
            monthView = new MonthView(mcv, getItem(position), mcv.getFirstDayOfWeek(), false);
            monthView.setId(R.id.mcv_month_view);
            ViewGroup.MarginLayoutParams layoutParams = new ViewGroup.MarginLayoutParams(parent.getWidth(),
                    mcv.getResources().getDimensionPixelSize(R.dimen.month_view_height));
            monthView.setLayoutParams(layoutParams);
            monthContainer.addView(monthView);
            monthView.setContentDescription(mcv.getCalendarContentDescription());
            monthView.setSelectionEnabled(mAdapterHelper.getSelectionEnabled());
            monthView.setShowOtherDates(mAdapterHelper.getShowOtherDates());
            monthView.setMinimumDate(mAdapterHelper.getMininumDate());
            monthView.setMaximumDate(mAdapterHelper.getMaximumDate());
            monthView.setDayFormatter(mAdapterHelper.getDayFormatter());
            monthView.setShowWeekDays(false);
            monthView.setDateTextAppearance(mAdapterHelper.getDateTextAppearance());
            monthView.setWeekDayFormatter(mAdapterHelper.getWeekDayFormatter());
        } else {
            monthContainer = (ViewGroup) convertView;
            monthView = (MonthView) monthContainer.findViewById(R.id.mcv_month_view);
            monthView.reuse(getItem(position), mcv.getFirstDayOfWeek());
        }

        // TODO do more optimize
        TextView textView = (TextView) monthContainer.findViewById(R.id.item_title);
        textView.setText(DateUtil.getStringDateByFormat(calendarDay.getDate(), "yyyy")
                + mcv.getContext().getString(R.string.year)
                + DateUtil.getStringDateByFormat(calendarDay.getDate(), "M")
                + mcv.getContext().getString(R.string.month));

        monthView.setSelectedDates(mAdapterHelper.getSelectedDates());
        monthView.setDayViewDecorators(mAdapterHelper.getDecoratorResult());

        return monthContainer;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    public void setSelectionColor(int color) {
        mAdapterHelper.setSelectionColor(color);
    }

    @Override
    public void setDateTextAppearance(int taId) {
        mAdapterHelper.setDateTextAppearance(taId);
    }

    @Override
    public void setWeekDayTextAppearance(int resourceId) {

    }

    public void setDecorators(List<DayViewDecorator> decorators) {
        mAdapterHelper.setDecorators(decorators);
    }

    public void invalidateDecorators() {
        mAdapterHelper.invalidateDecorators();
        notifyDataSetChanged();
    }

    @Override
    public int getIndexForDay(CalendarDay day) {
        return mAdapterHelper.getIndexForDay(day);
    }

    @Override
    public int getDateTextAppearance() {
        return 0;
    }

    @Override
    public int getShowOtherDates() {
        return 0;
    }

    @Override
    public int getWeekDayTextAppearance() {
        return 0;
    }

    public void setDateSelected(CalendarDay day, boolean selected) {
        mAdapterHelper.setDateSelected(day, selected);
    }

    @Override
    public void setShowOtherDates(@MaterialCalendarView.ShowOtherDates int showFlags) {

    }

    @Override
    public void setWeekDayFormatter(WeekDayFormatter formatter) {
        mAdapterHelper.setWeekDayFormatter(formatter);
    }

    public void setSelectionEnabled(boolean enabled) {
        mAdapterHelper.setSelectionEnabled(enabled);
    }

    public void clearSelections() {
        mAdapterHelper.clearSelections();
    }

    public void setDayFormatter(DayFormatter formatter) {
        mAdapterHelper.setDayFormatter(formatter);
    }

    @Override
    public void setTitleFormatter(@NonNull TitleFormatter titleFormatter) {

    }

    @NonNull
    public List<CalendarDay> getSelectedDates() {
        return mAdapterHelper.getSelectedDates();
    }

}
