package hellgate.api.config;

import static com.google.common.base.CaseFormat.LOWER_CAMEL;
import static com.google.common.base.CaseFormat.LOWER_HYPHEN;
import static com.google.common.base.CaseFormat.UPPER_CAMEL;
import com.google.common.base.Joiner;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableCaching
@Configuration
public class CacheConfiguration extends CachingConfigurerSupport {

    private static final Joiner CACHE_JOINER = Joiner.on(':').skipNulls();

    /**
     * 换成 key 生成器。
     * <p>
     * 注意：只有在缓存相关注解没有指定 key 的时候，才会使用这个生成器。
     */
    @Bean
    @Override
    public KeyGenerator keyGenerator() {
        // classLowerHyphen -- CacheConfiguration >> cache-configuration
        // methodLowerHyphen -- keyGenerator >> key-generator
        // key -- classLowerHyphen:methodLowerHyphen:params...
        return (target, method, params) -> {
            String className = target.getClass().getSimpleName();
            String classLowerHyphen = UPPER_CAMEL.to(LOWER_HYPHEN, className);
            String methodName = method.getName();
            String methodLowerHyphen = LOWER_CAMEL.to(LOWER_HYPHEN, methodName);
            return CACHE_JOINER.join(classLowerHyphen, methodLowerHyphen, params);
        };
    }
}
