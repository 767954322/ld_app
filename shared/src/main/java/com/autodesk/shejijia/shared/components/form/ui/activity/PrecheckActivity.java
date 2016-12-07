package com.autodesk.shejijia.shared.components.form.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.entity.ResponseError;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Task;
import com.autodesk.shejijia.shared.components.common.utility.DividerItemDecoration;
import com.autodesk.shejijia.shared.components.common.utility.ScreenUtil;
import com.autodesk.shejijia.shared.components.common.utility.ToastUtils;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.components.form.common.entity.ItemCell;
import com.autodesk.shejijia.shared.components.form.common.entity.categoryForm.SHPrecheckForm;
import com.autodesk.shejijia.shared.components.form.common.entity.microBean.FormFeedBack;
import com.autodesk.shejijia.shared.components.form.contract.PrecheckContract;
import com.autodesk.shejijia.shared.components.form.presenter.PrecheckPresenter;
import com.autodesk.shejijia.shared.components.form.ui.adapter.FormListAdapter;
import com.autodesk.shejijia.shared.framework.activity.BaseActivity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by t_aij on 16/10/25.
 */

public class PrecheckActivity extends BaseActivity implements View.OnClickListener, PrecheckContract.View, FormListAdapter.OnItemClickListener {

    private RadioButton mOkBtn;
    private RadioButton mNoBtn;
    private LinearLayout mNecessaryLayout;
    private RecyclerView mAdditionalRv;
    private Button mOptionBtn;
    private PrecheckPresenter mPresenter;
    private FormListAdapter mAdapter;
    private List<ItemCell> mItemCellList = new ArrayList<>();
    private List<FormFeedBack> mFormFeedBack = new ArrayList<>();

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_precheck;
    }

    @Override
    protected void initView() {
        mOkBtn = (RadioButton) findViewById(R.id.btn_ok);
        mNoBtn = (RadioButton) findViewById(R.id.btn_no);
        mNecessaryLayout = (LinearLayout) findViewById(R.id.ll_necessary);
        mAdditionalRv = (RecyclerView) findViewById(R.id.rv_additional);
        mOptionBtn = (Button) findViewById(R.id.btn_option);
    }

    @Override
    protected void initExtraBundle() {
        Task task = (Task) getIntent().getSerializableExtra("task");
        initToolbar(task);

        mAdditionalRv.setLayoutManager(new LinearLayoutManager(this));
        mAdditionalRv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        mAdditionalRv.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new FormListAdapter(mItemCellList, this);
        mAdditionalRv.setAdapter(mAdapter);

        mPresenter = new PrecheckPresenter(this, this);
        mPresenter.showForm(task);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initListener() {
        mOkBtn.setOnClickListener(this);
        mNoBtn.setOnClickListener(this);
        mOptionBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();

        if (i == R.id.btn_ok) {
            mPresenter.showQualifiedBtn();
        } else if (i == R.id.btn_no) {
            mPresenter.showUnqualifiedBtn();
        } else if (i == R.id.btn_option) {
            mPresenter.clickOptionBtn();
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        TextView resultTv = (TextView) view.findViewById(R.id.tv_result);
        setResult(resultTv, mFormFeedBack.get(position));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showNetError(ResponseError msg) {

    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void addNecessaryView(TextView view) {
        if (null != mNecessaryLayout) {
            mNecessaryLayout.addView(view);
        }
    }

    @Override
    public void addAdditionalData(Map<String, FormFeedBack> formFeedBackMap) {
        Iterator<String> iterator = formFeedBackMap.keySet().iterator();

        while (iterator.hasNext()) {
            ItemCell itemCell = new ItemCell();
            itemCell.setTitle(iterator.next());
            itemCell.setResult(UIUtils.getString(R.string.yes));
            mItemCellList.add(itemCell);
        }

        mAdapter.notifyDataSetChanged();

        mFormFeedBack.addAll(formFeedBackMap.values());
    }

    @Override
    public void showQualifiedBtn() {
        if (null != mOptionBtn && null != mOkBtn) {
            mOptionBtn.setVisibility(View.VISIBLE);
            mOptionBtn.setText(R.string.form_inspect_agree);
            mOptionBtn.setBackgroundResource(R.drawable.ic_big_button_blue);

            mOkBtn.setTextColor(UIUtils.getColor(R.color.con_white));
            mNoBtn.setTextColor(UIUtils.getColor(R.color.form_btn_bg_grey));
        }
    }

    @Override
    public void showUnqualifiedBtn() {
        if (null != mOptionBtn && null != mNoBtn) {
            mOptionBtn.setVisibility(View.VISIBLE);
            mOptionBtn.setText(R.string.form_inspect_disagree);
            mOptionBtn.setBackgroundResource(R.drawable.ic_big_button_orange);

            mNoBtn.setTextColor(UIUtils.getColor(R.color.con_white));
            mOkBtn.setTextColor(UIUtils.getColor(R.color.form_btn_bg_grey));
        }
    }

    @Override
    public void enterQualified(Task task, SHPrecheckForm shPrecheckForm) {
        // TODO: 16/11/18 数据还未保存,需要将数据保存再内存中,task提供各种表单的id,后者保存了辅助条件的信息
        Intent intent = new Intent(this, FormActivity.class);
        intent.putExtra("task", task);
        intent.putExtra("shPrecheckForm", shPrecheckForm);
        startActivity(intent);
        finish();

    }

    @Override
    public void enterUnqualified(SHPrecheckForm shPrecheckForm) {
        // TODO: 16/11/18 数据还未保存,需要将数据保存再内存中,
        ToastUtils.showShort(this, "另外再开启一个activity来处理验收条件不合格的情况");
    }

    private void initToolbar(Task task) {
        ActionBar actionBar = getSupportActionBar();
        if (null != actionBar) {
            actionBar.setTitle(task.getName());
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setResult(final TextView resultTv, final FormFeedBack formFeedBack) {

        View optionView = LayoutInflater.from(this).inflate(R.layout.view_option_precheck, null);
        TextView yesTv = (TextView) optionView.findViewById(R.id.tv_yes);
        TextView noTv = (TextView) optionView.findViewById(R.id.tv_no);
        int v1 = ScreenUtil.dip2px(57.5f);
        int y1 = ScreenUtil.dip2px(16f);

        final PopupWindow popupWindow = new PopupWindow(optionView, ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
        popupWindow.showAsDropDown(resultTv, -y1, -v1);

        yesTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultTv.setText(UIUtils.getString(R.string.yes));
                formFeedBack.setCurrentCheckIndex(0);
                popupWindow.dismiss();
            }
        });
        noTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultTv.setText(UIUtils.getString(R.string.no));
                formFeedBack.setCurrentCheckIndex(1);
                popupWindow.dismiss();
            }
        });
    }
}
