package com.pm.background.common.utils.util;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @description 进行日期格式化的工具类
 * @author : Larry
 * @date : 2020/4/17 10:00
 * @see org.apache.commons.lang.time.DateFormatUtils
 常用方法:
 *  1  dateToString  日期转化为字符串  参数为 date 和 string类型的 日期格式
 *  2  StringToDate   字符串转日期     参数为 date 和 string类型的 日期格式
 *  3  getFirstDayOfMonth    返回一个月的开头一天  参数为 int类型的 年和int 类型的月
 *  4  getLastDayOfMonth    返回一个月的最后一天  参数为 int类型的 年和int 类型的月
 *  5  getFirstDayOfYear    返回一个月的开头一天  参数为 int类型的 年
 *  6  firstDayOfCurrentMonth    返回当前月的第一天
 *  7  lastDayOfCurrentMonth    返回当前月最后一天
 *  8  compareDate    默认比较两个格式为"yyyy-MM-dd HH:mm:ss"的日期的大小  如果非此格式 需要添加 第三个参数:字符串类型的日期格式
 *  9  getNextDay    根据传入的Date类型日期 获取下一天
 *  10  getMonth    根据传入的Date类型日期 获取月份
 *  11  getTodayBegin  获得今天的开始时间
 *  12 getTodayEnd  获得今天的结束时间
 *  13 getWeekBegin  获得本周开始时间
 *  14 getWeekEnd  获得本周结束时间
 */
public class DateFormatUtils {

    private static Log logger = LogFactory.getLog(DateUtils.class);
    
