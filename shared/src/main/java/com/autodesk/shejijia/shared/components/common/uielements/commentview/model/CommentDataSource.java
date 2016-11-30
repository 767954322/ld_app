package com.autodesk.shejijia.shared.components.common.uielements.commentview.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;

import com.autodesk.shejijia.shared.components.common.uielements.commentview.model.entity.AlbumFolder;
import com.autodesk.shejijia.shared.components.common.uielements.commentview.model.entity.ImageInfo;

import java.util.List;

/**
 * Created by t_panya on 16/12/2.
 */

public interface CommentDataSource {

    void initImgRepository(@NonNull LoaderManager loaderManager, @NonNull InitAlbumCallback mCallback);

    void initRemoteRepository(@NonNull List<ImageInfo> images);

    @Nullable
    List<ImageInfo> getSelectedResult();

    void addSelected(List<ImageInfo> pics);

    void addSelect(@NonNull ImageInfo pic);

    void removeSelect(@NonNull ImageInfo pic);

    void clearCacheAndSelect();

    int getSelectedCount();

    void addSelectedOnline(List<ImageInfo> pics);


    interface InitAlbumCallback {

        void onInitFinish(List<AlbumFolder> folders);

        void onDataNotAvailable();
    }

}
