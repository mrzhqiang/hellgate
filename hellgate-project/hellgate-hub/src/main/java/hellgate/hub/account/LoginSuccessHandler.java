package hellgate.hub.account;

import com.google.common.base.Strings;
import hellgate.common.account.AccountService;
import hellgate.hub.config.HubSecurityProperties;
import okhttp3.HttpUrl;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;

@Component
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final HubSecurityProperties hubSecurityProperties;

    public LoginSuccessHandler(HubSecurityProperties hubSecurityProperties) {
        this.hubSecurityProperties = hubSecurityProperties;
        setAlwaysUseDefaultTargetUrl(true);
        setDefaultTargetUrl(hubSecurityProperties.getDefaultSuccessUrl());
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {
        String username = request.getParameter(HubAccountService.USERNAME_KEY);
        String password = request.getParameter(HubAccountService.PASSWORD_KEY);
        if (!Strings.isNullOrEmpty(username) && !Strings.isNullOrEmpty(password)) {
            String bookmarkPath = hubSecurityProperties.getBookmarkPath();
            String url = HttpUrl.get(bookmarkPath).newBuilder()
                    .addQueryParameter(AccountService.USERNAME_KEY, username)
                    .addQueryParameter(AccountService.PASSWORD_KEY, password)
                    .addQueryParameter("timestamp", String.valueOf(Instant.now().getEpochSecond()))
                    .toString();
            setDefaultTargetUrl(url);
        } else {
            setDefaultTargetUrl(hubSecurityProperties.getDefaultSuccessUrl());
        }
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
