package com.autodesk.shejijia.enterprise.nodeprocess.ui.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.autodesk.shejijia.enterprise.R;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Task;
import com.autodesk.shejijia.enterprise.nodeprocess.ui.viewholder.TaskListVH;

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
            String status = taskLists.get(position).getStatus();
            if (status.equalsIgnoreCase("open")) {
                taskListVH.mTaskStatus.setText(mContext.getString(R.string.task_open));
                taskListVH.mTaskStatus.setBackground(ContextCompat.getDrawable(mContext, R.drawable.project_list_tv_blue_shape));
            } else if (status.equalsIgnoreCase("reserving")) {
                taskListVH.mTaskStatus.setText(mContext.getString(R.string.task_reserving));
            } else if (status.equalsIgnoreCase("inProgress")) {
                taskListVH.mTaskStatus.setText(mContext.getString(R.string.task_inProgress));
                taskListVH.mTaskStatus.setBackground(ContextCompat.getDrawable(mContext, R.drawable.project_list_tv_blue_shape));
            } else if (status.equalsIgnoreCase("delayed")) {
                taskListVH.mTaskStatus.setText(mContext.getString(R.string.task_delayed));
            } else if (status.equalsIgnoreCase("qualified")) {
                taskListVH.mTaskStatus.setText(mContext.getString(R.string.task_qualified));
            } else if (status.equalsIgnoreCase("unqualified")) {
                taskListVH.mTaskStatus.setText(mContext.getString(R.string.task_unqualified));
            } else if (status.equalsIgnoreCase("resolved")) {
                taskListVH.mTaskStatus.setText(mContext.getString(R.string.task_resolved));
                taskListVH.mTaskStatus.setBackground(ContextCompat.getDrawable(mContext, R.drawable.project_list_tv_lightblue_shape));
            } else if (status.equalsIgnoreCase("rejected")) {
                taskListVH.mTaskStatus.setText(mContext.getString(R.string.task_rejected));
            } else if (status.equalsIgnoreCase("reinspection")) {
                taskListVH.mTaskStatus.setText(mContext.getString(R.string.task_reinspection));
                taskListVH.mTaskStatus.setBackground(ContextCompat.getDrawable(mContext, R.drawable.project_list_tv_orange_shape));
            } else if (status.equalsIgnoreCase("rectification")) {
                taskListVH.mTaskStatus.setText(mContext.getString(R.string.task_rectification));
            } else if (status.equalsIgnoreCase("reinspecting")) {
                taskListVH.mTaskStatus.setText(mContext.getString(R.string.task_reinspecting));
            } else {
                taskListVH.mTaskStatus.setText(status);
            }
        }

        //当前任务节点的类型
        if (!TextUtils.isEmpty(taskLists.get(position).getCategory())) {
            String category = taskLists.get(position).getCategory();
            if (category.equalsIgnoreCase("timeline")) { //开工交底
                Drawable drawable = ContextCompat.getDrawable(mContext, R.drawable.default_head);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                taskListVH.mTaskName.setCompoundDrawables(drawable, null, null, null);
            } else if (category.equalsIgnoreCase("inspection")) { //验收类
                Drawable drawable = ContextCompat.getDrawable(mContext, R.drawable.check_accept);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                taskListVH.mTaskName.setCompoundDrawables(drawable, null, null, null);
            } else if (category.equalsIgnoreCase("construction")) { //施工类
                Drawable drawable = ContextCompat.getDrawable(mContext, R.drawable.construction);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                taskListVH.mTaskName.setCompoundDrawables(drawable, null, null, null);
            } else if (category.equalsIgnoreCase("materialMeasuring")) { //主材测量
                Drawable drawable = ContextCompat.getDrawable(mContext, R.drawable.material);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                taskListVH.mTaskName.setCompoundDrawables(drawable, null, null, null);
            } else if (category.equalsIgnoreCase("materialInstallation")) { //主材安装
                Drawable drawable = ContextCompat.getDrawable(mContext, R.drawable.material);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                taskListVH.mTaskName.setCompoundDrawables(drawable, null, null, null);
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

}
