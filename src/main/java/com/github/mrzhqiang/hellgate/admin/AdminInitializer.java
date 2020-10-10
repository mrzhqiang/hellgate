package com.github.mrzhqiang.hellgate.admin;

import com.github.mrzhqiang.hellgate.account.Account;
import com.github.mrzhqiang.hellgate.account.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Slf4j
@EnableConfigurationProperties(AdminProperties.class)
@Configuration
public class AdminInitializer implements CommandLineRunner {

    private final AdminProperties adminProperties;
    private final AccountService accountService;

    public AdminInitializer(AdminProperties adminProperties, AccountService accountService) {
        this.adminProperties = adminProperties;
        this.accountService = accountService;
    }

    @Override
    public void run(String... args) {
        log.debug("command line runner run: {}", Arrays.toString(args));
        String username = adminProperties.getUsername();
        if (accountService.find(username).isPresent()) {
            log.info("admin account is present.");
            return;
        }
        Account account = accountService.create(username, adminProperties.getPassword());
        log.info("init admin account: {}", account);
    }
}
