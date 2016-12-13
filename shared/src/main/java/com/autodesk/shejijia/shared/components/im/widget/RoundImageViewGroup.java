package com.autodesk.shejijia.shared.components.im.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.utility.ImageUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by t_xuz on 12/13/16.
 * description: 用于群聊列表的带有圆角的viewGroup
 */

public class RoundImageViewGroup<T> extends ViewGroup {

    private int mRowCount;//行数
    private int mColumnCount; //列数

    private int mMaxSize; //最大图片数
    private int mGapSize; //图片之间的间隙大小
    private int mSingleImageSize;// 单张图片的尺寸大小
    private int mGridSize;   // 宫格大小,即图片大小

    private List<ImageView> mImageViewList = new ArrayList<>();
    private List<String> mImgDataList;

    public RoundImageViewGroup(Context context) {
        this(context, null);
    }

    public RoundImageViewGroup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundImageViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray attr = context.obtainStyledAttributes(attrs, R.styleable.RoundImageViewGroup);
        if (attr == null) {
            return;
        }

        try {
            this.mGapSize = (int) attr.getDimension(R.styleable.RoundImageViewGroup_gapSize, 0);
            this.mSingleImageSize = attr.getDimensionPixelSize(R.styleable.RoundImageViewGroup_singleImageSize, -1);
            this.mMaxSize = attr.getInt(R.styleable.RoundImageViewGroup_maxSize, 9);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            attr.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height;
        int totalWidth = width - getPaddingLeft() - getPaddingRight();
        if (mImgDataList != null && mImgDataList.size() > 0) {
            if (mImgDataList.size() == 1 && mSingleImageSize != -1) {
                mGridSize = mSingleImageSize > totalWidth ? totalWidth : mSingleImageSize;
            } else {
                mGridSize = (totalWidth - mGapSize * (mColumnCount - 1)) / mColumnCount;
            }
            int realHeight = mGridSize * mRowCount + mGapSize * (mRowCount - 1) + getPaddingTop() + getPaddingBottom();
            //height = width > realHeight ? width : realHeight;
            setMeasuredDimension(width, realHeight);
        } else {
            height = width > MeasureSpec.getSize(heightMeasureSpec) ? width : MeasureSpec.getSize(heightMeasureSpec);
            setMeasuredDimension(width, height);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (mImgDataList == null) {
            return;
        }
        int childCount = mImgDataList.size();
        for (int i = 0; i < childCount; i++) {
            ImageView childView = (ImageView) getChildAt(i);
            int rowNum = i / mColumnCount;
            int columnNum = i % mColumnCount;
            int left = (mGridSize + mGapSize) * columnNum + getPaddingLeft();
            int top = (mGridSize + mGapSize) * rowNum + getPaddingTop();
            int right = left + mGridSize;
            int bottom = top + mGridSize;

            childView.layout(left, top, right, bottom);
        }
    }

    public void setImageDataList(List<String> dataList) {
        if (dataList == null || dataList.isEmpty()) {
            this.setVisibility(GONE);
            return;
        } else {
            this.setVisibility(VISIBLE);
        }

        if (mMaxSize > 0 && dataList.size() > mMaxSize) {
            dataList = dataList.subList(0, mMaxSize);
        }

        int[] gridParam = calculateGridParam(dataList.size());
        mRowCount = gridParam[0];
        mColumnCount = gridParam[1];

        if (mImgDataList == null) { //第一次加入数据的时候
            int i = 0;
            while (i < dataList.size()) {
                ImageView imageView = getImageView(i);
                if (imageView == null) {
                    return;
                }
                ImageUtils.displayIconImage(dataList.get(i), imageView);
                addView(imageView, generateDefaultLayoutParams());
                i++;
            }
        } else {
            int oldViewCount = mImgDataList.size();
            int newViewCount = dataList.size();
            if (oldViewCount > newViewCount) {
                removeViews(newViewCount, oldViewCount - newViewCount);
            } else if (oldViewCount < newViewCount) {
                for (int i = oldViewCount; i < newViewCount; i++) {
                    ImageView imageView = getImageView(i);
                    if (imageView == null) {
                        return;
                    }
                    ImageUtils.displayIconImage(dataList.get(i), imageView);
                    addView(imageView, generateDefaultLayoutParams());
                }
            }
        }
        mImgDataList = dataList;
        requestLayout();
    }

    /*
    * 计算宫格行数与列数
    * gridParam[0] -- 行数
    * gridParam[1] -- 列数
    * */
    private static int[] calculateGridParam(int imagesSize) {
        int[] gridParam = new int[2];
        if (imagesSize < 3) {
            gridParam[0] = 1;
            gridParam[1] = imagesSize;
        } else if (imagesSize <= 4) {
            gridParam[0] = 2;
            gridParam[1] = 2;
        } else {
            gridParam[0] = imagesSize / 3 + (imagesSize % 3 == 0 ? 0 : 1);
            gridParam[1] = 3;
        }
        return gridParam;
    }

    private ImageView getImageView(final int position) {
        if (position < mImageViewList.size()) {//保证imageView的重用
            return mImageViewList.get(position);
        } else {
            ImageView imageView = new ImageView(getContext());
            mImageViewList.add(imageView);
            return imageView;
        }
    }

    public void setGapSize(int mGapSize) {
        this.mGapSize = mGapSize;
    }

    public void setMaxSize(int mMaxSize) {
        this.mMaxSize = mMaxSize;
    }

    public void setSingleImageSize(int mSingleImageSize) {
        this.mSingleImageSize = mSingleImageSize;
    }
}
