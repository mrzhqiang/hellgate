package hellgate.common.audit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

/**
 * 审计配置。
 * <p>
 * 参考：<a href="https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#auditing">Spring 官方审计文档</a>
 */
@EnableJpaAuditing
@Configuration
public class AuditingConfiguration {

    @Bean
    public AuditorAware<String> auditor() {
        return () -> Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .filter(Authentication::isAuthenticated)
                .map(Authentication::getPrincipal)
                .map(UserDetails.class::cast)
                .map(UserDetails::getUsername);
    }
}
