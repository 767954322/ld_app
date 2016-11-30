package com.autodesk.shejijia.shared.components.issue.contract;

import com.autodesk.shejijia.shared.components.common.entity.ProjectInfo;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Member;
import com.autodesk.shejijia.shared.components.common.uielements.commentview.model.entity.ImageInfo;
import com.autodesk.shejijia.shared.framework.BasePresenter;
import com.autodesk.shejijia.shared.framework.BaseView;

import java.util.List;

/**
 * Created by Menghao.Gu on 2016/12/5.
 */

public interface IssueAddContract {

    interface View {

        void onShowStatus(boolean status);

    }

    interface Presenter {

        /*
        *上传问题
        * */
        void putIssueTracking(int notifyCustormer, ProjectInfo projectInfo, int issueType, Member followMember, String date, String contentText, String audioPath, List<ImageInfo> imgPath);


    }

}
