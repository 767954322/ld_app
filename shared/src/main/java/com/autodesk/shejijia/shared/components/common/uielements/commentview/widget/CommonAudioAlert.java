package com.autodesk.shejijia.shared.components.common.uielements.commentview.widget;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.OnDismissListener;


/**
 * Created by t_panya on 16/11/30.
 */

public class CommonAudioAlert {

    public static final int PRE_RECORD = 0;
    public static final int RECORDING = 1;
    public static final int RECORDED = 2;

    private final FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM
    );
    private int gravity = Gravity.BOTTOM;

    private Context mContext;
    private ViewGroup contentContainer;
    private ViewGroup decorView;    //activity的根View
    private ViewGroup rootView;     //AlertView 的 根View
    private ImageView mRecordingImage;
    private ImageView mSuspendImage;
    private ImageView mPlayImage;
    private TextView mDoneText;

    private boolean isDismiss;


    private OnItemClickListener mListener;
    private OnDismissListener onDismissListener;

    public CommonAudioAlert(Context context, OnItemClickListener listener){
        mContext = context;
        mListener = listener;
        initViews();
    }

    private void initViews(){
        if(mContext == null){
            return;
        }
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        decorView = (ViewGroup) ((Activity) mContext).getWindow().getDecorView().findViewById(android.R.id.content);
        rootView = (ViewGroup) layoutInflater.inflate(R.layout.layout_alertview, decorView, false);
        rootView.setLayoutParams(new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
        ));
        contentContainer = (ViewGroup) rootView.findViewById(R.id.content_container);

        params.gravity = Gravity.BOTTOM;
        params.setMargins(0, 0, 0, 0);
        contentContainer.setLayoutParams(params);
        gravity = Gravity.BOTTOM;
        initAudioAlertDetailViews(layoutInflater);
    }

    private void initAudioAlertDetailViews(LayoutInflater inflater){
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.layout_record_content, contentContainer);
        mRecordingImage = (ImageView) viewGroup.findViewById(R.id.iv_sound_recording);
        mSuspendImage = (ImageView) viewGroup.findViewById(R.id.iv_sound_suspend);
        mPlayImage = (ImageView) viewGroup.findViewById(R.id.iv_sound_playing);
        mDoneText = (TextView) viewGroup.findViewById(R.id.tv_done);
        setListener();
    }

    /**
     * show的时候调用
     *
     * @param view 这个View
     */
    private void onAttached(View view) {
        decorView.addView(view);
    }

    private void setListener(){
        mRecordingImage.setOnClickListener(mInnerClickListener);
        mSuspendImage.setOnClickListener(mInnerClickListener);
        mPlayImage.setOnClickListener(mInnerClickListener);
        mDoneText.setOnClickListener(mInnerClickListener);
    }

    /**
     * 添加这个View到Activity的根视图
     */
    public void show() {
        if (isShowing()) {
            return;
        }
        onAttached(rootView);
    }

    public CommonAudioAlert setOnDismissListener(OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
        return this;
    }

    public CommonAudioAlert setCancelable(boolean isCancelable) {
        View view = rootView.findViewById(R.id.outmost_container);

        if (isCancelable) {
            view.setOnTouchListener(onCancelableTouchListener);
        } else {
            view.setOnTouchListener(null);
        }
        return this;
    }

    /**
     * 检测该View是不是已经添加到根视图
     *
     * @return 如果视图已经存在该View返回true
     */
    public boolean isShowing() {
        View view = decorView.findViewById(R.id.outmost_container);
        return view != null;
    }

    public void dismiss() {
        if (isDismiss) {
            return;
        }
        decorView.post(new Runnable() {
            @Override
            public void run() {
                //从activity根视图移除
                decorView.removeView(rootView);
                isDismiss = false;
                if (onDismissListener != null) {
                    onDismissListener.onDismiss(CommonAudioAlert.this);
                }
            }
        });
        isDismiss = true;
    }

    public <T extends View> T getView(int id){
        return (T) contentContainer.findViewById(id);
    }

    public void setRecordStatus(int status){
        switch (status){
            case PRE_RECORD:
                mRecordingImage.setVisibility(View.VISIBLE);
                mSuspendImage.setVisibility(View.INVISIBLE);
                mPlayImage.setVisibility(View.INVISIBLE);
                break;
            case RECORDING:
                mRecordingImage.setVisibility(View.INVISIBLE);
                mSuspendImage.setVisibility(View.VISIBLE);
                mPlayImage.setVisibility(View.INVISIBLE);
                break;
            case RECORDED:
                mRecordingImage.setVisibility(View.VISIBLE);
                mSuspendImage.setVisibility(View.INVISIBLE);
                mPlayImage.setVisibility(View.VISIBLE);
                break;
        }
    }

    /**
     * Called when the user touch on black overlay in order to dismiss the dialog
     */
    private final View.OnTouchListener onCancelableTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                dismiss();
            }
            return false;
        }
    };

    private View.OnClickListener mInnerClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mListener.onItemClick(CommonAudioAlert.this,v.getId());
        }
    };


}
