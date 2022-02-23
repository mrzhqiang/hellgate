package hellgate.config;

import com.google.common.base.CaseFormat;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CacheConfigurationTest {

    @Test
    public void testGuavaCaseFormat() {
        String methodName = this.getClass().getMethods()[0].getName();
        String lowerHyphen = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN, methodName);
        Assertions.assertEquals("test-guava-case-format", lowerHyphen);
    }
}
