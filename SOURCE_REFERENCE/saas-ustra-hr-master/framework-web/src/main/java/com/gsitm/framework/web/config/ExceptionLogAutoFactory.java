package com.gsitm.framework.web.config;

import com.gsitm.framework.core.exception.UstraExceptionLogService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import jakarta.servlet.http.HttpServletRequest;

/**
 * <pre>
 * com.gsitm.framework.web.config
 *
 * Description :
 * </pre>
 *
 * @author mercury
 * @since 2020/08/24
 */
@Configuration
public class ExceptionLogAutoFactory {

    @Lazy
    @Bean
    @ConditionalOnMissingBean
    public UstraExceptionLogService createBean() {
        return new UstraExceptionLogService() {
            @Override
            public void insert(HttpServletRequest request, Exception exception) {

            }
        };
    }
}
