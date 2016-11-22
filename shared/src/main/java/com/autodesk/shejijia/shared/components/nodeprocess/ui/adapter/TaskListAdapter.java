package com.autodesk.shejijia.shared.components.nodeprocess.ui.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.appglobal.ConstructionConstants;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Task;

import java.util.List;

/**
 * Created by t_xuz on 8/23/16.
 * 任务列表 － adapter
 */
public class TaskListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Task> taskLists;
    private int resId;
    private Context mContext;
    private ProjectListAdapter.ProjectListItemListener mTaskListItemListener;

    public TaskListAdapter(List<Task> taskLists, int resId, Context mContext, ProjectListAdapter.ProjectListItemListener mTaskListItemListener) {
        this.taskLists = taskLists;
        this.resId = resId;
        this.mContext = mContext;
        this.mTaskListItemListener = mTaskListItemListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resId, parent, false);
        return new TaskListVH(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        TaskListVH taskListVH = (TaskListVH) holder;

        initView(taskListVH, position);

        initEvents(taskListVH, position);
    }

    @Override
    public int getItemCount() {
        return taskLists.size();
    }

    private void initView(TaskListVH taskListVH, int position) {
        // 当前任务节点名
        if (!TextUtils.isEmpty(taskLists.get(position).getName())) {
            taskListVH.mTaskName.setText(taskLists.get(position).getName());
        }

        // 当前任务节点的状态
        if (!TextUtils.isEmpty(taskLists.get(position).getStatus())) {
            String status = taskLists.get(position).getStatus().toLowerCase();
            switch (status) {
                case ConstructionConstants.TaskStatus.OPEN:
                    taskListVH.mTaskStatus.setText(mContext.getString(R.string.task_open));
                    taskListVH.mTaskStatus.setTextColor(ContextCompat.getColor(mContext, R.color.white));
                    taskListVH.mTaskStatus.setBackground(ContextCompat.getDrawable(mContext, R.drawable.project_list_tv_blue_shape));
                    break;
                case ConstructionConstants.TaskStatus.RESERVED:
                    taskListVH.mTaskStatus.setText(mContext.getString(R.string.task_reserved));
                    taskListVH.mTaskStatus.setTextColor(ContextCompat.getColor(mContext, R.color.white));
                    taskListVH.mTaskStatus.setBackground(ContextCompat.getDrawable(mContext, R.drawable.project_list_tv_grey_shape));
                    break;
                case ConstructionConstants.TaskStatus.RESERVING:
                    taskListVH.mTaskStatus.setText(mContext.getString(R.string.task_reserving));
                    taskListVH.mTaskStatus.setTextColor(ContextCompat.getColor(mContext, R.color.white));
                    taskListVH.mTaskStatus.setBackground(ContextCompat.getDrawable(mContext, R.drawable.project_list_tv_blue_shape));
                    break;
                case ConstructionConstants.TaskStatus.INPROGRESS:
                    taskListVH.mTaskStatus.setText(mContext.getString(R.string.task_inProgress));
                    taskListVH.mTaskStatus.setTextColor(ContextCompat.getColor(mContext, R.color.white));
                    taskListVH.mTaskStatus.setBackground(ContextCompat.getDrawable(mContext, R.drawable.project_list_tv_blue_shape));
                    break;
                case ConstructionConstants.TaskStatus.DELAYED:
                    taskListVH.mTaskStatus.setText(mContext.getString(R.string.task_delayed));
                    taskListVH.mTaskStatus.setTextColor(ContextCompat.getColor(mContext, R.color.white));
                    taskListVH.mTaskStatus.setBackground(ContextCompat.getDrawable(mContext, R.drawable.project_list_tv_orange_shape));
                    break;
                case ConstructionConstants.TaskStatus.QUALIFIED:
                    taskListVH.mTaskStatus.setText(mContext.getString(R.string.task_qualified));
                    taskListVH.mTaskStatus.setTextColor(ContextCompat.getColor(mContext, R.color.white));
                    taskListVH.mTaskStatus.setBackground(ContextCompat.getDrawable(mContext, R.drawable.project_list_tv_blue_shape));
                    break;
                case ConstructionConstants.TaskStatus.UNQUALIFIED:
                    taskListVH.mTaskStatus.setText(mContext.getString(R.string.task_unqualified));
                    taskListVH.mTaskStatus.setTextColor(ContextCompat.getColor(mContext, R.color.white));
                    taskListVH.mTaskStatus.setBackground(ContextCompat.getDrawable(mContext, R.drawable.project_list_tv_orange_shape));
                    break;
                case ConstructionConstants.TaskStatus.RESOLVED:
                    taskListVH.mTaskStatus.setText(mContext.getString(R.string.task_resolved));
                    taskListVH.mTaskStatus.setTextColor(ContextCompat.getColor(mContext, R.color.white));
                    taskListVH.mTaskStatus.setBackground(ContextCompat.getDrawable(mContext, R.drawable.project_list_tv_lightblue_shape));
                    break;
                case ConstructionConstants.TaskStatus.REJECTED:
                    taskListVH.mTaskStatus.setText(mContext.getString(R.string.task_rejected));
                    taskListVH.mTaskStatus.setTextColor(ContextCompat.getColor(mContext, R.color.white));
                    taskListVH.mTaskStatus.setBackground(ContextCompat.getDrawable(mContext, R.drawable.project_list_tv_blue_shape));
                    break;
                case ConstructionConstants.TaskStatus.REINSPECTION:
                    taskListVH.mTaskStatus.setText(mContext.getString(R.string.task_reinspection));
                    taskListVH.mTaskStatus.setTextColor(ContextCompat.getColor(mContext, R.color.white));
                    taskListVH.mTaskStatus.setBackground(ContextCompat.getDrawable(mContext, R.drawable.project_list_tv_orange_shape));
                    break;
                case ConstructionConstants.TaskStatus.RECTIFICATION:
                    taskListVH.mTaskStatus.setText(mContext.getString(R.string.task_rectification));
                    taskListVH.mTaskStatus.setTextColor(ContextCompat.getColor(mContext, R.color.white));
                    taskListVH.mTaskStatus.setBackground(ContextCompat.getDrawable(mContext, R.drawable.project_list_tv_blue_shape));
                    break;
                case ConstructionConstants.TaskStatus.REINSPECTING:
                    taskListVH.mTaskStatus.setText(mContext.getString(R.string.task_reinspecting));
                    taskListVH.mTaskStatus.setTextColor(ContextCompat.getColor(mContext, R.color.white));
                    taskListVH.mTaskStatus.setBackground(ContextCompat.getDrawable(mContext, R.drawable.project_list_tv_blue_shape));
                    break;
                case ConstructionConstants.TaskStatus.REINSPECTION_AND_RECTIFICATION:
                    taskListVH.mTaskStatus.setText(mContext.getString(R.string.task_reinspectionand_rectification));
                    taskListVH.mTaskStatus.setTextColor(ContextCompat.getColor(mContext, R.color.white));
                    taskListVH.mTaskStatus.setBackground(ContextCompat.getDrawable(mContext, R.drawable.project_list_tv_blue_shape));
                    break;
                case ConstructionConstants.TaskStatus.REINSPECT_RESERVING:
                    taskListVH.mTaskStatus.setText(mContext.getString(R.string.task_reinspect_treserving));
                    taskListVH.mTaskStatus.setTextColor(ContextCompat.getColor(mContext, R.color.white));
                    taskListVH.mTaskStatus.setBackground(ContextCompat.getDrawable(mContext, R.drawable.project_list_tv_blue_shape));
                    break;
                case ConstructionConstants.TaskStatus.REINSPECT_RESERVED:
                    taskListVH.mTaskStatus.setText(mContext.getString(R.string.task_reinspect_reserved));
                    taskListVH.mTaskStatus.setTextColor(ContextCompat.getColor(mContext, R.color.white));
                    taskListVH.mTaskStatus.setBackground(ContextCompat.getDrawable(mContext, R.drawable.project_list_tv_blue_shape));
                    break;
                case ConstructionConstants.TaskStatus.REINSPECT_INPROGRESS:
                    taskListVH.mTaskStatus.setText(mContext.getString(R.string.task_reinspect_inprogress));
                    taskListVH.mTaskStatus.setTextColor(ContextCompat.getColor(mContext, R.color.white));
                    taskListVH.mTaskStatus.setBackground(ContextCompat.getDrawable(mContext, R.drawable.project_list_tv_blue_shape));
                    break;
                case ConstructionConstants.TaskStatus.REINSPECT_DELAY:
                    taskListVH.mTaskStatus.setText(mContext.getString(R.string.task_reinspect_delay));
                    taskListVH.mTaskStatus.setTextColor(ContextCompat.getColor(mContext, R.color.white));
                    taskListVH.mTaskStatus.setBackground(ContextCompat.getDrawable(mContext, R.drawable.project_list_tv_blue_shape));
                    break;
                case ConstructionConstants.TaskStatus.DELETED:
                    taskListVH.mTaskStatus.setText(mContext.getString(R.string.task_deleted));
                    taskListVH.mTaskStatus.setTextColor(ContextCompat.getColor(mContext, R.color.white));
                    taskListVH.mTaskStatus.setBackground(ContextCompat.getDrawable(mContext, R.drawable.project_list_tv_blue_shape));
                    break;
                default:
                    taskListVH.mTaskStatus.setText(status);
                    taskListVH.mTaskStatus.setTextColor(ContextCompat.getColor(mContext, R.color.white));
                    taskListVH.mTaskStatus.setBackground(ContextCompat.getDrawable(mContext, R.drawable.project_list_tv_blue_shape));
                    break;
            }

        }

        //当前任务节点的类型
        if (!TextUtils.isEmpty(taskLists.get(position).getCategory())) {
            String category = taskLists.get(position).getCategory();
            Drawable drawable = null;
            switch (category) {
                case ConstructionConstants.TaskCategory.TIME_LINE:
                    drawable = ContextCompat.getDrawable(mContext, R.drawable.default_head);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    taskListVH.mTaskName.setCompoundDrawables(drawable, null, null, null);
                    break;
                case ConstructionConstants.TaskCategory.INSPECTOR_INSPECTION:
                    drawable = ContextCompat.getDrawable(mContext, R.drawable.ic_task_checkaccept);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    taskListVH.mTaskName.setCompoundDrawables(drawable, null, null, null);
                    break;
                case ConstructionConstants.TaskCategory.CLIENT_MANAGER_INSPECTION:

                    break;
                case ConstructionConstants.TaskCategory.CONSTRUCTION:
                    drawable = ContextCompat.getDrawable(mContext, R.drawable.ic_task_construction);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    taskListVH.mTaskName.setCompoundDrawables(drawable, null, null, null);
                    break;
                case ConstructionConstants.TaskCategory.MATERIAL_MEASURING:
                    drawable = ContextCompat.getDrawable(mContext, R.drawable.ic_task_material);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    taskListVH.mTaskName.setCompoundDrawables(drawable, null, null, null);
                    break;
                case ConstructionConstants.TaskCategory.MATERIAL_INSTALLATION:
                    drawable = ContextCompat.getDrawable(mContext, R.drawable.ic_task_material);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    taskListVH.mTaskName.setCompoundDrawables(drawable, null, null, null);
                    break;
                default:
                    break;
            }
        }
    }

    private void initEvents(TaskListVH taskListVH, final int position) {
        taskListVH.mTaskDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTaskListItemListener.onTaskClick(taskLists, position);
            }
        });
    }

    private static class TaskListVH extends RecyclerView.ViewHolder {
        private TextView mTaskName;
        private TextView mTaskStatus;
        private LinearLayout mTaskDetails;

        TaskListVH(View itemView) {
            super(itemView);

            mTaskName = (TextView) itemView.findViewById(R.id.tv_task_name);
            mTaskStatus = (TextView) itemView.findViewById(R.id.tv_task_status);
            mTaskDetails = (LinearLayout) itemView.findViewById(R.id.ll_task_details);
        }
    }
}
