package hellgate.admin.third;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

@SuppressWarnings("HttpUrlsUsage")
public interface PublicApi {

    String WHOIS_IP_URL = "http://whois.pconline.com.cn/ipJson.jsp";

    @GET
    Observable<WhoisIpData> whoisIp(@Url String url, @Query("ip") String ip, @Query("json") boolean json);
}
