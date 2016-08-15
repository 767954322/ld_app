package com.autodesk.shejijia.consumer.home.decorationdesigners.entity;

import java.util.List;

/**
 * @author he.liu .
 * @version v1.0 .
 * @date 2016-8-15 .
 * @file DesignerWorkTimeBean.java .
 * @brief 设计师设计风格实体类 .
 */
public class DesignerStyleBean {


    /**
     * code : japanese
     * name : 日式
     * extension : 日式风格
     * description : null
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
        private String extension;
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

        public String getExtension() {
            return extension;
        }

        public void setExtension(String extension) {
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
