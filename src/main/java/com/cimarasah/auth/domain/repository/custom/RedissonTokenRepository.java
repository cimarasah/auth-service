package com.cimarasah.auth.domain.repository.custom;

import java.util.Date;

import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cimarasah.auth.config.RedisConfiguration;
import com.cimarasah.auth.domain.repository.TokenRepository;

/**
 * Created by andre.matteo on 2/17/18.
 */
@Repository
public class RedissonTokenRepository implements TokenRepository {

    private static final Logger logger = LoggerFactory.getLogger(RedissonTokenRepository.class);

    @Autowired
    private RedisConfiguration redisConfiguration;

    @Autowired
    private RedissonClient redissonClient;

    @Override
    public void save(String jwt, String username, Date validationDate) {
        logger.info("** Redisson: save {}", username);

        RMap<String, Object> rMap = redissonClient.getMap(keyProvider(username));
        rMap.fastPut("validationDate", validationDate);
        rMap.fastPut("token", jwt);
        rMap.fastPut("Redisson", "Teste");
        rMap.expire(redisConfiguration.getTimeout(), redisConfiguration.getTimeUnit());
    }

    @Override
    public void refreshValidationDate(String username, Date validationDate) {
        logger.info("** Redisson: refresh {}", username);

        RMap<String, Object> rMap = redissonClient.getMap(keyProvider(username));
        rMap.fastPutAsync("validationDate", validationDate);
    }

    @Override
    public Date findValidationDate(String token, String username) {
        logger.info("** Redisson: findValidationDate {}", username);
        RMap<String, Object> rMap = redissonClient.getMap(keyProvider(username));
        Date validationDate = (Date) rMap.get("validationDate");
        logger.info("** Redisson: validationDate {}", validationDate);
        return validationDate;
    }

    @Override
    public String findToken(String username) {
        logger.info("** Redisson: findToken {}", username);
        RMap<String, Object> rMap = redissonClient.getMap(keyProvider(username));
        String token = (String) rMap.get("token");
        logger.info("** Redisson: token {}", token);
        return token;
    }

    @Override
    public void delete(String token, String username) {
        logger.info("** Redisson: delete {}", username);
        RMap<String, Object> rMap = redissonClient.getMap(keyProvider(username));
        rMap.delete();
    }

    private String keyProvider(String username) {
        return "jwt:" + username;
    }
}
