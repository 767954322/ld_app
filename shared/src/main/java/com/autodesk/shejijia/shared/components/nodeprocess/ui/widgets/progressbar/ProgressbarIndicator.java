package com.autodesk.shejijia.shared.components.nodeprocess.ui.widgets.progressbar;

import android.animation.ObjectAnimator;
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
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.BounceInterpolator;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.appglobal.ConstructionConstants;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.components.nodeprocess.entity.MilestoneStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by t_xuz on 11/18/16.
 */

public class ProgressbarIndicator extends View {

    private static final int DEFAULT_STEP_RADIUS = 16;   //DP
    private static final int DEFAULT_LINE_STOKE_WIDTH = 2;  //DP
    private static final int DEFAULT_CIRCLE_STOKE_WIDTH = 6;  //DP
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
    private int stepsCount;
    private int currentStepPosition;
    private int currentStatus;
    private int stepLineColor;
    private int stepLineDoneColor;
    private int circleUnOpenColor;
    private int circleCompleteColor;
    private int circleInProgressColor;
    private int circleUnOpenSelectedColor;
    private int circleInProgressSelectedColor;
    private int circleCompleteSelectedColor;
    private int textColor;
    private float textSize;
    private float strokeCircleWidth;
    private float strokeAnimCircleWidth;
    private float strokeLineWidth;

    private int centerY;
    private int startX;
    private int endX;
    private int stepDistance;

    private Paint linePaint;//虚线paint
    private Paint circlePaint;//未选中circle paint
    private Paint pStoke; //selected stroke circle
    private Paint textPaint;//文本

    private int bitMapHeight, bitMapWidth;

    private Path mDashPath;//虚线path
    private List<MilestoneStatus> mMilestoneStatusList;
    private final Rect textBounds = new Rect();
    private DashPathEffect dashPathEffect = new DashPathEffect(new float[]{8, 12}, 0);
    private List<Bitmap> iconList = new ArrayList<>(3);

    private OnClickListener onClickListener;
    private ObjectAnimator checkAnimator;

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
            strokeCircleWidth = attr.getDimension(R.styleable.ProgressbarIndicator_piStepCircleStrokeWidth, dip2px(context, DEFAULT_CIRCLE_STOKE_WIDTH));
            textSize = attr.getDimension(R.styleable.ProgressbarIndicator_piStepTextSize, sp2px(context, DEFAULT_TEXT_SIZE));
            strokeLineWidth = attr.getDimension(R.styleable.ProgressbarIndicator_piStepLineStrokeWidth, dip2px(context, DEFAULT_LINE_STOKE_WIDTH));
            strokeAnimCircleWidth = strokeCircleWidth;
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

    public void setStepsCount(int stepsCount, List<MilestoneStatus> milestoneStatusList) {
        this.stepsCount = stepsCount;
        this.mMilestoneStatusList = milestoneStatusList;
        invalidate();
    }

