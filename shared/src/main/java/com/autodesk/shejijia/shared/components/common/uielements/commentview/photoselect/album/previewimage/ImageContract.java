package com.autodesk.shejijia.shared.components.common.uielements.commentview.photoselect.album.previewimage;

import android.support.annotation.NonNull;

import com.autodesk.shejijia.shared.components.common.uielements.commentview.photoselect.base.PhotoSelectBasePresenter;
import com.autodesk.shejijia.shared.components.common.uielements.commentview.photoselect.base.PhotoSelectBaseView;
import com.autodesk.shejijia.shared.components.common.uielements.commentview.model.entity.ImageInfo;

public interface ImageContract {

    interface View extends PhotoSelectBaseView<Presenter> {

        void updateIndicator();

        void showOutOfRange(int position);

        void showSelectedCount(int count);
    }

    interface Presenter extends PhotoSelectBasePresenter {

        void selectImage(@NonNull ImageInfo imageInfo, int maxCount, int position);

        void unSelectImage(@NonNull ImageInfo imageInfo, int position);
    }

}
