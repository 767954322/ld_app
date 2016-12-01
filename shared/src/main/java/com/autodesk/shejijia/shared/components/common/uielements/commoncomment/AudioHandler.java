package com.autodesk.shejijia.shared.components.common.uielements.commoncomment;

import android.content.Context;
import android.graphics.Rect;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.utility.MPAudioManager;
import com.autodesk.shejijia.shared.components.common.utility.MPFileUtility;
import com.autodesk.shejijia.shared.components.im.constants.IMChatInfo;
import com.autodesk.shejijia.shared.components.im.constants.MPChatConstants;
import com.autodesk.shejijia.shared.components.im.manager.ChatEventHandler;

import java.io.File;

/**
 * Created by t_panya on 16/11/21.
 */

public class AudioHandler implements View.OnTouchListener, View.OnClickListener, TextWatcher {

    private long mVoiceRecordingStartTime;
    private boolean mIsInVoiceRecordMode;
    private boolean mIsInVoicePlayMode;
    private File mAudioFile;
    private Context mContext;
    private CommonCommentHandlerInterface mCommentHandlerInterface;
    private LinearLayout mRecordAudioContainer;
    private LinearLayout mPlayAudioContainer;

    public interface CommonCommentHandlerInterface{
        void onBeEdited(boolean isEdited);

        void audioRecordingStarted();

        void audioRecordingEnded(String filePath);

        void audioPlay(boolean isInPlaying);
    }

    public AudioHandler(Context context, RelativeLayout touchParent, CommonCommentHandlerInterface handlerInterface){
        mContext = context;
        mCommentHandlerInterface = handlerInterface;
        mIsInVoiceRecordMode = false;
        mIsInVoicePlayMode = false;
        mRecordAudioContainer = (LinearLayout) touchParent.findViewById(R.id.ll_record_audio_container);
        mPlayAudioContainer = (LinearLayout) touchParent.findViewById(R.id.ll_audio_play_container);
        enableUserInteraction();
        mRecordAudioContainer.setOnTouchListener(this);
        mPlayAudioContainer.setOnClickListener(this);
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.ll_audio_play_container){
           if(mIsInVoicePlayMode){
               mCommentHandlerInterface.audioPlay(true);
           }else {
               mCommentHandlerInterface.audioPlay(false);
           }
            mIsInVoicePlayMode = !mIsInVoicePlayMode;
        }
    }

    public void enableUserInteraction(){
        if (!mIsInVoiceRecordMode)
            setRecordEnabled(true);
        else {
            setRecordEnabled(false);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (v.getId() == R.id.ll_record_audio_wrap){
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    mVoiceRecordingStartTime = System.nanoTime();
                    startVoiceRecord();
                    break;
                case MotionEvent.ACTION_UP:
                    if(isMoveOutSide(v,event)){
                        cancelVoiceRecord();
                    }else {
                        onRecordActionUp();
                    }
                    break;
                case MotionEvent.ACTION_CANCEL:
                    cancelVoiceRecord();
                    break;
            }
        }
        return true;
    }

    private void onRecordActionUp(){
        long endTime = System.nanoTime();
        if ((endTime - mVoiceRecordingStartTime) / Math.pow(10, 9) >= IMChatInfo.VoiceLeastTime){      //大于1s,则认为录音过了,否则时间太短
            stopVoice();
        } else {
            cancelVoiceRecord();
        }
    }
    private void stopVoice(){
        MPAudioManager manager = MPAudioManager.getInstance();
        manager.stopRecording();
    }


    private void cancelVoiceRecord(){
        MPAudioManager manager = MPAudioManager.getInstance();
        manager.cancelAndDeleteRecording();
    }

    private void startVoiceRecord(){
        try{
            String uniqueAudioFileName = MPFileUtility.getUniqueFileNameWithExtension(MPChatConstants.AUDIO_FILE_EXT);
            mAudioFile = MPFileUtility.getFileFromName(mContext,uniqueAudioFileName);

            MPAudioManager manager = MPAudioManager.getInstance();
            manager.startRecording(mAudioFile.getAbsolutePath(),MPChatConstants.MAX_AUDIO_RECORDING_DURATION,
                    new MPAudioManager.AudioRecorderListener(){
                        @Override
                        public void onAudioRecordingStart() {
                            mCommentHandlerInterface.audioRecordingStarted();
                        }

                        @Override
                        public void onAudioRecordingEnd() {
                            setRecordEnabled(false);
                            mCommentHandlerInterface.audioRecordingEnded(mAudioFile.getAbsolutePath());
                        }

                        @Override
                        public void onAudioRecordingCancelled() {
                            mCommentHandlerInterface.audioRecordingEnded(null);
                        }

                        @Override
                        public void onAudioRecordingError() {
                            mCommentHandlerInterface.audioRecordingEnded(null);
                        }
                    });
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private boolean isMoveOutSide(View view, MotionEvent event){
        int[] origin = new int[2];
        view.getLocationOnScreen(origin);
        Rect viewRect = new Rect(origin[0], origin[1], origin[0] + view.getWidth(),
                origin[1] + view.getHeight());

        boolean isOutside = !(viewRect.contains((int) event.getRawX(), (int) event.getRawY()));
        return isOutside;
    }

    private void setRecordEnabled(boolean enabled) {
        if (enabled){
            mPlayAudioContainer.setVisibility(View.GONE);
            mRecordAudioContainer.setVisibility(View.VISIBLE);
        } else {
            mRecordAudioContainer.setVisibility(View.GONE);
            mPlayAudioContainer.setVisibility(View.VISIBLE);
        }
        mRecordAudioContainer.setEnabled(enabled);
    }
}
