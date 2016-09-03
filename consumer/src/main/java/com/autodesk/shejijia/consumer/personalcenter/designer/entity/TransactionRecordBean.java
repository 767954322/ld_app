package com.autodesk.shejijia.consumer.personalcenter.designer.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author DongXueQiu .
 * @version 1.0 .
 * @date 16-6-7
 * @file TransactionRecordBean.java  .
 * @brief .
 */
public class TransactionRecordBean implements Serializable {

    private int count;
    private int limit;
    private int offset;
    /**
     * adjustment : 0.01
     * create_date : 1460441064000
     * name : 焦旭全流程测试1354
     * order_line_id : 1562869
     * title : 量房费
     * type : 1
     */

    private List<DesignerTransListEntity> designer_trans_list;

    public void setCount(int count) {
        this.count = count;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public void setDesigner_trans_list(List<DesignerTransListEntity> designer_trans_list) {
        this.designer_trans_list = designer_trans_list;
    }

    public int getCount() {
        return count;
    }

    public int getLimit() {
        return limit;
    }

    public int getOffset() {
        return offset;
    }

    public List<DesignerTransListEntity> getDesigner_trans_list() {
        return designer_trans_list;
    }

    public static class DesignerTransListEntity implements Serializable {
        private double adjustment;
        private long create_date;
        private String name;
        private int order_line_id;
        private String title;
        private String type;

        public void setAdjustment(double adjustment) {
            this.adjustment = adjustment;
        }

        public void setCreate_date(long create_date) {
            this.create_date = create_date;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setOrder_line_id(int order_line_id) {
            this.order_line_id = order_line_id;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setType(String type) {
            this.type = type;
        }

        public double getAdjustment() {
            return adjustment;
        }

        public long getCreate_date() {
            return create_date;
        }

        public String getName() {
            return name;
        }

        public int getOrder_line_id() {
            return order_line_id;
        }

        public String getTitle() {
            return title;
        }

        public String getType() {
            return type;
        }
    }
}
