package com.github.avatar21.generics.utils;

import java.util.concurrent.TimeUnit;

/**
 * time watch utility functions
 */
public class WatchTimer {
    long starts;

    public static WatchTimer start() {
        return new WatchTimer();
    }

    private WatchTimer() {
        reset();
    }

    public WatchTimer reset() {
        starts = System.currentTimeMillis();
        return this;
    }

    public long time() {
        long ends = System.currentTimeMillis();
        return ends - starts;
    }

    public long time(TimeUnit unit) {
        return unit.convert(time(), TimeUnit.MILLISECONDS);
    }
}