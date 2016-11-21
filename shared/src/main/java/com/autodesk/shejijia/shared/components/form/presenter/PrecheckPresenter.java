package com.autodesk.shejijia.shared.components.form.presenter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Form;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Task;
import com.autodesk.shejijia.shared.components.common.listener.ResponseCallback;
import com.autodesk.shejijia.shared.components.common.utility.ScreenUtil;
import com.autodesk.shejijia.shared.components.common.utility.ToastUtils;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.components.form.common.entity.SHForm;
import com.autodesk.shejijia.shared.components.form.common.entity.microBean.CheckItem;
import com.autodesk.shejijia.shared.components.form.contract.PrecheckContract;
import com.autodesk.shejijia.shared.components.form.data.FormRepository;
import com.autodesk.shejijia.shared.components.form.ui.activity.PrecheckActivity;

import java.util.ArrayList;
import java.util.List;

import static com.autodesk.shejijia.shared.components.common.utility.UIUtils.getResources;

/**
 * Created by t_aij on 16/10/28.
 */

public class PrecheckPresenter implements PrecheckContract.Presenter {
    private PrecheckContract.View mView;
    private Context mContext;
    private int index;  //index表示合格不合格按钮初始化状态,1表示不合格;2表示合格
    private boolean flag;   //flag表示是否加载辅助条件第一个,false表示不是,true表示是
    private SHForm mForm;
    private Task mTask;

    public PrecheckPresenter(Context context, PrecheckContract.View view) {
        mContext = context;
        mView = view;
    }

    @Override
    public void showForm(Task task) {
        mView.setToolbarTitle(task.getName());
        mTask = task;

        List<Form> formList = task.getForms();
        for (Form form : formList) {
            if ("precheck".equals(form.getCategory())) {   //查找预检的表格
                String templateId = form.getFormId();
                List<String> formIds = new ArrayList<>();
                formIds.add(templateId);
                // TODO: 16/11/11 获取数据,显示界面
                FormRepository.getInstance().getRemoteFormItemDetails(new ResponseCallback<List>() {
                    @Override
                    public void onSuccess(List data) {
                        for (SHForm form : (List<SHForm>) data) {   //可以同时获取多张form
                            String category = form.getCategory();
                            if ("precheck".equals(category)) {   //预检的from
                                mForm = form;
                                findPrecheckForm(form);
                                return; //data中只有一份表单是预检的,所以加载到之后,不需要在循环
                            }
                        }
                    }

                    @Override
                    public void onError(String errorMsg) {
                        ToastUtils.showShort((PrecheckActivity) mContext, errorMsg);
                    }
                }, formIds);

                return;
            }
        }
    }

    private void findPrecheckForm(SHForm form) {
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
        // TODO: 16/11/19 改用ListView展现数据
        View necessaryView = LayoutInflater.from(mContext).inflate(R.layout.view_plain_table_cell, null);
        TextView standardTv = (TextView) necessaryView.findViewById(R.id.tv_title);
        final TextView resultTv = (TextView) necessaryView.findViewById(R.id.tv_result);

        View optionView = LayoutInflater.from(mContext).inflate(R.layout.view_option_precheck, null);
        TextView yesTv = (TextView) optionView.findViewById(R.id.tv_yes);
        TextView noTv = (TextView) optionView.findViewById(R.id.tv_no);

        final PopupWindow popupWindow = new PopupWindow(optionView, ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));

        yesTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultTv.setText(UIUtils.getString(R.string.yes));
                popupWindow.dismiss();
            }
        });
        noTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultTv.setText(UIUtils.getString(R.string.no));
                popupWindow.dismiss();
            }
        });

        standardTv.setText(checkItem.getStandard());
        standardTv.setTextColor(UIUtils.getColor(R.color.font_title));
        resultTv.setText(UIUtils.getString(R.string.yes));
        resultTv.setTextColor(UIUtils.getColor(R.color.font_subtitle));

        final int v1 = ScreenUtil.dip2px(57.5f);
        final int y1 = ScreenUtil.dip2px(16);

        resultTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.showAsDropDown(v, -y1, -v1);
            }
        });

        if (!flag) {
            flag = true;
        } else {
            View lineView = new View(mContext);
            lineView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ScreenUtil.dip2px(1)));
            lineView.setBackgroundResource(R.color.form_line_grey);
            mView.addAdditionalLayout(lineView);
        }


        mView.addAdditionalLayout(necessaryView);

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
            mView.enterUnqualified(mForm);
        } else if (2 == index) {
            mView.enterQualified(mTask);
        }
    }
}
