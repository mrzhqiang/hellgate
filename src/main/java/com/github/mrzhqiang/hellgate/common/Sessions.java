package com.github.mrzhqiang.hellgate.common;

import org.springframework.security.web.WebAttributes;
import org.springframework.ui.Model;

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
     * <p>这通常是用户主动抛出来的异常，比如 404、400 之类的 HTTP 异常，需要显示在 Web 页面上。
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
        Object authenticationException = session.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        if (authenticationException instanceof Exception) {
            Exception exception = (Exception) authenticationException;
            String message = exception.getMessage();
            model.addAttribute(KEY_EXCEPTION_MESSAGE, message);
        }
    }
}
