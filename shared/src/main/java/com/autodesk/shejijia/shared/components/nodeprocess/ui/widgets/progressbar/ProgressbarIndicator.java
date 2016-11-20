package com.autodesk.shejijia.shared.components.nodeprocess.ui.widgets.progressbar;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import com.autodesk.shejijia.shared.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by t_xuz on 11/18/16.
 */

public class ProgressbarIndicator extends View {

    private static final int DEFAULT_STEP_RADIUS = 16;   //DP
    private static final int DEFAULT_STOKE_WIDTH = 6;  //DP
    private static final int DEFAULT_STEP_COUNT = 6;  //DP
    private static final int DEFAULT_TEXT_SIZE = 15;  //SP
    private static final int DEFAULT_LINE_COLOR = R.color.progressbar_line_color;
    private static final int DEFAULT_LINE_DONE_COLOR = R.color.progressbar_line_done_color;
    private static final int DEFAULT_CIRCLE_UNOPEN_COLOR = R.color.progressbar_circle_unopen_color;
    private static final int DEFAULT_CIRCLE_COMPLETE_COLOR = R.color.progressbar_circle_complete_color;
    private static final int DEFAULT_CIRCLE_INPROGRESS_COLOR = R.color.progressbar_circle_inprogress_color;
    private static final int DEFAULT_CIRCLE_UNOPEN_SELECTED_COLOR = R.color.progressbar_circle_unopen_selected_color;
    private static final int DEFAULT_CIRCLE_INPROGRESS_SELECTED_COLOR = R.color.progressbar_circle_inprogress_selected_color;
    private static final int DEFAULT_CIRCLE_COMPLETE_SELECTED_COLOR = R.color.progressbar_circle_complete_selected_color;
    private static final int DEFAULT_TEXT_COLOR = R.color.progressbar_text_color;

    private int radius;
    private int stepsCount = 1;
    private int currentStepPosition;
    private int currentStatus = -1;
    private int stepLineColor;
    private int stepLineDoneColor;
    private int circleUnOpenColor;
    private int circleCompleteColor;
    private int circleInProgressColor;
    private int circleUnOpenSelectedColor;
    private int circleInProgressSelectedColor;
    private int circleCompleteSelectedColor;
    private int textColor;
    private int strokeWidth;

    private int pagerScrollState;

    private int centerY;
    private int startX;
    private int endX;
    private int stepDistance;
    private float offset;
    private int offsetPixel;

    private Paint linePaint;//虚线paint
    private Paint circlePaint;//未选中circle paint
    private Paint pStoke; //selected stroke circle
    private Paint textPaint;//文本

    private int bitMapHeight;
    private int bitMapWidth;

    private Path mDashPath;//虚线path
    private String[] statusLists;
    private final Rect textBounds = new Rect();
    private DashPathEffect dashPathEffect = new DashPathEffect(new float[]{8, 12}, 0);
    private List<Bitmap> iconList = new ArrayList<>(3);

    private boolean hasDrawn = false;
    private boolean withViewpager;
    private OnClickListener onClickListener;

    public ProgressbarIndicator(Context context) {
        this(context, null);
    }

    public ProgressbarIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressbarIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ProgressbarIndicator(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attributeSet) {

        initAttributes(context, attributeSet);

        linePaint = new Paint();
        linePaint.setStrokeCap(Paint.Cap.ROUND);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setAntiAlias(true);
//        linePaint.setDither(true);

        circlePaint = new Paint();
        circlePaint.setAntiAlias(true);
        circlePaint.setStyle(Paint.Style.FILL);

        pStoke = new Paint();
        pStoke.setStyle(Paint.Style.STROKE);
        pStoke.setFlags(Paint.ANTI_ALIAS_FLAG);

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setStyle(Paint.Style.FILL);

        mDashPath = new Path();

        Bitmap icUnOpen = BitmapFactory.decodeResource(getResources(), R.drawable.ic_progressbar_unopen);
        Bitmap icInProgress = BitmapFactory.decodeResource(getResources(), R.drawable.ic_progressbar_inprocess);
        Bitmap icComplete = BitmapFactory.decodeResource(getResources(), R.drawable.ic_progressbar_complete);
        bitMapHeight = icUnOpen.getHeight();
        bitMapWidth = icUnOpen.getWidth();
        iconList.add(icUnOpen);
        iconList.add(icInProgress);
        iconList.add(icComplete);

        statusLists = getContext().getResources().getStringArray(R.array.project_progressbar_state);

    }

