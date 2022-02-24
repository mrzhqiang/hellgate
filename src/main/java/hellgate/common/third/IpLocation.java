package hellgate.common.third;

import com.google.common.base.Strings;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.model.CityResponse;
import io.reactivex.observers.DefaultObserver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.net.InetAddress;
import java.util.function.Consumer;

/**
 * IP 位置工具。
 */
@Slf4j
@Component
public class IpLocation {

    private static final String UNKNOWN = "(unknown-address)";

    private final PublicApi api;
    private final DatabaseReader reader;

    public IpLocation(PublicApi api, DatabaseReader reader) {
        this.api = api;
        this.reader = reader;
    }

    /**
     * IP 转换为地理位置。
     *
     * @param ip       IP 地址字符串。如果为 Null 则不进行任何处理。
     * @param consumer 消费者。只关心数据，不关心过程。
     */
    public void convert(String ip, Consumer<String> consumer) {
        if (Strings.isNullOrEmpty(ip) || consumer == null) {
            return;
        }

        api.whoisIp(PublicApi.WHOIS_IP_URL, ip, true)
                .subscribe(new DefaultObserver<WhoisIpData>() {
                    @Override
                    public void onNext(@Nonnull WhoisIpData data) {
                        String address = data.getAddr();
                        consumer.accept(address);
                    }

                    @Override
                    public void onError(@Nonnull Throwable e) {
                        log.error("无法为 {} 找到对应地址，可能是：{} 问题", ip, e.getLocalizedMessage());
                        // 网络不可用，那么使用本地数据库；记录错误日志是为了判断哪个更好用
                        String address = findLocation(ip);
                        consumer.accept(address);
                    }

                    @Override
                    public void onComplete() {
                        // no instances
                    }
                });
    }

    private String findLocation(String remoteAddress) {
        try {
            CityResponse city = reader.city(InetAddress.getByName(remoteAddress));
            String cityName = city.getCity().getNames().get("zh-CN");
            String countryName = city.getCountry().getNames().get("zh-CN");
            if (Strings.isNullOrEmpty(cityName)) {
                return countryName;
            }
            if (Strings.isNullOrEmpty(countryName)) {
                return cityName;
            }
            return Strings.lenientFormat("%s%s", countryName, cityName);
        } catch (Exception ex) {
            return UNKNOWN;
        }
    }
}
