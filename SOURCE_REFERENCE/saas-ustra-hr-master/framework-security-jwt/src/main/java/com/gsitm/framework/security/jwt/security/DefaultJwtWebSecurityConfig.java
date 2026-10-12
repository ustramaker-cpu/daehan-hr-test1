package com.gsitm.framework.security.jwt.security;

import com.gsitm.framework.security.core.config.UstraWebSecurityConfig;
import com.gsitm.framework.security.core.properties.LoginProperties;
import com.gsitm.framework.security.jwt.config.DefaultJwtLogoutSuccessHandler;
import com.gsitm.framework.security.jwt.config.UstraJwtTokenConfig;
import com.gsitm.framework.security.jwt.filter.UstraJwtAuthenticationFilter;
import com.gsitm.framework.security.jwt.properties.JwtProperties;
import com.gsitm.framework.security.jwt.properties.LogoutProperties;
import com.gsitm.framework.security.jwt.storage.DefaultJwtStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.SessionCookieConfig;
import jakarta.servlet.SessionTrackingMode;
import java.util.Collections;

@Configuration
@RequiredArgsConstructor
public class DefaultJwtWebSecurityConfig implements UstraWebSecurityConfig {
    private final UstraJwtTokenConfig ustraJwtTokenConfig;

    private final DefaultJwtStorage defaultJwtStorage;
    private final JwtProperties jwtProperties;
    private final LoginProperties loginProperties;
    private final LogoutProperties logoutProperties;
//    private final OAuth2AuthorizedClientService clientService;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .logout(logout -> logout
//                    .deleteCookies(jwtProperties.getTokenKey(), jwtProperties.getKeycloakName(), "JSESSIONID")
                        .deleteCookies(jwtProperties.getTokenKey())
                        .logoutSuccessUrl(loginProperties.getDefaultUri())
                        .logoutSuccessHandler(new DefaultJwtLogoutSuccessHandler(jwtProperties, ustraJwtTokenConfig, loginProperties, logoutProperties)))
                .addFilterBefore(new UstraJwtAuthenticationFilter(ustraJwtTokenConfig, defaultJwtStorage, jwtProperties), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public ServletContextInitializer clearJsession() {
        return new ServletContextInitializer() {
            @Override
            public void onStartup(ServletContext servletContext) throws ServletException {
                servletContext.setSessionTrackingModes(Collections.singleton(SessionTrackingMode.COOKIE));
                SessionCookieConfig sessionCookieConfig=servletContext.getSessionCookieConfig();
                sessionCookieConfig.setHttpOnly(true);
            }
        };
    }
}