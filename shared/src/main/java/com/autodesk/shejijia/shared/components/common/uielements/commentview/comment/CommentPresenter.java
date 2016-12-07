package com.autodesk.shejijia.shared.components.common.uielements.commentview.comment;

import android.support.annotation.NonNull;

import com.autodesk.shejijia.shared.components.common.entity.microbean.SHFile;
import com.autodesk.shejijia.shared.components.common.uielements.commentview.model.entity.ImageInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by t_panya on 16/12/5.
 */

public class CommentPresenter implements CommentContract.CommentPresenter {
    private CommentContract.CommentView mCommentView;
    private ArrayList<SHFile> mFiles;
    private ArrayList<String> mPictures;
    private ArrayList<ImageInfo> mImages;

    public CommentPresenter(@NonNull CommentContract.CommentView view, List<String> pictures){
        mCommentView = view;
//        mFiles = (ArrayList<SHFile>) files;
        mPictures = (ArrayList<String>) pictures;
        mCommentView.setPresenter(this);
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

    public String getCommentContent(){
        return mCommentView.getCommentContent();
    }

    public String getAudioPath(){
        return mCommentView.getCommentContent();
    }

    public List<String> getImageData(){
        return mCommentView.getImagePathList();
    }

    /**
     * 加载数据
     */
    private void loadData(){
        if(mPictures != null && mPictures.size() != 0){
//            for(int i = 0; i < mFiles.size(); i++){
//                mPictures.add(i,mFiles.get(i).getPictureUrl());
//                ImageInfo info = new ImageInfo(mFiles.get(i).getPictureUrl(),mFiles.get(i).getThumbnailUrl());
//                mImages.add(i,info);
//            }
            mCommentView.showImages(mPictures);
        } else {
            mCommentView.showRecyclerEmptyView();
        }
    }
}
