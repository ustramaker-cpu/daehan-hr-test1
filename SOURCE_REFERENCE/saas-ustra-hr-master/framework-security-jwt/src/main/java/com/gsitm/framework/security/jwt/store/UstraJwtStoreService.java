package com.gsitm.framework.security.jwt.store;

public interface UstraJwtStoreService {
    void insertJwtStore(JwtStoreDto jwtStoreDto);
    Long selectCountJwtStore(String token);

    JwtStoreDto getRefreshToken(String token);
    void deleteJwtStore(String token);
}
