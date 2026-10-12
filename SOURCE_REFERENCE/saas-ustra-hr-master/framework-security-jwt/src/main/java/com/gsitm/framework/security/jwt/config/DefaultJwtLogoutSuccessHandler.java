package com.gsitm.framework.security.jwt.config;

import com.gsitm.framework.security.core.properties.LoginProperties;
import com.gsitm.framework.security.jwt.properties.JwtProperties;
import com.gsitm.framework.security.jwt.properties.LogoutProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class DefaultJwtLogoutSuccessHandler implements LogoutSuccessHandler {
    private final JwtProperties jwtProperties;
    private final UstraJwtTokenConfig ustraJwtTokenConfig;
    private final LoginProperties loginProperties;
    private final LogoutProperties logoutProperties;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        if(jwtProperties.getActiveDbManage()){
            ustraJwtTokenConfig.deleteTokenStore(ustraJwtTokenConfig.resolveToken(request));
        }
        String logoutSuccessuri = logoutProperties.getSuccessUri();

        if("".equals(logoutSuccessuri)){
            logoutSuccessuri = loginProperties.getDefaultUri();
        }

        log.info("★★★★★ DefaultJwtLogoutSuccessHandler.onLogoutSuccess ==>>{}", logoutSuccessuri);

        response.sendRedirect(logoutSuccessuri);

//        if(jwtProperties.getActiveDbManage()){
//            ustraJwtTokenConfig.deleteTokenStore(ustraJwtTokenConfig.resolveToken(request));
//        }
//        String logoutSuccessuri = logoutProperties.getSuccessUri();
//
//        if("".equals(logoutSuccessuri)){
//            logoutSuccessuri = loginProperties.getDefaultUri();
//        }
//
//        if(jwtProperties.getKeycloakEnabled()){
//            response.sendRedirect("https://demo-awp-sso.alice-ailab.com/realms/test/protocol/openid-connect/logout");
//        }else{
//            response.sendRedirect(logoutSuccessuri);
//        }
    }
}
