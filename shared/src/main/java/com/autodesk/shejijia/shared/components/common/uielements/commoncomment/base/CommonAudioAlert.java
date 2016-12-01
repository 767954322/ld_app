package com.autodesk.shejijia.shared.components.common.uielements.commoncomment.base;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.FrameLayout;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.AlertView;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.OnDismissListener;


/**
 * Created by t_panya on 16/11/30.
 */

public class CommonAudioAlert {

    private final FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM
    );
    private int gravity = Gravity.BOTTOM;

    private Context mContext;
    private ViewGroup contentContainer;
    private ViewGroup decorView;    //activity的根View
    private ViewGroup rootView;     //AlertView 的 根View
    private Button mReRecordBtn;
    private Button mStartAndStopBtn;
    private Button mPlayBtn;
    private Button mEnsureBtn;

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
        mReRecordBtn = (Button) viewGroup.findViewById(R.id.btn_re_record);
        mReRecordBtn.setOnClickListener(mInnerClickListener);
        mStartAndStopBtn = (Button) viewGroup.findViewById(R.id.btn_start_stop_record);
        mStartAndStopBtn.setOnClickListener(mInnerClickListener);
        mPlayBtn = (Button) viewGroup.findViewById(R.id.btn_record_play);
        mPlayBtn.setOnClickListener(mInnerClickListener);
        mEnsureBtn = (Button) viewGroup.findViewById(R.id.btn_done);
        mEnsureBtn.setOnClickListener(mInnerClickListener);
    }

    /**
     * show的时候调用
     *
     * @param view 这个View
     */
    private void onAttached(View view) {
        decorView.addView(view);
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
        resetButton();
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

    private void resetButton(){
        mReRecordBtn.setText("重新录制");
        mStartAndStopBtn.setText("开始录音");
        mPlayBtn.setText("播放");
    }
    public <T extends View> T getView(int id){
        return (T) contentContainer.findViewById(id);
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
