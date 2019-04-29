package com.demo.spring.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Description:
 *
 * @Author: 鲁砚琨
 * @Date: 2019/2/26 13:27
 * @Version: v1.0
 */
public class DateUtil {

    public static final String PATTERN_DATE = "yyyy-MM-dd";
    public static final String PATTERN_DATETIME = "yyyy-MM-dd HH:mm:ss";
    public static final String PATTERN_DATETIME_SECOND = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String PATTERN_DATETIME_NOT_FORMAT = "yyyyMMddHHmmss";


    /**
     * 初始化格式化模板
     * @param pattern 格式化参数
     */
    private static SimpleDateFormat getSimpleDateFormat(String pattern) {
        return new SimpleDateFormat(pattern);
    }

    /**
     * 格式化时间
     * @param date 指定时间
     * @param format 格式化参数
     */
    public static String formatting(Date date, String format) {
        return getSimpleDateFormat(format).format(date);
    }

    /**
     * 格式化时间
     * @param date 指定时间
     * @param format 格式化参数
     */
    public static Date formatting(String date, String format) {
        try {
            return getSimpleDateFormat(format).parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取当前时间
     */
    public static Date getDateTime() {
        return Calendar.getInstance().getTime();
    }

    /**
     * 获取日期的年份
     * @param date 指定时间
     */
    public static int getYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        if (date != null)
            calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 获取日期的月份
     * @param date 指定时间
     */
    public static int getMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        if (date != null)
            calendar.setTime(date);
        return (calendar.get(Calendar.MONTH) + 1);
    }

    /**
     * 获取日期的天数
     * @param date 指定时间
     */
    public static int getDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        if (date != null)
            calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取日期的小时数
     * @param date 指定时间
     */
    public static int getHour(Date date) {
        Calendar calendar = Calendar.getInstance();
        if (date != null)
            calendar.setTime(date);
        return calendar.get(Calendar.HOUR);
    }

    /**
     * 获取日期的分钟数
     * @param date 指定时间
     */
    public static int getMinute(Date date) {
        Calendar calendar = Calendar.getInstance();
        if (date != null)
            calendar.setTime(date);
        return calendar.get(Calendar.MINUTE);
    }

    /**
     * 获取日期的秒钟数
     * @param date 指定时间
     */
    public static int getSecond(Date date) {
        Calendar calendar = Calendar.getInstance();
        if (date != null)
            calendar.setTime(date);
        return calendar.get(Calendar.SECOND);
    }

    /**
     * 获取日期属于第几周
     * @param date 指定时间
     */
    public static int getDayOfWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        if (date != null)
            calendar.setTime(date);
        int week = calendar.get(Calendar.DAY_OF_WEEK);
        return  week == 1 ? 7 : week - 1;
    }


    /**
     * 获取日期前几年或后几年
     * @param date 指定时间
     */
    public static Date getRollYearByDate(Date date, int value) {
        Calendar calendar = Calendar.getInstance();
        if (date != null)
            calendar.setTime(date);
        calendar.add(Calendar.YEAR, value);
        return calendar.getTime();
    }

    /**
     * 获取日期前几月或后几月
     * @param date 指定时间
     */
    public static Date getRollMonthByDate(Date date, int value) {
        Calendar calendar = Calendar.getInstance();
        if (date != null)
            calendar.setTime(date);
        calendar.add(Calendar.MONTH, value);
        return calendar.getTime();
    }

    /**
     * 获取日期前几日或后几日
     * @param date 指定时间
     */
    public static Date getRollDayByDate(Date date, int value) {
        Calendar calendar = Calendar.getInstance();
        if (date != null)
            calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, value);
        return calendar.getTime();
    }

    /**
     * 获取日期前几小时或后几小时
     * @param date 指定时间
     */
    public static Date getRollHourByDate(Date date, int value) {
        Calendar calendar = Calendar.getInstance();
        if (date != null)
            calendar.setTime(date);
        calendar.add(Calendar.HOUR, value);
        return calendar.getTime();
    }

    /**
     * 获取日期前几分钟或后几分钟
     * @param date 指定时间
     */
    public static Date getRollMinuteByDate(Date date, int value) {
        Calendar calendar = Calendar.getInstance();
        if (date != null)
            calendar.setTime(date);
        calendar.add(Calendar.MINUTE, value);
        return calendar.getTime();
    }

    /**
     * 获取日期前几秒钟或后几秒钟
     * @param date 指定时间
     */
    public static Date getRollSecondByDate(Date date, int value) {
        Calendar calendar = Calendar.getInstance();
        if (date != null)
            calendar.setTime(date);
        calendar.add(Calendar.SECOND, value);
        return calendar.getTime();
    }

    /**
     * 获取日期当月的第一天
     * @param date 指定时间
     */
    public static Date getFirstDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        if (date != null)
            calendar.setTime(date);
        calendar.add(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    /**
     * 获取日期当月的最后一天
     * @param date 指定时间
     */
    public static Date getLastDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        if (date != null)
            calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return calendar.getTime();
    }

    /**
     * 获取当月实际天数
     * @param date 指定时间
     */
    public static int getActualMaximumDaysOfMonth(Date date){
        Calendar calendar = Calendar.getInstance();
        if (date != null)
            calendar.setTime(date);
        return calendar.getActualMaximum(Calendar.DATE);
    }

    /**
     * 比较两个时间的大小
     * @param firstDate 指定第一个时间
     * @param secondDate 指定第二个时间
     */
    public static int compareTo(Date firstDate, Date secondDate){
        Calendar firstCalendar = Calendar.getInstance();
        firstCalendar.setTime(firstDate);
        Calendar secondCalendar = Calendar.getInstance();
        secondCalendar.setTime(secondDate);
        return firstCalendar.compareTo(secondCalendar);
    }

}
