package com.autodesk.shejijia.shared.components.common.tools.photopicker;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

/**
 * Created by jainar on 29/04/16.
 */
public class MPPhotoUtility
{
    public static String getThumbnailUri(int originalId, Context context, boolean conformsToFileScheme)
    {
        assert (context != null);

        String unmodifiedUri = MPPhotoUtility.getUriFromThumbnailProvider(originalId, context);

        if (unmodifiedUri == null || unmodifiedUri.isEmpty())
            unmodifiedUri = MPPhotoUtility.getUriFromImageProvider(originalId, context);

        if (unmodifiedUri == null || unmodifiedUri.isEmpty())
            return null;

        if (conformsToFileScheme)
            return kFilePrefix + unmodifiedUri;
        else
            return unmodifiedUri;
    }

    private static String getUriFromThumbnailProvider(int originalId, Context context)
    {
        String[] projection =
                {
                        MediaStore.Images.Thumbnails.DATA,
                        MediaStore.Images.Thumbnails.HEIGHT,
                        MediaStore.Images.Thumbnails.WIDTH
                };

        Cursor cursor = MediaStore.Images.Thumbnails.queryMiniThumbnail(context.getContentResolver(),
                originalId, MediaStore.Images.Thumbnails.MINI_KIND, projection);

        try
        {
            if (cursor == null)
                return null;

            if (cursor.getCount() == 0)
            {
                cursor.close();
                return null;
            }

            if (cursor.moveToFirst())
            {
                String dataUri = cursor.getString(cursor.getColumnIndex(
                        MediaStore.Images.Thumbnails.DATA));
                Integer height = cursor.getInt(cursor.getColumnIndex(
                        MediaStore.Images.Thumbnails.HEIGHT));
                Integer width = cursor.getInt(cursor.getColumnIndex(
                        MediaStore.Images.Thumbnails.WIDTH));

                cursor.close();

                if (width > 0 && height > 0)
                    return dataUri;
            }
            else
            {
                // The image does not have a thumbnail in the thumbnail provider
                cursor.close();
            }
        }
        catch (Exception e)
        {
            Log.d("MPPhotoUtility", "Exception while querying thumbnail table: " + e.getMessage());

            cursor.close();
        }

        return null;
    }

    private static String getUriFromImageProvider(int originalId, Context context)
    {
        String[] projection =
                {
                        MediaStore.Images.Media.DATA,
                        MediaStore.Images.Media.HEIGHT,
                        MediaStore.Images.Media.WIDTH
                };

        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String selection = MediaStore.Images.Media._ID + " = ?";
        String[] selectionArgs = { String.valueOf(originalId) };
        String orderBy = MediaStore.Images.Media.DEFAULT_SORT_ORDER;

        Cursor cursor = MediaStore.Images.Media.query(context.getContentResolver(),
                uri, projection, selection, selectionArgs, orderBy);

        try
        {
            if (cursor == null)
                return null;

            if (cursor.getCount() == 0)
            {
                cursor.close();
                return null;
            }

            if (cursor.moveToFirst())
            {
                String dataUri = cursor.getString(cursor.getColumnIndex(
                        MediaStore.Images.Media.DATA));
                Integer height = cursor.getInt(cursor.getColumnIndex(
                        MediaStore.Images.Media.HEIGHT));
                Integer width = cursor.getInt(cursor.getColumnIndex(
                        MediaStore.Images.Media.WIDTH));

                cursor.close();

                if (width > 0 && height > 0)
                    return dataUri;
            }
            else
            {
                // The image does not have an image in the image table; abandon all hope
                cursor.close();
            }
        }
        catch (Exception e)
        {
            Log.d("MPPhotoUtility", "Exception while querying image table: " + e.getMessage());

            cursor.close();
        }

        return null;
    }

    private static final String kFilePrefix = "file://";
}
