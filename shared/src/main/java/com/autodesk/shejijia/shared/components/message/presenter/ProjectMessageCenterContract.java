package com.autodesk.shejijia.shared.components.message.presenter;

import android.os.Bundle;

import com.autodesk.shejijia.shared.components.message.entity.MessageInfo;
import com.autodesk.shejijia.shared.framework.BasePresenter;
import com.autodesk.shejijia.shared.framework.BaseView;

/**
 * Created by luchongbin on 2016/12/8.
 */

public interface ProjectMessageCenterContract {
    interface View extends BaseView{
        void updateProjectDetailsView(MessageInfo messageInfo);
    }
    interface Presenter extends BasePresenter{
        void listMessageCenterInfo(Bundle bundle,String mTAG);
    }
}
