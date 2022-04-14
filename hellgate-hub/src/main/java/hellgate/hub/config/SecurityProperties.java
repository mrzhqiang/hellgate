package hellgate.hub.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@Getter
@Setter
@ToString
@ConfigurationProperties("security")
public class SecurityProperties {

    public static final String ERROR_SUFFIX = "?error";

    private static final String DEF_HOME_PATH = "/";
    private static final String DEF_LOGIN_PATH = "/login";
    private static final String DEF_REGISTER_PATH = "/register";
    private static final String DEF_REGISTER_ERROR_PATH = DEF_REGISTER_PATH + ERROR_SUFFIX;
    private static final String[] DEF_IGNORE_PATH = {};
    private static final String[] DEF_PUBLIC_PATH = {DEF_HOME_PATH, "/index", DEF_LOGIN_PATH, DEF_REGISTER_PATH};
    private static final String DEF_BOOKMARK_PATH = "/bookmark";
    private static final String DEF_BOOKMARK_TEMPLATE = DEF_BOOKMARK_PATH + "?username=%s&password=%s&timestamp=%s";

    private static final int DEF_MAX_LOGIN_FAILED = 5;
    private static final Duration DEF_LOCKED_DURATION = Duration.ofMinutes(5);
    private static final Duration DEF_FIRST_FAILED_DURATION = Duration.ofHours(1);

    /**
     * 可忽略安全策略的路径。
     */
    private String[] ignorePath = DEF_IGNORE_PATH;
    /**
     * 无需认证的公开路径。
     */
    private String[] publicPath = DEF_PUBLIC_PATH;
    /**
     * 登录路径。
     */
    private String loginPath = DEF_LOGIN_PATH;
    /**
     * 注册路径。
     */
    private String registerPath = DEF_REGISTER_PATH;
    /**
     * 注册失败跳转路径。
     */
    private String registerErrorPath = DEF_REGISTER_ERROR_PATH;
    /**
     * 认证成功默认跳转的地址。
     */
    private String defaultSuccessUrl = DEF_HOME_PATH;
    /**
     * 书签路径
     */
    private String bookmarkPath = DEF_BOOKMARK_PATH;
    /**
     * 书签模板
     */
    private String bookmarkTemplate = DEF_BOOKMARK_TEMPLATE;
    /**
     * 最大登录失败次数。
     * <p>
     * 超过失败次数将会被锁定账号一段时间。
     */
    private Integer maxLoginFailed = DEF_MAX_LOGIN_FAILED;
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
