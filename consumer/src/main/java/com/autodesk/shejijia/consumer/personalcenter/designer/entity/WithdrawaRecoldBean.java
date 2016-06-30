package com.autodesk.shejijia.consumer.personalcenter.designer.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author DongXueQiu .
 * @version 1.0 .
 * @date 16-6-7
 * @file WithdrawaRecoldBean.java  .
 * @brief 提现记录.
 */
public class WithdrawaRecoldBean implements Serializable {

    private int count;
    private int limit;
    private int offset;
    /**
     * account_user_name : 杨丽
     * amount : 100.0
     * bank_name : 北京银行
     * date : 2016-04-14
     * deposit_card : 6224564487464136
     * remark : dekang.zhu   测试20160414
     * status : 2
     * target_account_id : 48
     * transLog_id : 505
     */

    private List<TranslogListEntity> translog_list;

    public void setCount(int count) {
        this.count = count;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public void setTranslog_list(List<TranslogListEntity> translog_list) {
        this.translog_list = translog_list;
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

    public List<TranslogListEntity> getTranslog_list() {
        return translog_list;
    }

    public static class TranslogListEntity {
        private String account_user_name;
        private double amount;
        private String bank_name;
        private String date;
        private String deposit_card;
        private String remark;
        private int status;
        private int target_account_id;
        private int transLog_id;

        public void setAccount_user_name(String account_user_name) {
            this.account_user_name = account_user_name;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public void setBank_name(String bank_name) {
            this.bank_name = bank_name;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public void setDeposit_card(String deposit_card) {
            this.deposit_card = deposit_card;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public void setTarget_account_id(int target_account_id) {
            this.target_account_id = target_account_id;
        }

        public void setTransLog_id(int transLog_id) {
            this.transLog_id = transLog_id;
        }

        public String getAccount_user_name() {
            return account_user_name;
        }

        public double getAmount() {
            return amount;
        }

        public String getBank_name() {
            return bank_name;
        }

        public String getDate() {
            return date;
        }

        public String getDeposit_card() {
            return deposit_card;
        }

        public String getRemark() {
            return remark;
        }

        public int getStatus() {
            return status;
        }

        public int getTarget_account_id() {
            return target_account_id;
        }

        public int getTransLog_id() {
            return transLog_id;
        }
    }
}
