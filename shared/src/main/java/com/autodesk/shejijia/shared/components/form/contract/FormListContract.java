package com.autodesk.shejijia.shared.components.form.contract;

import com.autodesk.shejijia.shared.components.form.common.entity.categoryForm.SHInspectionForm;
import com.autodesk.shejijia.shared.framework.BasePresenter;
import com.autodesk.shejijia.shared.framework.BaseView;

/**
 * Created by t_aij on 16/11/22.
 */

public interface FormListContract {

    interface View extends BaseView{

//        void setFormTitle(List<String> titleList);

        void enterFormItem(SHInspectionForm shInspectionForm);
    }


    interface Presenter extends BasePresenter{

//        void showFormList(Task task);

        void showFormItemList(int position);
    }
}
