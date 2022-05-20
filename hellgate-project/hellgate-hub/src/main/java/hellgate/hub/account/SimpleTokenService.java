package hellgate.hub.account;

import hellgate.hub.config.JwtProperties;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class SimpleTokenService implements TokenService {

    private final JwtEncoder encoder;
    private final JwtProperties properties;

    public SimpleTokenService(JwtEncoder encoder,
                              JwtProperties properties) {
        this.encoder = encoder;
        this.properties = properties;
    }

    @Override
    public String create(String scope, String username) {
        Instant now = Instant.now();
        // @formatter:off
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(properties.getExpiry()))
                .subject(username)
                .claim("scope", scope)
                .build();
        // @formatter:on
        return this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}
