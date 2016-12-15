package com.autodesk.shejijia.shared.components.form.contract;

import com.autodesk.shejijia.shared.components.form.common.entity.ItemCell;
import com.autodesk.shejijia.shared.framework.BasePresenter;
import com.autodesk.shejijia.shared.framework.BaseView;

import java.util.List;

/**
 * Created by t_aij on 16/12/15.
 */

public interface ImmutableItemsContract {

    interface View extends BaseView {

        void initItemCellList(List<ItemCell> itemCellList);
    }

    interface Presenter extends BasePresenter {

    }

}
