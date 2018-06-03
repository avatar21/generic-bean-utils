package com.github.avatar21.generics.constants;

import java.util.Calendar;

public enum CalendarUnit {
    MILLISECOND(Calendar.MILLISECOND, 1L),
    SECOND(Calendar.SECOND, 1000 * MILLISECOND.milliSec),
    MINUTE(Calendar.MINUTE, 60 * SECOND.milliSec),
    HOUR(Calendar.HOUR_OF_DAY, 60 * MINUTE.milliSec),
    DAY(Calendar.DAY_OF_YEAR, 24 * HOUR.milliSec),
    DAY_OF_MONTH(Calendar.DAY_OF_MONTH, DAY.milliSec),
    DAY_OF_WEEK(Calendar.DAY_OF_WEEK, DAY.milliSec),
    WEEK_OF_YEAR(Calendar.WEEK_OF_MONTH, 7 * DAY.milliSec),
    WEEK_OF_MONTH(Calendar.WEEK_OF_YEAR, WEEK_OF_YEAR.milliSec),
    MONTH_31_DAYS(Calendar.MONTH, 31 * DAY.milliSec),
    MONTH(Calendar.MONTH, 30 * DAY.milliSec),
    MONTH_29_DAYS(Calendar.MONTH, 29 * DAY.milliSec),
    MONTH_28_DAYS(Calendar.MONTH, 28 * DAY.milliSec),
    YEAR(Calendar.YEAR, 365 * DAY.milliSec);

    int unit;
    long milliSec;

    CalendarUnit(int unit, long milliSec) {
        this.unit = unit;
        this.milliSec = milliSec;
    }

    public int getUnit() {
        return unit;
    }

    public long getMilliSec() {
        return milliSec;
    }
}
