package hellgate.hub.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@Getter
@Setter
@ToString
@ConfigurationProperties("session")
public class SessionProperties {

    private static final int DEF_MAX_LOGIN_FAILED = 5;
    private static final Duration DEF_COOKIE_TIMEOUT = Duration.ofMinutes(15);
    private static final Duration DEF_LOCKED_DURATION = Duration.ofMinutes(5);
    private static final Duration DEF_FIRST_FAILED_DURATION = Duration.ofHours(1);

    /**
     * 最大登录失败次数。
     * <p>
     * 超过失败次数将会被锁定账号一段时间。
     */
    private Integer maxLoginFailed = DEF_MAX_LOGIN_FAILED;
    /**
     * Cookie 超时时间。
     * <p>
     * Remember-me 即 Cookie 的会话超时时长。
     */
    private Duration cookieTimeout = DEF_COOKIE_TIMEOUT;
    /**
     * 锁定持续时长。
     * <p>
     * 当超过最大登录失败次数，则直接锁定账号，此时无法再尝试密码。
     */
    private Duration lockedDuration = DEF_LOCKED_DURATION;
    /**
     * 登录失败次数统计的持续时长。
     * <p>
     * 超过此区间时长，则重置统计次数。
     */
    private Duration firstFailedDuration = DEF_FIRST_FAILED_DURATION;
}
