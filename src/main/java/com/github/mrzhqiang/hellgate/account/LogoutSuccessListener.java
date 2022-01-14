package com.github.mrzhqiang.hellgate.account;

import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.LogoutSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;

@Component
public class LogoutSuccessListener implements ApplicationListener<LogoutSuccessEvent> {

    private final AccountService accountService;

    public LogoutSuccessListener(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public void onApplicationEvent(@Nonnull LogoutSuccessEvent event) {
        Authentication authentication = event.getAuthentication();
        accountService.handleLogoutSuccess(authentication);
    }
}
