package com.autodesk.shejijia.shared.components.common.uielements.photoselect.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;

import com.autodesk.shejijia.shared.components.common.uielements.photoselect.model.entity.AlbumFolder;

import java.util.List;


/**
 * Model 层接口
 */
public interface AlbumDataSource {


    void initImgRepository(@NonNull LoaderManager loaderManager, @NonNull InitAlbumCallback mCallback);

    @Nullable
    List<String> getSelectedResult();

    void addSelect(@NonNull String path);

    void removeSelect(@NonNull String path);

    void clearCacheAndSelect();

    int getSelectedCount();


    interface InitAlbumCallback {

        void onInitFinish(List<AlbumFolder> folders);

        void onDataNoAvaliable();
    }

}

