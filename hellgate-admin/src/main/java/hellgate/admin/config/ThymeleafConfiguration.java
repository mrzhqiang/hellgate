package hellgate.admin.config;

import com.github.mrzhqiang.helper.Environments;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

@Slf4j
@ConditionalOnClass(ThymeleafViewResolver.class)
@EnableConfigurationProperties(WebsiteProperties.class)
@Configuration
public class ThymeleafConfiguration implements CommandLineRunner {

    private static final String WEBSITE_KEY = "website";

    private final WebsiteProperties websiteProperties;
    private final ThymeleafViewResolver resolver;

    public ThymeleafConfiguration(WebsiteProperties websiteProperties, ThymeleafViewResolver resolver) {
        this.websiteProperties = websiteProperties;
        this.resolver = resolver;
    }

    @Override
    public void run(String... args) {
        log.info("add website properties to thymeleaf view resolver static variable.");
        if (Environments.debug()) {
            resolver.addStaticVariable("debug", Boolean.TRUE.toString());
        }
        resolver.addStaticVariable(WEBSITE_KEY, websiteProperties);
    }
}
