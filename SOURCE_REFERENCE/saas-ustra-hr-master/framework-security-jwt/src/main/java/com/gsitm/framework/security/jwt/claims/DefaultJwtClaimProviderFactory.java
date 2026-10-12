package com.gsitm.framework.security.jwt.claims;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;

/**
 * <pre>
 * com.gsitm.framework.security.jwt.config
 *
 * Description :
 * </pre>
 *
 * @author mercury
 * @since 2020/09/14
 */
@Configuration
public class DefaultJwtClaimProviderFactory {

    @Lazy
    @Bean
    @ConditionalOnMissingBean
    public DefaultJwtClaimProvider defaultJwtClaimFactory () {
        return new DefaultJwtClaimProvider() {
            @Override
            public void append(JwtTokenClaims claims, Authentication authentication) {
                claims.put("role", authentication.getAuthorities());
            }
        };
    }
}
