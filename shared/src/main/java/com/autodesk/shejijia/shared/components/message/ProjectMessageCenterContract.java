package com.autodesk.shejijia.shared.components.message;

import android.os.Bundle;

import com.autodesk.shejijia.shared.components.common.entity.ProjectInfo;
import com.autodesk.shejijia.shared.components.message.entity.MessageInfo;
import com.autodesk.shejijia.shared.framework.BasePresenter;
import com.autodesk.shejijia.shared.framework.BaseView;

import java.util.List;

/**
 * Created by luchongbin on 2016/12/8.
 */

public interface ProjectMessageCenterContract {
    interface View extends BaseView{
        void refreshProjectMessagesView(MessageInfo messageInfo);
//
        void loadMoreProjectMessagesView(MessageInfo messageInfo);

        void changeUnreadStateView();


    }
    interface Presenter extends BasePresenter{
        void getMessageCenterInfo(Bundle bundle,String mTAG);
        /*
       * 上拉刷新项目列表
       * */
        void refreshProjectMessages(long mProjectId,boolean mIsUnread);

        /*
        * 下拉加载更多项目列表
        * */
        void loadMoreProjectMessages(long mProjectId,boolean mIsUnread);
        /*
         * 更新消息状态
         * */
        void changeUnreadState(String threadId);

    }
}
