package com.github.mrzhqiang.hellgate.account.impl;

import com.github.mrzhqiang.hellgate.account.Account;
import com.github.mrzhqiang.hellgate.account.AccountRepository;
import com.github.mrzhqiang.hellgate.account.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    public AccountServiceImpl(AccountRepository accountRepository,
                              PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<Account> find(String username) {
        return accountRepository.findByUsername(username);
    }

    @Override
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
