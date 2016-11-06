package com.autodesk.shejijia.shared.components.nodeprocess.ui.fragment;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.nodeprocess.contract.EditPlanContract;
import com.autodesk.shejijia.shared.components.nodeprocess.ui.widgets.calendar.MileStoneDayFormator;
import com.autodesk.shejijia.shared.components.nodeprocess.ui.widgets.calendar.DateSelectorDecorator;
import com.autodesk.shejijia.shared.components.nodeprocess.ui.widgets.calendar.ActiveMileStoneDecorator;
import com.autodesk.shejijia.shared.components.nodeprocess.ui.widgets.calendar.MileStoneNodeDecorator;
import com.autodesk.shejijia.shared.components.nodeprocess.ui.widgets.calendar.WeekDayFormatter;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Task;
import com.autodesk.shejijia.shared.components.common.uielements.calanderview.MaterialCalendarView;
import com.autodesk.shejijia.shared.framework.fragment.BaseFragment;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by wenhulin on 11/2/16.
 */

public class EditMilestoneNodeFragment extends BaseFragment implements EditPlanContract.View {

    private MaterialCalendarView widget;
    private MileStoneNodeDecorator mMileStoneDecorator;
    private DateSelectorDecorator mSelectorDecorator;
    private ActiveMileStoneDecorator mMileStoneActiveDecorator;
    private MileStoneDayFormator mMileStoneDayFormator;

    private EditPlanContract.Presenter mPresenter;

    //TODO will remove this test data later
    private static final class MockupTask {
        Date date;
        String name;

        MockupTask(Date date, String name) {
            this.date = date;
            this.name = name;
        }
    }


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

        widget = (MaterialCalendarView) rootView.findViewById(R.id.calendarView);
        setUpCalendarView();
    }

    @Override
    protected void initData() {
        mPresenter.fetchPlan();
    }

    private void setUpCalendarView() {
        mMileStoneDayFormator = new MileStoneDayFormator();
        mMileStoneDecorator = new MileStoneNodeDecorator(getActivity());
        mSelectorDecorator = new DateSelectorDecorator(getActivity(), false);
        mMileStoneActiveDecorator = new ActiveMileStoneDecorator(getActivity());
        widget.addDecorator(mSelectorDecorator);
        widget.addDecorator(mMileStoneActiveDecorator);
        widget.addDecorator(mMileStoneDecorator);
        widget.setSelectionMode(MaterialCalendarView.SELECTION_MODE_SINGLE);
        widget.setWeekDayFormatter(new WeekDayFormatter(getContext()));
        widget.setDayFormatter(mMileStoneDayFormator);


        //TODO setup date limit
        Calendar instance1 = Calendar.getInstance();
        instance1.set(instance1.get(Calendar.YEAR), Calendar.OCTOBER, 1);

        Calendar instance2 = Calendar.getInstance();
        instance2.set(instance2.get(Calendar.YEAR), Calendar.DECEMBER, 31);
        widget.state()
                .edit()
                .setMinimumDate(instance1.getTime())
                .setMaximumDate(instance2.getTime())
                .commit();
    }

    @Override
    public void showTasks(List<Task> tasks) {
        // TODO show plan on calendar view
        mMileStoneDecorator.setData(tasks);
        mMileStoneDayFormator.setData(tasks);
        widget.invalidateDecorators(); // TODO
    }

    @Override
    public void bindPresenter(EditPlanContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showNetError(String msg) {
        AppCompatButton a;
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
}
