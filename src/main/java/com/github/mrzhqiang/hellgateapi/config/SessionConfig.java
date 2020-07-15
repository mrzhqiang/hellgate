package com.github.mrzhqiang.hellgateapi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * @author qiang.zhang
 */
@Configuration
@EnableRedisHttpSession
public class SessionConfig {
}
