package com.cimarasah.auth.config.redisson;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SentinelServersConfig;
import org.redisson.config.SingleServerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by andre.matteo on 2/17/18.
 */
@Configuration
public class RedissonConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(RedissonConfiguration.class);

    @Autowired
    private RedisProperties redisProperties;

    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        //sentinel
        if (redisProperties.getSentinel() != null) {
            SentinelServersConfig sentinelServersConfig = config.useSentinelServers();
            sentinelServersConfig.setMasterName(redisProperties.getSentinel().getMaster());
            redisProperties.getSentinel().getNodes();
            sentinelServersConfig.addSentinelAddress(redisProperties.getSentinel().getNodes().split(","));
            sentinelServersConfig.setDatabase(redisProperties.getDatabase());
            if (redisProperties.getPassword() != null) {
                sentinelServersConfig.setPassword(redisProperties.getPassword());
            }
        } else { //single server

            logger.info("**** Redisson Configuration ****");
            logger.info("Database {} ", redisProperties.getDatabase());
            logger.info("Host {} ", redisProperties.getHost());
            logger.info("SSL {} ", redisProperties.isSsl());
            logger.info("Timeout {} ", redisProperties.getTimeout());
            logger.info("MaxIdle {} ", redisProperties.getPool().getMaxIdle());
            logger.info("MinIdle {} ", redisProperties.getPool().getMinIdle());
            logger.info("MaxActive {} ", redisProperties.getPool().getMaxActive());

            SingleServerConfig singleServerConfig = config.useSingleServer();
            String schema = redisProperties.isSsl() ? "rediss://" : "redis://";
            singleServerConfig.setAddress(schema + redisProperties.getHost() + ":" + redisProperties.getPort());
            singleServerConfig.setDatabase(redisProperties.getDatabase());
            if (redisProperties.getPassword() != null) {
                singleServerConfig.setPassword(redisProperties.getPassword());
            }
        }
        return Redisson.create(config);
    }
}
