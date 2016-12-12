package com.autodesk.shejijia.shared.components.nodeprocess.ui.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.appglobal.ConstructionConstants;
import com.autodesk.shejijia.shared.components.common.entity.ProjectInfo;
import com.autodesk.shejijia.shared.components.common.entity.ResponseError;
import com.autodesk.shejijia.shared.components.common.entity.microbean.File;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Member;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Task;
import com.autodesk.shejijia.shared.components.common.uielements.PickDateDialogFragment;
import com.autodesk.shejijia.shared.components.common.uielements.calanderview.MaterialCalendarView;
import com.autodesk.shejijia.shared.components.common.uielements.commentview.comment.CommentFragment;
import com.autodesk.shejijia.shared.components.common.uielements.commentview.comment.CommentPreviewActivity;
import com.autodesk.shejijia.shared.components.common.utility.ImageUtils;
import com.autodesk.shejijia.shared.components.form.ui.activity.FormActivity;
import com.autodesk.shejijia.shared.components.nodeprocess.contract.TaskDetailsContract;
import com.autodesk.shejijia.shared.components.nodeprocess.presenter.TaskDetailsPresenter;
import com.autodesk.shejijia.shared.components.nodeprocess.ui.activity.UploadPhotoActivity;
import com.autodesk.shejijia.shared.components.nodeprocess.utility.DialogHelper;
import com.autodesk.shejijia.shared.components.nodeprocess.utility.TaskActionHelper;
import com.autodesk.shejijia.shared.components.nodeprocess.utility.TaskUtils;

