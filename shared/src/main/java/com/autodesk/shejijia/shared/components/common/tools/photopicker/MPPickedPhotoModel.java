package com.autodesk.shejijia.shared.components.common.tools.photopicker;

/**
 * Created by jainar on 06/05/16.
 */
public class MPPickedPhotoModel
{
    public enum PhotoSource
    {
        LOCAL,
        CLOUD
    }

    public String fullImageUri;
    public Integer orientation;
    public PhotoSource photoSource;
}
