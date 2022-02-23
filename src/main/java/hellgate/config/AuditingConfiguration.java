package hellgate.config;

import hellgate.common.Authentications;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * 审计配置。
 */
@EnableJpaAuditing
@Configuration
public class AuditingConfiguration {

    @Bean
    public AuditorAware<String> auditor() {
        return () -> Authentications.ofCurrent().flatMap(Authentications::findUsername);
    }
}
