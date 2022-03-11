package hellgate.controller.account;

import com.google.common.base.MoreObjects;
import hellgate.common.session.Sessions;
import hellgate.config.AccountProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.time.Duration;
import java.time.LocalDateTime;

/**
 * 账户服务。
 */
@Slf4j
@Service
public class AccountService implements UserDetailsService {

    private final AccountRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final AccountProperties properties;

    public AccountService(AccountRepository repository,
                          PasswordEncoder passwordEncoder,
                          AccountProperties properties) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.properties = properties;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUsername(username)
                .map(User::withUserDetails)
                .map(User.UserBuilder::build)
                .orElseThrow(() -> new UsernameNotFoundException("message.login.account.not-found"));
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
     * 处理登录失败的情况。
     * <p>
     * 一般来说，我们对登录失败的重复尝试，有一定的容忍度，比如说 1 小时内最多不超过 5 次。
     * <p>
     * 但我们不可能使用定时器针对失败次数的时间区间进行检测，这样非常浪费资源。
     * <p>
     * 因此我们在每次进行失败处理之前，先检测上述时间区间，发现区间值
     *
     * @param authentication 失败的验证实例。
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
        HttpSession httpSession = Sessions.ofCurrent();
        if (firstFailed == null || firstFailed.plus(firstFailedDuration).isBefore(now)) {
            account.setFirstFailed(now);
            account.setFailedCount(1);
            return account;
        }

        int failedCount = account.getFailedCount();
        if (failedCount >= Math.max(properties.getMaxLoginFailed(), 5)) {
            Duration duration = MoreObjects.firstNonNull(properties.getLockedDuration(), Duration.ofMinutes(5));
            account.setLocked(now.plus(duration));
        } else {
            account.setFailedCount(failedCount + 1);
        }
        return account;
    }

    public void handleLoginSuccess(Authentication authentication) {
        repository.findByUsername(authentication.getName())
                .filter(it -> !it.isAccountNonLocked())
                .map(this::resetFailedAndLocked)
                .ifPresent(repository::save);
    }

    private Account resetFailedAndLocked(Account account) {
        account.setFailedCount(0);
        account.setLocked(null);
        return account;
    }

    public void handleLogoutSuccess(Authentication authentication) {
        // 目前没有处理逻辑，等待丰富
    }
}
