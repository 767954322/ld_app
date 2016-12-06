package com.autodesk.shejijia.shared.components.common.uielements;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.appglobal.ConstructionConstants;
import com.autodesk.shejijia.shared.components.common.uielements.calanderview.CalendarDay;
import com.autodesk.shejijia.shared.components.common.uielements.calanderview.DayViewDecorator;
import com.autodesk.shejijia.shared.components.common.uielements.calanderview.MaterialCalendarView;
import com.autodesk.shejijia.shared.components.common.uielements.calanderview.format.DayFormatter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Custom pick date dialog, support single and range mode.
 * if start this fragment from an activity, activity must implements callback interface
 * if start this fragment from a fragment, callback will in fragment.onActivityResult
 * Please refer usage in {@link com.autodesk.shejijia.shared.components.nodeprocess.ui.fragment.EditTaskNodeFragment}
 *
 * @author wenhulin
 * @since 11/20/16
 */

public class PickDateDialogFragment extends AppCompatDialogFragment implements View.OnClickListener {
    public final static String BUNDLE_KEY_SELECTED_DATE = "selected_date";
    public final static String BUNDLE_KEY_SELECTED_RANGE = "selected_dates";

    private final static String BUNDLE_KEY_TITLE = "title";
    private final static String BUNDLE_KEY_SELECTION_MODE = "selection_mode";
    private final static String BUNDLE_KEY_DECORATIORS = "decorators";
    private final static String BUNDLE_KEY_FORMATTER = "formatter";
    private final static String BUNDLE_KEY_MIN_DATE = "min_date";
    private final static String BUNDLE_KEY_MAX_DATE = "max_date";
    private final static String BUNDLE_KEY_CUR_DATE = "cur_date";
    private final static String BUNDLE_KEY_SELECTED_RANGE_START = "selected_range_start";
    private final static String BUNDLE_KEY_SELECTED_RANGE_END = "selected_range_end";

    private MaterialCalendarView mCalendarView;
    private TextView mTitleTextView;

    private OnDateSelectedCallback mOnDateSelectedCallback;
    private OnDateRangeSelectedCallback mOnDatesSelectedCallback;

    public interface OnDateSelectedCallback {
        void onDateSelected(Date date);
    }

    public interface OnDateRangeSelectedCallback {
        void onDateRangeSelected(Date startDate, Date endDate);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        setRetainInstance(false);

        Fragment parentFragment = getParentFragment();
        if (parentFragment == null) {
            if (activity instanceof OnDateSelectedCallback) {
                mOnDateSelectedCallback = (OnDateSelectedCallback) activity;
            }

            if (activity instanceof OnDateRangeSelectedCallback) {
                mOnDatesSelectedCallback = (OnDateRangeSelectedCallback) activity;
            }
        } else {
            if (parentFragment instanceof OnDateSelectedCallback) {
                mOnDateSelectedCallback = (OnDateSelectedCallback) parentFragment;
            }

            if (parentFragment instanceof OnDateRangeSelectedCallback) {
                mOnDatesSelectedCallback = (OnDateRangeSelectedCallback) parentFragment;
            }
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new BottomSheetDialog(getActivity(), R.style.BottomSheetDialogTheme_Calendar);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View bottomSheet = getDialog().findViewById(android.support.design.R.id.design_bottom_sheet);
        BottomSheetBehavior.from(bottomSheet).setHideable(false);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_pick_date_dialog, container, false);

        mTitleTextView = (TextView) view.findViewById(R.id.tv_title);
        TextView actionBtn = (TextView) view.findViewById(R.id.btn_action);
        actionBtn.setOnClickListener(this);

        mCalendarView = (MaterialCalendarView) view.findViewById(R.id.calendarView);
        setupCalendarView();
        return view;
    }

