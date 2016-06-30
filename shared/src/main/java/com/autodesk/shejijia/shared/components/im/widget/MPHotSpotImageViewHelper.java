package com.autodesk.shejijia.shared.components.im.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.im.constants.HotSpotsInfo;
import com.autodesk.shejijia.shared.components.common.utility.ImageProcessingUtil;

/**
 * Created by kubern on 25/05/16.
 */
public class MPHotSpotImageViewHelper
{
    public MPHotSpotImageViewHelper(Context context, ImageView mainImageView, ImageView hotspotImageView, Point pt)
    {
        mMainImageView = mainImageView;
        mHotspotImageView = hotspotImageView;
        mImageAbsolutePt.x = pt.x;
        mImageAbsolutePt.y = pt.y;
        mContext = context;
    }

    public void reloadImageWithScale(Bitmap bmp)
    {
        mOrgImageWidth = bmp.getWidth();
        mOrgImageHeight = bmp.getHeight();
        ImageProcessingUtil imageProcessingUtil = new ImageProcessingUtil();
        mAvailableImageWidth = imageProcessingUtil.getCurrentImgWH()[0];
        mAvailableImageHeight = (int) (imageProcessingUtil.getCurrentImgWH()[2] * HotSpotsInfo.subImg_height_factor);
        Bitmap scaledMainImage = imageProcessingUtil.getScaledBitmap(bmp, mAvailableImageWidth, mAvailableImageHeight);

        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) mMainImageView.getLayoutParams();
        lp.width = scaledMainImage.getWidth();
        lp.height = scaledMainImage.getHeight();
        mMainImageView.setLayoutParams(lp);
        mMainImageView.setImageBitmap(scaledMainImage);

        setupParams(scaledMainImage);
        zoomMainImage();
    }

    private void setupParams(Bitmap bmp)
    {
        setZoomScale(bmp);
        setMainImageTranslation();
    }

    private void setZoomScale(Bitmap bmp)
    {
        int imageW = bmp.getWidth();
        int imageH = bmp.getHeight();

        if (mAvailableImageWidth > imageW)
            mZoomScale = mAvailableImageWidth / (float) imageW;
        else if (mAvailableImageHeight > imageH)
            mZoomScale = mAvailableImageHeight / (float) imageH;

        setImageBounds(bmp, mZoomScale);
    }

    private void setImageBounds(Bitmap bmp, float zoomScale)
    {
        mImageRect.left = 0;
        mImageRect.top = 0;

        float right = bmp.getWidth() * zoomScale;
        float bottom = bmp.getHeight() * zoomScale;

        mImageRect.right = (int) (right);
        mImageRect.bottom = (int) (bottom);
    }


    private void setMainImageTranslation()
    {
        Point pt = getHotSpotImageViewPoint();

        Rect rect = new Rect();
        rect.set(0, 0, mAvailableImageWidth, mAvailableImageHeight);

        // we are trying to center point in available imageview space

        int deltaX = mImageRect.width() - pt.x;
        int translationX = pt.x - rect.centerX();

        int deltaY = mImageRect.height() - pt.y;
        int translationY = pt.y - rect.centerY();

        int halfWidth = (int) (mAvailableImageWidth / 2.0);
        int halfHeight = (int) (mAvailableImageHeight / 2.0);


        if (pt.x > mAvailableImageWidth)
        {

            if (translationX <= deltaX)
            {
                mTranslateX = translationX;
            }
            else
            {
                mTranslateX = pt.x - mAvailableImageWidth;

                if (deltaX > halfWidth)
                    mTranslateX += halfWidth;
                else
                    mTranslateX += deltaX;
            }
        }
        else
        {

            deltaX = mImageRect.width() - mAvailableImageWidth;

            if (translationX > 0 && deltaX > halfWidth)
            {
                mTranslateX = translationX;
            }
        }


        if (pt.y > mAvailableImageHeight)
        {

            if (translationY <= deltaY)
            {
                mTranslateY = translationY;
            }
            else
            {
                mTranslateY = pt.y - mAvailableImageHeight;

                if (deltaY > halfHeight)
                    mTranslateY += halfHeight;
                else
                    mTranslateY += deltaY;
            }
        }
        else
        {

            deltaY = mImageRect.height() - mAvailableImageHeight;

            if (translationY > 0 && deltaY > halfHeight)
            {
                mTranslateY = translationY;
            }
        }

        mTranslateX = (mTranslateX * -1);
        mTranslateY = (mTranslateY * -1);

    }

    private void zoomMainImage()
    {
        zoomMainImageWithAnimation();
    }


    private Point getAbsoluteImagePoint()
    {
        return new Point(mImageAbsolutePt.x, mImageAbsolutePt.y);
    }

    private Point getHotSpotImageViewPoint()
    {
        //convert point first interms of scale
        float x = mImageAbsolutePt.x * mImageRect.width() / (float) mOrgImageWidth;
        float y = mImageAbsolutePt.y * mImageRect.height() / (float) mOrgImageHeight;

        return new Point((int) x, (int) y);
    }

    // do not call this function directtly
    // zoomscale should be set first


    private void showHotspotImageWithAnimation()
    {
        Point pt = getHotSpotImageViewPoint();

        float xTranslation = pt.x - mContext.getResources().getDimension(R.dimen.size_42) / 2;
        float yTranlation = pt.y - mContext.getResources().getDimension(R.dimen.size_42);

        if (mTranslateX < 0)
            xTranslation -= Math.abs(mTranslateX);

        if (mTranslateY < 0)
            yTranlation -= Math.abs(mTranslateY);

        mHotspotImageView.setTranslationX(xTranslation);
        mHotspotImageView.setTranslationY(yTranlation);

        AnimatorSet set = new AnimatorSet();
        set.play(ObjectAnimator.ofFloat(mHotspotImageView, View.ALPHA, 0.0f, 1.0f));

        set.setDuration(500);
        set.setInterpolator(new DecelerateInterpolator());
        set.start();
    }


    private void zoomMainImageWithAnimation()
    {
        mMainImageView.setPivotX(0);
        mMainImageView.setPivotY(0);

        AnimatorSet set = new AnimatorSet();
        set
                .play(ObjectAnimator.ofFloat(mMainImageView, View.SCALE_X, 1.0f, mZoomScale))
                .with(ObjectAnimator.ofFloat(mMainImageView, View.SCALE_Y, 1.0f, mZoomScale))
                .with(ObjectAnimator.ofFloat(mMainImageView, View.X, 0.0f, mTranslateX))
                .with(ObjectAnimator.ofFloat(mMainImageView, View.Y, 0.0f, mTranslateY));

        set.setDuration(900);
        set.setInterpolator(new DecelerateInterpolator());
        set.addListener(new AnimatorListenerAdapter()
        {
            @Override
            public void onAnimationEnd(Animator animation)
            {

                showHotspotImageWithAnimation();

            }

            @Override
            public void onAnimationCancel(Animator animation)
            {

            }
        });
        set.start();

    }

    private int mAvailableImageWidth;
    private int mAvailableImageHeight;

    private int mOrgImageWidth;
    private int mOrgImageHeight;

    ImageView mMainImageView;
    ImageView mHotspotImageView;

    private float mZoomScale = 1.5f; //default scale
    private Rect mImageRect = new Rect();
    private float mTranslateX = 0.0f;
    private float mTranslateY = 0.0f;

    private Point mImageAbsolutePt = new Point();

    private Context mContext;
}
