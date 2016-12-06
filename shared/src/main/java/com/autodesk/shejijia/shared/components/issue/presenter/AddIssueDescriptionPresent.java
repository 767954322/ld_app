package com.autodesk.shejijia.shared.components.issue.presenter;

import com.autodesk.shejijia.shared.components.common.entity.ResponseError;
import com.autodesk.shejijia.shared.components.common.listener.ResponseCallback;
import com.autodesk.shejijia.shared.components.issue.contract.AddIssueDescriptionContract;
import com.autodesk.shejijia.shared.components.issue.data.IssueRepository;

import java.util.List;

/**
 * Created by Administrator on 2016/12/5.
 */

public class AddIssueDescriptionPresent implements AddIssueDescriptionContract.Presenter {

    private AddIssueDescriptionContract.View mView;

    public AddIssueDescriptionPresent(AddIssueDescriptionContract.View mView) {
        this.mView = mView;
    }


    @Override
    public void putIssueTracking(String contentText, String audioPath, List<String> imgPath) {

        IssueRepository.getInstance().putIssueTracking(
                contentText, audioPath, imgPath,
                new ResponseCallback<Boolean, ResponseError>() {
                    @Override
                    public void onSuccess(Boolean data) {
                        mView.onShowStatus(data);
                    }

                    @Override
                    public void onError(ResponseError error) {
                        mView.onShowStatus(false);
                    }
                }
        );

    }
}
