package hellgate.hub.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

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

    /**
     * 是否开启 cookie 功能。
     */
    private Boolean rememberMe = true;
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
}
