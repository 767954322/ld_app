package com.autodesk.shejijia.consumer.utils.AppDataFormatValidator;

import android.text.TextUtils;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.shared.components.common.utility.RegexUtil;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;

import java.util.Formatter;
import java.util.Locale;

/**
 * Created by franco on 2016/9/3.
 */
public class MPDesignFormatValidator extends MPFormatValidator{

    public boolean isNameValid(String name)
    {
        if  (isStringSemanticsNull(name))
            return false;

        if  (!name.matches(RegexUtil.NAME_REGEX1))
            return false;

        return  true;
    }

    public boolean isEmailValid(String mailstr)
    {
        if  (isStringSemanticsNull(mailstr))
            return false;

        if  (!mailstr.matches(RegexUtil.EMAIL_REGEX))
            return false;

        return  true;
    }

    public boolean isAddressValid(String Address)
    {
        if  (isStringSemanticsNull(Address))
            return false;

        return  isStringLenthValid(Address,2,32);
    }






    public String getStrProvinceCityDistrict(String Province,String City,String District){

        String a,b,c;
        StringBuilder sb = new StringBuilder();
        Formatter formatter = new Formatter(sb, Locale.getDefault());

        a= (isStringSemanticsNull(Province))?(""):(Province);
        b= (isStringSemanticsNull(City))?(""):(City);
        c= (isStringSemanticsNull(District))?(""):(District);

        String str=formatter.format("%s %s %s", a, b, c).toString();

        return str;
    }

    public String getStringWithNullDefaultEmpty(String str){

        return getStringWithNullDefaultString(str,"");
    }

    public String getStringWithNullDefaultString(String str,String defaultStr){

        String Strval;
        StringBuilder sb = new StringBuilder();
        Formatter formatter = new Formatter(sb, Locale.getDefault());

        Strval= (isStringSemanticsNull(str))?(defaultStr):(str);

        return Strval;
    }


}
