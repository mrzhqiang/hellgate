package hellgate.common.session;

import com.google.common.base.Strings;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import hellgate.common.third.PublicApi;
import hellgate.common.third.WhoisIpData;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.InetAddress;

/**
 * 会话详情服务。
 */
@Slf4j
@Service
public class SessionDetailsService {

    private final PublicApi api;
    private final DatabaseReader reader;

    public SessionDetailsService(PublicApi api, DatabaseReader reader) {
        this.api = api;
        this.reader = reader;
    }

    /**
     * 通过第三方 API 将 IP 转换为包含地理位置的会话详情。
     */
    public Observable<SessionDetails> observeApi(String ip) {
        return api.whoisIp(PublicApi.WHOIS_IP_URL, ip, true)
                .subscribeOn(Schedulers.io())
                .map(this::convertSessionDetails);
    }

    /**
     * 从本地数据库寻找 IP 映射的地理位置，作为会话详情数据。
     */
    public Observable<SessionDetails> observeDb(String remoteAddress) {
        return Observable.just(remoteAddress)
                .subscribeOn(Schedulers.io())
                .map(InetAddress::getByName)
                .map(this::convertSessionDetails);
    }

    private SessionDetails convertSessionDetails(WhoisIpData data) {
        SessionDetails details = new SessionDetails();
        details.setIp(data.getIp());
        details.setLocation(data.getAddr());
        return details;
    }

    private SessionDetails convertSessionDetails(InetAddress address) throws IOException, GeoIp2Exception {
        SessionDetails details = new SessionDetails();
        CityResponse response = reader.city(address);
        details.setIp(response.getTraits().getIpAddress());

        String cityName = response.getCity().getNames().get("zh-CN");
        String countryName = response.getCountry().getNames().get("zh-CN");
        String location = "(unknown)";
        if (!Strings.isNullOrEmpty(countryName) && !Strings.isNullOrEmpty(cityName)) {
            location = Strings.lenientFormat("%s%s", countryName, cityName);
        }
        details.setLocation(location);
        return details;
    }
}
