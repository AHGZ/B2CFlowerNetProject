package com.android.p2pflowernet.project.utils;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.text.format.Time;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.ghnor.flora.common.FileUtil.getDateFormat;

/**
 * @author zhangpeisen
 * @ClassName: DateUtils
 * @Description: TODO 日期时间工具类
 * @date 2016年5月6日 上午10:42:45
 */
public class DateUtils extends android.text.format.DateUtils {

    /**
     * The enum Difference mode.
     */
    public enum DifferenceMode {
        /**
         * Second difference mode.
         */
        Second, /**
         * Minute difference mode.
         */
        Minute, /**
         * Hour difference mode.
         */
        Hour, /**
         * Day difference mode.
         */
        Day
    }

    /**
     * Calculate different second long.
     *
     * @param startDate the start date
     * @param endDate   the end date
     * @return the long
     */
    public static long calculateDifferentSecond(Date startDate, Date endDate) {
        return calculateDifference(startDate, endDate, DifferenceMode.Second);
    }

    /**
     * Calculate different minute long.
     *
     * @param startDate the start date
     * @param endDate   the end date
     * @return the long
     */
    public static long calculateDifferentMinute(Date startDate, Date endDate) {
        return calculateDifference(startDate, endDate, DifferenceMode.Minute);
    }

    /**
     * Calculate different hour long.
     *
     * @param startDate the start date
     * @param endDate   the end date
     * @return the long
     */
    public static long calculateDifferentHour(Date startDate, Date endDate) {
        return calculateDifference(startDate, endDate, DifferenceMode.Hour);
    }

    /**
     * Calculate different day long.
     *
     * @param startDate the start date
     * @param endDate   the end date
     * @return the long
     */
    public static long calculateDifferentDay(Date startDate, Date endDate) {
        return calculateDifference(startDate, endDate, DifferenceMode.Day);
    }

    /**
     * Calculate different second long.
     *
     * @param startTimeMillis the start time millis
     * @param endTimeMillis   the end time millis
     * @return the long
     */
    public static long calculateDifferentSecond(long startTimeMillis, long endTimeMillis) {
        return calculateDifference(startTimeMillis, endTimeMillis, DifferenceMode.Second);
    }

    /**
     * Calculate different minute long.
     *
     * @param startTimeMillis the start time millis
     * @param endTimeMillis   the end time millis
     * @return the long
     */
    public static long calculateDifferentMinute(long startTimeMillis, long endTimeMillis) {
        return calculateDifference(startTimeMillis, endTimeMillis, DifferenceMode.Minute);
    }

    /**
     * Calculate different hour long.
     *
     * @param startTimeMillis the start time millis
     * @param endTimeMillis   the end time millis
     * @return the long
     */
    public static long calculateDifferentHour(long startTimeMillis, long endTimeMillis) {
        return calculateDifference(startTimeMillis, endTimeMillis, DifferenceMode.Hour);
    }

    /**
     * Calculate different day long.
     *
     * @param startTimeMillis the start time millis
     * @param endTimeMillis   the end time millis
     * @return the long
     */
    public static long calculateDifferentDay(long startTimeMillis, long endTimeMillis) {
        return calculateDifference(startTimeMillis, endTimeMillis, DifferenceMode.Day);
    }

    /**
     * Calculate difference long.
     *
     * @param startTimeMillis the start time millis
     * @param endTimeMillis   the end time millis
     * @param mode            the mode
     * @return the long
     */
    public static long calculateDifference(long startTimeMillis, long endTimeMillis, DifferenceMode mode) {
        return calculateDifference(new Date(startTimeMillis), new Date(endTimeMillis), mode);
    }

    /**
     * Calculate difference long.
     *
     * @param startDate the start date
     * @param endDate   the end date
     * @param mode      the mode
     * @return the long
     */
    public static long calculateDifference(Date startDate, Date endDate, DifferenceMode mode) {
        long[] different = calculateDifference(startDate, endDate);
        if (mode.equals(DifferenceMode.Minute)) {
            return different[2];
        } else if (mode.equals(DifferenceMode.Hour)) {
            return different[1];
        } else if (mode.equals(DifferenceMode.Day)) {
            return different[0];
        } else {
            return different[3];
        }
    }

    /**
     * Calculate difference long [ ].
     *
     * @param startDate the start date
     * @param endDate   the end date
     * @return the long [ ]
     */
    public static long[] calculateDifference(Date startDate, Date endDate) {
        return calculateDifference(endDate.getTime() - startDate.getTime());
    }

