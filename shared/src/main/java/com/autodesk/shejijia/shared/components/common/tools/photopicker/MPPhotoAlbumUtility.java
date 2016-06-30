package com.autodesk.shejijia.shared.components.common.tools.photopicker;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.im.datamodel.MPChatHomeStylerCloudfile;
import com.autodesk.shejijia.shared.components.im.datamodel.MPChatHomeStylerCloudfiles;
import com.autodesk.shejijia.shared.components.common.utility.MPNetworkUtils;

import org.json.JSONObject;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author jainar .
 * @version v1.0 .
 * @date 2016-6-13 .
 * @file MPPhotoAlbumUtility.java .
 * @brief .
 */
public class MPPhotoAlbumUtility
{
    public static final Integer kCloudAlbumId = -2; // Special ID for the cloud files album
    public static final String kCloudThumbnailQualifier = "Medium.jpg";

    public static MPPhotoCollectionModel getLocalPhotoCollection(Context context, Integer albumId)
    {
        assert (context != null);

        MPPhotoCollectionModel album = new MPPhotoCollectionModel();
        album.albumType = MPPhotoCollectionModel.AlbumType.LOCAL_ALBUM;
        album.photos = new ArrayList<>();

        String[] projection =
                {
                        MediaStore.Images.Media._ID,
                        MediaStore.Images.Media.DATA,
                        MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
                        MediaStore.Images.Media.ORIENTATION
                };

        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String orderBy = MediaStore.Images.Media.DATE_MODIFIED + " DESC";
        String selection = MediaStore.Images.Media.BUCKET_ID + " = ?";
        String[] selectionArgs = {String.valueOf(albumId)};

        Cursor cursor = context.getContentResolver().query(uri, projection, selection,
                selectionArgs, orderBy);

        try
        {
            if (cursor == null)
                return null;

            if (cursor.getCount() == 0)
            {
                cursor.close();
                return null;
            }

            String albumName = null;

            while (cursor.moveToNext())
            {
                Integer imageId = cursor.getInt(cursor.getColumnIndex(
                        MediaStore.Images.Media._ID));
                String data = cursor.getString(cursor.getColumnIndex(
                        MediaStore.Images.Media.DATA));
                albumName = cursor.getString(cursor.getColumnIndex(
                        MediaStore.Images.Media.BUCKET_DISPLAY_NAME));
                Integer orientation = cursor.getInt(cursor.getColumnIndex(
                        MediaStore.Images.Media.ORIENTATION));

                MPPhotoModel photo = new MPPhotoModel();
                photo.fullImageUri = data;
                photo.imageId = imageId;
                photo.isSelected = false;
                photo.thumbnailUri = MPPhotoUtility.getThumbnailUri(imageId, context, true);
                photo.orientation = orientation;

                album.photos.add(photo);
            }

            album.albumName = albumName;
            album.albumId = albumId;
        }
        catch (Exception e)
        {
            Log.d("MPPhotoAlbumUtility", "Exception while querying image table: " + e.getMessage());
            e.printStackTrace();

            cursor.close();
            return null;
        }

        cursor.close();

        return album;
    }


