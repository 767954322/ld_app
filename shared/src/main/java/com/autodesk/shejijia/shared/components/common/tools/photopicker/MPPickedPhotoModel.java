package com.autodesk.shejijia.shared.components.common.tools.photopicker;

import java.io.Serializable;

/**
 * Created by jainar on 06/05/16.
 */
public class MPPickedPhotoModel implements Serializable
{
    public enum PhotoSource
    {
        LOCAL,
        CLOUD
    }

    public String fullImageUri;
    public String thumbnailUri;
    public Integer orientation;
    public PhotoSource photoSource;

    // add hashCode & equals to compare
    @Override
    public int hashCode() {
        if(fullImageUri != null)return fullImageUri.hashCode();
        return super.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof MPPickedPhotoModel){
            return o.hashCode() == this.hashCode();
        }
        return super.equals(o);
    }
}
