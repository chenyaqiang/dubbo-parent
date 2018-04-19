package com.dubbo.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Description:
 * @Author: wb
 * @CreateDate: 2018-04-17 19:49
 * @version:
 **/
public final class DateUtil {

    public static final Logger logger = LoggerFactory.getLogger(DateUtil.class);


    public static final String YYMMDD = "yy-MM-dd";
    public static final String YYYYMMDD = "yyyy-MM-dd";
    public static final String YYYYMMDDHHmm = "yyyy-MM-dd HH:mm";
    public static final String YYYYMMDDHHmmss = "yyyy-MM-dd HH:mm:ss";

    public static final String YYYYMMDD_SLASH = "yyyy/MM/dd";

    private static final String TIME = "HH:mm:ss";

    private DateUtil() {
        super();
    }

    /**
     * 获取当前时间的时间对象
     *
     * @return 时间对象
     */
    public static final Date nowDate() {
        return new Date();
    }


    /**
     * 获取指定时间的年
     *
     * @param date 时间对象
     * @return 年数
     */
    public static final int year(Date date) {
        if (date == null) return 0;

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 获取指定时间的月
     *
     * @param date 时间对象
     * @return 月数
     */
    public static final int month(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取指定时间的日
     *
     * @param date 时间对象
     * @return 日数
     */
    public static final int day(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DATE);
    }

    /**
     * 获取一个时间对象
     *
     * @param year  格式为：2004
     * @param month 从1开始
     * @param date  从1开始
     * @return 时间对象
     */
    public static final Date getDate(int year, int month, int date) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, date);
        return calendar.getTime();
    }

    /**
     * 获取一个时间对象
     *
     * @param year   格式为：2004
     * @param month  从1开始
     * @param date   从1开始
     * @param hour   小时
     * @param minute 分钟
     * @param second 秒钟
     * @return 日期对象
     */
    public static final Date getDateTime(int year, int month, int date, int hour, int minute, int second) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, date, hour, minute, second);
        return calendar.getTime();
    }


    /**
     * 在一个已知时间的基础上增加指定的时间,负数表示减少
     *
     * @param oldDate 已知时间对象
     * @param year    年
     * @param month   月
     * @param date    日
     * @return 时间对象
     */
    public static final Date addDate(Date oldDate, int year, int month, int date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(oldDate);
        calendar.add(Calendar.YEAR, year);
        calendar.add(Calendar.MONTH, month);
        calendar.add(Calendar.DATE, date);
        return calendar.getTime();
    }

    /**
     * 从字符串转换为date 默认格式为 "yyyy-MM-dd"
     *
     * @param source 需转换的字符串
     * @return 时间对象
     */
    public static final Date parseDate(String source) {
        if (source == null || source.trim().length() == 0) return null;
        // 判断日期格式
        if (source.trim().length() == 8) try {
            Date returnDate = new SimpleDateFormat(YYYYMMDD).parse(source);
            return returnDate;
        } catch (ParseException e) {
            logger.error("DateUtil parseDate error", e);
            return null;
        }
        try {
            Date returnDate = new SimpleDateFormat(YYYYMMDD).parse(source);
            return returnDate;
        } catch (ParseException e) {
            logger.error("DateUtil parseDate error", e);
            return null;
        }
    }

    /**
     * 从字符串转换为date 默认格式为 "yyyy-MM-dd HH:mm"
     *
     * @param source 需转换的字符串
     * @return 日期对象
     */
    public static final Date parseDateTime(String source) {
        if (source == null || source.length() == 0) return null;
        try {
            return new SimpleDateFormat(YYYYMMDDHHmm).parse(source);
        } catch (ParseException e) {
            logger.error("DateUtil parseDate error", e);
            return null;
        }
    }

    /**
     * 从字符串转换为date 默认格式为 "yyyy-MM-dd HH:mm:ss"
     *
     * @param source 需转换的字符串
     * @return 日期对象
     */
    public static final Date parseDateTimes(String source) {
        if (source == null || source.equals("") || source.length() == 0) return null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sdf.parse(source);
        } catch (ParseException e) {
            logger.error("DateUtil parseDate error", e);
        }
        return null;
    }

    /**
     * 格式化输出（只读的时候） 默认格式为 "yyyy-MM-dd"
     *
     * @param date 时间对象
     * @return 格式化后的时间字符串
     */
    public static String formatDate(Date date) {
        if (date == null) return "";
        return new SimpleDateFormat(YYYYMMDD).format(date);
    }

    public static String formatDateTimeS(Date date) {
        if (date == null) return "";
        return new SimpleDateFormat(YYYYMMDDHHmmss).format(date);
    }

}
