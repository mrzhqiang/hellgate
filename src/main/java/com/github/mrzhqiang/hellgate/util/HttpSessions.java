package com.github.mrzhqiang.hellgate.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.WebAttributes;
import org.springframework.ui.Model;

import javax.annotation.Nonnull;
import javax.servlet.http.HttpSession;
import java.util.Objects;
import java.util.Optional;

public enum HttpSessions {
    ;

    public static final String MESSAGE_KEY = "message";

    /**
     * 从 HttpSession 中提取异常类。
     */
    public static Optional<Exception> ofException(HttpSession session) {
        return Optional.ofNullable(session)
                .map(it -> it.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION))
                .map(Exception.class::cast);
    }

    /**
     * 从 HttpSession 中获取当前登录的账号信息。
     */
    @Nonnull
    public static Optional<String> ofUsername() {
        return Optional.ofNullable(Authentications.of())
                .filter(Authentications::isLogin)
                .map(Authentication::getPrincipal)
                .map(UserDetails.class::cast)
                .map(UserDetails::getUsername);
    }

    /**
     * 处理 HttpSession 中的异常，如果有，那就放到 Model 里面，从而传递到 Thymeleaf 中，可用于页面提示。
     */
    public static void handleException(HttpSession session, Model model) {
        if (Objects.isNull(model)) {
            return;
        }

        HttpSessions.ofException(session)
                .map(Throwable::getMessage)
                .ifPresent(message -> model.addAttribute(MESSAGE_KEY, message));
    }

    public static void logout(HttpSession session) {
        Optional.ofNullable(session).ifPresent(HttpSession::invalidate);
        Authentications.logout();
    }
}
