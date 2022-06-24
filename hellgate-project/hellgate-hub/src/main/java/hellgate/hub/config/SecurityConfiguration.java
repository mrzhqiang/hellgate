package hellgate.hub.config;

import com.github.mrzhqiang.kaptcha.autoconfigure.KaptchaAuthenticationConverter;
import com.github.mrzhqiang.kaptcha.autoconfigure.KaptchaProperties;
import hellgate.hub.account.BookmarkAuthenticationConverter;
import hellgate.hub.account.LoginFailureHandler;
import hellgate.hub.account.LoginSuccessHandler;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableConfigurationProperties({SecurityProperties.class})
@EnableWebSecurity
public class SecurityConfiguration {

    private final SecurityProperties properties;
    private final KaptchaProperties kaptchaProperties;

    public SecurityConfiguration(SecurityProperties properties,
                                 KaptchaProperties kaptchaProperties) {
        this.properties = properties;
        this.kaptchaProperties = kaptchaProperties;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer ignoring() {
        return web -> web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                .antMatchers(properties.getIgnorePath());
    }

    @Bean
    public SecurityFilterChain webFilterChain(HttpSecurity http,
                                              KaptchaAuthenticationConverter kaptchaConverter,
                                              BookmarkAuthenticationConverter bookmarkConverter,
                                              AuthenticationConfiguration configuration,
                                              LoginFailureHandler failureHandler,
                                              LoginSuccessHandler successHandler) throws Exception {
        AuthenticationManager manager = configuration.getAuthenticationManager();

        AuthenticationFilter kaptchaFilter = new AuthenticationFilter(manager, kaptchaConverter);
        kaptchaFilter.setRequestMatcher(new AntPathRequestMatcher(properties.getRegisterPath(), HttpMethod.POST.name()));
        kaptchaFilter.setFailureHandler(new SimpleUrlAuthenticationFailureHandler(properties.getRegisterFailedPath()));
        http.addFilterAfter(kaptchaFilter, AnonymousAuthenticationFilter.class);

        AuthenticationFilter bookmarkFilter = new AuthenticationFilter(manager, bookmarkConverter);
        bookmarkFilter.setRequestMatcher(new AntPathRequestMatcher(properties.getBookmarkPath(), HttpMethod.GET.name()));
        bookmarkFilter.setFailureHandler(new SimpleUrlAuthenticationFailureHandler(properties.getLoginPath()));
        // 认证成功不做任何处理，从而调用后续 filter 链，抵达书签页面
        bookmarkFilter.setSuccessHandler((request, response, authentication) -> {
        });
        http.addFilterAfter(bookmarkFilter, UsernamePasswordAuthenticationFilter.class);

        http.authorizeRequests(registry -> registry
                        .antMatchers(kaptchaProperties.getPath()).permitAll()
                        .antMatchers(properties.getPublicPath()).permitAll()
                        .antMatchers(properties.getLoginPath()).permitAll()
                        .antMatchers(properties.getRegisterPath()).permitAll()
                        .anyRequest().authenticated())
                .formLogin(configurer -> configurer
                        .loginPage(properties.getLoginPath())
                        .failureHandler(failureHandler)
                        .successHandler(successHandler).permitAll())
                .csrf().disable()
                .logout().permitAll();
        return http.build();
    }
}
