package com.gsitm.framework.security.jwt.filter;

import com.gsitm.framework.core.utils.JwtSecretKeys;
import com.gsitm.framework.security.jwt.config.UstraJwtTokenConfig;
import com.gsitm.framework.security.jwt.properties.JwtProperties;
import com.gsitm.framework.security.jwt.storage.DefaultJwtStorage;
import com.gsitm.framework.security.jwt.store.JwtStoreDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.filter.GenericFilterBean;

import jakarta.servlet.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import javax.crypto.SecretKey;
import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class UstraJwtAuthenticationFilter extends GenericFilterBean {
    private final UstraJwtTokenConfig ustraJWTTokenConfig;
    private final DefaultJwtStorage defaultJwtStorage;
    private final JwtProperties jwtProperties;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 헤더에서 JWT 를 받아옵니다.
        String token = ustraJWTTokenConfig.resolveToken((HttpServletRequest) request);

        log.info("token==>" + token);

        // 유효한 토큰인지 확인합니다.
        try {
            if( token == null || "".equals(token) ){
                //토큰값 없으면 로그인 페이지로....
                chain.doFilter(request, response);
                return;
            }

            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            HttpServletResponse httpServletResponse = (HttpServletResponse) response;

            if (token != null && ustraJWTTokenConfig.validateToken(token)) {
                log.info("유효한 토큰...");
                //토큰 내용을 확인하여 서버네임이 일치하는지 확인하여 일치하지 않으면 로그인으로 이동...
                SecretKey secretKey = JwtSecretKeys.hs256(jwtProperties.getSecretKey());
                String serverName = jwtProperties.getServerName();
                //토큰에서 userId, coId을 불러온다.
                Claims claims = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();

                //토큰에 저장된 서버네임과 설정에 저장된 서버네임이 다른경우 로그아웃한다.
                if( !serverName.equals(claims.get("serverName")) ) {
                    logoutProcess(token, httpServletResponse);
                    chain.doFilter(request, response);
                    return;
                }

                //DB에 Access Token이 존재하는지 체크..
                if (ustraJWTTokenConfig.existTokenStore(token)) {
                    log.info("디비존재...");
                    // 토큰이 유효하면 토큰으로부터 유저 정보를 받아옵니다.
                    Authentication authentication = ustraJWTTokenConfig.getAuthentication(token);
                    // SecurityContext 에 Authentication 객체를 저장합니다.
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                    log.info("디비없음...");
                    //DB에 존재하지 않으면 token 삭제 및
                    logoutProcess(token, httpServletResponse);
                    log.info("쿠키삭제...");
                    //ajax요청시 토큰이 만료된 경우 419 error 코드 리턴
                    if ("XMLHttpRequest".equalsIgnoreCase(httpServletRequest.getHeader("X-Requested-With"))) {
                        httpServletResponse.sendError(419);
                        return;
                    }
                }
            } else {
                //유효기간이 지난경우...
                //Refresh Token이 유효한지 체크
                //ustraJWTTokenConfig.createToken()
                //1. Refresh Token이 유효한지 체크...
                //2. Refresh Token이 유효하다면 Access Token 재생성..후 토큰사용여부 업데이트
                //3. Refresh Token이 유효하지 않다면 재발급
                //DB에서 Access Token과 맵핑된 RefreshToken을 가져온다.
                log.info("유효기간 지남.....");

                JwtStoreDto jwtStoreDto = ustraJWTTokenConfig.getRefreshToken(token);
                //이전토큰의 user정보를 가져온다....
                //재발급 후 토큰 삭제
                ustraJWTTokenConfig.deleteTokenStore(token);
                if (jwtStoreDto != null && ustraJWTTokenConfig.validateToken(jwtStoreDto.getRtoken())) {
                    //rtoken이 valid하면 재발급 로직
                    Authentication authentication = ustraJWTTokenConfig.getAuthentication(jwtStoreDto.getRtoken());
                    //log.info("토큰재발급 ");
                    //토큰 재발급 메서드
                    String ntoken = ustraJWTTokenConfig.createRToken(authentication);

                    //쿠키저장..
                    //HttpServletResponse httpServletResponse = (HttpServletResponse) response;

                    //쿠키저장전 삭제
                    deleteCookie(httpServletResponse);

                    defaultJwtStorage.store(httpServletResponse, jwtProperties.getTokenKey(), ntoken);

                    // SecurityContext 에 Authentication 객체를 저장합니다.
                    SecurityContextHolder.getContext().setAuthentication(ustraJWTTokenConfig.getAuthentication(ntoken));
                } else {

                    log.info("R토큰유효기간 지남.. ");
                    //invalid면 토큰 삭제
                    logoutProcess(token, httpServletResponse);
                    //ajax요청시 토큰이 만료된 경우 419 error 코드 리턴
                    if ("XMLHttpRequest".equalsIgnoreCase(httpServletRequest.getHeader("X-Requested-With"))) {
                        httpServletResponse.sendError(419);
                        return;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Throwable cause = ExceptionUtils.getRootCause(e);

            HttpServletResponse httpServletResponse = (HttpServletResponse) response;
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;

            if (cause instanceof UsernameNotFoundException) {
                logoutProcess(token, httpServletResponse);

                httpServletRequest.setAttribute(RequestDispatcher.ERROR_STATUS_CODE, 419);
                httpServletRequest.getRequestDispatcher("/error").forward(httpServletRequest, httpServletResponse);
                return;
            } else {
                deleteCookie(httpServletResponse);
                httpServletResponse.sendError(419, "인증오류");
                return;
            }
        }

        chain.doFilter(request, response);
    }

    private void deleteCookie(HttpServletResponse httpServletResponse) {
        Cookie cookie = new Cookie(jwtProperties.getTokenKey(), null);
        // 쿠키의 유효시간을 0으로 설정하여 바로 만료시킨다.
        cookie.setMaxAge(0);
        // 응답에 쿠키 추가
        httpServletResponse.addCookie(cookie);
    }

    private void logoutProcess(String token, HttpServletResponse response){
        ustraJWTTokenConfig.deleteTokenStore(token);
        //쿠키저장전 삭제
        deleteCookie(response);
    }
}
