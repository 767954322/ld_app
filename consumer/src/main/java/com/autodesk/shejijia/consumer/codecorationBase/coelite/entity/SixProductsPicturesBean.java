package com.autodesk.shejijia.consumer.codecorationBase.coelite.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by luchongbin on 16-8-19.
 */
public class SixProductsPicturesBean implements Serializable{

    private AndroidBean android;

    public AndroidBean getAndroid() {
        return android;
    }

    public void setAndroid(AndroidBean android) {
        this.android = android;
    }

    public static class AndroidBean implements Serializable{

        private List<StudioBean> studio;

        private List<SelectionBean> selection;

        private List<MasterBean> master;

        private List<BiddingBean> bidding;

        private List<BackgroundBean> background;

        public List<StudioBean> getStudio() {
            return studio;
        }

        public void setStudio(List<StudioBean> studio) {
            this.studio = studio;
        }

        public List<BackgroundBean> getBackground() {
            return background;
        }

        public void setBackground(List<BackgroundBean> background) {
            this.background = background;
        }

        public List<BiddingBean> getBidding() {
            return bidding;
        }

        public void setBidding(List<BiddingBean> bidding) {
            this.bidding = bidding;
        }

        public List<MasterBean> getMaster() {
            return master;
        }

        public void setMaster(List<MasterBean> master) {
            this.master = master;
        }

        public List<SelectionBean> getSelection() {
            return selection;
        }

        public void setSelection(List<SelectionBean> selection) {
            this.selection = selection;
        }

        public static class StudioBean implements Serializable{
            private String banner;

            public String getBanner() {
                return banner;
            }

            public void setBanner(String banner) {
                this.banner = banner;
            }
        }

        public static class SelectionBean implements Serializable{
            private String png;

            public String getPng() {
                return png;
            }

            public void setPng(String png) {
                this.png = png;
            }
        }

        public static class MasterBean implements Serializable{
            private String poster;

            public String getPoster() {
                return poster;
            }

            public void setPoster(String poster) {
                this.poster = poster;
            }
        }

        public static class BiddingBean implements Serializable{
            private String back;

            public String getBack() {
                return back;
            }

            public void setBack(String back) {
                this.back = back;
            }
        }

        public static class BackgroundBean implements Serializable{
            private String pic_1;

            public String getPic_1() {
                return pic_1;
            }

            public void setPic_1(String pic_1) {
                this.pic_1 = pic_1;
            }
        }
    }
}