package com.autodesk.shejijia.shared.components.message;

import android.os.Bundle;

import com.autodesk.shejijia.shared.components.message.entity.MessageInfo;
import com.autodesk.shejijia.shared.framework.BasePresenter;
import com.autodesk.shejijia.shared.framework.BaseView;

/**
 * Created by luchongbin on 2016/12/8.
 */

public interface ProjectMessageCenterContract {
    interface View extends BaseView{
        void updateProjectMessageView(MessageInfo messageInfo);
        void updateUnreadCountView();

    }
    interface Presenter extends BasePresenter{
        void getMessageCenterInfo(Bundle bundle,String mTAG);
        void getUnreadCount(String projectIds, String requestTag);
    }
}
