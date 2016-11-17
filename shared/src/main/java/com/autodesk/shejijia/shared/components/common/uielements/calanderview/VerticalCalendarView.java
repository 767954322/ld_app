package com.autodesk.shejijia.shared.components.common.uielements.calanderview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.uielements.calanderview.format.TitleFormatter;

/**
 * Created by wenhulin on 10/30/16.
 */

public class VerticalCalendarView extends MaterialCalendarView {

    private VerticalCalendarAdapter mListAdapter;
    private RecyclerView mListView;
    private LinearLayoutManager mLayoutManager;

    public VerticalCalendarView(Context context) {
        super(context);
    }

    public VerticalCalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void initView() {
        mListView = new RecyclerView(getContext());
    }

    @Override
    protected IAdapter createAdapter() {
        if (mListAdapter == null) {
            mListAdapter = new VerticalCalendarAdapter(this);
        }
        return mListAdapter;
    }

    @Override
    protected void setUpEditView() {
        setUpEditView();
    }

    @Override
    protected void setupChildren() {
        // TODO need to optimize
        LinearLayout container = new LinearLayout(getContext());
        container.setOrientation(LinearLayout.VERTICAL);

        topbar = new LinearLayout(getContext());
        topbar.setOrientation(LinearLayout.HORIZONTAL);
        topbar.setClipChildren(false);
        topbar.setClipToPadding(false);

        // TODO setup weekday formatter
        WeekNamesView weekNamesView = new WeekNamesView(getContext(), getFirstDayOfWeek());
        container.addView(weekNamesView, new LayoutParams(60));

        mListView.setId(R.id.mcv_list);
        mListView.setAdapter(mListAdapter);
        //noinspection deprecation
        mLayoutManager = new LinearLayoutManager(getContext());
        mListView.setLayoutManager(mLayoutManager);
        container.addView(mListView, new MarginLayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

        addView(container, new LayoutParams(calendarMode.visibleWeeksCount + DAY_NAMES_ROW));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int specWidthSize = MeasureSpec.getSize(widthMeasureSpec);
        final int specWidthMode = MeasureSpec.getMode(widthMeasureSpec);
        final int specHeightSize = MeasureSpec.getSize(heightMeasureSpec);
        final int specHeightMode = MeasureSpec.getMode(heightMeasureSpec);

        //We need to disregard padding for a while. This will be added back later
        final int desiredWidth = specWidthSize - getPaddingLeft() - getPaddingRight();
        final int desiredHeight = specHeightSize - getPaddingTop() - getPaddingBottom();

        final int weekCount = getWeekCountBasedOnMode();

        final int viewTileHeight = getTopbarVisible() ? (weekCount + 1) : weekCount;

        //Calculate independent tile sizes for later
        int desiredTileWidth = desiredWidth / DEFAULT_DAYS_IN_WEEK;
        int desiredTileHeight = desiredHeight / viewTileHeight;

        int measureTileSize = -1;
        int measureTileWidth = -1;
        int measureTileHeight = -1;

        if (this.tileWidth > 0 || this.tileHeight > 0) {
            if (this.tileWidth > 0) {
                //We have a tileWidth set, we should use that
                measureTileWidth = this.tileWidth;
            }
            if (this.tileHeight > 0) {
                //We have a tileHeight set, we should use that
                measureTileHeight = this.tileHeight;
            }
        } else if (specWidthMode == MeasureSpec.EXACTLY) {
            if (specHeightMode == MeasureSpec.EXACTLY) {
                //Pick the larger of the two explicit sizes
                measureTileSize = Math.min(desiredTileWidth, desiredTileHeight);
            } else {
                //Be the width size the user wants
                measureTileSize = desiredTileWidth;
            }
        } else if (specHeightMode == MeasureSpec.EXACTLY) {
            //Be the height size the user wants
            measureTileSize = desiredTileHeight;
        }

        if (measureTileSize > 0) {
            //Use measureTileSize if set
            measureTileHeight = measureTileSize;
            measureTileWidth = measureTileSize;
        } else if (measureTileSize <= 0) {
            if (measureTileWidth <= 0) {
                //Set width to default if no value were set
                measureTileWidth = dpToPx(DEFAULT_TILE_SIZE_DP);
            }
            if (measureTileHeight <= 0) {
                //Set height to default if no value were set
                measureTileHeight = dpToPx(DEFAULT_TILE_SIZE_DP);
            }
        }

        //Calculate our size based off our measured tile size
        int measuredWidth = measureTileWidth * DEFAULT_DAYS_IN_WEEK;
        int measuredHeight = measureTileHeight * viewTileHeight;

        //Put padding back in from when we took it away
        measuredWidth += getPaddingLeft() + getPaddingRight();
        measuredHeight += getPaddingTop() + getPaddingBottom();

        //Contract fulfilled, setting out measurements
        setMeasuredDimension(
                //We clamp inline because we want to use un-clamped versions on the children
                clampSize(measuredWidth, widthMeasureSpec),
                clampSize(specHeightSize, heightMeasureSpec)
        );

        int count = getChildCount();

        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);

            LayoutParams p = (LayoutParams) child.getLayoutParams();

            int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(
                    DEFAULT_DAYS_IN_WEEK * measureTileWidth,
                    MeasureSpec.EXACTLY
            );

            int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(
                    specHeightSize,
                    MeasureSpec.EXACTLY
            );

            child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
        }
    }

    @Override
    public void setHeaderTextAppearance(int resourceId) {
        // TODO setup header
    }

    @Override
    public CalendarDay getCurrentDate() {
        // TODO return current date
        return super.getCurrentDate();
    }

    @Override
    public void setCurrentDate(@Nullable CalendarDay day, boolean useSmoothScroll) {
        if (day == null) {
            return;
        }
        int index = mListAdapter.getIndexForDay(day);
        mLayoutManager.scrollToPositionWithOffset(index, 0);
    }

    @Override
    public void setTitleFormatter(TitleFormatter titleFormatter) {
        // TODO
    }

    @Override
    protected void setRangeDates(CalendarDay min, CalendarDay max) {
        super.setRangeDates(min, max);
        //TODO scroll to current month
    }
}
