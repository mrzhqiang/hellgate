package com.github.mrzhqiang.hellgate.account;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface AccountService extends UserDetailsService {

    @Override
    User loadUserByUsername(String username) throws UsernameNotFoundException;

    boolean register(AccountForm form);

    void handleLoginFailed(Authentication authentication);

    void handleLoginSuccessful(Authentication authentication);

}
