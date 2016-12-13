package com.autodesk.shejijia.shared.components.issue.common.entity;

import java.io.Serializable;

/**
 * Created by allengu on 16-12-13.
 */
public class IssueFiles implements Serializable {

    private String type;
    private String resource_file_id;
    private String resource_url;

    public IssueFiles(String type, String resource_file_id, String resource_url) {
        this.type = type;
        this.resource_file_id = resource_file_id;
        this.resource_url = resource_url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getResource_file_id() {
        return resource_file_id;
    }

    public void setResource_file_id(String resource_file_id) {
        this.resource_file_id = resource_file_id;
    }

    public String getResource_url() {
        return resource_url;
    }

    public void setResource_url(String resource_url) {
        this.resource_url = resource_url;
    }

    @Override
    public String toString() {
        return "IssueFiles{" +
                "type='" + type + '\'' +
                ", resource_file_id='" + resource_file_id + '\'' +
                ", resource_url='" + resource_url + '\'' +
                '}';
    }
}
