package com.autodesk.shejijia.shared.components.form.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.network.FileHttpManager;
import com.autodesk.shejijia.shared.components.common.uielements.commentview.model.entity.ImageInfo;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.LogUtils;
import com.autodesk.shejijia.shared.components.form.common.entity.categoryForm.SHPrecheckForm;
import com.autodesk.shejijia.shared.components.form.common.entity.microBean.CheckItem;
import com.autodesk.shejijia.shared.components.form.contract.PreCheckUnqualified;
import com.autodesk.shejijia.shared.components.form.data.FormRepository;
import com.autodesk.shejijia.shared.components.form.ui.activity.UnqualifiedActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by t_panya on 16/12/12.
 */

public class PreCheckUnqualifiedPresenter implements PreCheckUnqualified.Presenter {
    private static final String TAG = "PreCheckUnqualifiedPresenter";
    private PreCheckUnqualified.View mView;
    private Context mContext;
    private List<com.autodesk.shejijia.shared.components.common.entity.microbean.File> mResponseImageFileList;
    private com.autodesk.shejijia.shared.components.common.entity.microbean.File mAudioFile;
    private List<ImageInfo> mPictures;
    private String mAudioPath;
    private String mCommentContent;
    private SHPrecheckForm mPreCheckForm;

    private int mMissImageFileNum = 0;
    private int mCurrentImageFileNum = 0;
    private boolean mOkPutVoiceFile = false;

    public PreCheckUnqualifiedPresenter(PreCheckUnqualified.View view) {
        mView = view;
        if (view instanceof UnqualifiedActivity) {
            mContext = (Context) view;
        }
        mResponseImageFileList = new ArrayList<>();
    }

    @Override
    public void upLoadFiles(final List<ImageInfo> pictures, final String audioPath, final String commentContent, SHPrecheckForm preCheckForm) {
        if(preCheckForm == null){
            return;
        }
        mCommentContent = commentContent;
        mPreCheckForm = preCheckForm;
        if(pictures != null && pictures.size() != 0){
            mPictures = pictures;
            for(ImageInfo info : mPictures){
                if(info.getPhotoSource() == ImageInfo.PhotoSource.CLOUD){
                    continue;
                }
                String path = info.getPath();
                final File file = new File(path);
                FileHttpManager.getInstance().upLoadFileByType(file, "IMAGE", new FileHttpManager.ResponseHandler() {
                    @Override
                    public void onSuccess(String response) {
                        LogUtils.d(TAG, response);
                        ++mCurrentImageFileNum;
                        com.autodesk.shejijia.shared.components.common.entity.microbean.File reponseFile =
                                GsonUtil.jsonToBean(response, com.autodesk.shejijia.shared.components.common.entity.microbean.File.class);
                        mResponseImageFileList.add(reponseFile);
                        if(mCurrentImageFileNum == mPictures.size()){
                            resultPutFile();
                        }
                    }

                    @Override
                    public void onFailure() {
                        ++mCurrentImageFileNum;
                        ++mMissImageFileNum;
                        resultPutFile();
                        LogUtils.d(TAG,"onFailure");
                    }
                });
            }
        }

        if(!TextUtils.isEmpty(audioPath)){
            mAudioPath = audioPath;
            File file = new File(mAudioPath);
            FileHttpManager.getInstance().upLoadFileByType(file, "AUDIO", new FileHttpManager.ResponseHandler() {
                @Override
                public void onSuccess(String response) {
                    mOkPutVoiceFile = true;
                    com.autodesk.shejijia.shared.components.common.entity.microbean.File responseFile =
                            GsonUtil.jsonToBean(response, com.autodesk.shejijia.shared.components.common.entity.microbean.File.class);
                    mAudioFile = responseFile;
                    resultPutFile();
                }

                @Override
                public void onFailure() {
                    resultPutFile();
                    LogUtils.d(TAG,"onFailure");
                }
            });
        } else {
            mOkPutVoiceFile = true;
            resultPutFile();
        }
    }

