package com.autodesk.shejijia.shared.components.form.presenter;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Form;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Task;
import com.autodesk.shejijia.shared.components.common.listener.ResponseCallback;
import com.autodesk.shejijia.shared.components.common.utility.LogUtils;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
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
    private int index = 0;  //flag表示合格不合格按钮初始化状态,1表示不合格;2表示合格

    public PrecheckPresenter(Context context, PrecheckContract.View view) {
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
                                int spacing = UIUtils.getDimens(R.dimen.precheck_spacing_padding_7_5);
                                int index = 0;
                                for (CheckItem checkItem : checkItems) {
                                    String itemCategory = checkItem.getCategory();
                                    if ("必要条件".equals(itemCategory)) {      //获取子条目
                                        index++;
                                        addNecessaryView(index, spacing, checkItem);

                                    } else if ("辅助条件".equals(itemCategory)) {
                                        addAdditionalLayout(checkItem);

                                    }
                                }

                                return; //data中只有一份表单是预检的,所以加载到之后,不需要在循环
                            }

                        }
                    }

                    @Override
                    public void onError(String errorMsg) {
                        LogUtils.d("asdf", errorMsg);
                    }
                }, fids);


            }
        }
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

    private void addAdditionalLayout(CheckItem checkItem) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.item_plain_table_cell, null);
        TextView standardTv = (TextView) view.findViewById(R.id.tv_title);
        final TextView resultTv = (TextView) view.findViewById(R.id.tv_result);
        standardTv.setText(checkItem.getStandard());
        standardTv.setTextColor(UIUtils.getColor(R.color.con_font_black));
        resultTv.setText("是");

        view.findViewById(R.id.imgBtn_option).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(mContext, v);
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.prechck_additional_option_menu, popup.getMenu());
                popup.show();
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int i = item.getItemId();
                        if (i == R.id.yes) {
                            resultTv.setText("是");
                        } else if (i == R.id.no) {
                            resultTv.setText("否");
                        }

                        return true;
                    }
                });

            }
        });

        mView.addAdditionalLayout(view);
    }

    @Override
    public void showOkBtn() {
        if (2 != index) {
            mView.showQualifiedBtn();
            index = 2;
        }
    }

    @Override
    public void showNoBtn() {
        if (1 != index) {
            mView.showUnqualifiedBtn();
            index = 1;
        }
    }

    @Override
    public void clickOptionBtn() {
        if (1 == index) {
            mView.enterUnqualified();
        } else if (2 == index) {
            mView.enterQualified();
        }
    }
}
