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
    private static final int DEFAULT_STEP_COUNT = 6;
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

    private int mRadius;
    private int mStepsCount;
    private int mCurrentStepPosition;
    private int mCurrentStatus;
    private int mStepLineColor;
    private int mStepLineDoneColor;
    private int mCircleUnOpenColor;
    private int mCircleCompleteColor;
    private int mCircleInProgressColor;
    private int mCircleUnOpenSelectedColor;
    private int mCircleInProgressSelectedColor;
    private int mCircleCompleteSelectedColor;
    private int mTextColor;
    private float mTextSize;
    private float mStrokeCircleWidth;
    private float mStrokeAnimCircleWidth;
    private float mStrokeLineWidth;

    private int mCenterY;
    private int mStartX;
    private int mEndX;
    private int mStepDistance;

    private Paint mLinePaint;//虚线paint
    private Paint mCirclePaint;//未选中circle paint
    private Paint mCircleStokePaint; //selected stroke circle
    private Paint mTextPaint;//文本

    private int mBitMapHeight, mBitMapWidth;

    private Path mDashPath;//虚线path
    private List<MilestoneStatus> mMilestoneStatusList;
    private String[] mStatusLists;
    private final Rect mTextBounds = new Rect();
    private DashPathEffect mDashPathEffect = new DashPathEffect(new float[]{8, 12}, 0);
    private List<Bitmap> mIconList = new ArrayList<>(3);

    private OnClickListener mClickListener;
    private ObjectAnimator mCheckAnimator;

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

        mLinePaint = new Paint();
        mLinePaint.setStrokeCap(Paint.Cap.ROUND);
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setAntiAlias(true);

        mCirclePaint = new Paint();
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setStyle(Paint.Style.FILL);

        mCircleStokePaint = new Paint();
        mCircleStokePaint.setStyle(Paint.Style.STROKE);
        mCircleStokePaint.setFlags(Paint.ANTI_ALIAS_FLAG);

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setStyle(Paint.Style.FILL);

        mDashPath = new Path();

        Bitmap icUnOpen = BitmapFactory.decodeResource(getResources(), R.drawable.ic_progressbar_unopen);
        Bitmap icInProgress = BitmapFactory.decodeResource(getResources(), R.drawable.ic_progressbar_inprocess);
        Bitmap icComplete = BitmapFactory.decodeResource(getResources(), R.drawable.ic_progressbar_complete);
        mBitMapHeight = icUnOpen.getHeight();
        mBitMapWidth = icUnOpen.getWidth();
        mIconList.add(icUnOpen);
        mIconList.add(icInProgress);
        mIconList.add(icComplete);

        mStatusLists = getContext().getResources().getStringArray(R.array.project_progressbar_state);
    }

    private void initAttributes(Context context, AttributeSet attributeSet) {
        TypedArray attr = context.obtainStyledAttributes(attributeSet, R.styleable.ProgressbarIndicator, 0, 0);
        if (attr == null) {
            return;
        }

        try {
            mRadius = (int) attr.getDimension(R.styleable.ProgressbarIndicator_piRadius, dip2px(context, DEFAULT_STEP_RADIUS));
            mStepsCount = attr.getInt(R.styleable.ProgressbarIndicator_piStepCount, DEFAULT_STEP_COUNT);
            mStepLineColor = attr.getColor(R.styleable.ProgressbarIndicator_piStepLineColor, ContextCompat.getColor(context, DEFAULT_LINE_COLOR));
            mStepLineDoneColor = attr.getColor(R.styleable.ProgressbarIndicator_piStepLineDoneColor, ContextCompat.getColor(context, DEFAULT_LINE_DONE_COLOR));
            mCircleUnOpenColor = attr.getColor(R.styleable.ProgressbarIndicator_piStepUnOpenCircleColor, ContextCompat.getColor(context, DEFAULT_CIRCLE_UNOPEN_COLOR));
            mCircleInProgressColor = attr.getColor(R.styleable.ProgressbarIndicator_piStepInProgressCircleColor, ContextCompat.getColor(context, DEFAULT_CIRCLE_INPROGRESS_COLOR));
            mCircleCompleteColor = attr.getColor(R.styleable.ProgressbarIndicator_piStepCompleteCircleColor, ContextCompat.getColor(context, DEFAULT_CIRCLE_COMPLETE_COLOR));
            mCircleUnOpenSelectedColor = attr.getColor(R.styleable.ProgressbarIndicator_piStepUnOpenSelectedCircleColor, ContextCompat.getColor(context, DEFAULT_CIRCLE_UNOPEN_SELECTED_COLOR));
            mCircleInProgressSelectedColor = attr.getColor(R.styleable.ProgressbarIndicator_piStepInProgressSelectedCircleColor, ContextCompat.getColor(context, DEFAULT_CIRCLE_INPROGRESS_SELECTED_COLOR));
            mCircleCompleteSelectedColor = attr.getColor(R.styleable.ProgressbarIndicator_piStepCompleteSelectedCircleColor, ContextCompat.getColor(context, DEFAULT_CIRCLE_COMPLETE_SELECTED_COLOR));
            mTextColor = attr.getColor(R.styleable.ProgressbarIndicator_piStepTextColor, ContextCompat.getColor(context, DEFAULT_TEXT_COLOR));
            mStrokeCircleWidth = attr.getDimension(R.styleable.ProgressbarIndicator_piStepCircleStrokeWidth, dip2px(context, DEFAULT_CIRCLE_STOKE_WIDTH));
            mTextSize = attr.getDimension(R.styleable.ProgressbarIndicator_piStepTextSize, sp2px(context, DEFAULT_TEXT_SIZE));
            mStrokeLineWidth = attr.getDimension(R.styleable.ProgressbarIndicator_piStepLineStrokeWidth, dip2px(context, DEFAULT_LINE_STOKE_WIDTH));
            mStrokeAnimCircleWidth = mStrokeCircleWidth;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            attr.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mRadius = mBitMapHeight / 2;
        setMeasuredDimension(widthMeasureSpec, mRadius * 5);
        mCenterY = mRadius * 2;
        mStartX = mRadius * 2;
        mEndX = getWidth() - (mRadius * 2);
        mStepDistance = (mEndX - mStartX) / (mStepsCount - 1);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mCenterY = mRadius * 2;
        mStartX = mRadius * 2;
        mEndX = getWidth() - (mRadius * 2);
        mStepDistance = (mEndX - mStartX) / (mStepsCount - 1);
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
        this.mStepsCount = stepsCount;
        this.mMilestoneStatusList = milestoneStatusList;
        invalidate();
    }

    private void setmCurrentStepPosition(int mCurrentStepPosition) {
        this.mCurrentStepPosition = mCurrentStepPosition;
//        setCheckedAnimation();
        invalidate();
    }

    public interface OnClickListener {
        void onClick(int position);
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.mClickListener = onClickListener;
    }

    public void setmCurrentStatus(int mCurrentStatus) {
        this.mCurrentStatus = mCurrentStatus;
        this.mCurrentStepPosition = this.mCurrentStatus;
        invalidate();
    }

    private void setCheckedAnimation() {
        mCheckAnimator = ObjectAnimator.ofFloat(ProgressbarIndicator.this, "strokeAnimCircleWidth", 0f, mStrokeCircleWidth * 1.4f, mStrokeCircleWidth);
        mCheckAnimator.setDuration(500);
        mCheckAnimator.setInterpolator(new BounceInterpolator());
        mCheckAnimator.start();
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
            if (mCurrentStepPosition != currentItem) {
                mCurrentStepPosition = currentItem;
            }
        }

        // 3. add listener
        viewPager.addOnPageChangeListener(new ViewPagerOnChangeListener(this));

        setOnClickListener(new ViewPagerOnSelectedListener(viewPager));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mStepsCount < 1) {
            setVisibility(GONE);
            return;
        }
        super.onDraw(canvas);

        drawLines(canvas);

        drawCircles(canvas);

        drawBitmaps(canvas);

        drawSelectedCircles(canvas);

        drawTexts(canvas);

    }

    /**
     * draw Lines
     */
    private void drawLines(Canvas canvas) {
        int pointX = mStartX;
        mLinePaint.setStrokeWidth(mStrokeLineWidth);
        mLinePaint.setColor(mStepLineColor);
        mDashPath.moveTo(pointX, mCenterY);
        mDashPath.lineTo(getWidth() - mStartX, mCenterY);
        mLinePaint.setPathEffect(mDashPathEffect);
        canvas.drawPath(mDashPath, mLinePaint);
    }

    /*
    *draw circles
    * */
    private void drawCircles(Canvas canvas) {
        int pointX = mStartX;

        int radius = this.mRadius + dip2px(getContext(), 3);
        if (mMilestoneStatusList == null || mMilestoneStatusList.size() == 0) {
            for (int i = 0; i < mStepsCount; i++) {
                if (i < mCurrentStatus) {
                    mCirclePaint.setColor(mCircleCompleteColor);
                    canvas.drawCircle(pointX, mCenterY, radius, mCirclePaint);
                } else if (i == mCurrentStatus) {
                    if (mCurrentStatus == 0) {
                        mCirclePaint.setColor(mCircleUnOpenColor);
                        canvas.drawCircle(pointX, mCenterY, radius, mCirclePaint);
                    } else {
                        mCirclePaint.setColor(mCircleInProgressColor);
                        canvas.drawCircle(pointX, mCenterY, radius, mCirclePaint);
                    }
                } else {
                    mCirclePaint.setColor(mCircleUnOpenColor);
                    canvas.drawCircle(pointX, mCenterY, radius, mCirclePaint);
                }
                pointX += mStepDistance;
            }
        } else {
            for (int i = 0; i < mMilestoneStatusList.size(); i++) {
                if (mMilestoneStatusList.get(i).getStatus().equalsIgnoreCase(UIUtils.getString(R.string.task_resolved))) {
                    mCirclePaint.setColor(mCircleCompleteColor);
                    canvas.drawCircle(pointX, mCenterY, radius, mCirclePaint);
                } else if (mMilestoneStatusList.get(i).getStatus().equalsIgnoreCase(UIUtils.getString(R.string.task_inProgress))) {
                    if (mCurrentStatus == 0) {
                        mCirclePaint.setColor(mCircleUnOpenColor);
                        canvas.drawCircle(pointX, mCenterY, radius, mCirclePaint);
                    } else {
                        mCirclePaint.setColor(mCircleInProgressColor);
                        canvas.drawCircle(pointX, mCenterY, radius, mCirclePaint);
                    }
                } else {
                    mCirclePaint.setColor(mCircleUnOpenColor);
                    canvas.drawCircle(pointX, mCenterY, radius, mCirclePaint);
                }
                pointX += mStepDistance;
            }
        }
    }

    /*
    *draw bitmap
    * */
    private void drawBitmaps(Canvas canvas) {
        int pointX = mStartX;

        if (mMilestoneStatusList == null || mMilestoneStatusList.size() == 0) {
            for (int i = 0; i < mStepsCount; i++) {
                if (mIconList != null && mIconList.size() == 3) {
                    if (i < mCurrentStatus) {
                        canvas.drawBitmap(mIconList.get(2), pointX - mIconList.get(2).getWidth() / 2, mCenterY - mIconList.get(2).getHeight() / 2, null);
                    } else if (i == mCurrentStatus) {
                        if (mCurrentStatus == 0) {
                            canvas.drawBitmap(mIconList.get(0), pointX - mIconList.get(0).getWidth() / 2, mCenterY - mIconList.get(0).getHeight() / 2, null);
                        } else {
                            canvas.drawBitmap(mIconList.get(1), pointX - mIconList.get(1).getWidth() / 2, mCenterY - mIconList.get(1).getHeight() / 2, null);
                        }
                    } else {
                        canvas.drawBitmap(mIconList.get(0), pointX - mIconList.get(0).getWidth() / 2, mCenterY - mIconList.get(0).getHeight() / 2, null);
                    }
                    pointX += mStepDistance;
                }
            }
        } else {
            for (int i = 0; i < mMilestoneStatusList.size(); i++) {
                if (mIconList != null && mIconList.size() == 3) {
                    if (mMilestoneStatusList.get(i).getStatus().equalsIgnoreCase(UIUtils.getString(R.string.task_resolved))) {
                        canvas.drawBitmap(mIconList.get(2), pointX - mIconList.get(2).getWidth() / 2, mCenterY - mIconList.get(2).getHeight() / 2, null);
                    } else if (mMilestoneStatusList.get(i).getStatus().equalsIgnoreCase(UIUtils.getString(R.string.task_inProgress))) {
                        if (mCurrentStatus == 0) {
                            canvas.drawBitmap(mIconList.get(0), pointX - mIconList.get(0).getWidth() / 2, mCenterY - mIconList.get(0).getHeight() / 2, null);
                        } else {
                            canvas.drawBitmap(mIconList.get(1), pointX - mIconList.get(1).getWidth() / 2, mCenterY - mIconList.get(1).getHeight() / 2, null);
                        }
                    } else {
                        canvas.drawBitmap(mIconList.get(0), pointX - mIconList.get(0).getWidth() / 2, mCenterY - mIconList.get(0).getHeight() / 2, null);
                    }
                    pointX += mStepDistance;
                }
            }
        }
    }

    /*
    *draw selected circle
    * */
    private void drawSelectedCircles(Canvas canvas) {
        int pointX = mStartX;

        int radius = this.mRadius + dip2px(getContext(), 3);
        if (mMilestoneStatusList == null || mMilestoneStatusList.size() == 0) {
            for (int i = 0; i < mStepsCount; i++) {
                if (i == mCurrentStepPosition) {
                    if (i < mCurrentStatus) {
                        mCircleStokePaint.setColor(mCircleCompleteSelectedColor);
                    } else if (i == mCurrentStatus) {
                        if (mCurrentStatus == 0) {
                            mCircleStokePaint.setColor(mCircleUnOpenSelectedColor);
                        } else {
                            mCircleStokePaint.setColor(mCircleInProgressSelectedColor);
                        }
                    } else {
                        mCircleStokePaint.setColor(mCircleUnOpenSelectedColor);
                    }
                    //draw current step
                    mCircleStokePaint.setStrokeWidth(Math.round(mStrokeAnimCircleWidth));
                    canvas.drawCircle(pointX, mCenterY, radius + dip2px(getContext(), 2), mCircleStokePaint);
                }
                pointX += mStepDistance;
            }
        } else {
            for (int i = 0; i < mMilestoneStatusList.size(); i++) {
                if (i == mCurrentStepPosition) {
                    if (mMilestoneStatusList.get(i).getStatus().equalsIgnoreCase(UIUtils.getString(R.string.task_resolved))) {
                        mCircleStokePaint.setColor(mCircleCompleteSelectedColor);
                    } else if (mMilestoneStatusList.get(i).getStatus().equalsIgnoreCase(UIUtils.getString(R.string.task_inProgress))) {
                        if (mCurrentStatus == 0) {
                            mCircleStokePaint.setColor(mCircleUnOpenSelectedColor);
                        } else {
                            mCircleStokePaint.setColor(mCircleInProgressSelectedColor);
                        }
                    } else {
                        mCircleStokePaint.setColor(mCircleUnOpenSelectedColor);
                    }
                    //draw current step
                    mCircleStokePaint.setStrokeWidth(Math.round(mStrokeAnimCircleWidth));
                    canvas.drawCircle(pointX, mCenterY, radius + dip2px(getContext(), 2), mCircleStokePaint);
                }
                pointX += mStepDistance;
            }
        }
    }

    /*
    *draw texts
    * */
    private void drawTexts(Canvas canvas) {
        if (mMilestoneStatusList == null || mMilestoneStatusList.size() == 0) {
            if (mStatusLists != null && mStatusLists.length > 0) {
                for (int i = 0; i < mStepsCount; i++) {
                    String status = mStatusLists[i];
                    mTextPaint.setColor(mTextColor);
                    mTextPaint.setTextSize(mTextSize);
                    mTextPaint.getTextBounds(status, 0, status.length(), mTextBounds);
                    int textStartX = (Math.abs(mRadius - mTextBounds.width() / 2) + mRadius) + mStepDistance * i;
                    int textStartY = mRadius * 4 + (mTextBounds.height() / 2);
                    canvas.drawText(status, textStartX, textStartY, mTextPaint);
                }
            }
        } else {
            for (int i = 0; i < mMilestoneStatusList.size(); i++) {
                String status = mMilestoneStatusList.get(i).getName();
                mTextPaint.setColor(mTextColor);
                mTextPaint.setTextSize(mTextSize);
                mTextPaint.getTextBounds(status, 0, status.length(), mTextBounds);
                int textStartX = (Math.abs(mRadius - mTextBounds.width() / 2) + mRadius) + mStepDistance * i;
                int textStartY = mRadius * 4 + (mTextBounds.height() / 2);
                canvas.drawText(status, textStartX, textStartY, mTextPaint);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int pointX = mStartX;
        int xTouch;
        int yTouch;
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                xTouch = (int) event.getX(0);
                yTouch = (int) event.getY(0);
                for (int i = 0; i < mStepsCount; i++) {
                    if (Math.abs(xTouch - pointX) < mRadius + 5 && Math.abs(yTouch - mCenterY) < mRadius + 5) {

                        if (mClickListener != null) {
                            mClickListener.onClick(i);
                        }
                    }
                    pointX = pointX + mStepDistance;
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
            progressbarIndicator.setmCurrentStepPosition(position);
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
        savedState.mRadius = this.mRadius;
        savedState.mStepsCount = this.mStepsCount;
        savedState.mCurrentStepPosition = this.mCurrentStepPosition;
        savedState.mCurrentStatus = this.mCurrentStatus;
        savedState.mStepLineColor = this.mStepLineColor;
        savedState.mStepLineDoneColor = this.mStepLineDoneColor;
        savedState.mCircleUnOpenColor = this.mCircleUnOpenColor;
        savedState.mCircleCompleteColor = this.mCircleCompleteColor;
        savedState.mCircleInProgressColor = this.mCircleInProgressColor;
        savedState.mCircleUnOpenSelectedColor = this.mCircleUnOpenSelectedColor;
        savedState.mCircleInProgressSelectedColor = this.mCircleInProgressSelectedColor;
        savedState.mCircleCompleteSelectedColor = this.mCircleCompleteSelectedColor;
        savedState.mTextColor = this.mTextColor;
        savedState.mTextSize = this.mTextSize;
        savedState.mStrokeCircleWidth = this.mStrokeCircleWidth;
        savedState.mStrokeLineWidth = this.mStrokeLineWidth;
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
        this.mRadius = savedState.mRadius;
        this.mStepsCount = savedState.mStepsCount;
        this.mCurrentStepPosition = savedState.mCurrentStepPosition;
        this.mCurrentStatus = savedState.mCurrentStatus;
        this.mStepLineColor = savedState.mStepLineColor;
        this.mStepLineDoneColor = savedState.mStepLineDoneColor;
        this.mCircleUnOpenColor = savedState.mCircleUnOpenColor;
        this.mCircleCompleteColor = savedState.mCircleCompleteColor;
        this.mCircleInProgressColor = savedState.mCircleInProgressColor;
        this.mCircleUnOpenSelectedColor = savedState.mCircleUnOpenSelectedColor;
        this.mCircleInProgressSelectedColor = savedState.mCircleInProgressSelectedColor;
        this.mCircleCompleteSelectedColor = savedState.mCircleCompleteSelectedColor;
        this.mTextColor = savedState.mTextColor;
        this.mTextSize = savedState.mTextSize;
        this.mStrokeCircleWidth = savedState.mStrokeCircleWidth;
        this.mStrokeLineWidth = savedState.mStrokeLineWidth;

    }

    static class SavedState extends BaseSavedState {

        private int mRadius;
        private int mStepsCount;
        private int mCurrentStepPosition;
        private int mCurrentStatus;
        private int mStepLineColor;
        private int mStepLineDoneColor;
        private int mCircleUnOpenColor;
        private int mCircleCompleteColor;
        private int mCircleInProgressColor;
        private int mCircleUnOpenSelectedColor;
        private int mCircleInProgressSelectedColor;
        private int mCircleCompleteSelectedColor;
        private int mTextColor;
        private float mTextSize;
        private float mStrokeCircleWidth;
        private float mStrokeLineWidth;

        SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            mRadius = in.readInt();
            mStepsCount = in.readInt();
            mCurrentStepPosition = in.readInt();
            mCurrentStatus = in.readInt();
            mStepLineColor = in.readInt();
            mStepLineDoneColor = in.readInt();
            mCircleUnOpenColor = in.readInt();
            mCircleCompleteColor = in.readInt();
            mCircleInProgressColor = in.readInt();
            mCircleUnOpenSelectedColor = in.readInt();
            mCircleInProgressSelectedColor = in.readInt();
            mCircleCompleteSelectedColor = in.readInt();
            mTextColor = in.readInt();
            mTextSize = in.readFloat();
            mStrokeCircleWidth = in.readFloat();
            mStrokeLineWidth = in.readFloat();
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(mRadius);
            out.writeInt(mStepsCount);
            out.writeInt(mCurrentStepPosition);
            out.writeInt(mCurrentStatus);
            out.writeInt(mStepLineColor);
            out.writeInt(mStepLineDoneColor);
            out.writeInt(mCircleUnOpenColor);
            out.writeInt(mCircleCompleteColor);
            out.writeInt(mCircleInProgressColor);
            out.writeInt(mCircleUnOpenSelectedColor);
            out.writeInt(mCircleInProgressSelectedColor);
            out.writeInt(mCircleCompleteSelectedColor);
            out.writeInt(mTextColor);
            out.writeFloat(mTextSize);
            out.writeFloat(mStrokeCircleWidth);
            out.writeFloat(mStrokeLineWidth);
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
