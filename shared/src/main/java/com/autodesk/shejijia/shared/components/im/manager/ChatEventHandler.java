package com.autodesk.shejijia.shared.components.im.manager;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Rect;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.utility.MPNetworkUtils;
import com.autodesk.shejijia.shared.components.im.constants.IMChatInfo;
import com.autodesk.shejijia.shared.components.im.constants.MPChatConstants;
import com.autodesk.shejijia.shared.components.common.utility.MPAudioManager;
import com.autodesk.shejijia.shared.components.common.utility.MPFileUtility;
import com.autodesk.shejijia.shared.framework.AdskApplication;

import java.io.File;

/**
 * @author mishraa .
 * @version 1.0 .
 * @date 16-6-18
 * @file ChatEventHandler.java  .
 * @brief .
 */
public class ChatEventHandler implements View.OnTouchListener, TextWatcher, View.OnClickListener {
    public interface ChatEventHandlerInterface {
        void onSendTextClicked(String msg);

        void customButtonDidClicked();

        void audioRecordingStarted();

        void audioRecordingEnded(String filePath);

        boolean shouldHideCustomButton();
    }


    public ChatEventHandler(Context context, LinearLayout chatParent, ChatEventHandlerInterface eventHandleInterface) {
        mContext = context;
        mBottomChatLayout = chatParent;
        mEventHandleInterface = eventHandleInterface;
        mChatTextView = (EditText) mBottomChatLayout.findViewById(R.id.chat_editextview);
        mRecordTextView = (TextView) mBottomChatLayout.findViewById(R.id.chat_voice_record);
        mRecordAudioButton = (ImageView) mBottomChatLayout.findViewById(R.id.voice_imageview);
        mOpenCustomViewButton = (ImageView) mBottomChatLayout.findViewById(R.id.chat_otherfeature_imageview);
        mSendTextButton = (Button) mBottomChatLayout.findViewById(R.id.chat_send_message_button);

        if (eventHandleInterface.shouldHideCustomButton())
            mOpenCustomViewButton.setVisibility(View.GONE);

        mSendTextButton.setOnClickListener(this);
        mRecordAudioButton.setOnClickListener(this);
        mOpenCustomViewButton.setOnClickListener(this);
        mRecordTextView.setOnTouchListener(this);
        mChatTextView.addTextChangedListener(this);

        mIsInVoiceRecordMode = false;
    }

    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (mChatTextView.getText().toString().length() == 0) {
            if (!mEventHandleInterface.shouldHideCustomButton())
                mOpenCustomViewButton.setVisibility(View.VISIBLE);

            mSendTextButton.setVisibility(View.GONE);
        } else {
            mOpenCustomViewButton.setVisibility(View.GONE);
            mSendTextButton.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (mChatTextView.getText().toString().length() == 0) {
            if (!mEventHandleInterface.shouldHideCustomButton())
                mOpenCustomViewButton.setVisibility(View.VISIBLE);

            mSendTextButton.setVisibility(View.GONE);
        } else {
            mOpenCustomViewButton.setVisibility(View.GONE);
            mSendTextButton.setVisibility(View.VISIBLE);

            if (s.length() > MPChatConstants.MAX_CHARACTERS_ALLOWED) {
                CharSequence tmp = s.subSequence(0, MPChatConstants.MAX_CHARACTERS_ALLOWED);
                final String str = tmp.toString();
                mChatTextView.setText(str);
                int textLength = mChatTextView.getText().length();
                mChatTextView.setSelection(textLength, textLength);
                showMaxcharacterLimitReachAlert();
            }
        }
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (v.getId() == R.id.chat_voice_record) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    onTouchDownAudioRecordingView();
                    break;
                case MotionEvent.ACTION_UP:
                    if (didMoveOutside(v, event))
                        onTouchCancelAudioRecordingView();
                    else
                        onTouchupAudioRecordingView();
                    break;
                case MotionEvent.ACTION_CANCEL:
                    onTouchCancelAudioRecordingView();
                    break;
                default:
                    break;
            }
        }
        return true;
    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.chat_send_message_button) {
            onSendTextClicked();

        } else if (i == R.id.voice_imageview) {
            onVoiceClicked();

        } else if (i == R.id.chat_otherfeature_imageview) {
            mEventHandleInterface.customButtonDidClicked();

        }
    }


    public void enableUserInteraction() {
        if (mIsInVoiceRecordMode)
            setVoiceModeEnabled(true);
        else
            setTextModeEnabled(true);

        mSendTextButton.setEnabled(true);
        mOpenCustomViewButton.setEnabled(true);
        mRecordAudioButton.setEnabled(true);
    }


    public void disableUserInteraction() {
        setVoiceModeEnabled(false);
        setTextModeEnabled(false);

        mSendTextButton.setEnabled(false);
        mOpenCustomViewButton.setEnabled(false);
        mRecordAudioButton.setEnabled(false);
    }


    private boolean didMoveOutside(View v, MotionEvent event) {
        int[] origin = new int[2];
        v.getLocationOnScreen(origin);
        Rect viewRect = new Rect(origin[0], origin[1], origin[0] + v.getWidth(),
                origin[1] + v.getHeight());

        boolean isOutside = !(viewRect.contains((int) event.getRawX(), (int) event.getRawY()));
        return isOutside;
    }


    private void onSendTextClicked() {
        //fixme fix bug DP-6147  发送增加网络判断
        if (!NetWorkHelper.isNetConnected(AdskApplication.getInstance())) {
            Toast.makeText(mContext, R.string.net_error, Toast.LENGTH_SHORT).show();
            return;
        }

        String info_Msg = mChatTextView.getText().toString();

        // commenting following code currently
        // no need of encoding
        /*
        try {
            info_Msg = URLEncoder.encode(info_Msg, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        */
        if (!info_Msg.isEmpty()) {
            mChatTextView.setText("");
            mEventHandleInterface.onSendTextClicked(info_Msg);
        }
    }

    private void onVoiceClicked() {
        if (mIsInVoiceRecordMode) {
            mIsInVoiceRecordMode = false;

            mChatTextView.setText("");
            mRecordAudioButton.setImageResource(R.drawable.voice_ico);

            setVoiceModeEnabled(false);
            setTextModeEnabled(true);
        } else {
            mIsInVoiceRecordMode = true;

            mChatTextView.setText("");
            mRecordTextView.setText(R.string.hold_down_the_talk);
            mRecordAudioButton.setImageResource(R.drawable.keyboard_ico);

            setTextModeEnabled(false);
            setVoiceModeEnabled(true);
        }
    }


    private void onTouchDownAudioRecordingView() {
        mRecordTextView.setText(R.string.audio_record_slide_cancel);
        mVoiceRecordingStartTime = System.nanoTime();
        startVoice();
    }


    private void onTouchupAudioRecordingView() {
        mRecordTextView.setText(R.string.hold_down_the_talk);
        long endTime = System.nanoTime();
        if ((endTime - mVoiceRecordingStartTime) / Math.pow(10, 9) >= IMChatInfo.VoiceLeastTime)
            stopVoice();
        else
            cancelVoice();
    }


    private void onTouchCancelAudioRecordingView() {
        mRecordTextView.setText(R.string.hold_down_the_talk);
        cancelVoice();
    }


    private void startVoice() {
        try {
            String uniqueFileName = MPFileUtility.getUniqueFileNameWithExtension(MPChatConstants.AUDIO_FILE_EXT);
            mAudioFile = MPFileUtility.getFileFromName(mContext, uniqueFileName);

            MPAudioManager manager = MPAudioManager.getInstance();
            manager.startRecording(mAudioFile.getAbsolutePath(), MPChatConstants.MAX_AUDIO_RECORDING_DURATION,
                    new MPAudioManager.AudioRecorderListener() {
                        @Override
                        public void onAudioRecordingStart() {
                            mEventHandleInterface.audioRecordingStarted();
                        }

                        @Override
                        public void onAudioRecordingEnd() {
                            mEventHandleInterface.audioRecordingEnded(mAudioFile.getAbsolutePath());
                        }

                        @Override
                        public void onAudioRecordingCancelled() {
                            mEventHandleInterface.audioRecordingEnded(null);
                        }

                        @Override
                        public void onAudioRecordingError() {
                            mEventHandleInterface.audioRecordingEnded(null);
                        }

                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void stopVoice() {
        MPAudioManager manager = MPAudioManager.getInstance();
        manager.stopRecording();
    }

    private void cancelVoice() {
        MPAudioManager manager = MPAudioManager.getInstance();
        manager.cancelAndDeleteRecording();
    }

    private void showMaxcharacterLimitReachAlert() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
        alertDialogBuilder.setTitle(mContext.getString(R.string.tip));
        alertDialogBuilder.setMessage(mContext.getString(R.string.max_characters_limit_reached));
        alertDialogBuilder.setPositiveButton(mContext.getString(R.string.sure), null);
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void setVoiceModeEnabled(boolean enabled) {
        if (enabled)
            mRecordTextView.setVisibility(View.VISIBLE);
        else
            mRecordTextView.setVisibility(View.GONE);

        mRecordTextView.setEnabled(enabled);
    }

    private void setTextModeEnabled(boolean enabled) {
        mChatTextView.setEnabled(enabled);
    }

    private Context mContext;
    private ChatEventHandlerInterface mEventHandleInterface;

    private LinearLayout mBottomChatLayout;
    private EditText mChatTextView;
    private TextView mRecordTextView;
    private ImageView mRecordAudioButton;
    private ImageView mOpenCustomViewButton;
    private Button mSendTextButton;

    private long mVoiceRecordingStartTime;
    private boolean mIsInVoiceRecordMode;

    private File mAudioFile;
}
