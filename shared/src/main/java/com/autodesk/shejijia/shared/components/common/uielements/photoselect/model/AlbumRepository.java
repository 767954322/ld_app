package com.autodesk.shejijia.shared.components.common.uielements.photoselect.model;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.util.Log;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.uielements.photoselect.model.entity.AlbumFolder;
import com.autodesk.shejijia.shared.components.common.uielements.photoselect.model.entity.ImageInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.autodesk.shejijia.shared.components.common.uielements.photoselect.utils.CommonUtils.checkNotNull;

public class AlbumRepository implements AlbumDataSource {

    public static final String TAG = AlbumRepository.class.getSimpleName();

    private static final String sFilePrefix = "file://";

    private static volatile AlbumRepository mInstance;
    /**
     * Loader的唯一ID号
     */
    private final static int IMAGE_LOADER_ID = 1000;

    Map<String, AlbumFolder> mCachedFolders;
    /**
     * 用户选择的图片路径集合
     */
    private List<String> mSelectedResult = new ArrayList<>();

    /**
     * 包涵所有图片的相册名  综合的相册名
     */
    private String mGeneralFolderName;

    private CursorLoader mLoader;

    private Context mContext;

    public static AlbumRepository getInstance(Context context) {
        if (mInstance == null) {
            synchronized (AlbumRepository.class) {
                if (mInstance == null) {
                    mInstance = new AlbumRepository(context.getApplicationContext());
                    return mInstance;
                }
            }
        }
        return mInstance;
    }

    public AlbumRepository(Context context) {
        mContext = context;
        String[] IMAGE_PROJECTION = {
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.DATE_ADDED,
                MediaStore.Images.Media.MIME_TYPE,
                MediaStore.Images.Media.SIZE,
                MediaStore.Images.Media._ID};
        mGeneralFolderName = context.getString(R.string.label_general_folder_name);
        mLoader = new CursorLoader(context, MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                IMAGE_PROJECTION, null,
                null, IMAGE_PROJECTION[2] + " DESC");
    }

