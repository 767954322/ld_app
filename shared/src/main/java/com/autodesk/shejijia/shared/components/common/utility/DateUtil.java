package com.autodesk.shejijia.shared.components.common.utility;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.autodesk.shejijia.shared.R;

import org.joda.time.DateTime;
import org.joda.time.Instant;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

/**
 * @author  yangxuewu .
 * @version  v1.0 .
 * @date       2016-6-8 .
 * @file          DateUtil.java .
 * @brief       日期转换工具类 .
 */
public class DateUtil {

    private static String TAG = "DateUtil";

    public static String getTimeMY(String dateString) {
        // String dateString = "March 14, 2016 11:08:35";
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy HH:mm:ss", Locale.US);
        TimeZone tz = TimeZone.getTimeZone("UTC");
        sdf.setTimeZone(tz);

        String showTimeme = "";
        Date s;
        try {
            s = sdf.parse(dateString);
            sdf = new SimpleDateFormat("MMMM dd, yyyy HH:mm:ss");
            String dssate = sdf.format(s);
            Date date = sdf.parse(dssate);
            long timeStemp = date.getTime();
            showTimeme = DateUtil.showTime(timeStemp);
            // System.out.println(showTime);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return showTimeme;


    }

    public static String getStringDateByFormat(long lTime, String format) {
        Date currentTime = new Date(lTime);
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        String dateString = formatter.format(currentTime);
        return dateString;
    }


    public static boolean isYestoday(long lTime) {
        Calendar c = Calendar.getInstance();
        Calendar data = Calendar.getInstance();
        data.setTimeInMillis(lTime);
        if (c.get(Calendar.YEAR) != data.get(Calendar.YEAR)) {
            return false;
        }
        int cDay = c.get(Calendar.DAY_OF_YEAR);
        int dataDay = data.get(Calendar.DAY_OF_YEAR);
        return 1 == (cDay - dataDay);

    }

    public static boolean isToday(long lTime) {
        Calendar c = Calendar.getInstance();
        Calendar data = Calendar.getInstance();
        data.setTimeInMillis(lTime);
        if (c.get(Calendar.YEAR) != data.get(Calendar.YEAR)) {
            return false;
        } else if (c.get(Calendar.DAY_OF_YEAR) != data
                .get(Calendar.DAY_OF_YEAR)) {
            return false;
        }
        return true;
    }

    public static boolean isYesterday(Date date) {
        Calendar c = Calendar.getInstance();
        Calendar data = Calendar.getInstance();
        data.setTime(date);
        if (c.get(Calendar.YEAR) != data.get(Calendar.YEAR)) {
            return false;
        }
        int cDay = c.get(Calendar.DAY_OF_YEAR);
        int dataDay = data.get(Calendar.DAY_OF_YEAR);
        return 1 == (cDay - dataDay);
    }

    public static boolean isToday(Date date) {
        Calendar c = Calendar.getInstance();
        Calendar data = Calendar.getInstance();
        data.setTime(date);
        if (c.get(Calendar.YEAR) != data.get(Calendar.YEAR)) {
            return false;
        } else if (c.get(Calendar.DAY_OF_YEAR) != data
                .get(Calendar.DAY_OF_YEAR)) {
            return false;
        }
        return true;
    }

    public static boolean isThisWeek(long lTime) {
        Calendar data = Calendar.getInstance();
        Calendar c = Calendar.getInstance();
        data.setTimeInMillis(lTime);
        if (data.get(Calendar.YEAR) != c.get(Calendar.YEAR)) {
            return false;
        } else if (data.get(Calendar.WEEK_OF_YEAR) == c
                .get(Calendar.WEEK_OF_YEAR)) {
            return true;
        }
        return false;
    }

    public static String getWeekString(long lTime) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(lTime);
        String week = null;
        switch (c.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.SUNDAY:
                week = UIUtils.getString(R.string.sunday);
                break;
            case Calendar.MONDAY:
                week = UIUtils.getString(R.string.monday);
                break;
            case Calendar.TUESDAY:
                week = UIUtils.getString(R.string.tuesday);
                break;
            case Calendar.WEDNESDAY:
                week = UIUtils.getString(R.string.wednesday);
                break;
            case Calendar.THURSDAY:
                week = UIUtils.getString(R.string.thursday);
                break;
            case Calendar.FRIDAY:
                week = UIUtils.getString(R.string.friday);
                break;
            case Calendar.SATURDAY:
                week = UIUtils.getString(R.string.saturday);
                break;
            default:
                break;
        }
        return week;
    }

