package com.gsitm.framework.security.jwt.claims;

import com.gsitm.framework.core.utils.JwtSecretKeys;
import com.gsitm.framework.core.utils.WebSupportUtils;
import com.gsitm.framework.security.jwt.properties.JwtProperties;
import com.gsitm.framework.security.jwt.storage.DefaultJwtStorage;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;

/**
 * <pre>
 * com.gsitm.framework.security.jwt.claims
 *
 * Description :
 * </pre>
 *
 * @author mercury
 * @since 2020/09/16
 */
@RequiredArgsConstructor
@Configuration
public class JwtTokenClaimsManager {
    private final DefaultJwtStorage defaultJwtStorage;
    private final JwtProperties jwtProperties;

    public Object getClaim(String claimKey) {
        SecretKey secretKey = JwtSecretKeys.hs256(jwtProperties.getSecretKey());
        String token = defaultJwtStorage.getToken(WebSupportUtils.getCurrentRequest(), jwtProperties.getTokenKey());
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        Claims claims = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();
        return claims.get(claimKey);
    }
}
