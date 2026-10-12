package com.gsitm.framework.security.jwt.config;

import com.gsitm.framework.security.jwt.store.JwtStoreDto;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import jakarta.servlet.http.HttpServletRequest;

@Configuration
public interface UstraJwtTokenConfig {
    String createToken(Authentication authentication);
    String createRToken(Authentication authentication);
    Authentication getAuthentication(String token);
    String getUserId(String token);
    String resolveToken(HttpServletRequest httpServletRequest);
    boolean validateToken(String jwtToken);
    Boolean existTokenStore(String token);
    void deleteTokenStore(String token);

    JwtStoreDto getRefreshToken(String token);
}
