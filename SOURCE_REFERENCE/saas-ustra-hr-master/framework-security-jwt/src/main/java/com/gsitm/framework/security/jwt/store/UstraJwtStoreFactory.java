package com.gsitm.framework.security.jwt.store;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
@Slf4j
public class UstraJwtStoreFactory {
    @Lazy
    @Bean
    @ConditionalOnMissingBean
    public UstraJwtStoreService createUstraJtwStoreService() {
        return new UstraJwtStoreService() {
            @Override
            public void insertJwtStore(JwtStoreDto jwtStoreDto) {

            }

            @Override
            public Long selectCountJwtStore(String token) {
                return 0L;
            }

            @Override
            public JwtStoreDto getRefreshToken(String token) {return new JwtStoreDto();}

            @Override
            public void deleteJwtStore(String token) {

            }
        };
    }
}
