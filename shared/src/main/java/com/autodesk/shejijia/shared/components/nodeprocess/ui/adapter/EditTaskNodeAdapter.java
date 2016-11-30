package com.autodesk.shejijia.shared.components.nodeprocess.ui.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.appglobal.ConstructionConstants;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Task;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Time;
import com.autodesk.shejijia.shared.components.common.utility.DateUtil;
import com.autodesk.shejijia.shared.components.common.utility.LogUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wenhulin on 11/17/16.
 */
public class EditTaskNodeAdapter extends RecyclerView.Adapter<EditTaskNodeAdapter.TaskNodeViewHolder> {
    private final static int VIEW_TYPE_MILESTONE_NODE = 0;
    private final static int VIEW_TYPE_TASK_NODE = 1;

    private Activity mActivity;

    private List<Task> mTasks = new ArrayList<>();
    private List<Task> mSelectedTasks = new ArrayList<>();

    private boolean mIsInActionMode = false;

    private ItemClickListener mClickListener;

    public EditTaskNodeAdapter(Activity activity, ItemClickListener clickListener) {
        mActivity = activity;
        mClickListener = clickListener;
    }

    public void setIsActionMode(boolean isActionMode) {
        mIsInActionMode = isActionMode;
        if (!mIsInActionMode) {
            mSelectedTasks.clear();
        }
        notifyItemRangeChanged(0, mTasks.size());
    }

    public List<Task> getSelectedTasks() {
        return mSelectedTasks;
    }

    public void setData(List<Task> tasks) {
        animateTo(tasks);
    }

    private void animateTo(List<Task> models) {
        applyAndAnimateRemovals(models);
        applyAndAnimateAdditions(models);
        applyAndAnimateMovedItems(models);
    }

    private void applyAndAnimateRemovals(List<Task> newTasks) {
        for (int i = mTasks.size() - 1; i >= 0; i--) {
            final Task task = mTasks.get(i);
            if (!newTasks.contains(task)) {
                removeItem(i);
            }
        }
    }

    private void applyAndAnimateAdditions(List<Task> newTasks) {
        for (int i = 0, count = newTasks.size(); i < count; i++) {
            final Task task = newTasks.get(i);
            if (!mTasks.contains(task)) {
                addItem(i, task);
            }
        }
    }

    private void applyAndAnimateMovedItems(List<Task> newTasks) {
        for (int toPosition = newTasks.size() - 1; toPosition >= 0; toPosition--) {
            final Task task = newTasks.get(toPosition);
            final int fromPosition = mTasks.indexOf(task);
            if (fromPosition >= 0) {
                if (fromPosition != toPosition) {
                    moveItem(fromPosition, toPosition);
                }
                updateItem(toPosition);
            }
        }
    }

    private Task removeItem(int position) {
        final Task task = mTasks.remove(position);
        notifyItemRemoved(position);
        return task;
    }

    private void addItem(int position, Task model) {
        mTasks.add(position, model);
        notifyItemInserted(position);
    }

    private void moveItem(int fromPosition, int toPosition) {
        final Task task = mTasks.remove(fromPosition);
        mTasks.add(toPosition, task);
        notifyItemMoved(fromPosition, toPosition);
    }

    private void updateItem(int position) {
        notifyItemChanged(position);
    }

