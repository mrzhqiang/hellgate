package hellgate.common.account;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.method.annotation.AuthenticationPrincipalArgumentResolver;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 当前用户。
 * <p>
 * 这是一个可以从当前会话中得到 {@link UserDetails} 的注解。
 * <p>
 * 主要利用：{@link Authentication#getPrincipal()} 来获得实例。
 * <p>
 * 类型必须与 {@link UserDetails} 接口的实现类保持一致，否则将得到 Null 值。
 *
 * @see AuthenticationPrincipalArgumentResolver
 */
@Retention(RetentionPolicy.RUNTIME)
@AuthenticationPrincipal
public @interface CurrentUser {
}
