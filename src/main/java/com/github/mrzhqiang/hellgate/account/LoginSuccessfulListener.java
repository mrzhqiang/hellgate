package com.github.mrzhqiang.hellgate.account;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;

@RequiredArgsConstructor
@Component
public class LoginSuccessfulListener implements ApplicationListener<AuthenticationSuccessEvent> {

    private final AccountService accountService;

    @Override
    public void onApplicationEvent(@Nonnull AuthenticationSuccessEvent event) {
        Authentication authentication = event.getAuthentication();
        accountService.handleLoginSuccessful(authentication);
    }

}
