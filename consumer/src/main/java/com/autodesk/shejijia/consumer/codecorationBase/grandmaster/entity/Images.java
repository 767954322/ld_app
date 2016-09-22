package com.autodesk.shejijia.consumer.codecorationBase.grandmaster.entity;

/**
 * Created by allengu on 16-9-5.
 */
public class Images {

    private String file_name;
    private String file_url;
    private boolean is_primary;

    public Images(String file_name, String file_url, boolean is_primary) {
        this.file_name = file_name;
        this.file_url = file_url;
        this.is_primary = is_primary;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public String getFile_url() {
        return file_url;
    }

    public void setFile_url(String file_url) {
        this.file_url = file_url;
    }

    public boolean is_primary() {
        return is_primary;
    }

    public void setIs_primary(boolean is_primary) {
        this.is_primary = is_primary;
    }

    @Override
    public String toString() {
        return "Images{" +
                "file_name='" + file_name + '\'' +
                ", file_url='" + file_url + '\'' +
                ", is_primary=" + is_primary +
                '}';
    }
}
