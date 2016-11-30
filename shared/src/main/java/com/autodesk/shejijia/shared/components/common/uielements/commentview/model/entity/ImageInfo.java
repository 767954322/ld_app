package com.autodesk.shejijia.shared.components.common.uielements.commentview.model.entity;

import android.net.Uri;
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
     * 文件路径
     */
    private String mPath;
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
    private PhotoSource ePhotoSource = PhotoSource.LOCAL;

    public ImageInfo(String uri, String path, String mDisplayName,long mAddedTime, long mSize) {
        this.mDisplayName = mDisplayName;
        this.mAddedTime = mAddedTime;
        this.mPictureURL = uri;
        this.mPath = path;
        this.mSize = mSize;
    }

    public ImageInfo(String uri, String mDisplayName,long mAddedTime, long mSize) {
        this.mDisplayName = mDisplayName;
        this.mAddedTime = mAddedTime;
        this.mPictureURL = uri;
        this.mSize = mSize;
    }

    public ImageInfo(String uri, String thumbnail, long mSize) {
        this.mPictureURL = uri;
        this.mThumbnailURL = thumbnail;
        this.mSize = mSize;
    }

    public ImageInfo(String uri, String thumbnail) {
        this.mPictureURL = uri;
        this.mThumbnailURL = thumbnail;
    }

    public ImageInfo() {
    }

    public String getPath() {
        return mPath;
    }

    public void setPath(String path) {
        this.mPath = path;
    }

    public String getThumbnailUri() {
        return mThumbnailURL;
    }

    public void setThumbnailUri(String uri) {
        this.mThumbnailURL = uri;
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

    public String getPictureUri() {
        return mPictureURL;
    }

    public void setPictureUri(String uri) {
        this.mPictureURL = uri;
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
        dest.writeString(this.mPath);
        dest.writeLong(this.mSize);
        dest.writeByte(isSelected ? (byte) 1 : (byte) 0);
        dest.writeInt(ePhotoSource.ordinal());
    }

    protected ImageInfo(Parcel in) {
        this.mDisplayName = in.readString();
        this.mAddedTime = in.readLong();
        this.mPictureURL = in.readString();
        this.mThumbnailURL = in.readString();
        this.mPath = in.readString();
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

    @Override
    public int hashCode() {
        if(mPictureURL != null){
            return mPictureURL.hashCode();
        }

        if(mPath != null){
            return mPictureURL.hashCode();
        }

        return super.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof ImageInfo) {
            return o.hashCode() == this.hashCode();
        }
        return super.equals(o);
    }
}
