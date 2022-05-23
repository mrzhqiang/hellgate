package hellgate.manage.config;

import com.github.mrzhqiang.helper.Environments;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ToString
@ConfigurationProperties("security")
public class SecurityProperties {

    private static final boolean DEF_REMEMBER_ME = !Environments.debug();
    private static final String DEF_HOME_PATH = "/";
    private static final String DEF_LOGIN_PATH = "/login";
    private static final String[] DEF_IGNORE_PATH = {"/fontawesome/**"};
    private static final String DEF_ERROR_PATH = "/error";
    private static final String DEF_ERROR_CHILDREN_PATH = "/error/**";
    private static final String[] DEF_PUBLIC_PATH = {DEF_HOME_PATH, "/index", DEF_ERROR_PATH, DEF_ERROR_CHILDREN_PATH};

    /**
     * 是否展示记住我按钮并开启记住我功能（cookie）。
     */
    private Boolean rememberMe = DEF_REMEMBER_ME;
    /**
     * 可忽略安全策略的路径。
     */
    private String[] ignorePath = DEF_IGNORE_PATH;
    /**
     * 无需认证的公开路径。
     */
    private String[] publicPath = DEF_PUBLIC_PATH;
    /**
     * 认证成功默认跳转的地址。
     */
    private String defaultSuccessUrl = DEF_HOME_PATH;
    /**
     * 登录路径。
     */
    private String loginPath = DEF_LOGIN_PATH;
}
