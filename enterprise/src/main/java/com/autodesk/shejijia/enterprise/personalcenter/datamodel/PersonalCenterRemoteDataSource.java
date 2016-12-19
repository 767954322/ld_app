package com.autodesk.shejijia.enterprise.personalcenter.datamodel;

import android.support.annotation.NonNull;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.enterprise.personalcenter.network.PersonalCenterHttpManagerImpl;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.entity.ResponseError;
import com.autodesk.shejijia.shared.components.common.listener.ResponseCallback;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.utility.ResponseErrorUtil;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

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

    @Override
    public void uploadPersonalHeadPicture(File file,final ResponseCallback<Boolean, Boolean> callback) {
        PersonalCenterHttpManagerImpl.getInstance().uploadPersonalHeadPicture(file,new Callback(){
            @Override
            public void onResponse(Response response) throws IOException {
                if(response.isSuccessful()){
                    callback.onSuccess(true);
                    return;
                }
                callback.onError(false);
            }
            @Override
            public void onFailure(Request request, IOException e) {
                callback.onError(false);
            }

        });
    }
}
