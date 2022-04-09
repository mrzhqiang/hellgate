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

    private void handleLoginSuccess(Authentication authentication) {
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
