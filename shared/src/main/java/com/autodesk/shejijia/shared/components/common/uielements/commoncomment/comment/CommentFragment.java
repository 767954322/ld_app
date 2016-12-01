package com.autodesk.shejijia.shared.components.common.uielements.commoncomment.comment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.OnDismissListener;
import com.autodesk.shejijia.shared.components.common.uielements.commoncomment.AudioHandler;
import com.autodesk.shejijia.shared.components.common.uielements.commoncomment.base.CommonAudioAlert;
import com.autodesk.shejijia.shared.components.common.uielements.commoncomment.base.OnItemClickListener;
import com.autodesk.shejijia.shared.components.common.uielements.commoncomment.CommentConfig;
import com.autodesk.shejijia.shared.components.common.uielements.commoncomment.contract.CommentContract;
import com.autodesk.shejijia.shared.components.common.uielements.photoselect.ImageSelector;
import com.autodesk.shejijia.shared.framework.activity.BaseActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by t_panya on 16/11/29.
 */

public class CommentFragment extends Fragment implements CommentContract.View,
        View.OnClickListener {

    private static final String TAG = "CommentFragment";
    public static final String PARCELABLE_DATA = "parcelable_data";
    private BaseActivity mContext;
    private CommentContract.Presenter mPresenter;
    private CommentConfig mConfig;

    /**
     * config data to determine
     */
    private CommentConfig.ModuleType mModuleType = CommentConfig.ModuleType.MODULE_FORM;    //表格模块
    private CommentConfig.RoleType mRoleType = CommentConfig.RoleType.NORMAL;               //如果是网络数据 不可编辑
    private CommentConfig.DataSource mDataSource = CommentConfig.DataSource.LOCAL;          //本地数据
    private String X_Token;
    public String mOnlineCommentContent;
    public String mAudioPath;
    public ArrayList<String> mPictureData;
    public int mSelectModel;

    /**
     * view
     */
    private RecyclerView mRecyclerView;
    private EditText mEditText;
    private TextView mTextView;
    private LinearLayout mAudioRecord;
    private LinearLayout mAudioPlay;
    private CommonAudioAlert mAlertView;

    private SelectedImgAdapter mAdapter;
    private ImageLoader mImageLoader;
    private AudioHandler mAudioHandler;

    private boolean isRecording;
    private boolean isPlaying;

    public interface ImageAddItemClickListener {
        void onAddItemClick();

        void onImgItemClick(int position);
    }

    public static CommentFragment getInstance(CommentConfig parcelable) {
        CommentFragment fragment = new CommentFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(PARCELABLE_DATA, parcelable);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {

        } else {
            mConfig = getArguments().getParcelable(PARCELABLE_DATA);
            mModuleType = mConfig.geteModuleType();
            mRoleType = mConfig.geteRoleType();
            mDataSource = mConfig.geteDataSource();
            X_Token = mConfig.getX_Token();
            mOnlineCommentContent = mConfig.getOnlineCommentContent();
            mAudioPath = mConfig.getAudioPath();
            mPictureData = (ArrayList<String>) mConfig.getPictureData();
            mSelectModel = mConfig.getSelectModel();
        }
        mImageLoader = ImageLoader.getInstance();
        mAdapter = new SelectedImgAdapter(mConfig, mListener, mImageLoader);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_common_comment, container, false);
        mEditText = (EditText) view.findViewById(R.id.et_comment_content);
        mTextView = (TextView) view.findViewById(R.id.tv_comment_content);
        mAudioRecord = (LinearLayout) view.findViewById(R.id.ll_record_audio_container);
        mAudioRecord.setOnClickListener(this);
        mAudioPlay = (LinearLayout) view.findViewById(R.id.ll_audio_play_container);
        mAudioPlay.setOnClickListener(this);
        if (isEditMode()) {
            mEditText.setVisibility(View.VISIBLE);
            mAudioRecord.setVisibility(View.VISIBLE);
        }
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        initRecyclerView();
        mAlertView = new CommonAudioAlert(mContext, mItemListener);
        mAlertView = mAlertView.setCancelable(true).setOnDismissListener(mDismissListener);
        mAudioHandler = AudioHandler.getInstance(mContext, mAudioListener);
        return view;
    }

    private void initRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        //给RecclerView设置GridlayoutManager，并根据配置信息，指定列数
        mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 3));
