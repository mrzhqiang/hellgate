package hellgate.common.exception;

import com.github.mrzhqiang.helper.Environments;
import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import lombok.Data;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 异常数据。
 */
@Data(staticConstructor = "of")
public final class ExceptionData {

    private final int status;
    private final String error;
    private final String message;
    private final String path;
    private final Date timestamp = new Date();

    public static ExceptionData of(HttpStatus httpStatus, Exception exception, HttpServletRequest request) {
        Preconditions.checkNotNull(httpStatus, "http status == null");
        Preconditions.checkNotNull(exception, "exception == null");
        Preconditions.checkNotNull(request, "request == null");
        String message = findMessage(httpStatus, exception);
        return of(httpStatus.value(), httpStatus.getReasonPhrase(), message, request.getRequestURI());
    }

    public static String findMessage(HttpStatus httpStatus, Exception exception) {
        String message;
        if (Environments.debug()) {
            message = Throwables.getStackTraceAsString(exception);
        } else {
            message = ExceptionCode.format(httpStatus.value(), exception);
        }
        return message;
    }
}
