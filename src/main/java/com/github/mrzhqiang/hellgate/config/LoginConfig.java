package com.github.mrzhqiang.hellgate.config;

import com.github.mrzhqiang.hellgate.account.AccountService;
import com.github.mrzhqiang.hellgate.account.Accounts;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class LoginConfig extends GlobalAuthenticationConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(AccountService accountService) {
        return username -> accountService.find(username)
                .map(Accounts::toUser)
                .orElseThrow(() -> Accounts.ofNotFound(username));
    }
}
