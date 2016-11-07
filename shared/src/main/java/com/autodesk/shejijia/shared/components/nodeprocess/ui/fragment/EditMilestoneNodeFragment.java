package com.autodesk.shejijia.shared.components.nodeprocess.ui.fragment;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Task;
import com.autodesk.shejijia.shared.components.common.uielements.calanderview.MaterialCalendarView;
import com.autodesk.shejijia.shared.components.common.utility.DateUtil;
import com.autodesk.shejijia.shared.components.nodeprocess.contract.EditPlanContract;
import com.autodesk.shejijia.shared.components.nodeprocess.ui.widgets.calendar.ActiveMileStoneDecorator;
import com.autodesk.shejijia.shared.components.nodeprocess.ui.widgets.calendar.DateSelectorDecorator;
import com.autodesk.shejijia.shared.components.nodeprocess.ui.widgets.calendar.MileStoneDayFormator;
import com.autodesk.shejijia.shared.components.nodeprocess.ui.widgets.calendar.MileStoneNodeDecorator;
import com.autodesk.shejijia.shared.components.nodeprocess.ui.widgets.calendar.WeekDayFormatter;
import com.autodesk.shejijia.shared.framework.fragment.BaseFragment;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by wenhulin on 11/2/16.
 */

public class EditMilestoneNodeFragment extends BaseFragment implements EditPlanContract.View {

    private MaterialCalendarView mCalendarWidget;
    private MileStoneNodeDecorator mMileStoneDecorator;
    private DateSelectorDecorator mSelectorDecorator;
    private ActiveMileStoneDecorator mMileStoneActiveDecorator;
    private MileStoneDayFormator mMileStoneDayFormator;

    private EditPlanContract.Presenter mPresenter;

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

        mCalendarWidget = (MaterialCalendarView) rootView.findViewById(R.id.calendarView);
        setUpCalendarView();
    }

    @Override
    protected void initData() {
        mPresenter.fetchPlan();
    }

    @Override
    public void showTasks(List<Task> tasks) {
        mCalendarWidget.setVisibility(View.VISIBLE);
        mMileStoneDecorator.setData(tasks);
        mMileStoneDayFormator.setData(tasks);

        Date startDate = DateUtil.isoStringToDate(tasks.get(0).getPlanningTime().getStart());
        Date endDate = DateUtil.isoStringToDate(tasks.get(tasks.size() - 1).getPlanningTime().getCompletion());

        int limitMonthOffset = 6;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.add(Calendar.MONTH, -limitMonthOffset);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1);
        Date minDate = calendar.getTime();

        calendar.setTime(endDate);
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

    private void setUpCalendarView() {
        mMileStoneDayFormator = new MileStoneDayFormator();
        mMileStoneDecorator = new MileStoneNodeDecorator(getActivity());
        mSelectorDecorator = new DateSelectorDecorator(getActivity(), false);
        mMileStoneActiveDecorator = new ActiveMileStoneDecorator(getActivity());
        mCalendarWidget.addDecorator(mSelectorDecorator);
        mCalendarWidget.addDecorator(mMileStoneActiveDecorator);
        mCalendarWidget.addDecorator(mMileStoneDecorator);
        mCalendarWidget.setSelectionMode(MaterialCalendarView.SELECTION_MODE_SINGLE);
        mCalendarWidget.setWeekDayFormatter(new WeekDayFormatter(getContext()));
        mCalendarWidget.setDayFormatter(mMileStoneDayFormator);
    }
}
