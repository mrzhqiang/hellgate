package com.github.mrzhqiang.hellgate.common;

import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;

public enum ErrorDatas {
    ;

    public static ErrorData of(int status, String error, String message, String path) {
        return new ErrorData(status, error, message, path);
    }

    public static ErrorData ofException(HttpStatus httpStatus, Exception ex, HttpServletRequest request) {
        return of(httpStatus.value(), httpStatus.getReasonPhrase(), ex.getMessage(), request.getRequestURI());
    }
}
