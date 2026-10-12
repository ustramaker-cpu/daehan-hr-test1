package com.gsitm.framework.security.core.config;

import com.gsitm.framework.core.log.properties.LogActiveProperties;
import com.gsitm.framework.security.core.authentication.UstraAuthenticationFailureHandler;
import com.gsitm.framework.security.core.authentication.UstraAuthenticationSuccessHandler;
import com.gsitm.framework.security.core.authority.AuthorizationChecker;
import com.gsitm.framework.security.core.authority.UstraAuthority;
import com.gsitm.framework.security.core.authority.UstraStaticAuthorityProvider;
import com.gsitm.framework.security.core.log.UstraLoginLog;
import com.gsitm.framework.security.core.log.UstraLoginLogService;
import com.gsitm.framework.security.core.properties.LoginProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.RequestCache;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Spring Security 6 대응.
 * WebSecurityConfigurerAdapter 가 제거되어 SecurityFilterChain / WebSecurityCustomizer 빈 방식으로 전환했다.
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
public class WebSecurityCoreConfig {

    private final ServiceWebSecurityConfig serviceWebSecurityConfig;
    private final UstraWebSecurityConfig ustraWebSecurityConfig;
    private final LoginProperties loginProperties;
    private final UstraAuthenticationSuccessHandler ustraAuthenticationSuccessHandler;
    private final UstraAuthenticationFailureHandler ustraAuthenticationFailureHandler;
    private final UstraLoginLogService ustraLoginLogService;
    private final UstraStaticAuthorityProvider ustraStaticAuthorityProvider;
    private final AuthorizationChecker authorizationChecker;
    private final RequestCache requestCache;
    private final LogActiveProperties logActiveProperties;
//    private final OAuth2AuthorizedClientService clientService;
//    private final CustomOAuth2UserHandler customOAuth2UserHandler;

    @Bean
    public SecurityFilterChain ustraSecurityFilterChain(HttpSecurity http) throws Exception {
        http.headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin()));

        ustraWebSecurityConfig.configure(http);
        serviceWebSecurityConfig.configure(http);

        http.authorizeHttpRequests(registry -> {
            if (CollectionUtils.isNotEmpty(ustraStaticAuthorityProvider.getAuthorities())) {
                for (UstraAuthority authority : ustraStaticAuthorityProvider.getAuthorities()) {
                    try {
                        AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizedUrl authorizedUrl;

                        if (authority.getHttpMethod() == null) {
                            authorizedUrl = registry.requestMatchers(authority.getUrl());
                        } else {
                            authorizedUrl = registry.requestMatchers(authority.getHttpMethod(), authority.getUrl());
                        }

                        switch (authority.getUstraAccessType()) {
                            case ANY:
                                authorizedUrl.anonymous();
                                break;
                            case PERMIT_ALL:
                                authorizedUrl.permitAll();
                                break;
                            case AUTHENTICATED:
                                authorizedUrl.authenticated();
                                break;
                            default:
                                authorizedUrl.hasRole(authority.getRole());
                                break;
                        }
                    } catch (Exception e) {
                        log.error(ExceptionUtils.getStackTrace(e));
                    }
                }
            }

            // 기존 SpEL "@authorizationChecker.check(request, authentication)" 을
            // AuthorizationManager 직접 호출로 대체 (Security 6 에서 문자열 access() 제거됨)
            registry.anyRequest().access((authentication, context) ->
                    new AuthorizationDecision(authorizationChecker.check(context.getRequest(), authentication.get())));
        });

        http.httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .formLogin(formLogin -> formLogin
                        .loginPage(loginProperties.getDefaultUri())
                        .successHandler(authenticationSuccessHandler())
                        .failureHandler(authenticationFailureHandler())
                        .permitAll())
                .requestCache(cache -> cache.requestCache(requestCache));

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer ustraWebSecurityCustomizer() {
        return web -> {
            try {
                serviceWebSecurityConfig.configure(web);
            } catch (Exception e) {
                throw new IllegalStateException("Failed to configure WebSecurity", e);
            }
        };
    }

    private AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
                this.insertLoginLog(request, authentication);
                ustraAuthenticationSuccessHandler.onAuthenticationSuccess(request, response, chain, authentication, loginProperties.getSuccessUri());
            }

            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                this.insertLoginLog(request, authentication);
                ustraAuthenticationSuccessHandler.onAuthenticationSuccess(request, response, authentication, loginProperties.getSuccessUri());
            }

            private void insertLoginLog(HttpServletRequest request, Authentication authentication) {
                if (!logActiveProperties.getLogin()) {
                    return;
                }

                try {
                    String userId = request.getParameter(loginProperties.getIdParam());
                    UstraLoginLog ustraLoginLog = new UstraLoginLog(request);
                    ustraLoginLog.setUserId(userId);
                    ustraLoginLog.setLoginDateTime(LocalDateTime.now());
                    ustraLoginLog.setSuccess(true);
                    ustraLoginLogService.insertLoginLog(ustraLoginLog);
                } catch (Exception ex) { // 비즈니스 로직 영향
                    log.error(ExceptionUtils.getStackTrace(ex));
                }
            }
        };
    }

    private AuthenticationFailureHandler authenticationFailureHandler() {
        return new AuthenticationFailureHandler() {
            @Override
            public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
                //실패시에는 로그 쌓지 않음..
                //this.insertLoginLog(request);
                ustraAuthenticationFailureHandler.onAuthenticationFailure(request, response, exception);
            }

            private void insertLoginLog(HttpServletRequest request) {
                if (!logActiveProperties.getLogin()) {
                    return;
                }
                try {
                    String userId = request.getParameter(loginProperties.getIdParam());
                    UstraLoginLog ustraLoginLog = new UstraLoginLog(request);
                    ustraLoginLog.setUserId(userId);
                    ustraLoginLog.setLoginDateTime(LocalDateTime.now());
                    ustraLoginLog.setSuccess(false);
                    ustraLoginLogService.insertLoginLog(ustraLoginLog);
                } catch (Exception ex) { // 비즈니스 로직 영향
                    log.error(ExceptionUtils.getStackTrace(ex));
                }

            }
        };
    }
}
