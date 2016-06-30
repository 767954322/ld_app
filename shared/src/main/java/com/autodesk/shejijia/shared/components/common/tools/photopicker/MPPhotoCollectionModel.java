package com.autodesk.shejijia.shared.components.common.tools.photopicker;

import java.util.ArrayList;

/**
 * Created by jainar on 04/05/16.
 */
public class MPPhotoCollectionModel
{
    public enum AlbumType
    {
        LOCAL_ALBUM,
        CLOUD_ALBUM
    }

    public String albumName;
    public Integer albumId;

    public ArrayList<MPPhotoModel> photos;

    public AlbumType albumType;
}
