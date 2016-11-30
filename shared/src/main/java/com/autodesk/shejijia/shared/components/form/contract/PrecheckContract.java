package com.autodesk.shejijia.shared.components.form.contract;

import android.widget.TextView;

import com.autodesk.shejijia.shared.components.common.entity.microbean.Task;
import com.autodesk.shejijia.shared.components.form.common.entity.categoryForm.SHPrecheckForm;
import com.autodesk.shejijia.shared.components.form.common.entity.microBean.FormFeedBack;
import com.autodesk.shejijia.shared.framework.BasePresenter;
import com.autodesk.shejijia.shared.framework.BaseView;

import java.util.Map;

/**
 * Created by t_aij on 16/10/28.
 */

public interface PrecheckContract {

    interface View extends BaseView {

        void addNecessaryView(TextView view);

        void addAdditionalData(Map<String, FormFeedBack> formFeedBackMap);

        void showQualifiedBtn();

        void showUnqualifiedBtn();

        void enterQualified(Task task, SHPrecheckForm shPrecheckForm);

        void enterUnqualified(SHPrecheckForm shPrecheckForm);


    }

    interface Presenter extends BasePresenter {

        void showForm(Task task);

        void showQualifiedBtn();

        void showUnqualifiedBtn();

        void clickOptionBtn();
    }


}
