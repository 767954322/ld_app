package com.autodesk.shejijia.shared.components.form.presenter;

import com.autodesk.shejijia.shared.components.common.entity.microbean.Form;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Task;
import com.autodesk.shejijia.shared.components.common.utility.LogUtils;
import com.autodesk.shejijia.shared.components.form.contract.PrecheckContract;

import java.util.List;

/**
 * Created by t_aij on 16/10/28.
 */

public class PrecheckPresenter implements PrecheckContract.Presenter {
    private PrecheckContract.View mView;

    public PrecheckPresenter(PrecheckContract.View view) {
        mView = view;
    }


    @Override
    public void showForm(Task task) {
        mView.setToolbarTitle(task.getName());
        List<Form> formList = task.getForms();
        for (Form form : formList) {
            if("precheck".equals(form.getCategory())) {   //查找预检的表格
                // TODO: 16/11/10 待做啊
                String templateId = form.getFormId();
                LogUtils.d("asdf",templateId);
                String[] fids = {templateId};
                // TODO: 16/11/11 获取数据,显示界面
//                FormRepository.getInstance().getRemoteFormItemDetails(new ResponseCallback<List>() {
//                    @Override
//                    public void onSuccess(List data) {
//                        LogUtils.d("asdf",data.toString());
//                    }
//
//                    @Override
//                    public void onError(String errorMsg) {
//
//                    }
//                },fids);


            }
        }
    }
}
