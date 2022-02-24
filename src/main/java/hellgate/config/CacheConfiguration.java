package hellgate.config;

import com.google.common.base.Joiner;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.google.common.base.CaseFormat.LOWER_CAMEL;
import static com.google.common.base.CaseFormat.LOWER_HYPHEN;
import static com.google.common.base.CaseFormat.UPPER_CAMEL;

/**
 * 缓存配置。
 */
@EnableCaching
@Configuration
public class CacheConfiguration extends CachingConfigurerSupport {

    private static final String CACHE_PREFIX = "cache";
    private static final Joiner CACHE_JOINER = Joiner.on(':').skipNulls();

    @Bean
    @Override
    public KeyGenerator keyGenerator() {
        return (target, method, params) -> {
            String className = target.getClass().getSimpleName();
            String classLowerHyphen = UPPER_CAMEL.to(LOWER_HYPHEN, className);
            String methodName = method.getName();
            String methodLowerHyphen = LOWER_CAMEL.to(LOWER_HYPHEN, methodName);
            return CACHE_JOINER.join(CACHE_PREFIX, classLowerHyphen, methodLowerHyphen, params);
        };
    }
}
