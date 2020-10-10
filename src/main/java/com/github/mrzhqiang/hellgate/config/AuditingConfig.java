package com.github.mrzhqiang.hellgate.config;

import com.github.mrzhqiang.hellgate.account.Authentications;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.userdetails.User;

@EnableJpaAuditing
@Configuration
public class AuditingConfig {

    @Bean
    public AuditorAware<User> sessionAuditor() {
        return Authentications::ofUser;
    }
}
