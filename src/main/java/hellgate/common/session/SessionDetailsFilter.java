package hellgate.common.session;

import com.google.common.net.HttpHeaders;
import hellgate.common.third.IpLocation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Nonnull;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 获取会话详情的过滤器。
 * <p>
 * 这个类来自 spring-session-sample-boot-findbyusername/src/main/java/sample/session/SessionDetails.java
 */
@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 101)
public class SessionDetailsFilter extends OncePerRequestFilter {

    public static final String SESSION_DETAILS = "SESSION_DETAILS";

    private final IpLocation location;

    public SessionDetailsFilter(IpLocation location) {
        this.location = location;
    }

    @Override
    public void doFilterInternal(@Nonnull HttpServletRequest request,
                                 @Nonnull HttpServletResponse response,
                                 FilterChain chain) throws IOException, ServletException {
        chain.doFilter(request, response);

        SessionDetails details = new SessionDetails();
        String userAgent = request.getHeader(HttpHeaders.USER_AGENT);
        details.setAccessType(userAgent);

        HttpSession session = request.getSession(false);
        if (session != null) {
            String remoteAddress = Sessions.findRemoteAddress(request);
            details.setIp(remoteAddress);
            location.convert(remoteAddress, address -> {
                details.setLocation(address);
                session.setAttribute(SESSION_DETAILS, details);
            });
        }
    }
}
