package hellgate.controller.account;

import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;

@Component
public class LoginSuccessListener implements ApplicationListener<AuthenticationSuccessEvent> {

    private final AccountService accountService;

    public LoginSuccessListener(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public void onApplicationEvent(@Nonnull AuthenticationSuccessEvent event) {
        Authentication authentication = event.getAuthentication();
        accountService.handleLoginSuccess(authentication);
    }
}
