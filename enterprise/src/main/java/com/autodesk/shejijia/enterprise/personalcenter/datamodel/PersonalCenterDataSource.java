package com.autodesk.shejijia.enterprise.personalcenter.datamodel;

import android.support.annotation.NonNull;

import com.autodesk.shejijia.shared.components.common.entity.ResponseError;
import com.autodesk.shejijia.shared.components.common.listener.ResponseCallback;

import java.io.File;

/**
 * Created by luchongbin on 2016/12/18.
 */

public interface PersonalCenterDataSource {
    void getPersonalHeadPicPicture(String requestTag,String acsMemberId,@NonNull final ResponseCallback<String, ResponseError> callback);
    void uploadPersonalHeadPicture(File file,@NonNull final ResponseCallback<Boolean, Boolean> callback);
}
