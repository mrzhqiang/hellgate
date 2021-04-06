package com.github.mrzhqiang.hellgate.util;

import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public enum Authentications {
    ;

    public static final String ADMIN = "ROLE_ADMIN";
    public static final String USER = "ROLE_USER";

    private static final AuthenticationTrustResolver TRUST_RESOLVER = new AuthenticationTrustResolverImpl();

    public static Authentication of() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static boolean isLogin(Authentication authentication) {
        return Optional.ofNullable(authentication)
                .filter(Authentication::isAuthenticated)
                .filter(it -> !TRUST_RESOLVER.isAnonymous(it))
                .isPresent();
    }

    public static void logout() {
        SecurityContextHolder.getContext().setAuthentication(null);
        SecurityContextHolder.clearContext();
    }

    public static boolean isAdmin() {
        Authentication authentication = of();
        return isLogin(authentication) && new SecurityExpressionRoot(authentication) {
        }.hasRole(ADMIN);
    }

}
