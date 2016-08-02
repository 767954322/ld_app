package com.autodesk.shejijia.consumer.home.decorationdesigners.entity;

import java.util.List;

/**
 * @author he.liu .
 * @version v1.0 .
 * @date 2016-8-2 .
 * @file AttentionOrUnfollowDesignerBean.java .
 * @brief 关注或者取消关注实体类 .
 */
public class AttentionOrUnfollowDesignerBean {

    private int count;
    private int offset;
    private int limit;

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
        private String is_real_name;

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

        public String getIs_real_name() {
            return is_real_name;
        }

        public void setIs_real_name(String is_real_name) {
            this.is_real_name = is_real_name;
        }
    }
}
