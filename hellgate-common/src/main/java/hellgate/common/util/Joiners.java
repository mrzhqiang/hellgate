package hellgate.common.util;

import com.google.common.base.Joiner;

public final class Joiners {
    private Joiners() {
        // no instances
    }

    public static final Joiner MESSAGE = Joiner.on(" - ").skipNulls();
    public static final Joiner QUERY = Joiner.on('?').skipNulls();

}
