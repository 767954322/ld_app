package com.autodesk.shejijia.shared.components.form.presenter;

import android.os.Bundle;
import android.text.TextUtils;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.appglobal.ConstructionConstants;
import com.autodesk.shejijia.shared.components.common.entity.ResponseError;
import com.autodesk.shejijia.shared.components.common.listener.ResponseCallback;
import com.autodesk.shejijia.shared.components.common.network.FileHttpManager;
import com.autodesk.shejijia.shared.components.common.uielements.commentview.model.entity.ImageInfo;
import com.autodesk.shejijia.shared.components.common.utility.JsonFileUtil;
import com.autodesk.shejijia.shared.components.common.utility.LogUtils;
import com.autodesk.shejijia.shared.components.common.utility.UserInfoUtils;
import com.autodesk.shejijia.shared.components.form.common.entity.SHForm;
import com.autodesk.shejijia.shared.components.form.common.entity.categoryForm.SHPrecheckForm;
import com.autodesk.shejijia.shared.components.form.common.entity.microBean.CheckItem;
import com.autodesk.shejijia.shared.components.form.contract.UnqualifiedContract;
import com.autodesk.shejijia.shared.components.form.data.FormRepository;
import com.autodesk.shejijia.shared.framework.AdskApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by t_panya on 16/12/12.
 */

public class UnqualifiedPresenter implements UnqualifiedContract.Presenter {
    private static final String TAG = "PreCheckUnqualifiedPresenter";
    private UnqualifiedContract.View mView;
    private List<com.autodesk.shejijia.shared.components.common.entity.microbean.File> mResponseImageFileList;
    private List<com.autodesk.shejijia.shared.components.common.entity.microbean.File> mAudioFileList;
    private List<ImageInfo> mPictures;
    private String mAudioPath;
    private String mCommentContent;
    private SHPrecheckForm mPreCheckForm;

    private int mMissImageFileNum = 0;
    private int mCurrentImageFileNum = 0;
    private boolean mOkPutVoiceFile = false;

    public UnqualifiedPresenter(UnqualifiedContract.View view) {
        mView = view;
        mResponseImageFileList = new ArrayList<>();
        mAudioFileList = new ArrayList<>();
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
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray array = jsonObject.optJSONArray("files");
                            List<com.autodesk.shejijia.shared.components.common.entity.microbean.File> files =
                                    JsonFileUtil.jsonArray2ModelList(array, com.autodesk.shejijia.shared.components.common.entity.microbean.File.class);
                            ++mCurrentImageFileNum;
//                        com.autodesk.shejijia.shared.components.common.entity.microbean.File responseFile =
//                                GsonUtil.jsonToBean(response, com.autodesk.shejijia.shared.components.common.entity.microbean.File.class);
                            mResponseImageFileList.addAll(files);
                            if(mCurrentImageFileNum == mPictures.size()){
                                resultPutFile();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
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

                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray array = jsonObject.optJSONArray("files");
                        List<com.autodesk.shejijia.shared.components.common.entity.microbean.File> files =
                                JsonFileUtil.jsonArray2ModelList(array, com.autodesk.shejijia.shared.components.common.entity.microbean.File.class);
                        mOkPutVoiceFile = true;
                        mAudioFileList.addAll(files);
                        resultPutFile();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
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
        LogUtils.d(TAG,"reultput file");
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
        LogUtils.d(TAG,"upLoadFormFile");
        if(mPreCheckForm == null){
            return;
        }
        for(CheckItem item : mPreCheckForm.getCheckItems()){
            mPreCheckForm.setStatus(0);
            if("input_content".equals(item.getItemId())){
                if(mResponseImageFileList != null && mResponseImageFileList.size() != 0){
                    item.getFormFeedBack().setImages(mResponseImageFileList);
                }
                if(mAudioFileList != null && mAudioFileList.size() != 0){
                    item.getFormFeedBack().setAudio(mAudioFileList.get(0));
                }
                if(!TextUtils.isEmpty(mCommentContent)){
                    item.getFormFeedBack().setComment(mCommentContent);
                }
                break;
            }
        }
        List<SHForm> formList = new ArrayList<>();
        formList.add(mPreCheckForm);
        Bundle bundle = new Bundle();
        bundle.putString("user_id", UserInfoUtils.getMemberId(AdskApplication.getInstance().getApplicationContext()));
        FormRepository.getInstance().updateRemoteForms(formList, bundle, new ResponseCallback() {
            @Override
            public void onSuccess(Object data) {
                modifyTaskStatus();
            }

            @Override
            public void onError(Object error) {

            }
        });
    }

    private void modifyTaskStatus() {
        Bundle bundle = new Bundle();
        bundle.putLong("pid", Long.valueOf(mPreCheckForm.getProjectId()));
        bundle.putString("tid", mPreCheckForm.getTaskId());

        Map<String, String> map = new HashMap<>();
        map.put("result",ConstructionConstants.TaskStatus.REJECTED.toUpperCase());

        FormRepository.getInstance().inspectTask(bundle, new JSONObject(map), new ResponseCallback<Map, ResponseError>() {
            @Override
            public void onSuccess(Map data) {
                // TODO: 16/12/13 改变Task状态成功后的业务逻辑
                mView.submitSuccess();
            }

            @Override
            public void onError(ResponseError error) {
                // TODO: 16/12/13 改变Task状态失败后的业务逻辑
                mView.hideLoading();
                mView.showNetError(error);
            }
        });


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
