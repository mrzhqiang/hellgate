package hellgate.config;

import com.github.mrzhqiang.kaptcha.autoconfigure.KaptchaAuthenticationConverter;
import com.github.mrzhqiang.kaptcha.autoconfigure.KaptchaProperties;
import static org.springframework.boot.autoconfigure.security.SecurityProperties.BASIC_AUTH_ORDER;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.session.data.redis.RedisIndexedSessionRepository;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;
import org.springframework.session.security.web.authentication.SpringSessionRememberMeServices;

/**
 * 安全配置。
 */
@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends GlobalAuthenticationConfigurerAdapter {

    /**
     * 密码编码器。
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    /**
     * API 安全适配器。
     */
    @Configuration
    @Order(BASIC_AUTH_ORDER - 10)
    static class ApiSecurityAdapter extends WebSecurityConfigurerAdapter {

        private final WebsiteProperties websiteProperties;

        ApiSecurityAdapter(WebsiteProperties websiteProperties) {
            this.websiteProperties = websiteProperties;
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.requestMatchers().antMatchers(websiteProperties.getApiPath())
                    .and().authorizeRequests().anyRequest().authenticated()
                    .and().httpBasic()
                    .and().csrf().disable();

            // todo jwt authentication
        }
    }

    /**
     * 网站安全适配器。
     */
    @EnableConfigurationProperties(WebsiteProperties.class)
    @Configuration
    static class WebsiteSecurityAdapter extends WebSecurityConfigurerAdapter {

        private final WebsiteProperties websiteProperties;
        private final AccountProperties accountProperties;
        private final KaptchaProperties kaptchaProperties;
        private final KaptchaAuthenticationConverter converter;
        private final UserDetailsService userDetailsService;
        private final RedisIndexedSessionRepository sessionRepository;

        public WebsiteSecurityAdapter(WebsiteProperties websiteProperties,
                                      AccountProperties accountProperties,
                                      KaptchaProperties kaptchaProperties,
                                      KaptchaAuthenticationConverter converter,
                                      UserDetailsService userDetailsService,
                                      RedisIndexedSessionRepository sessionRepository) {
            this.websiteProperties = websiteProperties;
            this.accountProperties = accountProperties;
            this.kaptchaProperties = kaptchaProperties;
            this.converter = converter;
            this.userDetailsService = userDetailsService;
            this.sessionRepository = sessionRepository;
        }

        @Bean
        public SessionRegistry sessionRegistry() {
            return new SpringSessionBackedSessionRegistry<>(sessionRepository);
        }

        @Override
        public void configure(WebSecurity web) {
            web.ignoring().antMatchers(websiteProperties.getIgnorePath());
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.addFilterAfter(getAuthenticationFilter(), AnonymousAuthenticationFilter.class)
                    .userDetailsService(userDetailsService)
                    .authorizeRequests()
                    .antMatchers(kaptchaProperties.getPath()).permitAll()
                    .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                    .antMatchers(websiteProperties.getPublicPath()).permitAll()
                    .anyRequest().authenticated()
                    .and().formLogin().loginPage(websiteProperties.getLoginPath()).permitAll()
                    .defaultSuccessUrl(websiteProperties.getDefaultSuccessUrl())
                    .and().logout().permitAll()
                    .and().sessionManagement(it -> it.maximumSessions(accountProperties.getMaxSession())
                            .sessionRegistry(sessionRegistry()));
            if (websiteProperties.getRememberMe()) {
                http.rememberMe(it -> it.rememberMeServices(rememberMeServices()));
            }
        }

        @Bean
        public SpringSessionRememberMeServices rememberMeServices() {
            SpringSessionRememberMeServices rememberMeServices = new SpringSessionRememberMeServices();
            rememberMeServices.setAlwaysRemember(websiteProperties.getRememberMe());
            return rememberMeServices;
        }

        private AuthenticationFilter getAuthenticationFilter() throws Exception {
            AuthenticationFilter filter = new AuthenticationFilter(authenticationManager(), converter);
            filter.setRequestMatcher(new AntPathRequestMatcher(websiteProperties.getRegisterPath(), HttpMethod.POST.name()));
            filter.setFailureHandler(new SimpleUrlAuthenticationFailureHandler(websiteProperties.getRegisterErrorPath()));
            return filter;
        }
    }
}
