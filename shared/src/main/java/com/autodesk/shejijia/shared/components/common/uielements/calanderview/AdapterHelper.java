package com.autodesk.shejijia.shared.components.common.uielements.calanderview;

import android.support.annotation.NonNull;
import android.support.v4.util.SparseArrayCompat;
import android.util.Log;

import com.autodesk.shejijia.shared.components.common.uielements.calanderview.format.DayFormatter;
import com.autodesk.shejijia.shared.components.common.uielements.calanderview.format.TitleFormatter;
import com.autodesk.shejijia.shared.components.common.uielements.calanderview.format.WeekDayFormatter;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by wenhulin on 10/24/16.
 */

public class AdapterHelper<V extends CalendarPagerView> {
    private final ArrayDeque<V> currentViews;

    protected final MaterialCalendarView mcv;
    private final CalendarDay today;

    private TitleFormatter titleFormatter = null;
    private Integer color = null;
    private Integer dateTextAppearance = null;
    private Integer weekDayTextAppearance = null;
    @MaterialCalendarView.ShowOtherDates
    private int showOtherDates = MaterialCalendarView.SHOW_DEFAULTS;
    private CalendarDay minDate = null;
    private CalendarDay maxDate = null;
    private DateRangeIndex rangeIndex;
    private List<CalendarDay> selectedDates = new ArrayList<>();
    private WeekDayFormatter weekDayFormatter = WeekDayFormatter.DEFAULT;
    private DayFormatter dayFormatter = DayFormatter.DEFAULT;
    private List<DayViewDecorator> decorators = new ArrayList<>();
    private List<DecoratorResult> decoratorResults = null;
    private boolean selectionEnabled = true;

    AdapterHelper(MaterialCalendarView mcv) {
        this.mcv = mcv;
        this.today = CalendarDay.today();
        currentViews = new ArrayDeque<>();
        currentViews.iterator();
        setRangeDates(null, null);
    }

    public void setDecorators(List<DayViewDecorator> decorators) {
        this.decorators = decorators;
        invalidateDecorators();
    }

    public void invalidateDecorators() {
        decoratorResults = new ArrayList<>();
        for (DayViewDecorator decorator : decorators) {
            DayViewFacade facade = new DayViewFacade();
            decorator.decorate(facade);
            if (facade.isDecorated()) {
                decoratorResults.add(new DecoratorResult(decorator, facade));
            }
        }
        for (V pagerView : currentViews) {
            pagerView.setDayViewDecorators(decoratorResults);
        }
    }

    public int getCount() {
        return rangeIndex.getCount();
    }

    public int getIndexForDay(CalendarDay day) {
        if (day == null) {
            return getCount() / 2;
        }
        if (minDate != null && day.isBefore(minDate)) {
            return 0;
        }
        if (maxDate != null && day.isAfter(maxDate)) {
            return getCount() - 1;
        }
        return rangeIndex.indexOf(day);
    }

    public Object initItem(V pagerView) {
        pagerView.setContentDescription(mcv.getCalendarContentDescription());
        pagerView.setAlpha(0);
        pagerView.setSelectionEnabled(selectionEnabled);

        pagerView.setWeekDayFormatter(weekDayFormatter);
        pagerView.setDayFormatter(dayFormatter);
        if (color != null) {
            pagerView.setSelectionColor(color);
        }
        if (dateTextAppearance != null) {
            pagerView.setDateTextAppearance(dateTextAppearance);
        }
        if (weekDayTextAppearance != null) {
            pagerView.setWeekDayTextAppearance(weekDayTextAppearance);
        }
        pagerView.setShowOtherDates(showOtherDates);
        pagerView.setMinimumDate(minDate);
        pagerView.setMaximumDate(maxDate);
        pagerView.setSelectedDates(selectedDates);

        currentViews.add(pagerView);

        pagerView.setDayViewDecorators(decoratorResults);

        return pagerView;
    }

    public void setSelectionEnabled(boolean enabled) {
        selectionEnabled = enabled;
        for (V pagerView : currentViews) {
            pagerView.setSelectionEnabled(selectionEnabled);
        }
    }

    public boolean getSelectionEnabled() {
        return selectionEnabled;
    }

    public void setSelectionColor(int color) {
        this.color = color;
        for (V pagerView : currentViews) {
            pagerView.setSelectionColor(color);
        }
    }

    public void setDateTextAppearance(int taId) {
        if (taId == 0) {
            return;
        }
        this.dateTextAppearance = taId;
        for (V pagerView : currentViews) {
            pagerView.setDateTextAppearance(taId);
        }
    }

    public void setShowOtherDates(@MaterialCalendarView.ShowOtherDates int showFlags) {
        this.showOtherDates = showFlags;
        for (V pagerView : currentViews) {
            pagerView.setShowOtherDates(showFlags);
        }
    }

