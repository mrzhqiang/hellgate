package com.github.mrzhqiang.hellgate.common;

import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

/**
 * 身份验证工具。
 */
public final class Authentications {
    private Authentications() {
        // no instances
    }

    private static final AuthenticationTrustResolver TRUST_RESOLVER =
            new AuthenticationTrustResolverImpl();

    /**
     * 得到当前身份验证实例。
     *
     * <p>注意，如果没有 Authentication 实例，或者 Authentication 没有通过验证，或者 Authentication 是一个匿名实例，则返回不存在。
     *
     * @return 可选的实例，表示有可能返回不存在，但永远不会是 null 值。
     */
    public static Optional<Authentication> current() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return Optional.ofNullable(authentication)
                .filter(Authentication::isAuthenticated)
                .filter(it -> !TRUST_RESOLVER.isAnonymous(it));
    }
}
