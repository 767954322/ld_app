package com.autodesk.shejijia.shared.components.nodeprocess.ui.fragment;

import android.app.Activity;
import android.content.DialogInterface;
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
import com.autodesk.shejijia.shared.components.common.uielements.ConProgressDialog;
import com.autodesk.shejijia.shared.components.common.uielements.calanderview.CalendarDay;
import com.autodesk.shejijia.shared.components.common.uielements.calanderview.MaterialCalendarView;
import com.autodesk.shejijia.shared.components.common.utility.DateUtil;
import com.autodesk.shejijia.shared.components.nodeprocess.contract.EditPlanContract;
import com.autodesk.shejijia.shared.components.nodeprocess.presenter.EditPlanPresenter;
import com.autodesk.shejijia.shared.components.nodeprocess.ui.widgets.calendar.DateSelectorDecorator;
import com.autodesk.shejijia.shared.components.nodeprocess.ui.widgets.calendar.MileStoneDayFormatter;
import com.autodesk.shejijia.shared.components.nodeprocess.ui.widgets.calendar.MileStoneNodeDecorator;
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

    private BottomSheetDialog mBottomSheetDialog;
    private MaterialCalendarView mCalendarView;

    private ConProgressDialog mProgressDialog;

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

        initBottomSheetDialog();
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
        if (mProgressDialog == null) {
            mProgressDialog = new ConProgressDialog(getActivity());
            mProgressDialog.setMessage(getString(R.string.loading));
        }

        mProgressDialog.show();
    }

    @Override
    public void hideLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.cancel();
        }
    }

    private void initBottomSheetDialog() {
        mBottomSheetDialog = new BottomSheetDialog(getActivity(),
                R.style.BottomSheetDialogTheme_Calendar);
    }

    public void showBottomSheet(List<Task> milstoneTasks, Task task) {
        View view  = LayoutInflater.from(getActivity()).inflate(R.layout.bottom_sheet_edit_task_node, null);
        mBottomSheetDialog.setContentView(view);

        mCalendarView = (MaterialCalendarView) view.findViewById(R.id.calendarView);
        mCalendarView.setSelectionMode(MaterialCalendarView.SELECTION_MODE_RANGE);

        // Set formatter
        MileStoneDayFormatter mileStoneDayFormatter = new MileStoneDayFormatter();
        mileStoneDayFormatter.setData(milstoneTasks);
        mCalendarView.setDayFormatter(mileStoneDayFormatter);

        // Set decorators
        mCalendarView.removeDecorators();
        MileStoneNodeDecorator mileStoneDecorator = new MileStoneNodeDecorator(getActivity());
        DateSelectorDecorator selectorDecorator = new DateSelectorDecorator(getActivity());
        mileStoneDecorator.setData(milstoneTasks);
        mCalendarView.addDecorators(selectorDecorator,
                mileStoneDecorator);

        // set date limit
        Date startDate = DateUtil.iso8601ToDate(milstoneTasks.get(0).getPlanningTime().getStart());
        Date endDate = DateUtil.iso8601ToDate(milstoneTasks.get(milstoneTasks.size() - 1).getPlanningTime().getCompletion());

        int limitMonthOffset = 6;
        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();
        // TODO optimize date format fail case
        calendar.setTime(startDate == null ? today : startDate);
        calendar.add(Calendar.MONTH, -limitMonthOffset);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1);
        Date minDate = calendar.getTime();

        // TODO optimize date format fail case
        calendar.setTime(endDate == null ? today : endDate);
        calendar.add(Calendar.MONTH, limitMonthOffset);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date maxDate = calendar.getTime();

        mCalendarView.state()
                .edit()
                .setMinimumDate(minDate)
                .setMaximumDate(maxDate)
                .commit();

        Date taskStartDay = DateUtil.iso8601ToDate(task.getPlanningTime().getStart());
        Date taskEndDay = DateUtil.iso8601ToDate(task.getPlanningTime().getCompletion());
        mCalendarView.setCurrentDate(taskStartDay == null ? today : taskStartDay);

        if (DateUtil.isSameDay(taskStartDay, taskEndDay)) {
            mCalendarView.setSelectedDate(taskStartDay);
        } else if (taskStartDay != null){
            mCalendarView.setSelectedRange(taskStartDay, taskEndDay);
        }


        TextView taskNameView = (TextView) view.findViewById(R.id.tv_task_name);
        taskNameView.setText(task.getName());

        TextView actionBtn = (TextView) view.findViewById(R.id.tv_action);
        actionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideBottomSheetDialog();
                List<CalendarDay> selectedDays = mCalendarView.getSelectedDates();
                List<Date> selectedDates = new ArrayList<Date>();
                for (CalendarDay day: selectedDays) {
                    selectedDates.add(day.getDate());
                }
                mPresenter.updateTask(selectedDates);
            }
        });

        View parent = (View) view.getParent();
        BottomSheetBehavior behavior = BottomSheetBehavior.from(parent);
        behavior.setHideable(false);

        mBottomSheetDialog.show();
    }

    private void hideBottomSheetDialog() {
        if (mBottomSheetDialog != null && mBottomSheetDialog.isShowing()) {
            mBottomSheetDialog.cancel();
        }

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
            if (startDate != null && (task.isMilestone() || (endDate != null && DateUtil.isSameDay(startDate, endDate)))) {
                dateStingBuilder.append(DateUtil.getStringDateByFormat(startDate, "M.d"));
            } else {
                dateStingBuilder.append(startDate == null ? "NA" : DateUtil.getStringDateByFormat(startDate, "M.d"))
                        .append("-")
                        .append(endDate == null ? "NA" : DateUtil.getStringDateByFormat(endDate, "M.d"));
            }
            return dateStingBuilder.toString();
        }

        public interface IViewHolderClick {
            void onTaskClick(Task task);
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
