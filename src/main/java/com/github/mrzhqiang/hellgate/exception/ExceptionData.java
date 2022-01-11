package com.github.mrzhqiang.hellgate.exception;

import com.google.common.base.Preconditions;
import lombok.Data;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Data(staticConstructor = "of")
public final class ExceptionData {

    private final int status;
    private final String error;
    private final String message;
    private final String path;
    private final LocalDateTime timestamp = LocalDateTime.now();

    public static ExceptionData of(HttpStatus httpStatus, Exception exception, HttpServletRequest request) {
        Preconditions.checkNotNull(httpStatus, "http status == null");
        Preconditions.checkNotNull(exception, "exception == null");
        Preconditions.checkNotNull(request, "request == null");

        return of(httpStatus.value(), httpStatus.getReasonPhrase(), exception.getMessage(), request.getRequestURI());
    }

}
