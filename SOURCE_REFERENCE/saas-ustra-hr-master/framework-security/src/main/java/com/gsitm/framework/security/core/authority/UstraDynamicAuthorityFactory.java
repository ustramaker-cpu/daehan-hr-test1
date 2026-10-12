package com.gsitm.framework.security.core.authority;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;

import jakarta.servlet.http.HttpServletRequest;

/**
 * <pre>
 * com.gsitm.framework.security.core.authority
 *
 * Description :
 * </pre>
 *
 * @author mercury
 * @since 2020/08/23
 */
@Configuration
public class UstraDynamicAuthorityFactory {

    @Lazy
    @Bean
    @ConditionalOnMissingBean
    public UstraDynamicAuthorityChecker authorizationCheckerFactory() {
        return new UstraDynamicAuthorityChecker() {
            @Override
            public boolean check(HttpServletRequest request, Authentication authentication) {
                return true;
            }
        };
    }
}
