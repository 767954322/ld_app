package com.autodesk.shejijia.shared.components.nodeprocess.ui.fragment;

import android.app.Activity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Task;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Time;
import com.autodesk.shejijia.shared.components.common.utility.DateUtil;
import com.autodesk.shejijia.shared.components.nodeprocess.contract.EditPlanContract;
import com.autodesk.shejijia.shared.framework.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wenhulin on 11/3/16.
 */

public class EditTaskNodeFragment extends BaseFragment implements EditPlanContract.View {
    private EditPlanContract.Presenter mPresenter;
    private TaskNodeAdapter mAdapter;

    @Override
    public void showTasks(List<Task> tasks) {
        mAdapter.setData(tasks);
    }

    @Override
    public void bindPresenter(EditPlanContract.Presenter presenter) {
        mPresenter = presenter;
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

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_edit_tasknode;
    }

    @Override
    protected void initView() {
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.edit_plan_title_second_step);
        }

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new TaskNodeAdapter(getActivity());
        recyclerView.setAdapter(mAdapter);

    }

    @Override
    protected void initData() {
        mPresenter.fetchPlan();
    }

    private static class TaskNodeAdapter extends RecyclerView.Adapter<TaskNodeViewHolder> {
        private final static int VIEW_TYPE_MILESTONE_NODE = 0;
        private final static int VIEW_TYPE_TASK_NODE = 1;
        private Activity mActivity;
        private List<Task> mTasks = new ArrayList<>();

        TaskNodeAdapter(Activity activity) {
            mActivity = activity;
        }

        void setData(List<Task> tasks) {
            mTasks.clear();
            mTasks.addAll(tasks);
            notifyItemRangeChanged(0, mTasks.size());
        }

        @Override
        public TaskNodeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view;
            if (viewType == VIEW_TYPE_MILESTONE_NODE) {
                view = LayoutInflater.from(mActivity).inflate(R.layout.item_edit_plan_milestone_node, parent, false);
            } else {
                view = LayoutInflater.from(mActivity).inflate(R.layout.item_edit_plan_task_node, parent, false);
            }

            return new TaskNodeViewHolder(view);
        }

        @Override
        public void onBindViewHolder(TaskNodeViewHolder holder, int position) {
            Task task = mTasks.get(position);
            holder.mTvNodeName.setText(task.getName());
            holder.mTvNodeTime.setText(getDateString(task));
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

        private String getDateString(Task task) {
            Time time = task.getPlanningTime();
            Date startDate = DateUtil.isoStringToDate(time.getStart());
            Date endDate = DateUtil.isoStringToDate(time.getCompletion());
            StringBuilder dateSting = new StringBuilder("");
            if (startDate != null && endDate != null && DateUtil.getDurationDays(startDate, endDate) <= 1) {
                dateSting.append(DateUtil.getStringDateByFormat(startDate, "M.d"));
            } else {
                dateSting.append(startDate == null ? "NA" : DateUtil.getStringDateByFormat(startDate, "M.d"))
                        .append("-")
                        .append(endDate == null ? "NA" : DateUtil.getStringDateByFormat(endDate, "M.d"));
            }
            return dateSting.toString();
        }

    }

    private static class TaskNodeViewHolder extends RecyclerView.ViewHolder {
        public final TextView mTvNodeName;
        public final TextView mTvNodeTime;

        public TaskNodeViewHolder(View itemView) {
            super(itemView);
            mTvNodeName = (TextView) itemView.findViewById(R.id.tv_node_name);
            mTvNodeTime = (TextView) itemView.findViewById(R.id.tv_node_time);
        }

    }

}
