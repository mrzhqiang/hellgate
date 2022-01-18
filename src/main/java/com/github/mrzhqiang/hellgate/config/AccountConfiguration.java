package com.github.mrzhqiang.hellgate.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;


/**
 * 账户配置。
 */
@Configuration
@EnableConfigurationProperties(AccountProperties.class)
public class AccountConfiguration {
}
