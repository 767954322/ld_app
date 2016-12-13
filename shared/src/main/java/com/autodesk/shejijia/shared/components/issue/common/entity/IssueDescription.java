package com.autodesk.shejijia.shared.components.issue.common.entity;

import com.autodesk.shejijia.shared.components.common.uielements.commentview.model.entity.ImageInfo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Menghao.Gu on 2016/12/6.
 */

public class IssueDescription implements Serializable {

    private String mDescription;
    private String mAudioPath;
    private List<ImageInfo> mImagePath;

    public IssueDescription(String description, String audioPath, List<ImageInfo> imagePath) {
        this.mDescription = description;
        this.mAudioPath = audioPath;
        this.mImagePath = imagePath;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public String getmAudioPath() {
        return mAudioPath;
    }

    public void setmAudioPath(String mAudioPath) {
        this.mAudioPath = mAudioPath;
    }

    public List<ImageInfo> getmImagePath() {
        return mImagePath;
    }

    public void setmImagePath(List<ImageInfo> mImagePath) {
        this.mImagePath = mImagePath;
    }

    @Override
    public String toString() {
        return "IssueDescription{" +
                "mDescription='" + mDescription + '\'' +
                ", mAudioPath='" + mAudioPath + '\'' +
                ", mImagePath=" + mImagePath +
                '}';
    }
}
