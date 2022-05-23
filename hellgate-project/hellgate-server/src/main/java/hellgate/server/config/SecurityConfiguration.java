package hellgate.server.config;

import static org.springframework.boot.autoconfigure.security.SecurityProperties.BASIC_AUTH_ORDER;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;

@EnableWebSecurity
@EnableConfigurationProperties(SecurityProperties.class)
@Configuration
public class SecurityConfiguration extends GlobalAuthenticationConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Configuration
    @Order(BASIC_AUTH_ORDER - 10)
    static class ApiSecurityAdapter extends WebSecurityConfigurerAdapter {

        private final SecurityProperties properties;

        ApiSecurityAdapter(SecurityProperties properties) {
            this.properties = properties;
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.requestMatchers().antMatchers(properties.getApiPath())
                    .and().authorizeRequests().anyRequest().authenticated()
                    .and().csrf((csrf) -> csrf.ignoringAntMatchers(properties.getApiPath()))
                    .httpBasic(Customizer.withDefaults())
                    .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
                    .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .exceptionHandling((exceptions) -> exceptions
                            .authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint())
                            .accessDeniedHandler(new BearerTokenAccessDeniedHandler())
                    );
        }
    }
}
