package com.github.mrzhqiang.hellgate.common;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;

/**
 * 视图工具。
 */
public class Views {
    private Views() {
        // no instances
    }

    public static final String EXCEPTION_VIEW_NAME_PREFIX = "error/";

    public static final String KEY_STATUS = "status";
    public static final String KEY_ERROR = "error";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_TIMESTAMP = "timestamp";

    public static ModelAndView ofException(HttpStatus httpStatus, Exception exception) {
        ModelAndView view = new ModelAndView(EXCEPTION_VIEW_NAME_PREFIX + httpStatus.value(), httpStatus);
        view.addObject(KEY_STATUS, httpStatus.value());
        view.addObject(KEY_ERROR, httpStatus.getReasonPhrase());
        view.addObject(KEY_MESSAGE, exception.getMessage());
        view.addObject(KEY_TIMESTAMP, LocalDateTime.now().format(ISO_LOCAL_DATE_TIME));
        return view;
    }

    public static ModelAndView ofRefresh() {
        return new ModelAndView("refresh");
    }
}
