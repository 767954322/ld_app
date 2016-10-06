package com.autodesk.shejijia.consumer.uielements;

/**
 * @author allengu .
 * @version v1.0 .
 * @date 16-8-4 .
 * @file SingleClickUtils.java .
 * @brief 设置控件无法重复点击 .
 */
public class SingleClickUtils {


    private static long lastClickTime;

    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < 2000) {
            return true;
        }
        lastClickTime = time;
        return false;
    }
}

