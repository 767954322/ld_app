package com.autodesk.shejijia.shared.components.form.contract;

import com.autodesk.shejijia.shared.components.form.common.entity.ItemCell;
import com.autodesk.shejijia.shared.components.form.common.entity.microBean.CheckItem;
import com.autodesk.shejijia.shared.framework.BasePresenter;
import com.autodesk.shejijia.shared.framework.BaseView;

import java.util.List;

/**
 * Created by t_aij on 16/11/29.
 */

public interface FormSubListContract {

    interface View extends BaseView {

        void initItemCellList(List<ItemCell> itemCellList);
    }

    interface Presenter extends BasePresenter {

        int getCategoryIndex(List<CheckItem> checkItemList, String category);  //根据种类,获取到初始化的位置

        void refreshFormData(List<ItemCell> itemCells);
    }
}
