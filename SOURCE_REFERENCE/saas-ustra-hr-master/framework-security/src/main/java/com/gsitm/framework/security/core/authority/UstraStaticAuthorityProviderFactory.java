package com.gsitm.framework.security.core.authority;

import java.util.List;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

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
public class UstraStaticAuthorityProviderFactory {

    @Lazy
    @Bean
    @ConditionalOnMissingBean
    public UstraStaticAuthorityProvider UstraStaticAuthorityProvider() {
        return new UstraStaticAuthorityProvider() {

            @Override
            public List<UstraAuthority> getAuthorities() {
                return null;
            }
        };
    }
}
