package com.autodesk.shejijia.consumer.personalcenter.workflow.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author   he.liu .
 * @version  v1.0 .
 * @date       2016-6-12 .
 * @file          MPSubNodeStateBean.java .
 * @brief       当前子节点json文件转换的实体类.
 */
public class MPSubNodeStateBean implements Serializable {

    private ArrayList<RootEntity> Root;

    public ArrayList<RootEntity> getRoot() {
        return Root;
    }

    public void setRoot(ArrayList<RootEntity> Root) {
        this.Root = Root;
    }

    public static class RootEntity implements Serializable {
        private String wk_cur_sub_node_id;
        private String wk_cur_sub_name;
        private ArrayList<DetailEntity> detail;

        public String getWk_cur_sub_node_id() {
            return wk_cur_sub_node_id;
        }

        public void setWk_cur_sub_node_id(String wk_cur_sub_node_id) {
            this.wk_cur_sub_node_id = wk_cur_sub_node_id;
        }

        public String getWk_cur_sub_name() {
            return wk_cur_sub_name;
        }

        public void setWk_cur_sub_name(String wk_cur_sub_name) {
            this.wk_cur_sub_name = wk_cur_sub_name;
        }

        public List<DetailEntity> getDetail() {
            return detail;
        }

        public void setDetail(ArrayList<DetailEntity> detail) {
            this.detail = detail;
        }

        public static class DetailEntity implements Serializable {
            private String message;
            private String selectShow;

            public String getMessage() {
                return message;
            }

            public void setMessage(String message) {
                this.message = message;
            }

            public String getSelectShow() {
                return selectShow;
            }

            public void setSelectShow(String selectShow) {
                this.selectShow = selectShow;
            }
        }
    }
}
