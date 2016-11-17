package com.autodesk.shejijia.consumer.personalcenter.recommend.entity;

import java.io.Serializable;

/**
 * Created by xueqiudong on 16-10-26.
 */

public class NewInventoryEntity implements Serializable {

    /**
     * asset_id : 1623058
     * scfd : null
     */

    private String asset_id;
    private RecommendSCFDBean scfd;
    /**
     * scfd : null
     * asset_id : 1633425
     * brand_count_limit : null
     * project_number : TJ161117633425
     */

    private int brand_count_limit;
    private String project_number;


    public String getAsset_id() {
        return asset_id;
    }

    public void setAsset_id(String asset_id) {
        this.asset_id = asset_id;
    }

    public RecommendSCFDBean getScfd() {
        return scfd;
    }

    public void setScfd(RecommendSCFDBean scfd) {
        this.scfd = scfd;
    }

    public int getBrand_count_limit() {
        return brand_count_limit;
    }

    public void setBrand_count_limit(int brand_count_limit) {
        this.brand_count_limit = brand_count_limit;
    }

    public String getProject_number() {
        return project_number;
    }

    public void setProject_number(String project_number) {
        this.project_number = project_number;
    }
}