    @Override
    public void onClick(View v) {
        if (R.id.btn_action == v.getId()) {
            dismiss();

            if (mCalendarView.getSelectionMode() == MaterialCalendarView.SELECTION_MODE_SINGLE) {
                CalendarDay selectedDate = mCalendarView.getSelectedDate();
                if (selectedDate == null) {
                    return;
                }

                Date date = selectedDate.getDate();

                if (mOnDateSelectedCallback != null) {
                    mOnDateSelectedCallback.onDateSelected(date);
                } else if (getTargetFragment() != null) {
                    Intent data = new Intent();
                    data.putExtra(BUNDLE_KEY_SELECTED_DATE, date);
                    getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, data);
                } else if (getParentFragment() != null) {
                    Intent data = new Intent();
                    data.putExtra(BUNDLE_KEY_SELECTED_DATE, date);
                    getParentFragment().onActivityResult(ConstructionConstants.REQUEST_CODE_PICK_DATE, Activity.RESULT_OK, data);
                }
            } else if (mCalendarView.getSelectionMode() == MaterialCalendarView.SELECTION_MODE_RANGE) {
                List<CalendarDay> selectedDays = mCalendarView.getSelectedDates();
                if (selectedDays.isEmpty()) {
                    return;
                }

                ArrayList<Date> selectedDates = new ArrayList<>();
                for (CalendarDay day : selectedDays) {
                    selectedDates.add(day.getDate());
                }

                Collections.sort(selectedDates);
                if (mOnDatesSelectedCallback != null) {
                    mOnDatesSelectedCallback.onDateRangeSelected(selectedDates.get(0),
                            selectedDates.get(selectedDates.size() - 1));
                } else if (getTargetFragment() != null) {
                    Intent data = new Intent();
                    data.putExtra(BUNDLE_KEY_SELECTED_RANGE, selectedDates);
                    getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, data);
                } else if (getParentFragment() != null) {
                    Intent data = new Intent();
                    data.putExtra(BUNDLE_KEY_SELECTED_RANGE, selectedDates);
                    getParentFragment().onActivityResult(ConstructionConstants.REQUEST_CODE_PICK_DATE_RANGE, Activity.RESULT_OK, data);
                }
            }
        }
    }

    private void setupCalendarView() {
        Bundle args = getArguments();
        mTitleTextView.setText(args.getString(BUNDLE_KEY_TITLE));
        //noinspection WrongConstant
        mCalendarView.setSelectionMode(args.getInt(BUNDLE_KEY_SELECTION_MODE, MaterialCalendarView.SELECTION_MODE_SINGLE));

        if (args.containsKey(BUNDLE_KEY_FORMATTER)) {
            mCalendarView.setDayFormatter((DayFormatter) args.getSerializable(BUNDLE_KEY_FORMATTER));
        }

        if (args.containsKey(BUNDLE_KEY_DECORATIORS)) {
            mCalendarView.addDecorators((DayViewDecorator[]) args.getSerializable(BUNDLE_KEY_DECORATIORS));
        }

        if (args.containsKey(BUNDLE_KEY_MIN_DATE) && args.containsKey(BUNDLE_KEY_MAX_DATE)) {
            mCalendarView.state()
                    .edit()
                    .setMinimumDate((Date) args.getSerializable(BUNDLE_KEY_MIN_DATE))
                    .setMaximumDate((Date) args.getSerializable(BUNDLE_KEY_MAX_DATE))
                    .commit();
        }

        if (args.containsKey(BUNDLE_KEY_CUR_DATE)) {
            mCalendarView.setCurrentDate((Date) args.getSerializable(BUNDLE_KEY_CUR_DATE));
        }

        if (args.containsKey(BUNDLE_KEY_SELECTED_DATE)) {
            mCalendarView.setSelectedDate((Date) args.getSerializable(BUNDLE_KEY_SELECTED_DATE));
        }

        if (args.containsKey(BUNDLE_KEY_SELECTED_RANGE_START) && args.containsKey(BUNDLE_KEY_SELECTED_RANGE_END)) {
            mCalendarView.setSelectedRange((Date) args.getSerializable(BUNDLE_KEY_SELECTED_RANGE_START),
                    (Date) args.getSerializable(BUNDLE_KEY_SELECTED_RANGE_END));
        }
    }

    public static class Builder {
        Bundle mBundle = new Bundle();

        public Builder(@NonNull String title) {
            mBundle.putString(BUNDLE_KEY_TITLE, title);
        }

        public Builder setSelectionMode(@MaterialCalendarView.SelectionMode int mode) {
            if (mode != MaterialCalendarView.SELECTION_MODE_SINGLE
                    && mode != MaterialCalendarView.SELECTION_MODE_RANGE) {
                throw new RuntimeException("Not supported selection mode!");
            }
            mBundle.putInt(BUNDLE_KEY_SELECTION_MODE, mode);
            return this;
        }

        public Builder setDayFormatter(@NonNull DayFormatter formatter) {
            mBundle.putSerializable(BUNDLE_KEY_FORMATTER, formatter);
            return this;
        }

        public Builder addDecorators(DayViewDecorator... decorators) {
            mBundle.putSerializable(BUNDLE_KEY_DECORATIORS, decorators);
            return this;
        }

        public Builder setDateLimit(@NonNull Date minDate, @NonNull Date maxDate) {
            mBundle.putSerializable(BUNDLE_KEY_MIN_DATE, minDate);
            mBundle.putSerializable(BUNDLE_KEY_MAX_DATE, maxDate);
            return this;
        }

        public Builder setCurrentDate(@Nullable Date date) {
            mBundle.putSerializable(BUNDLE_KEY_CUR_DATE, date);
            return this;
        }

        public Builder setSelectedDate(@Nullable Date date) {
            mBundle.putSerializable(BUNDLE_KEY_SELECTED_DATE, date);
            return this;
        }

        public Builder setSelectedRange(@NonNull Date startDate, @NonNull Date endDate) {
            mBundle.putSerializable(BUNDLE_KEY_SELECTED_RANGE_START, startDate);
            mBundle.putSerializable(BUNDLE_KEY_SELECTED_RANGE_END, endDate);
            return this;
        }

        public PickDateDialogFragment create() {
            PickDateDialogFragment pickDateDialogFragment = new PickDateDialogFragment();
            pickDateDialogFragment.setArguments(mBundle);
            return pickDateDialogFragment;
        }

    }
}
