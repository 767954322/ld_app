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
}
