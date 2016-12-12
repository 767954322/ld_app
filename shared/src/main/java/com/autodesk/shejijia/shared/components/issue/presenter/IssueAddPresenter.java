package com.autodesk.shejijia.shared.components.issue.presenter;

import android.app.Activity;
import android.text.TextUtils;

import com.autodesk.shejijia.shared.components.common.network.FileHttpManager;
import com.autodesk.shejijia.shared.components.common.utility.ToastUtils;
import com.autodesk.shejijia.shared.components.issue.contract.IssueAddContract;

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
                    resultPutAudioFile = true;
                    resultPutFile();
                }

                @Override
                public void onFailure() {
                    resultPutAudioFile = false;
                    resultPutFile();
                }
            });
        } else {
            resultPutAudioFile = true;
            resultPutFile();
        }

        if (imgPath != null && imgPath.size() > 0) {
            for (int i = 0; i < imgPath.size(); i++) {
                numTitalPutImageFile = i + 1;
                File file = new File(imgPath.get(i));
                if (file.exists()) {
                    FileHttpManager.getInstance().upLoadFileByType(file, "IMAGE", new FileHttpManager.ResponseHandler() {
                        @Override
                        public void onSuccess(String response) {

                            if (numTitalPutImageFile == imgPath.size()) {
                                resultPutImageFile = true;
                            }
                            resultPutFile();
                        }

                        @Override
                        public void onFailure() {
                            resultPutImageFile = false;
                            ++numFailurePutImageFile;
                            resultPutFile();
                        }
                    });
                }

            }
        } else {
            resultPutImageFile = true;
            resultPutFile();
        }

    }

    void resultPutFile() {

        if (numFailurePutImageFile == 0 && numTitalPutImageFile == (mImgPath == null ? 0 : mImgPath.size())) {

            if (resultPutImageFile && resultPutAudioFile) {
                //成功
                ToastUtils.showLong(mContext, "成功");
            } else {
                ToastUtils.showLong(mContext, "失败");
            }

        } else {
            ToastUtils.showLong(mContext, "失败");
        }

    }

    private int numTitalPutImageFile = 0;
    private int numFailurePutImageFile = 0;
    private boolean resultPutImageFile = false;
    private boolean resultPutAudioFile = false;
    private List<String> urlFromServer;


//        IssueRepository.getInstance().putIssueTracking(
//                contentText, audioPath, imgPath,
//                new ResponseCallback<Boolean, ResponseError>() {
//                    @Override
//                    public void onSuccess(Boolean data) {
//                        mView.onShowStatus(data);
//                    }
//
//                    @Override
//                    public void onError(ResponseError error) {
//                        mView.onShowStatus(false);
//                    }
//                }
//        );
}
