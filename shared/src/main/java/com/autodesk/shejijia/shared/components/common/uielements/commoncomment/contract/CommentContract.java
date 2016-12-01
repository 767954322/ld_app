package com.autodesk.shejijia.shared.components.common.uielements.commoncomment.contract;

import java.util.List;

/**
 * Created by t_panya on 16/11/29.
 */

public interface CommentContract {
    interface View {
        void setLayoutType();
        /**
         * 设置presenter
         * @param presenter
         */
        void setPresenter(Presenter presenter);

        /**
         * 用来显示提示信息，用Toast实现
         * @param message 消息体
         */
        void showToast(CharSequence message);

        void showImages(List<String> imageStrings);

        void showRecyclerEmptyView();

        void startPlayAudio(String path);

        void stopPlayAudio(String path);

        void startRecordAudio();

        void stopRecordAudio();

        void resetButtonText();

        String getCommentContent();

        String getAudioRecordPath();

        List<String> getImagePathList();
    }

    interface Presenter{
        /**
         * 一般在onResume（）方法中调用，执行一些数据初始化工作
         */
        void start();

        void startPlayAudio(String path);

        void stopPlayAudio(String path);

        void startRecordAudio();

        void stopRecordAudio();

    }
}
