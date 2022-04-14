package hellgate.core.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.security.interfaces.RSAPublicKey;

@Getter
@Setter
@ToString
@ConfigurationProperties("jwt")
public class JwtProperties {

    /**
     * 公钥。
     * <p>
     * 需要与 hellgate-hub 模块中的公钥保持一致。
     */
    private RSAPublicKey publicKey;
}
