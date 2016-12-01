package com.autodesk.shejijia.shared.components.nodeprocess.ui.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.entity.ResponseError;
import com.autodesk.shejijia.shared.components.common.entity.ProjectInfo;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Member;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Task;
import com.autodesk.shejijia.shared.components.nodeprocess.contract.TaskDetailsContract;
import com.autodesk.shejijia.shared.components.nodeprocess.presenter.TaskDetailsPresenter;
import com.autodesk.shejijia.shared.components.nodeprocess.utility.TaskUtils;

import java.util.ArrayList;

/**
 * Created by t_xuz on 11/11/16.
 * 节点详情对话框
 */

public class TaskDetailsFragment extends AppCompatDialogFragment implements TaskDetailsContract.View {
    private static final String BUNDLE_KEY_PROJECT = "project";
    private static final String BUNDLE_KEY_TASK = "task";

    private ViewGroup mHeaderView;
    private TextView mTaskNameView;
    private TextView mTaskStatusView;
    private TextView mTaskDateView;
    private TextView mTaskAddressView;
    private TextView mTaskPhoneView;
    private TextInputEditText mCommentEditView;
    private RecyclerView mTaskMemberListView;
    private ImageButton mCloseBtn;

    private TaskDetailsContract.Presenter mTaskDetailsPresenter;

    public static TaskDetailsFragment newInstance(ProjectInfo projectInfo, Task task) {
        TaskDetailsFragment taskDetailsFragment = new TaskDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(BUNDLE_KEY_PROJECT, projectInfo);
        bundle.putSerializable(BUNDLE_KEY_TASK, task);
        taskDetailsFragment.setArguments(bundle);
        return taskDetailsFragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ProjectInfo projectInfo = (ProjectInfo) getArguments().getSerializable(BUNDLE_KEY_PROJECT);
        Task task = (Task) getArguments().getSerializable(BUNDLE_KEY_TASK);

        mTaskDetailsPresenter = new TaskDetailsPresenter(this, projectInfo, task);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view  = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_task_details_dialog, container, false);
        initView(view);
        initEvent();
        mTaskDetailsPresenter.startPresent();
        return view;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AppCompatDialog(getActivity(), R.style.Construction_DialogStyle_Translucent);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void showNetError(ResponseError error) {

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
    public void showTaskName(@NonNull String taskName) {
        mTaskNameView.setText(taskName);
    }

    @Override
    public void showTaskStatus(@NonNull String status) {
        mTaskStatusView.setText(TaskUtils.getDisplayStatus(status));
        mHeaderView.getBackground().setLevel(TaskUtils.getStatusLevel(status));
    }

    @Override
    public void showTaskMembers(@NonNull ArrayList<Member> members) {
        // TODO
    }

    @Override
    public void showTaskAddress(@NonNull String address) {
        mTaskAddressView.setText(address);
    }

    @Override
    public void showTaskTime(@NonNull String time) {
        mTaskDateView.setText(time);
    }

    @Override
    public void showInspectCompanyInfo(@NonNull String companyName, @NonNull String phoneNumber) {
        mTaskPhoneView.setText(companyName + " " + phoneNumber);
    }

    @Override
    public void showComment(@NonNull String comment) {
        mCommentEditView.setText(comment);
    }

    @Override
    public void editComment(@NonNull String comment) {
        mCommentEditView.setText(comment);
    }

    @Override
    public void showActions(@NonNull Task task) {
        // TODO
    }

    private void initView(View view) {
        mCloseBtn = (ImageButton) view.findViewById(R.id.imgBtn_close);
        mHeaderView = (ViewGroup) view.findViewById(R.id.ll_header);
        mTaskNameView = (TextView) view.findViewById(R.id.tv_task_name);
        mTaskStatusView = (TextView) view.findViewById(R.id.tv_task_status);
        mTaskDateView = (TextView) view.findViewById(R.id.tv_task_date);
        mTaskAddressView = (TextView) view.findViewById(R.id.tv_task_address);
        mTaskMemberListView = (RecyclerView) view.findViewById(R.id.rcy_task_person_list);
        mTaskPhoneView = (TextView) view.findViewById(R.id.tv_task_phone);
        mCommentEditView = (TextInputEditText) view.findViewById(R.id.edt_task_remark);
    }

    private void initEvent(){
        mCloseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
