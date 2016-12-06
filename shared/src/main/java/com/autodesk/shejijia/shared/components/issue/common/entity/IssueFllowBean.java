package com.autodesk.shejijia.shared.components.issue.common.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/12/6.
 */

public class IssueFllowBean implements Serializable {

    private String mImagePath;
    private String mIssueFllowRole;
    private String mIssueFllowName;

    public IssueFllowBean(String mImagePath, String mIssueFllowRole, String mIssueFllowName) {
        this.mImagePath = mImagePath;
        this.mIssueFllowRole = mIssueFllowRole;
        this.mIssueFllowName = mIssueFllowName;
    }

    public String getmImagePath() {

        return mImagePath;
    }

    public void setmImagePath(String mImagePath) {
        this.mImagePath = mImagePath;
    }

    public String getmIssueFllowRole() {
        return mIssueFllowRole;
    }

    public void setmIssueFllowRole(String mIssueFllowRole) {
        this.mIssueFllowRole = mIssueFllowRole;
    }

    public String getmIssueFllowName() {
        return mIssueFllowName;
    }

    public void setmIssueFllowName(String mIssueFllowName) {
        this.mIssueFllowName = mIssueFllowName;
    }

    @Override
    public String toString() {
        return "IssueFllowBean{" +
                "mImagePath='" + mImagePath + '\'' +
                ", mIssueFllowRole='" + mIssueFllowRole + '\'' +
                ", mIssueFllowName='" + mIssueFllowName + '\'' +
                '}';
    }
}
