package com.github.mrzhqiang.hellgate.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 网站属性。
 */
@Getter
@Setter
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
     * 认证成功默认跳转的地址。
     */
    private String defaultSuccessUrl;
}
