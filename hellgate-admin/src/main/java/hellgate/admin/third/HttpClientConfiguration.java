package hellgate.admin.third;

import com.github.mrzhqiang.helper.Environments;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import static okhttp3.logging.HttpLoggingInterceptor.Level.BODY;
import static okhttp3.logging.HttpLoggingInterceptor.Level.NONE;
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

@EnableConfigurationProperties(HttpClientProperties.class)
@Configuration
public class HttpClientConfiguration {

    /**
     * BaseUrl 占位符。
     * <p>
     * {@link retrofit2.http.Url @Url} 注解可以重新定义访问网址，所以这里只是占位符，不具备实际意义。
     */
    private static final String BASE_URL_HOLDER = "http://localhost";
    private static final int CONNECT_TIMEOUT = 3;
    private static final int CALL_TIMEOUT = 5;

    private final HttpClientProperties properties;

    public HttpClientConfiguration(HttpClientProperties properties) {
        this.properties = properties;
    }

    @Bean
    public OkHttpClient okHttpClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(Duration.ofSeconds(CONNECT_TIMEOUT))
                .callTimeout(Duration.ofSeconds(CALL_TIMEOUT))
                .cache(localCache())
                .followSslRedirects(false)
                .followRedirects(false)
                .addInterceptor(loggingInterceptor())
                .build();
    }

    private Interceptor loggingInterceptor() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        // IDEA 调试模式，打印细节
        loggingInterceptor.setLevel(Environments.debug() ? BODY : NONE);
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
