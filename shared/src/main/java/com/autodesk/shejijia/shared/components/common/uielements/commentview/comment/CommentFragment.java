package com.autodesk.shejijia.shared.components.common.uielements.commentview.comment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.OnDismissListener;
import com.autodesk.shejijia.shared.components.common.uielements.commentview.CommentConfig;
import com.autodesk.shejijia.shared.components.common.uielements.commentview.AudioHandler;
import com.autodesk.shejijia.shared.components.common.uielements.commentview.model.entity.ImageInfo;
import com.autodesk.shejijia.shared.components.common.uielements.commentview.widget.CommonAudioAlert;
import com.autodesk.shejijia.shared.components.common.uielements.commentview.widget.GridDividerDecorator;
import com.autodesk.shejijia.shared.components.common.uielements.commentview.widget.OnItemClickListener;
import com.autodesk.shejijia.shared.components.common.uielements.commentview.photoselect.ImageSelector;
import com.autodesk.shejijia.shared.components.common.uielements.commentview.widget.SpaceItemDecoration;
import com.autodesk.shejijia.shared.components.common.utility.SharedPreferencesUtils;
import com.autodesk.shejijia.shared.components.common.utility.ToastUtils;
import com.autodesk.shejijia.shared.components.im.constants.IMChatInfo;
import com.autodesk.shejijia.shared.framework.activity.BaseActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by t_panya on 16/12/5.
 */

public class CommentFragment extends Fragment implements CommentContract.CommentView, View.OnClickListener {
    public static final String TAG = "CommentFragment";
    public static final String PARCELABLE_DATA = "parcelable_data";

    public static final String POSITION = "position";
    public static final String IMAGE_LIST = "image_list";

    private BaseActivity mContext;

    private CommentContract.CommentPresenter mPresenter;
    private CommentConfig mConfig;

    /**
     * config data to determine
     */
    private CommentConfig.ModuleType mModuleType = CommentConfig.ModuleType.MODULE_FORM;    //表格模块
    private CommentConfig.RoleType mRoleType = CommentConfig.RoleType.NORMAL;               //如果是网络数据 不可编辑
    private CommentConfig.DataSource mDataSource = CommentConfig.DataSource.LOCAL;          //本地数据
    private CommentConfig.LayoutType mLayoutType = CommentConfig.LayoutType.EDIT;           //可编辑

    private String X_Token;
    public String mCommentContent;
    public String mAudioPath;
    public ArrayList<ImageInfo> mImages;
    public int mSelectModel;

    private ImageLoader mImageLoader;
    private SelectedImgAdapter mAdapter;

    /**
     * view
     */
    private RecyclerView mRecyclerView;
    private EditText mEditText;
    private TextView mTextView;
    private LinearLayout mAudioRecord;
    private LinearLayout mAudioPlay;
    private ImageView mDeleteImage;
    private CommonAudioAlert mAlertView;
    private TextView mAudioTime;

    /**
     * audio
     */
    private AudioHandler mAudioHandler;
    private boolean isRecording;
    private boolean isPlaying;
    private long mVoiceRecordingStartTime;

    private int mTimer;

    public interface ImageItemClickListener {
        void onAddItemClick();

        void onImgItemClick(int position);

        void onDeleteImageClick(int position);
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
            mLayoutType = mConfig.geteLayoutType();
            X_Token = mConfig.getX_Token();
            mCommentContent = mConfig.getCommentContent();
            mAudioPath = mConfig.getAudioPath();
            mSelectModel = mConfig.getSelectModel();
        }
        mImages = new ArrayList<>();
        mImageLoader = ImageLoader.getInstance();
        mAdapter = new SelectedImgAdapter(mConfig, mImgItemClickListener, mImageLoader);
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
        mAudioTime = (TextView) view.findViewById(R.id.tv_audio_play_duration);
        mDeleteImage = (ImageView) view.findViewById(R.id.iv_delete_voice);
        mDeleteImage.setOnClickListener(this);

