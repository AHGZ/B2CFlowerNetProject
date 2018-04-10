package com.android.p2pflowernet.project.utils;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DatesUtils {


    /**
     * 按格式将日期转换成字符串
     *
     * @param date      日期
     * @param formatStr 日期显示格式
     * @return
     */
    public String date2StringByFormat(Date date, String formatStr) {
        String str = null;
        DateFormat dateFormat = new SimpleDateFormat(formatStr);
        str = dateFormat.format(date);
        return str;
    }

    /**
     * 按格式将日期转换成字符串
     *
     * @param date      日期
     * @param formatStr 日期显示格式
     * @return
     */
    public Date date2DateByFormat(Date date, String formatStr) {
        String str = null;
        DateFormat dateFormat = new SimpleDateFormat(formatStr);
        str = dateFormat.format(date);
        return this.string2DateTime(str, formatStr);
    }

    /**
     * 字符串转时间格式
     *
     * @param dateStr    日期字符串
     * @param dateFormat 日期格式
     * @return
     */
    public Date string2DateTime(String dateStr, String dateFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        Date date = null;
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 计算天数
     *
     * @param date 运算的基准时间
     * @param days 运算的天数 +-
     * @return 运算后的日期
     */
    public Date calculateDay(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DATE, (calendar.get(Calendar.DATE) + days));
        return calendar.getTime();
    }

    /**
     * @param date
     * @param
     * @return
     */
    public String calculateDayStr(Date date, int days) {
        return this.date2StringByFormat(this.calculateDay(date, days), DateConstants.DEFAULTSHOWDATE);
    }

    /**
     * 计算月份
     *
     * @param date   运算的基准时间
     * @param months 运算的月数+-
     * @return 运算后的日期
     */
    public Date calculateMonth(Date date, int months) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MONTH,
                (calendar.get(Calendar.MONTH) + months));
        return calendar.getTime();
    }

    /**
     * 计算年
     *
     * @param date  运算的基准时间
     * @param years 运算的年+-
     * @return 运算后的年
     */
    public Date calculateYear(Date date, int years) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.YEAR,
                (calendar.get(Calendar.YEAR) + years));
        return calendar.getTime();
    }

    /**
     * 计算小时
     *
     * @param date 运算的基准时间
     * @param hour 运算的小时+-
     * @return 运算后的小时
     */
    public Date calculateHour(Date date, int hour) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR,
                (calendar.get(Calendar.HOUR) + hour));
        return calendar.getTime();
    }

    /**
     * 计算分钟
     *
     * @param date   运算的基准时间
     * @param minute 运算的分钟+-
     * @return 运算后的分钟
     */
    public Date calculateMinute(Date date, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MINUTE,
                (calendar.get(Calendar.MINUTE) + minute));
        return calendar.getTime();
    }

    /**
     * 返回当前年的字符串格式
     *
     * @return
     */
    public String nowYear() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        return year + "";
    }

    /**
     * 计算两个日期之间相差的天数
     *
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     * @return 相差天数
     * @throws ParseException
     */
    public int daysBetween(Date smdate, Date bdate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        smdate = sdf.parse(sdf.format(smdate));
        bdate = sdf.parse(sdf.format(bdate));
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 获取两个时间的时间差
     *
     * @param minTime 较小的时间
     * @param maxTime 较大的时间
     * @return
     * @throws ParseException
     */
    public String getTimeDifference(String minTime, String maxTime) throws ParseException {
        SimpleDateFormat dfs = new SimpleDateFormat(DateConstants.DEFAULTDATEFORMAT);
        Date begin = dfs.parse(minTime);
        Date end = dfs.parse(maxTime);
        long between = (end.getTime() - begin.getTime()) / 1000;// 除以1000是为了转换成秒
        long day = between / (24 * 3600);
        long hour = between % (24 * 3600) / 3600;
        long minute = between % 3600 / 60;
        long second = between % 60;
        return day + " 天 " + hour + " 小时 " + minute + " 分 " + second + " 秒 ";
    }

    /**
     * 获取两个时间的时间差
     *
     * @param minTime 较小的时间
     * @param maxTime 较大的时间
     * @return
     * @throws ParseException
     */
    public String getTimeDifferenceMinuteAndSecond(String minTime, String maxTime) throws ParseException {
        SimpleDateFormat dfs = new SimpleDateFormat(DateConstants.DEFAULTDATEFORMAT);
        Date begin = dfs.parse(minTime);
        Date end = dfs.parse(maxTime);
        long between = (end.getTime() - begin.getTime()) / 1000;// 除以1000是为了转换成秒
        long minute = between % 3600 / 60;
        long second = between % 60;
        return minute + " 分 " + second + " 秒 ";
    }

    /**
     * 获取两个时间的时间差(小时)
     *
     * @param minTime 较小的时间
     * @param maxTime 较大的时间
     * @return
     * @throws ParseException
     */
    public String getTimeDifferenceHour(String minTime, String maxTime) throws ParseException {
        SimpleDateFormat dfs = new SimpleDateFormat(DateConstants.DEFAULTDATEFORMAT);
        Date begin = dfs.parse(minTime);
        Date end = dfs.parse(maxTime);
        long between = (end.getTime() - begin.getTime()) / 1000;// 除以1000是为了转换成秒
        long hour = between % (24 * 3600) / 3600;
        return hour + "";
    }

    /**
     * 获取两个时间的时间差(分钟)
     *
     * @param minTime 较小的时间
     * @param maxTime 较大的时间
     * @return
     * @throws ParseException
     */
    public String getTimeDifferenceMinute(String minTime, String maxTime) throws ParseException {
        SimpleDateFormat dfs = new SimpleDateFormat(DateConstants.DEFAULTDATEFORMAT);
        Date begin = dfs.parse(minTime);
        Date end = dfs.parse(maxTime);
        long between = (end.getTime() - begin.getTime()) / 1000;// 除以1000是为了转换成秒
        long minute = between % 3600 / 60;
        return minute + "";
    }

    /**
     * 判断日期是否大于当前日期
     *
     * @param date
     * @return 大于当前日期返回true，小于当前日期返回false
     */
    public boolean overNowDate(Date date) {
        long dateTime = date.getTime(),
                nowDateTime = new Date().getTime();
        return (nowDateTime - dateTime) <= 0;
    }

    /**
     * 判断日期是否大于指定日期
     *
     * @param TargetDate   指定日期
     * @param comparedDate 比较日期
     * @return 大于指定日期返回true，小于指定日期返回false
     */
    public boolean overDate(Date TargetDate, Date comparedDate) {
        long sTime = TargetDate.getTime(),
                eTime = comparedDate.getTime();
        return (sTime - eTime) < 0;
    }

    /**
     * 判断日期是否大于指定日期
     *
     * @param targetData   指定日期
     * @param comparedDate 比较日期
     * @return 大于指定日期返回true，小于指定日期返回false
     */
    public boolean overDate(String targetData, String comparedDate) {
        return this.overDate(this.string2DateTime(targetData, DateConstants.DEFAULTSHOWDATE), this.string2DateTime(comparedDate, DateConstants.DEFAULTSHOWDATE));
    }

    /**
     * 获取默认的日期格式时间
     *
     * @return 默认日期格式 yyyy-MM-dd hh:mm:sss
     */
    public String getDefaultDateTime() {
        return this.date2StringByFormat(new Date(), DateConstants.DEFAULTDATEFORMAT);
    }

    /**
     * 获取默认的日期
     *
     * @return 默认日期格式yyyy-MM-dd
     */
    public String getDefaultDate() {
        return this.date2StringByFormat(new Date(), DateConstants.DEFAULTDATE);
    }

    /**
     * 获取默认的日期
     *
     * @return 默认日期格式yyyyMMdd
     */
    public String getDefaultIndexDate() {
        return this.date2StringByFormat(new Date(), DateConstants.DEFAULTINDEXDATE);
    }

    /**
     * 验证时间是否在指定时间范围内
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @param time      待验证时间
     * @return 在范围内返回ture，不在返回false;
     */
    public boolean isBetweenDate(String startTime, String endTime, String time) {
        Date startDate = this.string2DateTime(startTime, DateConstants.DEFAULTDATEFORMAT);
        Date endDate = this.string2DateTime(endTime, DateConstants.DEFAULTDATEFORMAT);
        Date date = this.string2DateTime(time, DateConstants.DEFAULTDATEFORMAT);
        return this.isBetweenDate(startDate, endDate, date);
    }

    /**
     * 验证时间是否在指定时间范围内
     *
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @param date      待验证时间
     * @return 在范围内返回ture，不在返回false;
     */
    public boolean isBetweenDate(Date startDate, Date endDate, Date date) {
        return (date.equals(startDate) || date.after(startDate)) && (date.equals(endDate) || date.before(endDate));
    }

    /**
     * 获取今天的日期
     *
     * @return
     */
    public Date todayDate() {
        return this.string2DateTime(this.getDefaultDate(), DateConstants.DEFAULTDATE);
    }

    /**
     * 获取昨天的日期
     *
     * @return
     */
    public String yesterdayDate(String dateTime) {
        Date date = null;
        try {
            if (null != dateTime) {
                date = string2DateTime(dateTime, DateConstants.DEFAULTSEARCHDATE);
            } else {
                date = new Date();
            }
        } catch (Exception ex) {
            date = new Date();
        }
        return this.date2StringByFormat(this.calculateDay(date, -1), DateConstants.DEFAULTSEARCHDATE);
    }

    /**
     * 获取明天的日期
     *
     * @return
     */
    public String tomorrowDate(String dateTime) {
        Date date = null;
        try {
            if (null != dateTime) {
                date = string2DateTime(dateTime, DateConstants.DEFAULTSEARCHDATE);
            } else {
                date = new Date();
            }
        } catch (Exception ex) {
            date = new Date();
        }
        return this.date2StringByFormat(this.calculateDay(date, 1), DateConstants.DEFAULTSEARCHDATE);
    }

    /**
     * 获取默认的日期格式时间
     *
     * @return 默认日期格式 yyyy-MM-dd hh:mm:ss
     */
    public String getDefaultDateTime2() {
        return this.date2StringByFormat(new Date(), DateConstants.DEFAULTSHOWDATE);
    }

    /**
     * 将日期格式字符串转换成指定格式的日期字符串
     *
     * @param dateTime       待转换的字符串
     * @param dateTimeFormat 匹配字符串转的日期格式
     * @param format         最终转换后的日期格式
     * @return
     */
    public String dateTimeString2FormatDateTimeString(String dateTime, String dateFormat, String format) {
        return this.date2StringByFormat(this.string2DateTime(dateTime, dateFormat), format);
    }

    /**
     * 获取默认的时间
     *
     * @return 默认时间格式hh:mm:ss
     */
    public String getDefaultTime() {
        return this.date2StringByFormat(new Date(), DateConstants.DEFAULTTIME);
    }
}
