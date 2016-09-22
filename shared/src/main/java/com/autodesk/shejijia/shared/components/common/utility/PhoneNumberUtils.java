package com.autodesk.shejijia.shared.components.common.utility;

/**
 * Created by yaoxuehua on 16-9-21.
 * 手机号的判断
 *
 * 由于最近几个月,国内三大通信运行商，都有新手机号推出，所以要用最近的手机号判断，
 * 最好也判断三大运营商不同的手机号类型;
 */

public class PhoneNumberUtils {

    /**
     * 中国电信号码格式验证 手机段： 133,153,180,181,189,177,1700
     * **/
    private static final String CHINA_TELECOM_PATTERN = "(^1(33|53|77|8[019])\\d{8}$)|(^1700\\d{7}$)";

    /**
     * 中国联通号码格式验证 手机段：130,131,132,155,156,185,186,145,176,1709
     * **/
    private static final String CHINA_UNICOM_PATTERN = "(^1(3[0-2]|4[5]|5[56]|7[6]|8[56])\\d{8}$)|(^1709\\d{7}$)";

    /**
     * 中国移动号码格式验证
     * 手机段：134,135,136,137,138,139,150,151,152,157,158,159,182,183,184
     * ,187,188,147,178,1705
     * **/
    private static final String CHINA_MOBILE_PATTERN = "(^1(3[4-9]|4[7]|5[0-27-9]|7[8]|8[2-478])\\d{8}$)|(^1705\\d{7}$)";

    public static boolean justPhoneNumber(String phoneNumber){
        //有任何一个进入,随即认为该手机号码正确
        boolean isPhoneNumberMove = false;
        if (phoneNumber.matches(CHINA_MOBILE_PATTERN) || phoneNumber.matches(CHINA_UNICOM_PATTERN) || phoneNumber.matches(CHINA_TELECOM_PATTERN)){

            isPhoneNumberMove = true;

        }

        return isPhoneNumberMove;
    }
}
