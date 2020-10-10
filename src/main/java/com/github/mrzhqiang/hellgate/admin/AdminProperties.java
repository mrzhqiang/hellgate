package com.github.mrzhqiang.hellgate.admin;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.UUID;

@Getter
@Setter
@ConfigurationProperties("admin")
public class AdminProperties {
    public static final String DEFAULT_ADMIN_USERNAME = "root";

    private String username = DEFAULT_ADMIN_USERNAME;
    private String password = UUID.randomUUID().toString();
}