    /**
     * Calculate difference long [ ].
     *
     * @param differentMilliSeconds the different milli seconds
     * @return the long [ ]
     */
    public static long[] calculateDifference(long differentMilliSeconds) {
        long secondsInMilli = 1000;//1s==1000ms
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;
        long elapsedDays = differentMilliSeconds / daysInMilli;
        differentMilliSeconds = differentMilliSeconds % daysInMilli;
        long elapsedHours = differentMilliSeconds / hoursInMilli;
        differentMilliSeconds = differentMilliSeconds % hoursInMilli;
        long elapsedMinutes = differentMilliSeconds / minutesInMilli;
        differentMilliSeconds = differentMilliSeconds % minutesInMilli;
        long elapsedSeconds = differentMilliSeconds / secondsInMilli;
        LogUtils.e("", String.format("different: %d ms, %d days, %d hours, %d minutes, %d seconds",
                differentMilliSeconds, elapsedDays, elapsedHours, elapsedMinutes, elapsedSeconds));
        return new long[]{elapsedDays, elapsedHours, elapsedMinutes, elapsedSeconds};
    }

    /**
     * Calculate days in month int.
     *
     * @param month the month
     * @return the int
     */
    public static int calculateDaysInMonth(int month) {
        return calculateDaysInMonth(0, month);
    }

