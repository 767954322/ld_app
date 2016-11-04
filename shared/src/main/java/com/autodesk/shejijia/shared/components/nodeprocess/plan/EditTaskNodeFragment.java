package com.autodesk.shejijia.shared.components.nodeprocess.plan;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.autodesk.shejijia.enterprise.R;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Task;
import com.autodesk.shejijia.shared.framework.fragment.BaseFragment;

import java.util.List;

/**
 * Created by wenhulin on 11/3/16.
 */

public class EditTaskNodeFragment extends BaseFragment implements EditPlanContract.View{

    public void setPresenter(EditPlanContract.Presenter presenter) {
        // TODO
    }

    @Override
    public void showTasks(List<Task> tasks) {
        // TODO show plan on calendar view
    }

    @Override
    public void bindPresenter(EditPlanContract.Presenter presenter) {

    }

    @Override
    public void showNetError(String msg) {

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
    protected int getLayoutResId() {
        return R.layout.fragment_edit_tasknode;
    }

    @Override
    protected void initView() {
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("第二步: 调整子节点时间");  //TODO localize string
        }
    }

    @Override
    protected void initData() {

    }
}
