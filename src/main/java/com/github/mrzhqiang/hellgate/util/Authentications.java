package com.github.mrzhqiang.hellgate.util;

import com.github.mrzhqiang.hellgate.account.User;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.WebAttributes;
import org.springframework.ui.Model;

import javax.annotation.Nonnull;
import javax.servlet.http.HttpSession;
import java.util.Objects;
import java.util.Optional;

public enum Authentications {
    ;

    private static final String EXCEPTION_MESSAGE = "message";

    private static final AuthenticationTrustResolver TRUST_RESOLVER = new AuthenticationTrustResolverImpl();

    public static Optional<Authentication> current() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return Optional.ofNullable(authentication)
                .filter(Authentication::isAuthenticated)
                .filter(it -> !TRUST_RESOLVER.isAnonymous(it));
    }

    /**
     * @deprecated 请使用 @Principal 注解返回 User
     */
    @Nonnull
    public static Optional<User> currentUser() {
        return current().map(Authentication::getPrincipal).map(User.class::cast);
    }

    public static void handleException(HttpSession session, Model model) {
        Optional.ofNullable(session)
                .filter(it -> Objects.nonNull(model))
                .map(it -> it.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION))
                .map(Exception.class::cast)
                .map(Throwable::getMessage)
                .ifPresent(message -> model.addAttribute(EXCEPTION_MESSAGE, message));
    }

    @SuppressWarnings("unused")
    public static void logout(HttpSession session) {
        Optional.ofNullable(session).ifPresent(HttpSession::invalidate);
        SecurityContextHolder.clearContext();
    }
}