    public void setWeekDayFormatter(WeekDayFormatter formatter) {
        this.weekDayFormatter = formatter;
        for (V pagerView : currentViews) {
            pagerView.setWeekDayFormatter(formatter);
        }
    }

    public void setDayFormatter(DayFormatter formatter) {
        this.dayFormatter = formatter;
        for (V pagerView : currentViews) {
            pagerView.setDayFormatter(formatter);
        }
    }

    public DayFormatter getDayFormatter() {
        return this.dayFormatter;
    }

    @MaterialCalendarView.ShowOtherDates
    public int getShowOtherDates() {
        return showOtherDates;
    }

    public void setWeekDayTextAppearance(int taId) {
        if (taId == 0) {
            return;
        }
        this.weekDayTextAppearance = taId;
        for (V pagerView : currentViews) {
            pagerView.setWeekDayTextAppearance(taId);
        }
    }

    public DateRangeIndex getRangeIndex() {
        return rangeIndex;
    }

    public void clearSelections() {
        selectedDates.clear();
        invalidateSelectedDates();
    }

    public void setDateSelected(CalendarDay day, boolean selected) {
        if (selected) {
            if (!selectedDates.contains(day)) {
                selectedDates.add(day);
                invalidateSelectedDates();
            }
        } else {
            if (selectedDates.contains(day)) {
                selectedDates.remove(day);
                invalidateSelectedDates();
            }
        }
    }

    public void invalidateSelectedDates() {
        validateSelectedDates();
        for (V pagerView : currentViews) {
            pagerView.setSelectedDates(selectedDates);
        }
    }

    private void validateSelectedDates() {
        for (int i = 0; i < selectedDates.size(); i++) {
            CalendarDay date = selectedDates.get(i);

            if ((minDate != null && minDate.isAfter(date)) || (maxDate != null && maxDate.isBefore(date))) {
                selectedDates.remove(i);
                mcv.onDateUnselected(date);
                i -= 1;
            }
        }
    }

    public CalendarDay getItem(int position) {
        return rangeIndex.getItem(position);
    }

    @NonNull
    public List<CalendarDay> getSelectedDates() {
        return Collections.unmodifiableList(selectedDates);
    }

    protected int getDateTextAppearance() {
        return dateTextAppearance == null ? 0 : dateTextAppearance;
    }

    protected int getWeekDayTextAppearance() {
        return weekDayTextAppearance == null ? 0 : weekDayTextAppearance;
    }

    public void setRangeDates(CalendarDay min, CalendarDay max) {
        this.minDate = min;
        this.maxDate = max;
        for (V pagerView : currentViews) {
            pagerView.setMinimumDate(min);
            pagerView.setMaximumDate(max);
        }

        if (min == null) {
            min = CalendarDay.from(today.getYear() - 200, today.getMonth(), today.getDay());
        }

        if (max == null) {
            max = CalendarDay.from(today.getYear() + 200, today.getMonth(), today.getDay());
        }

        rangeIndex = createRangeIndex(min, max);
        Log.i("Wenhui", "rangeIndex=" + rangeIndex.getCount());
    }

    protected DateRangeIndex createRangeIndex(CalendarDay min, CalendarDay max) {
        return new Monthly(min, max);
    }

    public static class Monthly implements DateRangeIndex {

        private final CalendarDay min;
        private final int count;

        private SparseArrayCompat<CalendarDay> dayCache = new SparseArrayCompat<>();

        public Monthly(@NonNull CalendarDay min, @NonNull CalendarDay max) {
            this.min = CalendarDay.from(min.getYear(), min.getMonth(), 1);
            max = CalendarDay.from(max.getYear(), max.getMonth(), 1);
            this.count = indexOf(max) + 1;
        }

        public int getCount() {
            return count;
        }

        public int indexOf(CalendarDay day) {
            int yDiff = day.getYear() - min.getYear();
            int mDiff = day.getMonth() - min.getMonth();

            return (yDiff * 12) + mDiff;
        }

        public CalendarDay getItem(int position) {

            CalendarDay re = dayCache.get(position);
            if (re != null) {
                return re;
            }

            int numY = position / 12;
            int numM = position % 12;

            int year = min.getYear() + numY;
            int month = min.getMonth() + numM;
            if (month >= 12) {
                year += 1;
                month -= 12;
            }

            re = CalendarDay.from(year, month, 1);
            dayCache.put(position, re);
            return re;
        }
    }

    public List<DecoratorResult> getDecoratorResult() {
        return decoratorResults;
    }

    public CalendarDay getMininumDate() {
        return this.minDate;
    }

    public CalendarDay getMaximumDate() {
        return this.maxDate;
    }


}
