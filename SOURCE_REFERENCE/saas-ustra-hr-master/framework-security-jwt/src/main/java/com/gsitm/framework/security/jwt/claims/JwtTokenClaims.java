package com.gsitm.framework.security.jwt.claims;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

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
@Getter
@Setter
public class JwtTokenClaims {
    private Map<String, Object> claims;

    public JwtTokenClaims() {
        this.claims = new HashMap<>();
    }

    public void put(String key, Object obj) {
        claims.put(key, obj);
    }

    public Object get(String key) {
        return claims.get(key);
    }

}
