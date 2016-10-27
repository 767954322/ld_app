package com.autodesk.shejijia.shared.components.form.entity.microBean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by t_panya on 16/10/24.
 */

public class CheckItem implements Serializable {
    private String category;
    private String title;
    private String standard;
    @SerializedName("item_id")
    private String itemId;
    @SerializedName("check_type")
    private String checkType;
    private ArrayList<String> checkOptions;
    @SerializedName("action_type")
    private String actionType;
    private ArrayList<String> actionOptions;
    @SerializedName("action_visibility")
    private Boolean actionVisibility;
    @SerializedName("action_changeability")
    private Boolean actionChangeability;
    @SerializedName("item_type")
    private String itemType;
    @SerializedName("acceptance_criteria")
    private ArrayList<Integer> acceptanceCriteria;
    @SerializedName("comment_conditions")
    private ArrayList<Integer> commentConditions;
    private ArrayList<FeedBack> value;

    public ArrayList<FeedBack> getValue() {
        return value;
    }

    public void setValue(ArrayList<FeedBack> value) {
        this.value = value;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getCheckType() {
        return checkType;
    }

    public void setCheckType(String checkType) {
        this.checkType = checkType;
    }

    public Boolean getActionVisibility() {
        return actionVisibility;
    }

    public void setActionVisibility(Boolean actionVisibility) {
        this.actionVisibility = actionVisibility;
    }

    public Boolean getActionChangeability() {
        return actionChangeability;
    }

    public void setActionChangeability(Boolean actionChangeability) {
        this.actionChangeability = actionChangeability;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public List<Integer> getAcceptanceCriteria() {
        return acceptanceCriteria;
    }

    public void setAcceptanceCriteria(ArrayList<Integer> acceptanceCriteria) {
        this.acceptanceCriteria = acceptanceCriteria;
    }

    public List<Integer> getCommentConditions() {
        return commentConditions;
    }

    public void setCommentConditions(ArrayList<Integer> commentConditions) {
        this.commentConditions = commentConditions;
    }

    public void exChangeLocal(){

    }

}
