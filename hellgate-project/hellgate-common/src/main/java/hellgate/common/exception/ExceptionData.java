package hellgate.common.exception;

import com.github.mrzhqiang.helper.Environments;
import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import lombok.Data;
import org.springframework.http.HttpStatus;

import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * 异常数据。
 */
@Data(staticConstructor = "of")
public final class ExceptionData {

    private final int status;
    private final String error;
    private final String message;
    private final String path;
    private final LocalDateTime timestamp = LocalDateTime.now();

    public static ExceptionData of(HttpStatus httpStatus, @Nullable Exception exception, HttpServletRequest request) {
        Preconditions.checkNotNull(httpStatus, "http status == null");
        Preconditions.checkNotNull(request, "request == null");
        String message = findMessage(httpStatus, exception);
        return of(httpStatus.value(), httpStatus.getReasonPhrase(), message, request.getRequestURI());
    }

    public static String findMessage(HttpStatus httpStatus, @Nullable Exception exception) {
        return Optional.ofNullable(exception)
                .filter(it -> Environments.debug())
                .map(Throwables::getStackTraceAsString)
                .orElseGet(() -> ExceptionCode.format(httpStatus.value(), exception));
    }
}
