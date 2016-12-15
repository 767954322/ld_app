package com.autodesk.shejijia.shared.components.common.uielements.commentview.comment;

import android.support.annotation.NonNull;

import com.autodesk.shejijia.shared.components.common.entity.microbean.ConstructionFile;
import com.autodesk.shejijia.shared.components.common.uielements.commentview.model.entity.ImageInfo;
import com.autodesk.shejijia.shared.components.common.utility.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by t_panya on 16/12/5.
 */

public class CommentPresenter implements CommentContract.CommentPresenter {
    private CommentContract.CommentView mCommentView;
    private ArrayList<ConstructionFile> mFiles;
    //    private ArrayList<String> mPictures;
    private ArrayList<ImageInfo> mImages;
    private boolean isJustShowLocal;

    public CommentPresenter(@NonNull CommentContract.CommentView view, List<ConstructionFile> files) {
        mCommentView = view;
        mFiles = (ArrayList<ConstructionFile>) files;
        mImages = new ArrayList<>();
//        mPictures = (ArrayList<String>) pictures;
        mCommentView.setPresenter(this);
    }

    public CommentPresenter(@NonNull CommentContract.CommentView view, List<ImageInfo> images, boolean justShowLocal) {
        mCommentView = view;
        mImages = (ArrayList<ImageInfo>) images;
//        mPictures = (ArrayList<String>) pictures;
        mCommentView.setPresenter(this);
        isJustShowLocal = justShowLocal;
    }

    @Override
    public void previewImage(int position) {
        mCommentView.showImageDetailUi(position);
    }

    @Override
    public void startPlayAudio(String path) {
        mCommentView.startPlayAudio(path);
    }

    @Override
    public void stopPlayAudio() {
        mCommentView.stopPlayAudio();
    }

    @Override
    public void startRecordAudio() {
        mCommentView.startRecordAudio();
    }

    @Override
    public void stopRecordAudio() {
        mCommentView.stopRecordAudio();
    }

    @Override
    public void cancelRecordAudio() {
        mCommentView.cancelAndDeleteAudio();
    }

    @Override
    public void deleteVoice(String path) {
        mCommentView.deleteVoice(path);
    }

    @Override
    public void start() {
        loadData();
    }

    public String getCommentContent() {
        return mCommentView.getCommentContent();
    }

    public String getAudioPath() {
        return mCommentView.getAudioRecordPath();
    }

    public List<ImageInfo> getImageData() {
        return mCommentView.getImages();
    }

    /**
     * 加载数据
     */
    private void loadData() {
        if (isJustShowLocal) {
            if (mImages != null && mImages.size() != 0) {
                mCommentView.showImages(mImages);
            } else {
                mCommentView.showRecyclerEmptyView();
            }
        } else {
            if (mImages != null && mImages.size() != 0) {
                for (int i = 0; i < mFiles.size(); i++) {
//                mPictures.add(i,mFiles.get(i).getPublicUrl());
                    ImageInfo info = new ImageInfo(mFiles.get(i).getPublicUrl(), mFiles.get(i).getThumbnailUrl());
                    info.setPhotoSource(ImageInfo.PhotoSource.CLOUD);
                    mImages.add(i, info);
                }
                mCommentView.showImages(mImages);
            } else {
                mCommentView.showRecyclerEmptyView();
            }
        }
    }
}