    /*
     * @author Larry
     * @date 2020/4/17 13:33
     * @param [year, month]
     * @return java.lang.String
     * @description  返回一个月的开头一天
     **/
    public static String getFirstDayOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        // 设置年份
        cal.set(Calendar.YEAR, year);
        // 设置月份
        cal.set(Calendar.MONTH, month - 1);
        // 设置日历中月份的第1天
        cal.set(Calendar.DAY_OF_MONTH, 1);
        // 格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String firstDayOfMonth = sdf.format(cal.getTime());
        return firstDayOfMonth;
    }

    /*
     * @author Larry
     * @date 2020/4/17 13:33
     * @param [year, month]
     * @return java.lang.String
     * @description  返回一个月的最后一天
     **/
    public static String getLastDayOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        // 设置年份
        cal.set(Calendar.YEAR, year);
        // 设置月份
        cal.set(Calendar.MONTH, month);
        // 设置日历中月份的最后1天
        cal.set(Calendar.DATE, 0);
        // 格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String lastDayOfMonth = sdf.format(cal.getTime());
        return lastDayOfMonth;
    }

    /*
     * @author Larry
     * @date 2020/4/17 13:33
     * @param [year, month]
     * @return java.lang.String
     * @description  返回一年的第一天
     **/
    public static String getFirstDayOfYear(int year) {
        Calendar cal = Calendar.getInstance();
        // 设置年份
        cal.set(Calendar.YEAR, year);
        // 设置月份
        cal.set(Calendar.MONTH, 0);
        // 设置日历中月份的第1天
        cal.set(Calendar.DAY_OF_MONTH, 1);
        // 格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String firstDayOfYear = sdf.format(cal.getTime());
        return firstDayOfYear;
    }
    /*
     * @author Larry
     * @date 2020/4/17 13:33
     * @param [year, month]
     * @return java.lang.String
     * @description  返回一年的最后一天
     **/
    public static String getLastDayOfYear(int year) {
        Calendar cal = Calendar.getInstance();
        // 设置年份
        cal.set(Calendar.YEAR, year);
        // 设置月份
        cal.set(Calendar.MONTH, 11);
        // 设置日历中月份的最后1天
        cal.set(Calendar.DATE, 0);
        // 格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String lastDayOfYear = sdf.format(cal.getTime());
        return lastDayOfYear;
    }

    /**
     * 获取当前月第一天
     * @return
     */
    public static String firstDayOfCurrentMonth(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal=Calendar.getInstance();//获取当前日期
        cal.add(Calendar.MONTH, 0);
        cal.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天
        return sdf.format(cal.getTime());
    }

    /**
     * 获取当前月最后一天
     * @return
     */
    public static String lastDayOfCurrentMonth(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();//获取当前日期
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        return sdf.format(cal.getTime());
    }

    /*
     * @author Larry
     * @date 2020/4/17 0017 13:36
     * @param [firstDate, lastDate]
     * @return java.lang.String
     * @description  比较两个日期的大小的方法 返回大的日期
     **/
    public static String compareDate(String firstDate, String lastDate) {
        String retDate = null ;
        if(StringUtils.isEmpty(firstDate) && !StringUtils.isEmpty(lastDate)) {
            return lastDate ;
        }
        if(!StringUtils.isEmpty(firstDate) && StringUtils.isEmpty(lastDate)) {
            return firstDate ;
        }
        if(!StringUtils.isEmpty(firstDate) && !StringUtils.isEmpty(lastDate)) {
            // 格式化日期
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                Date first = sdf.parse(firstDate) ;
                Date last = sdf.parse(lastDate) ;
                if(first.after(last)) {
                    return sdf.format(first) ;
                }else{
                    return sdf.format(last) ;
                }
            } catch (ParseException e) {
                logger.error("", e);
            }
        }

        return retDate ;
    }
    /*
     * @author Larry
     * @date 2020/4/17 0017 13:37
     * @param [date]
     * @return java.util.Date
     * @description  根据传入的日期 获取下一天
     **/
    public static Date getNextDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, +1);//+1今天的时间加一天
        date = calendar.getTime();
        return date;
    }

    /**
     * 获取日期的月份
     * @param date
     * @return
     */
    public static String getMonth(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH) + 1;
        if(month < 10){
            return "0"+month;
        } else {
            return String.valueOf(month);
        }
    }

    /**
     * 使用用户格式格式化日期
     * @param date日期
     * @param pattern日期格式
     * @return
     */
    public static String format(Date date, String pattern) {
        String returnValue = "";
        if (date != null) {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            returnValue = df.format(date);
        }
        return (returnValue);
    }

    /**
     * 比较两个日期大小
     * @param DATE1
     * @param DATE2
     * @param format 格式 yyyy-MM-dd,yyyy-MM-dd hh:mm:ss
     * @return
     */
    public static int compareDate(String DATE1, String DATE2, String format) {
        DateFormat df = new SimpleDateFormat(format);
        try {
            Date dt1 = df.parse(DATE1);
            Date dt2 = df.parse(DATE2);
            if (dt1.getTime() > dt2.getTime()) {
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                return -1;
            } else {
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


    /**
     * 传入月份，将period转换为MM的格式
     * @param period
     * @return
     */
    public static String getMonthTwoPlace(String period){
        if(period.length() == 2){
            return period;
        }

        if(period.length() == 1){
            return "0" + period;
        }
        return null;
    }

    /**
     * 将日期字符串转化为Date类型
     * @param dateStr
     * @param pattern
     * @return
     */
    public static Date StringToDate(String dateStr, String pattern) {
        try {
            DateFormat sdf = new SimpleDateFormat(pattern);
            Date date = sdf.parse(dateStr);
            return date;
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * 将日期转化为字符串类型
     * @param dateStr
     * @param pattern
     * @return
     */
    public static String dateToString(Date date, String pattern) {
        DateFormat sdf = new SimpleDateFormat(pattern);
        String dateStr = sdf.format(date);
        return dateStr;
    }



    public static Date getTodayBegin(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    public static Date getTodayEnd(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.add(Calendar.DAY_OF_YEAR,1);
        return calendar.getTime();
    }


    public static Date getWeekBegin(){
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR),cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0,0);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return cal.getTime();
    }

    public static Date getWeekEnd(){
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR),cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0,0);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        cal.add(Calendar.WEEK_OF_YEAR,1);
        return cal.getTime();
    }
    }
