package hellgate.common.config;

import hellgate.common.util.Authentications;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * 审计配置。
 * <p>
 * 参考：https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#auditing
 */
@EnableJpaAuditing
@Configuration
public class AuditingConfiguration {

    @Bean
    public AuditorAware<String> auditor() {
        return () -> Authentications.ofLogin().flatMap(Authentications::findUsername);
    }
}
