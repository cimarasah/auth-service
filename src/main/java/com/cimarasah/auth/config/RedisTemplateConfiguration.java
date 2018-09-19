package com.cimarasah.auth.config;

import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.data.redis.cache.RedisCacheManager;
//import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
//
//import com.fasterxml.jackson.annotation.JsonTypeInfo;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
//
//import redis.clients.jedis.JedisPoolConfig;
//import redis.clients.jedis.JedisShardInfo;

@Configuration
public class RedisTemplateConfiguration {

    @Value("${redis.hostname}")
    private String hostname;

    @Value("${redis.port}")
    private int port;

    @Value("${redis.ssl}")
    private boolean ssl;

    @Value("${redis.password:#{null}}")
    private String password;

    @Value("${redis.database}")
    private int database;

//    @Bean
//    public JedisConnectionFactory jedisConnectionFactory() {
//        JedisShardInfo jedisShardInfo = new JedisShardInfo(hostname, port, ssl);
//        if (password != null) {
//            jedisShardInfo.setPassword(password);
//        }
//
//        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(jedisShardInfo);
//        jedisConnectionFactory.setHostName(hostname);
//        jedisConnectionFactory.setPort(port);
//
//        JedisPoolConfig poolConfig = new JedisPoolConfig();
//        poolConfig.setTestWhileIdle(true);
//        poolConfig.setMaxIdle(2000);
//        poolConfig.setMaxTotal(2000);
//        poolConfig.setBlockWhenExhausted(true);
//        poolConfig.setMinIdle(50);
//        poolConfig.setMinEvictableIdleTimeMillis(60000);
//        poolConfig.setTimeBetweenEvictionRunsMillis(30000);
//        poolConfig.setNumTestsPerEvictionRun(-1);
//
//        jedisConnectionFactory.setPoolConfig(poolConfig);
//        if (password != null) {
//            jedisConnectionFactory.setPassword(password);
//        }
//
//        jedisConnectionFactory.setDatabase(database);
//        return jedisConnectionFactory;
//    }
//
//    @Bean
//    @Primary
//    public RedisTemplate<String, Object> redisTemplate() {
//        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
//        redisTemplate.setConnectionFactory(jedisConnectionFactory());
//        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.registerModule(new JavaTimeModule());
//        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
//
//        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer(objectMapper));
//        return redisTemplate;
//    }
//
//    @Bean
//    public RedisCacheManager cacheManager() {
//        return new RedisCacheManager(redisTemplate());
//    }
}
