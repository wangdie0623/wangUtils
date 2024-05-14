package cn.wang.custom.utils;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 日期相关工具类
 */
public class WDateUtils {
    public static final String DATE_FORMAT_PATTERN_1 = "yyyy-MM-dd HH:mm:ss";

    public static final Pattern PATTERN_1 = Pattern.compile("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}");

    public static final String DATE_FORMAT_PATTERN_2 = "yyyy-MM-dd HH:mm:ss.SSS";

    public static final Pattern PATTERN_2 = Pattern.compile("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}\\.\\d{3}");

    public static final String DATE_FORMAT_PATTERN_3 = "yyyyMMddHHmmss";

    public static final Pattern PATTERN_3 = Pattern.compile("\\d{14}");

    public static final String DATE_FORMAT_PATTERN_4 = "yyyyMMdd";

    public static final Pattern PATTERN_4 = Pattern.compile("\\d{8}");

    public static final String DATE_FORMAT_PATTERN_5 = "yyyy-MM-dd";

    public static final Pattern PATTERN_5 = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");

    public static final String DATE_FORMAT_PATTERN_6 = "yyyy.MM.dd";

    public static final Pattern PATTERN_6 = Pattern.compile("\\d{4}\\.\\d{2}\\.\\d{2}");


    /**
     * 得到当前天 00:00:00
     *
     * @return 当前天 00:00:00
     */
    public static Date getNowDayStartTime() {
        return getDateOfStartTime(new Date());
    }

    /**
     * 得到当前天 23:59:59
     *
     * @return 当前天 23:59:59
     */
    public static Date getNowDayEndTime() {
        return getDateOfEndTime(new Date());
    }

