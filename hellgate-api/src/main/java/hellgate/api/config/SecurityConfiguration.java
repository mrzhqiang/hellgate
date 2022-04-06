package hellgate.api.config;

import com.github.mrzhqiang.kaptcha.autoconfigure.KaptchaAuthenticationConverter;
import com.github.mrzhqiang.kaptcha.autoconfigure.KaptchaProperties;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import hellgate.api.controller.account.LoginFailureHandler;
import hellgate.api.controller.rest.JwtProperties;
import hellgate.common.util.Joiners;
import static org.springframework.boot.autoconfigure.security.SecurityProperties.BASIC_AUTH_ORDER;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.session.security.web.authentication.SpringSessionRememberMeServices;

@EnableWebSecurity
@EnableConfigurationProperties(SecurityProperties.class)
@Configuration
public class SecurityConfiguration extends GlobalAuthenticationConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @EnableConfigurationProperties(JwtProperties.class)
    @Configuration
    @Order(BASIC_AUTH_ORDER - 10)
    static class ApiSecurityAdapter extends WebSecurityConfigurerAdapter {

        private final SecurityProperties properties;
        private final JwtProperties jwtProperties;

        ApiSecurityAdapter(SecurityProperties properties, JwtProperties jwtProperties) {
            this.properties = properties;
            this.jwtProperties = jwtProperties;
        }

        @Bean
        JwtDecoder jwtDecoder() {
            return NimbusJwtDecoder.withPublicKey(jwtProperties.getPublicKey()).build();
        }

        @Bean
        JwtEncoder jwtEncoder() {
            JWK jwk = new RSAKey.Builder(jwtProperties.getPublicKey())
                    .privateKey(jwtProperties.getPrivateKey())
                    .build();
            JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
            return new NimbusJwtEncoder(jwks);
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.requestMatchers().antMatchers(properties.getApiPath())
                    .and().authorizeRequests().anyRequest().authenticated()
                    .and().csrf((csrf) -> csrf.ignoringAntMatchers(properties.getApiTokenPath()))
                    .httpBasic(Customizer.withDefaults())
                    .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
                    .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .exceptionHandling((exceptions) -> exceptions
                            .authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint())
                            .accessDeniedHandler(new BearerTokenAccessDeniedHandler())
                    );
        }
    }

    @Configuration
    static class WebSecurityAdapter extends WebSecurityConfigurerAdapter {

        private final SecurityProperties securityProperties;
        private final SessionProperties sessionProperties;
        private final KaptchaProperties kaptchaProperties;
        private final KaptchaAuthenticationConverter converter;
        private final UserDetailsService userDetailsService;
        private final LoginFailureHandler failureHandler;

        public WebSecurityAdapter(SecurityProperties securityProperties,
                                  SessionProperties sessionProperties,
                                  KaptchaProperties kaptchaProperties,
                                  KaptchaAuthenticationConverter converter,
                                  UserDetailsService userDetailsService,
                                  LoginFailureHandler failureHandler) {
            this.securityProperties = securityProperties;
            this.sessionProperties = sessionProperties;
            this.kaptchaProperties = kaptchaProperties;
            this.converter = converter;
            this.userDetailsService = userDetailsService;
            this.failureHandler = failureHandler;
            this.failureHandler.setDefaultFailureUrl(
                    Joiners.QUERY.join(securityProperties.getLoginPath(), "error"));
        }

        @Override
        public void configure(WebSecurity web) {
            web.ignoring()
                    .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                    .antMatchers(securityProperties.getIgnorePath());
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.addFilterAfter(getAuthenticationFilter(), AnonymousAuthenticationFilter.class)
                    .userDetailsService(userDetailsService)
                    .authorizeRequests()
                    .antMatchers(kaptchaProperties.getPath()).permitAll()
                    .antMatchers(securityProperties.getLoginPath()).permitAll()
                    .antMatchers(securityProperties.getRegisterPath()).permitAll()
                    .antMatchers(securityProperties.getPublicPath()).permitAll()
                    //.requestMatchers(EndpointRequest.toAnyEndpoint()).hasRole(Roles.RAW_ADMIN)
                    .anyRequest().authenticated()
                    .and().formLogin().loginPage(securityProperties.getLoginPath()).permitAll()
                    .failureHandler(failureHandler)
                    .defaultSuccessUrl(securityProperties.getDefaultSuccessUrl())
                    .and().logout().permitAll()
                    .and().sessionManagement(it ->
                            it.maximumSessions(sessionProperties.getMaxSession())
                                    .expiredUrl(sessionProperties.getExpiredPath()));
            if (securityProperties.getRememberMe()) {
                http.rememberMe(it -> it.rememberMeServices(rememberMeServices()));
            }
        }

        @Bean
        public SpringSessionRememberMeServices rememberMeServices() {
            SpringSessionRememberMeServices rememberMeServices = new SpringSessionRememberMeServices();
            rememberMeServices.setAlwaysRemember(securityProperties.getRememberMe());
            return rememberMeServices;
        }

        private AuthenticationFilter getAuthenticationFilter() throws Exception {
            // 这里是 /register 接口的 post 匹配器，用来匹配验证码相关的回调
            AuthenticationFilter filter = new AuthenticationFilter(authenticationManager(), converter);
            filter.setRequestMatcher(new AntPathRequestMatcher(securityProperties.getRegisterPath(), HttpMethod.POST.name()));
            filter.setFailureHandler(new SimpleUrlAuthenticationFailureHandler(securityProperties.getRegisterErrorPath()));
            return filter;
        }
    }
}
