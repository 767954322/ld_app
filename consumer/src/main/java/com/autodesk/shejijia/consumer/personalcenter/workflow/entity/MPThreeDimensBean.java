package com.autodesk.shejijia.consumer.personalcenter.workflow.entity;

import java.io.Serializable;

/**
 * Created by xueqiudong on 16-8-9.
 */
public class MPThreeDimensBean implements Serializable {
    private String design_asset_id;
    private String designer_id;

    public String getDesign_asset_id() {
        return design_asset_id;
    }

    public void setDesign_asset_id(String design_asset_id) {
        this.design_asset_id = design_asset_id;
    }

    public String getDesigner_id() {
        return designer_id;
    }

    public void setDesigner_id(String designer_id) {
        this.designer_id = designer_id;
    }

}
