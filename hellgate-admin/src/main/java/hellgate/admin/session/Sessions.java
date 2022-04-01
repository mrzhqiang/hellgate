package hellgate.admin.session;

import org.springframework.web.context.request.RequestAttributes;
import static org.springframework.web.context.request.RequestAttributes.REFERENCE_SESSION;
import static org.springframework.web.context.request.RequestAttributes.SCOPE_SESSION;
import org.springframework.web.context.request.RequestContextHolder;

import javax.annotation.Nullable;
import javax.servlet.http.HttpSession;

public final class Sessions {
    private Sessions() {
        // no instances
    }

    @Nullable
    public static HttpSession ofCurrent() {
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        return (HttpSession) requestAttributes.getAttribute(REFERENCE_SESSION, SCOPE_SESSION);
    }

    @Nullable
    public static SessionDetails ofCurrentDetails() {
        HttpSession session = Sessions.ofCurrent();
        if (session != null) {
            // 通过 SessionDetailsFilter 存放的会话详情，我们可以尝试获取
            Object details = session.getAttribute(SessionDetailsFilter.SESSION_DETAILS);
            if (details != null) {
                return ((SessionDetails) details);
            }
        }
        return null;
    }
}
