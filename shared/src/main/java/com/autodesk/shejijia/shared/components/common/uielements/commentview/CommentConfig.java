package com.autodesk.shejijia.shared.components.common.uielements.commentview;

import android.os.Parcel;
import android.os.Parcelable;

import com.autodesk.shejijia.shared.components.common.entity.microbean.SHFile;

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

    public ModuleType eModuleType = ModuleType.MODULE_FORM;
    public RoleType eRoleType = RoleType.NORMAL;
    public DataSource eDataSource = DataSource.LOCAL;

    public String X_Token;
    public String onlineCommentContent;
    public String audioPath;
    public List<String> pictureData;
    public List<String> thumbnailData;
    public int selectModel;


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

    public CommentConfig setOnlineCommentContent(String onlineCommentContent) {
        this.onlineCommentContent = onlineCommentContent;
        return this;
    }

    public CommentConfig setAudioPath(String audioPath) {
        this.audioPath = audioPath;
        return this;
    }

    public CommentConfig setPictureData(List<String> pictureData) {
        this.pictureData = pictureData;
        return this;
    }

    public CommentConfig setThumbnailData(List<String> thumbnailData) {
        this.thumbnailData = thumbnailData;
        return this;
    }

    public List<String> getThumbnailData() {
        return thumbnailData;
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

    public String getOnlineCommentContent() {
        return onlineCommentContent;
    }

    public String getAudioPath() {
        return audioPath;
    }

    public List<String> getPictureData() {
        return pictureData;
    }

    public int getSelectModel() {
        return selectModel;
    }

    public CommentConfig(){
        eModuleType = ModuleType.MODULE_FORM;
        eRoleType = RoleType.NORMAL;
        eDataSource = DataSource.LOCAL;
        selectModel = MULTI_MODE;
        pictureData = new ArrayList<>();
        thumbnailData = new ArrayList<>();
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
        dest.writeString(X_Token);
        dest.writeString(onlineCommentContent);
        dest.writeString(audioPath);
        dest.writeStringList(pictureData);
        dest.writeStringList(thumbnailData);
        dest.writeInt(selectModel);
    }

    protected CommentConfig(Parcel in) {
        eModuleType = ModuleType.values()[in.readInt()];
        eRoleType = RoleType.values()[in.readInt()];
        eDataSource = DataSource.values()[in.readInt()];
        X_Token = in.readString();
        onlineCommentContent = in.readString();
        audioPath = in.readString();
        in.readStringList(pictureData);
        in.readStringList(thumbnailData);
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
