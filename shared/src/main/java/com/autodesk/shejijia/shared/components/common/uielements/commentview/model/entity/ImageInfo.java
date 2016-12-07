package com.autodesk.shejijia.shared.components.common.uielements.commentview.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class ImageInfo implements Parcelable {

    public enum PhotoSource{
        LOCAL,
        CLOUD;
    }

    /**
     * 图片文件的名字
     */
    private String mDisplayName;
    /**
     * 文件被添加到 media provider的时间 ，单位是 从1970年开始的毫秒数
     */
    private long mAddedTime;
    /**
     * 文件存储路径 or 网络大图url
     */
    private String mPictureURL;
    /**
     * 文件缩略图 url
     */
    private String mThumbnailURL;
    /**
     * 图片大小
     */
    private long mSize;
    /**
     * 选择状态
     */
    private boolean isSelected;
    /**
     * 照片来源
     */
    private PhotoSource ePhotoSource;

    public ImageInfo(String mPath, String mDisplayName, long mAddedTime, long mSize) {
        this.mDisplayName = mDisplayName;
        this.mAddedTime = mAddedTime;
        this.mPictureURL = mPath;
        this.mSize = mSize;
    }

    public ImageInfo(String mPath, String thumbnail, long mSize) {
        this.mPictureURL = mPath;
        this.mThumbnailURL = thumbnail;
        this.mSize = mSize;
    }

    public ImageInfo(String mPath, String thumbnail) {
        this.mPictureURL = mPath;
        this.mThumbnailURL = thumbnail;
    }

    public String getUrl() {
        return mThumbnailURL;
    }

    public void setUrl(String url) {
        this.mThumbnailURL = url;
    }

    public long getSize() {
        return mSize;
    }

    public void setSize(long mSize) {
        this.mSize = mSize;
    }

    public String getDisplayName() {
        return mDisplayName;
    }

    public void setDisplayName(String mDisplayName) {
        this.mDisplayName = mDisplayName;
    }

    public long getAddedTime() {
        return mAddedTime;
    }

    public void setAddedTime(long mAddedTime) {
        this.mAddedTime = mAddedTime;
    }

    public String getPath() {
        return mPictureURL;
    }

    public void setPath(String mPath) {
        this.mPictureURL = mPath;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public PhotoSource getPhotoSource() {
        return ePhotoSource;
    }

    public void setPhotoSource(PhotoSource mPhotoSource) {
        this.ePhotoSource = mPhotoSource;
    }

    public ImageInfo() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mDisplayName);
        dest.writeLong(this.mAddedTime);
        dest.writeString(this.mPictureURL);
        dest.writeString(this.mThumbnailURL);
        dest.writeLong(this.mSize);
        dest.writeByte(isSelected ? (byte) 1 : (byte) 0);
        dest.writeInt(ePhotoSource.ordinal());
    }

    protected ImageInfo(Parcel in) {
        this.mDisplayName = in.readString();
        this.mAddedTime = in.readLong();
        this.mPictureURL = in.readString();
        this.mThumbnailURL = in.readString();
        this.mSize = in.readLong();
        this.isSelected = in.readByte() != 0;
        this.ePhotoSource = PhotoSource.values()[in.readInt()];
    }

    public static final Creator<ImageInfo> CREATOR = new Creator<ImageInfo>() {
        @Override
        public ImageInfo createFromParcel(Parcel source) {
            return new ImageInfo(source);
        }

        @Override
        public ImageInfo[] newArray(int size) {
            return new ImageInfo[size];
        }
    };
}
