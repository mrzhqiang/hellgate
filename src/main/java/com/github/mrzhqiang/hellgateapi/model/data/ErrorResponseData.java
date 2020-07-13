package com.github.mrzhqiang.hellgateapi.model.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor(staticName = "of")
public class ErrorResponseData {
    private final int status;
    private final String error;
    private final String message;
    private final String path;
    private final LocalDateTime timestamp = LocalDateTime.now();

    public static ErrorResponseData ofException(HttpStatus httpStatus, Exception ex, HttpServletRequest request) {
        return of(httpStatus.value(), httpStatus.getReasonPhrase(), ex.getMessage(), request.getRequestURI());
    }
}
