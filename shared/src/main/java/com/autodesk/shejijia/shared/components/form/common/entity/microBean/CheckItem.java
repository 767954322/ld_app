package com.autodesk.shejijia.shared.components.form.common.entity.microBean;

import com.autodesk.shejijia.shared.components.common.utility.CastUtils;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @SerializedName("action_type")
    private String actionType;
    @SerializedName("action_visibility")
    private Boolean actionVisibility;
    @SerializedName("action_changeability")
    private Boolean actionChangeability;
    @SerializedName("item_type")
    private String itemType;
    @SerializedName("acceptance_criteria")
    private List<Integer> acceptanceCriteria;
    @SerializedName("comment_conditions")

    private List<Integer> commentConditions;
    private FormFeedBack formFeedBack;
    private HashMap itemTypeDict;
    private List<String> checkOptions;
    private List<String> actionOptions;

    public void setAcceptanceCriteria(List<Integer> acceptanceCriteria) {
        this.acceptanceCriteria = acceptanceCriteria;
    }

    public void setCommentConditions(List<Integer> commentConditions) {
        this.commentConditions = commentConditions;
    }

    public FormFeedBack getFormFeedBack() {
        return formFeedBack;
    }

    public void setFormFeedBack(FormFeedBack formFeedBack) {
        this.formFeedBack = formFeedBack;
    }

    public HashMap getItemTypeDict() {
        return itemTypeDict;
    }

    public void setItemTypeDict(HashMap itemTypeDict) {
        this.itemTypeDict = itemTypeDict;
    }

    public List<String> getCheckOptions() {
        return checkOptions;
    }

    public void setCheckOptions(List<String> checkOptions) {
        this.checkOptions = checkOptions;
    }

    public List<String> getActionOptions() {
        return actionOptions;
    }

    public void setActionOptions(List<String> actionOptions) {
        this.actionOptions = actionOptions;
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

    public CheckItem(HashMap map){
        this.itemTypeDict = (HashMap) map.get("type_dict");
        this.itemId = (String) map.get("item_id");
        this.title = (String) map.get("title");
        this.category = (String) map.get("category");
        this.standard = (String) map.get("standard");
        this.checkType = (String) map.get("check_type");
        this.actionType = (String) map.get("action_type");
        this.itemType = (String) map.get("item_type");
        this.checkOptions = CastUtils.cast(itemTypeDict.get(checkType));
        this.actionOptions = CastUtils.cast(itemTypeDict.get(actionType));
        this.commentConditions = CastUtils.cast(map.get("comment_conditions"));
        this.acceptanceCriteria = CastUtils.cast(map.get("acceptance_criteria"));
        this.actionVisibility = (Boolean) map.get("action_visibility");
        this.actionChangeability = (Boolean) map.get("action_changeability");
        List<Map> values = CastUtils.cast(map.get("value"));

        if("hollowness".equals(itemType)){
            // TODO: 16/11/1
        }else {
            formFeedBack = new FormFeedBack(values);
        }
    }

    public void applyItemValue(List<Map> values){
        formFeedBack.applyInitData(values);

    }

}
