package com.autodesk.shejijia.shared.components.common.entity.microbean;

import java.io.Serializable;

/**
 * Created by t_panya on 16/11/1.
 */

public class SHFile {
    private String fileId;
    private String fileType;
    private String fileName;
    private String picture_url;
    private Long size;

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getPicture_url() {
        return picture_url;
    }

    public void setPicture_url(String picture_url) {
        this.picture_url = picture_url;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public SHFile(){

    }
}
