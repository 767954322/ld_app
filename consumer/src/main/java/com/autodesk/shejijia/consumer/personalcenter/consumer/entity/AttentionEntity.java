package com.autodesk.shejijia.consumer.personalcenter.consumer.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author DongXueQiu .
 * @version 1.0 .
 * @date 2016/8/1 0029 17:32 .
 * @file AttentionEntity  .
 * @brief 关注列表实体类 .
 */
public class AttentionEntity implements Serializable{


    /**
     * count : 1
     * offset : 0
     * limit : 0
     * designer_list : [{"avatar":"http://image.juranzx.com.cn:8082/img/5559489c498e9ec1e7d7c85b.img","nick_name":"鑼冩\u20ac濇\u20ac�","member_id":20730424,"hs_uid":"cbf508b0-6c94-4c53-9f9f-ab647885e435","is_real_name":2}]
     */

    private int count;
    private int offset;
    private int limit;
    /**
     * avatar : http://image.juranzx.com.cn:8082/img/5559489c498e9ec1e7d7c85b.img
     * nick_name : 鑼冩€濇€�
     * member_id : 20730424
     * hs_uid : cbf508b0-6c94-4c53-9f9f-ab647885e435
     * is_real_name : 2
     */

    private List<DesignerListBean> designer_list;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public List<DesignerListBean> getDesigner_list() {
        return designer_list;
    }

    public void setDesigner_list(List<DesignerListBean> designer_list) {
        this.designer_list = designer_list;
    }

    public static class DesignerListBean {
        private String avatar;
        private String nick_name;
        private String member_id;
        private String hs_uid;
        private int is_real_name;

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getNick_name() {
            return nick_name;
        }

        public void setNick_name(String nick_name) {
            this.nick_name = nick_name;
        }

        public String getMember_id() {
            return member_id;
        }

        public void setMember_id(String member_id) {
            this.member_id = member_id;
        }

        public String getHs_uid() {
            return hs_uid;
        }

        public void setHs_uid(String hs_uid) {
            this.hs_uid = hs_uid;
        }

        public int getIs_real_name() {
            return is_real_name;
        }

        public void setIs_real_name(int is_real_name) {
            this.is_real_name = is_real_name;
        }
    }
}
