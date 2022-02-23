package hellgate.common;

import com.google.common.base.Strings;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/**
 * 数字工具。
 */
public final class Numbers {
    private Numbers() {
        // no instances
    }

    @Nullable
    public static Integer ofInteger(String source) {
        return ofInteger(source, null);
    }

    @Nullable
    public static Integer ofInteger(String source, @Nullable Integer defaultValue) {
        if (Strings.isNullOrEmpty(source)) {
            return defaultValue;
        }

        try {
            return Integer.parseInt(source);
        } catch (Exception ignored) {
            return defaultValue;
        }
    }

    @Nullable
    public static Long ofLong(String source) {
        return ofLong(source, null);
    }

    @Nullable
    public static Long ofLong(String source, @Nullable Long defaultValue) {
        if (Strings.isNullOrEmpty(source)) {
            return defaultValue;
        }

        try {
            return Long.parseLong(source);
        } catch (Exception ignored) {
            return defaultValue;
        }
    }

    @Nullable
    public static Float ofFloat(String source) {
        return ofFloat(source, null);
    }

    @Nullable
    public static Float ofFloat(String source, @Nullable Float defaultValue) {
        if (Strings.isNullOrEmpty(source)) {
            return defaultValue;
        }

        try {
            return Float.parseFloat(source);
        } catch (Exception ignored) {
            return defaultValue;
        }
    }

    @Nullable
    public static Double ofDouble(String source) {
        return ofDouble(source, null);
    }

    @Nullable
    public static Double ofDouble(String source, @Nullable Double defaultValue) {
        if (Strings.isNullOrEmpty(source)) {
            return defaultValue;
        }

        try {
            return Double.parseDouble(source);
        } catch (Exception ignored) {
            return defaultValue;
        }
    }

    @Nullable
    public static BigDecimal ofBigDecimal(String source) {
        return ofBigDecimal(source, null);
    }

    @Nullable
    public static BigDecimal ofBigDecimal(String source, @Nullable BigDecimal defaultValue) {
        if (Strings.isNullOrEmpty(source)) {
            return defaultValue;
        }

        try {
            return new BigDecimal(source);
        } catch (Exception ignored) {
            return defaultValue;
        }
    }
}
