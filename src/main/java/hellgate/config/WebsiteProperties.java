package hellgate.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 网站属性。
 */
@Getter
@Setter
@ToString
@ConfigurationProperties("website")
public class WebsiteProperties {

    /**
     * 网站作者。
     */
    private String author;
    /**
     * 网站图标。
     */
    private String favicon;
    /**
     * 网站标题。
     */
    private String title;
    /**
     * 网站版权声明。
     */
    private String copyRight;
    /**
     * 是否展示记住我按钮。
     */
    private Boolean rememberMe;
    /**
     * 可忽略安全策略的路径。
     */
    private String[] ignorePath;
    /**
     * 无需认证的公开路径。
     */
    private String[] publicPath;
    /**
     * API 路径。
     */
    private String apiPath;
    /**
     * 认证成功默认跳转的地址。
     */
    private String defaultSuccessUrl;
    /**
     * 登录路径。
     */
    private String loginPath;
    /**
     * 注册路径。
     */
    private String registerPath;
    /**
     * 注册失败跳转路径。
     */
    private String registerErrorPath;
}
