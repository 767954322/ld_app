package com.autodesk.shejijia.enterprise.personalcenter.network;

import android.support.annotation.NonNull;

import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;

import java.io.File;

/**
 * Created by luchongbin on 2016/12/18.
 */

public interface PersonalCenterHttpManager {
    void getPersonalHeadPicPicture(String requestTag,String acsMemberId,@NonNull OkJsonRequest.OKResponseCallback callback);
    void uploadPersonalHeadPicture(File file,String acsMemberId);
}
