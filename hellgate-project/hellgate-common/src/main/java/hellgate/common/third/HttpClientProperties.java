package hellgate.common.third;

import com.github.mrzhqiang.helper.Environments;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.unit.DataSize;

import java.nio.file.Paths;

@Getter
@Setter
@ToString
@ConfigurationProperties("http.client")
public class HttpClientProperties {

    private final static String DEF_CACHE_PATH = Paths.get(Environments.USER_DIR, ".cache").toString();
    private final static DataSize DEF_CACHE_MAX_SIZE = DataSize.ofGigabytes(1);

    private String cachePath = DEF_CACHE_PATH;
    private DataSize cacheMaxSize = DEF_CACHE_MAX_SIZE;
}
