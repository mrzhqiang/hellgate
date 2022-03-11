package hellgate.common.exception;

import com.google.common.base.Strings;

import java.io.IOException;
import java.util.Optional;

/**
 * 异常代码。
 * <p>
 * 设计方案：
 * <p>
 * 1. 以 http code 为前缀，比如：4xx 客户端异常、5xx 服务器异常
 * <p>
 * 2. 以当前异常类型为中缀，最小 3 位长度不足补零，比如：4xx-000 客户端-默认类型
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
     * <p>
     * {@link java.io.IOException}
     */
    IO("1"),
    /**
     * 空指针异常。
     * <p>
     * 通常是代码中存在空值导致。
     * <p>
     * {@link NullPointerException}
     */
    NPE("2"),
    /**
     * 非法相关异常。
     * <p>
     * 通常是方法参数、程序状态或访问未授权导致。
     * <p>
     * {@link IllegalArgumentException}
     * <p>
     * {@link IllegalStateException}
     * <p>
     * {@link IllegalAccessException}
     */
    ILLEGAL("3"),
    ;

    private static final int TYPE_MIN_LENGTH = 3;
    private static final int MESSAGE_MOD = 10000;
    private static final int MESSAGE_MIN_LENGTH = 4;
    private static final String FORMAT_TEMPLATE = "%s-%s-%s";

    private final String type;

    ExceptionCode(String type) {
        this.type = type;
    }

    public static String format(int httpStatus, Exception exception) {
        ExceptionCode code = DEFAULT;
        if (exception instanceof IOException) {
            code = IO;
        }
        if (exception instanceof NullPointerException) {
            code = NPE;
        }
        if (exception instanceof IllegalArgumentException
                || exception instanceof IllegalStateException
                || exception instanceof IllegalAccessException) {
            code = ILLEGAL;
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
