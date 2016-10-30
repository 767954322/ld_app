package com.autodesk.shejijia.shared.components.common.uielements.calanderview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.uielements.calanderview.format.DateFormatTitleFormatter;
import com.autodesk.shejijia.shared.components.common.uielements.calanderview.format.TitleFormatter;

import java.util.Calendar;

/**
 * Created by wenhulin on 10/30/16.
 */

public class HorCalandarView extends MaterialCalendarView {

    private static final TitleFormatter DEFAULT_TITLE_FORMATTER = new DateFormatTitleFormatter();
    private TitleChanger titleChanger;

    private TextView title;
    private DirectionButton buttonPast;
    private DirectionButton buttonFuture;
    private CalendarPager pager;
    private CalendarPagerAdapter<?> adapter;

    private CalendarDay currentMonth;

    private int arrowColor = Color.BLACK;
    private Drawable leftArrowMask;
    private Drawable rightArrowMask;

    private final OnClickListener onClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == buttonFuture) {
                pager.setCurrentItem(pager.getCurrentItem() + 1, true);
            } else if (v == buttonPast) {
                pager.setCurrentItem(pager.getCurrentItem() - 1, true);
            }
        }
    };

    private final ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageSelected(int position) {
            titleChanger.setPreviousMonth(currentMonth);
            currentMonth = adapter.getItem(position);
            updateUi();

            dispatchOnMonthChanged(currentMonth);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }
    };

    public HorCalandarView(Context context) {
        this(context, null);
    }

    public HorCalandarView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void initView() {
        buttonPast = new DirectionButton(getContext());
        buttonPast.setContentDescription(getContext().getString(R.string.previous));
        title = new TextView(getContext());
        buttonFuture = new DirectionButton(getContext());
        buttonFuture.setContentDescription(getContext().getString(R.string.next));
        pager = new CalendarPager(getContext());

        title.setOnClickListener(onClickListener);
        buttonPast.setOnClickListener(onClickListener);
        buttonFuture.setOnClickListener(onClickListener);

        titleChanger = new TitleChanger(title);
        titleChanger.setTitleFormatter(DEFAULT_TITLE_FORMATTER);

        pager.setOnPageChangeListener(pageChangeListener);
        pager.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {
                position = (float) Math.sqrt(1 - Math.abs(position));
                page.setAlpha(position);
            }
        });
    }

    @Override
    protected void setAttributes(Context context, TypedArray a) {
        super.setAttributes(context, a);
        setArrowColor(a.getColor(
                R.styleable.MaterialCalendarView_mcv_arrowColor,
                Color.BLACK
        ));
        Drawable leftMask = a.getDrawable(
                R.styleable.MaterialCalendarView_mcv_leftArrowMask
        );
        if (leftMask == null) {
            leftMask = getResources().getDrawable(R.drawable.mcv_action_previous);
        }
        setLeftArrowMask(leftMask);
        Drawable rightMask = a.getDrawable(
                R.styleable.MaterialCalendarView_mcv_rightArrowMask
        );
        if (rightMask == null) {
            rightMask = getResources().getDrawable(R.drawable.mcv_action_next);
        }
        setRightArrowMask(rightMask);
    }

    @Override
    protected IAdapter createAdapter() {
        // Recreate adapter
        final CalendarPagerAdapter<?> newAdapter;
        switch (calendarMode) {
            case MONTHS:
                newAdapter = new MonthPagerAdapter(this);
                break;
            case WEEKS:
                newAdapter = new WeekPagerAdapter(this);
                break;
            default:
                throw new IllegalArgumentException("Provided display mode which is not yet implemented");
        }
        if (adapter == null) {
            adapter = newAdapter;
        } else {
            adapter = adapter.migrateStateAndReturn(newAdapter);
        }
        pager.setAdapter(adapter);

        return adapter;
    }

    @Override
    protected void resetViewHeight() {
        // Reset height params after mode change
        pager.setLayoutParams(new LayoutParams(calendarMode.visibleWeeksCount + DAY_NAMES_ROW));
    }

    protected void setupChildren() {
        // Adapter is created while parsing the TypedArray attrs, so setup has to happen after
        adapter.setTitleFormatter(DEFAULT_TITLE_FORMATTER);

        topbar = new LinearLayout(getContext());
        topbar.setOrientation(LinearLayout.HORIZONTAL);
        topbar.setClipChildren(false);
        topbar.setClipToPadding(false);
        addView(topbar, new LayoutParams(1));

        buttonPast.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        buttonPast.setImageResource(R.drawable.mcv_action_previous);
        topbar.addView(buttonPast, new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 1));

        title.setGravity(Gravity.CENTER);
        topbar.addView(title, new LinearLayout.LayoutParams(
                0, LayoutParams.MATCH_PARENT, DEFAULT_DAYS_IN_WEEK - 2
        ));

        buttonFuture.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        buttonFuture.setImageResource(R.drawable.mcv_action_next);
        topbar.addView(buttonFuture, new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 1));

        pager.setId(R.id.mcv_pager);
        pager.setOffscreenPageLimit(1);
        addView(pager, new LayoutParams(calendarMode.visibleWeeksCount + DAY_NAMES_ROW));
    }

    @Override
    protected void setUpEditView() {
        removeView(pager);
        super.setUpEditView();
    }

    protected void updateUi() {
        titleChanger.change(currentMonth);
        buttonPast.setEnabled(canGoBack());
        buttonFuture.setEnabled(canGoForward());
    }

    /**
     * Go to previous month or week without using the button {@link #buttonPast}. Should only go to
     * previous if {@link #canGoBack()} is true, meaning it's possible to go to the previous month
     * or week.
     */
    public void goToPrevious() {
        if (canGoBack()) {
            pager.setCurrentItem(pager.getCurrentItem() - 1, true);
        }
    }

    /**
     * Go to next month or week without using the button {@link #buttonFuture}. Should only go to
     * next if {@link #canGoForward()} is enabled, meaning it's possible to go to the next month or
     * week.
     */
    public void goToNext() {
        if (canGoForward()) {
            pager.setCurrentItem(pager.getCurrentItem() + 1, true);
        }
    }

    /**
     * TODO should this be public?
     *
     * @return true if there is a future month that can be shown
     */
    public boolean canGoForward() {
        return pager.getCurrentItem() < (adapter.getCount() - 1);
    }

    /**
     * TODO should this be public?
     *
     * @return true if there is a previous month that can be shown
     */
    public boolean canGoBack() {
        return pager.getCurrentItem() > 0;
    }

    /**
     * Pass all touch events to the pager so scrolling works on the edges of the calendar view.
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return pager.dispatchTouchEvent(event);
    }


    /**
     * @return color used to draw arrows
     */
    public int getArrowColor() {
        return arrowColor;
    }

    /**
     * @param color the new color for the paging arrows
     */
    public void setArrowColor(int color) {
        if (color == 0) {
            return;
        }
        arrowColor = color;
        buttonPast.setColor(color);
        buttonFuture.setColor(color);
        invalidate();
    }

    /**
     * Set content description for button past
     *
     * @param description String to use as content description
     */
    public void setContentDescriptionArrowPast(final CharSequence description) {
        buttonPast.setContentDescription(description);
    }

    /**
     * Set content description for button future
     *
     * @param description String to use as content description
     */
    public void setContentDescriptionArrowFuture(final CharSequence description) {
        buttonFuture.setContentDescription(description);
    }

    /**
     * @return icon used for the left arrow
     */
    public Drawable getLeftArrowMask() {
        return leftArrowMask;
    }

    /**
     * @param icon the new icon to use for the left paging arrow
     */
    public void setLeftArrowMask(Drawable icon) {
        leftArrowMask = icon;
        buttonPast.setImageDrawable(icon);
    }

    /**
     * @return icon used for the right arrow
     */
    public Drawable getRightArrowMask() {
        return rightArrowMask;
    }

    /**
     * @param icon the new icon to use for the right paging arrow
     */
    public void setRightArrowMask(Drawable icon) {
        rightArrowMask = icon;
        buttonFuture.setImageDrawable(icon);
    }

    @Override
    public void setHeaderTextAppearance(int resourceId) {
        title.setTextAppearance(getContext(), resourceId);
    }

    /**
     * @return The current month shown, will be set to first day of the month
     */
    public CalendarDay getCurrentDate() {
        return adapter.getItem(pager.getCurrentItem());
    }

    @Override
    public void setCurrentDate(@Nullable CalendarDay day, boolean useSmoothScroll) {
        if (day == null) {
            return;
        }
        int index = adapter.getIndexForDay(day);
        pager.setCurrentItem(index, useSmoothScroll);
        updateUi();
    }

    @Override
    public void setTitleFormatter(TitleFormatter titleFormatter) {
        if (titleFormatter == null) {
            titleFormatter = DEFAULT_TITLE_FORMATTER;
        }
        titleChanger.setTitleFormatter(titleFormatter);
        adapter.setTitleFormatter(titleFormatter);
        updateUi();
    }

    /**
     * Sets the visibility {@link #topbar}, which contains
     * the previous month button {@link #buttonPast}, next month button {@link #buttonFuture},
     * and the month title {@link #title}.
     *
     * @param visible Boolean indicating if the topbar is visible
     */
    public void setTopbarVisible(boolean visible) {
        topbar.setVisibility(visible ? View.VISIBLE : View.GONE);
        requestLayout();
    }

    /**
     * @return true if the topbar is visible
     */
    public boolean getTopbarVisible() {
        return topbar.getVisibility() == View.VISIBLE;
    }

    @Override
    protected void setRangeDates(CalendarDay min, CalendarDay max) {
        super.setRangeDates(min, max);
        int position = adapter.getIndexForDay(currentMonth);
        pager.setCurrentItem(position, false);
        updateUi();
    }

    @Override
    protected void onDateClicked(DayView dayView) {
        final int currentMonth = getCurrentDate().getMonth();
        final int selectedMonth = dayView.getDate().getMonth();

        if (calendarMode == CalendarMode.MONTHS) {
            if (allowClickDaysOutsideCurrentMonth || currentMonth == selectedMonth) {
                if (currentMonth > selectedMonth) {
                    goToPrevious();
                } else if (currentMonth < selectedMonth) {
                    goToNext();
                }
                onDateClicked(dayView.getDate(), !dayView.isChecked());
            }
        } else {
            onDateClicked(dayView.getDate(), !dayView.isChecked());
        }
    }

    @Override
    protected int getWeekCountBasedOnMode() {
        int weekCount = calendarMode.visibleWeeksCount;
        boolean isInMonthsMode = calendarMode.equals(CalendarMode.MONTHS);
        if (isInMonthsMode && mDynamicHeightEnabled && adapter != null && pager != null) {
            Calendar cal = (Calendar) adapter.getItem(pager.getCurrentItem()).getCalendar().clone();
            cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
            //noinspection ResourceType
            cal.setFirstDayOfWeek(getFirstDayOfWeek());
            weekCount = cal.get(Calendar.WEEK_OF_MONTH);
        }
        return weekCount + DAY_NAMES_ROW;
    }

    /**
     * Enable or disable the ability to swipe between months.
     *
     * @param pagingEnabled pass false to disable paging, true to enable (default)
     */
    public void setPagingEnabled(boolean pagingEnabled) {
        pager.setPagingEnabled(pagingEnabled);
        updateUi();
    }

    /**
     * @return true if swiping months is enabled, false if disabled. Default is true.
     */
    public boolean isPagingEnabled() {
        return pager.isPagingEnabled();
    }
}
