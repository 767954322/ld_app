package com.autodesk.shejijia.consumer.codecorationBase.studio.fragment.entity;


import java.io.Serializable;


public class ImageFileRsp implements Serializable {


    /**
     * @Fields fileId 文件id
     */
    private String file_id;

    /**
     * @Fields fileName 文件名称
     */
    private String file_name;

    /**
     * @Fields fileUrl 文件url
     */
    private String file_url;


    public String getFile_id() {
        return file_id;
    }

    public void setFile_id(String file_id) {
        this.file_id = file_id;
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

    public ImageFileRsp(String file_id, String file_name, String file_url) {
        this.file_id = file_id;
        this.file_name = file_name;
        this.file_url = file_url;
    }

    public ImageFileRsp() {
        super();
    }

}
