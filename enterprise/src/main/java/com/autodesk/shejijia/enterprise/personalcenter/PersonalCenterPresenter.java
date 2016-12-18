package com.autodesk.shejijia.enterprise.personalcenter;

import android.content.Context;
import android.os.Bundle;

import com.autodesk.shejijia.enterprise.personalcenter.datamodel.PersonalCenterRemoteDataSource;
import com.autodesk.shejijia.shared.components.common.entity.ResponseError;
import com.autodesk.shejijia.shared.components.common.listener.ResponseCallback;
import com.autodesk.shejijia.shared.components.common.utility.UserInfoUtils;


/**
 * Created by luchongbin on 16-12-16.
 */

public class PersonalCenterPresenter implements PersonalCenterContract.Presenter {
    private Context mContext;
    private PersonalCenterRemoteDataSource mPersonalCenterRemoteDataSource;
    private PersonalCenterContract.View mPersonalCenterContract;

    public PersonalCenterPresenter(Context mContext) {
        this.mContext = mContext;
        this.mPersonalCenterContract = (PersonalCenterContract.View) mContext;
        this.mPersonalCenterRemoteDataSource = PersonalCenterRemoteDataSource.getInstance();
    }

    @Override
    public void uploadPersonalHeadPicture(Bundle bundle, String requestTag) {
    }

    @Override
    public void getPersonalHeadPicture(String requestTag) {
        String acsMemberId = UserInfoUtils.getAcsMemberId(mContext);
        mPersonalCenterRemoteDataSource.getPersonalHeadPicPicture(requestTag, acsMemberId, new ResponseCallback<String, ResponseError>() {
            @Override
            public void onSuccess(String avatar) {
                mPersonalCenterContract.updatePersonalHeadPictureView(avatar);
            }

            @Override
            public void onError(ResponseError error) {
            }
        });
    }
}
