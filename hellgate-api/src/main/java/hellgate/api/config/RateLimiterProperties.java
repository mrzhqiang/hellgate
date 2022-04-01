package hellgate.api.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ToString
@ConfigurationProperties("session.rate-limiter")
public class RateLimiterProperties {

    public static final String DEF_RATE_LIMITER_KEY = "RateLimiter";
    public static final double DEF_RATE_LIMITER_PERMITS = 2.0;

    /**
     * 会话中持有的限流器 KEY 名称。
     */
    private String keyName = DEF_RATE_LIMITER_KEY;
    /**
     * 许可：平均每秒访问次数不超过 N 次。
     */
    private Double permits = DEF_RATE_LIMITER_PERMITS;
}
