package com.github.mrzhqiang.hellgate.account;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    public AccountService(AccountRepository accountRepository,
                          PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<Account> find(String username) {
        return accountRepository.findByUsername(username);
    }

    public Account create(String username, String rawPassword) {
        Account account = new Account();
        account.setUsername(username);
        account.setPassword(passwordEncoder.encode(rawPassword));
        accountRepository.save(account);
        if (log.isDebugEnabled()) {
            log.debug("create account: {}", account);
        }
        return account;
    }
}
