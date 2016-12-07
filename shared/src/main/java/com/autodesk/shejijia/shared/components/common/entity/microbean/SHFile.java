package com.autodesk.shejijia.shared.components.common.entity.microbean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by t_panya on 16/11/1.
 */

public class SHFile implements Serializable, Parcelable{
    private String fileId;
    private String fileType;
    private String fileName;
    private String pictureUrl;
    private String thumbnailUrl;
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

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String picture_url) {
        this.pictureUrl = picture_url;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public SHFile(){

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(fileId);
        dest.writeString(fileType);
        dest.writeString(fileName);
        dest.writeString(pictureUrl);
        dest.writeString(thumbnailUrl);
    }

    protected SHFile(Parcel in) {
        fileId = in.readString();
        fileType = in.readString();
        fileName = in.readString();
        pictureUrl = in.readString();
        thumbnailUrl = in.readString();
    }

    public static final Creator<SHFile> CREATOR = new Creator<SHFile>() {
        @Override
        public SHFile createFromParcel(Parcel in) {
            return new SHFile(in);
        }

        @Override
        public SHFile[] newArray(int size) {
            return new SHFile[size];
        }
    };
}
