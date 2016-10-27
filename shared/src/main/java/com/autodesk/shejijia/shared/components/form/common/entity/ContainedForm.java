package com.autodesk.shejijia.shared.components.form.common.entity;

import com.autodesk.shejijia.shared.components.form.common.entity.microBean.CheckItem;
import com.autodesk.shejijia.shared.components.form.common.entity.microBean.TypeDict;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

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
    private Integer projectId;
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
    @SerializedName("type_dict")
    private TypeDict typeDict;
    @SerializedName("check_items")
    private ArrayList<CheckItem> checkItems;

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

    public TypeDict getTypeDict() {
        return typeDict;
    }

    public void setTypeDict(TypeDict typeDict) {
        this.typeDict = typeDict;
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

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
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

    public void applyFormData(ContainedForm form){
        this.formTemplateId = form.getFormTemplateId();
        this.formInstanceId = form.getFormInstanceId();
        this.projectId = form.getProjectId();
        this.taskId = form.getTaskId();
        this.status = form.getStatus();
        this.version = form.getVersion();
        ArrayList<CheckItem> items = form.getCheckItems();
        for(int i = 0; i < items.size(); i++){
            CheckItem item = getItemWithId(items.get(i).getItemId());
            if(item != null){
                item.setValue(items.get(i).getValue());
            }
        }
    }

    private CheckItem getItemWithId(String itemId){
        for(CheckItem item : checkItems){
            if((item.getItemId()).equals(itemId)){
                return item;
            }
        }
        return null;
    }

}
