package hellgate.api.config;

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
    private static final Duration DEF_FIRST_FAILED_DURATION = Duration.ofHours(1);
    private static final Duration DEF_LOCKED_DURATION = Duration.ofMinutes(5);
    private static final int DEF_MAX_SESSION = 1;
    private static final String DEF_EXPIRED_PATH = "/login?expired";

    /**
     * 最大登录失败次数。
     */
    private Integer maxLoginFailed = DEF_MAX_LOGIN_FAILED;
    /**
     * 登录失败次数统计的持续时长。
     * <p>
     * 超过此区间时长，则重置统计次数。
     */
    private Duration firstFailedDuration = DEF_FIRST_FAILED_DURATION;
    /**
     * 锁定持续时长。
     * <p>
     * 当超过最大登录失败次数，则直接锁定账号，此时无法再尝试密码。
     */
    private Duration lockedDuration = DEF_LOCKED_DURATION;
    /**
     * 每个账号最多能登录的会话数量。
     */
    private Integer maxSession = DEF_MAX_SESSION;
    /**
     * 会话过期重定向地址。
     * <p>
     * 可能是由于会话数量过多而引起会话过期。
     */
    private String expiredPath = DEF_EXPIRED_PATH;
}