        if (isEditMode()) {
            initDetailShowStatus();
        } else {
            if (!TextUtils.isEmpty(mCommentContent)) {
                mTextView.setVisibility(View.VISIBLE);
                mTextView.setText(mCommentContent);
            }
            if (!TextUtils.isEmpty(mAudioPath)) {
                mAudioPlay.setVisibility(View.VISIBLE);
//                mAudioTime.setText();
            }
        }
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        initRecyclerView();
        mAlertView = new CommonAudioAlert(mContext, mItemListener);
        mAlertView = mAlertView.setCancelable(true).setOnDismissListener(mDismissListener);
        mAudioHandler = new AudioHandler(mContext, mAudioListener);
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = (BaseActivity) activity;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (isPlaying) {
            mAudioHandler.stopVoiceRecord();
        }
        if (isRecording) {
            mAudioHandler.cancelVoiceRecord();
        }
        isPlaying = false;
        isRecording = false;
        if (mAlertView.isShowing()) {
            mAlertView.dismiss();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ll_record_audio_container) {
            mAlertView.show();
        } else if (v.getId() == R.id.ll_audio_play_container) {
            mPresenter.startPlayAudio(mAudioPath);
        } else if (v.getId() == R.id.iv_delete_voice) {
            if (isPlaying) {
                mPresenter.stopPlayAudio();
            }
            mAudioPlay.setVisibility(View.GONE);
            mAudioRecord.setVisibility(View.VISIBLE);
            mAudioHandler.deleteVoiceRecord(mAudioPath);
            mAudioPath = null;
            SharedPreferencesUtils.writeString("AudioPath", "");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ImageSelector.REQUEST_SELECT_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                mImages = data.getParcelableArrayListExtra(ImageSelector.SELECTED_RESULT);
                mAdapter.replaceData(mImages);
            }
        }
    }

    @Override
    public void showImages(List<ImageInfo> images) {
        if (images != null) {
            mImages = (ArrayList<ImageInfo>) images;
            mAdapter.replaceData(mImages);
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
    public void stopPlayAudio() {
        mAudioHandler.stopPlayAudio();
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
    public void cancelAndDeleteAudio() {
        mAudioHandler.cancelVoiceRecord();
    }

    @Override
    public void deleteVoice(String path) {
        mAudioHandler.deleteVoiceRecord(path);
    }

    @Override
    public void showImageDetailUi(int position) {
        Intent intent = new Intent(getActivity(), CommentPreviewActivity.class);
        intent.putExtra(POSITION, position);
        intent.putParcelableArrayListExtra(IMAGE_LIST, mImages);
        getActivity().startActivity(intent);
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
    public List<ImageInfo> getImages() {
        if (mImages == null || mImages.size() == 0) {
            return null;
        }
        return mImages;
    }

    @Override
    public void setPresenter(CommentContract.CommentPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showToast(String message) {
        ToastUtils.showShort(mContext, message);
    }

    private void initRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        //给RecclerView设置GridlayoutManager，并根据配置信息，指定列数
        mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, mConfig.getColumnNum()));
//        mRecyclerView.addItemDecoration(new SpaceItemDecoration(10,mConfig.getColumnNum())); //添加divider
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initDetailShowStatus() {
        if (mLayoutType == CommentConfig.LayoutType.EDIT) {
            mEditText.setVisibility(View.VISIBLE);
            mAudioRecord.setVisibility(View.VISIBLE);
        } else if (mLayoutType == CommentConfig.LayoutType.EDIT_AUDIO_ONLY) {
            mAudioRecord.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        } else if (mLayoutType == CommentConfig.LayoutType.EDIT_IMAGE_ONLY) {
            mEditText.setVisibility(View.GONE);
            mAudioRecord.setVisibility(View.GONE);
        } else if (mLayoutType == CommentConfig.LayoutType.EDIT_TEXT_ONLY) {
            mAudioRecord.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.GONE);
        }
    }

    private boolean isEditMode() {
        if (mLayoutType != CommentConfig.LayoutType.SHOW) {
            return true;
        }
        return false;
    }

    private OnItemClickListener mItemListener = new OnItemClickListener() {
        @Override
        public void onItemClick(Object object, int id) {
            if (object != mAlertView) {
                return;
            }
            if (id == R.id.iv_sound_recording) {
                if (!isRecording) {
                    mVoiceRecordingStartTime = System.nanoTime();
                    mPresenter.startRecordAudio();
                    mAlertView.setRecordStatus(CommonAudioAlert.RECORDING);
                }
            } else if (id == R.id.iv_sound_suspend) {
                long endTime = System.nanoTime();
                if ((endTime - mVoiceRecordingStartTime) / Math.pow(10, 9) >= IMChatInfo.VoiceLeastTime) {
                    mPresenter.stopRecordAudio();
                    mAlertView.setRecordStatus(CommonAudioAlert.RECORDED);
                } else {
                    mPresenter.cancelRecordAudio();
                    mAlertView.setRecordStatus(CommonAudioAlert.PRE_RECORD);
                    showToast("时间太短");
                }
                isRecording = false;
            } else if (id == R.id.iv_sound_playing) {
                Log.d(TAG, "onItemClick: mAudioPath == " + mAudioPath);
                mAudioPath = SharedPreferencesUtils.readString("AudioPath");
//                if(!TextUtils.isEmpty(mAudioPath)){
//                    mAudioHandler.startPlayAudio(mAudioPath);
//                }
                if (!TextUtils.isEmpty(mAudioPath)) {
                    mAudioHandler.startPlayAudio(mAudioPath);
                }
            } else if (id == R.id.tv_done) {
                if (isRecording) {
                    mAudioHandler.stopVoiceRecord();
                }
                mAlertView.dismiss();
                mAlertView.setRecordStatus(CommonAudioAlert.PRE_RECORD);
                mAudioRecord.setVisibility(View.GONE);
                mAudioPlay.setVisibility(View.VISIBLE);
                mAudioTime.setText(mTimer + "''");
            }
        }
    };

    private AudioHandler.AudioHandlerListener mAudioListener = new AudioHandler.AudioHandlerListener() {
        @Override
        public void audioRecordStart() {
            //// TODO: 16/11/30
            isRecording = true;
        }

        @Override
        public void audioRecordEnd(String filePath) {
            mAudioPath = filePath;
            SharedPreferencesUtils.writeString("AudioPath", filePath);
            isRecording = false;
            mTimer = mAudioHandler.getAudioTimer(filePath);
        }

        @Override
        public void audioPlayStart() {
            isPlaying = true;
        }

        @Override
        public void audioPlayStop() {
            isPlaying = false;
        }

    };

    private OnDismissListener mDismissListener = new OnDismissListener() {
        @Override
        public void onDismiss(Object o) {
            if (o != mAlertView) {
                return;
            }
            if (isPlaying) {
                mPresenter.stopPlayAudio();
            }

            if (isRecording) {
                mPresenter.stopRecordAudio();
            }
        }
    };

    private ImageItemClickListener mImgItemClickListener = new ImageItemClickListener() {
        @Override
        public void onAddItemClick() {
            ImageSelector.getInstance().setSelectModel(mConfig.selectModel).setStartData(mImages).startSelect(CommentFragment.this);
        }

        @Override
        public void onImgItemClick(int position) {
            mPresenter.previewImage(position);
        }

        @Override
        public void onDeleteImageClick(int position) {
            mImages.remove(position);
            mAdapter.replaceData(mImages);
        }

    };
}
