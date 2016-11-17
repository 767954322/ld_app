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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import com.autodesk.shejijia.shared.components.form.common.constant.TaskStatusTypeEnum;
import com.autodesk.shejijia.shared.components.nodeprocess.contract.EditPlanContract;
import com.autodesk.shejijia.shared.components.nodeprocess.contract.EditPlanContract.TaskNodePresenter;
import com.autodesk.shejijia.shared.components.nodeprocess.presenter.EditTaskNodePresenter;
import com.autodesk.shejijia.shared.components.nodeprocess.ui.widgets.calendar.ActiveMileStoneDecorator;
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

public class EditTaskNodeFragment extends BaseFragment implements EditPlanContract.TaskNodeView {
    private TaskNodePresenter mPresenter;
    private TaskNodeAdapter mAdapter;

    private BottomSheetDialog mBottomSheetDialog;
    private MaterialCalendarView mCalendarView;

    private ConProgressDialog mProgressDialog;

    private Menu mMenu;

    @Override
    public void showTasks(List<Task> tasks) {
        mAdapter.setData(tasks);
    }

    public EditPlanContract.TaskNodePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void onCommitSuccess() {
        getActivity().setResult(Activity.RESULT_OK);
        getActivity().finish();
    }

    @Override
    public void onCommitError(String error) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.alert_dialog__default_title);
        builder.setMessage(error);
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing
            }
        });
        builder.setPositiveButton(R.string.retry, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mPresenter.commitPlan();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void updateFilterIcon(int icon) {
        if (mMenu != null) {
            MenuItem item = mMenu.findItem(R.id.menu_filter);
            if (item != null) {
                item.setIcon(icon);
            }
        }
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
        setHasOptionsMenu(true);

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new TaskNodeAdapter(getActivity(), new TaskNodeAdapter.ItemClickListener() {
            @Override
            public void onTaskClick(Task task) {
                mPresenter.editTaskNode(task);
            }
        });
        recyclerView.setAdapter(mAdapter);

        initBottomSheetDialog();
    }

    @Override
    protected void initData() {
        mPresenter = new EditTaskNodePresenter(getActivity().getIntent().getStringExtra("pid"));
        mPresenter.bindView(this);
        mPresenter.fetchPlan();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.edit_task_node, menu);
        mMenu = menu;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == R.id.menu_add_task) {
            return true;
        } else if (i == R.id.menu_filter_task_all) {
            mPresenter.filterTasks(TaskNodePresenter.TaskFilterType.ALL_TASKS);
            return true;
        } else if (i == R.id.menu_filter_task_construction) {
            mPresenter.filterTasks(TaskNodePresenter.TaskFilterType.CONSTRUCTION_TASKS);
            return true;
        } else if (i == R.id.menu_filter_task_material) {
            mPresenter.filterTasks(TaskNodePresenter.TaskFilterType.MATERIAL_TASKS);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
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

    @Override
    public void showBottomSheet(List<Task> milstoneTasks, Task task) {
        View view  = LayoutInflater.from(getActivity()).inflate(R.layout.bottom_sheet_edit_task_node, null);
        mBottomSheetDialog.setContentView(view);
        mCalendarView = (MaterialCalendarView) view.findViewById(R.id.calendarView);

        setupBottomSheetHeader(view, task);
        setupCalendarView(milstoneTasks, task);

        View parent = (View) view.getParent();
        BottomSheetBehavior behavior = BottomSheetBehavior.from(parent);
        behavior.setHideable(false);

        mBottomSheetDialog.show();
    }

    @Override
    public void showUpLoading() {
        if (mProgressDialog == null) {
            mProgressDialog = new ConProgressDialog(getActivity());
            mProgressDialog.setMessage(getString(R.string.autonym_uploading));
        }

        mProgressDialog.show();
    }

    @Override
    public void hideUpLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.cancel();
        }
    }

    private void initBottomSheetDialog() {
        mBottomSheetDialog = new BottomSheetDialog(getActivity(),
                R.style.BottomSheetDialogTheme_Calendar);
    }

    private void hideBottomSheetDialog() {
        if (mBottomSheetDialog != null && mBottomSheetDialog.isShowing()) {
            mBottomSheetDialog.cancel();
        }
    }

    private void setupBottomSheetHeader(View parentView, Task task) {
        TextView taskNameView = (TextView) parentView.findViewById(R.id.tv_task_name);
        taskNameView.setText(task.getName());

        TextView actionBtn = (TextView) parentView.findViewById(R.id.tv_action);
        actionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideBottomSheetDialog();
                List<CalendarDay> selectedDays = mCalendarView.getSelectedDates();
                List<Date> selectedDates = new ArrayList<>();
                for (CalendarDay day: selectedDays) {
                    selectedDates.add(day.getDate());
                }
                mPresenter.updateTask(selectedDates);
            }
        });
    }

    private void setupCalendarView(List<Task> milstoneTasks, Task task) {
        mCalendarView.setSelectionMode(MaterialCalendarView.SELECTION_MODE_RANGE);

        // Set formatter
        MileStoneDayFormatter mileStoneDayFormatter = new MileStoneDayFormatter();
        mileStoneDayFormatter.setData(milstoneTasks);
        mCalendarView.setDayFormatter(mileStoneDayFormatter);

        // Set decorators
        mCalendarView.removeDecorators();
        MileStoneNodeDecorator mileStoneDecorator = new MileStoneNodeDecorator(getActivity());
        DateSelectorDecorator selectorDecorator = new DateSelectorDecorator(getActivity());
        ActiveMileStoneDecorator activeMileStoneDecorator = new ActiveMileStoneDecorator(getActivity());
        activeMileStoneDecorator.setActiveTask(milstoneTasks, task);
        mileStoneDecorator.setData(milstoneTasks);
        mCalendarView.addDecorators(selectorDecorator,
                activeMileStoneDecorator,
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

        // set selected days
        if (DateUtil.isSameDay(taskStartDay, taskEndDay)) {
            mCalendarView.setSelectedDate(taskStartDay);
        } else if (taskStartDay != null){
            mCalendarView.setSelectedRange(taskStartDay, taskEndDay);
        }
    }

    private static class TaskNodeAdapter extends RecyclerView.Adapter<TaskNodeViewHolder> {
        private final static int VIEW_TYPE_MILESTONE_NODE = 0;
        private final static int VIEW_TYPE_TASK_NODE = 1;

        private Activity mActivity;
        private List<Task> mTasks = new ArrayList<>();

        private ItemClickListener mClickListener;

        TaskNodeAdapter(Activity activity, ItemClickListener clickListener) {
            mActivity = activity;
            mClickListener = clickListener;
        }

        void setData(List<Task> tasks) {
//            mTasks.clear();
//            mTasks.addAll(tasks);
//            notifyItemRangeChanged(0, mTasks.size());
//            notifyDataSetChanged();
            animateTo(tasks);
        }

        void animateTo(List<Task> models) {
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

        Task removeItem(int position) {
            final Task task = mTasks.remove(position);
            notifyItemRemoved(position);
            return task;
        }

        void addItem(int position, Task model) {
            mTasks.add(position, model);
            notifyItemInserted(position);
        }

        void moveItem(int fromPosition, int toPosition) {
            final Task task = mTasks.remove(fromPosition);
            mTasks.add(toPosition, task);
            notifyItemMoved(fromPosition, toPosition);
        }

        void updateItem(int position) {
            notifyItemChanged(position);
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

            if (task.getStatus().equalsIgnoreCase(TaskStatusTypeEnum.TASK_STATUS_RESOLVED.getTaskStatus())) {
                holder.itemView.setEnabled(false);
                holder.mTvNodeName.setEnabled(false);
                holder.mTvNodeTime.setEnabled(false);
            } else {
                holder.itemView.setEnabled(true);
                holder.mTvNodeName.setEnabled(true);
                holder.mTvNodeTime.setEnabled(true);
            }
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

        interface ItemClickListener {
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
