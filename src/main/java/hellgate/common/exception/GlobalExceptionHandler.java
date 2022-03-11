package hellgate.common.exception;

import com.google.common.base.VerifyException;
import hellgate.common.Views;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;

/**
 * 全局异常处理器。
 *
 * <p>
 * 实际上只在 Controller 层捕捉抛出的异常，来到这里进行处理。
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Object handleException(Exception ex, HttpServletRequest request) {
        log.error("global handle exception", ex);

        if (ex instanceof ResourceNotFoundException) {
            return resolveException(ex, request, HttpStatus.NOT_FOUND);
        }

        if (ex instanceof NullPointerException || ex instanceof IllegalArgumentException) {
            return resolveException(ex, request, HttpStatus.BAD_REQUEST);
        }

        if (ex instanceof VerifyException || ex instanceof IllegalStateException) {
            return resolveException(ex, request, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (ex instanceof IOException) {
            return resolveException(ex, request, HttpStatus.SERVICE_UNAVAILABLE);
        }

        return resolveException(ex, request, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private Object resolveException(Exception exception, HttpServletRequest request, HttpStatus httpStatus) {
        if (checkHtmlRequest(request)) {
            return Views.ofException(httpStatus, exception);
        }

        ExceptionData data = ExceptionData.of(httpStatus, exception, request);
        return ResponseEntity.status(httpStatus).body(data);
    }

    private boolean checkHtmlRequest(HttpServletRequest request) {
        return Optional.ofNullable(request)
                .map(it -> it.getHeader(HttpHeaders.ACCEPT))
                .filter(it -> it.contains(MediaType.TEXT_HTML_VALUE))
                .isPresent();
    }
}
