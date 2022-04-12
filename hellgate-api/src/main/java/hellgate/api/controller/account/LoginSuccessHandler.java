package hellgate.api.controller.account;

import com.google.common.base.Strings;
import hellgate.api.config.SecurityProperties;
import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;

@Setter
@Component
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final SecurityProperties properties;

    public LoginSuccessHandler(SecurityProperties properties) {
        this.properties = properties;
        setAlwaysUseDefaultTargetUrl(true);
        setDefaultTargetUrl(properties.getDefaultSuccessUrl());
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {
        String username = request.getParameter(AccountService.USERNAME_KEY);
        String password = request.getParameter(AccountService.PASSWORD_KEY);
        if (!Strings.isNullOrEmpty(username) && !Strings.isNullOrEmpty(password)) {
            String url = Strings.lenientFormat(properties.getBookmarkTemplate(),
                    username, password, Instant.now().getEpochSecond());
            setDefaultTargetUrl(url);
        } else {
            setDefaultTargetUrl(properties.getDefaultSuccessUrl());
        }
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
