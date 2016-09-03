package com.autodesk.shejijia.consumer.utils;

import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

/**
 * Created by：yangxuewu on 16/7/21 16:58
 */
public class AnimationUtil {
    private static final String TAG = AnimationUtil.class.getSimpleName();

    /**
     * 从控件所在位置移动到控件的底部
     *(
     * int fromXType,
     * float fromXValue,
     * int toXType,
     * float toXValue,
       int fromYType,
       float fromYValue,
       int toYType,
       float toYValue
     * @return
     */
    public static TranslateAnimation moveToViewBottom() {
        TranslateAnimation mHiddenAction = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF,
                0.0f,
                Animation.RELATIVE_TO_SELF,
                0.0f,
                Animation.RELATIVE_TO_SELF,
                0.0f,
                Animation.RELATIVE_TO_SELF,
                1.0f);
        mHiddenAction.setDuration(700);
        mHiddenAction.setFillBefore(false);
        return mHiddenAction;
    }

    /**
     * 从控件的底部移动到控件所在位置
     *
     * @return
     */
    public static TranslateAnimation moveToViewLocation() {
        TranslateAnimation mHiddenAction = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF,
                0.0f,
                Animation.RELATIVE_TO_SELF,
                0.0f,
                Animation.RELATIVE_TO_SELF,
                1.0f,
                Animation.RELATIVE_TO_SELF,
                0.0f);
        mHiddenAction.setDuration(700);
        mHiddenAction.setFillBefore(false);
        return mHiddenAction;
    }
}
