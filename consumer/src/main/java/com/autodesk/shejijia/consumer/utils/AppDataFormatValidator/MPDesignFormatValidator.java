package com.autodesk.shejijia.consumer.utils.AppDataFormatValidator;

import com.autodesk.shejijia.shared.components.common.utility.RegexUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;
import java.util.Locale;

/**
 * Created by franco on 2016/9/3.
 */
public class MPDesignFormatValidator extends MPFormatValidator {

    public boolean isNameValid(String name) {
        if (isStringSemanticsNull(name))
            return false;

        if (!name.matches(RegexUtil.NAME_REGEX1))
            return false;

        return true;
    }

    /// 判断合同中姓名格式的正则 .
    public boolean isContractNameValid(String name) {
        if (isStringSemanticsNull(name))
            return false;

        if (!name.matches(RegexUtil.NAME_REGEX))
            return false;

        return true;
    }

    public boolean isEmailValid(String mailstr) {
        if (isStringSemanticsNull(mailstr))
            return false;

        if (!mailstr.matches(RegexUtil.EMAIL_REGEX))
            return false;

        return true;
    }

    public boolean isStringValid(String str) {
        if (isStringSemanticsNull(str))
            return false;

        return true;
    }

    public boolean isMobileValid(String mobinum) {
        if (isStringSemanticsNull(mobinum))
            return false;

        if (!mobinum.matches(RegexUtil.PHONE_REGEX))
            return false;

        return true;
    }

    public boolean isAddressValid(String Address) {
        if (isStringSemanticsNull(Address))
            return false;

        return isStringLenthValid(Address, 2, 32);
    }


    public String getStrProvinceCityDistrict(String Province, String City, String District) {

        String a, b, c;
        StringBuilder sb = new StringBuilder();
        Formatter formatter = new Formatter(sb, Locale.getDefault());

        a = (isStringSemanticsNull(Province)) ? ("") : (Province);
        b = (isStringSemanticsNull(City)) ? ("") : (City);
        c = (isStringSemanticsNull(District)) ? ("") : (District);

        String str = formatter.format("%s %s %s", a, b, c).toString();

        return str;
    }

    public String getStringWithNullDefaultEmpty(String str) {

        return getStringWithNullDefaultString(str, "");
    }


    public String getStrDateToString(String time) {

        String re_StrTime = null;


        long lcc_time = Long.valueOf(time);

        Date date = new Date(lcc_time);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        String dateText = sdf.format(date);

        return dateText;
    }


    public String getStringWithNullDefaultString(String str, String defaultStr) {

        String Strval;
        StringBuilder sb = new StringBuilder();
        Formatter formatter = new Formatter(sb, Locale.getDefault());

        Strval = (isStringSemanticsNull(str)) ? (defaultStr) : (str);

        return Strval;
    }


}
