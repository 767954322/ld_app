package com.autodesk.shejijia.shared.components.common.entity.microbean;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by t_xuz on 10/19/16.
 * version 2.0 update 10/24
 * Task 里 files字段
 */
public class ConstructionFile implements Serializable{

    @SerializedName("file_id")
    private String fileId;
    private String type;
    private String name;
    private String md5sum;
    private int size;
    @SerializedName("public_url")
    private String publicUrl;
    @SerializedName("thumbnail_url")
    private String thumbnailUrlPrefix;

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMd5sum() {
        return md5sum;
    }

    public void setMd5sum(String md5sum) {
        this.md5sum = md5sum;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getPublicUrl() {
        return publicUrl;
    }

    public void setPublicUrl(String publicUrl) {
        this.publicUrl = publicUrl;
    }

    public String getThumbnailUrl() {
        String thumbnailUrl = thumbnailUrlPrefix;
        if (!TextUtils.isEmpty(thumbnailUrlPrefix)) {
            thumbnailUrl += "Medium.jpg";
        }
        return thumbnailUrl;
    }

    public String getThumbnailUrlPrefix() {
        return thumbnailUrlPrefix;
    }

    public void setThumbnailUrlPrefix(String thumbnailUrlPrefix) {
        this.thumbnailUrlPrefix = thumbnailUrlPrefix;
    }
}
