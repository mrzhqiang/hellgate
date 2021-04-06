package com.github.mrzhqiang.hellgate.account;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class JpaAccountService implements AccountService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Optional<Account> find(String username) {
        return accountRepository.findByUsername(username);
    }

    @Override
    public Account register(String username, String password) {
        Account account = new Account();
        account.setUsername(username);
        account.setPassword(passwordEncoder.encode(password));
        accountRepository.save(account);
        if (log.isDebugEnabled()) {
            log.debug("create account: {}", account);
        }
        return account;
    }
}
