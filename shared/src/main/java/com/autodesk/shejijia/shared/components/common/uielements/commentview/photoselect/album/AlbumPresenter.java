package com.autodesk.shejijia.shared.components.common.uielements.commentview.photoselect.album;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;

import com.autodesk.shejijia.shared.components.common.uielements.commentview.model.CommentDataSource;
import com.autodesk.shejijia.shared.components.common.uielements.commentview.model.CommentRepository;
import com.autodesk.shejijia.shared.components.common.uielements.commentview.photoselect.ImageSelector;
import com.autodesk.shejijia.shared.components.common.uielements.commentview.photoselect.cropimage.CropActivity;
import com.autodesk.shejijia.shared.components.common.uielements.commentview.model.entity.AlbumFolder;
import com.autodesk.shejijia.shared.components.common.uielements.commentview.model.entity.ImageInfo;

import java.io.File;
import java.util.List;

import static com.autodesk.shejijia.shared.components.common.uielements.commentview.utils.CommonUtils.checkNotNull;

public class AlbumPresenter implements AlbumContract.Presenter {

    private AlbumContract.View mAlbumView;

    private CommentRepository mAlbumRepository;

    private LoaderManager mLoadManager;


    public AlbumPresenter(
            @NonNull CommentRepository albumRepository,
            @NonNull LoaderManager loaderManager,
            @NonNull AlbumContract.View albumView) {
        mLoadManager = checkNotNull(loaderManager, "loader manager cannot be null");
        mAlbumView = checkNotNull(albumView, "albumView cannot be null");
        mAlbumRepository = checkNotNull(albumRepository, "albumRepository cannot be null");
        //为mAlbumView 设置Presenter
        mAlbumView.setPresenter(this);
    }

    @Override
    public void start(List<ImageInfo> images) {
        loadData(images);
    }

    private void loadData(List<ImageInfo> images) {
        mAlbumRepository.addSelected(images);
        mAlbumRepository.initImgRepository(mLoadManager, new CommentDataSource.InitAlbumCallback() {
            @Override
            public void onInitFinish(List<AlbumFolder> folders) {
                List<ImageInfo> allImages = folders.get(0).getImgInfos();
                mAlbumView.showImages(allImages);
                mAlbumView.initFolderList(folders);
            }

            @Override
            public void onDataNotAvailable() {
                mAlbumView.showEmptyView(null);
            }
        });
    }

    @Override
    public void result(int requestCode, int resultCode, Intent data, File mTmpFile) {

        switch (requestCode) {
            case ImageSelector.REQUEST_OPEN_CAMERA:
                if (resultCode == Activity.RESULT_OK) {
                    Uri photo = data.getData();
                    if (ImageSelector.getInstance().getConfig().getSelectModel() == ImageSelector.AVATOR_MODE) {
                        mAlbumView.showImageCropUi(photo.toString());
                        return;
                    }
                    ImageInfo info = new ImageInfo();
                    info.setPictureUri(photo.toString());
                    info.setPath(mTmpFile.getAbsolutePath());
                    mAlbumRepository.addSelect(info);
                    mAlbumView.selectComplete(mAlbumRepository.getSelectedResult(), true);
                } else if (mTmpFile != null && mTmpFile.exists()) {
                    //出错时，删除零时文件,
                    mTmpFile.delete();
                }
                break;
            case ImageSelector.REQUEST_CROP_IMAGE:
                if (resultCode == Activity.RESULT_OK) {
                    ImageInfo info = data.getParcelableExtra(CropActivity.CROP_RESULT);
                    mAlbumRepository.addSelect(info);
                    mAlbumView.selectComplete(mAlbumRepository.getSelectedResult(), false);
                }
                break;
        }

    }

    @Override
    public void switchFolder(@NonNull AlbumFolder folder) {
        checkNotNull(folder);
        mAlbumView.showImages(folder.getImgInfos());
        mAlbumView.hideFolderList();
    }


    @Override
    public void selectImage(@NonNull ImageInfo imageInfo, int maxCount, int position) {
        checkNotNull(imageInfo, "ImageInfo cannot be null");
        if (mAlbumRepository.getSelectedResult().size() >= maxCount) {
            mAlbumView.showOutOfRange(position);
            return;
        }
        imageInfo.setSelected(true);
        mAlbumRepository.addSelect(imageInfo);
        mAlbumView.showSelectedCount(mAlbumRepository.getSelectedCount());
    }

    @Override
    public void unSelectImage(@NonNull ImageInfo imageInfo, int positon) {
        checkNotNull(imageInfo, "ImageInfo cannot be null");
        imageInfo.setSelected(false);
        mAlbumRepository.removeSelect(imageInfo);
        mAlbumView.showSelectedCount(mAlbumRepository.getSelectedCount());
    }

    @Override
    public void previewImage(int position) {
        mAlbumView.showImageDetailUi(position);
    }

    @Override
    public void cropImage(ImageInfo imageInfo) {
        checkNotNull(imageInfo);
        mAlbumView.showImageCropUi(imageInfo.getPictureUri());
    }

    @Override
    public void returnResult() {
        List<ImageInfo> selectedResult = mAlbumRepository.getSelectedResult();
        mAlbumView.selectComplete(selectedResult, false);
    }

    @Override
    public void openCamera() {
        mAlbumView.showSystemCamera();
    }

    public void clearCache() {
        mAlbumRepository.clearCacheAndSelect();
    }

}