    public static MPPhotoCollectionModel getPartialLocalPhotoCollection(Context context,
                                                                        Integer albumId,
                                                                        int offset, int limit)
    {
        assert (context != null);
        assert (offset >= 0 && limit >= 0);

        MPPhotoCollectionModel album = new MPPhotoCollectionModel();
        album.albumType = MPPhotoCollectionModel.AlbumType.LOCAL_ALBUM;
        album.photos = new ArrayList<>();

        String[] projection =
                {
                        MediaStore.Images.Media._ID,
                        MediaStore.Images.Media.DATA,
                        MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
                        MediaStore.Images.Media.ORIENTATION
                };

        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String orderBy = MediaStore.Images.Media.DATE_MODIFIED + " DESC";
        String selection = MediaStore.Images.Media.BUCKET_ID + " = ?";
        String[] selectionArgs = {String.valueOf(albumId)};

        Cursor cursor = context.getContentResolver().query(uri, projection, selection,
                selectionArgs, orderBy);

        try
        {
            if (cursor == null)
                return null;

            if (cursor.getCount() == 0)
            {
                cursor.close();
                return null;
            }

            String albumName = null;

            int albumSize = cursor.getCount();

            if (offset > (albumSize - 1))
                return null;

            int adjustedLimit = limit;
            if (offset + limit > albumSize)
                adjustedLimit = albumSize - offset;

            for (int i = offset; i < offset + adjustedLimit; ++i)
            {
                cursor.moveToPosition(i);

                Integer imageId = cursor.getInt(cursor.getColumnIndex(
                        MediaStore.Images.Media._ID));
                String data = cursor.getString(cursor.getColumnIndex(
                        MediaStore.Images.Media.DATA));
                albumName = cursor.getString(cursor.getColumnIndex(
                        MediaStore.Images.Media.BUCKET_DISPLAY_NAME));
                Integer orientation = cursor.getInt(cursor.getColumnIndex(
                        MediaStore.Images.Media.ORIENTATION));

                MPPhotoModel photo = new MPPhotoModel();
                photo.fullImageUri = data;
                photo.imageId = imageId;
                photo.isSelected = false;
                photo.thumbnailUri = MPPhotoUtility.getThumbnailUri(imageId, context, true);
                photo.orientation = orientation;

                album.photos.add(photo);
            }

            album.albumName = albumName;
            album.albumId = albumId;
        }
        catch (Exception e)
        {
            Log.d("MPPhotoAlbumUtility", "Exception while querying image table: " + e.getMessage());
            e.printStackTrace();

            cursor.close();
            return null;
        }

        cursor.close();

        return album;
    }

    // Wrap the sync method in an AsyncTask, don't bother with this one.
    // We're not sure whether it's running asynchronously on a background thread or on the main
    // thread. Yes, we read the implementation.
    public static void getLocalPhotoCollectionAsync(final Context context, Integer albumId,
                                                    final CollectionFetchListener listener)
    {
        assert (context != null);

        MPPhotoCollectionModel collection = new MPPhotoCollectionModel();
        collection.albumType = MPPhotoCollectionModel.AlbumType.LOCAL_ALBUM;
        collection.photos = new ArrayList<>();

        String[] projection =
                {
                        MediaStore.Images.Media._ID,
                        MediaStore.Images.Media.DATA,
                        MediaStore.Images.Media.BUCKET_ID,
                        MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
                        MediaStore.Images.Media.DATE_MODIFIED,
                        MediaStore.Images.Media.ORIENTATION
                };

        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String orderBy = MediaStore.Images.Media.DATE_MODIFIED + " DESC";
        String selection = MediaStore.Images.Media.BUCKET_ID + " = ?";
        String[] selectionArgs = {String.valueOf(albumId)};

        final WeakReference<Context> weakContext = new WeakReference<>(context);
        final WeakReference<Integer> weakAlbumId = new WeakReference<>(albumId);

        AsyncPhotoQueryHandler queryHandler = new AsyncPhotoQueryHandler(context.getContentResolver(),
                new AsyncPhotoQueryListener()
                {
                    @Override
                    public void onQueryComplete(int token, Object cookie, Cursor cursor)
                    {
                        try
                        {
                            MPPhotoCollectionModel innerCollection = (MPPhotoCollectionModel) cookie;
                            if (cursor == null)
                            {
                                if (listener != null)
                                    listener.onError();

                                return;
                            }

                            if (cursor.getCount() == 0)
                            {
                                cursor.close();

                                if (listener != null)
                                    listener.onSuccess(innerCollection);

                                return;
                            }

                            String albumName = null;

                            while (cursor.moveToNext())
                            {
                                String data = cursor.getString(cursor.getColumnIndex(
                                        MediaStore.Images.Media.DATA));
                                albumName = cursor.getString(cursor.getColumnIndex(
                                        MediaStore.Images.Media.BUCKET_DISPLAY_NAME));
                                Integer imageId = cursor.getInt(cursor.getColumnIndex(
                                        MediaStore.Images.Media._ID));
                                Integer orientation = cursor.getInt(cursor.getColumnIndex(
                                        MediaStore.Images.Media.ORIENTATION));

                                MPPhotoModel photo = new MPPhotoModel();
                                photo.fullImageUri = data;
                                photo.imageId = imageId;
                                photo.isSelected = false;
                                photo.thumbnailUri = MPPhotoUtility.getThumbnailUri(imageId,
                                        weakContext.get(), true);
                                photo.orientation = orientation;

                                innerCollection.photos.add(photo);
                            }

                            innerCollection.albumName = albumName;
                            innerCollection.albumId = weakAlbumId.get();

                            cursor.close();

                            if (listener != null)
                                listener.onSuccess(innerCollection);
                        }
                        catch (Exception e)
                        {
                            Log.d("MPPhotoAlbumUtility", "Exception while getting photo collection: " +
                                    e.getMessage());
                            e.printStackTrace();

                            if (cursor != null)
                                cursor.close();

                            if (listener != null)
                                listener.onError();
                        }
                    }
                });

        // Inaccurate check, the class does not give out its actual looper
//        boolean isThreadUiThread = (queryHandler.getLooper() == Looper.getMainLooper());
//        Log.d("MPPhotoAlbumUtility", "Current thread is UI thread (pre-query): " + isThreadUiThread);

        queryHandler.startQuery(albumId, collection, uri, projection, selection, selectionArgs,
                orderBy);
    }

