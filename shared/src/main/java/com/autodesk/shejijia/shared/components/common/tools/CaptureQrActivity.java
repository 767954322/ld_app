package com.autodesk.shejijia.shared.components.common.tools;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.widget.TextView;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.tools.zxing.camera.CameraManager;
import com.autodesk.shejijia.shared.components.common.tools.zxing.decoding.CaptureActivityHandler;
import com.autodesk.shejijia.shared.components.common.tools.zxing.decoding.InactivityTimer;
import com.autodesk.shejijia.shared.components.common.tools.zxing.view.ViewfinderView;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.AlertView;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.OnItemClickListener;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.framework.activity.NavigationBarActivity;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

import java.io.IOException;
import java.util.Vector;


/**
 * @author he.liu .
 * @version v1.0 .
 * @date 2016-6-7 .
 * @file CaptureQrActivity.java .
 * @brief 扫描二维码 .
 */
public class CaptureQrActivity extends NavigationBarActivity implements Callback, OnItemClickListener {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_capture;
    }

    @Override
    protected void initView() {
        super.initView();
        CameraManager.init(getApplication());
        viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
       // fl_public_container.setBackgroundColor(UIUtils.getColor(R.color.zxing_title));
      //  ll_public_back.setBackgroundColor(UIUtils.getColor(R.color.zxing_title));
       // tv_public_title.setText(UIUtils.getString(R.string.scanner_title));
        setTitleForNavbar(UIUtils.getString(R.string.scanner_title));
        //tv_public_title.setTextColor(UIUtils.getColor(R.color.white));
        hasSurface = false;
        inactivityTimer = new InactivityTimer(this);
        showDialog();
    }

    @Override
    protected void setTitleForNavbar(String value) {
        super.setTitleForNavbar(value);
        TextView titleTextView = (TextView) findViewById(R.id.nav_title_textView);

        if (titleTextView != null)
        {
            titleTextView.setText(value);
            titleTextView.setTextColor(UIUtils.getColor(R.color.black));
        }
    }

    //弹窗提醒
    public void showDialog(){

        mAlertViewExt = new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.qr_code_format_error_tip), null, null, new String[]{UIUtils.getString(R.string.sure)}, CaptureQrActivity.this, AlertView.Style.Alert, this);
    }

    /**
     * 处理二维码扫描的结果
     *
     * @param result  扫描结果
     * @param barcode
     */
    public void handleDecode(Result result, Bitmap barcode) {
        inactivityTimer.onActivity();
        playBeepSoundAndVibrate();
        String resultString = result.getText();
        //FIXME
        if (resultString.contains("hs_uid") && resultString.contains("member_id")) {
            Intent resultIntent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putString(Constant.QrResultKey.SCANNER_RESULT, resultString);
            resultIntent.putExtras(bundle);
            this.setResult(RESULT_OK, resultIntent);
        } else {
            mAlertViewExt.show();
            return;
        }
        CaptureQrActivity.this.finish();
    }

    /**
     * 初始化相机
     *
     * @param surfaceHolder
     */
    private void initCamera(SurfaceHolder surfaceHolder) {
        try {
            CameraManager.get().openDriver(surfaceHolder);
        } catch (IOException ioe) {
            return;
        } catch (RuntimeException e) {
            return;
        }
        if (handler == null) {
            handler = new CaptureActivityHandler(this, decodeFormats,
                    characterSet);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;
    }

    public ViewfinderView getViewfinderView() {
        return viewfinderView;
    }

    public Handler getHandler() {
        return handler;
    }

    public void drawViewfinder() {
        viewfinderView.drawViewfinder();
    }

    private void initBeepSound() {
        if (playBeep && mediaPlayer == null) {
            // The volume on STREAM_SYSTEM is not adjustable, and users found it
            // too loud,
            // so we now play on the music stream.
            setVolumeControlStream(AudioManager.STREAM_MUSIC);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnCompletionListener(beepListener);

            AssetFileDescriptor file = getResources().openRawResourceFd(R.raw.beep);
            try {
                mediaPlayer.setDataSource(file.getFileDescriptor(),
                        file.getStartOffset(), file.getLength());
                file.close();
                mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
                mediaPlayer.prepare();
            } catch (IOException e) {
                mediaPlayer = null;
            }
        }
    }

    protected void playBeepSoundAndVibrate() {
        if (playBeep && mediaPlayer != null) {
            mediaPlayer.start();
        }
        if (vibrate) {
            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(VIBRATE_DURATION);
        }
    }

    /**
     * When the beep has finished playing, rewind to queue up another one.
     */
    private final OnCompletionListener beepListener = new OnCompletionListener() {
        public void onCompletion(MediaPlayer mediaPlayer) {
            mediaPlayer.seekTo(0);
        }
    };

    @Override
    public void onItemClick(Object o, int position) {
        if (o == mAlertViewExt && position != AlertView.CANCELPOSITION) {
            CaptureQrActivity.this.finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        if (hasSurface) {
            initCamera(surfaceHolder);
        } else {
            surfaceHolder.addCallback(this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
        decodeFormats = null;
        characterSet = null;

        playBeep = true;
        AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
        if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
            playBeep = false;
        }
        initBeepSound();
        vibrate = true;

        //quit the scan view
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        CameraManager.get().closeDriver();
    }

    @Override
    protected void onDestroy() {
        inactivityTimer.shutdown();
        super.onDestroy();
    }

    private static final float BEEP_VOLUME = 0.10f;
    private static final long VIBRATE_DURATION = 200L;

    private ViewfinderView viewfinderView;
    private AlertView mAlertViewExt;//窗口拓展例子
    protected InactivityTimer inactivityTimer;
    private MediaPlayer mediaPlayer;

    private boolean playBeep;
    private boolean hasSurface;
    private boolean vibrate;
    private String characterSet;
    protected CaptureActivityHandler handler;
    private Vector<BarcodeFormat> decodeFormats;
}