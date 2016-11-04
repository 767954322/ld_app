package com.autodesk.shejijia.shared.components.nodeprocess.plan;

import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;

import com.autodesk.shejijia.enterprise.R;
import com.autodesk.shejijia.shared.components.nodeprocess.plan.widgets.calendar.DateSelectorDecorator;
import com.autodesk.shejijia.shared.components.nodeprocess.plan.widgets.calendar.MileStoneActiveDecorator;
import com.autodesk.shejijia.shared.components.nodeprocess.plan.widgets.calendar.MileStoneNodeDecorator;
import com.autodesk.shejijia.shared.components.nodeprocess.plan.widgets.calendar.TaskNodeDecorator;
import com.autodesk.shejijia.shared.components.nodeprocess.plan.widgets.calendar.WeekDayFormatter;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Task;
import com.autodesk.shejijia.shared.components.common.uielements.calanderview.CalendarDay;
import com.autodesk.shejijia.shared.components.common.uielements.calanderview.MaterialCalendarView;
import com.autodesk.shejijia.shared.components.common.uielements.calanderview.format.DayFormatter;
import com.autodesk.shejijia.shared.framework.fragment.BaseFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by wenhulin on 11/2/16.
 */

public class EditMilestoneNodeFragment extends BaseFragment implements EditPlanContract.View {

    private MaterialCalendarView widget;
    private List<MockupTask> mMileStoneNodes = new ArrayList<>();

    private TaskNodeDecorator mDemoDecorator;
    private MileStoneNodeDecorator mDemoMileStoneDecorator;
    private DateSelectorDecorator mDemoSelectorDecorator;
    private MileStoneActiveDecorator mDemoMileStoneActiveDecorator;

    //TODO will remove this test data later
    private static final class MockupTask {
        Date date;
        String name;

        MockupTask(Date date, String name) {
            this.date = date;
            this.name = name;
        }
    }

    private class DemoMileStoneDayFormator implements DayFormatter {

        @NonNull
        @Override
        public String format(@NonNull CalendarDay day) {
            for(MockupTask task: mMileStoneNodes) {
                if (CalendarDay.from(task.date).equals(day)) {
                    return task.name;
                }
            }
            return DayFormatter.DEFAULT.format(day);
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
            actionBar.setTitle("第一步: 调整验收节点时间");  //TODO localize string
        }

        widget = (MaterialCalendarView) rootView.findViewById(R.id.calendarView);

        //TODO  remove mockup data
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            mMileStoneNodes.add(new MockupTask(formatter.parse("20/10/2016"), "开工\n交底"));
            mMileStoneNodes.add(new MockupTask(formatter.parse("31/10/2016"), "隐蔽工程验收"));
            mMileStoneNodes.add(new MockupTask(formatter.parse("15/11/2016"), "闭水验收"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        widget.setSelectionMode(MaterialCalendarView.SELECTION_MODE_SINGLE);
        widget.setWeekDayFormatter(new WeekDayFormatter(getContext()));
        widget.setDayFormatter(new DemoMileStoneDayFormator());
        mDemoMileStoneDecorator = new MileStoneNodeDecorator(getActivity());
        mDemoSelectorDecorator = new DateSelectorDecorator(getActivity(), false);
        mDemoMileStoneActiveDecorator = new MileStoneActiveDecorator(getActivity());
        for(MockupTask task: mMileStoneNodes) {
            mDemoMileStoneDecorator.addDate(task.date);
            mDemoSelectorDecorator.addDate(task.date);
        }
        widget.addDecorator(mDemoSelectorDecorator);
//        widget.addDecorator(mDemoMileStoneActiveDecorator);
        widget.addDecorator(mDemoMileStoneDecorator);


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
    protected void initData() {

    }

    @Override
    public void showTasks(List<Task> tasks) {
        // TODO show plan on calendar view
    }

    @Override
    public void bindPresenter(EditPlanContract.Presenter presenter) {

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
