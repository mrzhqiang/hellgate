package com.github.mrzhqiang.hellgateapi.config;

import com.github.mrzhqiang.hellgateapi.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author qiang.zhang
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/webjars/**", "/css/**", "/js/**", "/img/**", "/fontawesome-free/**");
    }

    @Configuration
    @ConditionalOnClass(UserDetailsService.class)
    public static class UserDetailsServiceConfiguration extends GlobalAuthenticationConfigurerAdapter {

        private final AccountService accountService;

        @Autowired
        public UserDetailsServiceConfiguration(AccountService accountService) {
            this.accountService = accountService;
        }

        @Override
        public void init(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userDetailsService());
        }

        @Bean
        public UserDetailsService userDetailsService() {
            return this.accountService;
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
            return PasswordEncoderFactories.createDelegatingPasswordEncoder();
        }

    }

    @Configuration
    @Order(1)
    public static class ApiSecurityConfiguration extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.csrf().disable()
                    .antMatcher("/api/**")
                    .authorizeRequests().anyRequest().authenticated()
                    .and().httpBasic();
        }
    }

    @Configuration
    @Order(2)
    public static class AdminSecurityConfiguration extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests(registry ->
                    registry.antMatchers("/", "/index", "/register").permitAll()
                            .anyRequest().authenticated())
                    .formLogin(configurer -> configurer.loginPage("/login").permitAll())
                    .logout().permitAll();
        }
    }
}
