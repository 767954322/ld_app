package com.autodesk.shejijia.shared.components.form.contract;

import com.autodesk.shejijia.shared.components.form.common.entity.ItemCell;
import com.autodesk.shejijia.shared.components.form.common.entity.SHForm;
import com.autodesk.shejijia.shared.components.form.common.entity.categoryForm.SHPrecheckForm;
import com.autodesk.shejijia.shared.framework.BasePresenter;
import com.autodesk.shejijia.shared.framework.BaseView;

import java.util.List;
import java.util.Map;

/**
 * Created by t_aij on 16/11/22.
 */

public interface FormListContract {

    interface View extends BaseView {

        void initItemCellList(List<ItemCell> itemCellList);

        void enterFormItem(SHForm shInspectionForm);

        void refreshReinspection(Map<String, List<String>> reinspectionMap);

        void refreshRectification(Map<String, List<String>> rectificationMap);

        void showSubmitBtn();

        void SubmitSuccess();
    }


    interface Presenter extends BasePresenter {

        void showFormItemList(int position);

        void refreshData(List<ItemCell> itemCellList);

        void submitData(SHPrecheckForm precheckForm);
    }
}
