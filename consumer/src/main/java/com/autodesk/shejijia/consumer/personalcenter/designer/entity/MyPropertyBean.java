package com.autodesk.shejijia.consumer.personalcenter.designer.entity;

import java.io.Serializable;

/**
 * @author DongXueQiu .
 * @version 1.0 .
 * @date 16-6-7
 * @file MyPropertyBean.java  .
 * @brief 我的资产.
 */
public class MyPropertyBean implements Serializable {

    private String account_user_name;
    private String amount;
    private String bank_info_id;
    private String bank_name;
    private String branch_bank_name;
    private String deposit_card;
    private String finance_account_info_id;
    private String gmt_create;
    private String gmt_modified;
    private String guarantee_amount;
    private String is_deleted;
    private String member_id;
    private String tenant_id;

    public String getAccount_user_name() {
        return account_user_name;
    }

    public void setAccount_user_name(String account_user_name) {
        this.account_user_name = account_user_name;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getBank_info_id() {
        return bank_info_id;
    }

    public void setBank_info_id(String bank_info_id) {
        this.bank_info_id = bank_info_id;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getBranch_bank_name() {
        return branch_bank_name;
    }

    public void setBranch_bank_name(String branch_bank_name) {
        this.branch_bank_name = branch_bank_name;
    }

    public String getDeposit_card() {
        return deposit_card;
    }

    public void setDeposit_card(String deposit_card) {
        this.deposit_card = deposit_card;
    }

    public String getFinance_account_info_id() {
        return finance_account_info_id;
    }

    public void setFinance_account_info_id(String finance_account_info_id) {
        this.finance_account_info_id = finance_account_info_id;
    }

    public String getGmt_create() {
        return gmt_create;
    }

    public void setGmt_create(String gmt_create) {
        this.gmt_create = gmt_create;
    }

    public String getGmt_modified() {
        return gmt_modified;
    }

    public void setGmt_modified(String gmt_modified) {
        this.gmt_modified = gmt_modified;
    }

    public String getGuarantee_amount() {
        return guarantee_amount;
    }

    public void setGuarantee_amount(String guarantee_amount) {
        this.guarantee_amount = guarantee_amount;
    }

    public String getIs_deleted() {
        return is_deleted;
    }

    public void setIs_deleted(String is_deleted) {
        this.is_deleted = is_deleted;
    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public String getTenant_id() {
        return tenant_id;
    }

    public void setTenant_id(String tenant_id) {
        this.tenant_id = tenant_id;
    }
}