    private void setCurrentStepPosition(int currentStepPosition) {
        this.currentStepPosition = currentStepPosition;
//        setCheckedAnimation();
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

    private void setCheckedAnimation() {
        checkAnimator = ObjectAnimator.ofFloat(ProgressbarIndicator.this, "strokeAnimCircleWidth", 0f, strokeCircleWidth * 1.4f, strokeCircleWidth);
        checkAnimator.setDuration(500);
        checkAnimator.setInterpolator(new BounceInterpolator());
        checkAnimator.start();
    }

    public void setupWithViewPager(@NonNull ViewPager viewPager, List<MilestoneStatus> milestoneStatusList) {
        final PagerAdapter pagerAdapter = viewPager.getAdapter();
        if (pagerAdapter == null) {
            throw new IllegalArgumentException("ViewPager does not have a PagerAdapter set");
        }

        // 1. we'll add Steps and milestoneStatusList.
        setStepsCount(pagerAdapter.getCount(), milestoneStatusList);

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
        if (mMilestoneStatusList == null || mMilestoneStatusList.size() == 0) {
            setVisibility(GONE);
            return;
        }
        super.onDraw(canvas);
        int pointX = startX;

        /** draw Line */
        linePaint.setStrokeWidth(strokeLineWidth);
        linePaint.setColor(stepLineColor);
        mDashPath.moveTo(pointX, centerY);
        mDashPath.lineTo(getWidth() - startX, centerY);
        linePaint.setPathEffect(dashPathEffect);
        canvas.drawPath(mDashPath, linePaint);


          /*draw circles*/
        pointX = startX;
        for (int i = 0; i < mMilestoneStatusList.size(); i++) {
            int radius = this.radius + dip2px(getContext(), 3);
            if (mMilestoneStatusList.get(i).getStatus().equalsIgnoreCase(UIUtils.getString(R.string.task_resolved))) {
                circlePaint.setColor(circleCompleteColor);
                canvas.drawCircle(pointX, centerY, radius, circlePaint);
            } else if (mMilestoneStatusList.get(i).getStatus().equalsIgnoreCase(UIUtils.getString(R.string.task_inProgress))) {
                if (currentStatus == 0) {
                    circlePaint.setColor(circleUnOpenColor);
                    canvas.drawCircle(pointX, centerY, radius, circlePaint);
                } else {
                    circlePaint.setColor(circleInProgressColor);
                    canvas.drawCircle(pointX, centerY, radius, circlePaint);
                }
            } else {
                circlePaint.setColor(circleUnOpenColor);
                canvas.drawCircle(pointX, centerY, radius, circlePaint);
            }
            pointX += stepDistance;
        }

        /*draw bitmap*/
        pointX = startX;
        for (int i = 0; i < mMilestoneStatusList.size(); i++) {
            if (iconList != null && iconList.size() == 3) {
                if (mMilestoneStatusList.get(i).getStatus().equalsIgnoreCase(UIUtils.getString(R.string.task_resolved))) {
                    canvas.drawBitmap(iconList.get(2), pointX - iconList.get(2).getWidth() / 2, centerY - iconList.get(2).getHeight() / 2, null);
                } else if (mMilestoneStatusList.get(i).getStatus().equalsIgnoreCase(UIUtils.getString(R.string.task_inProgress))) {
                    if (currentStatus == 0) {
                        canvas.drawBitmap(iconList.get(0), pointX - iconList.get(0).getWidth() / 2, centerY - iconList.get(0).getHeight() / 2, null);
                    } else {
                        canvas.drawBitmap(iconList.get(1), pointX - iconList.get(1).getWidth() / 2, centerY - iconList.get(1).getHeight() / 2, null);
                    }
                } else {
                    canvas.drawBitmap(iconList.get(0), pointX - iconList.get(0).getWidth() / 2, centerY - iconList.get(0).getHeight() / 2, null);
                }
                pointX += stepDistance;
            }
        }

        /*draw selected circle*/
        pointX = startX;
        for (int i = 0; i < mMilestoneStatusList.size(); i++) {
            int radius = this.radius + dip2px(getContext(), 3);
            if (i == currentStepPosition) {
                if (mMilestoneStatusList.get(i).getStatus().equalsIgnoreCase(UIUtils.getString(R.string.task_resolved))) {
                    pStoke.setColor(circleCompleteSelectedColor);
                } else if (mMilestoneStatusList.get(i).getStatus().equalsIgnoreCase(UIUtils.getString(R.string.task_inProgress))) {
                    if (currentStatus == 0) {
                        pStoke.setColor(circleUnOpenSelectedColor);
                    } else {
                        pStoke.setColor(circleInProgressSelectedColor);
                    }
                } else {
                    pStoke.setColor(circleUnOpenSelectedColor);
                }
                //draw current step
                pStoke.setStrokeWidth(Math.round(strokeAnimCircleWidth));
                canvas.drawCircle(pointX, centerY, radius + dip2px(getContext(), 2), pStoke);
            }
            pointX += stepDistance;
        }

        /*draw texts*/
        for (int i = 0; i < mMilestoneStatusList.size(); i++) {
            String status = mMilestoneStatusList.get(i).getName();
            textPaint.setColor(textColor);
            textPaint.setTextSize(textSize);
            textPaint.getTextBounds(status, 0, status.length(), textBounds);
            int textStartX = (Math.abs(radius - textBounds.width() / 2) + radius) + stepDistance * i;
            int textStartY = radius * 4 + (textBounds.height() / 2);
            canvas.drawText(status, textStartX, textStartY, textPaint);
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
        private final ProgressbarIndicator progressbarIndicator;

        ViewPagerOnChangeListener(ProgressbarIndicator progressbarIndicator) {
            this.progressbarIndicator = progressbarIndicator;
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            progressbarIndicator.setCurrentStepPosition(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }

    }

    public static class ViewPagerOnSelectedListener implements OnClickListener {
        private final ViewPager mViewPager;

        ViewPagerOnSelectedListener(ViewPager viewPager) {
            mViewPager = viewPager;
        }

        @Override
        public void onClick(int position) {
            mViewPager.setCurrentItem(position);
        }
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState savedState = new SavedState(superState);
        savedState.radius = this.radius;
        savedState.stepsCount = this.stepsCount;
        savedState.currentStepPosition = this.currentStepPosition;
        savedState.currentStatus = this.currentStatus;
        savedState.stepLineColor = this.stepLineColor;
        savedState.stepLineDoneColor = this.stepLineDoneColor;
        savedState.circleUnOpenColor = this.circleUnOpenColor;
        savedState.circleCompleteColor = this.circleCompleteColor;
        savedState.circleInProgressColor = this.circleInProgressColor;
        savedState.circleUnOpenSelectedColor = this.circleUnOpenSelectedColor;
        savedState.circleInProgressSelectedColor = this.circleInProgressSelectedColor;
        savedState.circleCompleteSelectedColor = this.circleCompleteSelectedColor;
        savedState.textColor = this.textColor;
        savedState.textSize = this.textSize;
        savedState.strokeCircleWidth = this.strokeCircleWidth;
        savedState.strokeLineWidth = this.strokeLineWidth;
        return super.onSaveInstanceState();
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }
        SavedState savedState = (SavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());
        this.radius = savedState.radius;
        this.stepsCount = savedState.stepsCount;
        this.currentStepPosition = savedState.currentStepPosition;
        this.currentStatus = savedState.currentStatus;
        this.stepLineColor = savedState.stepLineColor;
        this.stepLineDoneColor = savedState.stepLineDoneColor;
        this.circleUnOpenColor = savedState.circleUnOpenColor;
        this.circleCompleteColor = savedState.circleCompleteColor;
        this.circleInProgressColor = savedState.circleInProgressColor;
        this.circleUnOpenSelectedColor = savedState.circleUnOpenSelectedColor;
        this.circleInProgressSelectedColor = savedState.circleInProgressSelectedColor;
        this.circleCompleteSelectedColor = savedState.circleCompleteSelectedColor;
        this.textColor = savedState.textColor;
        this.textSize = savedState.textSize;
        this.strokeCircleWidth = savedState.strokeCircleWidth;
        this.strokeLineWidth = savedState.strokeLineWidth;

    }

    static class SavedState extends BaseSavedState {

        private int radius;
        private int stepsCount;
        private int currentStepPosition;
        private int currentStatus;
        private int stepLineColor;
        private int stepLineDoneColor;
        private int circleUnOpenColor;
        private int circleCompleteColor;
        private int circleInProgressColor;
        private int circleUnOpenSelectedColor;
        private int circleInProgressSelectedColor;
        private int circleCompleteSelectedColor;
        private int textColor;
        private float textSize;
        private float strokeCircleWidth;
        private float strokeLineWidth;

        SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            radius = in.readInt();
            stepsCount = in.readInt();
            currentStepPosition = in.readInt();
            currentStatus = in.readInt();
            stepLineColor = in.readInt();
            stepLineDoneColor = in.readInt();
            circleUnOpenColor = in.readInt();
            circleCompleteColor = in.readInt();
            circleInProgressColor = in.readInt();
            circleUnOpenSelectedColor = in.readInt();
            circleInProgressSelectedColor = in.readInt();
            circleCompleteSelectedColor = in.readInt();
            textColor = in.readInt();
            textSize = in.readFloat();
            strokeCircleWidth = in.readFloat();
            strokeLineWidth = in.readFloat();
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(radius);
            out.writeInt(stepsCount);
            out.writeInt(currentStepPosition);
            out.writeInt(currentStatus);
            out.writeInt(stepLineColor);
            out.writeInt(stepLineDoneColor);
            out.writeInt(circleUnOpenColor);
            out.writeInt(circleCompleteColor);
            out.writeInt(circleInProgressColor);
            out.writeInt(circleUnOpenSelectedColor);
            out.writeInt(circleInProgressSelectedColor);
            out.writeInt(circleCompleteSelectedColor);
            out.writeInt(textColor);
            out.writeFloat(textSize);
            out.writeFloat(strokeCircleWidth);
            out.writeFloat(strokeLineWidth);
        }

        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {
            @Override
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            @Override
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }
}
