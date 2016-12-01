package com.autodesk.shejijia.shared.components.common.uielements.commoncomment.comment;

import android.support.annotation.NonNull;

import com.autodesk.shejijia.shared.components.common.uielements.commoncomment.AudioHandler;
import com.autodesk.shejijia.shared.components.common.uielements.commoncomment.contract.CommentContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by t_panya on 16/11/29.
 */

public class CommentPresenter implements CommentContract.Presenter {
    private CommentContract.View mCommentView;
    private ArrayList<String> mPictureData;

    public CommentPresenter(@NonNull CommentContract.View view, List<String> data){
        mCommentView = view;
        mPictureData = (ArrayList<String>) data;
        mCommentView.setPresenter(this);
    }

    @Override
    public void start() {
        loadData();
    }

    @Override
    public void startPlayAudio(String path) {
        if(path == null){
            mCommentView.showToast("错误的文件路径");
            return;
        }
        mCommentView.startPlayAudio(path);
    }

    @Override
    public void stopPlayAudio(String path) {
        if(path == null){
            mCommentView.showToast("错误的文件路径");
            return;
        }
        mCommentView.stopPlayAudio(path);
    }

    @Override
    public void startRecordAudio() {
        mCommentView.startRecordAudio();
    }

    @Override
    public void stopRecordAudio() {
        mCommentView.stopRecordAudio();
    }

    public String getCommentContent(){
         return mCommentView.getCommentContent();
    }

    public List<String> getImageData(){
        return mCommentView.getImagePathList();
    }

    public String getAudioPath(){
        return mCommentView.getAudioRecordPath();
    }

    private void loadData(){
        if(mPictureData != null && mPictureData.size() != 0){
            mCommentView.showImages(mPictureData);
        } else {
            mCommentView.showRecyclerEmptyView();
        }
    }


}
