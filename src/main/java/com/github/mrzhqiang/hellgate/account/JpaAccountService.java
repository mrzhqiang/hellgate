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
    public Account register(AccountForm form) {
        Account account = new Account();
        account.setUsername(form.getUsername());
        account.setPassword(passwordEncoder.encode(form.getPassword()));
        accountRepository.save(account);
        if (log.isDebugEnabled()) {
            log.debug("create account: {}", account);
        }
        return account;
    }
}
