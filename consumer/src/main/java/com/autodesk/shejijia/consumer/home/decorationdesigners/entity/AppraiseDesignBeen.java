package com.autodesk.shejijia.consumer.home.decorationdesigners.entity;

import java.util.List;

/**
 * @author yaoxuehua .
 * @version 1.0 .
 * @date on 16-8-11
 * @file AppraiseDesignBeen  .
 * @brief 查看设计师appraiseFragment评价的数据been.
 */
public class AppraiseDesignBeen {


    /**
     * count : 2
     * offset : 0
     * limit : 10
     * estimates : [{"avatar":"http://sherpapreview-standard.s3.cn-north-1.amazonaws.com.cn/Preview/Images/Member/default_avatar.png","member_name":"china3","member_id":20730198,"member_estimate":"BBBBB","member_grade":4,"estimate_date":"2016-07-28 01:39:55"},{"avatar":"http://sherpapreview-standard.s3.cn-north-1.amazonaws.com.cn/Preview/Images/Member/default_avatar.png","member_name":"china3","member_id":20730198,"member_estimate":"AAAAA","member_grade":5,"estimate_date":"2016-07-28 01:36:55"}]
     */

    private int count;//评价的人数
    private int offset;
    private int limit;
    /**
     * avatar : http://sherpapreview-standard.s3.cn-north-1.amazonaws.com.cn/Preview/Images/Member/default_avatar.png
     * member_name : china3
     * member_id : 20730198
     * member_estimate : BBBBB
     * member_grade : 4
     * estimate_date : 2016-07-28 01:39:55
     */

    private List<EstimatesBean> estimates;

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

    public List<EstimatesBean> getEstimates() {
        return estimates;
    }

    public void setEstimates(List<EstimatesBean> estimates) {
        this.estimates = estimates;
    }

    public static class EstimatesBean {
        private String avatar;
        private String member_name;
        private int member_id;
        private String member_estimate;
        private int member_grade;
        private String estimate_date;

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getMember_name() {
            return member_name;
        }

        public void setMember_name(String member_name) {
            this.member_name = member_name;
        }

        public int getMember_id() {
            return member_id;
        }

        public void setMember_id(int member_id) {
            this.member_id = member_id;
        }

        public String getMember_estimate() {
            return member_estimate;
        }

        public void setMember_estimate(String member_estimate) {
            this.member_estimate = member_estimate;
        }

        public int getMember_grade() {
            return member_grade;
        }

        public void setMember_grade(int member_grade) {
            this.member_grade = member_grade;
        }

        public String getEstimate_date() {
            return estimate_date;
        }

        public void setEstimate_date(String estimate_date) {
            this.estimate_date = estimate_date;
        }
    }
}
