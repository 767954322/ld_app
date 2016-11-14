package com.autodesk.shejijia.shared.components.form.presenter;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.autodesk.shejijia.shared.components.common.entity.microbean.Form;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Task;
import com.autodesk.shejijia.shared.components.common.listener.ResponseCallback;
import com.autodesk.shejijia.shared.components.common.utility.LogUtils;
import com.autodesk.shejijia.shared.components.form.common.entity.ContainedForm;
import com.autodesk.shejijia.shared.components.form.common.entity.microBean.CheckItem;
import com.autodesk.shejijia.shared.components.form.contract.PrecheckContract;
import com.autodesk.shejijia.shared.components.form.data.FormRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by t_aij on 16/10/28.
 */

public class PrecheckPresenter implements PrecheckContract.Presenter {
    private PrecheckContract.View mView;
    private Context mContext;

    public PrecheckPresenter(Context context,PrecheckContract.View view) {
        mContext = context;
        mView = view;
    }


    @Override
    public void showForm(Task task) {
        mView.setToolbarTitle(task.getName());
        List<Form> formList = task.getForms();
        for (Form form : formList) {
            if ("precheck".equals(form.getCategory())) {   //查找预检的表格
                // TODO: 16/11/10 待做啊
                String templateId = form.getFormId();
                String[] fids = {templateId};
                // TODO: 16/11/11 获取数据,显示界面
                FormRepository.getInstance().getRemoteFormItemDetails(new ResponseCallback<List>() {
                    @Override
                    public void onSuccess(List data) {
                        for (ContainedForm form : (List<ContainedForm>) data) {   //可以同时获取多张form
                            String category = form.getCategory();
                            if ("precheck".equals(category)) {   //预检的from
                                ArrayList<CheckItem> checkItems = form.getCheckItems();
                                for (CheckItem checkItem : checkItems) {
                                    String itemCategory = checkItem.getCategory();
                                    if("必要条件".equals(itemCategory)) {      //获取子条目
                                        LinearLayout necessaryLayout = mView.getNecessaryLayout();
                                        TextView view = new TextView(mContext);
                                        String standard = checkItem.getStandard();
                                        if (standard.contains("是否")) {
                                            standard = standard.replaceAll("是否","");
                                            LogUtils.d("asdf",standard);
                                        }
                                        view.setText(standard);
                                        necessaryLayout.addView(view);

                                    } else if("辅助条件".equals(itemCategory)) {
                                        LinearLayout additionalLayout = mView.getAdditionalLayout();
                                        TextView view = new TextView(mContext);
                                        view.setText(checkItem.getStandard());
                                        additionalLayout.addView(view);

                                    } else if("必要条件".equals(itemCategory)) {

                                    }
                                }

                            }

                        }
                    }

                    @Override
                    public void onError(String errorMsg) {
                        LogUtils.d("asdf",errorMsg);
                    }
                }, fids);


            }
        }
    }
}
