package com.github.mrzhqiang.hellgate.account;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.time.Duration;
import java.time.Instant;

@Component
@RequiredArgsConstructor
public class LoginFailureListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

    private final AccountRepository repository;
    // todo login failure configuration

    @Override
    public void onApplicationEvent(@Nonnull AuthenticationFailureBadCredentialsEvent event) {
        Authentication authentication = event.getAuthentication();
        repository.findByUsername(authentication.getName())
                .filter(Account::isAccountNonLocked)
                .ifPresent(account -> {
                    int failedCount = account.getFailedCount();
                    if (failedCount > 5) {
                        account.setLocked(Instant.now().plus(Duration.ofMinutes(5)));
                    } else {
                        account.setFailedCount(failedCount + 1);
                    }
                    repository.save(account);
                });
    }
}
