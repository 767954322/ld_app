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
import com.autodesk.shejijia.shared.components.common.entity.microbean.Time;
import com.autodesk.shejijia.shared.components.common.uielements.CircleImageView;
import com.autodesk.shejijia.shared.components.common.utility.DateUtil;
import com.autodesk.shejijia.shared.components.common.utility.ImageUtils;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.components.nodeprocess.utility.TaskHeadPicHelper;
import com.autodesk.shejijia.shared.components.nodeprocess.utility.TaskUtils;

import java.util.Date;
import java.util.List;

import static com.autodesk.shejijia.shared.components.common.utility.DateUtil.getStringDateByFormat;

/**
 * Created by t_xuz on 11/10/16.
 * 项目详情页面里的任务列表
 */

public class ProjectDetailsTasksAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Task> mTaskLists;
    private int mResId;
    private String mAvatarUrl;
    private Context mContext;
    private ProjectDetailsTasksAdapter.TaskListItemClickListener mTaskListItemClickListener;

    public ProjectDetailsTasksAdapter(List<Task> taskLists, String avatarUrl, int resId, Context mContext, ProjectDetailsTasksAdapter.TaskListItemClickListener taskListItemListener) {
        this.mTaskLists = taskLists;
        this.mAvatarUrl = avatarUrl;
        this.mResId = resId;
        this.mContext = mContext;
        this.mTaskListItemClickListener = taskListItemListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(mResId, parent, false);
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
        return mTaskLists.size();
    }

    private void initView(TaskListVH taskListVH, int position) {
        // 当前任务节点名
        if (!TextUtils.isEmpty(mTaskLists.get(position).getName())) {
            taskListVH.mTaskName.setText(mTaskLists.get(position).getName());
        }

        //设置任务节点日期
        Time time = TaskUtils.getDisplayTime(mTaskLists.get(position));
        if (time != null) {
            String startDate = time.getStart();
            String endDate = time.getCompletion();
            setTaskDate(taskListVH, startDate, endDate);
        }

        // 当前任务节点的状态
        String status = mTaskLists.get(position).getStatus();
        if (!TextUtils.isEmpty(status)) {
            taskListVH.mTaskStatus.setText(TaskUtils.getDisplayStatus(status));
            taskListVH.mTaskStatus.setTextColor(TaskUtils.getStatusTextColor(mContext, status));
            taskListVH.mTaskStatus.getBackground().setLevel(TaskUtils.getStatusLevel(status));
        }

        // 当前任务节点头像
        String taskHeadStatus = TaskHeadPicHelper.getInstance().getActions(mTaskLists.get(position));
        switch (taskHeadStatus) {
            case TaskHeadPicHelper.SHOW_HEAD:
                taskListVH.mTaskIconHead.setVisibility(View.VISIBLE);
                taskListVH.mTaskIconDefault.setVisibility(View.GONE);
                if (!TextUtils.isEmpty(mAvatarUrl)) {
                    ImageUtils.loadUserAvatar(taskListVH.mTaskIconHead, mAvatarUrl);
                }
                break;
            case TaskHeadPicHelper.SHOW_DEFAULT:
                taskListVH.mTaskIconDefault.setVisibility(View.VISIBLE);
                taskListVH.mTaskIconHead.setVisibility(View.GONE);
                if (!TextUtils.isEmpty(mTaskLists.get(position).getCategory())) {
                    String category = mTaskLists.get(position).getCategory();
                    switch (category) {
                        case ConstructionConstants.TaskCategory.TIME_LINE://开工交底
                            taskListVH.mTaskIconDefault.setImageResource(R.drawable.default_head);
                            break;
                        case ConstructionConstants.TaskCategory.INSPECTOR_INSPECTION://监理验收
                        case ConstructionConstants.TaskCategory.CLIENT_MANAGER_INSPECTION://客户经理验收
                            taskListVH.mTaskIconDefault.setImageResource(R.drawable.ic_task_checkaccept);
                            break;
                        case ConstructionConstants.TaskCategory.CONSTRUCTION://施工类
                            taskListVH.mTaskIconDefault.setImageResource(R.drawable.ic_task_construction);
                            break;
                        case ConstructionConstants.TaskCategory.MATERIAL_MEASURING://主材测量
                        case ConstructionConstants.TaskCategory.MATERIAL_INSTALLATION: //主材安装
                            taskListVH.mTaskIconDefault.setImageResource(R.drawable.ic_task_material);
                            break;
                        default:
                            break;
                    }
                }
                break;
        }
    }

    private void initEvents(TaskListVH taskListVH, final int position) {
        taskListVH.mTaskDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTaskListItemClickListener.onTaskClick(mTaskLists, position);
            }
        });
    }

    private void setTaskDate(TaskListVH taskListVH, String startDate, String endDate) {
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

    private String formattedDateFromDate(Date date) {
        return getStringDateByFormat(date, UIUtils.getString(R.string.date_format_month_day));
    }

    private static class TaskListVH extends RecyclerView.ViewHolder {
        private ImageView mTaskIconDefault;
        private CircleImageView mTaskIconHead;
        private TextView mTaskName;
        private TextView mTaskStatus;
        private TextView mTaskDate;
        private RelativeLayout mTaskDetails;

        TaskListVH(View itemView) {
            super(itemView);
            mTaskIconDefault = (ImageView) itemView.findViewById(R.id.img_task_iconDefault);
            mTaskIconHead = (CircleImageView) itemView.findViewById(R.id.img_task_iconHead);
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
