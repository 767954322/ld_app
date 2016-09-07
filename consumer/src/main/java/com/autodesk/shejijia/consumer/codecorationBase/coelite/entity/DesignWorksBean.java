package com.autodesk.shejijia.consumer.codecorationBase.coelite.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by luchongbin on 16-8-19.
 */
public class DesignWorksBean implements Serializable{

    private String outerPic;
    private List<InnerPicListBean> innerPicList;

    public String getOuterPic() {
        return outerPic;
    }

    public void setOuterPic(String outerPic) {
        this.outerPic = outerPic;
    }

    public List<InnerPicListBean> getInnerPicList() {
        return innerPicList;
    }

    public void setInnerPicList(List<InnerPicListBean> innerPicList) {
        this.innerPicList = innerPicList;
    }

    public static class InnerPicListBean implements Serializable{
        private String android;
        private String iphone;

        public String getAndroid() {
            return android;
        }

        public void setAndroid(String android) {
            this.android = android;
        }

        public String getIphone() {
            return iphone;
        }

        public void setIphone(String iphone) {
            this.iphone = iphone;
        }
    }
}