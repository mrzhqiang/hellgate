package com.github.mrzhqiang.hellgateapi.config;

import com.github.mrzhqiang.hellgateapi.exception.ResourceNotFoundException;
import com.github.mrzhqiang.hellgateapi.model.data.ErrorResponseData;
import com.google.common.base.VerifyException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Object handleException(Exception ex, HttpServletRequest request) {
        if (ex instanceof ResourceNotFoundException) {
            return resolveException(ex, request, HttpStatus.NOT_FOUND);
        }
        if (ex instanceof NullPointerException
                || ex instanceof IllegalArgumentException
                || ex instanceof IllegalStateException) {
            return resolveException(ex, request, HttpStatus.BAD_REQUEST);
        }
        //noinspection UnstableApiUsage
        if (ex instanceof VerifyException) {
            return resolveException(ex, request, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return null;
    }

    private Object resolveException(Exception ex, HttpServletRequest request, HttpStatus httpStatus) {
        if (checkHtmlRequest(request)) {
            ModelAndView view = new ModelAndView("error/" + httpStatus.value());
            view.addObject("status", httpStatus.value());
            view.addObject("error", httpStatus.getReasonPhrase());
            view.addObject("message", ex.getMessage());
            return view;
        }
        ErrorResponseData data = ErrorResponseData.ofException(httpStatus, ex, request);
        return ResponseEntity.status(httpStatus).body(data);
    }

    private boolean checkHtmlRequest(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(HttpHeaders.ACCEPT))
                .orElse("")
                .contains(MediaType.TEXT_HTML_VALUE);
    }
}
