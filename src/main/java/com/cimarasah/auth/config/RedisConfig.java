package com.cimarasah.auth.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedisConfig {

    @Value("${redis.timeout}")
    private long timeout;

    @Value("${redis.timeunit}")
    private String timeUnit;

    @Bean
    public RedisConfiguration redisConfiguration() {
        return new RedisConfiguration(timeout, timeUnit);
    }
}
