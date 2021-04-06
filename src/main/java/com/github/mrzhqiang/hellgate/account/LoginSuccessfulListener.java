package com.github.mrzhqiang.hellgate.account;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;

@Component
@RequiredArgsConstructor
public class LoginSuccessfulListener implements ApplicationListener<AuthenticationSuccessEvent> {

    private final AccountRepository repository;

    @Override
    public void onApplicationEvent(@Nonnull AuthenticationSuccessEvent event) {
        Authentication authentication = event.getAuthentication();
        repository.findByUsername(authentication.getName())
                .filter(it -> !it.isAccountNonLocked())
                .ifPresent(account -> {
                    account.setLocked(null);
                    account.setFailedCount(0);
                    repository.save(account);
                });

    }
}
