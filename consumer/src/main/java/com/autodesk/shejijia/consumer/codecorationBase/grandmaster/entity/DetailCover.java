package com.autodesk.shejijia.consumer.codecorationBase.grandmaster.entity;

/**
 * Created by allengu on 16-8-29.
 */
public class DetailCover {

    private  String name;
    private  String file_id;
    private  String public_url;

    public DetailCover(String public_url, String name, String file_id) {
        this.public_url = public_url;
        this.name = name;
        this.file_id = file_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFile_id() {
        return file_id;
    }

    public void setFile_id(String file_id) {
        this.file_id = file_id;
    }

    public String getPublic_url() {
        return public_url;
    }

    public void setPublic_url(String public_url) {
        this.public_url = public_url;
    }

    @Override
    public String toString() {
        return "ProfileCover{" +
                "name='" + name + '\'' +
                ", file_id='" + file_id + '\'' +
                ", public_url='" + public_url + '\'' +
                '}';
    }
}
