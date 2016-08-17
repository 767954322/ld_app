package com.autodesk.shejijia.consumer.personalcenter.workflow.entity;

import java.io.Serializable;

/**
 * @author he.liu .
 * @version v1.0 .
 * @date 2016-07-17 .
 * @file MPPaymentBean.java .
 * @brief 全流程支付的实体类 .
 */
public class MPPaymentBean implements Serializable {
    private String create_date;
    private String measurement_fee;
    private String paid_fee;
    private String total_fee;
    private String unpaid_fee;

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public String getMeasurement_fee() {
        return measurement_fee;
    }

    public void setMeasurement_fee(String measurement_fee) {
        this.measurement_fee = measurement_fee;
    }

    public String getPaid_fee() {
        return paid_fee;
    }

    public void setPaid_fee(String paid_fee) {
        this.paid_fee = paid_fee;
    }

    public String getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(String total_fee) {
        this.total_fee = total_fee;
    }

    public String getUnpaid_fee() {
        return unpaid_fee;
    }

    public void setUnpaid_fee(String unpaid_fee) {
        this.unpaid_fee = unpaid_fee;
    }
}
