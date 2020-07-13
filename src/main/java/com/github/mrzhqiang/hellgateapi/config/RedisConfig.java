package com.github.mrzhqiang.hellgateapi.config;

import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Configuration;

/**
 * @author qiang.zhang
 */
@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {
    @Override
    public KeyGenerator keyGenerator() {
        return (target, method, params) -> {
            StringBuilder builder = new StringBuilder("hg:cache:");
            for (Object param : params) {
                builder.append(param.toString()).append(":");
            }
            return builder.toString();
        };
    }
}
