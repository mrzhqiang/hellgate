package hellgate.config;

import com.google.common.collect.Lists;
import com.maxmind.db.CHMCache;
import com.maxmind.geoip2.DatabaseReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.InputStream;
import java.util.List;

/**
 * GeoIp2 配置。
 */
@Configuration
public class GeoIpConfiguration {

    /**
     * 按照喜欢程度排序的语言列表，从最喜欢到最不喜欢。
     */
    public static final List<String> LOCALES = Lists.newArrayList("zh_CN", "en");

    @Bean
    public DatabaseReader geoDatabaseReader(@Value("classpath:GeoLite2-City.mmdb") InputStream geoInputStream)
            throws Exception {
        return new DatabaseReader.Builder(geoInputStream)
                .locales(LOCALES)
                .withCache(new CHMCache())
                .build();
    }
}
