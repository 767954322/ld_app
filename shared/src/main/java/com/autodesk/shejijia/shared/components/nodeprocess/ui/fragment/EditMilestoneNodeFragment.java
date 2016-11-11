package com.autodesk.shejijia.shared.components.nodeprocess.ui.fragment;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Task;
import com.autodesk.shejijia.shared.components.common.uielements.ConProgressDialog;
import com.autodesk.shejijia.shared.components.common.uielements.calanderview.CalendarDay;
import com.autodesk.shejijia.shared.components.common.uielements.calanderview.MaterialCalendarView;
import com.autodesk.shejijia.shared.components.common.uielements.calanderview.OnDateSelectedListener;
import com.autodesk.shejijia.shared.components.common.utility.DateUtil;
import com.autodesk.shejijia.shared.components.nodeprocess.contract.EditPlanContract;
import com.autodesk.shejijia.shared.components.nodeprocess.ui.widgets.calendar.ActiveMileStoneDecorator;
import com.autodesk.shejijia.shared.components.nodeprocess.ui.widgets.calendar.DateSelectorDecorator;
import com.autodesk.shejijia.shared.components.nodeprocess.ui.widgets.calendar.MileStoneDayFormatter;
import com.autodesk.shejijia.shared.components.nodeprocess.ui.widgets.calendar.MileStoneNodeDecorator;
import com.autodesk.shejijia.shared.components.nodeprocess.ui.widgets.calendar.WeekDayFormatter;
import com.autodesk.shejijia.shared.framework.fragment.BaseFragment;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by wenhulin on 11/2/16.
 */

public class EditMilestoneNodeFragment extends BaseFragment implements EditPlanContract.View, OnDateSelectedListener {

    private MaterialCalendarView mCalendarWidget;
    private MileStoneNodeDecorator mMileStoneDecorator;
    private DateSelectorDecorator mSelectorDecorator;
    private ActiveMileStoneDecorator mMileStoneActiveDecorator;
    private MileStoneDayFormatter mMileStoneDayFormator;

    private EditPlanContract.Presenter mPresenter;
    private ConProgressDialog mProgressDialog;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_edit_milestone;
    }

    @Override
    protected void initView() {
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.edit_plan_title_first_step);
        }

        initCalendarView();
    }

    @Override
    protected void initData() {
        showLoading();
        mPresenter.fetchPlan();
    }

    @Override
    public void showTasks(List<Task> tasks) {
        hideLoading();
        mCalendarWidget.setVisibility(View.VISIBLE);
        mMileStoneDecorator.setData(tasks);
        mMileStoneDayFormator.setData(tasks);

        // set date limit
        Date startDate = DateUtil.iso8601ToDate(tasks.get(0).getPlanningTime().getStart());
        Date endDate = DateUtil.iso8601ToDate(tasks.get(tasks.size() - 1).getPlanningTime().getCompletion());

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

        mCalendarWidget.state()
                .edit()
                .setMinimumDate(minDate)
                .setMaximumDate(maxDate)
                .commit();
        mCalendarWidget.setCurrentDate(startDate);
    }

    @Override
    public void bindPresenter(EditPlanContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showActiveTask(Task task) {
        //TODO disable some dates according to active task
        mMileStoneActiveDecorator.setActiveTask(task);
        mCalendarWidget.invalidateDecorators();
    }

    @Override
    public void onTaskDateChange(Task task, Date oldDate, Date newDate) {
        mMileStoneDecorator.updateTask(task, oldDate, newDate);
        mMileStoneDayFormator.updateTask(task, oldDate, newDate);
        mCalendarWidget.invalidateDecorators();
    }

    @Override
    public void onCommitSuccess() {
        //TODO Do refactor
//        getActivity().finish();
    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        mPresenter.updateTask(date.getDate());
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

    private void initCalendarView() {
        mCalendarWidget = (MaterialCalendarView) rootView.findViewById(R.id.calendarView);
        mMileStoneDayFormator = new MileStoneDayFormatter();
        mMileStoneDecorator = new MileStoneNodeDecorator(getActivity());
        mSelectorDecorator = new DateSelectorDecorator(getActivity());
        mMileStoneActiveDecorator = new ActiveMileStoneDecorator(getActivity());
        mCalendarWidget.addDecorators(mSelectorDecorator,
                mMileStoneActiveDecorator,
                mMileStoneDecorator);
        mCalendarWidget.setSelectionMode(MaterialCalendarView.SELECTION_MODE_SINGLE);
        mCalendarWidget.setWeekDayFormatter(new WeekDayFormatter(getContext()));
        mCalendarWidget.setDayFormatter(mMileStoneDayFormator);
        mCalendarWidget.setOnDateChangedListener(this);
    }
}