    /**
     * @param lTime
     * @return
     */
    public static String showTime(long lTime) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(lTime);
        if (isToday(lTime)) {
            return getStringDateByFormat(lTime, "HH:mm");
        } else if (isYestoday(lTime)) {
            return UIUtils.getString(R.string.yesterday);
        } else {

            if (isThisWeek(lTime))
                return getWeekString(lTime);
            else
                return getStringDateByFormat(lTime, "yyyy-MM-dd");
        }

    }
    public static String showDate(long lTime){
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(lTime);
        return getStringDateByFormat(lTime, "yyyy-MM-dd HH:mm");
    }

    //Returns the user's preferred locale
    private static Locale getPreferredLocale() {
        return Locale.getDefault();
    }


    public static String getStringDateByFormat(Date date, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format,getPreferredLocale());
        String dateString = formatter.format(date);
        return dateString;
    }


    public static String formattedTimeFromDate(Date date) {
        return getStringDateByFormat(date,"h:mm a");
    }

    public static String formattedDateFromDate(Date date) {
        return getStringDateByFormat(date,"MM/dd");
    }

    //it is doing same as formattedTimeFromDate
    // just adding this to sync with iOS interfaces
    public static String formattedTimeFromDateForMessageCell(Date date) {
        return formattedTimeFromDate(date);
    }

    public static String formattedStringFromDateForChatRoom(Context context, Date date) {

        if (isToday(date)) {
            return context.getString(R.string.today);
        } else if (isYesterday(date)) {
            return context.getString(R.string.yesterday);
        }

        return formattedDateFromDate(date);
    }

    public static String formattedStringFromDateForChatList(Context context, Date date) {

        //TODO: needs to localized these strings
        if (isToday(date)) {
            return formattedTimeFromDate(date);
        } else if (isYesterday(date)) {
            return context.getString(R.string.yesterday);
        }

        return formattedDateFromDate(date);
    }

    public static boolean checkIfDateChangeIn(Date date1, Date date2) {

        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date1);

        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(date2);

        return (!((calendar1.get(Calendar.DAY_OF_YEAR) == calendar2.get(Calendar.DAY_OF_YEAR)) &&
                (calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH)) &&
                (calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR)) &&
                (calendar1.get(Calendar.ERA) == calendar2.get(Calendar.ERA))));
    }


    public static Date acsDateToDate(String acsDate) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy HH:mm:ss", Locale.US);
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        Date date = null;

        try {
            date = dateFormat.parse(acsDate);
        }
        catch (Exception e) {
            Log.d(TAG, e.getMessage());
        }

        return date;

    }

    public static String dateToAcsDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy HH:mm:ss", Locale.US);
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        String dateStr = dateFormat.format(date);
        return dateStr;
    }

    /**
     * 将指定时间字符串转换为对应的格式
     * @param date 时间字符串 如："2008-10-19 10:11:30"
     * @param fromFormat 与 date 对应的字符串格式 如："yyyy-MM-dd HH:mm:ss"
     * @param toFormat 要转换成的格式 如："yyyy年MM月dd日 HH时mm分ss秒"
     * @return  2008年10月19日 10时11分30秒
     */
    public static String dateFormat(String date, String fromFormat, String toFormat){
        SimpleDateFormat sdf1 = new SimpleDateFormat(fromFormat) ; // 实例化模板对象
        SimpleDateFormat sdf2 = new SimpleDateFormat(toFormat) ; // 实例化模板对象
        Date d = null ;
        try{
            d = sdf1.parse(date) ; // 将给定的字符串中的日期提取出来
        }catch(Exception e){ // 如果提供的字符串格式有错误，则进行异常处理
            e.printStackTrace() ; // 打印异常信息
        }
        return sdf2.format(d);// 将日期变为新的格式
    }

    /**
     * Parse iso 8601 format to date
     * @param dateStr e.g. "2016-09-01T12:00:00Z", "2016-09-01", "2016-09-01T10:15:30", "2016-09-01T00:00:00.000+08:00”
     * @return Date
     */
    public static final Date iso8601ToDate(String dateStr) {
        Date date = null;
        try {
            Instant instant = Instant.parse(dateStr);
            date = instant.toDate();
        } catch (IllegalArgumentException e) {
            LogUtils.e(TAG, "e = "+ e);
        }
        return date;
    }

    /**
     * Format date to iso 8601 string
     * @param date
     * @return e.g. "2016-09-01T12:00:00.000Z"
     */
    public static final String dateToIso8601(Date date) {
        Instant instant = new Instant(date.getTime());
        return instantToIso8601(instant);
    }

    /**
     * Plus days
     * @param iso8601DateStr
     * @param days
     * @return iso 8601 format string
     */
    public static String iso8601PlusDays(String iso8601DateStr, int days) {
        Instant instant = iso8601ToInstant(iso8601DateStr);
        return instantToIso8601(plusDays(instant, days));
    }

    private static final Instant iso8601ToInstant(String dateStr) {
        return Instant.parse(dateStr);
    }

    private static String instantToIso8601(Instant instant) {
        return instant.toString();
    }

    private static Instant plusDays(Instant instant, int days) {
        DateTime dateTime = plusDays(instant.toDateTime(), days);
        return dateTime.toInstant();
    }

    private static DateTime plusDays(DateTime dateTime, int days) {
        return dateTime.plusDays(days);
    }

    /**
     * Check if is same day
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isSameDay(@Nullable Date date1, @Nullable Date date2) {
        if (date1 == null || date2 == null) {
            return false;
        }
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
    }

    /**
     * Get days account between two dates
     * @param date1
     * @param date2
     * @return days account
     */
    public static long getDurationDays(@NonNull Date date1, @NonNull Date date2) {
        return TimeUnit.MILLISECONDS.toDays(date2.getTime() - date1.getTime());
    }
}
