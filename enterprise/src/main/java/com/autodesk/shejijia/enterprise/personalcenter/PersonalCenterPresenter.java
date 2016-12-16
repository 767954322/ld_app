package com.autodesk.shejijia.enterprise.personalcenter;
import android.content.Context;
import android.os.Bundle;
/**
 * Created by luchongbin on 16-12-16.
 */

public class PersonalCenterPresenter implements PersonalCenterContract.Presenter{
    private Context mContext;
    public PersonalCenterPresenter(Context mContext) {
        this.mContext = mContext;
    }
    @Override
    public void uploadPersonalHeadPic(Bundle bundle, String requestTag) {
    }

    @Override
    public void getPersonalHeadPicPicture() {

    }
}
