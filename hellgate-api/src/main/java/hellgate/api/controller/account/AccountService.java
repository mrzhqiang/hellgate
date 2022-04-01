package hellgate.api.controller.account;

import com.google.common.base.MoreObjects;
import hellgate.api.config.SessionProperties;
import hellgate.common.domain.account.Account;
import hellgate.common.domain.account.AccountForm;
import hellgate.common.domain.account.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Slf4j
@Service
public class AccountService implements UserDetailsService {

    private static final int DEF_MAX_LOGIN_FAILED = 5;
    private static final int DEF_LOCKED_DURATION = 5;

    private final AccountRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final SessionProperties properties;

    public AccountService(AccountRepository repository,
                          PasswordEncoder passwordEncoder,
                          SessionProperties properties) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.properties = properties;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUsername(username)
                .map(User::withUserDetails)
                .map(User.UserBuilder::build)
                .orElseThrow(() -> new UsernameNotFoundException("AccountService.usernameNotFound"));
    }

    /**
     * 注册账户。
     *
     * @param form 注册时填写的表单。
     * @return true 表示注册成功；false 表示注册失败。基本上属于用户名已存在的问题。但我们一般不返回类似的提示信息，以免被恶意攻击。
     */
    public boolean register(AccountForm form) {
        String username = form.getUsername();
        if (repository.findByUsername(username).isPresent()) {
            return false;
        }

        Account account = new Account();
        account.setUsername(username);
        String encodePassword = passwordEncoder.encode(form.getPassword());
        account.setPassword(encodePassword);
        repository.save(account);
        if (log.isDebugEnabled()) {
            log.debug("create account: {} for register", account);
        }
        return true;
    }

    /**
     * 登录失败处理。
     * <p>
     * 一般来说，我们对登录失败的重复尝试，有一定的容忍度，比如说 1 小时内最多不超过 5 次。
     * <p>
     * 但我们不可能使用定时器针对失败次数的时间区间进行检测，这样非常浪费资源。
     * <p>
     * 因此我们在每次进行失败处理之前，先检测上述时间区间，发现区间值内已超过 5 次则锁定账号。
     *
     * @param authentication 认证实例。
     */
    public void handleLoginFailed(Authentication authentication) {
        repository.findByUsername(authentication.getName())
                .filter(Account::isAccountNonLocked)
                .map(this::computeFailedCount)
                .ifPresent(repository::save);
    }

    private Account computeFailedCount(Account account) {
        LocalDateTime firstFailed = account.getFirstFailed();
        LocalDateTime now = LocalDateTime.now();
        Duration firstFailedDuration = properties.getFirstFailedDuration();
        if (firstFailed == null || firstFailed.plus(firstFailedDuration).isBefore(now)) {
            account.setFirstFailed(now);
            account.setFailedCount(1);
            return account;
        }

        int failedCount = account.getFailedCount();
        if (failedCount >= Math.max(properties.getMaxLoginFailed(), DEF_MAX_LOGIN_FAILED)) {
            Duration duration = MoreObjects.firstNonNull(properties.getLockedDuration(), Duration.ofMinutes(DEF_LOCKED_DURATION));
            account.setLocked(now.plus(duration));
        } else {
            account.setFailedCount(failedCount + 1);
        }
        return account;
    }

    /**
     * 登录成功处理。
     * <p>
     * 如果账号未锁定，则设置账号为正常状态；如果账号已锁定，需要等待锁定时间过期。
     *
     * @param authentication 认证实例。
     */
    public void handleLoginSuccess(Authentication authentication) {
        repository.findByUsername(authentication.getName())
                .filter(it -> !it.isAccountNonLocked())
                .map(this::resetNormalAccount)
                .ifPresent(repository::save);
    }

    private Account resetNormalAccount(Account account) {
        account.setFailedCount(0);
        account.setLocked(null);
        return account;
    }

    /**
     * 注销成功处理。
     * <p>
     * 暂时不做任何处理。
     *
     * @param authentication 认证实例。
     */
    public void handleLogoutSuccess(Authentication authentication) {
        // 目前没有处理逻辑，等待丰富
    }
}
