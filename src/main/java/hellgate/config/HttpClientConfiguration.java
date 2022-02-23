package hellgate.config;

import com.github.mrzhqiang.helper.Environments;
import hellgate.common.third.ThirdApi;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import static okhttp3.logging.HttpLoggingInterceptor.Level.BASIC;
import static okhttp3.logging.HttpLoggingInterceptor.Level.BODY;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import java.time.Duration;

/**
 * HTTP 客户端配置。
 * <p>
 * 一般用来访问公共的 API 或远端服务器。
 */
@Configuration
public class HttpClientConfiguration {

    /**
     * BaseUrl 占位符。
     * <p>
     * 我们一般在 API 接口里面写死 URL 地址，避免出现很多的 API 接口，并且需要用 Retrofit 创建这些接口实例
     */
    private static final String BASE_URL_HOLDER = "https://www.baidu.com";

    @Bean
    public OkHttpClient okHttpClient() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(Environments.debug() ? BODY : BASIC);
        return new OkHttpClient.Builder()
                .readTimeout(Duration.ofSeconds(10))
                .writeTimeout(Duration.ofSeconds(30))
                .connectTimeout(Duration.ofSeconds(5))
                .addInterceptor(loggingInterceptor)
                .build();
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
    public ThirdApi ipLocationAPi(Retrofit retrofit) {
        return retrofit.create(ThirdApi.class);
    }
}
