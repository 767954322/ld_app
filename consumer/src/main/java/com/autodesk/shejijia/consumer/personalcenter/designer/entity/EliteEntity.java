package com.autodesk.shejijia.consumer.personalcenter.designer.entity;

import java.io.Serializable;

/**
 * @Author: lizhipeng
 * @Data: 16/11/17 下午3:56
 * @Description:
 */

public class EliteEntity implements Serializable {

    /**
     * fee : 0.06
     * payment_status : 1
     * payment_date : null
     * order_id : 4475
     * order_line_id : 7790
     */

    private MeasurementBean measurement;

    public MeasurementBean getMeasurement() {
        return measurement;
    }

    public void setMeasurement(MeasurementBean measurement) {
        this.measurement = measurement;
    }

    public static class MeasurementBean {
        private double fee;
        private int payment_status;
        private Object payment_date;
        private int order_id;
        private int order_line_id;

        public double getFee() {
            return fee;
        }

        public void setFee(double fee) {
            this.fee = fee;
        }

        public int getPayment_status() {
            return payment_status;
        }

        public void setPayment_status(int payment_status) {
            this.payment_status = payment_status;
        }

        public Object getPayment_date() {
            return payment_date;
        }

        public void setPayment_date(Object payment_date) {
            this.payment_date = payment_date;
        }

        public int getOrder_id() {
            return order_id;
        }

        public void setOrder_id(int order_id) {
            this.order_id = order_id;
        }

        public int getOrder_line_id() {
            return order_line_id;
        }

        public void setOrder_line_id(int order_line_id) {
            this.order_line_id = order_line_id;
        }
    }
}
