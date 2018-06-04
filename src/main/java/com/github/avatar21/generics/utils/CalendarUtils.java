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
     * <p>add one day to given date</p>
     *
     * @param date given date
     * @return {@link Date} modified date (next day of given date)
     */
    public static Date addOneDay(Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 1);// 把日期往后增加一天.整数往后推,负数往前移动
        Date a = calendar.getTime();
        return a;
    }

    /**
     * <p>get day start date (HH:mm:ss.SSS = 00:00:00.000)</p>
     *
     * @param date date
     * @return {@link Date} modified date
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
     * <p>get date with 1 millisecond before next day (HH:mm:ss.SSS = 23:59:59.999)</p>
     *
     * @param date given date
     * @return {@link Date} modified date
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
     * <p>verify given date is between date range</p>
     *
     * @param targetTime given date (format: yyyy-MM-dd HH:mm:ss.SSS)
     * @param filterFromTime start compound date(format: yyyy-MM-dd HH:mm:ss.SSS)
     * @param filterToTime end compound date (format: yyyy-MM-dd HH:mm:ss.SSS)
     * @return whether is between date range
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
     * verify and parse given string to date (yyyyMMdd)
     *
     * @param strDate string date with format "yyyyMMdd"
     * @return return either successfully parsed date, or null
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
     * verify and parse string to date (yyyy-MM-dd)
     *
     * @param strDate string date with format "yyyy-MM-dd"
     * @return return either successfully parsed date, or null
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
     * verify and parse string to date (yyyyMMddHHmmss)
     *
     * @param strDate string date with format "yyyyMMddHHmmss"
     * @return return either successfully parsed date, or null
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
     * add or minus n time unit from now
     *
     * @param n (signed integer) time operand n
     * @param calUnit {@link CalendarUnit} time unit, given like CalendarUnit.DAY_OF_MONTH, CalendarUnit.MONTH ...
     * @return altered date
     */
    public static Date getNDaysFromNow(int n, CalendarUnit calUnit) {
        return getNDaysFrom(new Date(), n, calUnit);
    }

    /**
     * add or minus n time unit from given date
     *
     * @param date given date
     * @param n integer operand, positive for future date, negative for passed date
     * @param calUnit {@link CalendarUnit} time unit, e.g.: CalendarUnit.DAY, CalendarUnit.MONTH ...
     * @return 结束日期
     */
    public static Date getNDaysFrom(Date date, int n, CalendarUnit calUnit) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(calUnit.getUnit(), n);
        return cal.getTime();
    }

    /**
     * check if given year is a leap year
     *
     * @param year integer year
     * @return is leap year flag
     */
    public static boolean isLeapYear(int year) {
        return (year%4 == 0 && (year%100 != 0 || year%400 == 0));
    }

    /**
     * finds out difference between 2 given dates
     *
     * @param startDate begin date
     * @param endDate end date
     * @return {@link TimeDuration} bean which describe time difference between two dates
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
     * finds out realistic difference between 2 given dates (means take actual days of month, days of year into consideration)
     *
     * @param startDate begin date
     * @param endDate end date
     * @return {@link TimeDuration} bean which describe time difference between two dates
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
     * <p>determine given date pair is within the same day (ignoring time)</p>
     *
     * <p>E.g: passing "28 Mar 2002 13:45" and "28 Mar 2002 06:01" yield true.
     * passing "28 Mar 2002 13:45" and "12 Mar 2002 13:45" yield false.
     * </p>
     *
     * @param date1  date 1 (not null)
     * @param date2  date 2 (not null)
     * @return true if they falls on the same day
     * @throws IllegalArgumentException any date is null
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
     * <p>determine given date pair is within the same month (ignoring time)</p>
     *
     * <p>E.g: passing "28 Mar 2002 13:45" and "28 Mar 2002 06:01" yield true.
     * passing "28 Mar 2002 13:45" and "12 Apr 2002 13:45" yield false.
     * </p>
     *
     * @param date1  date 1 (not null)
     * @param date2  date 2 (not null)
     * @return true if they falls on the same month
     * @throws IllegalArgumentException any date is null
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
     * <p>determine given date pair is within the same year (ignoring time)</p>
     *
     * <p>E.g: passing "28 Mar 2002 13:45" and "28 Mar 2002 06:01" yield true.
     * passing "28 Mar 2002 13:45" and "Mar 2003 13:45" yield false.
     * </p>
     *
     * @param date1  date 1 (not null)
     * @param date2  date 2 (not null)
     * @return true if they falls on the same year
     * @throws IllegalArgumentException any date is null
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
     * <p>determine given {@link Calendar} pair is within the same day (ignoring time)</p>
     *
     * <p>E.g: passing "28 Mar 2002 13:45" and "28 Mar 2002 06:01" yield true.
     * passing "28 Mar 2002 13:45" and "12 Mar 2002 13:45" yield false.
     * </p>
     *
     * @param cal1  calendar 1 (not null)
     * @param cal2  calendar 2 (not null)
     * @return true if they falls on the same day
     * @throws IllegalArgumentException any calendar is <code>null</code>
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
     * <p>determine given {@link Calendar} pair is within the same month (ignoring time)</p>
     *
     * <p>E.g: passing "28 Mar 2002 13:45" and "28 Mar 2002 06:01" yield true.
     * passing "28 Mar 2002 13:45" and "12 Apr 2002 13:45" yield false.
     * </p>
     *
     * @param cal1  calendar 1 (not null)
     * @param cal2  calendar 2 (not null)
     * @return true if they falls on the same month
     * @throws IllegalArgumentException any calendar is <code>null</code>
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
     * <p>determine given {@link Calendar} pair is within the same year (ignoring time)</p>
     *
     * <p>E.g: passing "28 Mar 2002 13:45" and "28 Mar 2002 06:01" yield true.
     * passing "28 Mar 2002 13:45" and "12 Apr 2003 13:45" yield false.
     * </p>
     *
     * @param cal1  calendar 1 (not null)
     * @param cal2  calendar 2 (not null)
     * @return true if they falls on the same year
     * @throws IllegalArgumentException any calendar is <code>null</code>
     */
    public static boolean isSameYear(Calendar cal1, Calendar cal2) {
        if (cal1 == null || cal2 == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        return (cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA) &&
                cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR));
    }

}
