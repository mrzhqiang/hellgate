package hellgate.server.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ToString
@ConfigurationProperties("security")
public class SecurityProperties {

    private static final String DEF_HUB_URL = "http://hellgate.com";
    private static final String DEF_SCRIPT_PATH = "/script";
    private static final String DEF_LOGIN_PATH = "/login";
    private static final String DEF_API_PATH = "/api/**";

    /**
     * 是否开启 cookie 功能。
     */
    private Boolean rememberMe = true;
    /**
     * 账号中心网址。
     */
    private String hubUrl = DEF_HUB_URL;
    /**
     * 剧本页面路径。
     */
    private String scriptPath = DEF_SCRIPT_PATH;
    /**
     * 登录页面路径。
     */
    private String loginPath = DEF_LOGIN_PATH;
    /**
     * API 路径。
     */
    private String apiPath = DEF_API_PATH;
}
