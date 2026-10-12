package com.gsitm.framework.security.jwt.config;

import com.gsitm.framework.core.utils.JwtSecretKeys;
import com.gsitm.framework.security.core.user.UstraUserDetails;
import com.gsitm.framework.security.jwt.storage.DefaultJwtStorage;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.Setter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.servlet.http.HttpServletRequest;
import javax.crypto.SecretKey;
import java.util.Date;

@Setter
public abstract class UstraJwtTokenProvider implements UstraJwtTokenConfig {

    private String ustraSecretKey;

    /**
     * 서명/검증에 사용할 키. 설정 시크릿이 짧아 SHA-256 파생 키를 사용한다.
     */
    protected SecretKey getSigningKey() {
        return JwtSecretKeys.hs256(ustraSecretKey);
    }

    public abstract DefaultJwtStorage getStorage();
    public abstract UstraUserDetails getUserDetails();
    public abstract String createToken(Authentication authentication);

    public Authentication getAuthentication(String token){
        Claims claims = Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload();
        UserDetails userDetails = getUserDetails().loadUserByUsername(claims.getSubject());
        return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), "", userDetails.getAuthorities());
    }

    public String getUserId(String token){
        return Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload().getSubject();
    }

    public abstract String resolveToken(HttpServletRequest httpServletRequest);

    public String resolveTokenByTokenName(HttpServletRequest httpServletRequest, String tokenName){
        return getStorage().getToken(httpServletRequest,tokenName);
    }

    public boolean validateToken(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(jwtToken);
            return !claims.getPayload().getExpiration().before(new Date());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
