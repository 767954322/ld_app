package com.autodesk.shejijia.shared.components.form.contract;

import com.autodesk.shejijia.shared.components.form.common.entity.ItemCell;
import com.autodesk.shejijia.shared.components.form.common.entity.categoryForm.SHInspectionForm;
import com.autodesk.shejijia.shared.framework.BasePresenter;
import com.autodesk.shejijia.shared.framework.BaseView;

import java.util.List;

/**
 * Created by t_aij on 16/11/22.
 */

public interface FormListContract {

    interface View extends BaseView {

        void initItemCellList(List<ItemCell> itemCellList);

        void enterFormItem(SHInspectionForm shInspectionForm);
    }


    interface Presenter extends BasePresenter {

        void showFormItemList(int position);

        void refreshData(List<ItemCell> itemCellList);
    }
}
