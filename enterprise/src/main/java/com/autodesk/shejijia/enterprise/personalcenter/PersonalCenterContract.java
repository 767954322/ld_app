package com.autodesk.shejijia.enterprise.personalcenter;

import android.os.Bundle;

import com.autodesk.shejijia.shared.framework.BasePresenter;
import com.autodesk.shejijia.shared.framework.BaseView;

/**
 * Created by luchongbin on 16-12-16.
 */

public interface PersonalCenterContract {
    interface View extends BaseView {


    }
    interface Presenter extends BasePresenter {

        void uploadPersonalHeadPic(Bundle bundle, String requestTag);
        void getPersonalHeadPicPicture();
    }
}
