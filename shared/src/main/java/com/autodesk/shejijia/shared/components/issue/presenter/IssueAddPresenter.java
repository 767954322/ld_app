package com.autodesk.shejijia.shared.components.issue.presenter;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;

import com.autodesk.shejijia.shared.components.common.entity.ResponseError;
import com.autodesk.shejijia.shared.components.common.listener.ResponseCallback;
import com.autodesk.shejijia.shared.components.common.network.FileHttpManager;
import com.autodesk.shejijia.shared.components.common.utility.ToastUtils;
import com.autodesk.shejijia.shared.components.issue.contract.IssueAddContract;
import com.autodesk.shejijia.shared.components.issue.data.IssueRepository;

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
    private List<String> mImgPath;

    public IssueAddPresenter(IssueAddContract.View mView, Activity context) {
        this.mView = mView;
        this.mContext = context;
    }


    @Override
    public void putIssueTracking(String contentText, String audioPath, final List<String> imgPath) {

        this.mAudioPath = audioPath;
        this.mImgPath = imgPath;
        urlFromServer = new ArrayList<>();
        if (!TextUtils.isEmpty(audioPath)) {
            FileHttpManager.getInstance().upLoadFileByType(new File(audioPath), "AUDIO", new FileHttpManager.ResponseHandler() {
                @Override
                public void onSuccess(String response) {
                    mOkPutVoiceFile = true;
                    resultPutFile();
                }

                @Override
                public void onFailure() {
                }
            });
        } else {
            mOkPutVoiceFile = true;
        }

        if (imgPath != null && imgPath.size() > 0) {
            for (int i = 0; i < imgPath.size(); i++) {
                mCurrentImageFilePosition = i + 1;
                FileHttpManager.getInstance().upLoadFileByType(new File(imgPath.get(i)), "IMAGE", new FileHttpManager.ResponseHandler() {
                    @Override
                    public void onSuccess(String response) {
                        if (mCurrentImageFilePosition == imgPath.size()) {
                            mOkPutImageFile = true;
                            resultPutFile();
                        }
                    }

                    @Override
                    public void onFailure() {
                    }
                });
            }
        } else {
            mOkPutImageFile = true;
        }

    }

    void resultPutFile() {

        if (mOkPutImageFile && mOkPutVoiceFile) {
            Log.d("test", "完成上传服务器");
            JSONObject jsonObject = new JSONObject();
            //TODO 做JsonObject　的操作
            IssueRepository.getInstance().putIssueTracking(
                    jsonObject, new ResponseCallback<Boolean, ResponseError>() {
                        @Override
                        public void onSuccess(Boolean data) {

                        }

                        @Override
                        public void onError(ResponseError error) {

                        }
                    }
            );

        } else {
            Log.d("test", "未完成，继续调接口");
        }

    }

    private int mCurrentImageFilePosition = 0;
    private boolean mOkPutImageFile = false;
    private boolean mOkPutVoiceFile = false;
    private List<String> urlFromServer;


}
