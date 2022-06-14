package hellgate.common.util;

import com.google.common.base.Splitter;

/**
 * 分离器工具。
 */
public final class Splitters {
    private Splitters() {
        // no instances
    }

    public static final Splitter COMMA = Splitter.on(',').trimResults();
}
