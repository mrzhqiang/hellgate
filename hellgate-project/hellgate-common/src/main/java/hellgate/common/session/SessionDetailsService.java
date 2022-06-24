package hellgate.common.session;

import com.maxmind.geoip2.DatabaseReader;
import hellgate.common.http.PublicApi;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.InetAddress;

/**
 * 会话详情服务。
 * <p>
 * 此服务返回的每个方法都将是可观测对象，它们在 IO 线程池中执行任务，可由调用者决定在哪个线程上订阅并处理结果。
 */
@Slf4j
@Service
public class SessionDetailsService {

    private final PublicApi api;
    private final DatabaseReader reader;
    private final SessionDetailsMapper mapper;

    public SessionDetailsService(PublicApi api, DatabaseReader reader, SessionDetailsMapper mapper) {
        this.api = api;
        this.reader = reader;
        this.mapper = mapper;
    }

    /**
     * 通过第三方 API 将 IP 转换为包含地理位置的会话详情。
     */
    public Observable<SessionDetails> observeApi(String ip) {
        return api.whoisIp(PublicApi.WHOIS_IP_URL, ip, true)
                .subscribeOn(Schedulers.io())
                .map(mapper::toDetails);
    }

    /**
     * 从本地数据库寻找 IP 映射的地理位置，作为会话详情数据。
     */
    public Observable<SessionDetails> observeDb(String remoteAddress) {
        //noinspection ReactiveStreamsTooLongSameOperatorsChain
        return Observable.just(remoteAddress)
                .subscribeOn(Schedulers.io())
                .map(InetAddress::getByName)
                .map(reader::city)
                .map(mapper::toDetails);
    }
}