    public static ArrayList<MPPhotoAlbumModel> getAllLocalAlbums(Context context)
    {
        assert (context != null);

        HashMap<Integer, MPPhotoAlbumModel> albumMap = new HashMap<>();
        ArrayList<MPPhotoAlbumModel> albums = new ArrayList<>();

        String[] projection =
                {
                        MediaStore.Images.Media._ID,
                        MediaStore.Images.Media.BUCKET_ID,
                        MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
                        MediaStore.Images.Media.ORIENTATION,
                };

        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String orderBy = MediaStore.Images.Media.DATE_MODIFIED + " DESC";

        Cursor cursor = context.getContentResolver().query(uri, projection, null,
                null, orderBy);

        try
        {
            if (cursor == null)
                return null;

            if (cursor.getCount() == 0)
            {
                cursor.close();
                return null;
            }

            while (cursor.moveToNext())
            {
                Integer imageId = cursor.getInt(cursor.getColumnIndex(
                        MediaStore.Images.Media._ID));
                Integer albumId = cursor.getInt(cursor.getColumnIndex(
                        MediaStore.Images.Media.BUCKET_ID));
                String albumName = cursor.getString(cursor.getColumnIndex(
                        MediaStore.Images.Media.BUCKET_DISPLAY_NAME));
                Integer orientation = cursor.getInt(cursor.getColumnIndex(
                        MediaStore.Images.Media.ORIENTATION));

                MPPhotoAlbumModel album;

                if (albumMap.containsKey(albumId))
                {
                    album = albumMap.get(albumId);
                    album.albumSize++;
                }
                else
                {
                    album = new MPPhotoAlbumModel();
                    album.albumType = MPPhotoAlbumModel.eAlbumType.LOCAL_ALBUM;
                    album.albumName = albumName;
                    album.albumId = albumId;
                    album.isSelected = false;

                    album.thumbnailUri = MPPhotoUtility.getThumbnailUri(imageId, context, true);
                    album.thumbnailOrientation = orientation;
                    album.albumSize = 1;

                    albumMap.put(albumId, album);
                    albums.add(album);
                }
            }
        }
        catch (Exception e)
        {
            Log.d("MPPhotoAlbumUtility", "Exception while querying image table: " + e.getMessage());
            e.printStackTrace();

            cursor.close();
            return null;
        }

        cursor.close();

        return albums;
    }

