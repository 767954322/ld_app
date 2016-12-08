package com.autodesk.shejijia.shared.components.message.presenter;

import android.content.Context;
import android.os.Bundle;

import com.autodesk.shejijia.shared.components.common.entity.ResponseError;
import com.autodesk.shejijia.shared.components.common.listener.ResponseCallback;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.message.datamodel.MessageCenterDataSource;
import com.autodesk.shejijia.shared.components.message.datamodel.MessageCenterRemoteDataSource;
import com.autodesk.shejijia.shared.components.message.entity.MessageInfo;

import org.json.JSONObject;

/**
 * Created by luchongbin on 2016/12/8.
 */

public class ProjectMessageCenterPresenterImpl implements ProjectMessageCenterPresenter.Presenter {
    private Context mContext;
    private MessageCenterDataSource mMessageCenterDataSource;
    private ProjectMessageCenterPresenter.View mProjectMessageCenterPresenterView;
    public ProjectMessageCenterPresenterImpl(Context context, ProjectMessageCenterPresenter.View mProjectMessageCenterPresenterView) {
        this.mContext = context;
        this.mProjectMessageCenterPresenterView = mProjectMessageCenterPresenterView;
        mMessageCenterDataSource = new MessageCenterRemoteDataSource();
    }
    @Override
    public void listMessageCenterInfo(Bundle bundle,String mTAG) {
        mProjectMessageCenterPresenterView.showLoading();
        mMessageCenterDataSource.listMessageCenterInfo(bundle,mTAG,new ResponseCallback<JSONObject, ResponseError>(){
            @Override
            public void onSuccess(JSONObject jsonObject) {
                String result = jsonObject.toString();
                MessageInfo messageInfo = GsonUtil.jsonToBean(result, MessageInfo.class);
                mProjectMessageCenterPresenterView.hideLoading();
                mProjectMessageCenterPresenterView.updateProjectDetailsView(messageInfo);
            }
            @Override
            public void onError(ResponseError error) {
                mProjectMessageCenterPresenterView.hideLoading();
                mProjectMessageCenterPresenterView.showNetError(error);
            }
        });
    }
}
