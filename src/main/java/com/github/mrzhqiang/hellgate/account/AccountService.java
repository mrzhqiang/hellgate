package com.github.mrzhqiang.hellgate.account;

import javax.validation.Valid;
import java.util.Optional;

public interface AccountService {

    Optional<Account> find(String username);

    Account register(String username, String password);

}