import java.util.ArrayList;
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
    private ViewGroup mTaskMembersContainer;
    private GridLayout mTaskPhotosContainer;
    private LinearLayout mActionsContainer;
    private ImageButton mCloseBtn;

    private DialogHelper mDialogHelper;

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
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mDialogHelper = new DialogHelper(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mDialogHelper = null;
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
        setCancelable(false);
        return view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AppCompatDialog(getActivity(), R.style.Construction_DialogStyle_Translucent);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        mTaskDetailsPresenter.startPresent();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mTaskDetailsPresenter.startPresent();
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        super.show(manager, tag);
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
                    Date selectedDate = (Date) data.getSerializableExtra(PickDateDialogFragment.BUNDLE_KEY_SELECTED_DATE);
                    mTaskDetailsPresenter.changeReserveTime(selectedDate);
                }
            default:
                break;
        }
    }

    @Override
    public void showNetError(ResponseError error) {
        if (mDialogHelper != null) {
            mDialogHelper.showNetError(error);
        }
    }

    @Override
    public void showError(String msg) {
        if (mDialogHelper != null) {
            mDialogHelper.showError(msg);
        }
    }

    @Override
    public void showLoading() {
        if (mDialogHelper != null) {
            mDialogHelper.showLoading();
        }
    }

    @Override
    public void hideLoading() {
        if (mDialogHelper != null) {
            mDialogHelper.hideLoading();
        }
    }

    @Override
    public void showUploading() {
        if (mDialogHelper != null) {
            mDialogHelper.showUpLoading();
        }
    }

    @Override
    public void hideUploading() {
        if (mDialogHelper != null) {
            mDialogHelper.hideUpLoading();
        }
    }

    @Override
    public void showError(@NonNull String error, @NonNull DialogInterface.OnClickListener positiveClickListener, @NonNull DialogInterface.OnClickListener negativeClickListener) {
        if (mDialogHelper != null) {
            mDialogHelper.showError(error, positiveClickListener, negativeClickListener);
        }
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
        for (Member member : members) {
            ImageView avatarImageView = (ImageView) LayoutInflater.from(getContext()).inflate(R.layout.view_task_member, mTaskMembersContainer, false);

            String imageFile = member.getProfile().getAvatar();
            if (ConstructionConstants.MemberType.INSPECTOR_COMPANY.equalsIgnoreCase(member.getRole())
                    || ConstructionConstants.MemberType.INSPECTOR.equalsIgnoreCase(member.getRole())) {
                avatarImageView.setImageResource(R.drawable.ic_default_avatar_inspector);
            } else if (TextUtils.isEmpty(imageFile)) {
                avatarImageView.setImageResource(R.drawable.ic_default_head);
            } else {
                ImageUtils.loadUserAvatar(avatarImageView, imageFile);
            }
            mTaskMembersContainer.addView(avatarImageView);
        }

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
    public void showInspectCompanyInfo(@Nullable String companyName) {
        if (TextUtils.isEmpty(companyName)) {
            mTaskPhoneView.setVisibility(View.GONE);
        } else {
            mTaskPhoneView.setVisibility(View.VISIBLE);
            mTaskPhoneView.setText(companyName);
        }
    }

    @Override
    public void showComment(@NonNull String comment) {
        if (TextUtils.isEmpty(comment)) {
            mCommentEditView.setVisibility(View.GONE);
        } else {
            mCommentEditView.setVisibility(View.VISIBLE);
            mCommentEditView.setText(comment);
            mCommentEditView.setEnabled(false);
        }
    }

    @Override
    public void editComment(@NonNull String comment) {
        mCommentEditView.setVisibility(View.VISIBLE);
        mCommentEditView.setText(comment);
        mCommentEditView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mTaskDetailsPresenter.updateComment(String.valueOf(s));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void showTaskPhoto(@NonNull Task task) {
        final List<File> files = task.getFiles();
        if (files == null) {
            return;
        }

        final ArrayList<String> photos = new ArrayList<>();
        for (File file: files) {
            if (ConstructionConstants.FileType.IMAGE.equalsIgnoreCase(file.getType())) {
                photos.add(file.getPublicUrl());
            }
        }

        getView().post(new Runnable() {
            @Override
            public void run() {
                int itemMargin = getResources().getDimensionPixelSize(R.dimen.spacing_margin_micro);
                int width = (mTaskPhotosContainer.getMeasuredWidth() - itemMargin * 4) / 4;
                for (int index = 0; index < photos.size(); index++) {
                    String photoUri = photos.get(index);
                    ImageView imageView = new ImageView(getContext());
                    mTaskPhotosContainer.addView(imageView);
                    GridLayout.LayoutParams layoutParams = (GridLayout.LayoutParams) imageView.getLayoutParams();
                    layoutParams.width = width;
                    layoutParams.height = width;
                    layoutParams.setMargins(itemMargin, itemMargin, itemMargin, itemMargin);
                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    imageView.setTag(index);
                    ImageUtils.loadImage(imageView, photoUri);

                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getActivity(),CommentPreviewActivity.class);
                            intent.putExtra(CommentFragment.POSITION, (int) v.getTag());
                            intent.putStringArrayListExtra(CommentFragment.STRING_LIST, photos);
                            getActivity().startActivity(intent);
                        }
                    });
                }
            }
        });

    }

    @Override
    public void showActions(@NonNull final Task task, @NonNull final ProjectInfo projectInfo) {
        mActionsContainer.removeAllViews();
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

    @Override
    public void close() {
        dismiss();
    }

    private void initView(View view) {
        mCloseBtn = (ImageButton) view.findViewById(R.id.imgBtn_close);
        mHeaderView = (ViewGroup) view.findViewById(R.id.ll_header);
        mTaskNameView = (TextView) view.findViewById(R.id.tv_task_name);
        mTaskStatusView = (TextView) view.findViewById(R.id.tv_task_status);
        mTaskDateView = (TextView) view.findViewById(R.id.tv_task_date);
        mTaskAddressView = (TextView) view.findViewById(R.id.tv_task_address);
        mTaskMembersContainer = (ViewGroup) view.findViewById(R.id.members_container);
        mTaskPhoneView = (TextView) view.findViewById(R.id.tv_task_phone);
        mCommentEditView = (TextInputEditText) view.findViewById(R.id.edt_task_remark);
        mActionsContainer = (LinearLayout) view.findViewById(R.id.actions_container);
        mTaskPhotosContainer = (GridLayout) view.findViewById(R.id.photos_container);
    }

    private void initEvent(){
        mCloseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTaskDetailsPresenter.submitComment();
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
            case UPLOAD_PHOTO:
                return getString(R.string.upload_photo);
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
            case UPLOAD_PHOTO:
                uploadPhoto(task);
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
        mTaskDetailsPresenter.markComplete();
    }

    private void uploadPhoto(Task task) {
        //TODO navigate to common component
        Intent intent = new Intent(getActivity(), UploadPhotoActivity.class);
        getActivity().startActivityForResult(intent, 0x0105);
    }

    private void selectDate(Task task, ProjectInfo projectInfo) {
        PickDateDialogFragment.Builder builder = TaskUtils.getPickDateDialogBuilder(task, projectInfo);
        builder.setSelectionMode(MaterialCalendarView.SELECTION_MODE_SINGLE);
        PickDateDialogFragment dialogFragment = builder.create();
        dialogFragment.show(getChildFragmentManager(), "pick_date");
    }
}
