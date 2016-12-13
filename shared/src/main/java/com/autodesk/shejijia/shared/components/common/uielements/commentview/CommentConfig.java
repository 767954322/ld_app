package com.autodesk.shejijia.shared.components.common.uielements.commentview;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by t_panya on 16/11/29.
 */

public class CommentConfig implements Parcelable{

    public static final int AVATAR_MODE = 0x0;

    public static final int MULTI_MODE = 0x1;

    /**
     * enum 单独用Serializable
     */
    public enum ModuleType{     //模块类型
        MODULE_FORM,        //表单
        MODULE_PATROL;      //巡查
    }

    public enum RoleType{       //判断角色类型
        NORMAL,                 //不可编辑
        SUPER_EDIT;            //可编辑
    }

    public enum DataSource{
        LOCAL,              //本地
        CLOUD;              //网络
    }

    public enum LayoutType{
        EDIT,           //编辑
        SHOW;           //展示
    }

    public ModuleType eModuleType = ModuleType.MODULE_FORM;
    public RoleType eRoleType = RoleType.NORMAL;
    public DataSource eDataSource = DataSource.LOCAL;
    public LayoutType eLayoutType = LayoutType.EDIT;

    public String X_Token;
    public String commentContent;
    public String audioPath;
    public int selectModel;

    public LayoutType geteLayoutType() {
        return eLayoutType;
    }

    public CommentConfig seteLayoutType(LayoutType eLayoutType) {
        this.eLayoutType = eLayoutType;
        return this;
    }

    public CommentConfig seteModuleType(ModuleType eModuleType) {
        this.eModuleType = eModuleType;
        return this;
    }

    public CommentConfig seteRoleType(RoleType eRoleType) {
        this.eRoleType = eRoleType;
        return this;
    }

    public CommentConfig setX_Token(String x_Token) {
        X_Token = x_Token;
        return this;
    }

    public CommentConfig setCommentContent(String commentContent) {
        this.commentContent = commentContent;
        return this;
    }

    public CommentConfig setAudioPath(String audioPath) {
        this.audioPath = audioPath;
        return this;
    }

    public CommentConfig setSelectModel(int selectModel) {
        this.selectModel = selectModel;
        return this;
    }

    public void seteDataSource(DataSource eDataSource) {
        this.eDataSource = eDataSource;
    }

    // get
    public DataSource geteDataSource() {
        return eDataSource;
    }
    public ModuleType geteModuleType() {
        return eModuleType;
    }

    public RoleType geteRoleType() {
        return eRoleType;
    }

    public String getX_Token() {
        return X_Token;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public String getAudioPath() {
        return audioPath;
    }

    public int getSelectModel() {
        return selectModel;
    }

    public CommentConfig(){
        eModuleType = ModuleType.MODULE_FORM;
        eRoleType = RoleType.NORMAL;
        eDataSource = DataSource.LOCAL;
        eLayoutType = LayoutType.EDIT;
        selectModel = MULTI_MODE;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(eModuleType.ordinal());
        dest.writeInt(eRoleType.ordinal());
        dest.writeInt(eDataSource.ordinal());
        dest.writeInt(eLayoutType.ordinal());
        dest.writeString(X_Token);
        dest.writeString(commentContent);
        dest.writeString(audioPath);
        dest.writeInt(selectModel);
    }

    protected CommentConfig(Parcel in) {
        eModuleType = ModuleType.values()[in.readInt()];
        eRoleType = RoleType.values()[in.readInt()];
        eDataSource = DataSource.values()[in.readInt()];
        eLayoutType = LayoutType.values()[in.readInt()];
        X_Token = in.readString();
        commentContent = in.readString();
        audioPath = in.readString();
        selectModel = in.readInt();
    }

    public static final Creator<CommentConfig> CREATOR = new Creator<CommentConfig>() {
        @Override
        public CommentConfig createFromParcel(Parcel in) {
            return new CommentConfig(in);
        }

        @Override
        public CommentConfig[] newArray(int size) {
            return new CommentConfig[size];
        }
    };
}
