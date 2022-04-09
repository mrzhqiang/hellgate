package hellgate.api.controller.account;

import com.google.common.base.Strings;
import hellgate.common.util.Authentications;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationConverter;
import static org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY;
import static org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class BookmarkConverter implements AuthenticationConverter {

    @Override
    public Authentication convert(HttpServletRequest request) {
        String username = request.getParameter(SPRING_SECURITY_FORM_USERNAME_KEY);
        String password = request.getParameter(SPRING_SECURITY_FORM_PASSWORD_KEY);
        if (!Strings.isNullOrEmpty(username) && !Strings.isNullOrEmpty(password)) {
            if (!Authentications.currentUsername().equals(username)) {
                // 返回用户名和密码的认证 Token 表示需要进行认证，如果认证通过，则切换到对应账号
                return new UsernamePasswordAuthenticationToken(username, password);
            }
        }
        // 如果已经登录，则不需要更新会话，返回 null 将会调用后续的 Filter 实例
        return null;
    }
}
