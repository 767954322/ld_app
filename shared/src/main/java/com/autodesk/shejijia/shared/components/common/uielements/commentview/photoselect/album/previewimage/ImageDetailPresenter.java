package com.autodesk.shejijia.shared.components.common.uielements.commentview.photoselect.album.previewimage;

import android.support.annotation.NonNull;

import com.autodesk.shejijia.shared.components.common.uielements.commentview.model.CommentRepository;
import com.autodesk.shejijia.shared.components.common.uielements.commentview.photoselect.album.AlbumContract;
import com.autodesk.shejijia.shared.components.common.uielements.commentview.model.entity.ImageInfo;

import java.util.List;

import static com.autodesk.shejijia.shared.components.common.uielements.commentview.utils.CommonUtils.checkNotNull;


/**
 * Created by lijunguan on 2016/4/24.
 */
public class ImageDetailPresenter implements ImageContract.Presenter {
    private CommentRepository mAlbumRepository;

    private ImageContract.View mImageDetailView;

    private AlbumContract.View mAlbumView;

    public ImageDetailPresenter(
            @NonNull CommentRepository albumRepository,
            @NonNull ImageContract.View imageDetailView,
            @NonNull AlbumContract.View albumView) {

        mImageDetailView = checkNotNull(imageDetailView, "ImageContract.View  cannt be null");
        mAlbumRepository = checkNotNull(albumRepository, "AlbumRepository cannt be null");
        mAlbumView = checkNotNull(albumView,"AlbumContract.View cannt be null");
        mImageDetailView.setPresenter(this);
    }

    public void start(List<String> mDatas) {
//        AlbumFolder folder = mAlbumRepository.getFolderByImage(mImageInfo);
//        int index = folder.getImgInfos().indexOf(mImageInfo);
//        mImageDetailView.showImageDetail(index, folder.getImgInfos());
    }


    @Override
    public void selectImage(@NonNull ImageInfo imageInfo, int maxCount, int position) {
        checkNotNull(imageInfo, "ImageInfo cannot be null");
        if (mAlbumRepository.getSelectedResult().size() >= maxCount) {
            mImageDetailView.showOutOfRange(0);
            return;
        }
        imageInfo.setSelected(true);
        mAlbumRepository.addSelect(imageInfo.getPath());
        mImageDetailView.showSelectedCount(mAlbumRepository.getSelectedCount());
        mAlbumView.syncCheckboxStatus(position);
    }

    @Override
    public void unSelectImage(@NonNull ImageInfo imageInfo, int position) {
        checkNotNull(imageInfo, "ImageInfo cannot be null");
        imageInfo.setSelected(false);
        mAlbumRepository.removeSelect(imageInfo.getPath());
        mImageDetailView.showSelectedCount(mAlbumRepository.getSelectedCount());
        mAlbumView.syncCheckboxStatus(position);
    }
}
