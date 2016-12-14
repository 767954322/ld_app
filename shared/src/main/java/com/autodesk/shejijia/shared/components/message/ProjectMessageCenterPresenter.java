package com.autodesk.shejijia.shared.components.message;

import android.content.Context;
import android.os.Bundle;

import com.autodesk.shejijia.shared.components.common.appglobal.ConstructionConstants;
import com.autodesk.shejijia.shared.components.common.entity.ResponseError;
import com.autodesk.shejijia.shared.components.common.listener.ResponseCallback;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.UserInfoUtils;
import com.autodesk.shejijia.shared.components.message.datamodel.MessageCenterRemoteDataSource;
import com.autodesk.shejijia.shared.components.message.entity.MessageInfo;

import org.json.JSONObject;

/**
 * Created by luchongbin on 2016/12/8.
 */

public class ProjectMessageCenterPresenter implements ProjectMessageCenterContract.Presenter {
    private Context mContext;
    private int mOffset = 0;
    public static final int LIMIT = 10;
    private String mTAG;
    private MessageCenterRemoteDataSource mMessageCenterDataSource;
    private ProjectMessageCenterContract.View mProjectMessageCenterPresenterView;
    public ProjectMessageCenterPresenter(Context context,String mTAG,ProjectMessageCenterContract.View mProjectMessageCenterPresenterView) {
        this.mContext = context;
        this.mTAG =mTAG;
        this.mProjectMessageCenterPresenterView = mProjectMessageCenterPresenterView;
        mMessageCenterDataSource = MessageCenterRemoteDataSource.getInstance();
    }

    @Override
    public void refreshProjectMessages(long mProjectId,boolean mIsUnread) {
        mOffset = 0;
        getMessage(mOffset,mProjectId,mIsUnread);
    }

    @Override
    public void loadMoreProjectMessages(long mProjectId,boolean mIsUnread) {
        mOffset++;
        getMessage(mOffset,mProjectId,mIsUnread);
    }
    private void getMessage(int offset,long mProjectId,boolean mIsUnread){
        Bundle requestParams = new Bundle();
        requestParams.putLong(ConstructionConstants.BUNDLE_KEY_PROJECT_ID,mProjectId);
        requestParams.putInt(ConstructionConstants.BUNDLE_KEY_OFFSET,offset);
        requestParams.putBoolean(ConstructionConstants.BUNDLE_KEY_UNREAD,mIsUnread);
        requestParams.putInt(ConstructionConstants.BUNDLE_KEY_LIMIT,LIMIT);
        getMessageCenterInfo(requestParams,mTAG);
    }

    @Override
    public void getMessageCenterInfo(Bundle bundle,String mTAG) {
        mProjectMessageCenterPresenterView.showLoading();
        mMessageCenterDataSource.getMessageCenterInfo(bundle,mTAG,new ResponseCallback<JSONObject, ResponseError>(){
            @Override
            public void onSuccess(JSONObject jsonObject) {
                String result = jsonObject.toString();
                MessageInfo messageInfo = GsonUtil.jsonToBean(result, MessageInfo.class);
                if(messageInfo.getOffset() == 0){
                    mProjectMessageCenterPresenterView.refreshProjectMessagesView(messageInfo);
                }else{
                    mProjectMessageCenterPresenterView.loadMoreProjectMessagesView(messageInfo);
                }
                mProjectMessageCenterPresenterView.hideLoading();
            }
            @Override
            public void onError(ResponseError error) {
                isHideLoading(error);
            }
        });
    }
    private void isHideLoading(ResponseError error){
        mProjectMessageCenterPresenterView.hideLoading();
        mProjectMessageCenterPresenterView.showNetError(error);
    }
    @Override
    public void changeUnreadMsgState(String threadId){
        String acsMemberId = UserInfoUtils.getAcsMemberId(mContext);
        mMessageCenterDataSource.changeUnreadMsgState(mTAG,acsMemberId,threadId,new ResponseCallback<JSONObject, ResponseError>(){
            @Override
            public void onSuccess(JSONObject data) {
                mProjectMessageCenterPresenterView.changeUnreadMsgStateView();
            }

            @Override
            public void onError(ResponseError error) {

            }
        });
    }
}
