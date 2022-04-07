package hellgate.api.controller.account;

import hellgate.common.model.account.Account;
import hellgate.common.model.account.AccountRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;

@Component
public class LoginSuccessListener implements ApplicationListener<AuthenticationSuccessEvent> {

    private final AccountRepository repository;

    public LoginSuccessListener(AccountRepository repository) {
        this.repository = repository;
    }

    @Override
    public void onApplicationEvent(@Nonnull AuthenticationSuccessEvent event) {
        Authentication authentication = event.getAuthentication();
        handleLoginSuccess(authentication);
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
}
