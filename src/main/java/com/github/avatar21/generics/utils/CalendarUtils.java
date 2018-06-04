package com.github.avatar21.generics.utils;

import com.github.avatar21.generics.model.TimeDuration;
import com.github.avatar21.generics.constants.CalendarUnit;
import com.github.avatar21.generics.constants.Regexp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * calendar/ date utility functions
 */
public class CalendarUtils {
    private static final Logger logger = LoggerFactory.getLogger(CalendarUtils.class);

    /**
     * <p>日期加一天</p>
     *
     * @param date 日期
     * @return {@link Date}已修改日期
     */
    public static Date addOneDay(Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 1);// 把日期往后增加一天.整数往后推,负数往前移动
        Date a = calendar.getTime();
        return a;
    }

    /**
     * <p>获取日期，一天开始时间 (HH:mm:ss.SSS = 00:00:00.000)</p>
     *
     * @param date 日期
     * @return {@link Date}已修改日期
     */
    public static Date getDayStartTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * <p>获取日期，第二天开始前一毫秒的时间 (HH:mm:ss.SSS = 23:59:59.999)</p>
     *
     * @param date 日期
     * @return {@link Date}已修改日期
     */
    public static Date getDayEndTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        return cal.getTime();
    }

    /**
     * <p>verify time is fall within 2 diff time</p>
     *
     * @param targetTime 校验日期(格式: yyyy-MM-dd HH:mm:ss.SSS)
     * @param filterFromTime 开始范围日期(格式: yyyy-MM-dd HH:mm:ss.SSS)
     * @param filterToTime 结束范围日期(格式: yyyy-MM-dd HH:mm:ss.SSS)
     * @return whether is within time flag
     */
    public static Boolean withinTime(String targetTime, String filterFromTime, String filterToTime) {
        Boolean isWithinTime = false;

        Date targetDate = null;
        Date fromDate = null;
        Date toDate = null;
        try {
            targetDate = Regexp.DATE_FORMAT_FULL_WITH_MISEC.parse(targetTime);
        } catch (ParseException e) {
            logger.error(e.getLocalizedMessage());
        }
        try {
            fromDate = Regexp.DATE_FORMAT_FULL_WITH_MISEC.parse(filterFromTime);
        } catch (ParseException e) {
            logger.error(e.getLocalizedMessage());
        }
        try {
            toDate = Regexp.DATE_FORMAT_FULL_WITH_MISEC.parse(filterToTime);
        } catch (ParseException e) {
            logger.error(e.getLocalizedMessage());
        }

        Calendar calTarget = Calendar.getInstance();
        calTarget.setTime(targetDate);

        Calendar calFrom = Calendar.getInstance();
        calFrom.setTime(fromDate);

        Calendar calTo = Calendar.getInstance();
        calTo.setTime(toDate);
        logger.debug(String.format("[TEST]\n\t target = %s\n\t from = %s\n\t to = %s",
                Regexp.DATE_FORMAT_FULL_WITH_MISEC.format(calTarget.getTime()),
                Regexp.DATE_FORMAT_FULL_WITH_MISEC.format(calFrom.getTime()),
                Regexp.DATE_FORMAT_FULL_WITH_MISEC.format(calTo.getTime())));
        logger.debug(String.format("[TEST]\n\t from greater(%s) OR equals(%s)\n\t to greater(%s) OR equals(%s)",
                calFrom.before(calTarget), calFrom.equals(calTarget),
                calTo.after(calTarget), calTo.equals(calTarget)));
        if ((calFrom.before(calTarget) || calFrom.equals(calTarget)) && (calTo.after(calTarget) || calTo.equals(calTarget))) {
            isWithinTime = true;
        }

        return isWithinTime;
    }

    /**
     * 转换字符串为日期
     *
     * @param strDate yyyyMMdd 格式
     * @return 成功返回已转换的日期，失败则返回null
     */
    public static Date verifyAndParseToDate08(String strDate) {
        Date date = null;
        try {
            date = (!StringUtils.isEmpty(strDate) && strDate.matches(Regexp.DATE_NUMERIC_08_REGEX) ? Regexp.DATE_FORMAT_SHORT_08.parse(strDate.trim()) : null);
        } catch (ParseException e) {
            logger.error("Error while parsing string to date with format \"yyyyMMdd\"", e);
        }
        return date;
    }

    /**
     * 转换字符串为日期 (yyyy-MM-dd)
     *
     * @param strDate yyyy-MM-dd 格式
     * @return 成功返回已转换的日期，失败则返回null
     */
    public static Date verifyAndParseToDate10(String strDate) {
        Date date = null;
        try {
            date = (!StringUtils.isEmpty(strDate) && strDate.matches(Regexp.DATE_FORMAT_DATE_ONLY_REGEX) ? Regexp.DATE_FORMAT_DATE_ONLY.parse(strDate.trim()) : null);
        } catch (ParseException e) {
            logger.error("Error while parsing string to date with format \"yyyyMMdd\"", e);
        }
        return date;
    }

    /**
     * 转换字符串为日期
     *
     * @param strDate ’yyyyMMddHHmmss‘ 格式
     * @return 成功返回已转换的日期，失败则返回null
     */
    public static Date verifyAndParseToDate14(String strDate) {
        Date date = null;
        try {
            date = (!StringUtils.isEmpty(strDate) && strDate.matches(Regexp.DATE_NUMERIC_14_REGEX) ? Regexp.DATE_FORMAT_LONG_14.parse(strDate.trim()) : null);
        } catch (ParseException e) {
            logger.error("Error while parsing string to date with format \"yyyyMMddHHmmss\"", e);
        }
        return date;
    }

    /**
     * 获取今天之前或之后的时间
     *
     * @param n 整数
     * @param type Calendar.DAY_OF_MONTH, Calendar.MONTH ...
     * @return 日期
     */
    public static Date getNDaysFromNow(int n, CalendarUnit type) {
        return getNDaysFrom(new Date(), n, type);
    }

    /**
     * 获取指定日期，之前或之后的时间
     *
     * @param date 开始日期
     * @param n 整数 +(正数)之后，-(负数)之前
     * @param calUnit {@link CalendarUnit} CalendarUnit.DAY, CalendarUnit.MONTH ...
     * @return 结束日期
     */
    public static Date getNDaysFrom(Date date, int n, CalendarUnit calUnit) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(calUnit.getUnit(), n);
        return cal.getTime();
    }

    /**
     * 是否闰年
     *
     * @param year n年份
     * @return 是否闰年标识
     */
    public static boolean isLeapYear(int year) {
        return (year%4 == 0 && (year%100 != 0 || year%400 == 0));
    }

    /**
     * 计算两个日期之间的差异
     *
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return {@link TimeDuration} 解析结果(分年，月，日，分，秒，毫秒)
     */
    public static TimeDuration calculateTimeDuration(Date startDate, Date endDate){
        TimeDuration duration = new TimeDuration();
        long totalTime = endDate.getTime() - startDate.getTime();
        duration.setTotalTime(Math.abs(totalTime));
        long remainderOfYear = duration.getTotalTime() % CalendarUnit.YEAR.getMilliSec();
        long remainderOfMonth = remainderOfYear % CalendarUnit.MONTH.getMilliSec();
        long remainderOfWeek = remainderOfMonth % CalendarUnit.WEEK_OF_MONTH.getMilliSec();
        long remainderOfDay = remainderOfWeek % CalendarUnit.DAY.getMilliSec();
        long remainderOfHour = remainderOfDay % CalendarUnit.HOUR.getMilliSec();
        long remainderOfMinute = remainderOfHour % CalendarUnit.MINUTE.getMilliSec();
        long remainderOfSecond = remainderOfMinute % CalendarUnit.SECOND.getMilliSec();
        duration.setYear((int) (duration.getTotalTime() / CalendarUnit.YEAR.getMilliSec()));
        duration.setMonth((int) (remainderOfYear / CalendarUnit.MONTH.getMilliSec()));
        duration.setWeek((int) (remainderOfMonth / CalendarUnit.WEEK_OF_MONTH.getMilliSec()));
        duration.setDayOfMonth((int) (remainderOfMonth / CalendarUnit.DAY.getMilliSec()));
        duration.setDayOfWeek((int) (remainderOfWeek / CalendarUnit.DAY.getMilliSec()));
        duration.setHour((int) (remainderOfDay / CalendarUnit.HOUR.getMilliSec()));
        duration.setMinute((int) (remainderOfHour / CalendarUnit.MINUTE.getMilliSec()));
        duration.setSecond((int) (remainderOfMinute / CalendarUnit.SECOND.getMilliSec()));
        duration.setMilisec((int) remainderOfSecond);

        return duration;
    }

    /**
     * 计算两个日期之间的差异
     *
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return {@link TimeDuration} 解析结果(分年，月，日，分，秒，毫秒)
     */
    public static TimeDuration calculateTimeDurationRealistic(Date startDate, Date endDate){
        TimeDuration duration = new TimeDuration();
        long totalTime = endDate.getTime() - startDate.getTime();
        duration.setTotalTime(Math.abs(totalTime));
        long remainderOfYear = duration.getTotalTime() % CalendarUnit.YEAR.getMilliSec();
        duration.setYear((int) (duration.getTotalTime() / CalendarUnit.YEAR.getMilliSec()));
        int generalMonth = (int) (remainderOfYear / CalendarUnit.MONTH.getMilliSec());
        int month = generalMonth;
        long remainderOfMonth;
        if (generalMonth >= 6) {
            remainderOfMonth = remainderOfYear;
            Calendar cal = Calendar.getInstance();
            cal.setTime(endDate);
            int endDateYr = cal.get(Calendar.YEAR) + 1900;
            long febMillisec;
            if (isLeapYear(endDateYr)) {
                febMillisec = CalendarUnit.MONTH_29_DAYS.getMilliSec();
            } else {
                febMillisec = CalendarUnit.MONTH_28_DAYS.getMilliSec();
            }
            remainderOfMonth -= febMillisec;
            --generalMonth;
            long maxMonthMillisec = generalMonth * CalendarUnit.MONTH_31_DAYS.getMilliSec();
            long minMonthMillisec = generalMonth * CalendarUnit.MONTH.getMilliSec();
            long avgMonthMillisec = (maxMonthMillisec + minMonthMillisec) / 2;
            remainderOfMonth -= avgMonthMillisec;
        } else {
            remainderOfMonth = remainderOfYear % CalendarUnit.MONTH.getMilliSec();
        }
        long remainderOfWeek = remainderOfMonth % CalendarUnit.WEEK_OF_MONTH.getMilliSec();
        long remainderOfDay = remainderOfWeek % CalendarUnit.DAY.getMilliSec();
        long remainderOfHour = remainderOfDay % CalendarUnit.HOUR.getMilliSec();
        long remainderOfMinute = remainderOfHour % CalendarUnit.MINUTE.getMilliSec();
        long remainderOfSecond = remainderOfMinute % CalendarUnit.SECOND.getMilliSec();
        duration.setMonth(month);
        duration.setWeek((int) (remainderOfMonth / CalendarUnit.WEEK_OF_MONTH.getMilliSec()));
        duration.setDayOfMonth((int) (remainderOfMonth / CalendarUnit.DAY.getMilliSec()));
        duration.setDayOfWeek((int) (remainderOfWeek / CalendarUnit.DAY.getMilliSec()));
        duration.setHour((int) (remainderOfDay / CalendarUnit.HOUR.getMilliSec()));
        duration.setMinute((int) (remainderOfHour / CalendarUnit.MINUTE.getMilliSec()));
        duration.setSecond((int) (remainderOfMinute / CalendarUnit.SECOND.getMilliSec()));
        duration.setMilisec((int) remainderOfSecond);

        return duration;
    }

    /**
     * <p>判断是否两个日期是同一天，忽略时间</p>
     *
     * <p>传 28 Mar 2002 13:45 与 28 Mar 2002 06:01 返回true.
     * 传 28 Mar 2002 13:45 与 12 Mar 2002 13:45 返回 false.
     * </p>
     *
     * @param date1  日期1, 非空
     * @param date2  日期2, 非空
     * @return true 如果它们是同一天
     * @throws IllegalArgumentException 任何一个日期为空值
     * @since 1.1.9
     */
    public static boolean isSameDay(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        return isSameDay(cal1, cal2);
    }

    /**
     <p>判断是否两个日期是同一个月，忽略时间</p>
     *
     * <p>传 28 Mar 2002 13:45 与 28 Mar 2002 06:01 返回true.
     * 传 28 Mar 2002 13:45 与 12 Apr 2002 13:45 返回 false.
     * </p>
     *
     * @param date1  日期1, 非空
     * @param date2  日期2, 非空
     * @return true 如果它们是同一个月
     * @throws IllegalArgumentException 任何一个日期为空值
     * @since 1.1.9
     */
    public static boolean isSameMonth(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        return isSameMonth(cal1, cal2);
    }

    /**
     <p>判断是否两个日期是同一年，忽略时间</p>
     *
     * <p>传 28 Mar 2002 13:45 与 28 Mar 2002 06:01 返回true.
     * 传 28 Mar 2002 13:45 与 12 Mar 2003 13:45 返回 false.
     * </p>
     *
     * @param date1  日期1, 非空
     * @param date2  日期2, 非空
     * @return true 如果它们是同一年
     * @throws IllegalArgumentException 任何一个日期为空值
     * @since 1.1.9
     */
    public static boolean isSameYear(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        return isSameYear(cal1, cal2);
    }

    /**
     * <p>判断两个{@link Calendar} 类之间是否同一天，忽略时间。</p>
     *
     * <p>28 Mar 2002 13:45 与 28 Mar 2002 06:01 返回true.
     * 28 Mar 2002 13:45 与 12 Mar 2002 13:45 返回 false.
     * </p>
     *
     * @param cal1  第一个日历类, 非空
     * @param cal2  第二个日历类, 非空
     * @return 若是同一天，则返回 true
     * @throws IllegalArgumentException 任何一个日历为 <code>null</code>
     * @since 1.1.9
     */
    public static boolean isSameDay(Calendar cal1, Calendar cal2) {
        if (cal1 == null || cal2 == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        return (cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA) &&
                cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR));
    }

    /**
     * <p>判断两个{@link Calendar} 类之间是否同一月，忽略时间。</p>
     *
     * <p>28 Mar 2002 13:45 与 28 Mar 2002 06:01 返回true.
     * 28 Mar 2002 13:45 与 12 Apr 2002 13:45 返回 false.
     * </p>
     *
     * @param cal1  第一个日历类, 非空
     * @param cal2  第二个日历类, 非空
     * @return 若是同一月，则返回 true
     * @throws IllegalArgumentException 任何一个日历为 <code>null</code>
     * @since 1.1.9
     */
    public static boolean isSameMonth(Calendar cal1, Calendar cal2) {
        if (cal1 == null || cal2 == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        return (cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA) &&
                cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH));
    }

    /**
     * <p>判断两个{@link Calendar} 类之间是否同一年，忽略时间。</p>
     *
     * <p>28 Mar 2002 13:45 与 28 Mar 2002 06:01 返回true.
     * 28 Mar 2002 13:45 与 12 Mar 2003 13:45 返回 false.
     * </p>
     *
     * @param cal1  第一个日历类, 非空
     * @param cal2  第二个日历类, 非空
     * @return 若是同一年，则返回 true
     * @throws IllegalArgumentException 任何一个日历为 <code>null</code>
     * @since 1.1.9
     */
    public static boolean isSameYear(Calendar cal1, Calendar cal2) {
        if (cal1 == null || cal2 == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        return (cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA) &&
                cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR));
    }

}
