package com.autodesk.shejijia.shared.components.form.entity;

import com.autodesk.shejijia.shared.components.form.entity.microBean.CheckItem;
import com.autodesk.shejijia.shared.components.form.entity.microBean.FeedBack;
import com.autodesk.shejijia.shared.components.form.entity.microBean.TypeDict;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by t_panya on 16/10/24.
 */

public class ContainedForm implements Serializable {

    private String id;
    private Integer status;
    private Integer version;
    private String formInstanceId;
    private String category;
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
    private List<CheckItem> checkItems;

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

    public List<CheckItem> getCheckItems() {
        return checkItems;
    }

    public void setCheckItems(List<CheckItem> checkItems) {
        this.checkItems = checkItems;
    }
}
