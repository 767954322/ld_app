package com.autodesk.shejijia.shared.components.form.ui.fragment;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Form;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Task;
import com.autodesk.shejijia.shared.framework.fragment.BaseConstructionFragment;

import java.util.List;

/**
 * Created by t_aij on 16/11/17.
 */

public class FormListFragment extends BaseConstructionFragment {
    private static Task mTask;
    private LinearLayout mFormListLayout;

    public static FormListFragment newInstance(Task task) {
        FormListFragment formListfragment = new FormListFragment();
        Bundle args = new Bundle();
        args.putSerializable("task",task);
        formListfragment.setArguments(args);

        return formListfragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_form_list;
    }

    @Override
    protected void initView() {
        mFormListLayout = (LinearLayout) rootView.findViewById(R.id.ll_form_list);
    }

    @Override
    protected void initData() {
        mTask = (Task) getArguments().getSerializable("task");
        List<Form> forms = mTask.getForms();
        for (Form form : forms) {
            if("inspection".equals(form.getCategory())) {
                TextView textView = new TextView(getContext());
                textView.setText(form.getCategory());
                mFormListLayout.addView(textView);
            }
        }

    }
}
