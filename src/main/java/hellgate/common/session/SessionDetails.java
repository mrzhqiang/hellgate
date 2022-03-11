package hellgate.common.session;

import com.google.common.base.Strings;
import com.maxmind.geoip2.model.CityResponse;
import hellgate.common.third.WhoisIpData;
import lombok.Data;

import java.io.Serializable;

/**
 * 会话详情。
 * <p>
 * 这个类来自 spring-session-sample-boot-findbyusername/src/main/java/sample/session/SessionDetails.java
 */
@Data
public class SessionDetails implements Serializable {

    private static final long serialVersionUID = 8850489178248613501L;

    private String ip;
    private String location;
    private String accessType;

    public static SessionDetails of(WhoisIpData data) {
        SessionDetails details = new SessionDetails();
        details.setIp(data.getIp());
        details.setLocation(data.getAddr());
        return details;
    }

    public static SessionDetails of(CityResponse response) {
        SessionDetails details = new SessionDetails();
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
