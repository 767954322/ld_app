package com.autodesk.shejijia.shared.components.common.uielements;

import android.content.Context;
import android.os.Build;

/**
 * 悬浮按钮工具类
 */
final class FloatingActionUtil {

    private FloatingActionUtil() {
    }

    static int dpToPx(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return Math.round(dp * scale);
    }

    static boolean hasJellyBean() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
    }

    static boolean hasLollipop() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }
}
