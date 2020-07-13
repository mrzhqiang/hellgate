package com.github.mrzhqiang.hellgateapi.repository;

import com.github.mrzhqiang.hellgateapi.model.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    Optional<Account> findByUsername(String username);
}
