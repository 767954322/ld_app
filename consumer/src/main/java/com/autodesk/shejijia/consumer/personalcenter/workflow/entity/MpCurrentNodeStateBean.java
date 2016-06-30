package com.autodesk.shejijia.consumer.personalcenter.workflow.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author   he.liu .
 * @version  v1.0 .
 * @date       2016-6-12 .
 * @file          MpCurrentNodeStateBean.java .
 * @brief        当前节点对应状态的json转化实体类.
 */
public class MpCurrentNodeStateBean implements Serializable {

    /**
     * wk_template_id : 1
     * wName : 应标
     * wk_cur_node : [{"wk_cur_node_id":"-1","nName":"应标中"},{"wk_cur_node_id":"1","nName":"量房"},{"wk_cur_node_id":"2","nName":"支付量房费"},{"wk_cur_node_id":"3","nName":"确认合同"},{"wk_cur_node_id":"4","nName":"支付设计首款"},{"wk_cur_node_id":"5","nName":"支付设计尾款"},{"wk_cur_node_id":"6","nName":"等待交付物"}]
     */

    private ArrayList<RootEntity> Root;

    public ArrayList<RootEntity> getRoot() {
        return Root;
    }

    public void setRoot(ArrayList<RootEntity> Root) {
        this.Root = Root;
    }

    public static class RootEntity implements Serializable {
        private String wk_template_id;
        private String wName;
        /**
         * wk_cur_node_id : -1
         * nName : 应标中
         */

        private ArrayList<WkCurNodeEntity> wk_cur_node;

        public String getWk_template_id() {
            return wk_template_id;
        }

        public void setWk_template_id(String wk_template_id) {
            this.wk_template_id = wk_template_id;
        }

        public String getWName() {
            return wName;
        }

        public void setWName(String wName) {
            this.wName = wName;
        }

        public ArrayList<WkCurNodeEntity> getWk_cur_node() {
            return wk_cur_node;
        }

        public void setWk_cur_node(ArrayList<WkCurNodeEntity> wk_cur_node) {
            this.wk_cur_node = wk_cur_node;
        }

        public static class WkCurNodeEntity implements Serializable {
            private String wk_cur_node_id;
            private String nName;

            public String getWk_cur_node_id() {
                return wk_cur_node_id;
            }

            public void setWk_cur_node_id(String wk_cur_node_id) {
                this.wk_cur_node_id = wk_cur_node_id;
            }

            public String getNName() {
                return nName;
            }

            public void setNName(String nName) {
                this.nName = nName;
            }
        }
    }
}
