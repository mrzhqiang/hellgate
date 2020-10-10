package com.github.mrzhqiang.hellgate.account;

import java.util.Optional;

public interface AccountService {

    Optional<Account> find(String username);

    Account create(String username, String rawPassword);
}
