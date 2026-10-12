package com.gsitm.framework.security.core.authentication;

import com.gsitm.framework.security.core.properties.LoginProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <pre>
 * com.gsitm.framework.security.core.authentication
 *
 * Description :
 * </pre>
 *
 * @author mercury
 * @since 2020/08/25
 */
@Configuration
@Slf4j
@RequiredArgsConstructor
public class UstraAuthenticationHandlerFactory {

    /**
     * 로그인 실패 forward 를 이미 수행했음을 표시하는 요청 속성.
     * <p>fail-uri 가 로그인 처리 URL(process-uri) 과 같기 때문에, 시큐리티 필터 체인이 FORWARD
     * 디스패치까지 처리하도록 설정되면 forward 된 요청이 다시 인증 필터에 걸려
     * 인증 실패 -> forward 가 무한 반복되고 StackOverflowError 로 죽는다.
     * 근본 설정은 {@code spring.security.filter.dispatcher-types} 이고, 이 속성은 안전장치다.
     */
    private static final String FORWARDED_ATTRIBUTE = UstraAuthenticationHandlerFactory.class.getName() + ".FORWARDED";

    private final LoginProperties loginProperties;

    @Lazy
    @Bean
    @ConditionalOnMissingBean
    public UstraAuthenticationSuccessHandler createUstraAuthenticationSuccessHandler() {
        return new UstraAuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication, String successUri) throws IOException, ServletException {

            }

            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication, String successUri) throws IOException, ServletException {

            }
        };
    }

    @Lazy
    @Bean
    @ConditionalOnMissingBean
    public UstraAuthenticationFailureHandler createUstraAuthenticationFailureHandler() {
        return new UstraAuthenticationFailureHandler() {
            @Override
            public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
                if (request.getAttribute(FORWARDED_ATTRIBUTE) != null) {
                    log.warn("로그인 실패 forward 가 재진입했습니다. 무한 재귀를 막기 위해 {} 로 리다이렉트합니다."
                            + " spring.security.filter.dispatcher-types 에서 FORWARD 가 제외되어 있는지 확인하세요.",
                            loginProperties.getFailUri());
                    if (!response.isCommitted()) {
                        response.sendRedirect(request.getContextPath() + loginProperties.getFailUri());
                    }
                    return;
                }
                request.setAttribute(FORWARDED_ATTRIBUTE, Boolean.TRUE);

                request.getRequestDispatcher(loginProperties.getFailUri()).forward(request, response);
            }
        };
    }
}
