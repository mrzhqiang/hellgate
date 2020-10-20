package com.github.mrzhqiang.hellgate.account;

import com.github.mrzhqiang.hellgate.common.Constant;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.WebAttributes;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;
import java.util.Optional;

/**
 * 认证辅助类。
 */
public enum Authentications {
    ;

    /**
     * 检测是否为登录用户。
     */
    public static boolean isUser(Authentication authentication) {
        return !isAnonymous(authentication);
    }

    /**
     * 检测是否为匿名用户。
     */
    public static boolean isAnonymous(Authentication authentication) {
        return authentication != null && Constant.ANONYMOUS_USER.equals(authentication.getName());
    }

    /**
     * 从 HttpSession 中提取异常。
     */
    public static Optional<Exception> ofException(HttpSession session) {
        return Optional.ofNullable(session)
                .map(s -> s.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION))
                .map(Exception.class::cast);
    }

    @NonNull
    public static Optional<User> ofUser() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .filter(Authentication::isAuthenticated)
                .filter(Authentications::isUser)
                .map(Authentication::getPrincipal)
                .map(User.class::cast);
    }

    public static void checkMessage(Model model, HttpSession session) {
        Optional.of(session)
                .flatMap(Authentications::ofException)
                .map(Throwable::getMessage)
                .ifPresent(message -> model.addAttribute("message", message));
    }
}
