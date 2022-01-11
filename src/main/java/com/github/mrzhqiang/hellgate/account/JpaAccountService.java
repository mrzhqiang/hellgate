package com.github.mrzhqiang.hellgate.account;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;

@Slf4j
@RequiredArgsConstructor
@Service
public class JpaAccountService implements AccountService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final MessageSourceAccessor accessor;

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(accessor.getMessage("account.not-found")));
    }

    @Override
    public boolean register(AccountForm form) {
        String username = form.getUsername();

        if (repository.findByUsername(username).isPresent()) {
            return false;
        }

        User user = new User();
        user.setUsername(username);
        String encodePassword = passwordEncoder.encode(form.getPassword());
        user.setPassword(encodePassword);
        repository.save(user);
        if (log.isDebugEnabled()) {
            log.debug("create account: {}", user);
        }
        return true;
    }

    @Override
    public void handleLoginFailed(Authentication authentication) {
        repository.findByUsername(authentication.getName())
                .filter(User::isAccountNonLocked)
                .ifPresent(this::handleFailed);
    }

    private void handleFailed(User user) {
        int failedCount = user.getFailedCount();
        // todo login failure configuration
        if (failedCount > 5) {
            user.setLocked(Instant.now().plus(Duration.ofMinutes(5)));
        } else {
            user.setFailedCount(failedCount + 1);
        }
        repository.save(user);
    }

    @Override
    public void handleLoginSuccessful(Authentication authentication) {
        repository.findByUsername(authentication.getName())
                .filter(it -> !it.isAccountNonLocked())
                .ifPresent(this::handleSuccessful);
    }

    private void handleSuccessful(User user) {
        user.setLocked(null);
        user.setFailedCount(0);
        repository.save(user);
    }
}
