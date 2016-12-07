package com.autodesk.shejijia.shared.components.common.utility;

import android.media.AudioManager;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;

/**
 * @author   jainar .
 * @version  v1.0 .
 * @date       2016-6-13 .
 * @file          MPAudioManager.java .
 * @brief        .
 */
public class MPAudioManager implements MediaPlayer.OnPreparedListener,
        MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener,
        MediaRecorder.OnErrorListener
{
    public interface AudioPlayerListener
    {
        void onAudioPlayingStart();

        void onAudioPlayingEnd();

        void onAudioPlayingError();
    }

    public interface AudioRecorderListener
    {
        void onAudioRecordingStart();

        void onAudioRecordingEnd();

        void onAudioRecordingCancelled();

        void onAudioRecordingError();
    }

    public static MPAudioManager getInstance()
    {
        if (mInstance == null)
        {
            synchronized (MPAudioManager.class)
            {
                mInstance = new MPAudioManager();
            }
        }

        return mInstance;
    }


    @Override
    public void onPrepared(MediaPlayer mediaPlayer)
    {
        if (mediaPlayer != mMediaPlayer || mMediaPlayer == null)
            return;

        boolean success = false;

        try
        {
            mMediaPlayer.start();
            success = true;
        }
        catch (Exception e)
        {
            handleError("Could not start playback" + e.getMessage());
        }
        finally
        {
            if (mPlayerListener != null)
            {
                if (success)
                    mPlayerListener.onAudioPlayingStart();
                else
                    mPlayerListener.onAudioPlayingError();
            }
        }
    }


    @Override
    public void onCompletion(MediaPlayer mediaPlayer)
    {
        if (mediaPlayer != mMediaPlayer)
            return;

        if (mPlayerListener != null)
            mPlayerListener.onAudioPlayingEnd();
    }


    @Override
    public boolean onError(MediaPlayer mediaPlayer, int what, int extra)
    {
        if (mediaPlayer != mMediaPlayer)
            return false;

        handleError("Error occurred in media playback: " + what);

        if (mPlayerListener != null)
            mPlayerListener.onAudioPlayingError();

        return true;
    }


    @Override
    public void onError(MediaRecorder mediaRecorder, int what, int extra)
    {
        if (mediaRecorder != mMediaRecorder)
            return;

        handleError("Error occurred in media recording: " + what);

        if (mRecorderListener != null)
            mRecorderListener.onAudioRecordingError();
    }


    public void startPlayingFile(String filePath, AudioPlayerListener listener)
    {
        assert (filePath != null && !filePath.isEmpty());
        assert (listener != null);

        cleanupPlayer();
        cleanupRecorder();

        mPlayerListener = listener;

        boolean success = setupPlayer(filePath);

        // File playing starts in the onPrepared listener
        if (success)
            mFileCurrentlyPlaying = filePath;
        else
            mPlayerListener.onAudioPlayingError();
    }


    public void continuePlaying()
    {
        // Invalid state, do nothing
        if (mMediaPlayer == null || mMediaPlayer.isPlaying())
            return;

        boolean success = false;

        try
        {
            mMediaPlayer.start();
            success = true;
        }
        catch (Exception e)
        {
            handleError("Exception while resuming playback: " + e.getMessage());
        }
        finally
        {
            if (mPlayerListener != null)
            {
                if (success)
                    mPlayerListener.onAudioPlayingStart();
                else
                    mPlayerListener.onAudioPlayingError();
            }
        }
    }


    public void pausePlaying()
    {
        // Invalid state, do nothing
        if (mMediaPlayer == null || !mMediaPlayer.isPlaying())
            return;

        boolean success = false;

        try
        {
            mMediaPlayer.pause();
            success = true;
        }
        catch (Exception e)
        {
            handleError("Exception while pausing playback: " + e.getMessage());
        }
        finally
        {
            if (mPlayerListener != null)
            {
                if (success)
                    mPlayerListener.onAudioPlayingEnd();
                else
                    mPlayerListener.onAudioPlayingError();
            }
        }
    }


    public void stopPlaying()
    {
        // Invalid state, do nothing
        if (mMediaPlayer == null || !mMediaPlayer.isPlaying())
            return;

        boolean success = false;

        try
        {
            mMediaPlayer.stop();
            success = true;
        }
        catch (Exception e)
        {
            handleError("Exception while stopping playback: " + e.getMessage());
        }
        finally
        {
            mFileCurrentlyPlaying = null;

            if (mPlayerListener != null)
            {
                if (success)
                    mPlayerListener.onAudioPlayingEnd();
                else
                    mPlayerListener.onAudioPlayingError();
            }
        }
    }


    public boolean isCurrentlyPlayingFile(String localURL)
    {
        assert (localURL != null && !localURL.isEmpty());

        return (mMediaPlayer != null && mMediaPlayer.isPlaying() && mFileCurrentlyPlaying.equalsIgnoreCase(localURL));
    }


    public int getAudioDuration(String filePath)
    {
        assert (filePath != null && !filePath.isEmpty());

        MediaMetadataRetriever retriever = new MediaMetadataRetriever();

        int duration = 0;

        try
        {
            retriever.setDataSource(filePath);
            String durationValue = retriever.extractMetadata(
                    MediaMetadataRetriever.METADATA_KEY_DURATION);
            retriever.release();

            double durationInSeconds = Math.ceil(Double.parseDouble(durationValue) / kSecondToMilliseconds);

            duration = (int) durationInSeconds;
        }
        catch (Exception e)
        {
            handleError("Exception while querying media duration" + e.getMessage());
        }

        return duration;
    }


    public void startRecording(String filePath, int maxDurationInSeconds, AudioRecorderListener listener)
    {
        assert (filePath != null && !filePath.isEmpty());
        assert (listener != null);

        cleanupRecorder();
        cleanupPlayer();

        boolean success = setupRecorder(filePath, maxDurationInSeconds);

        mRecorderListener = listener;

        try
        {
            if (success)
            {
                mMediaRecorder.start();
                mFileCurrentlyRecording = filePath;
            }
        }
        catch (Exception e)
        {
            handleError("Could not start recording: " + e.getMessage());
            success = false;
        }

        if (success)
            mRecorderListener.onAudioRecordingStart();
        else
            mRecorderListener.onAudioRecordingError();
    }


    public void stopRecording()
    {
        // Invalid state, do nothing
        if (mMediaRecorder == null || mFileCurrentlyRecording == null)
            return;

        boolean success = false;

        try
        {
            mMediaRecorder.stop();
            success = true;
        }
        catch (Exception e)
        {
            handleError("Exception while stopping media recorder: " + e.getMessage());
        }
        finally
        {
            mFileCurrentlyRecording = null;

            mRecordingCountdownTimer.cancel();

            if (mRecorderListener != null)
            {
                if (success)
                    mRecorderListener.onAudioRecordingEnd();
                else
                    mRecorderListener.onAudioRecordingError();
            }
        }
    }


    public void cancelAndDeleteRecording()
    {
        // Invalid state, do nothing
        if (mMediaRecorder == null || mFileCurrentlyRecording == null)
            return;

        boolean success = false;

        try
        {
            mMediaRecorder.stop();
        }
        catch (Exception e)
        {
            handleError("Error cancelling recording" + e.getMessage());
        }

        success = MPFileUtility.removeFile(mFileCurrentlyRecording);

        mFileCurrentlyRecording = null;

        Log.d(TAG, "Audio recording cancelled and file deleted successfully: " + success);

        if (mRecorderListener != null)
            mRecorderListener.onAudioRecordingCancelled();
    }

    public void deleteVoice(String path)
    {
        if(TextUtils.isEmpty(path))
        {
            return;
        }
        try {
            MPFileUtility.removeFile(path);
        }
        catch (Exception ex)
        {
            handleError("Error delete voice" + ex.getMessage());
        }
    }

    private MPAudioManager()
    {

    }


    private void cleanupPlayer()
    {
        try
        {
            if (mMediaPlayer != null)
            {
                if (mMediaPlayer.isPlaying())
                    stopPlaying();

                mMediaPlayer.setOnCompletionListener(null);
                mMediaPlayer.setOnErrorListener(null);
                mMediaPlayer.setOnPreparedListener(null);

                mMediaPlayer.reset();
                mMediaPlayer.release();
            }
        }
        catch (Exception e)
        {
            handleError("Exception while stopping media player (cleanup): " + e.getMessage());
        }

        mFileCurrentlyPlaying = null;
        mPlayerListener = null;
        mMediaPlayer = null;
    }


    private void cleanupRecorder()
    {
        try
        {
            if (mMediaRecorder != null)
            {
                if (mFileCurrentlyRecording != null)
                    stopRecording();

                mMediaRecorder.setOnErrorListener(null);
                mRecordingCountdownTimer = null;

                mMediaRecorder.release();
            }
        }
        catch (Exception e)
        {
            handleError("Exception while stopping media recorder (cleanup): " + e.getMessage());
        }

        mFileCurrentlyRecording = null;
        mRecorderListener = null;
        mMediaRecorder = null;
    }


    private boolean setupPlayer(String localURL)
    {
        assert (localURL != null && !localURL.isEmpty());

        boolean success = false;

        try
        {
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

            mMediaPlayer.setDataSource(localURL);

            mMediaPlayer.setOnCompletionListener(this);
            mMediaPlayer.setOnErrorListener(this);
            mMediaPlayer.setOnPreparedListener(this);

            mMediaPlayer.prepareAsync();

            success = true;
        }
        catch (Exception e)
        {
            handleError("Exception while setting up player: " + e.getMessage());
        }

        return success;
    }


    private boolean setupRecorder(final String localURL, int maxDurationInSeconds)
    {
        assert (localURL != null && !localURL.isEmpty());

        boolean success = false;

        try
        {
            mMediaRecorder = new MediaRecorder();

            mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);

            // CountdownTimer takes duration in ms
            if (maxDurationInSeconds > 0)
            {
                mRecordingCountdownTimer = new CountDownTimer(maxDurationInSeconds * kSecondToMilliseconds,
                        kSecondToMilliseconds)
                {
                    @Override
                    public void onTick(long l)
                    {
                        // Can use this to show seconds elapsed
                    }

                    @Override
                    public void onFinish()
                    {
                        if (mFileCurrentlyRecording != null &&
                                mFileCurrentlyRecording.equalsIgnoreCase(localURL))
                            stopRecording();
                    }
                };

                mRecordingCountdownTimer.start();
            }

            mMediaRecorder.setOutputFile(localURL);
            mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
            mMediaRecorder.setAudioChannels(1);
            mMediaRecorder.setAudioSamplingRate(44100);

            mMediaRecorder.setOnErrorListener(this);

            mMediaRecorder.prepare();

            success = true;
        }
        catch (Exception e)
        {
            handleError("Exception while setting up recorder: " + e.getMessage());
        }

        return success;
    }


    private void handleError(String errorMessage)
    {
        MPNetworkUtils.logError(TAG, errorMessage);
    }

    private static final int kSecondToMilliseconds = 1000;
    private static final String TAG = "MPAudioManager";

    private static MPAudioManager mInstance = null;

    private MediaRecorder mMediaRecorder = null;
    private MediaPlayer mMediaPlayer = null;

    private String mFileCurrentlyPlaying = null;
    private String mFileCurrentlyRecording = null;

    private AudioPlayerListener mPlayerListener = null;
    private AudioRecorderListener mRecorderListener = null;

    private CountDownTimer mRecordingCountdownTimer = null;
}
