package hellgate.common.third;

import com.maxmind.geoip2.DatabaseReader;
import hellgate.common.session.SessionDetails;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.InetAddress;

/**
 * IP 服务。
 */
@Slf4j
@Service
public class IpService {

    private final PublicApi api;
    private final DatabaseReader reader;

    public IpService(PublicApi api, DatabaseReader reader) {
        this.api = api;
        this.reader = reader;
    }

    /**
     * 通过第三方 API 将 IP 转换为地理位置。
     */
    public Observable<SessionDetails> observeApi(String ip) {
        return api.whoisIp(PublicApi.WHOIS_IP_URL, ip, true)
                .subscribeOn(Schedulers.io())
                .map(SessionDetails::of);
    }

    /**
     * 从本地数据库寻找 IP 映射的地理位置。
     */
    public Observable<SessionDetails> observeDb(String remoteAddress) {
        return Observable.just(remoteAddress)
                .subscribeOn(Schedulers.io())
                .map(InetAddress::getByName)
                .map(reader::city)
                .map(SessionDetails::of);
    }
}
