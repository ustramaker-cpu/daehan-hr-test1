package com.gsitm.framework.security.jwt.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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
@Slf4j
public class DefaultJwtStorageFactory {

    @Lazy
    @Bean
    @ConditionalOnMissingBean
    public DefaultJwtStorage defaultJwtStorage() {
        return new DefaultJwtStorage() {
            @Override
            public void store(HttpServletResponse httpServletResponse, String tokenKey, String key) {
                Cookie cookie = new Cookie(tokenKey, key);
                cookie.setHttpOnly(true);
                cookie.setPath("/");
                httpServletResponse.addCookie(cookie);
            }

            @Override
            public String getToken(HttpServletRequest httpServletRequest, String tokenKey) {
                Cookie[] cookies = httpServletRequest.getCookies();
                if(tokenKey == null)
                    return null;
                String value = "";
                if(cookies != null){
                    for(int i=0;i<cookies.length;i++){
                        if(tokenKey.equals(cookies[i].getName())){
                            value = cookies[i].getValue();
                            break;
                        }
                    }
                }
                return value;
            }
        };
    }
}
