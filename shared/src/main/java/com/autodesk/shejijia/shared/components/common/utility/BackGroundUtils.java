package com.autodesk.shejijia.shared.components.common.utility;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by t_xuz on 11/2/16.
 * 对 window 窗体变暗变亮动画设置工具类
 */

public class BackGroundUtils {

    /*
    * 窗口背景变暗
    * dimBackground(1.0f,0.5f);
    * ------
    * 窗口背景变亮
    * dimBackground(0.5f,1.0f);
    * */
    public static void dimBackground(Activity context, final float from, final float to) {
        final Window window = context.getWindow();
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(from, to);
        valueAnimator.setDuration(500);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                WindowManager.LayoutParams params = window.getAttributes();
                params.alpha = (Float) animation.getAnimatedValue();
                window.setAttributes(params);
            }
        });

        valueAnimator.start();
    }
}
