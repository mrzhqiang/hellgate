package hellgate.common.session;

import com.google.common.net.HttpHeaders;
import static org.springframework.security.web.WebAttributes.AUTHENTICATION_EXCEPTION;
import org.springframework.ui.Model;
import org.springframework.web.context.request.RequestAttributes;
import static org.springframework.web.context.request.RequestAttributes.REFERENCE_SESSION;
import static org.springframework.web.context.request.RequestAttributes.SCOPE_SESSION;
import org.springframework.web.context.request.RequestContextHolder;

import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 会话工具。
 */
public final class Sessions {
    private Sessions() {
        // no instances
    }

    /**
     * 异常消息的 Key 值。
     */
    public static final String KEY_EXCEPTION_MESSAGE = "message";

    /**
     * HTTP 会话中的异常。
     *
     * <p>
     * 这通常是用户主动抛出来的异常，比如 404、400 之类的 HTTP 异常，需要显示在 Web 页面上。
     *
     * @param session 当前 HTTP 会话。
     * @param model   当前会话模型，包含一些临时属性。
     */
    public static void httpException(HttpSession session, Model model) {
        if (session == null || model == null) {
            // do nothing
            return;
        }

        // 当 Filter 检测到异常，则将异常放入 AUTHENTICATION_EXCEPTION，所以我们可以从这里获取到异常实例
        Object authenticationException = session.getAttribute(AUTHENTICATION_EXCEPTION);
        if (authenticationException instanceof Exception) {
            Exception exception = (Exception) authenticationException;
            String message = exception.getMessage();
            model.addAttribute(KEY_EXCEPTION_MESSAGE, message);
        }
    }

    /**
     * 获取当前会话。
     */
    @Nullable
    public static HttpSession ofCurrent() {
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        return (HttpSession) requestAttributes.getAttribute(REFERENCE_SESSION, SCOPE_SESSION);
    }

    /**
     * 获取当前会话详情。
     */
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

    /**
     * 通过 HTTP 请求找到准确的客户端 IP 地址。
     *
     * @param request HTTP 请求。
     * @return IP 地址。
     */
    public static String findRemoteAddress(HttpServletRequest request) {
        String remoteAddr = request.getHeader(HttpHeaders.X_FORWARDED_FOR);
        if (remoteAddr == null) {
            remoteAddr = request.getRemoteAddr();
        } else if (remoteAddr.contains(",")) {
            remoteAddr = remoteAddr.split(",")[0];
        }
        return remoteAddr;
    }
}
