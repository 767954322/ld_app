package com.autodesk.shejijia.consumer.manager;

/**
 * Created by he.liu 09/02/2016.
 */
public class WkTemplateConstants {

    /**
     * 1 -- 竞优（全流程）-- 普通订单
     * 2 -- 自选
     * 3 -- 北舒
     * 4 -- 精选
     */
    public static final String IS_AVERAGE = "1";
    public static final String IS_CHOOSE = "2";
    public static final String IS_BEISHU = "3";
    public static final String IS_ELITE = "4";

    /*等待确认量房*/
    public static final int INVITE_MEASURE = 11;
    /*订单结束 取消量房*/
    public static final int DECLINE_INVITE_MEASURE = 14;
    /*支付设计首款*/
    public static final int PAY_FOR_FIRST_FEE = 41;
    /*设计完成*/
    public static final int CONFIRM_DESIGN_RESULTS = 63;
    /*未确认设计交付物*/
    public static final int DELAY_CONFIRM_DESIGN_RESULTS = 64;


}