    public static MPPhotoAlbumModel getDefaultLocalAlbum(Context context)
    {
        assert (context != null);

        // Get the camera album by default
        MPPhotoAlbumModel defaultAlbum = getCameraAlbum(context);

        if (defaultAlbum != null)
            return defaultAlbum;

        // Fall back to another method
        ArrayList<MPPhotoAlbumModel> albums = getAllLocalAlbums(context);

        if (albums == null)
            return null;

        defaultAlbum = getAlbumWithMaxPhotos(albums);

        if (defaultAlbum != null)
            return defaultAlbum;

        return null;
    }

    /**
     * This method is optimized to get basic album info quickly. The album size is incorrect
     * and the thumbnail is unoptimized.
     */
    public static MPPhotoAlbumModel getCameraAlbum(Context context)
    {
        assert (context != null);

        MPPhotoAlbumModel album = null;
        File cameraDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);

        // This will construct an album using the first photo existing inside the parent DCIM
        // directory. So, the album might be "Camera" or "100ANDRO" or anything else.
        album = getAlbumUsingPath(context, cameraDirectory);

        return album;
    }


    public static MPPhotoAlbumModel getAlbumWithMaxPhotos(ArrayList<MPPhotoAlbumModel> albums)
    {
        assert (albums != null);
        assert (albums.size() != 0);

        Integer maxSize = 0;
        MPPhotoAlbumModel albumWithMaxPhotos = albums.get(0);
        for (MPPhotoAlbumModel album : albums)
        {
            if (album.albumSize > maxSize)
            {
                maxSize = album.albumSize;
                albumWithMaxPhotos = album;
            }
        }

        return albumWithMaxPhotos;
    }


    public static void getCloudAlbumAsync(Context context, final String X_Token,
                                          final String assetId, final String memberId,
                                          final AlbumFetchListener listener)
    {
        assert (listener != null);
        assert (context != null);
        assert (assetId != null && !assetId.isEmpty());
        assert (memberId != null && !memberId.isEmpty());
        assert (X_Token != null && !X_Token.isEmpty());

        final MPPhotoAlbumModel album = new MPPhotoAlbumModel();
        album.albumType = MPPhotoAlbumModel.eAlbumType.CLOUD_ALBUM;
        album.albumName = context.getString(R.string.photopicker_cloud_album);
        album.albumId = kCloudAlbumId;
        album.isSelected = false;
        album.thumbnailOrientation = 0;

        //TODO REFACTORING
//        MPServerHttpManager.getInstance().getCloudFiles(X_Token, assetId, memberId, new OkJsonRequest.OKResponseCallback()
//        {
//            @Override
//            public void onErrorResponse(VolleyError volleyError)
//            {
//                MPNetworkUtils.logError("MPPhotoAlbumUtility", volleyError);
//
//                listener.onError();
//            }
//
//            @Override
//            public void onResponse(JSONObject jsonObject)
//            {
//                try
//                {
//                    String userInfo = new String(jsonObject.toString().getBytes("ISO-8859-1"),
//                            "UTF-8");
//                    MPChatHomeStylerCloudfiles cloudfiles = MPChatHomeStylerCloudfiles.
//                            fromJSONString(userInfo);
//
//                    album.albumSize = cloudfiles.files.size();
//
//                    if (album.albumSize > 0)
//                        album.thumbnailUri = cloudfiles.files.get(0).thumbnail + kCloudThumbnailQualifier;
//
//                    listener.onSuccess(album);
//                }
//                catch (Exception e)
//                {
//                    Log.d("MPPhotoAlbumUtility", "Exception while fetching cloud albums: " + e.getMessage());
//                    e.printStackTrace();
//
//                    listener.onError();
//                }
//            }
//        });
    }

    public static void getCloudCollection(Context context, final String X_Token,
                                          final String assetId, final String memberId,
                                          final CollectionFetchListener listener)
    {
        assert (listener != null);
        assert (context != null);
        assert (assetId != null && !assetId.isEmpty());
        assert (memberId != null && !memberId.isEmpty());
        assert (X_Token != null && !X_Token.isEmpty());

        final MPPhotoCollectionModel collection = new MPPhotoCollectionModel();
        collection.albumType = MPPhotoCollectionModel.AlbumType.CLOUD_ALBUM;
        collection.albumName = context.getString(R.string.photopicker_cloud_album);
        collection.albumId = kCloudAlbumId;
        collection.photos = new ArrayList<>();

        // TODO: REFACTORING
//        MPServerHttpManager.getInstance().getCloudFiles(X_Token, assetId, memberId, new OkJsonRequest.OKResponseCallback()
//        {
//            @Override
//            public void onErrorResponse(VolleyError volleyError)
//            {
//                MPNetworkUtils.logError("MPPhotoAlbumUtility", volleyError);
//
//                listener.onError();
//            }
//
//            @Override
//            public void onResponse(JSONObject jsonObject)
//            {
//                try
//                {
//                    String userInfo = new String(jsonObject.toString().getBytes("ISO-8859-1"),
//                            "UTF-8");
//                    MPChatHomeStylerCloudfiles cloudfiles = MPChatHomeStylerCloudfiles.
//                            fromJSONString(userInfo);
//
//                    for (MPChatHomeStylerCloudfile file : cloudfiles.files)
//                    {
//                        MPPhotoModel photo = new MPPhotoModel();
//
//                        photo.imageId = file.uid;
//                        photo.orientation = 0;
//                        photo.fullImageUri = file.url;
//                        photo.thumbnailUri = file.thumbnail + kCloudThumbnailQualifier;
//                        photo.isSelected = false;
//
//                        collection.photos.add(photo);
//                    }
//
//                    listener.onSuccess(collection);
//                }
//                catch (Exception e)
//                {
//                    Log.d("MPPhotoAlbumUtility", "Exception while fetching cloud albums: " + e.getMessage());
//                    e.printStackTrace();
//
//                    listener.onError();
//                }
//            }
//        });
    }

    /**
     * This method is optimized to get basic album info quickly. The album size is incorrect
     * and the thumbnail is unoptimized.
     */
    private static MPPhotoAlbumModel getAlbumUsingName(Context context, String targetAlbum)
    {
        assert (context != null);
        assert (targetAlbum != null && !targetAlbum.isEmpty());

        MPPhotoAlbumModel album = null;

        String[] projection =
                {
                        MediaStore.Images.Media.DATA,
                        MediaStore.Images.Media.BUCKET_ID,
                        MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
                        MediaStore.Images.Media.ORIENTATION
                };

        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String orderBy = MediaStore.Images.Media.DATE_MODIFIED + " DESC";
        String selection = MediaStore.Images.Media.BUCKET_DISPLAY_NAME + " = ?";
        String[] selectionArgs = {targetAlbum};

        Cursor cursor = context.getContentResolver().query(uri, projection, selection,
                selectionArgs, orderBy);

        try
        {
            if (cursor == null)
                return null;

            if (cursor.getCount() == 0)
            {
                cursor.close();
                return null;
            }

            if (cursor.moveToNext())
            {
                String dataUri = cursor.getString(cursor.getColumnIndex(
                        MediaStore.Images.Media.DATA));
                Integer albumId = cursor.getInt(cursor.getColumnIndex(
                        MediaStore.Images.Media.BUCKET_ID));
                String albumName = cursor.getString(cursor.getColumnIndex(
                        MediaStore.Images.Media.BUCKET_DISPLAY_NAME));
                Integer orientation = cursor.getInt(cursor.getColumnIndex(
                        MediaStore.Images.Media.ORIENTATION));

                album = new MPPhotoAlbumModel();
                album.albumType = MPPhotoAlbumModel.eAlbumType.LOCAL_ALBUM;
                album.albumName = albumName;
                album.albumId = albumId;
                album.isSelected = false;

                album.thumbnailUri = kFilePrefix + dataUri;
                album.thumbnailOrientation = orientation;
                album.albumSize = 1;
            }
        }
        catch (Exception e)
        {
            Log.d("MPPhotoAlbumUtility", "Exception while querying image table: " + e.getMessage());
            e.printStackTrace();
        }

        cursor.close();

        return album;
    }


    /**
     * This method is optimized to get basic album info quickly. The album size is incorrect
     * and the thumbnail is unoptimized.
     */
    private static MPPhotoAlbumModel getAlbumUsingPath(Context context, File albumDirectory)
    {
        assert (context != null);
        assert (albumDirectory != null && albumDirectory.exists());

        MPPhotoAlbumModel album = null;

        String absoluteDirectoryPath = albumDirectory.getAbsolutePath();

        String[] projection =
                {
                        MediaStore.Images.Media.DATA,
                        MediaStore.Images.Media.BUCKET_ID,
                        MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
                        MediaStore.Images.Media.ORIENTATION,
                };
        String orderBy = MediaStore.Images.Media.DATE_MODIFIED + " DESC";
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        Cursor cursor = context.getContentResolver().query(uri, projection, null,
                null, orderBy);

        try
        {
            if (cursor == null)
                return null;

            if (cursor.getCount() == 0)
            {
                cursor.close();
                return null;
            }

            while (cursor.moveToNext())
            {
                String dataUri = cursor.getString(cursor.getColumnIndex(
                        MediaStore.Images.Media.DATA));
                Integer albumId = cursor.getInt(cursor.getColumnIndex(
                        MediaStore.Images.Media.BUCKET_ID));
                String albumName = cursor.getString(cursor.getColumnIndex(
                        MediaStore.Images.Media.BUCKET_DISPLAY_NAME));
                Integer orientation = cursor.getInt(cursor.getColumnIndex(
                        MediaStore.Images.Media.ORIENTATION));

                if (dataUri.contains(absoluteDirectoryPath))
                {
                    album = new MPPhotoAlbumModel();
                    album.albumType = MPPhotoAlbumModel.eAlbumType.LOCAL_ALBUM;
                    album.albumName = albumName;
                    album.albumId = albumId;
                    album.isSelected = false;

                    album.thumbnailUri = kFilePrefix + dataUri;
                    album.thumbnailOrientation = orientation;
                    album.albumSize = 1;

                    break;
                }
            }
        }
        catch (Exception e)
        {
            Log.d("MPPhotoAlbumUtility", "Exception while querying image table: " + e.getMessage());
            e.printStackTrace();
        }

        cursor.close();

        return album;
    }

    public interface AlbumFetchListener
    {
        void onError();

        void onSuccess(MPPhotoAlbumModel album);
    }

    public interface CollectionFetchListener
    {
        void onError();

        void onSuccess(MPPhotoCollectionModel collection);
    }

    private interface AsyncPhotoQueryListener
    {
        void onQueryComplete(int token, Object cookie, Cursor cursor);
    }

    private static class AsyncPhotoQueryHandler extends AsyncQueryHandler
    {
        AsyncPhotoQueryListener mListener;

        AsyncPhotoQueryHandler(ContentResolver cr, AsyncPhotoQueryListener l)
        {
            super(cr);

            mListener = l;
        }

        @Override
        protected void onQueryComplete(int token, Object cookie, Cursor cursor)
        {
            super.onQueryComplete(token, cookie, cursor);

            if (mListener != null)
                mListener.onQueryComplete(token, cookie, cursor);
        }
    }

    private static final String kFilePrefix = "file://";
}
