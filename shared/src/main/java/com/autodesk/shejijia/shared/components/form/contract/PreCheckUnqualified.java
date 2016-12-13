package com.autodesk.shejijia.shared.components.form.contract;

import com.autodesk.shejijia.shared.components.common.uielements.commentview.model.entity.ImageInfo;
import com.autodesk.shejijia.shared.components.form.common.entity.categoryForm.SHPrecheckForm;
import com.autodesk.shejijia.shared.framework.BasePresenter;
import com.autodesk.shejijia.shared.framework.BaseView;

import java.util.List;

/**
 * Created by t_panya on 16/12/12.
 */

public interface PreCheckUnqualified {
    interface View extends BaseView{
        void showDialog(String message);

    }

    interface Presenter extends BasePresenter {
        void nextStep(List<ImageInfo> mPictures, String mediaType);

        void setCheckIndex(SHPrecheckForm mPreCheckForm, boolean checked, int id);

        void onButtonClick(int id);
    }
}
