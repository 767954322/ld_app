package com.autodesk.shejijia.shared.components.common.uielements.mtab;

import android.content.res.Resources;
import android.util.TypedValue;

public class Utils {
    public static int dpToPx(Resources resources, float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.getDisplayMetrics());
    }
}