    /**
     * Calculate days in month int.
     *
     * @param year  the year
     * @param month the month
     * @return the int
     */
    public static int calculateDaysInMonth(int year, int month) {
        // 添加大小月月份并将其转换为list,方便之后的判断
        String[] bigMonths = {"1", "3", "5", "7", "8", "10", "12"};
        String[] littleMonths = {"4", "6", "9", "11"};
        List<String> bigList = Arrays.asList(bigMonths);
        List<String> littleList = Arrays.asList(littleMonths);
        // 判断大小月及是否闰年,用来确定"日"的数据
        if (bigList.contains(String.valueOf(month))) {
            return 31;
        } else if (littleList.contains(String.valueOf(month))) {
            return 30;
        } else {
            if (year <= 0) {
                return 29;
            }
            // 是否闰年
            if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
                return 29;
            } else {
                return 28;
            }
        }
    }

    /**
     * 月日时分秒，0-9前补0
     *
     * @param number the number
     * @return the string
     */
    @NonNull
    public static String fillZero(int number) {
        return number < 10 ? "0" + number : "" + number;
    }

    /**
     * 功能：判断日期是否和当前date对象在同一天。
     * 参见：http://www.cnblogs.com/myzhijie/p/3330970.html
     *
     * @param date 比较的日期
     * @return boolean 如果在返回true，否则返回false。
     * @author 沙琪玛 QQ：862990787 Aug 21, 2013 7:15:53 AM
     */
    public static boolean isSameDay(Date date) {
        if (date == null) {
            throw new IllegalArgumentException("date is null");
        }
        Calendar nowCalendar = Calendar.getInstance();
        Calendar newCalendar = Calendar.getInstance();
        newCalendar.setTime(date);
        return (nowCalendar.get(Calendar.ERA) == newCalendar.get(Calendar.ERA) &&
                nowCalendar.get(Calendar.YEAR) == newCalendar.get(Calendar.YEAR) &&
                nowCalendar.get(Calendar.DAY_OF_YEAR) == newCalendar.get(Calendar.DAY_OF_YEAR));
    }


    /**
     * 判断是否为今天(效率比较高)
     *
     * @param day 传入的 时间  "2016-06-28 10:10:30" "2016-06-28" 都可以
     * @return true今天 false不是
     * @throws ParseException
     */
    public static boolean IsToday(String day) throws ParseException {

        Calendar pre = Calendar.getInstance();
        Date predate = new Date(System.currentTimeMillis());
        pre.setTime(predate);

        Calendar cal = Calendar.getInstance();
        Date date = getDateFormat().parse(day);
        cal.setTime(date);

        if (cal.get(Calendar.YEAR) == (pre.get(Calendar.YEAR))) {
            int diffDay = cal.get(Calendar.DAY_OF_YEAR)
                    - pre.get(Calendar.DAY_OF_YEAR);

            if (diffDay == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 将yyyy-MM-dd HH:mm:ss字符串转换成日期<br/>
     *
     * @param dateStr    时间字符串
     * @param dataFormat 当前时间字符串的格式。
     * @return Date 日期 ,转换异常时返回null。
     */
    @SuppressLint("SimpleDateFormat")
    public static Date parseDate(String dateStr, String dataFormat) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(dataFormat);
            Date date = dateFormat.parse(dateStr);
            return new Date(date.getTime());
        } catch (Exception e) {
            LogUtils.e("", e.getMessage());
            return null;
        }
    }

    /**
     * 将yyyy-MM-dd HH:mm:ss字符串转换成日期<br/>
     *
     * @param dateStr yyyy-MM-dd HH:mm:ss字符串
     * @return Date 日期 ,转换异常时返回null。
     */
    public static Date parseDate(String dateStr) {
        return parseDate(dateStr, "yyyy-MM-dd HH:mm:ss");
    }

    private SimpleDateFormat sf = null;

    /**
     * 获取当前时间
     *
     * @return
     */
    public String getNowTime() {
        String timeString = null;
        Time time = new Time();
        time.setToNow();
        String year = thanTen(time.year);
        String month = thanTen(time.month + 1);
        String monthDay = thanTen(time.monthDay);
        String hour = thanTen(time.hour);
        String minute = thanTen(time.minute);

        timeString = year + "-" + month + "-" + monthDay + " " + hour + ":" + minute;
        // System.out.println("-------timeString----------" + timeString);
        return timeString;
    }

    public int calculate(int year, int month) {

        boolean yearleap = judge(year);
        int day;
        if (yearleap && month == 2) {
            day = 29;
        } else if (!yearleap && month == 2) {
            day = 28;
        } else if (month == 4 || month == 6 || month == 9 || month == 11) {
            day = 30;
        } else {
            day = 31;
        }
        return day;
    }

    public boolean judge(int year) {
        boolean yearleap = (year % 400 == 0) || (year % 4 == 0)
                && (year % 100 != 0);// 采用布尔数据计算判断是否能整除
        return yearleap;
    }

    /**
     * 十一下加零
     *
     * @param str
     * @return
     */
    public String thanTen(int str) {

        String string = null;

        if (str < 10) {
            string = "0" + str;
        } else {

            string = "" + str;

        }
        return string;
    }

    /**
     * 计算时间差
     *
     * @param starTime 开始时间
     * @param endTime  结束时间
     * @return 返回时间差
     * @para 返回类型 ==1----天，时，分。 ==2----时
     */
    public String getTimeDifference(String starTime, String endTime) {
        String timeString = "";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        try {
            Date parse = dateFormat.parse(starTime);
            Date parse1 = dateFormat.parse(endTime);

            long diff = parse1.getTime() - parse.getTime();

            long day = diff / (24 * 60 * 60 * 1000);
            long hour = (diff / (60 * 60 * 1000) - day * 24);
            long min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
            long s = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
            long ms = (diff - day * 24 * 60 * 60 * 1000 - hour * 60 * 60 * 1000
                    - min * 60 * 1000 - s * 1000);
            // System.out.println(day + "天" + hour + "小时" + min + "分" + s +
            // "秒");
            long hour1 = diff / (60 * 60 * 1000);
            String hourString = hour1 + "";
            long min1 = ((diff / (60 * 1000)) - hour1 * 60);
            timeString = hour1 + "小时" + min1 + "分";
            // System.out.println(day + "天" + hour + "小时" + min + "分" + s +
            // "秒");

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return timeString;

    }

    /**
     * 计算相差的小时
     *
     * @param starTime
     * @param endTime
     * @return
     */
    public String getTimeDifferenceHour(String starTime, String endTime) {
        String timeString = "";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        try {
            Date parse = dateFormat.parse(starTime);
            Date parse1 = dateFormat.parse(endTime);

            long diff = parse1.getTime() - parse.getTime();
            String string = Long.toString(diff);

            float parseFloat = Float.parseFloat(string);

            float hour1 = parseFloat / (60 * 60 * 1000);

            timeString = Float.toString(hour1);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return timeString;

    }

    /**
     * 获取时间中的某一个时间点
     *
     * @param str
     * @param type
     * @return
     */
    public String getJsonParseShiJian(String str, int type) {
        String shijanString = null;
        String nian = str.substring(0, str.indexOf("-"));
        String yue = str.substring(str.indexOf("-") + 1, str.lastIndexOf("-"));
        String tian = str.substring(str.lastIndexOf("-") + 1, str.indexOf(" "));
        String shi = str.substring(str.indexOf(" ") + 1, str.lastIndexOf(":"));
        String fen = str.substring(str.lastIndexOf(":") + 1, str.length());

        switch (type) {
            case 1:
                shijanString = nian;
                break;
            case 2:
                shijanString = yue;
                break;
            case 3:
                shijanString = tian;
                break;
            case 4:
                shijanString = shi;
                break;
            case 5:
                shijanString = fen;
                break;

        }
        return shijanString;
    }

    /**
     * Sring变int
     *
     * @param str
     * @return
     */
    public int strToInt(String str) {
        int value = 0;
        value = Integer.parseInt(str);
        return value;
    }

    /**
     * 与当前时间比较早晚
     *
     * @param time 需要比较的时间
     * @return 输入的时间比现在时间晚则返回true
     */
    public boolean compareNowTime(String time) {
        boolean isDayu = false;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        try {
            Date parse = dateFormat.parse(time);
            Date parse1 = dateFormat.parse(getNowTime());

            long diff = parse1.getTime() - parse.getTime();
            isDayu = diff <= 0;
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return isDayu;
    }

    /**
     * 把时间戳变yyyy-MM-dd HH:mm格式时间
     *
     * @param time
     * @return
     */
    public String getDateToString(long time) {

        Date d = new Date(time);
        sf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sf.format(d);
    }

    /**
     * 调用此方法输入所要转换的时间戳输入例如（1402733340）输出（"2014-06-14  16:09:00"）
     *
     * @param time
     * @return
     */
    public static String timetodate(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        @SuppressWarnings("unused")
        long lcc = Long.valueOf(time);
        int i = Integer.parseInt(time);
        String times = sdr.format(new Date(i * 1000L));
        return times;

    }

    /**
     * 获取当前时间戳
     *
     * @return 获取当前时间戳
     */
    public static String getTimeString() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        return df.format(calendar.getTime());
    }

    /**
     * 返回时间戳
     *
     * @param time
     * @return
     */
    public static long dataOne(String time) {

        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        Date date;
        long l = 0;
        try {
            date = sdr.parse(time);
            l = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return l;
    }


    /**
     * 调用此方法输入所要转换的时间戳输入例如（1402733340）输出（"2014-06-14  16:09:00"）
     *
     * @param time
     * @return
     */
    public static String timedate(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd");
        @SuppressWarnings("unused")
        long lcc = Long.valueOf(time);
        int i = Integer.parseInt(time);
        String times = sdr.format(new Date(i * 1000L));
        return times;

    }


    /**
     * 调用此方法输入所要转换的时间戳输入例如（1402733340）输出（"2014-06-14  16:09:00"）
     *
     * @param time
     * @return
     */
    public static String timesdate(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyyy-MM-dd HH:mm:ss");
        @SuppressWarnings("unused")
        long lcc = Long.valueOf(time);
        int i = Integer.parseInt(time);
        String times = sdr.format(new Date(i * 1000L));
        return times;

    }

    /**
     * 比较两个时间
     *
     * @param starTime  开始时间
     * @param endString 结束时间
     * @return 结束时间大于开始时间返回true，否则反之֮
     */
    public boolean compareTwoTime(String starTime, String endString) {
        boolean isDayu = false;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date parse = dateFormat.parse(starTime);
            Date parse1 = dateFormat.parse(endString);

            long diff = parse1.getTime() - parse.getTime();
            isDayu = diff >= 0;
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return isDayu;

    }

    public boolean compareTwoTime2(String starTime, String endString) {
        boolean isDayu = false;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        try {
            Date parse = dateFormat.parse(starTime);
            Date parse1 = dateFormat.parse(endString);

            long diff = parse1.getTime() - parse.getTime();
            isDayu = diff >= 0;
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return isDayu;

    }

    /**
     * 获取年
     *
     * @param time
     * @return
     */
    public String getTimeYear(String time) {

        String substring = time.substring(0, time.lastIndexOf(" "));
        return substring;

    }

    /**
     * 换算小时，0.5小时-->0小时30分
     *
     * @param hour
     * @return
     */
    private String convertTime(String hour) {

        String substring = hour.substring(0, hour.lastIndexOf("."));
        String substring2 = hour.substring(hour.lastIndexOf(".") + 1,
                hour.length());
        substring2 = "0." + substring2;
        float f2 = Float.parseFloat(substring2);
        f2 = f2 * 60;
        String string = Float.toString(f2);
        String min = string.substring(0, string.lastIndexOf("."));
        return substring + "小时" + min + "分";
    }

    /**
     * @param time
     * @return
     */
    public static String getHours(long time) {
        long second = time / 1000;
        long hour = second / 60 / 60;
        long minute = (second - hour * 60 * 60) / 60;
        long sec = (second - hour * 60 * 60) - minute * 60;

        String rHour = "";
        String rMin = "";
        String rSs = "";
        // 时
        if (hour < 10) {
            rHour = "0" + hour;
        } else {
            rHour = hour + "";
        }
        // 分
        if (minute < 10) {
            rMin = "0" + minute;
        } else {
            rMin = minute + "";
        }
        // 秒
        if (sec < 10) {
            rSs = "0" + sec;
        } else {
            rSs = sec + "";
        }

        // return_ticket hour + "小时" + minute + "分钟" + sec + "秒";
        return rHour + ":" + rMin + ":" + rSs;

    }

}
