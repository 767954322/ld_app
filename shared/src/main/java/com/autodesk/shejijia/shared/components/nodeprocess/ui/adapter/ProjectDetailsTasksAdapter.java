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
import com.autodesk.shejijia.shared.components.common.appglobal.ConstructionConstants;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Task;
import com.autodesk.shejijia.shared.components.common.utility.DateUtil;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;

import java.util.Date;
import java.util.List;

import static com.autodesk.shejijia.shared.components.common.utility.DateUtil.getStringDateByFormat;

/**
 * Created by t_xuz on 11/10/16.
 * 项目详情页面里的任务列表
 */

public class ProjectDetailsTasksAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Task> taskLists;
    private int resId;
    private Context mContext;
    private ProjectDetailsTasksAdapter.TaskListItemClickListener mTaskListItemClickListener;

    public ProjectDetailsTasksAdapter(List<Task> taskLists, int resId, Context mContext, ProjectDetailsTasksAdapter.TaskListItemClickListener taskListItemListener) {
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

        //节点日期
        if (taskLists.get(position).getReserveTime() != null) {
            String startDate = taskLists.get(position).getReserveTime().getStart();
            String endDate = taskLists.get(position).getReserveTime().getCompletion();
            if (!TextUtils.isEmpty(startDate) && !TextUtils.isEmpty(endDate)) {
                boolean isSameDate = DateUtil.isSameDay(DateUtil.iso8601ToDate(startDate), DateUtil.iso8601ToDate(endDate));
                if (isSameDate) {
                    taskListVH.mTaskDate.setText(formattedDateFromDate(DateUtil.iso8601ToDate(startDate)));
                } else {
                    taskListVH.mTaskDate.setText(formattedDateFromDate(DateUtil.iso8601ToDate(startDate)) + "-"
                            + formattedDateFromDate(DateUtil.iso8601ToDate(endDate)));
                }
            }
        }

        // 当前任务节点的状态
        if (!TextUtils.isEmpty(taskLists.get(position).getStatus())) {
            String status = taskLists.get(position).getStatus().toLowerCase();
            switch (status) {
                case ConstructionConstants.TaskStatus.OPEN:
                    taskListVH.mTaskStatus.setText(mContext.getString(R.string.task_open));
                    taskListVH.mTaskStatus.setTextColor(ContextCompat.getColor(mContext, R.color.con_font_gray));
                    taskListVH.mTaskStatus.setBackground(ContextCompat.getDrawable(mContext, R.drawable.project_list_tv_grey_shape));
                    break;
                case ConstructionConstants.TaskStatus.RESERVED:
                    taskListVH.mTaskStatus.setText(mContext.getString(R.string.task_reserved));
                    taskListVH.mTaskStatus.setTextColor(ContextCompat.getColor(mContext, R.color.con_font_gray));
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
            switch (category) {
                case ConstructionConstants.TaskCategory.TIME_LINE://开工交底
                    taskListVH.mTaskIcon.setImageResource(R.drawable.default_head);
                    break;
                case ConstructionConstants.TaskCategory.INSPECTOR_INSPECTION://监理验收
                case ConstructionConstants.TaskCategory.CLIENT_MANAGER_INSPECTION://客户经理验收
                    taskListVH.mTaskIcon.setImageResource(R.drawable.ic_task_checkaccept);
                    break;
                case ConstructionConstants.TaskCategory.CONSTRUCTION://施工类
                    taskListVH.mTaskIcon.setImageResource(R.drawable.ic_task_construction);
                    break;
                case ConstructionConstants.TaskCategory.MATERIAL_MEASURING://主材测量
                    taskListVH.mTaskIcon.setImageResource(R.drawable.ic_task_material);
                    break;
                case ConstructionConstants.TaskCategory.MATERIAL_INSTALLATION: //主材安装
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

    private String formattedDateFromDate(Date date) {
        return getStringDateByFormat(date, UIUtils.getString(R.string.calendar_month_day));
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
