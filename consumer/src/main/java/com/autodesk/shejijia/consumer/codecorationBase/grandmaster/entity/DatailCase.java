package com.autodesk.shejijia.consumer.codecorationBase.grandmaster.entity;

import java.util.List;

/**
 * Created by allengu on 16-9-5.
 */
public class DatailCase {

    private String title;
    private String community_name;
    private List<Images> images;

    public DatailCase() {
    }

    public DatailCase(String title, String community_name, List<Images> images) {
        this.title = title;
        this.community_name = community_name;
        this.images = images;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCommunity_name() {
        return community_name;
    }

    public void setCommunity_name(String community_name) {
        this.community_name = community_name;
    }

    public List<Images> getImages() {
        return images;
    }

    public void setImages(List<Images> images) {
        this.images = images;
    }

    @Override
    public String toString() {
        return "DatailCase{" +
                "title='" + title + '\'' +
                ", community_name='" + community_name + '\'' +
                ", images=" + images +
                '}';
    }
}
