package com.gsitm.framework.security.core.log;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;

import jakarta.servlet.http.HttpServletRequest;

/**
 * <pre>
 * com.gsitm.framework.security.core.log
 *
 * Description :
 * </pre>
 *
 * @author mercury
 * @since 2020/08/25
 */
@Configuration
@Slf4j
public class UstraLogServiceFactory {

    @Lazy
    @Bean
    @ConditionalOnMissingBean
    public UstraAccessLogService createUstraAccessLogService() {
        return new UstraAccessLogService() {
            @Override
            public void insertAccessLog(UstraAccessLog ustraAccessLog) {

            }

        };
    }

    @Lazy
    @Bean
    @ConditionalOnMissingBean
    public UstraLoginLogService createUstraLoginLogService() {
        return new UstraLoginLogService() {
            @Override
            public void insertLoginLog(UstraLoginLog ustraLoginLog) {

            }
        };
    }
}