    /**
     * 转换日期为当天 0:00:00;
     */
    public static Date getDateOfStartTime(Date source) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(source);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /**
     * 转换日期为当天 23:59:59
     *
     * @param source 来源日期
     * @return 当天 23:59:59
     */
    public static Date getDateOfEndTime(Date source) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(source);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
    }

    /**
     * 将匹配格式字符串转换成Date 对象
     * 支持格式：
     * yyyy-MM-dd HH:mm:ss
     * yyyy-MM-dd HH:mm:ss.SSS
     * yyyyMMddHHmmss
     * yyyyMMdd
     * yyyy-MM-dd
     * yyyy.MM.dd
     *
     * @param dateStr 日期字符串
     * @return 日期
     */
    public static Date getDate(String dateStr) {
        SimpleDateFormat format = null;
        try {
            if (PATTERN_1.matcher(dateStr).matches()) {
                format = getPattern(DATE_FORMAT_PATTERN_1);
                return format.parse(dateStr);
            }
            if (PATTERN_2.matcher(dateStr).matches()) {
                format = getPattern(DATE_FORMAT_PATTERN_2);
                return format.parse(dateStr);
            }
            if (PATTERN_3.matcher(dateStr).matches()) {
                format = getPattern(DATE_FORMAT_PATTERN_3);
                return format.parse(dateStr);
            }
            if (PATTERN_4.matcher(dateStr).matches()) {
                format = getPattern(DATE_FORMAT_PATTERN_4);
                return format.parse(dateStr);
            }
            if (PATTERN_5.matcher(dateStr).matches()) {
                format = getPattern(DATE_FORMAT_PATTERN_5);
                return format.parse(dateStr);
            }
            if (PATTERN_6.matcher(dateStr).matches()) {
                format = getPattern(DATE_FORMAT_PATTERN_6);
                return format.parse(dateStr);
            }
            throw new RuntimeException("不被支持的日期数据格式:" + dateStr);
        } catch (Exception e) {
            assert format != null;
            throw new RuntimeException("日期数据转换异常:" + dateStr + "|" + format.toPattern());
        }
    }

    /**
     * 以格式pattern，尝试转换dateStr 为Date对象
     *
     * @param dateStr 日期字符串
     * @param pattern 日期格式
     * @return 日期
     */
    public static Date getDate(String dateStr, String pattern) {
        SimpleDateFormat format = getPattern(pattern);
        try {
            return format.parse(dateStr);
        } catch (Exception e) {
            throw new RuntimeException("日期数据转换异常:" + dateStr + "|" + pattern);
        }
    }

    /**
     * 转换Date对象为指定格式：yyyy-MM-dd HH:mm:ss 字符串
     *
     * @param date 日期
     * @return 日期字符串
     */
    public static String getStr(Date date) {
        if (date == null) {
            return "";
        }
        return getPattern(DATE_FORMAT_PATTERN_1).format(date);
    }

    /**
     * 将Date对象转为指定格式字符串
     *
     * @param date        日期
     * @param datePattern 日期格式
     * @return 日期字符串
     */
    public static String getStr(Date date, String datePattern) {
        if (date == null || StringUtils.isBlank(datePattern)) {
            return "";
        }
        SimpleDateFormat pattern = getPattern(datePattern);
        return pattern.format(date);
    }

    /**
     * 创建日期格式化对象
     *
     * @param datePattern 日期格式
     * @return 日期转换工具对象
     */
    private static SimpleDateFormat getPattern(String datePattern) {
        return new SimpleDateFormat(datePattern);
    }

    /**
     * 得到当前周（中国人），所有Date对象
     *
     * @return 当前周日期集合
     */
    public static List<Date> getNowWeekList() {
        return getWeekList(new Date());
    }

    /**
     * 根据指定天 获取包含它的那一周(中国人)的Date集合
     *
     * @param position 日期
     * @return 当前周日期集合
     */
    public static List<Date> getWeekList(Date position) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(position);
        int i = calendar.get(Calendar.DAY_OF_WEEK);
        List<Date> result = new ArrayList<>();
        //根据外国人的每周第一天做调整
        if (i == 1) {
            calendar.add(Calendar.DAY_OF_YEAR, -6);
        } else {
            calendar.set(Calendar.DAY_OF_WEEK, 2);
        }
        //每周第一天
        result.add(calendar.getTime());
        for (int j = 0; j < 6; j++) {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            result.add(calendar.getTime());
        }
        return result;
    }

    /**
     * 得到当前时间，包含它的那周（中国人），格式:yyyy-MM-dd HH:mm:ss 的字符串集合
     *
     * @return 当前周日期字符串集合
     */
    public static List<String> getNowWeekStrList() {
        return getWeekStrList(new Date(), DATE_FORMAT_PATTERN_1);
    }

    /**
     * 得到指定日期，包含它的那周（中国人），指定格式的字符串集合
     *
     * @param position 日期
     * @param pattern  日期格式
     * @return 当前周日期字符串集合
     */
    public static List<String> getWeekStrList(Date position, String pattern) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(position);
        int i = calendar.get(Calendar.DAY_OF_WEEK);
        List<String> result = new ArrayList<>();
        SimpleDateFormat patternFormat = getPattern(pattern);
        //根据外国人的每周第一天做调整
        if (i == 1) {
            calendar.add(Calendar.DAY_OF_YEAR, -6);
        } else {
            calendar.set(Calendar.DAY_OF_WEEK, 2);
        }
        //每周第一天
        result.add(patternFormat.format(calendar.getTime()));
        for (int j = 0; j < 6; j++) {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            result.add(patternFormat.format(calendar.getTime()));
        }
        return result;
    }

    /**
     * 判断dateStr1是不是在dateStr2前面
     * 支持格式：
     * yyyy-MM-dd HH:mm:ss
     * yyyy-MM-dd HH:mm:ss.SSS
     * yyyyMMddHHmmss
     * yyyyMMdd
     * yyyy-MM-dd
     * yyyy.MM.dd
     *
     * @param dateStr1 日期字符串
     * @param dateStr2 日期字符串
     * @return true-是 false-不是
     */
    public static boolean before(String dateStr1, String dateStr2) {
        Date date1 = getDate(dateStr1);
        Date date2 = getDate(dateStr2);
        return date1.before(date2);
    }

    /**
     * 判断dateStr1是不是在dateStr2后面
     * 支持格式：
     * yyyy-MM-dd HH:mm:ss
     * yyyy-MM-dd HH:mm:ss.SSS
     * yyyyMMddHHmmss
     * yyyyMMdd
     * yyyy-MM-dd
     * yyyy.MM.dd
     *
     * @param dateStr1 日期字符串
     * @param dateStr2 日期字符串
     * @return true-是 false-不是
     */
    public static boolean after(String dateStr1, String dateStr2) {
        Date date1 = getDate(dateStr1);
        Date date2 = getDate(dateStr2);
        return date1.after(date2);
    }

    /**
     * 得到指定日期前N天日期
     *
     * @param source 指定日期
     * @param days   N天
     * @return 日期
     */
    public static Date beforeNDays(Date source, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(source);
        calendar.add(Calendar.DAY_OF_YEAR, -days);
        return calendar.getTime();
    }

    /**
     * 得到指定日期后N天日期
     *
     * @param source 指定日期
     * @param days   N天
     * @return 日期
     */
    public static Date afterNDays(Date source, int days) {
        return beforeNDays(source, -days);
    }

}
