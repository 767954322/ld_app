package com.autodesk.shejijia.enterprise.common.entity.microbean;

import java.io.Serializable;

/**
 * Created by t_xuz on 10/19/16.
 * version 1.0 update 10/19
 * Task 里 files字段
 */
public class File implements Serializable{

    private String name;
    private String file_id;
    private String file_type;
    private String file_url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFile_id() {
        return file_id;
    }

    public void setFile_id(String file_id) {
        this.file_id = file_id;
    }

    public String getFile_type() {
        return file_type;
    }

    public void setFile_type(String file_type) {
        this.file_type = file_type;
    }

    public String getFile_url() {
        return file_url;
    }

    public void setFile_url(String file_url) {
        this.file_url = file_url;
    }
}
