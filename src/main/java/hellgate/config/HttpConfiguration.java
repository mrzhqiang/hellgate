package hellgate.config;

import com.github.mrzhqiang.helper.Environments;
import hellgate.common.third.PublicApi;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import static okhttp3.logging.HttpLoggingInterceptor.Level.BASIC;
import static okhttp3.logging.HttpLoggingInterceptor.Level.BODY;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import java.io.File;
import java.nio.file.Paths;
import java.time.Duration;

/**
 * HTTP 客户端配置。
 * <p>
 * 一般用来访问公共的 API 或远端服务器。
 */
@EnableConfigurationProperties(HttpProperties.class)
@Configuration
public class HttpConfiguration {

    /**
     * BaseUrl 占位符。
     * <p>
     * 我们一般在 API 接口里面写死 URL 地址，避免出现很多的 API 接口，并且需要用 Retrofit 创建这些接口实例
     */
    private static final String BASE_URL_HOLDER = "http://localhost";

    private final HttpProperties properties;

    public HttpConfiguration(HttpProperties properties) {
        this.properties = properties;
    }

    @Bean
    public OkHttpClient okHttpClient() {
        return new OkHttpClient.Builder()
                // 需要及时响应
                .connectTimeout(Duration.ofSeconds(3))
                .addInterceptor(loggingInterceptor())
                .cache(localCache())
                .build();
    }

    private Interceptor loggingInterceptor() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(Environments.debug() ? BODY : BASIC);
        return loggingInterceptor;
    }

    private Cache localCache() {
        File directory = Paths.get(properties.getCachePath()).toFile();
        long maxSize = properties.getCacheMaxSize().toBytes();
        return new Cache(directory, maxSize);
    }

    @Bean
    public Retrofit ipLocationRetrofit(OkHttpClient client) {
        return new Retrofit.Builder()
                .client(client)
                .baseUrl(BASE_URL_HOLDER)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build();
    }

    @Bean
    public PublicApi ipLocationAPi(Retrofit retrofit) {
        return retrofit.create(PublicApi.class);
    }
}
