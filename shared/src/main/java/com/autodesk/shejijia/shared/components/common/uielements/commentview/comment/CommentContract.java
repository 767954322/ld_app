package com.autodesk.shejijia.shared.components.common.uielements.commentview.comment;

import com.autodesk.shejijia.shared.components.common.uielements.commentview.base.BasePresenter;
import com.autodesk.shejijia.shared.components.common.uielements.commentview.base.BaseView;


import java.util.List;

/**
 * Created by t_panya on 16/12/5.
 */

public interface CommentContract {

    public interface CommentView extends BaseView<CommentPresenter>{

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
    }

    public interface CommentPresenter extends BasePresenter{

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
    }

    public interface CommentImageView extends BaseView<CommentImagePresenter>{
        void updateIndicator();

        void showOutOfRange(int position);

        void showSelectedCount(int count);
    }

    public interface CommentImagePresenter extends BasePresenter{

    }

}