    @Override
    public EditTaskNodeAdapter.TaskNodeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view;
        if (viewType == VIEW_TYPE_MILESTONE_NODE) {
            view = LayoutInflater.from(mActivity).inflate(R.layout.item_edit_plan_milestone_node, parent, false);
        } else {
            view = LayoutInflater.from(mActivity).inflate(R.layout.item_edit_plan_task_node, parent, false);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mClickListener != null) {
                        int position = (int) view.getTag();
                        Task task = mTasks.get(position);
                        if (mIsInActionMode) {
                            if (mSelectedTasks.contains(task)) {
                                mSelectedTasks.remove(task);
                            } else {
                                mSelectedTasks.add(task);
                            }
                            notifyItemChanged(position);
                            mClickListener.onSelectedTaskCountChanged(mSelectedTasks.size());
                        } else {
                            mClickListener.onTaskClick(task);
                        }
                    }
                }
            });

            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    boolean handled = false;
                    if (mClickListener != null) {
                        int position = (int) view.getTag();
                        Task task = mTasks.get(position);
                        handled = mClickListener.onTaskLongClick(task);
                        if (handled) {
                            mSelectedTasks.add(task);
                            mClickListener.onSelectedTaskCountChanged(1);
                            notifyItemChanged(position);
                        }
                    }
                    return handled;
                }
            });
        }

        return new EditTaskNodeAdapter.TaskNodeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EditTaskNodeAdapter.TaskNodeViewHolder holder, int position) {
        Task task = mTasks.get(position);
        holder.itemView.setTag(position);
        holder.mTvNodeName.setText(task.getName());
        holder.mTvNodeTime.setText(getDateString(task));

        holder.itemView.setSelected(mSelectedTasks.contains(task));
        holder.itemView.setEnabled(isTaskEditable(task));
    }

    @Override
    public int getItemCount() {
        return mTasks.size();
    }

    @Override
    public int getItemViewType(int position) {
        Task task = mTasks.get(position);
        if (task.isMilestone()) {
            return VIEW_TYPE_MILESTONE_NODE;
        } else {
            return VIEW_TYPE_TASK_NODE;
        }
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    public int getItemPosition(Task task) {
        return mTasks.indexOf(task);
    }

    private String getDateString(Task task) {
        Time time = task.getPlanningTime();
        Date startDate = DateUtil.iso8601ToDate(time.getStart());
        Date endDate = DateUtil.iso8601ToDate(time.getCompletion());
        StringBuilder dateStingBuilder = new StringBuilder("");
        if (startDate != null && (task.isMilestone() || (endDate != null && DateUtil.isSameDay(startDate, endDate)))) {
            dateStingBuilder.append(DateUtil.getStringDateByFormat(startDate, "M.d"));
        } else {
            dateStingBuilder.append(startDate == null ? "NA" : DateUtil.getStringDateByFormat(startDate, "M.d"))
                    .append("-")
                    .append(endDate == null ? "NA" : DateUtil.getStringDateByFormat(endDate, "M.d"));
        }
        return dateStingBuilder.toString();
    }

    private boolean isTaskDeletable(Task task) {
        switch (task.getStatus().toLowerCase()) {
            case ConstructionConstants.TaskStatus.UNQUALIFIED:
            case ConstructionConstants.TaskStatus.REINSPECTION:
            case ConstructionConstants.TaskStatus.RECTIFICATION:
            case ConstructionConstants.TaskStatus.REINSPECTION_AND_RECTIFICATION:
            case ConstructionConstants.TaskStatus.REJECTED:
            case ConstructionConstants.TaskStatus.RESERVED:
            case ConstructionConstants.TaskStatus.REINSPECTING:
            case ConstructionConstants.TaskStatus.REINSPECT_DELAY:
            case ConstructionConstants.TaskStatus.RESOLVED:
                return false;
            default:
                return true;
        }
    }

    private boolean isTaskEditable(Task task) {
        switch (task.getStatus().toLowerCase()) {
            case ConstructionConstants.TaskStatus.RESOLVED:
                return false;
            default:
                return true;
        }
    }

    public interface ItemClickListener {
        void onTaskClick(Task task);

        boolean onTaskLongClick(Task task);

        void onSelectedTaskCountChanged(int count);
    }

    static class TaskNodeViewHolder extends RecyclerView.ViewHolder {
        final TextView mTvNodeName;
        final TextView mTvNodeTime;

        TaskNodeViewHolder(View itemView) {
            super(itemView);
            mTvNodeName = (TextView) itemView.findViewById(R.id.tv_node_name);
            mTvNodeTime = (TextView) itemView.findViewById(R.id.tv_node_time);
        }

    }
}
