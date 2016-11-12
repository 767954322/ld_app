package com.autodesk.shejijia.shared.components.nodeprocess.ui.fragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
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
import com.autodesk.shejijia.shared.components.common.uielements.calanderview.MaterialCalendarView;
import com.autodesk.shejijia.shared.components.common.utility.DateUtil;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.components.nodeprocess.contract.EditPlanContract;
import com.autodesk.shejijia.shared.components.nodeprocess.presenter.EditPlanPresenter;
import com.autodesk.shejijia.shared.components.nodeprocess.ui.widgets.calendar.ActiveMileStoneDecorator;
import com.autodesk.shejijia.shared.components.nodeprocess.ui.widgets.calendar.DateSelectorDecorator;
import com.autodesk.shejijia.shared.components.nodeprocess.ui.widgets.calendar.MileStoneDayFormatter;
import com.autodesk.shejijia.shared.components.nodeprocess.ui.widgets.calendar.MileStoneNodeDecorator;
import com.autodesk.shejijia.shared.components.nodeprocess.ui.widgets.calendar.WeekDayFormatter;
import com.autodesk.shejijia.shared.framework.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.Calendar;
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
    public void showActiveTask(Task task) {
        // TODO
    }

    @Override
    public void onTaskDateChange(Task task, Date oldDate, Date newDate) {
        // TODO
    }

    @Override
    public void onCommitSuccess() {
        getActivity().finish();
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
        mAdapter = new TaskNodeAdapter(getActivity(), new TaskNodeAdapter.IViewHolderClick() {
            @Override
            public void onTaskClick(Task task) {
                ((EditPlanPresenter)mPresenter).editTaskNode(task);
            }
        });
        recyclerView.setAdapter(mAdapter);

    }

    @Override
    protected void initData() {
        mPresenter.fetchPlan();
    }

    @Override
    public void showNetError(String msg) {

    }

    @Override
    public void showError(String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.alert_dialog__default_title);
        builder.setMessage(msg);
        builder.setPositiveButton(R.string.sure, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    public void showBottomSheet(List<Task> tasks, Task task) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity(),
                R.style.BottomSheetDialogTheme_Calendar);
        View view  = LayoutInflater.from(getActivity()).inflate(R.layout.bottom_sheet_edit_task_node, null);
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.show();

        View parent = (View) view.getParent();
        final BottomSheetBehavior behavior = BottomSheetBehavior.from(parent);
        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_DRAGGING) {
                    behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            }
        });

        MaterialCalendarView calendarView = (MaterialCalendarView) view.findViewById(R.id.calendarView);
        MileStoneDayFormatter mileStoneDayFormator = new MileStoneDayFormatter();
        MileStoneNodeDecorator mileStoneDecorator = new MileStoneNodeDecorator(getActivity());
        DateSelectorDecorator mSelectorDecorator = new DateSelectorDecorator(getActivity());
        mileStoneDayFormator.setData(tasks);
        mileStoneDecorator.setData(tasks);

        calendarView.setSelectionMode(MaterialCalendarView.SELECTION_MODE_RANGE);
//        calendarView.setWeekDayFormatter(new WeekDayFormatter(getContext()));
        calendarView.setDayFormatter(mileStoneDayFormator);
//        calendarView.addDecorators(mileStoneDecorator);
        calendarView.addDecorator(mSelectorDecorator);
        calendarView.addDecorator(mileStoneDecorator);
//        calendarView.setOnDateChangedListener(this);
        calendarView.setVisibility(View.VISIBLE);
        calendarView.invalidateDecorators();
//        calendarView.setSelectedDate(new Date());

//        Calendar instance1 = Calendar.getInstance();
//        instance1.set(instance1.get(Calendar.YEAR), Calendar.OCTOBER, 1);
//
//        Calendar instance2 = Calendar.getInstance();
//        instance2.set(instance2.get(Calendar.YEAR), Calendar.DECEMBER, 31);
//        calendarView.state()
//                .edit()
//                .setMinimumDate(instance1.getTime())
//                .setMaximumDate(instance2.getTime())
//                .commit();
    }

    private static class TaskNodeAdapter extends RecyclerView.Adapter<TaskNodeViewHolder> {
        private final static int VIEW_TYPE_MILESTONE_NODE = 0;
        private final static int VIEW_TYPE_TASK_NODE = 1;

        private Activity mActivity;
        private List<Task> mTasks = new ArrayList<>();

        private IViewHolderClick mClickListener;

        TaskNodeAdapter(Activity activity, IViewHolderClick clickListener) {
            mActivity = activity;
            mClickListener = clickListener;
        }

        void setData(List<Task> tasks) {
            mTasks.clear();
            mTasks.addAll(tasks);
            notifyItemRangeChanged(0, mTasks.size());
        }

        @Override
        public TaskNodeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            final View view;
            if (viewType == VIEW_TYPE_MILESTONE_NODE) {
                view = LayoutInflater.from(mActivity).inflate(R.layout.item_edit_plan_milestone_node, parent, false);
            } else {
                view = LayoutInflater.from(mActivity).inflate(R.layout.item_edit_plan_task_node, parent, false);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mClickListener != null) {
                            mClickListener.onTaskClick((Task) view.getTag());
                        }
                    }
                });
            }

            return new TaskNodeViewHolder(view);
        }

        @Override
        public void onBindViewHolder(TaskNodeViewHolder holder, int position) {
            Task task = mTasks.get(position);
            holder.itemView.setTag(task);
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
            Date startDate = DateUtil.iso8601ToDate(time.getStart());
            Date endDate = DateUtil.iso8601ToDate(time.getCompletion());
            StringBuilder dateStingBuilder = new StringBuilder("");
            if (startDate != null && endDate != null && DateUtil.getDurationDays(startDate, endDate) <= 1) {
                dateStingBuilder.append(DateUtil.getStringDateByFormat(startDate, "M.d"));
            } else {
                dateStingBuilder.append(startDate == null ? "NA" : DateUtil.getStringDateByFormat(startDate, "M.d"))
                        .append("-")
                        .append(endDate == null ? "NA" : DateUtil.getStringDateByFormat(endDate, "M.d"));
            }
            return dateStingBuilder.toString();
        }

        public static interface IViewHolderClick {
            public void onTaskClick(Task task);
        }

    }

    private static class TaskNodeViewHolder extends RecyclerView.ViewHolder {
        final TextView mTvNodeName;
        final TextView mTvNodeTime;

        TaskNodeViewHolder(View itemView) {
            super(itemView);
            mTvNodeName = (TextView) itemView.findViewById(R.id.tv_node_name);
            mTvNodeTime = (TextView) itemView.findViewById(R.id.tv_node_time);
        }

    }

}
