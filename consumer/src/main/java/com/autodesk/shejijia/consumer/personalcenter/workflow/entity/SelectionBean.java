package com.autodesk.shejijia.consumer.personalcenter.workflow.entity;

import java.io.Serializable;

/**
 * Created by luchongbin on 16-8-19.
 */
public class SelectionBean implements Serializable {
    /**
     * fee : 0.01
     * payment_date : null
     * order_line_id : 2819
     * order_id : 1795
     * payment_status : 1
     */

    private MeasurementBean measurement;

    public MeasurementBean getMeasurement() {
        return measurement;
    }

    public void setMeasurement(MeasurementBean measurement) {
        this.measurement = measurement;
    }

    public static class MeasurementBean implements Serializable{
        private String fee;
        private String payment_date;
        private String order_line_id;
        private String order_id;
        private String payment_status;

        public String getFee() {
            return fee;
        }

        public void setFee(String fee) {
            this.fee = fee;
        }

        public String getPayment_date() {
            return payment_date;
        }

        public void setPayment_date(String payment_date) {
            this.payment_date = payment_date;
        }

        public String getOrder_line_id() {
            return order_line_id;
        }

        public void setOrder_line_id(String order_line_id) {
            this.order_line_id = order_line_id;
        }

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public String getPayment_status() {
            return payment_status;
        }

        public void setPayment_status(String payment_status) {
            this.payment_status = payment_status;
        }
    }
}
