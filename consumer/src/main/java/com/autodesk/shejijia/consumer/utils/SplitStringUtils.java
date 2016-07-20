package com.autodesk.shejijia.consumer.utils;

import android.text.TextUtils;

/**
 * <p>Description:截取字符串的工具类</p>
 * <p>Copyright: Copyright (c) 2016</p>
 * <p>Company: http://www.leediancn.com</p>
 *
 * @author he.liu .
 * @date 2016-07-20.
 */
public class SplitStringUtils {
    private SplitStringUtils() {
    }

    /**
     * 字符串首字母为点，加上０
     *
     * @param measurement_price 　要截取的字符串
     * @return 增加了前缀0.的字符串
     */
    public static String splitStringDot(String measurement_price) {
        if (TextUtils.isEmpty(measurement_price)) {
            return "";
        }

        if (measurement_price.length() < 1) {
            return "";
        }

        if ("\\.".equals(measurement_price.subSequence(0, 1))) {
            return "0" + measurement_price;
        } else {
            return measurement_price;
        }
    }
}
