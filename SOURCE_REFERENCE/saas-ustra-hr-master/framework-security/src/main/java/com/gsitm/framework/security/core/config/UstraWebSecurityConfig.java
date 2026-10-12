package com.gsitm.framework.security.core.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

public interface UstraWebSecurityConfig {
    void configure(HttpSecurity http) throws Exception;
}
