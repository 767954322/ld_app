package com.autodesk.shejijia.shared.components.form.presenter;

import android.content.Context;
import android.widget.TextView;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.entity.ResponseError;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Form;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Task;
import com.autodesk.shejijia.shared.components.common.listener.ResponseCallback;
import com.autodesk.shejijia.shared.components.common.utility.ToastUtils;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.components.form.common.constant.SHFormConstant;
import com.autodesk.shejijia.shared.components.form.common.entity.SHForm;
import com.autodesk.shejijia.shared.components.form.common.entity.categoryForm.SHPrecheckForm;
import com.autodesk.shejijia.shared.components.form.common.entity.microBean.CheckItem;
import com.autodesk.shejijia.shared.components.form.common.entity.microBean.FormFeedBack;
import com.autodesk.shejijia.shared.components.form.contract.PrecheckContract;
import com.autodesk.shejijia.shared.components.form.data.FormRepository;
import com.autodesk.shejijia.shared.components.form.ui.activity.PrecheckActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by t_aij on 16/10/28.
 */

public class PrecheckPresenter implements PrecheckContract.Presenter {
    private PrecheckContract.View mView;
    private Context mContext;
    private int index;  //index表示合格不合格按钮初始化状态,1表示不合格;2表示合格
    private SHPrecheckForm mShForm;
    private Task mTask;


    public PrecheckPresenter(Context context, PrecheckContract.View view) {
        mContext = context;
        mView = view;
    }

    @Override
    public void showForm(Task task) {
        mTask = task;

        List<Form> formList = task.getForms();
        for (Form form : formList) {
            if (SHFormConstant.SHFormCategory.PRECHECK.equals(form.getCategory())) {
                String templateId = form.getFormId();
                List<String> formIds = new ArrayList<>();
                formIds.add(templateId);

                FormRepository.getInstance().setFormList(null);
                FormRepository.getInstance().getRemoteFormItemDetails(new ResponseCallback<List, ResponseError>() {
                    @Override
                    public void onSuccess(List data) {
                        for (SHForm shForm : (List<SHForm>) data) {
                            String category = shForm.getCategory();
                            if (SHFormConstant.SHFormCategory.PRECHECK.equals(category)) {
                                mShForm = (SHPrecheckForm) shForm;
                                setPrecheckForm(mShForm);
                                return;
                            }
                        }
                    }

                    @Override
                    public void onError(ResponseError error) {
                        ToastUtils.showShort((PrecheckActivity) mContext, error.getMessage());
                    }
                }, formIds);

                return;
            }
        }
    }

    @Override
    public void showQualifiedBtn() {
        if (2 != index) {
            mView.showQualifiedBtn();
            index = 2;
        }
    }

    @Override
    public void showUnqualifiedBtn() {
        if (1 != index) {
            mView.showUnqualifiedBtn();
            index = 1;
        }
    }

    @Override
    public void clickOptionBtn() {
        HashMap typeDict = mShForm.getTypeDict();
        Object status = typeDict.get("status");
        if (status instanceof List) {
            List<String> list = (List<String>) status;
            if (1 == index) {   //不合格
                for (int i = 0; i < list.size(); i++) {
                    if ("不合格".equals(list.get(i))) mShForm.setStatus(i);
                }
                mView.enterUnqualified(mShForm);
            } else if (2 == index) {   //合格
                for (int i = 0; i < list.size(); i++) {
                    if ("合格".equals(list.get(i))) mShForm.setStatus(i);
                }
                mView.enterQualified(mTask, mShForm);
            }
        }
    }

    private void setPrecheckForm(SHPrecheckForm form) {
        ArrayList<CheckItem> checkItems = form.getCheckItems();
        int spacing = UIUtils.getDimens(R.dimen.precheck_spacing_padding_7_5);
        int index = 0;
        Map<String, FormFeedBack> formFeedBackMap = new HashMap<>();   //将辅助条件对应到反馈上
        for (CheckItem checkItem : checkItems) {
            String itemCategory = checkItem.getCategory();
            if ("必要条件".equals(itemCategory)) {      //获取子条目
                index++;
                addNecessaryView(index, spacing, checkItem);
            } else if ("辅助条件".equals(itemCategory)) {
                formFeedBackMap.put(checkItem.getTitle(), checkItem.getFormFeedBack());
            }
        }

        mView.addAdditionalData(formFeedBackMap);
    }

    private void addNecessaryView(int index, int spacing, CheckItem checkItem) {
        TextView view = new TextView(mContext);
        view.setPadding(0, spacing, 0, spacing);
        String standard = checkItem.getStandard().replaceAll("是否", "");
        if (index < 10) {
            standard = "0" + String.valueOf(index) + " " + standard;
        } else {
            standard = String.valueOf(index) + " " + standard;
        }
        view.setText(standard);
        view.setTextColor(UIUtils.getColor(R.color.con_font_gray));

        mView.addNecessaryView(view);

    }
}
