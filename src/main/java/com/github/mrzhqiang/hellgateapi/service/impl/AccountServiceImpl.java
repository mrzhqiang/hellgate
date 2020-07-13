package com.github.mrzhqiang.hellgateapi.service.impl;

import com.github.mrzhqiang.hellgateapi.repository.AccountRepository;
import com.github.mrzhqiang.hellgateapi.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return accountRepository.findByUsername(username)
                .map(account -> User.withUserDetails(account).build())
                .orElseThrow(() -> new UsernameNotFoundException("can not found username " + username));
    }
}
