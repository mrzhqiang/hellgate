package com.github.mrzhqiang.hellgate.config;

import com.github.mrzhqiang.hellgate.account.Account;
import com.github.mrzhqiang.hellgate.common.Authentications;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;


/**
 * 暗黑之门配置。
 */
@EnableJpaAuditing
@EnableRedisHttpSession
@Configuration
@EnableConfigurationProperties(HellGateProperties.class)
public class HellGateConfiguration {

    /**
     * 消息源访问。
     *
     * @param messageSource 消息源。
     * @return 消息源访问实例。主要用于国际化消息。
     */
    @Bean
    public MessageSourceAccessor accessor(MessageSource messageSource) {
        return new MessageSourceAccessor(messageSource);
    }

    @Bean
    public AuditorAware<String> auditor() {
        return () -> Authentications.current()
                .map(Authentication::getPrincipal)
                .map(Account.class::cast)
                .map(Account::getUsername);
    }
}
