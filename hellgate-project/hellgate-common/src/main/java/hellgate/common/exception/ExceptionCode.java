package hellgate.common.exception;

import com.google.common.base.Strings;
import com.google.common.base.VerifyException;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.Optional;

/**
 * 异常代码。
 * <p>
 * 设计方案：
 * <p>
 * 1. 以 http code 为前缀，比如：4xx 客户端异常、5xx 服务器异常
 * <p>
 * 2. 以异常类型为中缀，最小 3 位长度不足补零，比如：4xx-000 客户端-默认类型
 * <p>
 * 3. 暂时设计为 hash code 取 10000 的余数进行拼接，不足补零，比如：4xx-000-xxxx 客户端-默认类型-具体异常
 */
public enum ExceptionCode {

    /**
     * 默认类型
     */
    DEFAULT("0"),
    /**
     * IO 异常。
     * <p>
     * 通常是数据库或网络连接出现问题。
     *
     * @see java.io.IOException
     */
    IO("1"),
    /**
     * 代码异常。
     * <p>
     * 通常是代码编写不规范导致。
     *
     * @see NullPointerException
     * @see IndexOutOfBoundsException
     */
    CODE("2"),
    /**
     * 非法相关异常。
     * <p>
     * 通常是方法参数或程序状态异常导致。
     *
     * @see IllegalArgumentException
     * @see IllegalStateException
     */
    ILLEGAL("3"),
    /**
     * 验证相关异常。
     * <p>
     * 通常是程序状态验证不通过导致。
     *
     * @see com.google.common.base.VerifyException
     */
    VERIFY("4");

    private static final int TYPE_MIN_LENGTH = 3;
    private static final int MESSAGE_MOD = 10000;
    private static final int MESSAGE_MIN_LENGTH = 4;
    private static final String FORMAT_TEMPLATE = "%s-%s-%s";

    private final String type;

    ExceptionCode(String type) {
        this.type = type;
    }

    public static String format(int httpStatus, @Nullable Exception exception) {
        ExceptionCode code = DEFAULT;
        if (exception instanceof IOException) {
            code = IO;
        }
        if (exception instanceof NullPointerException) {
            code = CODE;
        }
        if (exception instanceof IllegalArgumentException
                || exception instanceof IllegalStateException) {
            code = ILLEGAL;
        }
        if (exception instanceof VerifyException) {
            code = VERIFY;
        }

        String type = Strings.padStart(code.type, TYPE_MIN_LENGTH, '0');
        String message = Optional.ofNullable(exception)
                .map(Throwable::getMessage)
                .map(String::hashCode)
                .map(it -> it % MESSAGE_MOD)
                .map(String::valueOf)
                .orElse("0");
        message = Strings.padStart(message, MESSAGE_MIN_LENGTH, '0');

        return Strings.lenientFormat(FORMAT_TEMPLATE, httpStatus, type, message);
    }
}
