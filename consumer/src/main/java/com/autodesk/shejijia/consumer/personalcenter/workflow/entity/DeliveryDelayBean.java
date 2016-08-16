package com.autodesk.shejijia.consumer.personalcenter.workflow.entity;

/**
 * @author he.liu .
 * @version v1.0 .
 * @date 2016-7-28 .
 * @file DeliveryDelayBean.java .
 * @brief 点击延期确认后获取数据的实体类 .
 */
public class DeliveryDelayBean {

    /**
     * 判断是否延期过
     * 　　　　０：没有延期过
     * 　　　　１：延期过
     */
    public String delayCount;

    /**
     * 剩余交付时间：
     * 默认值为７
     * 点击延期后变为14
     */
    public String remain_days;

    /**
     * 延期以后的时间，暂无用
     */
    public String acceptDate;

}
