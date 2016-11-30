package com.autodesk.shejijia.shared.components.form.contract;

import com.autodesk.shejijia.shared.components.form.common.entity.OptionCell;
import com.autodesk.shejijia.shared.components.form.common.entity.microBean.CheckItem;
import com.autodesk.shejijia.shared.framework.BasePresenter;
import com.autodesk.shejijia.shared.framework.BaseView;

import java.util.List;

/**
 * Created by t_aij on 16/11/30.
 */

public interface ItemListContract {

    interface View extends BaseView {

        void initOptionCellList(List<OptionCell> optionCellList);
    }

    interface Presenter extends BasePresenter {

        void setCheckIndex(CheckItem checkItem, int type);


    }


}
