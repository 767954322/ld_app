package com.autodesk.shejijia.enterprise.personalcenter.datamodel;

import android.support.annotation.NonNull;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.enterprise.personalcenter.network.PersonalCenterHttpManagerImpl;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.entity.ResponseError;
import com.autodesk.shejijia.shared.components.common.listener.ResponseCallback;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.utility.ResponseErrorUtil;
import org.json.JSONObject;

/**
 * Created by luchongbin on 2016/12/18.
 */

public class PersonalCenterRemoteDataSource implements PersonalCenterDataSource {
    public PersonalCenterRemoteDataSource() {
    }
    private static class ServerHttpManagerHolder {
        private static final PersonalCenterRemoteDataSource INSTANCE = new PersonalCenterRemoteDataSource();
    }

    public static PersonalCenterRemoteDataSource getInstance() {
        return PersonalCenterRemoteDataSource.ServerHttpManagerHolder.INSTANCE;
    }
    @Override
    public void getPersonalHeadPicPicture(String requestTag,String acsMemberId,@NonNull final ResponseCallback<String, ResponseError> callback) {
        PersonalCenterHttpManagerImpl.getInstance().getPersonalHeadPicPicture(requestTag,acsMemberId,new OkJsonRequest.OKResponseCallback(){
            @Override
            public void onResponse(JSONObject jsonObject) {
                String avatar = jsonObject.optString(Constant.PersonCenterKey.AVATAR);
                callback.onSuccess(avatar);
            }
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                ResponseError responseError =  ResponseErrorUtil.checkVolleyError(volleyError);
                callback.onError(responseError);
            }
        });
    }
}
