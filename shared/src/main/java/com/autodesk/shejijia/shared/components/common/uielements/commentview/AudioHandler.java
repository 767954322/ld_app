package com.autodesk.shejijia.shared.components.common.uielements.commentview;

import android.content.Context;

import com.autodesk.shejijia.shared.components.common.utility.MPAudioManager;
import com.autodesk.shejijia.shared.components.common.utility.MPFileUtility;
import com.autodesk.shejijia.shared.components.im.constants.MPChatConstants;

import java.io.File;

/**
 * Created by t_panya on 16/11/21.
 */

public class AudioHandler{

    private static final String TAG = "AudioHandler";
    private long mVoiceRecordingStartTime;
    private File mAudioFile;
    private Context mContext;
    private  AudioHandlerListener mCommentHandlerListener;
    private static AudioHandler sInstance;

    public interface AudioHandlerListener {

        void audioRecordStart();

        void audioRecordEnd(String filePath);

        void audioPlayStart();

        void audioPlayStop();
    }

    public static AudioHandler getInstance(Context context, AudioHandlerListener handlerListener){
        if(sInstance == null){
            synchronized (Object.class){
               if (sInstance == null){
                   sInstance = new AudioHandler(context,handlerListener);
               }
            }
        }
        return sInstance;
    }

    private AudioHandler(Context context, AudioHandlerListener handlerInterface){
        mContext = context;
        mCommentHandlerListener = handlerInterface;
    }

    public void stopVoiceRecord(){
        MPAudioManager manager = MPAudioManager.getInstance();
        manager.stopRecording();
    }

    public void deleteVoiceRecord(String path){
        MPAudioManager manager = MPAudioManager.getInstance();
        manager.deleteVoice(path);
    }

    public void cancelVoiceRecord(){
        MPAudioManager manager = MPAudioManager.getInstance();
        manager.cancelAndDeleteRecording();
    }

    public void startVoiceRecord(){
        try{
            String uniqueAudioFileName = MPFileUtility.getUniqueFileNameWithExtension(MPChatConstants.AUDIO_FILE_EXT);
            mAudioFile = MPFileUtility.getFileFromName(mContext,uniqueAudioFileName);

            MPAudioManager manager = MPAudioManager.getInstance();
            manager.startRecording(mAudioFile.getAbsolutePath(),MPChatConstants.MAX_AUDIO_RECORDING_DURATION,
                    new MPAudioManager.AudioRecorderListener(){
                        @Override
                        public void onAudioRecordingStart() {
                            mCommentHandlerListener.audioRecordStart();
                        }

                        @Override
                        public void onAudioRecordingEnd() {
                            mCommentHandlerListener.audioRecordEnd(mAudioFile.getAbsolutePath());
                        }

                        @Override
                        public void onAudioRecordingCancelled() {
                            mCommentHandlerListener.audioRecordEnd(null);
                        }

                        @Override
                        public void onAudioRecordingError() {
                            mCommentHandlerListener.audioRecordEnd(null);
                        }
                    });
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void startPlayAudio(String filePath){
        try {
            MPAudioManager manager = MPAudioManager.getInstance();
            manager.startPlayingFile(filePath, new MPAudioManager.AudioPlayerListener() {
                @Override
                public void onAudioPlayingStart() {
                    mCommentHandlerListener.audioPlayStart();
                }

                @Override
                public void onAudioPlayingEnd() {
                    mCommentHandlerListener.audioPlayStop();
                }

                @Override
                public void onAudioPlayingError() {
                    mCommentHandlerListener.audioPlayStop();
                }
            });
        }catch (Exception ex){

        }
    }

    public void stopPlayAudio(){
        try {
            MPAudioManager manager = MPAudioManager.getInstance();
            manager.stopPlaying();
        }catch (Exception ex){

        }
    }
}