    //非空注解，参数都不能为空
    @Override
    public void initImgRepository(@NonNull LoaderManager loaderManager,
                                  @NonNull final InitAlbumCallback callback) {
        checkNotNull(loaderManager);
        checkNotNull(callback);
        if (mCachedFolders != null) {  //如果缓存可用，则立即响应
            callback.onInitFinish(getCachedAlbumFolder());
            return;
        }

        loaderManager.initLoader(IMAGE_LOADER_ID, null, new LoaderManager.LoaderCallbacks<Cursor>() {
            @Override
            public Loader<Cursor> onCreateLoader(int id, Bundle args) {
                return mLoader;
            }

            @Override
            public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
                List<AlbumFolder> albumFolders = new ArrayList<>();
                if (data == null) return;
                if (data.getCount() <= 0) {
                    callback.onDataNoAvaliable();
                    return;
                }
                //创建包涵所有图片的相册目录
                AlbumFolder generalAlbumFolder = new AlbumFolder();
                ArrayList<ImageInfo> imgList = new ArrayList<>();
                generalAlbumFolder.setImgInfos(imgList);
                generalAlbumFolder.setFloderName(mGeneralFolderName);
                albumFolders.add(generalAlbumFolder);

                while (data.moveToNext()) {
                    ImageInfo imageInfo = createImageInfo(data);
                    generalAlbumFolder.getImgInfos().add(imageInfo); //每一张图片都加入到allAlbumFolder 目录中

                    File folderFile = new File(imageInfo.getPath()).getParentFile(); //得到当前图片的目录
                    String path = folderFile.getAbsolutePath();
                    AlbumFolder albumFloder = getFloderByPath(path, albumFolders);
                    if (albumFloder == null) {
                        //相册集合中不存在，则创建该相册目录，并添加到集合中
                        albumFloder = new AlbumFolder();
                        albumFloder.setCover(imageInfo);
                        albumFloder.setFloderName(folderFile.getName());
                        albumFloder.setPath(path);
                        ArrayList<ImageInfo> imageInfos = new ArrayList<>();
                        imageInfos.add(imageInfo);
                        albumFloder.setImgInfos(imageInfos);
                        albumFolders.add(albumFloder);
                    } else {
                        albumFloder.getImgInfos().add(imageInfo);
                    }
                }

                generalAlbumFolder.setCover(generalAlbumFolder.getImgInfos().get(0));
                callback.onInitFinish(albumFolders);
                processLoadedAlbumFolder(albumFolders);
            }


            @Override
            public void onLoaderReset(Loader<Cursor> loader) {

            }
        });
    }

    private void processLoadedAlbumFolder(List<AlbumFolder> folders) {
        if (folders == null) {
            mCachedFolders = null;
            return;
        }
        if (mCachedFolders == null) {
            mCachedFolders = new LinkedHashMap<>();
        }
        mCachedFolders.clear();
        for (AlbumFolder task : folders) {
            mCachedFolders.put(task.getPath(), task);
        }
    }

    public List<AlbumFolder> getCachedAlbumFolder() {
        return mCachedFolders == null ? null : new ArrayList<>(mCachedFolders.values());
    }

    /**
     * 根据传入的路径得到AlbumFloder对象
     *
     * @param path 文件路径
     * @return 如果mAlbumFloders集合中存在 改path路径的albumFolder则返回AlbumFloder ，否则返回null
     */
    private AlbumFolder getFloderByPath(String path, List<AlbumFolder> albumFolders) {
        AlbumFolder folder = null;
        if (albumFolders != null) {
            for (AlbumFolder floder : albumFolders) {
                if (TextUtils.equals(floder.getPath(), path)) {
                    return floder;
                }
            }
        }
        return folder;
    }


    private ImageInfo createImageInfo(Cursor data) {
        String imgPath = data.getString(data.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
        String displayName = data.getString(data.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME));
        long addedTime = data.getLong(data.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_ADDED));
        long imageSize = data.getLong(data.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE));
        return new ImageInfo(imgPath, displayName, addedTime, imageSize);
    }

    public static String getThumbnailUri(int originalId, Context context, boolean conformsToFileScheme) {
        assert (context != null);

        String unmodifiedUri = getUriFromThumbnailProvider(originalId, context);

        if (unmodifiedUri == null || unmodifiedUri.isEmpty())
            unmodifiedUri = getUriFromImageProvider(originalId, context);

        if (unmodifiedUri == null || unmodifiedUri.isEmpty())
            return null;

        if (conformsToFileScheme)
            return sFilePrefix + unmodifiedUri;
        else
            return unmodifiedUri;
    }

    private static String getUriFromImageProvider(int originalId, Context context) {
        String[] projection =
                {
                        MediaStore.Images.Media.DATA
                };

        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String selection = MediaStore.Images.Media._ID + " = ?";
        String[] selectionArgs = {String.valueOf(originalId)};
        String orderBy = MediaStore.Images.Media.DEFAULT_SORT_ORDER;

        Cursor cursor = MediaStore.Images.Media.query(context.getContentResolver(),
                uri, projection, selection, selectionArgs, orderBy);

        try {
            if (cursor == null)
                return null;

            if (cursor.getCount() == 0) {
                cursor.close();
                return null;
            }

            if (cursor.moveToFirst()) {
                String dataUri = cursor.getString(cursor.getColumnIndex(
                        MediaStore.Images.Media.DATA));

                cursor.close();

                return dataUri;
            } else {
                // The image does not have an image in the image table; abandon all hope
                cursor.close();
            }
        } catch (Exception e) {
            Log.d("MPPhotoUtility", "Exception while querying image table: " + e.getMessage());

            cursor.close();
        }

        return null;
    }


    private static String getUriFromThumbnailProvider(int originalId, Context context) {
        String[] projection =
                {
                        MediaStore.Images.Thumbnails.DATA,
                        MediaStore.Images.Thumbnails.HEIGHT,
                        MediaStore.Images.Thumbnails.WIDTH
                };

        Cursor cursor = MediaStore.Images.Thumbnails.queryMiniThumbnail(context.getContentResolver(),
                originalId, MediaStore.Images.Thumbnails.MINI_KIND, projection);

        try {
            if (cursor == null)
                return null;

            if (cursor.getCount() == 0) {
                cursor.close();
                return null;
            }

            if (cursor.moveToFirst()) {
                String dataUri = cursor.getString(cursor.getColumnIndex(
                        MediaStore.Images.Thumbnails.DATA));
                Integer height = cursor.getInt(cursor.getColumnIndex(
                        MediaStore.Images.Thumbnails.HEIGHT));
                Integer width = cursor.getInt(cursor.getColumnIndex(
                        MediaStore.Images.Thumbnails.WIDTH));

                cursor.close();

                if (width > 0 && height > 0)
                    return dataUri;
            } else {
                // The image does not have a thumbnail in the thumbnail provider
                cursor.close();
            }
        } catch (Exception e) {
            Log.d("MPPhotoUtility", "Exception while querying thumbnail table: " + e.getMessage());

            cursor.close();
        }

        return null;
    }

    @Override
    public List<String> getSelectedResult() {
        return mSelectedResult;
    }

    @Override
    public void addSelect(@NonNull String path) {
        mSelectedResult.add(checkNotNull(path));
    }

    @Override
    public void removeSelect(@NonNull String path) {
        mSelectedResult.remove(checkNotNull(path));
    }

    @Override
    public int getSelectedCount() {
        return mSelectedResult.size();
    }

    public void clearCacheAndSelect() {
        mSelectedResult.clear();
        mCachedFolders = null;
    }
}
