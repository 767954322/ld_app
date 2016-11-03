package com.autodesk.shejijia.shared.components.common.uielements.reusewheel.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.components.common.uielements.reusewheel.libs.WheelView;
import com.autodesk.shejijia.shared.components.common.uielements.reusewheel.timepicker.BasePickerView;
import com.autodesk.shejijia.shared.components.common.uielements.reusewheel.timepicker.WheelTime;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by yaoxuehua on 16-6-17.
 */
public class TimePickerView extends BasePickerView implements View.OnClickListener {

    private GestureDetector gestureDetector;

    public enum Type {
        ALL, YEAR_MONTH_DAY, HOURS_MINS, MONTH_DAY_HOUR_MIN, YEAR_MONTH
    }// 四种选择模式，年月日时分，年月日，时分，月日时分

    WheelTime wheelTime;
    private Button btnSubmit, btnCancel;
    private TextView tvTitle;
    WheelView day;
    WheelView year;
    WheelView month;
    WheelView hour;
    WheelView minute;
    private Long endTime;
    private Long currentTime = 0l;
    private static final String TAG_SUBMIT = "submit";
    private static final String TAG_CANCEL = "cancel";
    private OnTimeSelectListener timeSelectListener;
    private Handler handler;
    private Timer timer;
    private TimerTask timerTask;
    private Date dateCurrentTime;

    private boolean justOnlyone = true;
    private boolean justOnlyTwo = true;

    public TimePickerView(Context context, final Type type) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.view_pickerview_time, contentContainer);
        // -----确定和取消按钮
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        day = (WheelView) findViewById(R.id.day);
        year = (WheelView) findViewById(R.id.year);
        month = (WheelView) findViewById(R.id.month);
        minute = (WheelView) findViewById(R.id.min);
        hour = (WheelView) findViewById(R.id.hour);
        minute.setVisibility(View.GONE);
        /**
         * 检查flying后的选中是否为合适的
         * */
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                switch (msg.what) {

                    case 0:

                        isMatchCurrentTime();
                        break;
                }

            }
        };
        //给每个数列监听
        setSureButtonListener(day);
        setSureButtonListener(year);
        setSureButtonListener(minute);
        setSureButtonListener(month);
        setSureButtonListener(hour);

        btnSubmit.setTag(TAG_SUBMIT);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnCancel.setTag(TAG_CANCEL);
        btnSubmit.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        //顶部标题
        tvTitle = (TextView) findViewById(R.id.tvTitle);

        // ----时间转轮
        final View timepickerview = findViewById(R.id.timepicker);
        wheelTime = new WheelTime(timepickerview, type);


        //默认选中当前时间
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        wheelTime.setPicker(year, month, day, hours, minute);

    }

    /**
     * 设置可以选择的时间范围
     *
     * @param startYear
     * @param endYear
     */
    public void setRange(int startYear, int endYear) {
        wheelTime.setStartYear(startYear);
        wheelTime.setEndYear(endYear);
    }

    /**
     * 判断选中时间是否为最终时间
     */

    public void justMethod() {

        timerTask = new TimerTask() {
            @Override
            public void run() {
                Message message = Message.obtain();
                message.what = 0;
                handler.sendMessage(message);
            }
        };

        if (timer == null) {

            timer = new Timer();
            timer.scheduleAtFixedRate(timerTask, 100, 100);
        }

    }

    /**
     * 设置选中时间
     *
     * @param date
     */
    public void setTime(Date date) {

        Calendar calendar = Calendar.getInstance();
        if (date == null) {
            calendar.setTimeInMillis(System.currentTimeMillis());
        } else {

            calendar.setTime(date);
        }
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        wheelTime.setPicker(year, month, day, hours + getAddTime(), minute);

    }


    public int getAddTime() {
        int minute = getMinute();
        if (minute > 0) {
            return 3;
        } else {
            return 2;
        }


    }

    /**
     * 功能描述：返回分
     *
     * @return 返回分钟
     */
    public static int getMinute() {
        Date date = new Date(System.currentTimeMillis());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MINUTE);
    }

    /**
     * 判断选中时间，是否是当前时间的两小时之后
     */

    private void isMatchCurrentTime() {

        try {
            Date date = WheelTime.dateFormat.parse(wheelTime.getTime());
//            currentTime = System.currentTimeMillis();
//            Long a = 3600*1000*2L;
//            currentTime = currentTime + a;
            if (justOnlyone) {
                dateCurrentTime = WheelTime.dateFormat.parse(wheelTime.getTime());
                currentTime = dateCurrentTime.getTime() + currentTime;
                justOnlyone = false;

            }
            endTime = date.getTime();
            Log.i("yaoxuehua--currentTime", "" + currentTime);
            Log.i("yaoxuehua--endTime", "" + endTime);
            if (endTime >= currentTime) {

                btnSubmit.setTextColor(UIUtils.getColor(R.color.pickerview_timebtn_nor));
                btnSubmit.setClickable(true);
            } else {
                btnSubmit.setTextColor(UIUtils.getColor(R.color.pickerview_timebtn_pre));
                btnSubmit.setClickable(false);

            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    /**
     * 设置确定按钮颜色，以及是否可点击
     * pickerview_surebutton_textcolor#dcdcdc
     */
    public void setSureButtonListener(WheelView wheelView) {

        wheelView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_MOVE) {

                    isMatchCurrentTime();

                }
                if (event.getAction() == MotionEvent.ACTION_DOWN) {

                    isMatchCurrentTime();

                }

                if (event.getAction() == MotionEvent.ACTION_UP) {

                    isMatchCurrentTime();
                    justMethod();

                }


                return false;
            }
        });
    }

//    /**
//     * 指定选中的时间，显示选择器
//     *
//     * @param date
//     */
//    public void show(Date date) {
//        Calendar calendar = Calendar.getInstance();
//        if (date == null)
//            calendar.setTimeInMillis(System.currentTimeMillis());
//        else
//            calendar.setTime(date);
//        int year = calendar.get(Calendar.YEAR);
//        int month = calendar.get(Calendar.MONTH);
//        int day = calendar.get(Calendar.DAY_OF_MONTH);
//        int hours = calendar.get(Calendar.HOUR_OF_DAY);
//        int minute = calendar.get(Calendar.MINUTE);
//        wheelTime.setPicker(year, month, day, hours, minute);
//        show();
//    }

    /**
     * 设置是否循环滚动
     *
     * @param cyclic
     */
    public void setCyclic(boolean cyclic) {
        wheelTime.setCyclic(cyclic);
    }

    @Override
    public void onClick(View v) {
        String tag = (String) v.getTag();
        if (tag.equals(TAG_CANCEL)) {
            dismiss();
            return;
        } else {
            if (timeSelectListener != null) {
                try {
                    Date date = WheelTime.dateFormat.parse(wheelTime.getTime());
                    timeSelectListener.onTimeSelect(date);
                    if (timer != null) {
                        timer.cancel();
                        timer = null;

                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            dismiss();
            return;
        }
    }

    public interface OnTimeSelectListener {
        public void onTimeSelect(Date date);
    }

    public void setOnTimeSelectListener(OnTimeSelectListener timeSelectListener) {
        this.timeSelectListener = timeSelectListener;
    }

    public void setTitle(String title) {
        tvTitle.setText(title);
    }
}

