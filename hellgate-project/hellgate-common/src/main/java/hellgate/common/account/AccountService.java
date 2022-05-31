package hellgate.common.account;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import static org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY;
import static org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY;

/**
 * 账号服务。
 */
public interface AccountService extends UserDetailsService, UserDetailsPasswordService {

    /**
     * 用户名参数名称。
     */
    String USERNAME_KEY = SPRING_SECURITY_FORM_USERNAME_KEY;
    /**
     * UID 参数名称。
     */
    String UID_KEY = "uid";
    /**
     * 密码参数名称。
     */
    String PASSWORD_KEY = SPRING_SECURITY_FORM_PASSWORD_KEY;

    /**
     * 通过用户名加载用户详情。
     *
     * @param usernameOrUid 用户名/uid，用户名必须以字母开头，uid 则是纯数字。
     * @return 用户详情，通常是 {@link org.springframework.security.core.userdetails.User} 类实例。
     * @throws UsernameNotFoundException 当无法通过用户名或 uid 找到账号时，抛出此异常，表示登录失败。
     */
    @Override
    UserDetails loadUserByUsername(String usernameOrUid) throws UsernameNotFoundException;

    /**
     * 通过用户详情找到账号。
     *
     * @param userDetails 用户详情，通常的实现是 {@link org.springframework.security.core.userdetails.User} 类。
     * @return 账号实例，同样也实现了 {@link UserDetails} 接口，但只是方便转换成 {@link org.springframework.security.core.userdetails.User} 类。
     * @throws RuntimeException 当无法通过用户详情找到账号时，抛出此异常，一般是数据库问题。
     */
    Account findByUserDetails(UserDetails userDetails);

    /**
     * 更新密码。
     * <p>
     * Spring Security 框架在认证账号时，如果发现密码强度低于当前的密码加密器，则自动调用此方法去更新密码。
     *
     * @param user        用户详情，由 loadUserByUsername 返回的实例。
     * @param newPassword 新的密码，从登录时提交的表单得到原始密码，再通过加密器进行编码后得到的新密码。
     * @return 新的用户详情。
     */
    @Override
    UserDetails updatePassword(UserDetails user, String newPassword);
}
