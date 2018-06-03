package com.github.avatar21.generics.model;

/**
 * <p>
 * TimeDuration class.
 * </p>
 *
 * @author wzw
 * @version 1.0.0
 * @since 2018-04-10 Ver 1.1.8
 */
public class TimeDuration {
    /**
     * total elapsed time (milli sec(s))
     */
    private long totalTime;

    /**
     * elapse year(s)
     */
    private int year;

    /**
     * elapsed month(s)
     */
    private int month;

    /**
     * elapsed week(s)
     */
    private int week;

    /**
     * elapsed day(s) of month
     */
    private int dayOfMonth;

    /**
     * elapsed day(s) of week
     */
    private int dayOfWeek;

    /**
     * elapsed hour(s)
     */
    private int hour;

    /**
     * elapsed minute(s)
     */
    private int minute;

    /**
     * elapsed second(s)
     */
    private int second;

    /**
     * elapsed milli seconds(s)
     */
    private int milisec;

    public long getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(long totalTime) {
        this.totalTime = totalTime;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public int getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(int dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    public int getMilisec() {
        return milisec;
    }

    public void setMilisec(int milisec) {
        this.milisec = milisec;
    }
}
