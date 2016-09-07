package com.autodesk.shejijia.consumer.home.decorationlibrarys.entity;

import android.support.annotation.Nullable;

import java.io.Serializable;
import java.util.List;

/**
 * Created by t_xuz on 9/6/16.
 * 重新组装的 案例详情内部,不同类型对应的图片列表
 */
public class Case3DDetailImageListBean implements Serializable{

    private String type;//类型
    private boolean isLocal;// 用于标记是否是本地图片,true 代表是本地图片,false代表网络图片
    private List<String> imageList; //图片url集合

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getImageList() {
        return imageList;
    }

    public void setImageList(List<String> imageList) {
        this.imageList = imageList;
    }

    public boolean isLocal() {
        return isLocal;
    }

    public void setLocal(boolean local) {
        isLocal = local;
    }

    @Override
    public String toString() {
        return "Case3DDetailImageListBean{" +
                "type='" + type + '\'' +
                ", isLocal=" + isLocal +
                ", imageList=" + imageList +
                '}';
    }
}
