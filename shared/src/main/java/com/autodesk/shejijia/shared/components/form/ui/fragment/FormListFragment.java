package com.autodesk.shejijia.shared.components.form.ui.fragment;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Task;
import com.autodesk.shejijia.shared.components.common.utility.DividerItemDecoration;
import com.autodesk.shejijia.shared.components.common.utility.ToastUtils;
import com.autodesk.shejijia.shared.components.form.common.constant.SHFormConstant;
import com.autodesk.shejijia.shared.components.form.common.entity.ItemCell;
import com.autodesk.shejijia.shared.components.form.common.entity.categoryForm.SHInspectionForm;
import com.autodesk.shejijia.shared.components.form.common.entity.categoryForm.SHPrecheckForm;
import com.autodesk.shejijia.shared.components.form.contract.FormListContract;
import com.autodesk.shejijia.shared.components.form.presenter.FormListPresenter;
import com.autodesk.shejijia.shared.components.form.ui.adapter.FormListAdapter;
import com.autodesk.shejijia.shared.framework.fragment.BaseConstructionFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by t_aij on 16/11/17.
 */

public class FormListFragment extends BaseConstructionFragment implements FormListContract.View, View.OnClickListener, FormListAdapter.OnItemClickListener {
    private RecyclerView mRecyclerView;        //表单
    private TextView mProblemTitleTv;          //问题记录
    private LinearLayout mRectificationLayout; //监督整改
    private LinearLayout mReinspectionLayout;  //强制复验
    private TextView mResultTv;                //结果

    private FormListPresenter mPresenter;
    private FormListAdapter mAdapter;
    private List<ItemCell> mItemCellList = new ArrayList<>();
    private String mTitle;

    public static FormListFragment newInstance(Bundle args) {
        FormListFragment formListfragment = new FormListFragment();
        formListfragment.setArguments(args);
        return formListfragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_form_list;
    }

    @Override
    protected void initView() {
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.rv_form_list);
        mProblemTitleTv = (TextView) rootView.findViewById(R.id.tv_problem_title);
        mRectificationLayout = (LinearLayout) rootView.findViewById(R.id.ll_rectification_log);
        mReinspectionLayout = (LinearLayout) rootView.findViewById(R.id.ll_reinspection_log);
        mResultTv = (TextView) rootView.findViewById(R.id.tv_result);
    }

    @Override
    protected void initData() {
        Bundle bundle = getArguments();
        Task task = (Task) bundle.getSerializable("task");
        // TODO: 16/12/7 验收条件满足的表格数据
        SHPrecheckForm precheckForm = (SHPrecheckForm) bundle.getSerializable("precheckForm");
        mTitle = task.getName();
        initToolbar(mTitle);

        mPresenter = new FormListPresenter(this, task);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL_LIST));
        if(mAdapter == null) {
            mAdapter = new FormListAdapter(mItemCellList, this);
        }
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    protected void initListener() {
        rootView.findViewById(R.id.btn_submit).setOnClickListener(this);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            // TODO: 16/11/28 如何该界面显示
            mPresenter.refreshData(mItemCellList);
            mAdapter.notifyDataSetChanged();
            initToolbar(mTitle);
        }
    }

    @Override
    public void initItemCellList(List<ItemCell> itemCellList) {
        mItemCellList.clear();
        mItemCellList.addAll(itemCellList);

        if(mAdapter == null) {
            mAdapter = new FormListAdapter(mItemCellList, this);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void enterFormItem(SHInspectionForm shInspectionForm) {
        // TODO: 16/11/28 进入到第二个fragment的判断,第一次进来,还是第二次显示出来
        Bundle args = new Bundle();
        args.putSerializable("shInspectionForm", shInspectionForm);  //将具体的表格传递进去
        FormSubListFragment formSubListFragment = FormSubListFragment.newInstance(args);

        mContext.getSupportFragmentManager().beginTransaction()
                .hide(mContext.getSupportFragmentManager().findFragmentByTag(SHFormConstant.FragmentTag.FORM_LIST_FRAGMENT))
                .add(R.id.fl_main_container, formSubListFragment, SHFormConstant.FragmentTag.FORM_SUBLIST_FRAGMENT)
                .addToBackStack(null)
                .commit();

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (R.id.btn_submit == id) {
            ToastUtils.showShort(mContext, "弹框,确定是否确定保存");
        }

    }

    @Override
    public void onItemClick(View view, int position) {
        mPresenter.showFormItemList(position);
    }

    private void initToolbar(String title) {
        ActionBar actionBar = mContext.getSupportActionBar();
        actionBar.setTitle(title);
    }
}