//        mRecyclerView.addItemDecoration(new GridDividerDecorator(mContext)); //添加divider
        mRecyclerView.setAdapter(mAdapter);
    }

    private boolean isEditMode() {
        if (mDataSource == CommentConfig.DataSource.LOCAL) {
            return true;
        }
        return false;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BaseActivity) {
            WeakReference<BaseActivity> mActivityRef = new WeakReference<>((BaseActivity) getActivity());
            mContext = mActivityRef.get();
        }
    }

    /**
     * 设置layout类型
     */
    @Override
    public void setLayoutType() {

    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
//        ViewTreeObserver vto = mRecyclerView.getViewTreeObserver();
//        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                mPresenter.start();
//            }
//        });
//        Log.d(TAG, "onResume: mRecyclerView.getWidth() == " + mRecyclerView.getWidth());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ImageSelector.REQUEST_SELECT_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                mPictureData = data.getStringArrayListExtra(ImageSelector.SELECTED_RESULT);
                mAdapter.replaceData(mPictureData);
            }
        }
    }

    /**
     * 设置presenter
     *
     * @param presenter
     */
    @Override
    public void setPresenter(CommentContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showToast(CharSequence message) {

    }

    @Override
    public void showImages(List<String> imageStrings) {
        if (imageStrings != null) {
            mPictureData = (ArrayList<String>) imageStrings;
            mAdapter.replaceData(mPictureData);
        }
    }

    @Override
    public void showRecyclerEmptyView() {
        mAdapter.showEmptyRecyclerView();
    }

    @Override
    public void startPlayAudio(String path) {
        mAudioHandler.startPlayAudio(path);
    }

    @Override
    public void stopPlayAudio(String path) {
        mAudioHandler.stopPlayAudio(path);
    }

    @Override
    public void startRecordAudio() {
        mAudioHandler.startVoiceRecord();
    }

    @Override
    public void stopRecordAudio() {
        mAudioHandler.stopVoiceRecord();
    }

    @Override
    public void resetButtonText() {

    }

    @Override
    public String getCommentContent() {
        if (TextUtils.isEmpty(mEditText.getText().toString())) {
            return "";
        }
        return mEditText.getText().toString();
    }

    @Override
    public String getAudioRecordPath() {
        if (TextUtils.isEmpty(mAudioPath)) {
            return "";
        }
        return mAudioPath;
    }

    @Override
    public List<String> getImagePathList() {
        if (mPictureData == null || mPictureData.size() == 0) {
            return null;
        }
        return mPictureData;
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ll_record_audio_container) {
            mAlertView.show();
        }else if(v.getId() == R.id.ll_audio_play_container){
            mPresenter.startPlayAudio(mAudioPath);
        }
    }

    private ImageAddItemClickListener mListener = new ImageAddItemClickListener() {
        @Override
        public void onAddItemClick() {
            ImageSelector.getInstance().setSelectModel(mConfig.selectModel).startSelect(CommentFragment.this);
        }

        @Override
        public void onImgItemClick(int position) {
            // TODO: 16/11/30  
        }
    };

    private OnItemClickListener mItemListener = new OnItemClickListener() {
        @Override
        public void onItemClick(Object object, int id) {
            if (object != mAlertView) {
                return;
            }
            if (id == R.id.btn_start_stop_record) {
                if (!isRecording) {
                    mPresenter.startRecordAudio();
                    ((Button) mAlertView.getView(id)).setText("停止录音");
                    isRecording = true;
                } else {
                    mPresenter.stopRecordAudio();
                    ((Button) mAlertView.getView(id)).setText("开始录音");
                    isRecording = false;
                }
            } else if (id == R.id.btn_record_play) {
                if (!isPlaying) {
                    mPresenter.startPlayAudio(mAudioPath);
                    ((Button) mAlertView.getView(id)).setText("停止播放");
                    isPlaying = true;
                } else {
                    mPresenter.stopPlayAudio(mAudioPath);
                    ((Button) mAlertView.getView(id)).setText("开始播放");
                    isPlaying = false;
                }
            } else if (id == R.id.btn_done) {
                   if(!TextUtils.isEmpty(mAudioPath)){
                       mAudioRecord.setVisibility(View.GONE);
                       mAudioPlay.setVisibility(View.VISIBLE);
                   }
                mAlertView.dismiss();
            }
        }
    };

    private AudioHandler.AudioHandlerListener mAudioListener = new AudioHandler.AudioHandlerListener() {
        @Override
        public void audioRecordStart() {
            //// TODO: 16/11/30
        }

        @Override
        public void audioRecordEnd(String filePath) {
            mAudioPath = filePath;
        }

        @Override
        public void audioPlayStart() {

        }

        @Override
        public void audioPlayStop() {

        }

    };

    private OnDismissListener mDismissListener = new OnDismissListener() {
        @Override
        public void onDismiss(Object o) {
            if(o != mAlertView){
                return;
            }
            if(isPlaying){
                mPresenter.stopPlayAudio(mAudioPath);
            }

            if(isRecording){
                mPresenter.stopRecordAudio();
            }
        }
    };
}
