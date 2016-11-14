package com.autodesk.shejijia.shared.components.nodeprocess.ui.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Task;
import com.autodesk.shejijia.shared.components.nodeprocess.contract.TaskDetailsContract;
import com.autodesk.shejijia.shared.components.nodeprocess.presenter.TaskDetailsPresenter;

/**
 * Created by t_xuz on 11/11/16.
 * 节点详情对话框
 */

public class TaskDetailsFragment extends DialogFragment implements TaskDetailsContract.View {

    private ImageButton mCloseBtn;
    private TextView mTaskName;
    private TextView mTaskStatus;
    private TextView mTaskDate;
    private TextView mTaskAddress;
    private RecyclerView mTaskMemberListView;
    private TextView mTaskPhone;
    private TextView mNavigateFrom;
    private TextView mNavigateTime;
    private TextInputEditText mEditRemark;
    private TaskDetailsContract.Presenter mTaskDetailsPresenter;

    public static TaskDetailsFragment newInstance(Bundle taskInfoBundle) {
        TaskDetailsFragment taskDetailsFragment = new TaskDetailsFragment();
        taskDetailsFragment.setArguments(taskInfoBundle);
        return taskDetailsFragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTaskDetailsPresenter = new TaskDetailsPresenter(this);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.Construction_AlertDialogStyle_Translucent);
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_task_details_dialog, null);

        initView(view);

        updateViewsData();

        initEvent();

        return builder.setView(view).create();

    }

    private void initView(View view) {
        mCloseBtn = (ImageButton) view.findViewById(R.id.imgBtn_close);
        mTaskName = (TextView) view.findViewById(R.id.tv_task_name);
        mTaskStatus = (TextView) view.findViewById(R.id.tv_task_status);
        mTaskDate = (TextView) view.findViewById(R.id.tv_task_date);
        mTaskAddress = (TextView) view.findViewById(R.id.tv_task_address);
        mTaskMemberListView = (RecyclerView) view.findViewById(R.id.rcy_task_person_list);
        mTaskPhone = (TextView) view.findViewById(R.id.tv_task_phone);
        mNavigateFrom = (TextView) view.findViewById(R.id.tv_navigate_form);
        mNavigateTime = (TextView) view.findViewById(R.id.tv_navigate_time);
        mEditRemark = (TextInputEditText) view.findViewById(R.id.edt_task_remark);
    }

    private void updateViewsData() {
        if (getArguments() != null){
            Task taskInfo = (Task) getArguments().getSerializable("taskInfo");
            if (taskInfo != null) {
                if (!TextUtils.isEmpty(taskInfo.getName())) {
                    mTaskName.setText(taskInfo.getName());
                }
                if (!TextUtils.isEmpty(taskInfo.getStatus())) {
                    mTaskStatus.setText(taskInfo.getStatus());
                }
                if (!TextUtils.isEmpty(taskInfo.getDescription())) {
                    mTaskAddress.setText("");
                }
            }
        }
    }

    private void initEvent(){
        mCloseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
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
}
