package com.github.mrzhqiang.hellgate.common;

import com.google.common.base.VerifyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Object handleException(Exception ex, HttpServletRequest request) {
        log.error("handle exception", ex);
        if (ex instanceof ResourceNotFoundException) {
            return resolveException(ex, request, HttpStatus.NOT_FOUND);
        }
        if (ex instanceof NullPointerException
                || ex instanceof IllegalArgumentException) {
            return resolveException(ex, request, HttpStatus.BAD_REQUEST);
        }
        if (ex instanceof VerifyException
                || ex instanceof IllegalStateException) {
            return resolveException(ex, request, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (ex instanceof IOException) {
            return resolveException(ex, request, HttpStatus.SERVICE_UNAVAILABLE);
        }
        return null;
    }

    private Object resolveException(Exception ex, HttpServletRequest request, HttpStatus httpStatus) {
        if (checkHtmlRequest(request)) {
            ModelAndView view = new ModelAndView("error/" + httpStatus.value());
            view.addObject("status", httpStatus.value());
            view.addObject("error", httpStatus.getReasonPhrase());
            view.addObject("message", ex.getMessage());
            view.addObject("timestamp", DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(LocalDateTime.now()));
            return view;
        }
        ErrorData data = ErrorDatas.ofException(httpStatus, ex, request);
        return ResponseEntity.status(httpStatus).body(data);
    }

    private boolean checkHtmlRequest(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(HttpHeaders.ACCEPT))
                .orElse("")
                .contains(MediaType.TEXT_HTML_VALUE);
    }
}
