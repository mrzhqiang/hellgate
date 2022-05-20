package hellgate.core.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

@EnableConfigurationProperties(JwtProperties.class)
@Configuration
public class JwtConfiguration {

    private final JwtProperties properties;

    public JwtConfiguration(JwtProperties properties) {
        this.properties = properties;
    }

    @Bean
    JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(properties.getPublicKey()).build();
    }
}
