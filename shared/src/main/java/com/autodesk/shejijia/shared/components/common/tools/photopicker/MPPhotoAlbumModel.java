package com.autodesk.shejijia.shared.components.common.tools.photopicker;

/**
 * Created by jainar on 27/04/16.
 */
public class MPPhotoAlbumModel
{
    public enum eAlbumType
    {
        LOCAL_ALBUM,
        CLOUD_ALBUM
    }

    // Album metadata
    public String albumName;
    public Integer albumId;
    public Integer albumSize;
    public eAlbumType albumType;

    public Integer thumbnailOrientation;
    public String thumbnailUri;

    public boolean isSelected;
}
