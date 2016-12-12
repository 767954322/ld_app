package com.autodesk.shejijia.shared.components.form.common.entity;

import com.autodesk.shejijia.shared.components.common.utility.CastUtils;
import com.autodesk.shejijia.shared.components.form.common.entity.microBean.CheckItem;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by t_panya on 16/10/24.
 */

public class SHForm implements Serializable {

    protected String id;
    protected Integer status;
    protected Integer version;
    protected String formInstanceId;
    protected String category;
    protected String title;
    @SerializedName("doc_type")
    protected String docType;
    @SerializedName("form_template_id")
    protected String formTemplateId;
    @SerializedName("form_template_version")
    protected Integer formTemplateVersion;
    @SerializedName("project_id")
    protected String projectId;
    @SerializedName("task_id")
    protected String taskId;
    @SerializedName("created_at")
    protected String createdAt;
    @SerializedName("modified_by")
    protected String modifiedBy;
    @SerializedName("schema_version")
    protected String schemaVersion;
    @SerializedName("check_items_variability")
    protected Boolean checkItemsVariability;
    @SerializedName("check_items")
    protected ArrayList<CheckItem> checkItems;
    protected HashMap typeDict;
    protected List<String> statusOptions;

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

    public SHForm(HashMap map){
        init(map);
    }

    protected void init(HashMap map){
        this.title = (String) map.get("title");
        this.version = (Integer) map.get("version");
        this.category = (String) map.get("category");
        this.status = (Integer) map.get("status");
        this.checkItemsVariability = Boolean.getBoolean((String) map.get("check_items_variability"));
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
        this.id = (String) map.get("id");
        this.projectId = String.valueOf(map.get("project_id"));
        this.taskId = (String) map.get("task_id");
        this.formInstanceId = (String) map.get("form_instance_id");
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

    protected CheckItem getFormItemWithItemID(String itemID){
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

    public Map getUpdateFormData(){
        Map<String,Object> updateFormData = new HashMap<>();
        updateFormData.put("project_id",this.projectId);
        updateFormData.put("task_id",this.taskId);
        updateFormData.put("template_id",this.formTemplateId);
        updateFormData.put("form_instance_id",this.formInstanceId);
        updateFormData.put("status",this.status);
        updateFormData.put("version",this.version);
        List<Map> tempCheckItemList = new ArrayList<>();
        for(CheckItem checkItem : this.checkItems){
            Map<String,Object> miniCheckItem = new HashMap<>();
            miniCheckItem.put("item_id",checkItem.getItemId());
            miniCheckItem.put("value",checkItem.getFormFeedBack().combineUpdateData());
            tempCheckItemList.add(miniCheckItem);
        }
        updateFormData.put("check_items",tempCheckItemList);
        return updateFormData;
    }

}
