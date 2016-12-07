package com.autodesk.shejijia.shared.components.common.uielements.commentview.comment;

import android.support.annotation.NonNull;

import com.autodesk.shejijia.shared.components.common.entity.microbean.SHFile;
import com.autodesk.shejijia.shared.components.common.uielements.commentview.contract.CommentContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by t_panya on 16/12/5.
 */

public class CommentPresenter implements CommentContract.CommentPresenter {
    private CommentContract.CommentView mCommentView;
    private ArrayList<SHFile> mFiles;

    public CommentPresenter(@NonNull CommentContract.CommentView view, List<SHFile> files){
        mCommentView = view;
        mFiles = (ArrayList<SHFile>) files;
        mCommentView.setPresenter(this);
    }

    @Override
    public void loadRemoteData(@NonNull List<SHFile> files) {

    }

    @Override
    public void previewImage(int position) {

    }

    @Override
    public void startPlayAudio(String path) {

    }

    @Override
    public void stopPlayAudio(String path) {

    }

    @Override
    public void startRecordAudio() {

    }

    @Override
    public void stopRecordAudio() {

    }

    @Override
    public void cancelRecordAudio() {

    }

    @Override
    public void create() {

    }

    @Override
    public void resume() {

    }
}
