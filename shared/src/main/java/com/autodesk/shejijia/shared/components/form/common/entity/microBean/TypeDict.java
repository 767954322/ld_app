package com.autodesk.shejijia.shared.components.form.common.entity.microBean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by t_panya on 16/10/24.
 */

public class TypeDict implements Serializable {
    private ArrayList<String> status;
    @SerializedName("action_options")
    private ArrayList<String> actionOptions;
    @SerializedName("review_options")
    private ArrayList<String> reviewOptions;
    @SerializedName("supervisor_inquiry_options")
    private ArrayList<String> supervisorInquiryOptions;
    @SerializedName("supervisor_notification_options")
    private ArrayList<String> supervisorNotificationOptions;
    @SerializedName("customer_options")
    private ArrayList<String> customerOptions;
    @SerializedName("acceptance_options")
    private ArrayList<String> acceptanceOptions;
    @SerializedName("notification_options")
    private ArrayList<String> notificationOptions;
    @SerializedName("level_options")
    private ArrayList<String> levelOptions;
    @SerializedName("is_checked")
    private ArrayList<String> isChecked;

    public ArrayList<String> getStatus() {
        return status;
    }

    public void setStatus(ArrayList<String> status) {
        this.status = status;
    }

    public ArrayList<String> getActionOptions() {
        return actionOptions;
    }

    public void setActionOptions(ArrayList<String> actionOptions) {
        this.actionOptions = actionOptions;
    }

    public ArrayList<String> getReviewOptions() {
        return reviewOptions;
    }

    public void setReviewOptions(ArrayList<String> reviewOptions) {
        this.reviewOptions = reviewOptions;
    }

    public ArrayList<String> getSupervisorInquiryOptions() {
        return supervisorInquiryOptions;
    }

    public void setSupervisorInquiryOptions(ArrayList<String> supervisorInquiryOptions) {
        this.supervisorInquiryOptions = supervisorInquiryOptions;
    }

    public ArrayList<String> getSupervisorNotificationOptions() {
        return supervisorNotificationOptions;
    }

    public void setSupervisorNotificationOptions(ArrayList<String> supervisorNotificationOptions) {
        this.supervisorNotificationOptions = supervisorNotificationOptions;
    }

    public ArrayList<String> getCustomerOptions() {
        return customerOptions;
    }

    public void setCustomerOptions(ArrayList<String> customerOptions) {
        this.customerOptions = customerOptions;
    }

    public ArrayList<String> getAcceptanceOptions() {
        return acceptanceOptions;
    }

    public void setAcceptanceOptions(ArrayList<String> acceptanceOptions) {
        this.acceptanceOptions = acceptanceOptions;
    }

    public ArrayList<String> getNotificationOptions() {
        return notificationOptions;
    }

    public void setNotificationOptions(ArrayList<String> notificationOptions) {
        this.notificationOptions = notificationOptions;
    }

    public ArrayList<String> getLevelOptions() {
        return levelOptions;
    }

    public void setLevelOptions(ArrayList<String> levelOptions) {
        this.levelOptions = levelOptions;
    }

    public ArrayList<String> getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(ArrayList<String> isChecked) {
        this.isChecked = isChecked;
    }
}
