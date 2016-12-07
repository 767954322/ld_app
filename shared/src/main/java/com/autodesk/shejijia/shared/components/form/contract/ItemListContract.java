package com.autodesk.shejijia.shared.components.form.contract;

import com.autodesk.shejijia.shared.components.form.common.entity.OptionCell;
import com.autodesk.shejijia.shared.components.form.common.entity.categoryForm.SHInspectionForm;
import com.autodesk.shejijia.shared.components.form.common.entity.microBean.CheckItem;
import com.autodesk.shejijia.shared.framework.BasePresenter;
import com.autodesk.shejijia.shared.framework.BaseView;

import java.util.List;

/**
 * Created by t_aij on 16/11/30.
 */

public interface ItemListContract {

    interface View extends BaseView {

    }

    interface Presenter extends BasePresenter {

        List<OptionCell> getOptionCells(String title, SHInspectionForm shInspectionForm);

        void setCheckIndex(CheckItem checkItem, int type);
    }


}
