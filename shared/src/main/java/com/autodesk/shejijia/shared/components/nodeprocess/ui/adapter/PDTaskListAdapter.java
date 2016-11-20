package com.autodesk.shejijia.shared.components.nodeprocess.ui.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Task;
import com.autodesk.shejijia.shared.components.common.utility.DateUtil;

import java.util.List;

/**
 * Created by t_xuz on 11/10/16.
 * 项目详情页面里的任务列表
 */

public class PDTaskListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Task> taskLists;
    private int resId;
    private Context mContext;
    private PDTaskListAdapter.TaskListItemClickListener mTaskListItemClickListener;

    public PDTaskListAdapter(List<Task> taskLists, int resId, Context mContext, PDTaskListAdapter.TaskListItemClickListener taskListItemListener) {
        this.taskLists = taskLists;
        this.resId = resId;
        this.mContext = mContext;
        this.mTaskListItemClickListener = taskListItemListener;
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

        String startDate = taskLists.get(position).getReserveTime().getStart();
        String endDate = taskLists.get(position).getReserveTime().getCompletion();
        //节点日期
        if (!TextUtils.isEmpty(startDate) && !TextUtils.isEmpty(endDate)) {
            boolean isSameDate = DateUtil.isSameDay(DateUtil.iso8601ToDate(startDate),DateUtil.iso8601ToDate(endDate));
            if (isSameDate) {
                taskListVH.mTaskDate.setText(startDate);
            }else {
                taskListVH.mTaskDate.setText(startDate+"-"+endDate);
            }
        }

        // 当前任务节点的状态
        if (!TextUtils.isEmpty(taskLists.get(position).getStatus())) {
            String status = taskLists.get(position).getStatus();
            switch (status) {
                case "OPEN":
                    taskListVH.mTaskStatus.setText(mContext.getString(R.string.task_open));
                    taskListVH.mTaskStatus.setTextColor(ContextCompat.getColor(mContext,R.color.con_font_gray));
                    taskListVH.mTaskStatus.setBackground(ContextCompat.getDrawable(mContext, R.drawable.project_list_tv_grey_shape));
                    break;
                case "RESERVED":
                    taskListVH.mTaskStatus.setText(mContext.getString(R.string.task_reserved));
                    taskListVH.mTaskStatus.setTextColor(ContextCompat.getColor(mContext,R.color.con_font_gray));
                    taskListVH.mTaskStatus.setBackground(ContextCompat.getDrawable(mContext, R.drawable.project_list_tv_grey_shape));
                    break;
                case "RESERVING":
                    taskListVH.mTaskStatus.setText(mContext.getString(R.string.task_reserving));
                    taskListVH.mTaskStatus.setTextColor(ContextCompat.getColor(mContext,R.color.white));
                    taskListVH.mTaskStatus.setBackground(ContextCompat.getDrawable(mContext, R.drawable.project_list_tv_blue_shape));
                    break;
                case "INPROGRESS":
                    taskListVH.mTaskStatus.setText(mContext.getString(R.string.task_inProgress));
                    taskListVH.mTaskStatus.setBackground(ContextCompat.getDrawable(mContext, R.drawable.project_list_tv_blue_shape));
                    break;
                case "DELAYED":
                    taskListVH.mTaskStatus.setText(mContext.getString(R.string.task_delayed));
                    taskListVH.mTaskStatus.setBackground(ContextCompat.getDrawable(mContext, R.drawable.project_list_tv_orange_shape));
                    break;
                case "QUALIFIED":
                    taskListVH.mTaskStatus.setText(mContext.getString(R.string.task_qualified));
                    taskListVH.mTaskStatus.setBackground(ContextCompat.getDrawable(mContext, R.drawable.project_list_tv_blue_shape));
                    break;
                case "UNQUALIFIED":
                    taskListVH.mTaskStatus.setText(mContext.getString(R.string.task_unqualified));
                    taskListVH.mTaskStatus.setBackground(ContextCompat.getDrawable(mContext, R.drawable.project_list_tv_orange_shape));
                    break;
                case "RESOLVED":
                    taskListVH.mTaskStatus.setText(mContext.getString(R.string.task_resolved));
                    taskListVH.mTaskStatus.setBackground(ContextCompat.getDrawable(mContext, R.drawable.project_list_tv_lightblue_shape));
                    break;
                case "REJECTED":
                    taskListVH.mTaskStatus.setText(mContext.getString(R.string.task_rejected));
                    taskListVH.mTaskStatus.setBackground(ContextCompat.getDrawable(mContext, R.drawable.project_list_tv_blue_shape));
                    break;
                case "REINSPECTION":
                    taskListVH.mTaskStatus.setText(mContext.getString(R.string.task_reinspection));
                    taskListVH.mTaskStatus.setBackground(ContextCompat.getDrawable(mContext, R.drawable.project_list_tv_orange_shape));
                    break;
                case "RECTIFICATION":
                    taskListVH.mTaskStatus.setText(mContext.getString(R.string.task_rectification));
                    taskListVH.mTaskStatus.setBackground(ContextCompat.getDrawable(mContext, R.drawable.project_list_tv_blue_shape));
                    break;
                case "REINSPECTING":
                    taskListVH.mTaskStatus.setText(mContext.getString(R.string.task_reinspecting));
                    taskListVH.mTaskStatus.setBackground(ContextCompat.getDrawable(mContext, R.drawable.project_list_tv_blue_shape));
                    break;
                default:
                    taskListVH.mTaskStatus.setText(status);
                    taskListVH.mTaskStatus.setBackground(ContextCompat.getDrawable(mContext, R.drawable.project_list_tv_blue_shape));
                    break;
            }
        }

        //当前任务节点的类型
        if (!TextUtils.isEmpty(taskLists.get(position).getCategory())) {
            String category = taskLists.get(position).getCategory();
            switch (category) {
                case "timeline"://开工交底
                    taskListVH.mTaskIcon.setImageResource(R.drawable.default_head);
                    break;
                case "inspection"://验收类
                    taskListVH.mTaskIcon.setImageResource(R.drawable.ic_task_checkaccept);
                    break;
                case "construction"://施工类
                    taskListVH.mTaskIcon.setImageResource(R.drawable.ic_task_construction);
                    break;
                case "materialMeasuring"://主材测量
                    taskListVH.mTaskIcon.setImageResource(R.drawable.ic_task_material);
                    break;
                case "materialInstallation": //主材安装
                    taskListVH.mTaskIcon.setImageResource(R.drawable.ic_task_material);
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
                mTaskListItemClickListener.onTaskClick(taskLists, position);
            }
        });
    }


    private static class TaskListVH extends RecyclerView.ViewHolder {
        private ImageView mTaskIcon;
        private TextView mTaskName;
        private TextView mTaskStatus;
        private TextView mTaskDate;
        private RelativeLayout mTaskDetails;

        TaskListVH(View itemView) {
            super(itemView);
            mTaskIcon = (ImageView) itemView.findViewById(R.id.img_task_icon);
            mTaskName = (TextView) itemView.findViewById(R.id.tv_task_name);
            mTaskStatus = (TextView) itemView.findViewById(R.id.tv_task_status);
            mTaskDate = (TextView) itemView.findViewById(R.id.tv_task_date);
            mTaskDetails = (RelativeLayout) itemView.findViewById(R.id.rly_task_details);
        }
    }

    public interface TaskListItemClickListener {
        //节点详情
        void onTaskClick(List<Task> taskList, int position);
    }
}