    private void resultPutFile(){
        if(mPictures == null || mPictures.size() == 0){
            if(mOkPutVoiceFile){
                upLoadFormFile();
            } else {
                // TODO: 16/12/14 上传失败
            }
        } else {
            if(mCurrentImageFileNum == mPictures.size() && mMissImageFileNum == 0 && mOkPutVoiceFile){
                upLoadFormFile();
            } else {
                // TODO: 16/12/14 上传失败
            }
        }
    }

    private void upLoadFormFile(){
        if(mPreCheckForm == null){
            return;
        }
        for(CheckItem item : mPreCheckForm.getCheckItems()){
            if("input_content".equals(item.getItemId())){
                if(mResponseImageFileList != null && mResponseImageFileList.size() != 0){
                    item.getFormFeedBack().setImages(mResponseImageFileList);
                }
                if(mAudioFile != null){
                    item.getFormFeedBack().setAudio(mAudioFile);
                }
                if(!TextUtils.isEmpty(mCommentContent)){
                    item.getFormFeedBack().setComment(mCommentContent);
                }
                break;
            }
        }
        List<SHPrecheckForm> formList = new ArrayList<>();
        formList.add(mPreCheckForm);
//        FormRepository.getInstance().updateRemoteForms();
    }

    @Override
    public void setCheckIndex(SHPrecheckForm mPreCheckForm, boolean checked, int id) {
        if (id == R.id.imgbtn_equipment) {
            if (checked) {
                for (CheckItem item : mPreCheckForm.getCheckItems()) {
                    if ("equipment_whether_qualification".equals(item.getItemId())) {
                        item.getFormFeedBack().setCurrentCheckIndex(1);
                        break;
                    }
                }
            } else {
                for (CheckItem item : mPreCheckForm.getCheckItems()) {
                    if ("equipment_whether_qualification".equals(item.getItemId())) {
                        item.getFormFeedBack().setCurrentCheckIndex(0);
                        break;
                    }
                }
            }
        } else if (id == R.id.imgbtn_custom_disagree) {
            if (checked) {
                for (CheckItem item : mPreCheckForm.getCheckItems()) {
                    if ("acceptance_whether_check".equals(item.getItemId())) {
                        item.getFormFeedBack().setCurrentCheckIndex(1);
                        break;
                    }
                }
            } else {
                for (CheckItem item : mPreCheckForm.getCheckItems()) {
                    if ("acceptance_whether_check".equals(item.getItemId())) {
                        item.getFormFeedBack().setCurrentCheckIndex(0);
                        break;
                    }
                }
            }
        } else if (id == R.id.imgbtn_monitor_absence) {
            if (checked) {
                for (CheckItem item : mPreCheckForm.getCheckItems()) {
                    if ("monitor_whether_presence".equals(item.getItemId())) {
                        item.getFormFeedBack().setCurrentCheckIndex(1);
                        break;
                    }
                }
            } else {
                for (CheckItem item : mPreCheckForm.getCheckItems()) {
                    if ("monitor_whether_presence".equals(item.getItemId())) {
                        item.getFormFeedBack().setCurrentCheckIndex(0);
                        break;
                    }
                }
            }
        } else if (id == R.id.imgbtn_not_standard) {
            if (checked) {
                for (CheckItem item : mPreCheckForm.getCheckItems()) {
                    if ("interface_wether_standard".equals(item.getItemId())) {
                        item.getFormFeedBack().setCurrentCheckIndex(1);
                        break;
                    }
                }
            } else {
                for (CheckItem item : mPreCheckForm.getCheckItems()) {
                    if ("interface_wether_standard".equals(item.getItemId())) {
                        item.getFormFeedBack().setCurrentCheckIndex(0);
                        break;
                    }
                }
            }
        }
    }

}
