package com.autodesk.shejijia.shared.components.nodeprocess.contract;

import android.content.DialogInterface;
import android.support.annotation.NonNull;

import com.autodesk.shejijia.shared.components.common.uielements.commentview.model.entity.ImageInfo;
import com.autodesk.shejijia.shared.framework.BasePresenter;
import com.autodesk.shejijia.shared.framework.BaseView;

import java.util.List;

/**
 * Class description
 *
 * @author wenhulin
 * @since 16/12/14
 */

public interface UploadPhotoContact {

    interface View extends BaseView {
        void showUploading();

        void hideUploading();

        void showError(@NonNull String error, @NonNull DialogInterface.OnClickListener positiveClickListener, @NonNull DialogInterface.OnClickListener negativeClickListener);

        void onUploadSuccess();
    }


    interface Presenter extends BasePresenter {

        void uploadPhotos(@NonNull List<ImageInfo> imageInfos);
    }
}
