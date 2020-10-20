package com.github.mrzhqiang.hellgate.website;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties("website")
public class WebsiteProperties {

    private String author;
    private String favicon;
    private String title;
    private String copyRight;
    private Boolean rememberMe;
    private String[] ignorePath;
}
