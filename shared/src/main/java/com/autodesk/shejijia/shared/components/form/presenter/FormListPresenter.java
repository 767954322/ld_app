package com.autodesk.shejijia.shared.components.form.presenter;

import com.autodesk.shejijia.shared.components.form.common.entity.ItemCell;
import com.autodesk.shejijia.shared.components.form.common.entity.categoryForm.SHInspectionForm;
import com.autodesk.shejijia.shared.components.form.contract.FormListContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by t_aij on 16/11/22.
 */

public class FormListPresenter implements FormListContract.Presenter {
    private FormListContract.View mView;
    private List<SHInspectionForm> mSHFormList = new ArrayList<>();

    public FormListPresenter(FormListContract.View view, List<SHInspectionForm> shFormList) {
        mView = view;
        mSHFormList.addAll(shFormList);
    }

    @Override
    public void showFormItemList(int position) {
        if (mSHFormList != null && mSHFormList.size() != 0) {
            SHInspectionForm shInspectionForm = mSHFormList.get(position);
            mView.enterFormItem(shInspectionForm);
        }
    }

    @Override
    public List<ItemCell> getItemCells() {
        List<ItemCell> itemCellList = new ArrayList<>();

        if (mSHFormList != null && mSHFormList.size() != 0) {
            for (SHInspectionForm shInspectionForm : mSHFormList) {
                String title = shInspectionForm.getTitle();
                ItemCell itemCell = new ItemCell();
                itemCell.setTitle(title);
                itemCellList.add(itemCell);
            }
        }

        return itemCellList;
    }
}
