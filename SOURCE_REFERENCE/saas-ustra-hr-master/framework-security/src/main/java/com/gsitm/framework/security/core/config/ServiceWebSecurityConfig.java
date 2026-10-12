package com.gsitm.framework.security.core.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;

public interface ServiceWebSecurityConfig {

    void configure(HttpSecurity http) throws Exception;

    void configure(WebSecurity web) throws Exception;

}
