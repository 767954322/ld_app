package com.autodesk.shejijia.shared.components.nodeprocess.ui.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.appglobal.ConstructionConstants;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.common.entity.ResponseError;
import com.autodesk.shejijia.shared.components.common.entity.ProjectInfo;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Member;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Plan;
import com.autodesk.shejijia.shared.components.common.entity.microbean.PlanInfo;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Task;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Time;
import com.autodesk.shejijia.shared.components.common.tools.photopicker.MPPhotoPickerActivity;
import com.autodesk.shejijia.shared.components.common.uielements.PickDateDialogFragment;
import com.autodesk.shejijia.shared.components.common.uielements.calanderview.MaterialCalendarView;
import com.autodesk.shejijia.shared.components.common.utility.DateUtil;
import com.autodesk.shejijia.shared.components.common.utility.ToastUtils;
import com.autodesk.shejijia.shared.components.form.ui.activity.FormActivity;
import com.autodesk.shejijia.shared.components.nodeprocess.contract.TaskDetailsContract;
import com.autodesk.shejijia.shared.components.nodeprocess.presenter.TaskDetailsPresenter;
import com.autodesk.shejijia.shared.components.nodeprocess.ui.widgets.calendar.ActiveMileStoneDecorator;
import com.autodesk.shejijia.shared.components.nodeprocess.ui.widgets.calendar.MileStoneDayFormatter;
import com.autodesk.shejijia.shared.components.nodeprocess.ui.widgets.calendar.MileStoneNodeDecorator;
import com.autodesk.shejijia.shared.components.nodeprocess.utility.TaskActionHelper;
import com.autodesk.shejijia.shared.components.nodeprocess.utility.TaskUtils;
import com.autodesk.shejijia.shared.framework.AdskApplication;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
    private LinearLayout mActionsContainer;

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

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AppCompatDialog(getActivity(), R.style.Construction_DialogStyle_Translucent);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case ConstructionConstants.REQUEST_CODE_PICK_DATE:
                if (resultCode ==  Activity.RESULT_OK) {
                    //TODO update reserve time
                    Date selectedDate = (Date) data.getSerializableExtra(PickDateDialogFragment.BUNDLE_KEY_SELECTED_DATE);
                    ToastUtils.showShort(getActivity(), selectedDate.toString());
                }
            default:
                break;
        }
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
    public void showActions(@NonNull final Task task, @NonNull final ProjectInfo projectInfo) {
        List<TaskActionHelper.TaskActionEnum> actions = TaskActionHelper.getInstance().getActions(task);
        for (TaskActionHelper.TaskActionEnum action: actions) {
            TextView actionBtn = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.view_action_button, mActionsContainer, false);
            actionBtn.setText(getActionName(action, task));
            actionBtn.setTag(action);
            actionBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TaskActionHelper.TaskActionEnum attachedAction = (TaskActionHelper.TaskActionEnum) v.getTag();
                    startAction(attachedAction, task, projectInfo);
                }
            });
            mActionsContainer.addView(actionBtn);
        }

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
        mActionsContainer = (LinearLayout) view.findViewById(R.id.actions_container);
    }

    private void initEvent(){
        mCloseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private String getActionName(TaskActionHelper.TaskActionEnum taskActionEnum, Task task) {
        switch (taskActionEnum) {
            case ADD_REVERSE_TIME:
                return getString(R.string.add_reserve_time);
            case UPDATE_REVERSE_TIME:
                return getString(R.string.modify_reserve_time);
            case FILL_FORM:
                return String.format(getString(R.string.fill_inspection_form), getInspectionName(task));
            case VIEW_FORM:
                return String.format(getString(R.string.view_inspection_form), getInspectionName(task));
            case UPDATE_FORM:
                return String.format(getString(R.string.update_inspection_form), getInspectionName(task));
            case MARK_COMPLETE:
                return getString(R.string.mark_complete);
            case ADD_REINSPECTION_TIME:
                return getString(R.string.add_reinspection_time);
            case UPDATE_REINSPECTION_TIME:
                return getString(R.string.modify_reinspection_time);
            default:
                return "";
        }
    }

    private String getInspectionName(Task task) {
        switch (task.getCategory()) {
            case ConstructionConstants.TaskCategory.MATERIAL_INSTALLATION:
                return getString(R.string.material_inspection);
            default:
                return task.getName();
        }
    }

    private void startAction(TaskActionHelper.TaskActionEnum taskActionEnum, Task task, ProjectInfo projectInfo) {
        switch (taskActionEnum) {
            case FILL_FORM:
            case UPDATE_FORM:
                fillForm(task);
                break;
            case VIEW_FORM:
                viewForm(task);
                break;
            case MARK_COMPLETE:
                markComplete(task);
                break;
            case ADD_REVERSE_TIME:
            case ADD_REINSPECTION_TIME:
            case UPDATE_REVERSE_TIME:
            case UPDATE_REINSPECTION_TIME:
                selectDate(task, projectInfo);
                break;
            default:
                break;
        }
    }

    private void fillForm(Task task) {
        // TODO Entry of form
        Intent intent = new Intent(getActivity(), FormActivity.class);
        intent.putExtra("task", task);
        getActivity().startActivity(intent);
    }

    private void viewForm(Task task) {
        // TODO Entry of form
        Intent intent = new Intent(getActivity(), FormActivity.class);
        intent.putExtra("task", task);
        getActivity().startActivity(intent);
    }

    private void markComplete(Task task) {
        //TODO mark complete
        ToastUtils.showShort(getActivity(), "Mark complete");
        uploadPhoto(task);
    }

    private void uploadPhoto(Task task) {
        //TODO navigate to common component
        Intent intent = new Intent(getActivity(), MPPhotoPickerActivity.class);
//      intent.putExtra(MPPhotoPickerActivity.ASSET_ID, mAssetId);

        MemberEntity memberEntity = AdskApplication.getInstance().getMemberEntity();
        intent.putExtra(MPPhotoPickerActivity.X_TOKEN, memberEntity.getHs_accesstoken());
        intent.putExtra(MPPhotoPickerActivity.MEMBER_ID, memberEntity.getAcs_member_id());

        getActivity().startActivityForResult(intent, 0x0105);
    }

    private void selectDate(Task task, ProjectInfo projectInfo) {
        PickDateDialogFragment.Builder builder = TaskUtils.getPickDateDialogBuilder(task, projectInfo);
        builder.setSelectionMode(MaterialCalendarView.SELECTION_MODE_SINGLE);
        PickDateDialogFragment dialogFragment = builder.create();
        dialogFragment.show(getChildFragmentManager(), "pick_date");
    }
}
