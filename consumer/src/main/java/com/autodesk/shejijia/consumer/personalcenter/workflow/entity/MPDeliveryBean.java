package com.autodesk.shejijia.consumer.personalcenter.workflow.entity;

import java.io.Serializable;

/**
 * <p>Description:</p>
 * <p>Copyright: Copyright (c) 2016</p>
 * <p>Company: http://www.leediancn.com</p>
 *
 * @author he.liu .
 * @date 2016-07-17.
 */
public class MPDeliveryBean implements Serializable {
    private MPFileBean files;
    private String type;
    private String designer_id;

    public MPFileBean getFiles() {
        return files;
    }

    public void setFiles(MPFileBean files) {
        this.files = files;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDesigner_id() {
        return designer_id;
    }

    public void setDesigner_id(String designer_id) {
        this.designer_id = designer_id;
    }
}
