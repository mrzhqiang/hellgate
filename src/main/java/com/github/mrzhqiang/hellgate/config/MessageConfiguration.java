package com.github.mrzhqiang.hellgate.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.MessageSourceAccessor;

@Configuration
@RequiredArgsConstructor
public class MessageConfiguration {

    private final MessageSource messageSource;

    @Bean
    public MessageSourceAccessor accessor() {
        return new MessageSourceAccessor(messageSource);
    }

}
