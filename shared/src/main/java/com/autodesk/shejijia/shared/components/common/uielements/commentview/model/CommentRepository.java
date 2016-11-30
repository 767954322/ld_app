package com.autodesk.shejijia.shared.components.common.uielements.commentview.model;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.text.TextUtils;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.uielements.commentview.model.entity.AlbumFolder;
import com.autodesk.shejijia.shared.components.common.uielements.commentview.model.entity.ImageInfo;
import com.autodesk.shejijia.shared.components.common.uielements.commentview.utils.CommonUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by t_panya on 16/12/2.
 */

public class CommentRepository implements CommentDataSource{

    private static CommentRepository mInstance;

    /**
     * 唯一loader号
     */
    private static final int IMAGE_LOADER_ID = 1000;

    /**
     * 缓存的相册文件
     */
    Map<String, AlbumFolder> mCachedFolders;
    /**
     * 用户选择的图片路径集合
     */
    private List<ImageInfo> mSelectedResult = new ArrayList<>();
    /**
     * 包涵所有图片的相册名  综合的相册名
     */
    private String mGeneralFolderName;

    private CursorLoader mLoader;

//    List<AlbumFolder> albumFolders;

    public static CommentRepository getInstance(Context context) {
        if (mInstance == null) {
            synchronized (CommentRepository.class) {
                if (mInstance == null) {
                    mInstance = new CommentRepository(context.getApplicationContext());
                    return mInstance;
                }
            }
        }
        return mInstance;
    }

    //first load local
    private CommentRepository(Context context){
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

    ////////////////////////////for photo select//////////////


    @Override
    public void initImgRepository(@NonNull LoaderManager loaderManager,
                                  @NonNull final InitAlbumCallback mCallback) {
        CommonUtils.checkNotNull(loaderManager);
        CommonUtils.checkNotNull(mCallback);

        if(mCachedFolders != null){
            mCallback.onInitFinish(getCachedAlbumFolder());
        }

        loaderManager.initLoader(IMAGE_LOADER_ID, null, new LoaderManager.LoaderCallbacks<Cursor>() {
            @Override
            public Loader<Cursor> onCreateLoader(int id, Bundle args) {
                return mLoader;
            }

            @Override
            public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
                List<AlbumFolder> albumFolders = new ArrayList<>();
                if(data == null){
                    return;
                }
                if(data.getCount() <= 0){
                    mCallback.onDataNotAvailable();
                    return;
                }

                //创建包涵所有图片的相册目录
                AlbumFolder generalAlbumFolder = new AlbumFolder();
                ArrayList<ImageInfo> imageList = new ArrayList<>();
                generalAlbumFolder.setImgInfos(imageList);
                generalAlbumFolder.setFloderName(mGeneralFolderName);
                albumFolders.add(generalAlbumFolder);
                while (data.moveToNext()) {
                    ImageInfo imageInfo = createImageInfo(data);
                    generalAlbumFolder.getImgInfos().add(imageInfo); //每一张图片都加入到allAlbumFolder 目录中

                    File folderFile = new File(imageInfo.getPictureUri()).getParentFile(); //得到当前图片的目录
                    String path = folderFile.getAbsolutePath();
                    AlbumFolder albumFolder = getFolderByPath(path, albumFolders);
                    if (albumFolder == null) {
                        //相册集合中不存在，则创建该相册目录，并添加到集合中
                        albumFolder = new AlbumFolder();
                        albumFolder.setCover(imageInfo);
                        albumFolder.setFloderName(folderFile.getName());
                        albumFolder.setPath(path);
                        ArrayList<ImageInfo> imageInfos = new ArrayList<>();
                        imageInfos.add(imageInfo);
                        albumFolder.setImgInfos(imageInfos);
                        albumFolders.add(albumFolder);
                    } else {
                        albumFolder.getImgInfos().add(imageInfo);
                    }
                }
                generalAlbumFolder.setCover(generalAlbumFolder.getImgInfos().get(0));
                mCallback.onInitFinish(albumFolders);
                processLoadedAlbumFolder(albumFolders);
            }

            @Override
            public void onLoaderReset(Loader<Cursor> loader) {

            }
        });

    }

    /**
     * 远程
     * @param images
     */
    @Override
    public void initRemoteRepository(@NonNull List<ImageInfo> images) {
//        if(images == null || images.size() == 0){
//            return;
//        }
//        AlbumFolder remoteAlbum = new AlbumFolder();
//        remoteAlbum.setFloderName("云相册");
//        remoteAlbum.setCover(images.get(0));
//        remoteAlbum.setImgInfos(images);
//        albumFolders.add(1,remoteAlbum);
    }


    @Nullable
    @Override
    public List<ImageInfo> getSelectedResult() {
        return mSelectedResult;
    }

    @Override
    public void addSelect(@NonNull ImageInfo pic) {
        mSelectedResult.add(CommonUtils.checkNotNull(pic));
    }

    @Override
    public void removeSelect(@NonNull ImageInfo pic) {
        mSelectedResult.remove(CommonUtils.checkNotNull(pic));
    }

    @Override
    public void clearCacheAndSelect() {
        mSelectedResult.clear();
        mCachedFolders = null;
    }

    @Override
    public int getSelectedCount() {
        return mSelectedResult.size();
    }

    @Override
    public void addSelectedOnline(List<ImageInfo> pics) {
        if(pics == null){
            return;
        }
        if(mSelectedResult == null){
            mSelectedResult = new ArrayList<>();
        }
        mSelectedResult.addAll(pics);
    }

    @Override
    public void addSelected(List<ImageInfo> pics) {
        if(pics == null || pics.size() == 0){
            return;
        }
        mSelectedResult.addAll(pics);
    }

    public List<AlbumFolder> getCachedAlbumFolder() {
        return mCachedFolders == null ? null : new ArrayList<>(mCachedFolders.values());
    }

    /**
     * 根据传入的路径得到AlbumFolder对象
     *
     * @param path 文件路径
     * @return 如果mAlbumFloders集合中存在 改path路径的albumFolder则返回AlbumFolder ，否则返回null
     */
    private AlbumFolder getFolderByPath(String path, List<AlbumFolder> albumFolders) {
        AlbumFolder folder = null;
        if (albumFolders != null) {
            for (AlbumFolder cacheFolder : albumFolders) {
                if (TextUtils.equals(cacheFolder.getPath(), path)) {
                    return cacheFolder;
                }
            }
        }
        return folder;
    }

    private ImageInfo createImageInfo(Cursor data) {
        int index = data.getInt(data.getColumnIndexOrThrow(MediaStore.Images.Media._ID));
        Uri uri_temp = MediaStore.Images.Media.EXTERNAL_CONTENT_URI.buildUpon().appendPath(Integer.toString(index)).build();
        String imgPath = data.getString(data.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
        String displayName = data.getString(data.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME));
        long addedTime = data.getLong(data.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_ADDED));
        long imageSize = data.getLong(data.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE));
        return new ImageInfo(uri_temp.toString(), imgPath, displayName, addedTime, imageSize);
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
}
