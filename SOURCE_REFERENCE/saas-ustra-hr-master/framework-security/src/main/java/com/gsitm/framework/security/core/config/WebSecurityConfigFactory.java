package com.gsitm.framework.security.core.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;

@Configuration
public class WebSecurityConfigFactory {


    @Lazy
    @Bean
    @ConditionalOnMissingBean
    public RequestCache requestCache() {
        return new HttpSessionRequestCache();
    }

    @Lazy
    @Bean
    @ConditionalOnMissingBean
    protected UstraWebSecurityConfig ustraWebSecurityConfigConfigFactory() {
        return new UstraWebSecurityConfig() {
            @Override
            public void configure(HttpSecurity http) throws Exception {

            }
        };
    }

    @Lazy
    @Bean
    @ConditionalOnMissingBean
    protected ServiceWebSecurityConfig serviceWebSecurityConfigFactory() {
        return new ServiceWebSecurityConfig() {
            @Override
            public void configure(HttpSecurity http) throws Exception {

            }

            @Override
            public void configure(WebSecurity web) throws Exception {
                web.ignoring()
                        .requestMatchers("/assets/**")
                        .requestMatchers("/images/**");
            }
        };
    }
}
