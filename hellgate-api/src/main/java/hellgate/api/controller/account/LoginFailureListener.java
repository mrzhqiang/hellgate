package hellgate.api.controller.account;

import hellgate.api.config.SessionProperties;
import hellgate.common.model.account.Account;
import hellgate.common.model.account.AccountRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;
import java.time.Duration;
import java.time.Instant;

@Service
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

    /**
     * 登录失败处理。
     * <p>
     * 一般来说，我们对登录失败的重复尝试，有一定的容忍度，比如说 1 小时内最多不超过 5 次。
     * <p>
     * 但我们不可能使用定时器针对失败次数的时间区间进行检测，这样非常浪费资源。
     * <p>
     * 因此我们在每次进行失败处理之前，先检测上述时间区间，发现区间值内已超过 5 次则锁定账号。
     */
    @Transactional(rollbackFor = {Exception.class})
    public void handleLoginFailed(Authentication authentication) {
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
