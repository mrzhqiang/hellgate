package com.github.mrzhqiang.hellgate.config;

import com.github.mrzhqiang.hellgate.util.HttpSessions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@Configuration
public class AuditingConfig {

    @Bean
    public AuditorAware<String> sessionAuditor() {
        return HttpSessions::ofUsername;
    }

}
