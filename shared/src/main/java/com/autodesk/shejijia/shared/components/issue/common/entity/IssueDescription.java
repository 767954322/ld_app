package com.autodesk.shejijia.shared.components.issue.common.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Menghao.Gu on 2016/12/6.
 */

public class IssueDescription implements Serializable {

    private String mDescription;
    private String mAudioPath;
    private List<String> mImagePath;

    public IssueDescription(String mDescription, String mAudioPath, List<String> mImagePath) {
        this.mDescription = mDescription;
        this.mAudioPath = mAudioPath;
        this.mImagePath = mImagePath;
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

    public List<String> getmImagePath() {
        return mImagePath;
    }

    public void setmImagePath(List<String> mImagePath) {
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
