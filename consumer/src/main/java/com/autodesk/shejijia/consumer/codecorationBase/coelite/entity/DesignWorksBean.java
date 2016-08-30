package com.autodesk.shejijia.consumer.codecorationBase.coelite.entity;

import java.io.Serializable;

/**
 * Created by luchongbin on 16-8-19.
 */
public class DesignWorksBean implements Serializable{
    private String outerPic;
    private InnerPicList innerPicList;
    public class InnerPicList implements Serializable{
        private String innerPic1;
        private String innerPic2;
        private String innerPic3;
        private String innerPic4;


        public String getInnerPic1() {
            return innerPic1;
        }

        public void setInnerPic1(String innerPic1) {
            this.innerPic1 = innerPic1;
        }

        public String getInnerPic2() {
            return innerPic2;
        }

        public void setInnerPic2(String innerPic2) {
            this.innerPic2 = innerPic2;
        }

        public String getInnerPic3() {
            return innerPic3;
        }

        public void setInnerPic3(String innerPic3) {
            this.innerPic3 = innerPic3;
        }

        public String getInnerPic4() {
            return innerPic4;
        }

        public void setInnerPic4(String innerPic4) {
            this.innerPic4 = innerPic4;
        }
    }

    public String getOuterPic() {
        return outerPic;
    }

    public void setOuterPic(String outerPic) {
        this.outerPic = outerPic;
    }

    public InnerPicList getInnerPicList() {
        return innerPicList;
    }

    public void setInnerPicList(InnerPicList innerPicList) {
        this.innerPicList = innerPicList;
    }
}
