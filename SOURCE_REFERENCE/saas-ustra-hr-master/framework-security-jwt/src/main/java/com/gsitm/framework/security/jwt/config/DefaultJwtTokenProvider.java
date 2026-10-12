package com.gsitm.framework.security.jwt.config;

import com.gsitm.framework.security.core.user.UstraUserDetails;
import com.gsitm.framework.security.jwt.claims.DefaultJwtClaimProvider;
import com.gsitm.framework.security.jwt.claims.JwtTokenClaims;
import com.gsitm.framework.security.jwt.properties.JwtProperties;
import com.gsitm.framework.security.jwt.storage.DefaultJwtStorage;
import com.gsitm.framework.security.jwt.store.JwtStoreDto;
import com.gsitm.framework.security.jwt.store.UstraJwtStoreService;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Date;

@Configuration
@RequiredArgsConstructor
public class DefaultJwtTokenProvider extends UstraJwtTokenProvider {

    private final JwtProperties jwtProperties;
    private final DefaultJwtClaimProvider defaultJwtClaimProvider;
    private final UstraUserDetails ustraUserDetails;
    private final DefaultJwtStorage defaultJwtStorage;
    private final UstraJwtStoreService ustraJwtStoreService;

    private String secretKey;

    @PostConstruct
    protected void init() {
        secretKey = jwtProperties.getSecretKey();
        super.setUstraSecretKey(secretKey);
    }

    @Override
    public DefaultJwtStorage getStorage() {
        return this.defaultJwtStorage;
    }

    @Override
    public UstraUserDetails getUserDetails() {
        return this.ustraUserDetails;
    }

    @Override
    public String createToken(Authentication authentication) {
        long tokenValidTime = jwtProperties.getValidMinute() * 60 * 1000L;
        long rtokenValidTime = jwtProperties.getRvalidMinute() * 60 * 1000L;
        JwtTokenClaims claims = new JwtTokenClaims();
        defaultJwtClaimProvider.append(claims, authentication);
        Date now = new Date();
        Date expireDate = new Date(now.getTime() + tokenValidTime);
        Date rexpireDate = new Date(now.getTime() + rtokenValidTime);
        String token = Jwts.builder()
                .claims(claims.getClaims())
                .subject(authentication.getName())
                .issuedAt(now)
                .expiration(expireDate)
                .signWith(getSigningKey(), Jwts.SIG.HS256)
                .compact();

        String rtoken = Jwts.builder()
                .claims(claims.getClaims())
                .subject(authentication.getName())
                .issuedAt(now)
                .expiration(rexpireDate)
                .signWith(getSigningKey(), Jwts.SIG.HS256)
                .compact();

        if(jwtProperties.getActiveDbManage()){
            JwtStoreDto jwtStoreDto = new JwtStoreDto();
            jwtStoreDto.setToken(token);
            jwtStoreDto.setRtoken(rtoken);
            jwtStoreDto.setExpireDate(expireDate);
            ustraJwtStoreService.insertJwtStore(jwtStoreDto);
        }

        return token;
    }

    @Override
    public String createRToken(Authentication authentication) {
        return "";
    }

    @Override
    public String resolveToken(HttpServletRequest httpServletRequest) {
        return super.resolveTokenByTokenName(httpServletRequest, jwtProperties.getTokenKey());
    }
    @Override
    public JwtStoreDto getRefreshToken(String token) {
        return ustraJwtStoreService.getRefreshToken(token);
    }


    @Override
    public Boolean existTokenStore(String token){
        if(jwtProperties.getActiveDbManage()){
            if (ustraJwtStoreService.selectCountJwtStore(token) > 0){
                return true;
            }else{
                return false;
            }
        }

        return true;
    }

    @Override
    public void deleteTokenStore(String token) {
        if(jwtProperties.getActiveDbManage()){
            ustraJwtStoreService.deleteJwtStore(token);
        }
    }
}
