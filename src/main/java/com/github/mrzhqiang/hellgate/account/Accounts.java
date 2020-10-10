package com.github.mrzhqiang.hellgate.account;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;

public enum Accounts {
    ;

    public static final String NOT_FOUND_USERNAME_FORMAT = "user [%s] count not be found";

    public static User toUser(Account account) {
        return new User(account.getUsername(), account.getPassword(), true, true,
                true, true, Collections.emptyList());
    }

    public static UsernameNotFoundException ofNotFound(String username) {
        return new UsernameNotFoundException(String.format(NOT_FOUND_USERNAME_FORMAT, username));
    }
}
