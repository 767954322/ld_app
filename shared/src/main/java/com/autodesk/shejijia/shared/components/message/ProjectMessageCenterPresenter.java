package com.autodesk.shejijia.shared.components.message;

import android.content.Context;
import android.os.Bundle;

import com.autodesk.shejijia.shared.components.common.entity.ResponseError;
import com.autodesk.shejijia.shared.components.common.listener.ResponseCallback;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.message.ProjectMessageCenterContract;
import com.autodesk.shejijia.shared.components.message.datamodel.MessageCenterRemoteDataSource;
import com.autodesk.shejijia.shared.components.message.entity.MessageInfo;

import org.json.JSONObject;

/**
 * Created by luchongbin on 2016/12/8.
 */

public class ProjectMessageCenterPresenter implements ProjectMessageCenterContract.Presenter {
    private Context mContext;
    private MessageCenterRemoteDataSource mMessageCenterDataSource;
    private ProjectMessageCenterContract.View mProjectMessageCenterPresenterView;
    public ProjectMessageCenterPresenter(Context context, ProjectMessageCenterContract.View mProjectMessageCenterPresenterView) {
        this.mContext = context;
        this.mProjectMessageCenterPresenterView = mProjectMessageCenterPresenterView;
        mMessageCenterDataSource = MessageCenterRemoteDataSource.getInstance();
    }
    @Override
    public void getUnreadCount(String projectIds, String requestTag) {
        mProjectMessageCenterPresenterView.showLoading();
        mMessageCenterDataSource.getUnreadCount(projectIds,requestTag,new ResponseCallback<JSONObject, ResponseError>(){
            @Override
            public void onSuccess(JSONObject data) {
                mProjectMessageCenterPresenterView.updateUnreadCountView();
            }

            @Override
            public void onError(ResponseError error) {
                mProjectMessageCenterPresenterView.hideLoading();
                mProjectMessageCenterPresenterView.showNetError(error);
            }
        });
    }
    @Override
    public void getMessageCenterInfo(Bundle bundle,String mTAG) {
        mProjectMessageCenterPresenterView.showLoading();
        mMessageCenterDataSource.getMessageCenterInfo(bundle,mTAG,new ResponseCallback<JSONObject, ResponseError>(){
            @Override
            public void onSuccess(JSONObject jsonObject) {
                String result = jsonObject.toString();
                MessageInfo messageInfo = GsonUtil.jsonToBean(result, MessageInfo.class);
                mProjectMessageCenterPresenterView.hideLoading();
                mProjectMessageCenterPresenterView.updateProjectMessageView(messageInfo);
            }
            @Override
            public void onError(ResponseError error) {
                mProjectMessageCenterPresenterView.hideLoading();
                mProjectMessageCenterPresenterView.showNetError(error);
            }
        });
    }
}
