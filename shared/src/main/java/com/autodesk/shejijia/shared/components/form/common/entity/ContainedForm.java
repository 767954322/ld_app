package com.autodesk.shejijia.shared.components.form.common.entity;

import com.autodesk.shejijia.shared.components.common.utility.CastUtils;
import com.autodesk.shejijia.shared.components.form.common.entity.microBean.CheckItem;
import com.autodesk.shejijia.shared.components.form.common.entity.microBean.TypeDict;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by t_panya on 16/10/24.
 */

public class ContainedForm implements Serializable {

    private String id;
    private Integer status;
    private Integer version;
    private String formInstanceId;
    private String category;
    private String title;
    @SerializedName("doc_type")
    private String docType;
    @SerializedName("form_template_id")
    private String formTemplateId;
    @SerializedName("form_template_version")
    private Integer formTemplateVersion;
    @SerializedName("project_id")
    private String projectId;
    @SerializedName("task_id")
    private String taskId;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("modified_by")
    private String modifiedBy;
    @SerializedName("schema_version")
    private String schemaVersion;
    @SerializedName("check_items_variability")
    private Boolean checkItemsVariability;
    @SerializedName("check_items")
    private ArrayList<CheckItem> checkItems;
    private HashMap typeDict;
    private List<String> statusOptions;

    public HashMap getTypeDict() {
        return typeDict;
    }

    public void setTypeDict(HashMap typeDict) {
        this.typeDict = typeDict;
    }

    public List<String> getStatusOptions() {
        return statusOptions;
    }

    public void setStatusOptions(List<String> statusOptions) {
        this.statusOptions = statusOptions;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSchemaVersion() {
        return schemaVersion;
    }

    public void setSchemaVersion(String schemaVersion) {
        this.schemaVersion = schemaVersion;
    }

    public Boolean getCheckItemsVariability() {
        return checkItemsVariability;
    }

    public void setCheckItemsVariability(Boolean checkItemsVariability) {
        this.checkItemsVariability = checkItemsVariability;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFormInstanceId() {
        return formInstanceId;
    }

    public void setFormInstanceId(String formInstanceId) {
        this.formInstanceId = formInstanceId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getFormTemplateId() {
        return formTemplateId;
    }

    public void setFormTemplateId(String formTemplateId) {
        this.formTemplateId = formTemplateId;
    }

    public Integer getFormTemplateVersion() {
        return formTemplateVersion;
    }

    public void setFormTemplateVersion(Integer formTemplateVersion) {
        this.formTemplateVersion = formTemplateVersion;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public ArrayList<CheckItem> getCheckItems() {
        return checkItems;
    }

    public void setCheckItems(ArrayList<CheckItem> checkItems) {
        this.checkItems = checkItems;
    }

    public ContainedForm(HashMap map){
        init(map);
    }

    private void init(HashMap map){
        this.formInstanceId = (String) map.get("id");
        this.formTemplateId = (String) map.get("id");
        this.title = (String) map.get("title");
        this.version = (Integer) map.get("version");
        this.category = (String) map.get("category");
        this.status = (Integer) map.get("status");
        this.checkItemsVariability = (Boolean) map.get("check_items_variability");
        this.typeDict = (HashMap) map.get("type_dict");
        this.statusOptions = CastUtils.cast(typeDict.get("status")) ;
        this.checkItems = new ArrayList<>();

        List<Map> tempItems = CastUtils.cast(map.get("check_items"));
        if(tempItems != null){
            for(Map itemMap : tempItems){
                if(itemMap instanceof HashMap) {
                    itemMap.put("type_dict", this.typeDict);
                    CheckItem checkItem = new CheckItem((HashMap) itemMap);
                    checkItems.add(checkItem);
                }
            }
        }
    }

    public void applyFormData(HashMap map){
        this.projectId = String.valueOf(map.get("project_id"));
        this.taskId = (String) map.get("task_id");
        this.formInstanceId = (String) map.get("formInstanceId");
        this.formTemplateId = (String) map.get("form_template_id");
        this.status = (Integer) map.get("status");
        this.version = (Integer) map.get("version");

        List<Map> tempItems = CastUtils.cast(map.get("check_items"));
        if(tempItems != null){
            for(Map itemMap : tempItems){
                CheckItem item = getFormItemWithItemID((String) itemMap.get("item_id"));
                if(item != null ){
                    List<Map> values = CastUtils.cast(itemMap.get("value"));
                    item.applyItemValue(values);
                }
            }
        }
    }

    private CheckItem getFormItemWithItemID(String itemID){
        if(checkItems == null || itemID == null){
            return null;
        }
        for(CheckItem item : checkItems){
            if(item.getItemId().equals(itemID)){
                return item;
            }
        }
        return null;
    }

}
