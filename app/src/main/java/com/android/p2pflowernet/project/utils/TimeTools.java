package com.android.p2pflowernet.project.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Author :leilei on 2016/11/11 2326.
 */
public class TimeTools {

    //毫秒换成00:00:00
    public static String getCountTimeByLong(long finishTime) {
        int totalTime = (int) (finishTime / 1000);//秒
        int hour = 0, minute = 0, second = 0;

        if (3600 <= totalTime) {
            hour = totalTime / 3600;
            totalTime = totalTime - 3600 * hour;
        }
        if (60 <= totalTime) {
            minute = totalTime / 60;
            totalTime = totalTime - 60 * minute;
        }
        if (0 <= totalTime) {
            second = totalTime;
        }
        StringBuilder sb = new StringBuilder();

//        if (hour < 10) {
//            sb.append("0").append(hour).append(":");
//        } else {
//            sb.append(hour).append(":");
//        }
        if (minute < 10) {
            sb.append("0").append(minute).append(":");
        } else {
            sb.append(minute).append(":");
        }
        if (second < 10) {
            sb.append("0").append(second);
        } else {
            sb.append(second);
        }
        return sb.toString();
    }

    //秒换成00:00
    public static String getCountTimeByLongHour(long finishTime) {
        int totalTime = (int) (finishTime);//秒
        int hour = 0, minute = 0, second = 0;

        if (3600 <= totalTime) {
            hour = totalTime / 3600;
            totalTime = totalTime - 3600 * hour;
        }
        if (60 <= totalTime) {
            minute = totalTime / 60;
            totalTime = totalTime - 60 * minute;
        }
        if (0 <= totalTime) {
            second = totalTime;
        }
        StringBuilder sb = new StringBuilder();

        if (hour < 10) {
            sb.append("0").append(hour).append(":");
        } else {
            sb.append(hour).append(":");
        }
        if (minute < 10) {
            sb.append("0").append(minute).append("");
        } else {
            sb.append(minute).append("");
        }
//        if (second < 10) {
//            sb.append("0").append(second);
//        } else {
//            sb.append(second);
//        }
        return sb.toString();
    }

    /**
     * 把String类型的事件转换为毫秒值 "yyyy-MM-dd HH:mm:ss"
     */
    public static Long stringToLongTime(String time) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        long millionSeconds = 0;//毫秒
        try {
            return millionSeconds = sdf.parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 把Long类型的毫秒值转换为
     *
     * @param counttime day天 HH时mm分ss秒
     * @return
     */
    public static String longToStringTime(long counttime) {

        long days = counttime / (1000 * 60 * 60 * 24);
        long hours = (counttime - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        long minutes = (counttime - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60);
        long second = (counttime - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60) - minutes * (1000 * 60)) / 1000;

        return minutes + ":" + second;
    }

    public static String[] formatTimer(long sec) {
        String[] s = new String[4];
        long days = sec / (60 * 60 * 24);
        long hours = (sec % (60 * 60 * 24)) / (60 * 60);
        long minutes = (sec % (60 * 60)) / (60);
        long seconds = (sec % (60));
        if (days<10){
            s[0] = "0"+days;
        }else{
            if (days>99){
                s[0] = 99+"";
            }else{
                s[0] = days+"";
            }
        }
        if (hours<10){
            s[1] = "0"+hours;
        }else{
            s[1] = hours+"";
        }
        if (minutes<10){
            s[2] = "0"+minutes;
        }else{
            s[2] = minutes+"";
        }
        if (seconds<10){
            s[3] = "0"+seconds;
        }else{
            s[3] = seconds+"";
        }
        return s;
//        return seconds+"秒/"+minutes + "分钟/"+ hours + "小时/"+days + "天";
    }

}
