package hellgate.common.third;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface ThirdApi {

    String IP_LOCATION = "http://whois.pconline.com.cn/ipJson.jsp";

    @GET
    Observable<WhoisIpData> whoisIp(@Url String url, @Query("ip") String ip, @Query("json") boolean json);

}
