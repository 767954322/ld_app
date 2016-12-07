package com.autodesk.shejijia.shared.components.nodeprocess.contract;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.autodesk.shejijia.shared.components.common.entity.ProjectInfo;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Member;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Task;
import com.autodesk.shejijia.shared.framework.BasePresenter;
import com.autodesk.shejijia.shared.framework.BaseView;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by t_xuz on 11/11/16.
 */

public interface TaskDetailsContract {

    interface View extends BaseView {

        void showTaskName(@NonNull String taskName);

        void showTaskStatus(@NonNull String status);

        void showTaskMembers(@NonNull ArrayList<Member> members);

        void showTaskAddress(@NonNull String address);

        void showTaskTime(@NonNull String time);

        void showInspectCompanyInfo(@Nullable String companyName);

        void showComment(@NonNull String comment);

        void editComment(@NonNull String comment);

        void showActions(@NonNull Task task, @NonNull ProjectInfo project);

        void close();

    }

    interface Presenter extends BasePresenter {

        void startPresent();

        void updateComment(@Nullable String comment);

        void submitComment();

        void changeReserveTime(@Nullable Date date);
    }
}
