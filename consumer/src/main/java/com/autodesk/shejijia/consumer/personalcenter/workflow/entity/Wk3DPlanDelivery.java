package com.autodesk.shejijia.consumer.personalcenter.workflow.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author he.liu .
 * @version v1.0 .
 * @date 2016-6-12 .
 * @file Wk3DPlanDelivery.java .
 * @brief 某一套设计方案的实体类 .
 */
public class Wk3DPlanDelivery implements Serializable {

    private int count;

    private List<DeliveryFilesEntity> deliveryFiles;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<DeliveryFilesEntity> getDeliveryFiles() {
        return deliveryFiles;
    }

    public void setDeliveryFiles(List<DeliveryFilesEntity> deliveryFiles) {
        this.deliveryFiles = deliveryFiles;
    }

    public static class DeliveryFilesEntity implements Serializable {
        private String id;
        private String type;
        private String usage_type;
        private String url;
        private String name;
        private String submitted_date;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUsage_type() {
            return usage_type;
        }

        public void setUsage_type(String usage_type) {
            this.usage_type = usage_type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSubmitted_date() {
            return submitted_date;
        }

        public void setSubmitted_date(String submitted_date) {
            this.submitted_date = submitted_date;
        }
    }
}
