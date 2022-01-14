package com.github.mrzhqiang.hellgate.common.annotation;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 当前用户。
 *
 * <p>这是一个可以从当前会话中得到用户实例的注解。
 *
 * <p>主要利用：{@link Authentication#getPrincipal()} 来获得实例，因此类型必须保持一致，否则将得到 null 值。
 */
@Retention(RetentionPolicy.RUNTIME)
@AuthenticationPrincipal
public @interface CurrentUser {
}
