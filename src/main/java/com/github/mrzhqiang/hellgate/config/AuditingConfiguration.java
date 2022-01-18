package com.github.mrzhqiang.hellgate.config;

import com.github.mrzhqiang.hellgate.common.Authentications;
import com.github.mrzhqiang.hellgate.controller.account.Account;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;

/**
 * 审计配置。
 */
@EnableJpaAuditing
@Configuration
public class AuditingConfiguration {

    @Bean
    public AuditorAware<String> auditor() {
        return () -> Authentications.current()
                .map(Authentication::getPrincipal)
                .map(Account.class::cast)
                .map(Account::getUsername);
    }
}
