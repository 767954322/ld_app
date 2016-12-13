package com.autodesk.shejijia.shared.components.issue.presenter;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;

import com.autodesk.shejijia.shared.components.common.appglobal.ConstructionConstants;
import com.autodesk.shejijia.shared.components.common.entity.ProjectInfo;
import com.autodesk.shejijia.shared.components.common.entity.ResponseError;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Member;
import com.autodesk.shejijia.shared.components.common.listener.ResponseCallback;
import com.autodesk.shejijia.shared.components.common.network.FileHttpManager;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.issue.common.entity.IssueFileBean;
import com.autodesk.shejijia.shared.components.issue.common.entity.IssueFiles;
import com.autodesk.shejijia.shared.components.issue.contract.IssueAddContract;
import com.autodesk.shejijia.shared.components.issue.data.IssueRepository;
import com.autodesk.shejijia.shared.framework.AdskApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Menghao.Gu on 2016/12/5.
 */

public class IssueAddPresenter implements IssueAddContract.Presenter {

    private Activity mContext;
    private IssueAddContract.View mView;
    private String mAudioPath;
    private String mContentText;
    private int mIssueType;
    private String mDate;
    private int mNotifyCustormer;
    private Member mFollowMember;
    private List<String> mImgPath;
    private ProjectInfo mProjectInfo;

    public IssueAddPresenter(IssueAddContract.View mView, Activity context) {
        this.mView = mView;
        this.mContext = context;
    }


    @Override
    public void putIssueTracking(int notifyCustormer, ProjectInfo projectInfo, int issueType, Member followMember, String date, String contentText, String audioPath, final List<String> imgPath) {

        this.mAudioPath = audioPath;
        this.mImgPath = imgPath;
        this.mContentText = contentText;
        this.mDate = date;
        this.mFollowMember = followMember;
        this.mIssueType = issueType;
        this.mProjectInfo = projectInfo;
        this.mNotifyCustormer = notifyCustormer;
        urlFromServer = new ArrayList<>();
        if (!TextUtils.isEmpty(audioPath)) {
            FileHttpManager.getInstance().upLoadFileByType(new File(audioPath), ConstructionConstants.IssueTracking.MEDIA_TYPE_VOICE, new FileHttpManager.ResponseHandler() {
                @Override
                public void onSuccess(String response) {
                    mOkPutVoiceFile = true;
                    IssueFileBean issueFile = GsonUtil.jsonToBean(response, IssueFileBean.class);
                    List<IssueFileBean.Files> files = issueFile.getFiles();
                    if (files.size() > 0) {
                        urlFromServer.add(new IssueFiles(ConstructionConstants.IssueTracking.MEDIA_TYPE_VOICE, files.get(0).getFile_id(), files.get(0).getPublic_url()));
                    }
                    resultPutFile();
                }

                @Override
                public void onFailure() {
                    resultPutFile();
                }
            });
        } else {
            mOkPutVoiceFile = true;
            resultPutFile();
        }

        if (imgPath != null && imgPath.size() > 0) {
            for (int i = 0; i < imgPath.size(); i++) {
                FileHttpManager.getInstance().upLoadFileByType(new File(imgPath.get(i)), ConstructionConstants.IssueTracking.MEDIA_TYPE_IMAGE, new FileHttpManager.ResponseHandler() {
                    @Override
                    public void onSuccess(String response) {
                        ++mCurrentImageFileNum;
                        IssueFileBean issueFile = GsonUtil.jsonToBean(response, IssueFileBean.class);
                        List<IssueFileBean.Files> files = issueFile.getFiles();
                        if (files.size() > 0) {
                            urlFromServer.add(new IssueFiles(ConstructionConstants.IssueTracking.MEDIA_TYPE_IMAGE, files.get(0).getFile_id(), files.get(0).getPublic_url()));
                        }
                        if (mCurrentImageFileNum == imgPath.size()) {
                            resultPutFile();
                        }
                    }

                    @Override
                    public void onFailure() {
                        ++mCurrentImageFileNum;
                        ++mMissImageFileNum;
                        resultPutFile();
                    }
                });
            }
        }

    }

    //判断是否语音图片上传结束
    private void resultPutFile() {

        if (mImgPath == null || mImgPath.size() == 0) {

            if (mOkPutVoiceFile) {
                putFiles();
            } else {
                //TODO 上传失败(无图片)
            }


        } else {

            if (mCurrentImageFileNum == mImgPath.size() && mMissImageFileNum == 0 && mOkPutVoiceFile) {
                putFiles();
            } else {
                //TODO 上传失败（包含图片）
            }


        }
    }


    private void putFiles() {
        JSONObject jsonObject = null;
        JSONArray jsonArray = new JSONArray();
        if (urlFromServer.size() > 0) {
            int count = urlFromServer.size();
            for (int i = 0; i < count; i++) {
                JSONObject tmpObj = new JSONObject();
                try {
                    tmpObj.put("type", urlFromServer.get(i).getType());
                    tmpObj.put("resource_file_id", urlFromServer.get(i).getResource_file_id());
                    tmpObj.put("resource_url", urlFromServer.get(i).getResource_url());
                    jsonArray.put(tmpObj);
                } catch (JSONException e) {
                }
            }
        }
        try {
            jsonObject = new JSONObject();
            jsonObject.put(ConstructionConstants.IssueTracking.JSONOBJECT_ADDISSUE_ACS_RESOURCES, jsonArray);
            jsonObject.put(ConstructionConstants.IssueTracking.JSONOBJECT_ADDISSUE_DESCRIPTION, mContentText);
            jsonObject.put(ConstructionConstants.IssueTracking.JSONOBJECT_ADDISSUE_CREATED_BY, AdskApplication.getInstance().getMemberEntity().getHs_uid());
            jsonObject.put(ConstructionConstants.IssueTracking.JSONOBJECT_ADDISSUE_DUE_AT, mDate);
            jsonObject.put(ConstructionConstants.IssueTracking.JSONOBJECT_ADDISSUE_FOLLOW_UP_ID, mFollowMember.getUid());
            jsonObject.put(ConstructionConstants.IssueTracking.JSONOBJECT_ADDISSUE_FOLLOW_UP_ROLE, mFollowMember.getRole());
            jsonObject.put(ConstructionConstants.IssueTracking.JSONOBJECT_ADDISSUE_TYPE, mIssueType);
            jsonObject.put(ConstructionConstants.IssueTracking.JSONOBJECT_ADDISSUE_PROJECT_ID, mProjectInfo.getProjectId());
            jsonObject.put(ConstructionConstants.IssueTracking.JSONOBJECT_ADDISSUE_CREATOR_ROLE, AdskApplication.getInstance().getMemberEntity().getMember_type());
            jsonObject.put(ConstructionConstants.IssueTracking.JSONOBJECT_ADDISSUE_IS_VISIBLE, mNotifyCustormer);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        IssueRepository.getInstance().putIssueTracking(
                jsonObject, new ResponseCallback<Boolean, ResponseError>() {
                    @Override
                    public void onSuccess(Boolean data) {

                        mView.onShowStatus(true);
                    }

                    @Override
                    public void onError(ResponseError error) {
                        mView.onShowStatus(false);
                    }
                }
        );

    }

    private int mMissImageFileNum = 0;
    private int mCurrentImageFileNum = 0;
    private boolean mOkPutVoiceFile = false;
    private List<IssueFiles> urlFromServer;


}
