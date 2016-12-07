package com.autodesk.shejijia.shared.components.common.uielements.commentview.comment;

import com.autodesk.shejijia.shared.components.common.uielements.commentview.base.CommentBasePresenter;
import com.autodesk.shejijia.shared.components.common.uielements.commentview.base.CommentBaseView;


import java.util.List;

/**
 * Created by t_panya on 16/12/5.
 */

public interface CommentContract {

    public interface CommentView extends CommentBaseView<CommentPresenter> {

        void showImages(List<String> imageStrings);

        void showRecyclerEmptyView();

        void startPlayAudio(String path);

        void stopPlayAudio();

        void startRecordAudio();

        void stopRecordAudio();

        void cancelAndDeleteAudio();

        void showImageDetailUi(int position);

        String getCommentContent();

        String getAudioRecordPath();

        List<String> getImagePathList();

        void deleteVoice(String path);
    }

    public interface CommentPresenter extends CommentBasePresenter {

        /**
         * 预览
         * @param position
         */
        void previewImage(int position);

        void startPlayAudio(String path);

        void stopPlayAudio();

        void startRecordAudio();

        void stopRecordAudio();

        void cancelRecordAudio();

        void deleteVoice(String path);
    }

    public interface CommentImageView extends CommentBaseView<CommentImagePresenter> {
        void updateIndicator();

        void showOutOfRange(int position);

        void showSelectedCount(int count);
    }

    public interface CommentImagePresenter extends CommentBasePresenter {

    }

}
