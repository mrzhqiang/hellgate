package hellgate.hub.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

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
     * 客服电话。
     */
    private String hotline;
    /**
     * 游戏群列表。
     */
    private List<String> groups;
}
