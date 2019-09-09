package com.github.mrzhqiang.hellgateapi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * @author qiang.zhang
 */
@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 20 * 60)
public class SessionConfig {
}
