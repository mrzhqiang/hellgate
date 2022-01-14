package com.github.mrzhqiang.hellgate.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 账户存储库。
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    /**
     * 通过用户名找到对应的账户。
     *
     * @param username 用户名。
     * @return 可选的账户。
     */
    Optional<Account> findByUsername(String username);
}
