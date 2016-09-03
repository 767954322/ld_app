package com.autodesk.shejijia.consumer.personalcenter.workflow.entity;

import java.io.Serializable;

/**
 * @author he.liu .
 * @version v1.0 .
 * @date 2016-07-17 .
 * @file MPOrderBean.java .
 * @brief 当前设计师订单信息实体类 .
 */
public class MPOrderBean implements Serializable {
    private String designer_id;
    private String order_line_no;
    private String order_no;
    private String order_status;
    private String order_type;

    public String getDesigner_id() {
        return designer_id;
    }

    public void setDesigner_id(String designer_id) {
        this.designer_id = designer_id;
    }

    public String getOrder_line_no() {
        return order_line_no;
    }

    public void setOrder_line_no(String order_line_no) {
        this.order_line_no = order_line_no;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public String getOrder_type() {
        return order_type;
    }

    public void setOrder_type(String order_type) {
        this.order_type = order_type;
    }
}
