package com.cimarasah.auth.config;

import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

@Component
public class RedisConfiguration {

    private long timeout;
    private TimeUnit timeUnit;

    public RedisConfiguration() {}

    public RedisConfiguration(long timeout, String timeUnit) {
        this.timeout = timeout;
        this.timeUnit = convertToTimeUnit(timeUnit);
    }

    private TimeUnit convertToTimeUnit(String timeUnit) {
        return TimeUnit.valueOf(timeUnit);
    }

    public long getTimeout() {
        return timeout;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }
}
