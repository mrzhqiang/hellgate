package hellgate.api.controller.account;

import hellgate.api.config.SessionProperties;
import hellgate.common.model.account.Account;
import hellgate.common.model.account.AccountRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.time.Duration;
import java.time.Instant;

@Component
public class LoginFailureListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

    private final SessionProperties properties;
    private final AccountRepository repository;

    public LoginFailureListener(SessionProperties properties, AccountRepository repository) {
        this.properties = properties;
        this.repository = repository;
    }

    @Override
    public void onApplicationEvent(@Nonnull AuthenticationFailureBadCredentialsEvent event) {
        Authentication authentication = event.getAuthentication();
        handleLoginFailed(authentication);
    }

    private void handleLoginFailed(Authentication authentication) {
        repository.findByUsername(authentication.getName())
                .filter(Account::isAccountNonLocked)
                .map(this::computeFailedCount)
                .ifPresent(repository::save);
    }

    private Account computeFailedCount(Account account) {
        Instant firstFailed = account.getFirstFailed();
        Instant now = Instant.now();
        Duration firstFailedDuration = properties.getFirstFailedDuration();
        if (firstFailed == null || firstFailed.plus(firstFailedDuration).isBefore(now)) {
            account.setFirstFailed(now);
            account.setFailedCount(1);
            return account;
        }

        int hasFailedCount = account.getFailedCount() + 1;
        account.setFailedCount(hasFailedCount);
        if (hasFailedCount >= properties.getMaxLoginFailed()) {
            Duration duration = properties.getLockedDuration();
            account.setLocked(now.plus(duration));
        }
        return account;
    }
}
