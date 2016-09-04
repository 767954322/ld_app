package com.autodesk.shejijia.consumer.utils.AppDataFormatValidator;

import android.text.TextUtils;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.shared.components.common.uielements.CustomProgress;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.AlertView;
import com.autodesk.shejijia.shared.components.common.utility.RegexUtil;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by franco on 2016/9/3.
 */
public class MPFormatValidator {

    public boolean isStringSemanticsNull(String str){

        if (str==null)
            return  true;
        if (TextUtils.isEmpty(str))
            return  true;

        if ("none".equals(str)
                || "null".equals(str)
                || "none".equals(str)
                || TextUtils.isEmpty(str)) {
            return true;

        }
        return false;
    }

    public boolean isStringLenthValid(String str ,int min ,int max)
    {
        if  (isStringSemanticsNull(str))
            return false;

        if  (str.length()<min)
            return false;

        if  (str.length()>max)
            return false;

        return  true;
    }

    public boolean isStringPositiveNumberValid(String Number)
    {
        if  (isStringSemanticsNull(Number))
            return false;

        return  Number.matches(RegexUtil.POSITIVE_INTEGER_REGEX);
    }








}
