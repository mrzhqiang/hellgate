package hellgate.hub.config;

import com.github.mrzhqiang.kaptcha.autoconfigure.KaptchaAuthenticationConverter;
import com.github.mrzhqiang.kaptcha.autoconfigure.KaptchaProperties;
import hellgate.hub.account.LoginFailureHandler;
import hellgate.hub.account.LoginSuccessHandler;
import hellgate.hub.bookmark.BookmarkConverter;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@EnableConfigurationProperties({SecurityProperties.class, SessionProperties.class})
@Configuration
public class SecurityConfiguration extends GlobalAuthenticationConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Configuration
    static class WebSecurityAdapter extends WebSecurityConfigurerAdapter {

        private final SecurityProperties securityProperties;
        private final SessionProperties sessionProperties;
        private final KaptchaProperties kaptchaProperties;
        private final KaptchaAuthenticationConverter converter;
        private final BookmarkConverter bookmarkConverter;
        private final UserDetailsService userDetailsService;
        private final LoginFailureHandler failureHandler;
        private final LoginSuccessHandler successHandler;

        public WebSecurityAdapter(SecurityProperties securityProperties,
                                  SessionProperties sessionProperties,
                                  KaptchaProperties kaptchaProperties,
                                  KaptchaAuthenticationConverter converter,
                                  BookmarkConverter bookmarkConverter,
                                  UserDetailsService userDetailsService,
                                  LoginFailureHandler failureHandler,
                                  LoginSuccessHandler successHandler) {
            this.securityProperties = securityProperties;
            this.sessionProperties = sessionProperties;
            this.kaptchaProperties = kaptchaProperties;
            this.converter = converter;
            this.bookmarkConverter = bookmarkConverter;
            this.userDetailsService = userDetailsService;
            // 自定义失败处理器，我们需要固定失败的重定向地址
            failureHandler.setDefaultFailureUrl(securityProperties.getLoginPath() + SecurityProperties.ERROR_SUFFIX);
            this.failureHandler = failureHandler;
            this.successHandler = successHandler;
        }

        @Override
        public void configure(WebSecurity web) {
            web.ignoring()
                    .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                    .antMatchers(securityProperties.getIgnorePath());
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.addFilterAfter(getKaptchaFilter(), AnonymousAuthenticationFilter.class)
                    .addFilterAfter(getBookmarkFilter(), AnonymousAuthenticationFilter.class)
                    .userDetailsService(userDetailsService)
                    .authorizeRequests()
                    .antMatchers(kaptchaProperties.getPath()).permitAll()
                    .antMatchers(securityProperties.getLoginPath()).permitAll()
                    .antMatchers(securityProperties.getRegisterPath()).permitAll()
                    .antMatchers(securityProperties.getPublicPath()).permitAll()
                    .anyRequest().authenticated()
                    .and().formLogin().loginPage(securityProperties.getLoginPath())
                    .failureHandler(failureHandler).successHandler(successHandler).permitAll()
                    .and().logout().permitAll();
            if (securityProperties.getRememberMe()) {
                http.rememberMe().alwaysRemember(true)
                        .tokenValiditySeconds((int) sessionProperties.getCookieTimeout().getSeconds());
            }
        }

        private AuthenticationFilter getKaptchaFilter() throws Exception {
            // 这里是 /register 接口的 post 匹配器，用来匹配验证码相关的回调
            AuthenticationFilter filter = new AuthenticationFilter(authenticationManager(), converter);
            filter.setRequestMatcher(new AntPathRequestMatcher(securityProperties.getRegisterPath(), HttpMethod.POST.name()));
            filter.setFailureHandler(new SimpleUrlAuthenticationFailureHandler(securityProperties.getRegisterErrorPath()));
            return filter;
        }

        private AuthenticationFilter getBookmarkFilter() throws Exception {
            // 书签过滤器，用来做书签自动登录功能
            AuthenticationFilter filter = new AuthenticationFilter(authenticationManager(), bookmarkConverter);
            filter.setRequestMatcher(new AntPathRequestMatcher(securityProperties.getBookmarkPath(), HttpMethod.GET.name()));
            filter.setFailureHandler(new SimpleUrlAuthenticationFailureHandler(securityProperties.getLoginPath()));
            filter.setSuccessHandler((request, response, authentication) -> {
                // 不进行任何操作，这样就会继续执行过滤器链，从而抵达书签页面
            });
            return filter;
        }
    }
}
