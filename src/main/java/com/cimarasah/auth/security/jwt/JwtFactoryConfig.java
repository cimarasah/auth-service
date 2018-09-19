package com.cimarasah.auth.security.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@RefreshScope
public class JwtFactoryConfig {

    @Value("${security.jwt.encrypted}")
    private Boolean encrypted;

    @Bean @Primary @RefreshScope
    public JwtFactory jwtFactory(JwtEncryptedTokenFactory jwtEncryptedTokenFactory,
                                 JwtTokenFactory jwtTokenFactory) {
        return encrypted ? jwtEncryptedTokenFactory : jwtTokenFactory;
    }
}
