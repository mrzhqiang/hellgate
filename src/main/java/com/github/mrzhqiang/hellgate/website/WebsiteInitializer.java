package com.github.mrzhqiang.hellgate.website;

import com.github.mrzhqiang.kaptcha.autoconfigure.KaptchaProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

@Slf4j
@ConditionalOnClass(ThymeleafViewResolver.class)
@Configuration
public class WebsiteInitializer implements CommandLineRunner {

    private static final String WEBSITE_KEY = "website";
    private static final String KAPTCHA_KEY = "kaptcha";

    private final WebsiteProperties websiteProperties;
    private final KaptchaProperties kaptchaProperties;
    private final ThymeleafViewResolver resolver;

    public WebsiteInitializer(WebsiteProperties websiteProperties, KaptchaProperties kaptchaProperties,
                              ThymeleafViewResolver resolver) {
        this.websiteProperties = websiteProperties;
        this.kaptchaProperties = kaptchaProperties;
        this.resolver = resolver;
    }

    @Override
    public void run(String... args) {
        log.info("add website properties to thymeleaf view resolver static variable.");
        resolver.addStaticVariable(WEBSITE_KEY, websiteProperties);
        log.info("add kaptcha properties to thymeleaf view resolver static variable.");
        resolver.addStaticVariable(KAPTCHA_KEY, kaptchaProperties);
    }
}
