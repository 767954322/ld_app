package com.autodesk.shejijia.shared.components.form.contract;

import com.autodesk.shejijia.shared.components.form.common.entity.OptionCell;
import com.autodesk.shejijia.shared.framework.BasePresenter;
import com.autodesk.shejijia.shared.framework.BaseView;

import java.util.List;

/**
 * Created by t_aij on 16/12/15.
 */

public interface MutableItemContract {

    interface View extends BaseView {
        void initOptionCellList(List<OptionCell> optionCellList);
    }

    interface Presenter extends BasePresenter {
        void setCheckItemIndex(int type, int position);
    }


}
