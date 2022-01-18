package com.github.mrzhqiang.hellgate.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * 会话配置。
 */
@EnableRedisHttpSession
@Configuration
public class SessionConfiguration {
}
