package com.autodesk.shejijia.consumer.personalcenter.designer.entity;

import java.util.List;

/**
 * Created by yaoxuehua on 16-8-16.
 */
public class DesignerDesignCostBean {


    /**
     * code : 0
     * name : 30-60
     * extension : null
     * description : 元/㎡
     */

    private List<RelateInformationListBean> relate_information_list;

    public List<RelateInformationListBean> getRelate_information_list() {
        return relate_information_list;
    }

    public void setRelate_information_list(List<RelateInformationListBean> relate_information_list) {
        this.relate_information_list = relate_information_list;
    }

    public static class RelateInformationListBean {
        private String code;
        private String name;
        private Object extension;
        private String description;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Object getExtension() {
            return extension;
        }

        public void setExtension(Object extension) {
            this.extension = extension;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
