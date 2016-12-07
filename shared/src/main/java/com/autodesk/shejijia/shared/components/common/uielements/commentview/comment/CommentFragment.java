package com.autodesk.shejijia.shared.components.common.uielements.commentview.comment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
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
import com.autodesk.shejijia.shared.components.common.uielements.commentview.base.CommonBaseFragment;
import com.autodesk.shejijia.shared.components.common.uielements.commentview.AudioHandler;
import com.autodesk.shejijia.shared.components.common.uielements.commentview.utils.FileUtils;
import com.autodesk.shejijia.shared.components.common.uielements.commentview.widget.CommonAudioAlert;
import com.autodesk.shejijia.shared.components.common.uielements.commentview.widget.OnItemClickListener;
import com.autodesk.shejijia.shared.components.common.uielements.commentview.photoselect.ImageSelector;
import com.autodesk.shejijia.shared.components.common.utility.ToastUtils;
import com.autodesk.shejijia.shared.components.im.constants.IMChatInfo;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by t_panya on 16/12/5.
 */

public class CommentFragment extends CommonBaseFragment implements CommentContract.CommentView, View.OnClickListener{
    private static final String TAG = "CommentFragment";
    public static final String PARCELABLE_DATA = "parcelable_data";

    public static final String POSITION = "position";
    public static final String STRING_LIST = "string_list";

    private CommentContract.CommentPresenter mPresenter;
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

    /**
     * audio
     */
    private AudioHandler mAudioHandler;
    private boolean isRecording;
    private boolean isPlaying;
    private long mVoiceRecordingStartTime;

    private int containerId;

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
            X_Token = mConfig.getX_Token();
            mOnlineCommentContent = mConfig.getOnlineCommentContent();
            mAudioPath = mConfig.getAudioPath();
            mPictureData = (ArrayList<String>) mConfig.getPictureData();
            mSelectModel = mConfig.getSelectModel();
        }
        mImageLoader = ImageLoader.getInstance();
        mAdapter = new SelectedImgAdapter(mConfig, mImgItemClickListener, mImageLoader);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_common_comment, container, false);
        containerId = container.getId();
        Log.d("CommonBaseActivity", "onCreateView: containerId == " + containerId);
        mEditText = (EditText) view.findViewById(R.id.et_comment_content);
        mTextView = (TextView) view.findViewById(R.id.tv_comment_content);
        mAudioRecord = (LinearLayout) view.findViewById(R.id.ll_record_audio_container);
        mAudioRecord.setOnClickListener(this);
        mAudioPlay = (LinearLayout) view.findViewById(R.id.ll_audio_play_container);
        mAudioPlay.setOnClickListener(this);
        mDeleteImage = (ImageView) view.findViewById(R.id.iv_delete_voice);
        mDeleteImage.setOnClickListener(this);
        if (isEditMode()) {
            mEditText.setVisibility(View.VISIBLE);
            mAudioRecord.setVisibility(View.VISIBLE);

        } else {
            if(!TextUtils.isEmpty(mOnlineCommentContent)){
//                mTextView.
            }
            if(!TextUtils.isEmpty(mAudioPath)){

            }
        }
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        initRecyclerView();
        mAlertView = new CommonAudioAlert(mContext, mItemListener);
        mAlertView = mAlertView.setCancelable(true).setOnDismissListener(mDismissListener);
        mAudioHandler = AudioHandler.getInstance(mContext, mAudioListener);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(isPlaying){
            mAudioHandler.stopVoiceRecord();
        }
        if(isRecording){
            mAudioHandler.cancelVoiceRecord();
        }
        isPlaying = false;
        isRecording = false;
        if(mAlertView.isShowing()){
          mAlertView.dismiss();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ll_record_audio_container) {
            mAlertView.show();
        }else if(v.getId() == R.id.ll_audio_play_container){
            mPresenter.startPlayAudio(mAudioPath);
        } else if(v.getId() == R.id.iv_delete_voice){
            if(isPlaying){
                mPresenter.stopPlayAudio();
            }
            mAudioPlay.setVisibility(View.GONE);
            mAudioRecord.setVisibility(View.VISIBLE);
            mAudioHandler.deleteVoiceRecord(mAudioPath);
            mAudioPath = null;

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
                mPictureData = data.getStringArrayListExtra(ImageSelector.SELECTED_RESULT);
                mAdapter.replaceData(mPictureData);
            }
        }
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
        Intent intent = new Intent(getActivity(),CommentPreviewActivity.class);
        intent.putExtra(POSITION,position);
        intent.putStringArrayListExtra(STRING_LIST,mPictureData);
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
    public List<String> getImagePathList() {
        if (mPictureData == null || mPictureData.size() == 0) {
            return null;
        }
        return mPictureData;
    }

    @Override
    public void setPresenter(CommentContract.CommentPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showToast(String message) {
        ToastUtils.showShort(mContext,message);
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
                    isRecording = true;
                }
            } else if (id == R.id.iv_sound_suspend) {
                long endTime = System.nanoTime();
                if((endTime - mVoiceRecordingStartTime) / Math.pow(10, 9) >= IMChatInfo.VoiceLeastTime){
                    mPresenter.stopRecordAudio();
                    mAlertView.setRecordStatus(CommonAudioAlert.RECORDED);
                } else {
                    mPresenter.cancelRecordAudio();
                    mAlertView.setRecordStatus(CommonAudioAlert.PRE_RECORD);
                    showToast("时间太短");
                }
                isRecording = false;
            } else if (id == R.id.iv_sound_playing) {
                if(!TextUtils.isEmpty(mAudioPath)){
                    mAudioHandler.startPlayAudio(mAudioPath);
                }
            } else if(id == R.id.tv_done){
                mAlertView.dismiss();
                mAlertView.setRecordStatus(CommonAudioAlert.PRE_RECORD);
                mAudioRecord.setVisibility(View.GONE);
                mAudioPlay.setVisibility(View.VISIBLE);
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
            isRecording = false;
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
            if(o != mAlertView){
                return;
            }
            if(isPlaying){
                mPresenter.stopPlayAudio();
            }

            if(isRecording){
                mPresenter.stopRecordAudio();
            }
        }
    };

    private ImageItemClickListener mImgItemClickListener = new ImageItemClickListener() {
        @Override
        public void onAddItemClick() {
            ImageSelector.getInstance().setSelectModel(mConfig.selectModel).setStartData(mPictureData).startSelect(CommentFragment.this);
        }

        @Override
        public void onImgItemClick(int position) {
            mPresenter.previewImage(position);
        }

        @Override
        public void onDeleteImageClick(int position) {
            mPictureData.remove(position);
            mAdapter.replaceData(mPictureData);
        }

    };
}
