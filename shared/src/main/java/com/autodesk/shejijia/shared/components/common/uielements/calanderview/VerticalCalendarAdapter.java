package com.autodesk.shejijia.shared.components.common.uielements.calanderview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.uielements.calanderview.format.DayFormatter;
import com.autodesk.shejijia.shared.components.common.uielements.calanderview.format.TitleFormatter;
import com.autodesk.shejijia.shared.components.common.uielements.calanderview.format.WeekDayFormatter;
import com.autodesk.shejijia.shared.components.common.utility.DateUtil;

import java.util.List;

/**
 * Created by wenhulin on 11/15/16.
 */

public class VerticalCalendarAdapter extends RecyclerView.Adapter<VerticalCalendarAdapter.ViewHolder> implements IAdapter{
    private AdapterHelper mAdapterHelper;
    private MaterialCalendarView mcv;

    VerticalCalendarAdapter(MaterialCalendarView mcv) {
        this.mcv = mcv;
        mAdapterHelper = new AdapterHelper<MonthView>(mcv);
    }

    public void setRangeDates(CalendarDay min, CalendarDay max) {
        mAdapterHelper.setRangeDates(min, max);
        mAdapterHelper.invalidateSelectedDates();
        notifyDataSetChanged();
    }

    public CalendarDay getItem(int position) {
        return mAdapterHelper.getItem(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mcv.getContext());
        ViewGroup monthContainer = (ViewGroup) inflater.inflate(R.layout.item_month, null);
        MonthView monthView = new MonthView(mcv, CalendarDay.today(), mcv.getFirstDayOfWeek(), false);
        monthView.setId(R.id.mcv_month_view);
        ViewGroup.MarginLayoutParams layoutParams = new ViewGroup.MarginLayoutParams(parent.getWidth(),
                mcv.getResources().getDimensionPixelSize(R.dimen.month_view_height));
        monthView.setLayoutParams(layoutParams);
        monthContainer.addView(monthView);
        monthView.setContentDescription(mcv.getCalendarContentDescription());
        return new ViewHolder(monthContainer);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CalendarDay calendarDay = getItem(position);
        holder.monthView.reuse(calendarDay, mcv.getFirstDayOfWeek());
        holder.monthView.setSelectionEnabled(mAdapterHelper.getSelectionEnabled());
        holder.monthView.setShowWeekDays(false);
        holder.monthView.setDayViewDecorators(mAdapterHelper.getDecoratorResult());
        holder.monthView.setDayFormatter(mAdapterHelper.getDayFormatter());

        holder.titleView.setText(DateUtil.getStringDateByFormat(calendarDay.getDate(), "yyyy")
                + mcv.getContext().getString(R.string.year)
                + DateUtil.getStringDateByFormat(calendarDay.getDate(), "M")
                + mcv.getContext().getString(R.string.month));
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return mAdapterHelper.getCount();
    }

    public void setSelectionColor(int color) {
        mAdapterHelper.setSelectionColor(color);
        notifyDataSetChanged();
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
        notifyDataSetChanged();
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
        notifyDataSetChanged();
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


    static class ViewHolder extends RecyclerView.ViewHolder {
        public MonthView monthView;
        public TextView titleView;

        public ViewHolder(View itemView) {
            super(itemView);
            monthView = (MonthView) itemView.findViewById(R.id.mcv_month_view);
            titleView = (TextView) itemView.findViewById(R.id.item_title);
        }
    }


}
