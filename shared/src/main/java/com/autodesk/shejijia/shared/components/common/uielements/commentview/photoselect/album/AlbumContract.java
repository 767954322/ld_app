package com.autodesk.shejijia.shared.components.common.uielements.commentview.photoselect.album;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.autodesk.shejijia.shared.components.common.uielements.commentview.photoselect.album.previewimage.ImageContract;
import com.autodesk.shejijia.shared.components.common.uielements.commentview.photoselect.base.PhotoSelectBaseView;
import com.autodesk.shejijia.shared.components.common.uielements.commentview.model.entity.AlbumFolder;
import com.autodesk.shejijia.shared.components.common.uielements.commentview.model.entity.ImageInfo;

import java.io.File;
import java.util.List;

/**
 * 指定 View 和 Presenter之间的关系，统一声明便于查看和管理接口方法
 */
public interface AlbumContract {

    interface View extends PhotoSelectBaseView<Presenter> {

        void showEmptyView(@Nullable CharSequence message);

        void showImages(@NonNull List<ImageInfo> imageInfos);

        void showSystemCamera();

        void showFolderList();

        void hideFolderList();

        void initFolderList(@NonNull List<AlbumFolder> folders);

        void showImageDetailUi(int currentPosition); // 打开ImagedetailFragment

        void showImageCropUi(@NonNull String imagePath);// 启动裁剪图片的Activity

        void showOutOfRange(int position); //提示用户图片选择数量已经达到上限

        void showSelectedCount(int count);

        /**
         * 图片选择完成，返回选择数据给等待结果的Activity，
         * 根据refreshMedia状态判断是否将相机拍摄或裁剪的图片加入媒体库
         *
         * @param imagePaths   选择的图片路径集合
         * @param refreshMedia 是否刷新系统媒体库 true 将通过相机拍摄的照片加入Media.Store
         */
        void selectComplete(List<ImageInfo> imagePaths, boolean refreshMedia);

        /**
         * 同步  ImageDetailFragment 界面Checkbox选中状态
         * @param position
         */
        void syncCheckboxStatus(int position);

    }

    interface Presenter extends ImageContract.Presenter {
        /**
         * 切换相册目录，刷新Grid显示
         *
         * @param folder 选择的相册目录实体
         */
        void switchFolder(@NonNull AlbumFolder folder);

        void previewImage(int position);

        void cropImage(ImageInfo imageInfo);

        /**
         * 将用户选择的图片结果返回
         */
        void returnResult();

        void openCamera();

        /**
         * 系统相机Activity 返回结果    * {@link Activity#onActivityResult(int, int, Intent)}.
         *
         * @param
         */
        void result(int requestCode, int resultCode, Intent data, File file);

        void clearCache();
    }

}