    private void initAttributes(Context context, AttributeSet attributeSet) {
        TypedArray attr = context.obtainStyledAttributes(attributeSet, R.styleable.ProgressbarIndicator, 0, 0);
        if (attr == null) {
            return;
        }

        try {
            radius = (int) attr.getDimension(R.styleable.ProgressbarIndicator_piRadius, dip2px(context, DEFAULT_STEP_RADIUS));
            stepsCount = attr.getInt(R.styleable.ProgressbarIndicator_piStepCount, DEFAULT_STEP_COUNT);
            stepLineColor = attr.getColor(R.styleable.ProgressbarIndicator_piStepLineColor, ContextCompat.getColor(context, DEFAULT_LINE_COLOR));
            stepLineDoneColor = attr.getColor(R.styleable.ProgressbarIndicator_piStepLineDoneColor, ContextCompat.getColor(context, DEFAULT_LINE_DONE_COLOR));
            circleUnOpenColor = attr.getColor(R.styleable.ProgressbarIndicator_piStepUnOpenCircleColor, ContextCompat.getColor(context, DEFAULT_CIRCLE_UNOPEN_COLOR));
            circleInProgressColor = attr.getColor(R.styleable.ProgressbarIndicator_piStepInProgressCircleColor, ContextCompat.getColor(context, DEFAULT_CIRCLE_INPROGRESS_COLOR));
            circleCompleteColor = attr.getColor(R.styleable.ProgressbarIndicator_piStepCompleteCircleColor, ContextCompat.getColor(context, DEFAULT_CIRCLE_COMPLETE_COLOR));
            circleUnOpenSelectedColor = attr.getColor(R.styleable.ProgressbarIndicator_piStepUnOpenSelectedCircleColor, ContextCompat.getColor(context, DEFAULT_CIRCLE_UNOPEN_SELECTED_COLOR));
            circleInProgressSelectedColor = attr.getColor(R.styleable.ProgressbarIndicator_piStepInProgressSelectedCircleColor, ContextCompat.getColor(context, DEFAULT_CIRCLE_INPROGRESS_SELECTED_COLOR));
            circleCompleteSelectedColor = attr.getColor(R.styleable.ProgressbarIndicator_piStepCompleteSelectedCircleColor, ContextCompat.getColor(context, DEFAULT_CIRCLE_COMPLETE_SELECTED_COLOR));
            textColor = attr.getColor(R.styleable.ProgressbarIndicator_piStepTextColor, ContextCompat.getColor(context, DEFAULT_TEXT_COLOR));
            strokeWidth = (int) attr.getDimension(R.styleable.ProgressbarIndicator_piStepCircleStrokeWidth, dip2px(context, DEFAULT_STOKE_WIDTH));

            textPaint.setTextSize(attr.getDimension(R.styleable.ProgressbarIndicator_piStepTextSize, sp2px(context, DEFAULT_TEXT_SIZE)));
            linePaint.setStrokeWidth(attr.getDimension(R.styleable.ProgressbarIndicator_piStepLineStrokeWidth, dip2px(context, DEFAULT_STOKE_WIDTH)));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            attr.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        radius = (int) bitMapHeight / 2;
        setMeasuredDimension(widthMeasureSpec, radius * 5);
        centerY = radius * 2;
        startX = radius * 2;
        endX = getWidth() - (radius * 2);
        stepDistance = (endX - startX) / (stepsCount - 1);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerY = radius * 2;
        startX = radius * 2;
        endX = getWidth() - (radius * 2);
        stepDistance = (endX - startX) / (stepsCount - 1);
        invalidate();
    }


    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public void setStepsCount(int stepsCount) {
        this.stepsCount = stepsCount;
        hasDrawn = true;
        invalidate();
    }

    public int getCurrentStepPosition() {
        return currentStepPosition;
    }

    public void setCurrentStepPosition(int currentStepPosition) {
        this.currentStepPosition = currentStepPosition;
        invalidate();
    }

    private void setPagerScrollState(int pagerScrollState) {
        this.pagerScrollState = pagerScrollState;
    }

    private void setOffset(float offset, int position) {
        this.offset = offset;
        offsetPixel = Math.round(stepDistance * offset);
        if (currentStepPosition > position) {
            offsetPixel = offsetPixel - stepDistance;
        } else {
            currentStepPosition = position;
        }

        invalidate();
    }

    public interface OnClickListener {
        void onClick(int position);
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void setCurrentStatus(int currentStatus) {
        this.currentStatus = currentStatus;
        this.currentStepPosition = this.currentStatus;
        invalidate();
    }

    public void setupWithViewPager(@NonNull ViewPager viewPager) {
        final PagerAdapter pagerAdapter = viewPager.getAdapter();
        if (pagerAdapter == null) {
            throw new IllegalArgumentException("ViewPager does not have a PagerAdapter set");
        }

        withViewpager = true;

        // 1. we'll add Steps.
        setStepsCount(pagerAdapter.getCount());

        // 2.setCurrentPosition
        if (pagerAdapter.getCount() > 0) {
            final int currentItem = viewPager.getCurrentItem();
            if (currentStepPosition != currentItem) {
                currentStepPosition = currentItem;
            }
        }

        // 3. add listener
        viewPager.addOnPageChangeListener(new ViewPagerOnChangeListener(this));

        setOnClickListener(new ViewPagerOnSelectedListener(viewPager));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (stepsCount < 1) {
            setVisibility(GONE);
            return;
        }
        super.onDraw(canvas);
        int pointX = startX;
        int pointOffset;

        /** draw Line */
        linePaint.setStrokeWidth(dip2px(getContext(), 2));
        linePaint.setColor(stepLineColor);
        mDashPath.moveTo(pointX, centerY);
        mDashPath.lineTo(getWidth() - startX, centerY);
        linePaint.setPathEffect(dashPathEffect);
        canvas.drawPath(mDashPath, linePaint);

          /*draw circles*/
        pointX = startX;
        for (int i = 0; i < stepsCount; i++) {
            int radius = this.radius + (int) dip2px(getContext(), 3);
            if (i < currentStatus) {
                circlePaint.setColor(circleCompleteColor);
                canvas.drawCircle(pointX, centerY, radius, circlePaint);
            } else if (i == currentStatus) {
                circlePaint.setColor(circleInProgressColor);
                canvas.drawCircle(pointX, centerY, radius, circlePaint);
            } else {
                circlePaint.setColor(circleUnOpenColor);
                canvas.drawCircle(pointX, centerY, radius, circlePaint);
            }


            pointX += stepDistance;
        }

        /*draw bitmap*/
        pointX = startX;
        for (int i = 0; i < stepsCount; i++) {
            if (iconList != null && iconList.size() == 3) {
                if (i < currentStatus) {
                    canvas.drawBitmap(iconList.get(2), pointX - iconList.get(2).getWidth() / 2, centerY - iconList.get(2).getHeight() / 2, null);
                } else if (i == currentStatus) {
                    canvas.drawBitmap(iconList.get(1), pointX - iconList.get(1).getWidth() / 2, centerY - iconList.get(1).getHeight() / 2, null);
                } else {
                    canvas.drawBitmap(iconList.get(0), pointX - iconList.get(0).getWidth() / 2, centerY - iconList.get(0).getHeight() / 2, null);
                }
                pointX += stepDistance;
            }
        }

        /*draw selected circle*/
        pointX = startX;
        for (int i = 0; i < stepsCount; i++) {
            int radius = this.radius + (int) dip2px(getContext(), 3);
            if (i == currentStepPosition) {
                if (i < currentStatus) {
                    pStoke.setColor(circleCompleteSelectedColor);
                } else if (i == currentStatus) {
                    pStoke.setColor(circleInProgressSelectedColor);
                } else {
                    pStoke.setColor(circleUnOpenSelectedColor);
                }
                //draw current step
                if (offsetPixel == 0 || pagerScrollState == 0) {
                    //set stroke default
                    pStoke.setStrokeWidth(Math.round(strokeWidth));
                    pStoke.setAlpha(255);
                } else if (offsetPixel < 0) {
                    pStoke.setStrokeWidth(Math.round(strokeWidth * offset));
                    pStoke.setAlpha(Math.round(offset * 255f));
                } else {
                    //set stroke transition
                    pStoke.setStrokeWidth(strokeWidth - Math.round(strokeWidth * offset));
                    pStoke.setAlpha(255 - Math.round(offset * 255f));
                }
                canvas.drawCircle(pointX, centerY, radius + dip2px(getContext(), 2), pStoke);
            } else if (i > currentStepPosition) {
                //draw transition
                if (i == currentStepPosition + 1 && offsetPixel > 0 && pagerScrollState == 1) {
                    pStoke.setColor(circleUnOpenSelectedColor);
                    pStoke.setStrokeWidth(Math.round(strokeWidth * offset));
                    pStoke.setAlpha(Math.round(offset * 255f));
                    canvas.drawCircle(pointX, centerY, radius + dip2px(getContext(), 2), pStoke);
                }
            }
            pointX += stepDistance;
        }

        /*draw texts*/
        pointX = startX;
        if (statusLists != null && statusLists.length > 0) {
            for (int i = 0; i < stepsCount; i++) {
                String status = statusLists[i];
                textPaint.setColor(textColor);
                textPaint.setTextSize((sp2px(getContext(), 15)));
                textPaint.getTextBounds(status, 0, status.length(), textBounds);
                int textStartX = (Math.abs(radius - textBounds.width() / 2) + radius) + stepDistance * i;
                int textStartY = radius * 4 + (textBounds.height() / 2);
                canvas.drawText(status, textStartX, textStartY, textPaint);
            }
        }

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int pointX = startX;
        int xTouch;
        int yTouch;
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                xTouch = (int) event.getX(0);
                yTouch = (int) event.getY(0);
                for (int i = 0; i < stepsCount; i++) {
                    if (Math.abs(xTouch - pointX) < radius + 5 && Math.abs(yTouch - centerY) < radius + 5) {
                      /*  if (withViewpager) {
                            setCurrentStepPosition(i);
                        }*/

                        if (onClickListener != null) {
                            onClickListener.onClick(i);
                        }
                    }
                    pointX = pointX + stepDistance;
                }
                break;
        }
        return super.onTouchEvent(event);
    }


    public static class ViewPagerOnChangeListener implements ViewPager.OnPageChangeListener {
        private final ProgressbarIndicator stepIndicator;

        public ViewPagerOnChangeListener(ProgressbarIndicator stepIndicator) {
            this.stepIndicator = stepIndicator;
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            stepIndicator.setOffset(positionOffset, position);
        }

        @Override
        public void onPageSelected(int position) {
            stepIndicator.setCurrentStepPosition(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            stepIndicator.setPagerScrollState(state);
        }

    }

    public static class ViewPagerOnSelectedListener implements OnClickListener {
        private final ViewPager mViewPager;

        public ViewPagerOnSelectedListener(ViewPager viewPager) {
            mViewPager = viewPager;
        }

        @Override
        public void onClick(int position) {
            mViewPager.setCurrentItem(position);
        }
    }
}
